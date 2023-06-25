package net.xalienated.xtech.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xalienated.xtech.XTech;
import net.xalienated.xtech.block.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<RecyclerBlockEntity> RECYCLER;

    public static void registerBlockEntities(){
        RECYCLER = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(XTech.MOD_ID,"recycler_block"),
                FabricBlockEntityTypeBuilder.create(RecyclerBlockEntity::new,
                        ModBlocks.RECYCLER).build(null));
    }
}
