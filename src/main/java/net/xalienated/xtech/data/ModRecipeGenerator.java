package net.xalienated.xtech.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.xalienated.xtech.XTech;
import net.xalienated.xtech.block.ModBlocks;
import net.xalienated.xtech.item.ModItems;
import org.apache.commons.compress.compressors.lz77support.LZ77Compressor;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        offerSmelting(exporter, List.of(ModItems.RAW_TIN), RecipeCategory.MISC, ModItems.TIN, 0.7f,200,"tin");
        offerBlasting(exporter, List.of(ModItems.RAW_TIN), RecipeCategory.MISC, ModItems.TIN, 0.7f,100,"tin");
        //offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.TIN, RecipeCategory.DECORATIONS, ModBlocks.TIN_BLOCK);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BARREL)
                .pattern("II ")
                .pattern("INI")
                .pattern(" II")
                .input('I', Items.IRON_INGOT)
                .input('N', Items.NETHERITE_INGOT)
                .criterion(FabricRecipeProvider.hasItem(Items.NETHERITE_INGOT),FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.BARREL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.TRIGGER)
                .pattern("   ")
                .pattern("PPI")
                .pattern(" PP")
                .input('I', Items.IRON_INGOT)
                .input('P', ItemTags.PLANKS)
                .criterion(FabricRecipeProvider.hasItem(Items.NETHERITE_INGOT),FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.TRIGGER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RECEIVER)
                .pattern(" IP")
                .pattern("INI")
                .pattern(" I ")
                .input('N', Items.NETHERITE_INGOT)
                .input('I', Items.IRON_INGOT)
                .input('P', ItemTags.PLANKS)
                .criterion(FabricRecipeProvider.hasItem(Items.NETHERITE_INGOT),FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.RECEIVER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.SHOTGUN)
                .pattern("B  ")
                .pattern("BRP")
                .pattern(" T ")
                .input('R', ModItems.RECEIVER)
                .input('B', ModItems.BARREL)
                .input('T', ModItems.TRIGGER)
                .input('P', ItemTags.PLANKS)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BARREL),FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.SHOTGUN)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.GLOCK)
                .pattern("   ")
                .pattern("BR ")
                .pattern(" T ")
                .input('R', ModItems.RECEIVER)
                .input('B', ModItems.BARREL)
                .input('T', ModItems.TRIGGER)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BARREL),FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.GLOCK)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RIFLE)
                .pattern(" N ")
                .pattern("BRP")
                .pattern(" T ")
                .input('R', ModItems.RECEIVER)
                .input('B', ModItems.BARREL)
                .input('T', ModItems.TRIGGER)
                .input('P', ItemTags.PLANKS)
                .input('N', Items.NETHERITE_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BARREL),FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.RIFLE)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.BAZOOKA)
                .pattern("NNN")
                .pattern("BSS")
                .pattern("NTN")
                .input('N', Items.NETHERITE_INGOT)
                .input('B', ModItems.BARREL)
                .input('T', ModItems.TRIGGER)
                .input('S', Items.NETHERITE_SCRAP)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BARREL),FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.BAZOOKA)));
        offerStonecuttingRecipe(exporter,RecipeCategory.MISC, ModBlocks.GOLD_PILLAR,Blocks.GOLD_BLOCK);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.MISIL,2)
                .pattern("TTT")
                .pattern("TGT")
                .pattern("TTT")
                .input('T', Items.TNT)
                .input('G', Items.GUNPOWDER)
                .criterion(FabricRecipeProvider.hasItem(Items.TNT),FabricRecipeProvider.conditionsFromItem(Items.TNT))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.MISIL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TP_STAFF)
                .pattern("EEE")
                .pattern("EGE")
                .pattern("EGE")
                .input('E', Items.ENDER_PEARL)
                .input('G', Blocks.GOLD_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.ENDER_PEARL),FabricRecipeProvider.conditionsFromItem(Items.ENDER_PEARL))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.TP_STAFF)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.BRONZE_BOOTS)
                .pattern("   ")
                .pattern("B B")
                .pattern("B B")
                .input('B', ModItems.BRONZE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BRONZE),FabricRecipeProvider.conditionsFromItem(ModItems.BRONZE))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.BRONZE_BOOTS)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.BRONZE_LEGGINGS)
                .pattern("BBB")
                .pattern("B B")
                .pattern("B B")
                .input('B', ModItems.BRONZE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BRONZE),FabricRecipeProvider.conditionsFromItem(ModItems.BRONZE))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.BRONZE_LEGGINGS)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.BRONZE_HELMET)
                .pattern("   ")
                .pattern("BBB")
                .pattern("B B")
                .input('B', ModItems.BRONZE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BRONZE),FabricRecipeProvider.conditionsFromItem(ModItems.BRONZE))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.BRONZE_HELMET)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.BRONZE_CHESTPLATE)
                .pattern("B B")
                .pattern("BBB")
                .pattern("BBB")
                .input('B', ModItems.BRONZE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BRONZE),FabricRecipeProvider.conditionsFromItem(ModItems.BRONZE))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.BRONZE_CHESTPLATE)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.NINE_MM,2)
                .input(Items.GUNPOWDER)
                .input(Items.GUNPOWDER)
                .input(ModItems.BRONZE)
                .criterion(FabricRecipeProvider.hasItem(Items.GUNPOWDER),FabricRecipeProvider.conditionsFromItem(Items.GUNPOWDER))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.NINE_MM)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RIFLE_BULLET, 2)
                .input(Items.GUNPOWDER)
                .input(Items.GUNPOWDER)
                .input(ModItems.BRONZE)
                .input(ModItems.BRONZE)
                .criterion(FabricRecipeProvider.hasItem(Items.GUNPOWDER),FabricRecipeProvider.conditionsFromItem(Items.GUNPOWDER))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.RIFLE_BULLET)));


        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SHOTGUN_SHELL,2)
                .input(Items.GUNPOWDER)
                .input(ModItems.BRONZE)
                .input(ModItems.BRONZE)
                .input(ModItems.BRONZE)
                .criterion(FabricRecipeProvider.hasItem(Items.GUNPOWDER),FabricRecipeProvider.conditionsFromItem(Items.GUNPOWDER))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.SHOTGUN_SHELL)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, XTech.KUNAI_ITEM,2)
                .input(Items.GUNPOWDER)
                .input(Items.IRON_INGOT)
                .input(Items.TNT)
                .criterion(FabricRecipeProvider.hasItem(Items.GUNPOWDER),FabricRecipeProvider.conditionsFromItem(Items.GUNPOWDER))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(XTech.KUNAI_ITEM)));
    }
}
