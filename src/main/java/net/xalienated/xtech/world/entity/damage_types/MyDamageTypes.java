package net.xalienated.xtech.world.entity.damage_types;

import net.minecraft.entity.damage.*;
import net.minecraft.registry.*;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import net.xalienated.xtech.XTech;

public class MyDamageTypes {
    public static final RegistryKey<DamageType> BULLET = register("damage_bullet");
    private static RegistryKey<DamageType> register (String name){
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(XTech.MOD_ID, name));
    }
    public static void bootstrap(Registerable<DamageType> context) { context.register(BULLET, new DamageType("bullet", 30.0F)); }
}
