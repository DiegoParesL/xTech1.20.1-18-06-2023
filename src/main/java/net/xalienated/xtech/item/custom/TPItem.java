package net.xalienated.xtech.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.xalienated.xtech.item.ModItems;

public class TPItem extends SwordItem {
    public TPItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            ItemStack itemStack = ModItems.TP_STAFF.getDefaultStack();
            int distance = 10;
            float f1 = MathHelper.cos(-user.getHeadYaw() * (float)Math.PI/180 - (float)Math.PI);
            float f2 = MathHelper.sin(-user.getHeadYaw() * (float)Math.PI/180 - (float)Math.PI);
            float f3 = -MathHelper.cos(-user.getPitch()* (float)Math.PI/180);
            float f4 = MathHelper.sin(-user.getPitch() * (float)Math.PI/180);
            Vec3d vector = new Vec3d(distance*f2*f3, distance*f4, distance*f1*f3);
            user.move(MovementType.SELF,vector);
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING,20, 5),user);

            return TypedActionResult.consume(itemStack);

        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    public UseAction getUseAction(ItemStack stack) {
        return stack.getItem().isFood() ? UseAction.EAT : UseAction.CROSSBOW;
    }
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {

    }
}
