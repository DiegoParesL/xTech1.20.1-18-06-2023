package net.xalienated.xtech;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.xalienated.xtech.util.ModModelPredicateProvider;

public class XTechClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(XTech.BulletEntityType, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(XTech.BulletEntityTypeNineMM, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(XTech.BulletEntityTypeShell, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(XTech.MisilEntity, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(XTech.KunaiEntity, FlyingItemEntityRenderer::new);

        ModModelPredicateProvider.registerModModels();
    }
}
