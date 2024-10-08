package net.xalienated.xtech.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTrimRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.SmithingTrimRecipe;
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
        //tin
        offerSmelting(exporter, List.of(ModItems.RAW_TIN), RecipeCategory.MISC, ModItems.TIN, 0.7f,200,"tin");
        offerBlasting(exporter, List.of(ModItems.RAW_TIN), RecipeCategory.MISC, ModItems.TIN, 0.7f,100,"tin");
        //offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.TIN, RecipeCategory.DECORATIONS, ModBlocks.TIN_BLOCK);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TIN_BLOCK)
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .input('T', ModItems.TIN)
                .criterion(FabricRecipeProvider.hasItem(ModItems.TIN),FabricRecipeProvider.conditionsFromItem(ModItems.TIN))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModBlocks.TIN_BLOCK)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.TIN,9)
                .input(ModBlocks.TIN_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(ModItems.TIN),FabricRecipeProvider.conditionsFromItem(ModItems.TIN))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.TIN)));

        //bronze
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BRONZE_BLOCK)
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .input('B', ModItems.BRONZE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BRONZE),FabricRecipeProvider.conditionsFromItem(ModItems.BRONZE))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModBlocks.BRONZE_BLOCK)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BRONZE,9)
                .input(ModBlocks.BRONZE_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BRONZE),FabricRecipeProvider.conditionsFromItem(ModItems.BRONZE))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.BRONZE)+"from_block"));



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
        //steel
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STEEL_BLOCK)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .input('S', ModItems.STEEL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.STEEL),FabricRecipeProvider.conditionsFromItem(ModItems.STEEL))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModBlocks.STEEL_BLOCK)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.STEEL,2)
                .input(Items.IRON_INGOT)
                .input(Items.IRON_INGOT)
                .input(Items.IRON_INGOT)
                .input(Items.IRON_INGOT)
                .input(Items.COAL)
                .criterion(FabricRecipeProvider.hasItem(Items.COAL),FabricRecipeProvider.conditionsFromItem(Items.COAL))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.STEEL)+"from_coal"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.STEEL,9)
                .input(ModBlocks.STEEL_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(ModItems.STEEL),FabricRecipeProvider.conditionsFromItem(ModItems.STEEL))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.STEEL)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STEEL_BOOTS)
                .pattern("   ")
                .pattern("S S")
                .pattern("S S")
                .input('S', ModItems.STEEL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.STEEL),FabricRecipeProvider.conditionsFromItem(ModItems.STEEL))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.STEEL_BOOTS)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STEEL_LEGGINGS)
                .pattern("SSS")
                .pattern("S S")
                .pattern("S S")
                .input('S', ModItems.STEEL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.STEEL),FabricRecipeProvider.conditionsFromItem(ModItems.STEEL))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.STEEL_LEGGINGS)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STEEL_HELMET)
                .pattern("   ")
                .pattern("SSS")
                .pattern("S S")
                .input('S', ModItems.STEEL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.STEEL),FabricRecipeProvider.conditionsFromItem(ModItems.STEEL))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.STEEL_HELMET)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STEEL_CHESTPLATE)
                .pattern("S S")
                .pattern("SSS")
                .pattern("SSS")
                .input('S', ModItems.STEEL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.STEEL),FabricRecipeProvider.conditionsFromItem(ModItems.STEEL))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.STEEL_CHESTPLATE)));

        SmithingTrimRecipeJsonBuilder.create(Ingredient.ofStacks(ModItems.SPAIN_TRIM.getDefaultStack()),Ingredient.ofStacks(ModItems.STEEL_CHESTPLATE.getDefaultStack()),Ingredient.ofStacks(ModItems.STEEL.getDefaultStack()),RecipeCategory.MISC);
        

        //gun parts
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
        //Guns
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
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TP_STAFF)
                .pattern("EEE")
                .pattern("EGE")
                .pattern("EGE")
                .input('E', Items.ENDER_PEARL)
                .input('G', Blocks.GOLD_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.ENDER_PEARL),FabricRecipeProvider.conditionsFromItem(Items.ENDER_PEARL))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.TP_STAFF)));
        //ammo or projectiles
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.MISIL,2)
                .pattern("TTT")
                .pattern("TGT")
                .pattern("TTT")
                .input('T', Items.TNT)
                .input('G', Items.GUNPOWDER)
                .criterion(FabricRecipeProvider.hasItem(Items.TNT),FabricRecipeProvider.conditionsFromItem(Items.TNT))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.MISIL)));

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
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SPAIN_TRIM,4)
                .pattern("RRR")
                .pattern("GGG")
                .pattern("RRR")
                .input('R', Items.REDSTONE)
                .input('G', Items.GOLD_INGOT)
                .criterion(FabricRecipeProvider.hasItem(Items.GOLD_INGOT),FabricRecipeProvider.conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.SPAIN_TRIM)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.DEMON_TRIM,4)
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .input('C', Items.CHORUS_FRUIT)
                .criterion(FabricRecipeProvider.hasItem(Items.CHORUS_FRUIT),FabricRecipeProvider.conditionsFromItem(Items.CHORUS_FRUIT))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.DEMON_TRIM)));
    }
}
