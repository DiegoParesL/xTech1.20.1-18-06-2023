package net.xalienated.xtech.item.client;

import net.xalienated.xtech.item.custom.RecyclerBlockItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class RecyclerItemRenderer extends GeoItemRenderer<RecyclerBlockItem> {
    public RecyclerItemRenderer() {
        super(new RecyclerBlockItemModel());
    }
}
