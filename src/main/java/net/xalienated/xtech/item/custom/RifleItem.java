package net.xalienated.xtech.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.SpyglassItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.xalienated.xtech.XTech;
import net.xalienated.xtech.item.ModItems;
import net.xalienated.xtech.world.entity.NineMMBulletEntity;
import net.xalienated.xtech.world.entity.RifleBulletEntity;

public class RifleItem extends SpyglassItem {
    public static final int MAX_USE_TIME = 1200;
    public static final float field_30922 = 0.1f;
    public RifleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            user.addStatusEffect(new StatusEffectInstance(XTech.ZOOM,200000000, 5),user);
            return ItemUsage.consumeHeldItem(world, user, hand);
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPYGLASS;
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        ItemStack itemStack = ModItems.RIFLE.getDefaultStack();
        RifleBulletEntity rifle_bullet = new RifleBulletEntity(world, user);
        user.removeStatusEffect(XTech.ZOOM);
        rifle_bullet.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 4f, 0.2f);

        if (user instanceof PlayerEntity player) {
            int slot = player.getInventory().getSlotWithStack(ModItems.RIFLE_BULLET.getDefaultStack());
            if (player.isCreative()) {
                //Spawning the bullets
                world.spawnEntity(rifle_bullet);
                player.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2F, 1F);
                player.getItemCooldownManager().set(ModItems.RIFLE, 20);
            } else if (player.getOffHandStack().isOf(ModItems.RIFLE_BULLET) && slot > 0) {
                player.getOffHandStack().split(1);
                //Spawning the bullets
                world.spawnEntity(rifle_bullet);
                player.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2F, 1F);
                player.getItemCooldownManager().set(ModItems.RIFLE, 20);
            } else if (slot > 0) {
                player.getInventory().getStack(slot).split(1);
                //Spawning the bullets
                world.spawnEntity(rifle_bullet);
                player.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2F, 1F); // plays a sound for the entity hit only
                player.getItemCooldownManager().set(ModItems.RIFLE, 20);
            }
            player.playSound(SoundEvents.BLOCK_DISPENSER_FAIL, 2f, 1f);
            player.incrementStat(Stats.USED.getOrCreateStat(this));
        }
    }

}
