package net.xalienated.xtech.screen;


import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.FabricScreenHandlerFactory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.xalienated.xtech.XTech;
import net.xalienated.xtech.block.ModBlocks;
import net.xalienated.xtech.block.entity.RecyclerBlockEntity;

public class ModScreenHandlers {

    public static ScreenHandlerType<RecyclerScreenHandler> RECYCLER_SCREEN_HANDLER=
            new ExtendedScreenHandlerType<>(RecyclerScreenHandler::new);

    public static void registerAllScreenHandlers(){
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(XTech.MOD_ID, "recycler_block"),RECYCLER_SCREEN_HANDLER);
    }

}
