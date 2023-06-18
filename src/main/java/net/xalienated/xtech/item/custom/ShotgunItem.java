package net.xalienated.xtech.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.xalienated.xtech.item.ModItems;
import net.xalienated.xtech.world.entity.RifleBulletEntity;
import net.xalienated.xtech.world.entity.ShotgunShellEntity;
import net.xalienated.xtech.world.entity.damage_types.MyDamageTypes;

public class ShotgunItem extends Item {

    public ShotgunItem(Settings settings) {
        super(settings);
    }
    private void shootFourBullets(World world, ShotgunShellEntity bullet1, ShotgunShellEntity bullet2, ShotgunShellEntity bullet3, ShotgunShellEntity bullet4){
        world.spawnEntity(bullet1);
        world.spawnEntity(bullet2);
        world.spawnEntity(bullet3);
        world.spawnEntity(bullet4);

    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            ItemStack itemStack = ModItems.SHOTGUN.getDefaultStack();
            ShotgunShellEntity bullet1 = new ShotgunShellEntity(world,user);
            ShotgunShellEntity bullet2 = new ShotgunShellEntity(world,user);
            ShotgunShellEntity bullet3 = new ShotgunShellEntity(world,user);
            ShotgunShellEntity bullet4 = new ShotgunShellEntity(world,user);

            bullet1.setVelocity(user,user.getPitch(), user.getYaw(),0.0F,3.5f,0.5f);
            bullet2.setVelocity(user,user.getPitch(), user.getYaw(),0.0F,3.5f,1f);
            bullet3.setVelocity(user,user.getPitch(), user.getYaw(),0.0F,3.5f,1.5f);
            bullet4.setVelocity(user,user.getPitch(), user.getYaw(),0.0F,3.5f,2f);


            int slot = user.getInventory().getSlotWithStack(ModItems.SHOTGUN_SHELL.getDefaultStack());
            if (user.isCreative()){
                //Spawning the bullets
                shootFourBullets(world,bullet1,bullet2,bullet3,bullet4);
                user.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2F, 1F);
                user.getItemCooldownManager().set(ModItems.SHOTGUN,15);
                return TypedActionResult.consume(itemStack);
            }else if (user.getOffHandStack().isOf(ModItems.SHOTGUN_SHELL) && slot >0) {
                user.getOffHandStack().split(1);
                //Spawning the bullets
                shootFourBullets(world,bullet1,bullet2,bullet3,bullet4);
                user.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2F, 1F);
                user.getItemCooldownManager().set(ModItems.SHOTGUN,15);
                return TypedActionResult.consume(itemStack);
            }else if ( slot >0){
                user.getInventory().getStack(slot).split(1);
                //Spawning the bullets
                shootFourBullets(world,bullet1,bullet2,bullet3,bullet4);
                user.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2F, 1F); // plays a sound for the entity hit only
                user.getItemCooldownManager().set(ModItems.SHOTGUN,15);
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
