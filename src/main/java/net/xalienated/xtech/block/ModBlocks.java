package net.xalienated.xtech.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.xalienated.xtech.XTech;
import net.xalienated.xtech.block.custom.RecyclerBlock;
import net.xalienated.xtech.item.ModItemGroup;

public class ModBlocks {
    public static final Block TIN_ORE = registerBlock("tin_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool(),
                    UniformIntProvider.create(2, 6)));
    public static final Block GOLD_PILLAR = registerBlock("gold_pillar",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.GOLD_BLOCK).strength(4.0f).requiresTool()));
    public static final Block CHISELED_GOLD = registerBlock("chiseled_gold_block",
            new Block(FabricBlockSettings.copyOf(Blocks.GOLD_BLOCK).strength(4.0f).requiresTool()));

    public static final Block BRONZE_BLOCK = registerBlock("bronze_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(4.0f).requiresTool()));

    public static final Block TIN_BLOCK = registerBlock("tin_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(4.0f).requiresTool()));

    public static final Block STEEL_BLOCK = registerBlock("steel_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(6.0f).requiresTool()));

    public static final Block RECYCLER = Registry.register(Registries.BLOCK,new Identifier(XTech.MOD_ID,"recycler_block"),
            new RecyclerBlock(FabricBlockSettings.of().nonOpaque().strength(6.0f)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(XTech.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, new Identifier(XTech.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        return item;
    }
    public static void registerModBlocks(){
        XTech.LOGGER.info("Registering Mod Blocks for "+ XTech.MOD_ID);
    }
}
