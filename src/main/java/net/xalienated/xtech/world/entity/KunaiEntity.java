package net.xalienated.xtech.world.entity;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.xalienated.xtech.XTech;
import net.xalienated.xtech.world.entity.damage_types.MyDamageTypes;

public class KunaiEntity extends ThrownItemEntity {

    public KunaiEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    public KunaiEntity(World world, LivingEntity owner){
        super(XTech.KunaiEntity,owner,world);
        player =owner;
    }
    public KunaiEntity(World world, double x, double y, double z){
        super(XTech.KunaiEntity,x,y,z,world);
        updateTrackedPosition(x,y,z);

    }
    private LivingEntity player;

    private final RegistryKey<DamageType> BULLET_SHOOT = MyDamageTypes.BULLET;
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        World world = getWorld();
        if(player instanceof PlayerEntity playerEntity) {
            entity.damage(world.getDamageSources().create(BULLET_SHOOT, playerEntity), 5f);
        }
    }
    private int count =0;
    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {

        World world = getWorld();
        super.onBlockHit(blockHitResult);
        Vec3d vec3d = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
        this.setVelocity(vec3d);
        this.setPos(this.getX(), this.getY(), this.getZ());

        if(count ==0) {
            this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0f, 1.2f / (this.random.nextFloat() * 0.2f + 0.9f));
            count++;
        }else{
            if(count<60) {
                count++;

            }else{
                world.createExplosion(this,this.getX()+vec3d.x,this.getY()+vec3d.y,this.getZ()+vec3d.z,1.5f, World.ExplosionSourceType.TNT);
                count =0;
                discard();
            }
        }
        this.groundCollision = true;

    }
    protected void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        World world = this.getWorld();

        if (!world.isClient) { // checks if the world is client
            world.sendEntityStatus(this, (byte)3); // particle?

        }

    }

    @Override
    protected Item getDefaultItem() {
        return XTech.KUNAI_ITEM;
    }
}
