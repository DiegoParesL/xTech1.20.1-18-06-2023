package net.xalienated.xtech.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xalienated.xtech.XTech;
import net.xalienated.xtech.item.custom.*;
import net.xalienated.xtech.world.entity.KunaiItem;


public class ModItems {
    public static final Item BRONZE = registerItem("bronze",
            new Item(new FabricItemSettings()));
    public static final Item TIN = registerItem("tin",
            new Item(new FabricItemSettings()));
    public static final Item STEEL = registerItem("steel",
            new Item(new FabricItemSettings()));
    public static final Item RAW_TIN = registerItem("raw_tin",
            new Item(new FabricItemSettings()));
    public static final Item BARREL = registerItem("barrel",
            new Item(new FabricItemSettings()));
    public static final Item TRIGGER = registerItem("trigger",
            new Item(new FabricItemSettings()));
    public static final Item RECEIVER = registerItem("receiver",
            new Item(new FabricItemSettings()));


    public static final Item SHOTGUN = registerItem("shotgun",
            new ShotgunItem(new FabricItemSettings().maxCount(1)));
    public static final Item GLOCK = registerItem("glock",
            new GlockItem(new FabricItemSettings().maxCount(1)));
    public static final Item RIFLE = registerItem("rifle",
            new RifleItem(new FabricItemSettings().maxCount(1)));
    public static final Item BAZOOKA = registerItem("bazooka",
            new BazookaItem(new FabricItemSettings().maxCount(1)));


    public static final Item RIFLE_BULLET = registerItem("bullet_sixteenmm",
            new Item(new FabricItemSettings().maxCount(12)));
    public static final Item NINE_MM = registerItem("ninemm",
            new Item(new FabricItemSettings().maxCount(24)));
    public static final Item SHOTGUN_SHELL = registerItem("shotgun_shell",
            new Item(new FabricItemSettings().maxCount(24)));
    public static final Item MISIL = registerItem("misil",
            new Item(new FabricItemSettings().maxCount(6)));

    //armors starts
    public static final Item BRONZE_BOOTS = registerItem("bronze_boots",
            new BronzeArmorItem(ModArmorMaterials.BRONZE, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    public static final Item BRONZE_LEGGINGS = registerItem("bronze_leggings",
            new BronzeArmorItem(ModArmorMaterials.BRONZE, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item BRONZE_CHESTPLATE = registerItem("bronze_chestplate",
            new BronzeArmorItem(ModArmorMaterials.BRONZE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item BRONZE_HELMET = registerItem("bronze_helmet",
            new BronzeArmorItem(ModArmorMaterials.BRONZE, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item STEEL_BOOTS = registerItem("steel_boots",
            new BronzeArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    public static final Item STEEL_LEGGINGS = registerItem("steel_leggings",
            new BronzeArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item STEEL_CHESTPLATE = registerItem("steel_chestplate",
            new BronzeArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item STEEL_HELMET = registerItem("steel_helmet",
            new BronzeArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.HELMET, new FabricItemSettings()));
    //armors ends

    public static final Item BRONZE_SWORD = registerItem("bronze_sword",
            new SwordItem(ToolMaterials.DIAMOND,1, -2.2f,new FabricItemSettings()));
    public static final Item BRONZE_THISWHAT = registerItem("bronze_thiswhat",
            new SwordItem(ToolMaterials.DIAMOND,0, -1.6f,new FabricItemSettings()));
    public static final Item TP_STAFF = registerItem("tp_staff",
            new TPItem(ToolMaterials.DIAMOND,0, -2f,new FabricItemSettings()));




    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(XTech.MOD_ID, name), item);
    }

    public static void registerModItems(){

        XTech.LOGGER.info("Registering Mod Items for " + XTech.MOD_ID);
    }
}
