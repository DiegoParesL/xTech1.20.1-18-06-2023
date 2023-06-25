package net.xalienated.xtech.block.entity;

import com.google.gson.JsonElement;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.renderer.v1.material.MaterialFinder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.impl.client.indigo.renderer.material.MaterialFinderImpl;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.xalienated.xtech.block.custom.RecyclerBlock;
import net.xalienated.xtech.item.ModItems;
import net.xalienated.xtech.screen.RecyclerScreen;
import net.xalienated.xtech.screen.RecyclerScreenHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;


public class RecyclerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, GeoBlockEntity {
    private int progress =0;
    private int maxProgress = 72;
    private int fuelTime = 0;
    private int maxFuelTime = 576;
    private int flintUses = 0;
    private int maxFlintUses = 10;

    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            switch (index){
                case 0: return RecyclerBlockEntity.this.progress;
                case 1: return RecyclerBlockEntity.this.maxProgress;
                case 2: return RecyclerBlockEntity.this.fuelTime;
                case 3: return RecyclerBlockEntity.this.maxFuelTime;
                case 4: return RecyclerBlockEntity.this.flintUses;
                case 5: return RecyclerBlockEntity.this.maxFlintUses;

                default: return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index){
                case 0: RecyclerBlockEntity.this.progress = value; break;
                case 1: RecyclerBlockEntity.this.maxProgress = value;break;
                case 2: RecyclerBlockEntity.this.fuelTime = value;break;
                case 3: RecyclerBlockEntity.this.maxFuelTime = value;break;
                case 4: RecyclerBlockEntity.this.flintUses = value;break;
                case 5: RecyclerBlockEntity.this.maxFlintUses = value;break;
            }
        }

        @Override
        public int size() {
            return 6;
        }
    };
    public RecyclerBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntities.RECYCLER, pos, state);
    }

    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(4, ItemStack.EMPTY);
    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }
    @Override
    public Text getDisplayName() {
        return Text.literal("Recycler");
    }



    @Override
    public void markDirty() {
        if(!world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());
            for(int i = 0; i < inventory.size(); i++) {
                data.writeItemStack(inventory.get(i));
            }
            data.writeBlockPos(getPos());


        }

        super.markDirty();
    }



    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new RecyclerScreenHandler(syncId,playerInventory, this, propertyDelegate );
    }
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("recycler_block.progress", progress);
        nbt.putInt("recycler_block.fuelTime", fuelTime);
        nbt.putInt("recycler_block.maxFuelTime", maxFuelTime);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("recycler_block.progress");
        fuelTime = nbt.getInt("recycler_block.fuelTime");
        maxFuelTime= nbt.getInt("recycler_block.maxFuelTime");
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, RecyclerBlockEntity entity){
        if(world.isClient()){return;}

        if(entity.fuelTime>= entity.maxFuelTime){
            entity.resetFuelTime();
        }else if (entity.fuelTime==0 && hasRecipe(entity)&& ( entity.getStack(0).getItem() == Items.BLAZE_ROD || entity.getStack(0).getItem() == Items.COAL || entity.getStack(0).getItem() == Items.BLAZE_POWDER || entity.getStack(0).getItem() == Items.COAL_BLOCK)) {
            entity.removeStack(0,1);
            entity.fuelTime++;
        } else if (entity.fuelTime>0 && entity.fuelTime <entity.maxFuelTime) {
            entity.fuelTime++;
        }

        if((hasRecipeAndFuel(entity) && hasNotReachedStackLimit(entity))|| entity.fuelTime >0){

            if(hasRecipeAndFuel(entity) && (canInsertItemIntoOutputSlot(entity) || isValidRecipe(entity,entity.getStack(1)))) {
                entity.progress++;
            }else {
                entity.resetProgress();
            }


            markDirty(world, blockPos, state);
            if(entity.progress >= entity.maxProgress){
                craftItem(entity);
                entity.resetProgress();
            }


        }else{
            entity.resetProgress();
        }
    }
    private void resetProgress(){
        this.progress = 0;
    }
    private void resetFuelTime(){
        this.fuelTime = 0;
    }
    private void resetFlintUses(){
        this.flintUses = 0;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(RecyclerBlock.FACING);

        if(side == Direction.UP || side == Direction.DOWN) {
            return false;
        }

        // Top insert 1
        // Right insert 1
        // Left insert 0
        return switch (localDir) {
            default ->
                    side.getOpposite() == Direction.NORTH && slot == 1 ||
                            side.getOpposite() == Direction.EAST && slot == 1 ||
                            side.getOpposite() == Direction.WEST && slot == 0;
            case EAST ->
                    side.rotateYClockwise() == Direction.NORTH && slot == 1 ||
                            side.rotateYClockwise() == Direction.EAST && slot == 1 ||
                            side.rotateYClockwise() == Direction.WEST && slot == 0;
            case SOUTH ->
                    side == Direction.NORTH && slot == 1 ||
                            side == Direction.EAST && slot == 1 ||
                            side == Direction.WEST && slot == 0;
            case WEST ->
                    side.rotateYCounterclockwise() == Direction.NORTH && slot == 1 ||
                            side.rotateYCounterclockwise() == Direction.EAST && slot == 1 ||
                            side.rotateYCounterclockwise() == Direction.WEST && slot == 0;
        };
    }
    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(RecyclerBlock.FACING);

        if(side == Direction.UP) {
            return false;
        }

        // Down extract 2
        if(side == Direction.DOWN) {
            return slot == 3;
        }

        // bottom extract 2
        // right extract 2
        return switch (localDir) {
            default -> side.getOpposite() == Direction.SOUTH && slot == 3 ||
                    side.getOpposite() == Direction.EAST && slot == 3;
            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == 3 ||
                    side.rotateYClockwise() == Direction.EAST && slot == 3;
            case SOUTH -> side == Direction.SOUTH && slot == 3 ||
                    side == Direction.EAST && slot == 3;
            case WEST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == 3 ||
                    side.rotateYCounterclockwise() == Direction.EAST && slot == 3;
        };
    }
    private static void craftItem(RecyclerBlockEntity entity) {
        SimpleInventory inv = new SimpleInventory( entity.size());
        for (int i =0; i< entity.size();i++){
            inv.setStack(i, entity.getStack(i));
        }

        if(hasRecipeAndFuel(entity)){
            ItemStack itemStack = entity.getStack(1);
            if(entity.flintUses>= entity.maxFlintUses){
                entity.removeStack(2, 1);
                entity.resetFlintUses();

            }else{
                entity.flintUses++;
            }

            if (isValidRecipe(entity, itemStack)){
                hasValidRecipe(entity,itemStack);
            }else{
                ItemStack itemStack1 = entity.getStack(1).copy();
                if(entity.getStack(3).getItem()==entity.getStack(1).getItem() || entity.getStack(3).isEmpty()) {
                    entity.setStack(3,new ItemStack(itemStack1.getItem(), (entity.getStack(3).getCount()+1)));
                    entity.removeStack(1, 1);
                }


            }
        }



    }
    private static boolean isValidRecipe(RecyclerBlockEntity entity, ItemStack itemStack){

        //NETHERITE
        if((entity.getStack(3).isOf(Items.NETHERITE_SCRAP) || entity.getStack(3).isEmpty()) &&(itemStack.isOf(Items.NETHERITE_CHESTPLATE) || itemStack.isOf(Items.NETHERITE_LEGGINGS) || itemStack.isOf(Items.NETHERITE_HELMET)|| itemStack.isOf(Items.NETHERITE_BOOTS))){
            return true;
        }else if((entity.getStack(3).isOf(Items.NETHERITE_SCRAP) ||  entity.getStack(3).isEmpty()) &&( itemStack.isOf(Items.NETHERITE_SHOVEL)|| itemStack.isOf(Items.NETHERITE_AXE) || itemStack.isOf(Items.NETHERITE_PICKAXE) || itemStack.isOf(Items.NETHERITE_HOE)|| itemStack.isOf(Items.NETHERITE_SWORD))){
            return true;
        }else if((entity.getStack(3).isOf(Items.NETHERITE_SCRAP) ||  entity.getStack(3).isEmpty() )&&(itemStack.isOf(ModItems.BARREL)|| itemStack.isOf(ModItems.RECEIVER))){
            return true;
        } else if ((entity.getStack(3).isOf(Items.NETHERITE_SCRAP) ||  entity.getStack(3).isEmpty()) &&(itemStack.isOf(ModItems.GLOCK))) {
            return true;
        }
        //DIAMOND
        if((entity.getStack(3).isOf(Items.DIAMOND)|| entity.getStack(3).isEmpty()) &&(itemStack.isOf(Items.DIAMOND_CHESTPLATE) || itemStack.isOf(Items.DIAMOND_LEGGINGS) || itemStack.isOf(Items.DIAMOND_HELMET)|| itemStack.isOf(Items.DIAMOND_BOOTS))){
            return true;
        }else if((entity.getStack(3).isOf(Items.DIAMOND)|| entity.getStack(3).isEmpty()) &&( itemStack.isOf(Items.DIAMOND_SHOVEL))){
            return true;
        }else if(  (entity.getStack(3).isOf(Items.DIAMOND)|| entity.getStack(3).isEmpty()) &&(itemStack.isOf(Items.DIAMOND_AXE) || itemStack.isOf(Items.DIAMOND_PICKAXE) || itemStack.isOf(Items.DIAMOND_HOE)|| itemStack.isOf(Items.DIAMOND_SWORD))){
            return true;
        }
        //IRON
        if(  (entity.getStack(3).isOf(Items.IRON_INGOT) || entity.getStack(3).isEmpty()) &&(itemStack.isOf(Items.IRON_CHESTPLATE) || itemStack.isOf(Items.IRON_LEGGINGS) || itemStack.isOf(Items.IRON_HELMET)|| itemStack.isOf(Items.IRON_BOOTS))){
            return true;
        }else if((entity.getStack(3).isOf(Items.IRON_INGOT) || entity.getStack(3).isEmpty())  &&( itemStack.isOf(Items.IRON_SHOVEL))){
            return true;
        }else if((entity.getStack(3).isOf(Items.IRON_INGOT) || entity.getStack(3).isEmpty())  &&( itemStack.isOf(Items.IRON_AXE) || itemStack.isOf(Items.IRON_PICKAXE) || itemStack.isOf(Items.IRON_HOE)|| itemStack.isOf(Items.IRON_SWORD)|| itemStack.isOf(Items.IRON_BOOTS))){
            return true;
        }
        //GOLD
        if((entity.getStack(3).isOf(Items.GOLD_INGOT) || entity.getStack(3).isEmpty()) &&(itemStack.isOf(Items.GOLDEN_CHESTPLATE) || itemStack.isOf(Items.GOLDEN_LEGGINGS) || itemStack.isOf(Items.GOLDEN_HELMET)|| itemStack.isOf(Items.GOLDEN_BOOTS))){
            return true;
        }else if((entity.getStack(3).isOf(Items.GOLD_INGOT) || entity.getStack(3).isEmpty()) &&( itemStack.isOf(Items.GOLDEN_SHOVEL))){
            return true;
        }else if((entity.getStack(3).isOf(Items.GOLD_INGOT) || entity.getStack(3).isEmpty() ) &&( itemStack.isOf(Items.GOLDEN_AXE) || itemStack.isOf(Items.GOLDEN_PICKAXE) || itemStack.isOf(Items.GOLDEN_HOE)|| itemStack.isOf(Items.GOLDEN_SWORD))){
            return true;
        }
        //LEATHER
        if((entity.getStack(3).isOf(Items.LEATHER) || entity.getStack(3).isEmpty()) &&(itemStack.isOf(Items.LEATHER_CHESTPLATE) || itemStack.isOf(Items.LEATHER_LEGGINGS) || itemStack.isOf(Items.LEATHER_HELMET)|| itemStack.isOf(Items.LEATHER_BOOTS))){
            return true;
        }
        //STONE
        if((entity.getStack(3).isOf(Items.COBBLESTONE) || entity.getStack(3).isEmpty())  &&(itemStack.isOf(Items.STONE_SHOVEL))){
            return true;
        }else if((entity.getStack(3).isOf(Items.LEATHER) || entity.getStack(3).isEmpty() ) &&( itemStack.isOf(Items.STONE_AXE) || itemStack.isOf(Items.STONE_PICKAXE) || itemStack.isOf(Items.STONE_HOE)|| itemStack.isOf(Items.STONE_SWORD))){
            return true;
        }
        //WOOD
        if((entity.getStack(3).isOf(Items.OAK_PLANKS) || entity.getStack(3).isEmpty()) &&( itemStack.isOf(Items.WOODEN_SHOVEL))){
            return true;
        }else if((entity.getStack(3).isOf(Items.OAK_PLANKS) || entity.getStack(3).isEmpty())&&( itemStack.isOf(Items.WOODEN_AXE) || itemStack.isOf(Items.WOODEN_PICKAXE) || itemStack.isOf(Items.WOODEN_HOE)|| itemStack.isOf(Items.WOODEN_SWORD))){
            return true;
        }
        return false;
    }
    private static void hasValidRecipe(RecyclerBlockEntity entity, ItemStack itemStack){

        //NETHERITE
        if((entity.getStack(3).isOf(Items.NETHERITE_SCRAP) || entity.getStack(3).isEmpty()) &&(itemStack.isOf(Items.NETHERITE_CHESTPLATE) || itemStack.isOf(Items.NETHERITE_LEGGINGS) || itemStack.isOf(Items.NETHERITE_HELMET)|| itemStack.isOf(Items.NETHERITE_BOOTS))){
            entity.setStack(3, new ItemStack(Items.NETHERITE_SCRAP,
                    entity.getStack(3).getCount() + 2));
        }else if((entity.getStack(3).isOf(Items.NETHERITE_SCRAP) ||  entity.getStack(3).isEmpty()) &&( itemStack.isOf(Items.NETHERITE_SHOVEL)|| itemStack.isOf(Items.NETHERITE_AXE) || itemStack.isOf(Items.NETHERITE_PICKAXE) || itemStack.isOf(Items.NETHERITE_HOE)|| itemStack.isOf(Items.NETHERITE_SWORD))){
            entity.setStack(3, new ItemStack(Items.NETHERITE_SCRAP,
                    entity.getStack(3).getCount() + 2));
        }else if((entity.getStack(3).isOf(Items.NETHERITE_SCRAP) ||  entity.getStack(3).isEmpty() )&&(itemStack.isOf(ModItems.BARREL)|| itemStack.isOf(ModItems.RECEIVER))){
            entity.setStack(3, new ItemStack(Items.NETHERITE_SCRAP,
                    entity.getStack(3).getCount() + 2));
        } else if ((entity.getStack(3).isOf(Items.NETHERITE_SCRAP) ||  entity.getStack(3).isEmpty()) &&(itemStack.isOf(ModItems.GLOCK))) {
            entity.setStack(3, new ItemStack(Items.NETHERITE_INGOT,
                    entity.getStack(3).getCount() + 2));
        }
        //DIAMOND
        if((entity.getStack(3).isOf(Items.DIAMOND)|| entity.getStack(3).isEmpty()) &&(itemStack.isOf(Items.DIAMOND_CHESTPLATE) || itemStack.isOf(Items.DIAMOND_LEGGINGS) || itemStack.isOf(Items.DIAMOND_HELMET)|| itemStack.isOf(Items.DIAMOND_BOOTS))){
            entity.setStack(3, new ItemStack(Items.DIAMOND,
                    entity.getStack(3).getCount() + 4));
        }else if((entity.getStack(3).isOf(Items.DIAMOND)|| entity.getStack(3).isEmpty()) &&( itemStack.isOf(Items.DIAMOND_SHOVEL))){
            entity.setStack(3, new ItemStack(Items.DIAMOND,
                    entity.getStack(3).getCount() + 1));
        }else if(  (entity.getStack(3).isOf(Items.DIAMOND)|| entity.getStack(3).isEmpty()) &&(itemStack.isOf(Items.DIAMOND_AXE) || itemStack.isOf(Items.DIAMOND_PICKAXE) || itemStack.isOf(Items.DIAMOND_HOE)|| itemStack.isOf(Items.DIAMOND_SWORD))){
            entity.setStack(3, new ItemStack(Items.DIAMOND,
                    entity.getStack(3).getCount() + 2));
        }
        //IRON
        if(  (entity.getStack(3).isOf(Items.IRON_INGOT) || entity.getStack(3).isEmpty()) &&(itemStack.isOf(Items.IRON_CHESTPLATE) || itemStack.isOf(Items.IRON_LEGGINGS) || itemStack.isOf(Items.IRON_HELMET)|| itemStack.isOf(Items.IRON_BOOTS))){
            entity.setStack(3, new ItemStack(Items.IRON_INGOT,
                    entity.getStack(3).getCount() + 4));
        }else if((entity.getStack(3).isOf(Items.IRON_INGOT) || entity.getStack(3).isEmpty())  &&( itemStack.isOf(Items.IRON_SHOVEL))){
            entity.setStack(3, new ItemStack(Items.IRON_INGOT,
                    entity.getStack(3).getCount() + 1));
        }else if((entity.getStack(3).isOf(Items.IRON_INGOT) || entity.getStack(3).isEmpty())  &&( itemStack.isOf(Items.IRON_AXE) || itemStack.isOf(Items.IRON_PICKAXE) || itemStack.isOf(Items.IRON_HOE)|| itemStack.isOf(Items.IRON_SWORD)|| itemStack.isOf(Items.IRON_BOOTS))){
            entity.setStack(3, new ItemStack(Items.IRON_INGOT,
                    entity.getStack(3).getCount() + 2));
        }
        //GOLD
        if((entity.getStack(3).isOf(Items.GOLD_INGOT) || entity.getStack(3).isEmpty()) &&(itemStack.isOf(Items.GOLDEN_CHESTPLATE) || itemStack.isOf(Items.GOLDEN_LEGGINGS) || itemStack.isOf(Items.GOLDEN_HELMET)|| itemStack.isOf(Items.GOLDEN_BOOTS))){
            entity.setStack(3, new ItemStack(Items.GOLD_INGOT,
                    entity.getStack(3).getCount() + 4));
        }else if((entity.getStack(3).isOf(Items.GOLD_INGOT) || entity.getStack(3).isEmpty()) &&( itemStack.isOf(Items.GOLDEN_SHOVEL))){
            entity.setStack(3, new ItemStack(Items.GOLD_INGOT,
                    entity.getStack(3).getCount() + 1));
        }else if((entity.getStack(3).isOf(Items.GOLD_INGOT) || entity.getStack(3).isEmpty() ) &&( itemStack.isOf(Items.GOLDEN_AXE) || itemStack.isOf(Items.GOLDEN_PICKAXE) || itemStack.isOf(Items.GOLDEN_HOE)|| itemStack.isOf(Items.GOLDEN_SWORD))){
            entity.setStack(3, new ItemStack(Items.GOLD_INGOT,
                    entity.getStack(3).getCount() + 2));
        }
        //LEATHER
        if((entity.getStack(3).isOf(Items.LEATHER) || entity.getStack(3).isEmpty()) &&(itemStack.isOf(Items.LEATHER_CHESTPLATE) || itemStack.isOf(Items.LEATHER_LEGGINGS) || itemStack.isOf(Items.LEATHER_HELMET)|| itemStack.isOf(Items.LEATHER_BOOTS))){
            entity.setStack(3, new ItemStack(Items.LEATHER,
                    entity.getStack(3).getCount() + 4));
        }
        //STONE
        if((entity.getStack(3).isOf(Items.COBBLESTONE) || entity.getStack(3).isEmpty())  &&(itemStack.isOf(Items.STONE_SHOVEL))){
            entity.setStack(3, new ItemStack(Items.COBBLESTONE,
                    entity.getStack(3).getCount() + 1));
        }else if((entity.getStack(3).isOf(Items.LEATHER) || entity.getStack(3).isEmpty() ) &&( itemStack.isOf(Items.STONE_AXE) || itemStack.isOf(Items.STONE_PICKAXE) || itemStack.isOf(Items.STONE_HOE)|| itemStack.isOf(Items.STONE_SWORD))){
            entity.setStack(3, new ItemStack(Items.COBBLESTONE,
                    entity.getStack(3).getCount() + 2));
        }
        //WOOD
        if((entity.getStack(3).isOf(Items.OAK_PLANKS) || entity.getStack(3).isEmpty()) &&( itemStack.isOf(Items.WOODEN_SHOVEL))){
            entity.setStack(3, new ItemStack(Items.OAK_PLANKS,
                    entity.getStack(3).getCount() + 1));
        }else if((entity.getStack(3).isOf(Items.OAK_PLANKS) || entity.getStack(3).isEmpty())&&( itemStack.isOf(Items.WOODEN_AXE) || itemStack.isOf(Items.WOODEN_PICKAXE) || itemStack.isOf(Items.WOODEN_HOE)|| itemStack.isOf(Items.WOODEN_SWORD))){
            entity.setStack(3, new ItemStack(Items.OAK_PLANKS,
                    entity.getStack(3).getCount() + 2));
        }
        entity.removeStack(1, 1);
        entity.resetProgress();
    }

    private static boolean hasRecipe(RecyclerBlockEntity entity) {
        SimpleInventory inv = new SimpleInventory( entity.size());
        for (int i =0; i< entity.size();i++){
            inv.setStack(i, entity.getStack(i));
        }

        boolean hasItemInSecondSlot = !entity.getStack(1).isEmpty();
        boolean hasItemInThirdSlot = entity.getStack(2).getItem() == Items.FLINT;

        return hasItemInSecondSlot && hasItemInThirdSlot && canInsertAmountIntoOutputSlot(inv)&& canInsertItemIntoOutputSlot(inv, entity.getStack(3).getItem());
    }

    private static boolean hasRecipeAndFuel(RecyclerBlockEntity entity) {
        SimpleInventory inv = new SimpleInventory( entity.size());
        for (int i =0; i< entity.size();i++){
            inv.setStack(i, entity.getStack(i));
        }

        return entity.fuelTime>0 && hasRecipe(entity) && canInsertAmountIntoOutputSlot(inv)&& canInsertItemIntoOutputSlot(inv, entity.getStack(3).getItem());
    }

    private static boolean hasNotReachedStackLimit(RecyclerBlockEntity entity) {
        return entity.getStack(3).getCount() < entity.getStack(3).getMaxCount();
    }
    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory){
        return inventory.getStack(3).getMaxCount() > inventory.getStack(3).getCount();
    }
    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output){
        return inventory.getStack(3).getItem() == output || inventory.getStack(3).isEmpty();
    }
    private static boolean canInsertItemIntoOutputSlot(RecyclerBlockEntity entity){
        return entity.getStack(1).getItem() == entity.getStack(3).getItem() || entity.getStack(3).isEmpty();
    }


    // Geckolib

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
