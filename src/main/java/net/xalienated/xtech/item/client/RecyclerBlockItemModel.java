package net.xalienated.xtech.item.client;

import net.minecraft.util.Identifier;
import net.xalienated.xtech.XTech;
import net.xalienated.xtech.item.custom.RecyclerBlockItem;
import software.bernie.geckolib.model.GeoModel;

public class RecyclerBlockItemModel extends GeoModel<RecyclerBlockItem> {

    @Override
    public Identifier getModelResource(RecyclerBlockItem animatable) {
        return new Identifier(XTech.MOD_ID,"geo/recycler_block.geo.json");
    }

    @Override
    public Identifier getTextureResource(RecyclerBlockItem animatable) {
        return new Identifier(XTech.MOD_ID,"textures/block/recycler_texture.png");
    }

    @Override
    public Identifier getAnimationResource(RecyclerBlockItem animatable) {
        return new Identifier(XTech.MOD_ID,"animations/recycler_block.animation.json");
    }
}
