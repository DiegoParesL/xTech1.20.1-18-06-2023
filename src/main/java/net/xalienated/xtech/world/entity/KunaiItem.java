package net.xalienated.xtech.world.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.xalienated.xtech.item.ModItems;

public class KunaiItem extends Item {

    public KunaiItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        KunaiEntity kunai = new KunaiEntity(world,user);

        kunai.setVelocity(user,user.getPitch(), user.getYaw(),0.0F,3.5f,0.2f);
        if(!world.isClient){
            itemStack.decrement(1);
            world.spawnEntity(kunai);
        }
        user.getItemCooldownManager().set(itemStack.getItem(),10);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }
}
