package net.xalienated.xtech.world;

import net.minecraft.client.render.DimensionEffects;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.xalienated.xtech.XTech;
import net.xalienated.xtech.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> TIN_ORE_KEY = registerKey("tin_ore");
    public static void bootstrap (Registerable<ConfiguredFeature<?,?>> context){
        RuleTest stoneReplacables =  new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        List<OreFeatureConfig.Target> overworldTinOres = List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.TIN_ORE.getDefaultState()));
        register(context, TIN_ORE_KEY,Feature.ORE, new OreFeatureConfig(overworldTinOres,12));
    }

    public static RegistryKey<ConfiguredFeature<?,?>> registerKey(String name){
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(XTech.MOD_ID,name));

    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?,?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?,?>> key, F feature, FC configuration){
    context.register(key, new ConfiguredFeature<>(feature,configuration));
    }
}
