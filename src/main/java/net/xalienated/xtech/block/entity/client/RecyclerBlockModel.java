package net.xalienated.xtech.block.entity.client;

import net.minecraft.util.Identifier;
import net.xalienated.xtech.XTech;
import net.xalienated.xtech.block.entity.RecyclerBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class RecyclerBlockModel extends GeoModel<RecyclerBlockEntity> {
    @Override
    public Identifier getModelResource(RecyclerBlockEntity animatable) {
        return new Identifier(XTech.MOD_ID,"geo/recycler_block.geo.json");
    }

    @Override
    public Identifier getTextureResource(RecyclerBlockEntity animatable) {
        return new Identifier(XTech.MOD_ID,"textures/block/recycler_texture.png");
    }

    @Override
    public Identifier getAnimationResource(RecyclerBlockEntity animatable) {
        return new Identifier(XTech.MOD_ID,"animations/recycler_block.animation.json");
    }
}
