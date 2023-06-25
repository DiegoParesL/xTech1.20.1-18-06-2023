package net.xalienated.xtech.block.entity.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.xalienated.xtech.block.custom.RecyclerBlock;
import net.xalienated.xtech.block.entity.RecyclerBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RecyclerBlockRenderer extends GeoBlockRenderer<RecyclerBlockEntity> implements BlockEntityRenderer<RecyclerBlockEntity> {
    public RecyclerBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(new RecyclerBlockModel());
    }


}
