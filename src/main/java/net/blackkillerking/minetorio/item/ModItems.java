package net.blackkillerking.minetorio.item;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.fluid.ModFluids;
import net.blackkillerking.minetorio.item.custom.CopperClipperItem;
import net.blackkillerking.minetorio.item.custom.CopperHammerItem;
import net.blackkillerking.minetorio.item.custom.HeatedMetalItem;
import net.blackkillerking.minetorio.sound.ModSound;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Minetorio.MOD_ID);

    public static final RegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_ROD = ITEMS.register("tin_rod", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_SHEET = ITEMS.register("tin_sheet", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_ZINC = ITEMS.register("raw_zinc", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZINC_INGOT = ITEMS.register("zinc_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZINC_ROD = ITEMS.register("zinc_rod", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZINC_SHEET = ITEMS.register("zinc_sheet", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_ROD = ITEMS.register("silver_rod", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_SHEET = ITEMS.register("silver_sheet", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COPPER_CLIPPER = ITEMS.register("copper_clipper", () -> new CopperClipperItem((new Item.Properties())));
    public static final RegistryObject<Item> BLUNT_COPPER_CLIPPER = ITEMS.register("blunt_copper_clipper", () -> new Item((new Item.Properties())));

    public static final RegistryObject<Item> COPPER_HAMMER = ITEMS.register("copper_hammer", () -> new CopperHammerItem(1, 1, ModToolTiers.COPPER, new Item.Properties().durability(512)));
    public static final RegistryObject<Item> BLUNT_COPPER_HAMMER = ITEMS.register("blunt_copper_hammer", () -> new Item((new Item.Properties())));

    public static final RegistryObject<Item> HEATED_METAL = ITEMS.register("heated_metal", () -> new HeatedMetalItem(new Item.Properties()));

    public static final RegistryObject<Item> CRUDE_OIL_BUCKET = ITEMS.register("crude_oil_bucket", () -> new BucketItem(ModFluids.SOURCE_CRUDE_OIL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

//    public static final RegistryObject<Item> M0_BEATS_RECORD = ITEMS.register("mo_beats_record", () -> new RecordItem(4, ModSound.MO_BEATS, new Item.Properties().stacksTo(1), 3280));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
