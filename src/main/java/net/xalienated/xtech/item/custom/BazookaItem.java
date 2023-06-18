package net.xalienated.xtech.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.xalienated.xtech.item.ModItems;
import net.xalienated.xtech.world.entity.MisilEntity;
import net.xalienated.xtech.world.entity.RifleBulletEntity;

public class BazookaItem extends Item {

    public BazookaItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            ItemStack itemStack = ModItems.BAZOOKA.getDefaultStack();
            MisilEntity misil = new MisilEntity(world,user);

            misil.setVelocity(user,user.getPitch(), user.getYaw(),0.0F,4f,0.7f);
            

            int slot = user.getInventory().getSlotWithStack(ModItems.MISIL.getDefaultStack());
            if (user.isCreative()){
                //Spawning the bullets
                world.spawnEntity(misil);
                user.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 4F, 1F);
                user.getItemCooldownManager().set(ModItems.BAZOOKA,1000);
                return TypedActionResult.consume(itemStack);
            }else if (user.getOffHandStack().isOf(ModItems.MISIL) && slot >0) {
                user.getOffHandStack().split(1);
                //Spawning the bullets
                world.spawnEntity(misil);
                user.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 4F, 1F);
                user.getItemCooldownManager().set(ModItems.BAZOOKA,1000);
                return TypedActionResult.consume(itemStack);
            }else if ( slot >0){
                user.getInventory().getStack(slot).split(1);
                //Spawning the bullets
                world.spawnEntity(misil);
                user.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 4F, 1F); // plays a sound for the entity hit only
                user.getItemCooldownManager().set(ModItems.BAZOOKA,1000);
                return TypedActionResult.consume(itemStack);
            }
            user.playSound(SoundEvents.BLOCK_DISPENSER_FAIL,2f,1f);
            return TypedActionResult.fail(itemStack);

        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    public UseAction getUseAction(ItemStack stack) {
        return stack.getItem().isFood() ? UseAction.EAT : UseAction.CROSSBOW;
    }
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {

    }
}
