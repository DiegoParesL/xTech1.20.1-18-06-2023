package net.xalienated.xtech.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;
import net.xalienated.xtech.block.ModBlocks;
import net.xalienated.xtech.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        /*
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.GOLD_PILLAR);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.TIN_ORE);
        */
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        /*
        itemModelGenerator.uploadArmor(new Identifier(ModItems.STEEL_CHESTPLATE.toString()),new Identifier("minecraft:models/armor/steel_layer_1"),new Identifier("xtech:trims/models/armor/spain_chestplate"));

        itemModelGenerator.register(ModItems.TIN, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_TIN, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_HELMET, Models.GENERATED);

         */
    }
}
