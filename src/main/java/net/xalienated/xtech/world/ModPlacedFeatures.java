package net.xalienated.xtech.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.xalienated.xtech.XTech;
import net.xalienated.xtech.block.ModBlocks;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> TIN_ORE_PLACED_KEY = registerKey("tin_ore_placed");
    public static void bootstrap (Registerable<PlacedFeature> context){
        var configuredFeatureRegistryEntryLookUp =context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        register(context, TIN_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookUp.getOrThrow(ModConfiguredFeatures.TIN_ORE_KEY),
                ModOrePlacement.modifiersWithCount(16,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-64), YOffset.fixed(80))));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name){
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(XTech.MOD_ID,name));

    }
    private static void register(Registerable<PlacedFeature> context,RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?,?>> configuration,
                                 List<PlacementModifier> modifiers){
        context.register(key, new PlacedFeature(configuration,List.copyOf(modifiers)));
    }
    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context,
                                                                                   RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?,?>> configuration,
                                                                                   PlacementModifier... modifiers){
        register(context,key,configuration,List.of(modifiers));

    }
}
