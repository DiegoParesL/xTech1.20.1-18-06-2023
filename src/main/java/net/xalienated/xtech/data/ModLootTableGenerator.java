package net.xalienated.xtech.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.xalienated.xtech.block.ModBlocks;
import net.xalienated.xtech.item.ModItems;

public class ModLootTableGenerator extends FabricBlockLootTableProvider {
    public ModLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.CHISELED_GOLD);
        addDrop(ModBlocks.BRONZE_BLOCK);
        addDrop(ModBlocks.TIN_BLOCK);

        /*
        oreDrops(ModBlocks.TIN_ORE, ModItems.RAW_TIN);

         */
    }
}
