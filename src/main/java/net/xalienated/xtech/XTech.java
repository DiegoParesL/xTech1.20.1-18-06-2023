package net.xalienated.xtech;

import net.fabricmc.api.ModInitializer;


import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.xalienated.xtech.block.ModBlocks;
import net.xalienated.xtech.block.entity.ModBlockEntities;
import net.xalienated.xtech.block.entity.RecyclerBlockEntity;
import net.xalienated.xtech.item.ModItemGroup;
import net.xalienated.xtech.item.ModItems;
import net.xalienated.xtech.screen.ModScreenHandlers;
import net.xalienated.xtech.screen.RecyclerScreen;
import net.xalienated.xtech.screen.RecyclerScreenHandler;
import net.xalienated.xtech.util.ZoomStatusEffect;
import net.xalienated.xtech.world.entity.*;
import net.xalienated.xtech.world.entity.damage_types.MyDamageTypes;
import net.xalienated.xtech.world.gen.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

import java.util.UUID;

public class XTech implements ModInitializer {
	public static final String MOD_ID = "xtech";
    public static final Logger LOGGER = LoggerFactory.getLogger("xtech");
	public static final EntityType<RifleBulletEntity> BulletEntityType = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MOD_ID, "bullet"),
			FabricEntityTypeBuilder.<RifleBulletEntity>create(SpawnGroup.MISC, RifleBulletEntity::new)
					.dimensions(EntityDimensions.fixed(0.5F, 0.5F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(70).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);
	public static final EntityType<NineMMBulletEntity> BulletEntityTypeNineMM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MOD_ID, "ninemm"),
			FabricEntityTypeBuilder.<NineMMBulletEntity>create(SpawnGroup.MISC, NineMMBulletEntity::new)
					.dimensions(EntityDimensions.fixed(0.5F, 0.5F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(70).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);
	public static final EntityType<ShotgunShellEntity> BulletEntityTypeShell = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MOD_ID, "shell"),
			FabricEntityTypeBuilder.<ShotgunShellEntity>create(SpawnGroup.MISC, ShotgunShellEntity::new)
					.dimensions(EntityDimensions.fixed(0.5F, 0.5F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(70).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);
	public static final EntityType<MisilEntity> MisilEntity = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MOD_ID, "misil"),
			FabricEntityTypeBuilder.<MisilEntity>create(SpawnGroup.MISC, MisilEntity::new)
					.dimensions(EntityDimensions.fixed(0.7F, 0.7F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(70).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);

	public static final EntityType<KunaiEntity> KunaiEntity = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MOD_ID, "kunai"),
			FabricEntityTypeBuilder.<KunaiEntity>create(SpawnGroup.MISC, KunaiEntity::new)
					.dimensions(EntityDimensions.fixed(0.3F, 0.3F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(70).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);
	public static final Item KUNAI_ITEM = new KunaiItem(new Item.Settings());

	public static final StatusEffect ZOOM = new ZoomStatusEffect();


	@Override
	public void onInitialize() {
		ModItemGroup.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		Registry.register(Registries.ITEM, new Identifier(MOD_ID,"kunai"),KUNAI_ITEM);
		ModWorldGeneration.generateModWorldGen();


		Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID,"zoom"), ZOOM.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,"48daff98-0f54-11ee-be56-0242ac120002", -0.15f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
		GeckoLib.initialize();


		ModScreenHandlers.registerAllScreenHandlers();
		ModBlockEntities.registerBlockEntities();
	}
}