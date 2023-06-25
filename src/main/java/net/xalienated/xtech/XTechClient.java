package net.xalienated.xtech;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.xalienated.xtech.block.ModBlocks;
import net.xalienated.xtech.block.entity.ModBlockEntities;
import net.xalienated.xtech.block.entity.client.RecyclerBlockRenderer;
import net.xalienated.xtech.screen.ModScreenHandlers;
import net.xalienated.xtech.screen.RecyclerScreen;
import net.xalienated.xtech.util.ModModelPredicateProvider;


public class XTechClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(XTech.BulletEntityType, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(XTech.BulletEntityTypeNineMM, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(XTech.BulletEntityTypeShell, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(XTech.MisilEntity, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(XTech.KunaiEntity, FlyingItemEntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RECYCLER, RenderLayer.getCutout());

        ModModelPredicateProvider.registerModModels();

        BlockEntityRendererFactories.register(ModBlockEntities.RECYCLER, RecyclerBlockRenderer::new);

        HandledScreens.register(ModScreenHandlers.RECYCLER_SCREEN_HANDLER, RecyclerScreen::new);

    }
}
