package net.xalienated.xtech.world.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.xalienated.xtech.XTech;
import net.xalienated.xtech.item.ModItems;
import net.xalienated.xtech.world.entity.damage_types.MyDamageTypes;

public class MisilEntity extends ThrownItemEntity {
    public MisilEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    public MisilEntity(World world, LivingEntity owner){
        super(XTech.MisilEntity,owner,world);
        player =owner;
    }
    public MisilEntity(World world, double x, double y, double z){
        super(XTech.MisilEntity,x,y,z,world);
        updateTrackedPosition(x,y,z);

    }
    private LivingEntity player;
    @Override
    protected Item getDefaultItem() {
        return Items.TNT;
    }
    private final RegistryKey<DamageType> BULLET_SHOOT = MyDamageTypes.BULLET;
    protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
        World world = getWorld();
        if(player instanceof PlayerEntity playerEntity) {
            entity.damage(world.getDamageSources().create(DamageTypes.EXPLOSION, playerEntity), 20f);
        }
    }
    protected void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        World world = this.getWorld();
        if (!world.isClient) { // checks if the world is client
            world.sendEntityStatus(this, (byte)3); // particle?
            world.createExplosion(this,this.getX(),this.getY(), this.getZ(),11f, World.ExplosionSourceType.TNT);
            this.kill(); // kills the projectile
        }

    }
}
