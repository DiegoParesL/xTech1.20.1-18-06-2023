package net.xalienated.xtech.util;

import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.mixin.object.builder.client.ModelPredicateProviderRegistrySpecificAccessor;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.xalienated.xtech.item.ModItems;

public class ModModelPredicateProvider {
    public static void registerModModels(){
        registerRifle(ModItems.RIFLE);
        registerElytra(ModItems.NETHERITE_ELYTRA);
    }
    public static void registerRifle(Item rifle) {
        ModelPredicateProviderRegistrySpecificAccessor.callRegister(rifle,new Identifier("pulling"), ((stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem()== stack? 1.0f:0.0f));
    }
    public static void registerElytra(Item elytra) {
        ModelPredicateProviderRegistrySpecificAccessor.callRegister(elytra,new Identifier("broken"), ((stack, world, entity, seed) -> entity != null && stack.getDamage()>0? 0.0f:1.0f));
    }
}
