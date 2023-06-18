package net.xalienated.xtech;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.xalienated.xtech.data.ModLootTableGenerator;
import net.xalienated.xtech.data.ModModelProvider;
import net.xalienated.xtech.data.ModRecipeGenerator;
import net.xalienated.xtech.data.ModWorldGenerator;
import net.xalienated.xtech.world.ModConfiguredFeatures;
import net.xalienated.xtech.world.ModPlacedFeatures;

public class XTechModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		//pack.addProvider(ModLootTableGenerator::new);
		pack.addProvider(ModRecipeGenerator::new);
		//pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModWorldGenerator::new);
	}
	@Override
	public void buildRegistry(RegistryBuilder registryBuilder){

		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);

	}
}
