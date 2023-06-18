package net.xalienated.xtech.world.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.xalienated.xtech.XTech;
import net.xalienated.xtech.item.ModItems;
import net.xalienated.xtech.world.entity.damage_types.MyDamageTypes;

public class RifleBulletEntity extends ThrownItemEntity {
    public RifleBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    public RifleBulletEntity(World world, LivingEntity owner){
        super(XTech.BulletEntityType,owner,world);
        player =owner;
    }
    public RifleBulletEntity(World world, double x, double y, double z){
        super(XTech.BulletEntityType,x,y,z,world);
        updateTrackedPosition(x,y,z);

    }
    private LivingEntity player;
    @Override
    protected Item getDefaultItem() {
        return ModItems.RIFLE_BULLET;
    }
    private final RegistryKey<DamageType> BULLET_SHOOT = MyDamageTypes.BULLET;
    protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
        World world = getWorld();
        if(player instanceof PlayerEntity playerEntity) {
            double x1 = playerEntity.getX();
            double y1 = playerEntity.getY();
            double z1 = playerEntity.getZ();
            double x2 = entity.getX();
            double y2 = entity.getY();
            double z2 = entity.getZ();
            double distancex_z = Math.abs((Math.sqrt(Math.abs((x1 * x1) + (z1 * z1))) - Math.sqrt(Math.abs((x2 * x2) + (z2 * z2)))));
            double distancey =Math.abs(y1-y2);
            float formula = (float) (30f - (distancex_z + distancey)/3);

            float formula2 = (float) (30f - distancex_z/3);
            if (formula > 1 && formula2>1) {
                if( y1>y2) {
                    entity.damage(world.getDamageSources().create(BULLET_SHOOT, playerEntity), formula);
                } else{
                    entity.damage(world.getDamageSources().create(BULLET_SHOOT, playerEntity), formula2);
                }
            } else {
                entity.damage(world.getDamageSources().create(BULLET_SHOOT, playerEntity), 1f); // deals damage
            }
        }
    }
    protected void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        World world = this.getWorld();

        if (!world.isClient) { // checks if the world is client
            world.sendEntityStatus(this, (byte)3); // particle?
            this.kill(); // kills the projectile
        }

    }
}
