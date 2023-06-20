package net.xalienated.xtech.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.xalienated.xtech.XTech;
import net.xalienated.xtech.block.ModBlocks;

public class ModItemGroup {
    public static ItemGroup BRONZE = Registry.register(Registries.ITEM_GROUP, new Identifier(XTech.MOD_ID,"bronze"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.bronze"))
                    .icon(()->new ItemStack(ModItems.BRONZE)).entries((displayContext, entries) -> {

                        entries.add(ModItems.BRONZE);
                        entries.add(ModItems.TIN);
                        entries.add(ModItems.RAW_TIN);
                        entries.add(ModItems.BARREL);
                        entries.add(ModItems.TRIGGER);
                        entries.add(ModItems.RECEIVER);
                        entries.add(ModBlocks.TIN_ORE);
                        entries.add(ModBlocks.GOLD_PILLAR);
                        entries.add(ModBlocks.CHISELED_GOLD);
                        entries.add(ModBlocks.BRONZE_BLOCK);
                        entries.add(ModBlocks.TIN_BLOCK);

                        entries.add(ModItems.BRONZE_BOOTS);
                        entries.add(ModItems.BRONZE_LEGGINGS);
                        entries.add(ModItems.BRONZE_CHESTPLATE);
                        entries.add(ModItems.BRONZE_HELMET);

                        entries.add(ModItems.SHOTGUN);
                        entries.add(ModItems.GLOCK);
                        entries.add(ModItems.RIFLE);
                        entries.add(ModItems.BAZOOKA);
                        entries.add(ModItems.TP_STAFF);
                        entries.add(XTech.KUNAI_ITEM);

                        entries.add(ModItems.NINE_MM);
                        entries.add(ModItems.RIFLE_BULLET);
                        entries.add(ModItems.SHOTGUN_SHELL);
                        entries.add(ModItems.MISIL);

                        entries.add(ModItems.BRONZE_SWORD);
                        entries.add(ModItems.BRONZE_THISWHAT);
                    }).build());
    public static void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ModItems.BRONZE);
            entries.add(ModItems.RAW_TIN);
        });
    }
}
