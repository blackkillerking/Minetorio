package net.blackkillerking.minetorio.item;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.fluid.ModFluids;
import net.blackkillerking.minetorio.item.custom.AbstractHeatedMetal;
import net.blackkillerking.minetorio.item.custom.CopperClipperItem;
import net.blackkillerking.minetorio.item.custom.CopperHammerItem;
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

    public static final RegistryObject<Item> TIN_SHEET = ITEMS.register("tin_sheet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_BAR = ITEMS.register("tin_bar", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_STRIPE = ITEMS.register("tin_stripe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_ROD = ITEMS.register("tin_rod", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_PANEL = ITEMS.register("tin_panel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_WIRE = ITEMS.register("tin_wire", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_SCREWS = ITEMS.register("tin_screws", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_COLUMN = ITEMS.register("tin_column", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_RING = ITEMS.register("tin_ring", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_ZINC = ITEMS.register("raw_zinc", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZINC_INGOT = ITEMS.register("zinc_ingot", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ZINC_SHEET = ITEMS.register("zinc_sheet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZINC_BAR = ITEMS.register("zinc_bar", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZINC_STRIPE = ITEMS.register("zinc_stripe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZINC_ROD = ITEMS.register("zinc_rod", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZINC_PANEL = ITEMS.register("zinc_panel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZINC_WIRE = ITEMS.register("zinc_wire", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZINC_SCREWS = ITEMS.register("zinc_screws", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZINC_COLUMN = ITEMS.register("zinc_column", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZINC_RING = ITEMS.register("zinc_ring", () -> new Item(new Item.Properties()));

    // SILVER
    public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SILVER_SHEET = ITEMS.register("silver_sheet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_BAR = ITEMS.register("silver_bar", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_STRIPE = ITEMS.register("silver_stripe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_ROD = ITEMS.register("silver_rod", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_PANEL = ITEMS.register("silver_panel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_WIRE = ITEMS.register("silver_wire", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_SCREWS = ITEMS.register("silver_screws", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_COLUMN = ITEMS.register("silver_column", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_RING = ITEMS.register("silver_ring", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> IRON_SHEET = ITEMS.register("iron_sheet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_BAR = ITEMS.register("iron_bar", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_STRIPE = ITEMS.register("iron_stripe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_ROD = ITEMS.register("iron_rod", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_PANEL = ITEMS.register("iron_panel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_WIRE = ITEMS.register("iron_wire", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_SCREWS = ITEMS.register("iron_screws", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_COLUMN = ITEMS.register("iron_column", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_RING = ITEMS.register("iron_ring", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COPPER_SHEET = ITEMS.register("copper_sheet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_BAR = ITEMS.register("copper_bar", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_STRIPE = ITEMS.register("copper_stripe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_ROD = ITEMS.register("copper_rod", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_PANEL = ITEMS.register("copper_panel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_SCREWS = ITEMS.register("copper_screws", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_COLUMN = ITEMS.register("copper_column", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_RING = ITEMS.register("copper_ring", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GOLD_SHEET = ITEMS.register("gold_sheet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_BAR = ITEMS.register("gold_bar", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_STRIPE = ITEMS.register("gold_stripe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_ROD = ITEMS.register("gold_rod", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_PANEL = ITEMS.register("gold_panel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_WIRE = ITEMS.register("gold_wire", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_SCREWS = ITEMS.register("gold_screws", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_COLUMN = ITEMS.register("gold_column", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_RING = ITEMS.register("gold_ring", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COPPER_CLIPPER = ITEMS.register("copper_clipper", () -> new CopperClipperItem((new Item.Properties())));
    public static final RegistryObject<Item> BLUNT_COPPER_CLIPPER = ITEMS.register("blunt_copper_clipper", () -> new Item((new Item.Properties())));

    public static final RegistryObject<Item> COPPER_HAMMER = ITEMS.register("copper_hammer", () -> new CopperHammerItem(1, 1, ModToolTiers.COPPER, new Item.Properties().durability(512)));
    public static final RegistryObject<Item> BLUNT_COPPER_HAMMER = ITEMS.register("blunt_copper_hammer", () -> new Item((new Item.Properties())));

    public static final RegistryObject<Item> COPPER_ROLLER = ITEMS.register("copper_roller", () -> new CopperClipperItem((new Item.Properties())));
    public static final RegistryObject<Item> BLUNT_COPPER_ROLLER = ITEMS.register("blunt_copper_roller", () -> new Item((new Item.Properties())));

    public static final RegistryObject<Item> COPPER_SOLID_CONE = ITEMS.register("copper_solid_cone", () -> new CopperClipperItem((new Item.Properties())));
    public static final RegistryObject<Item> BLUNT_COPPER_SOLID_CONE = ITEMS.register("blunt_copper_solid_cone", () -> new Item((new Item.Properties())));

    public static final RegistryObject<Item> COPPER_HOLLOW_CONE = ITEMS.register("copper_hollow_cone", () -> new CopperClipperItem((new Item.Properties())));
    public static final RegistryObject<Item> BLUNT_COPPER_HOLLOW_CONE = ITEMS.register("blunt_copper_hollow_cone", () -> new Item((new Item.Properties())));

    public static final RegistryObject<Item> HEATED_INGOT = ITEMS.register("heated_ingot", () -> new AbstractHeatedMetal(new Item.Properties()));

    public static final RegistryObject<Item> HEATED_SHEET = ITEMS.register("heated_sheet", () -> new AbstractHeatedMetal(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_BAR = ITEMS.register("heated_bar", () -> new AbstractHeatedMetal(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_STRIPE = ITEMS.register("heated_stripe", () -> new AbstractHeatedMetal(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_ROD = ITEMS.register("heated_rod", () -> new AbstractHeatedMetal(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_PANEL = ITEMS.register("heated_panel", () -> new AbstractHeatedMetal(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_WIRE = ITEMS.register("heated_wire", () -> new AbstractHeatedMetal(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_SCREWS = ITEMS.register("heated_screws", () -> new AbstractHeatedMetal(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_COLUMN = ITEMS.register("heated_column", () -> new AbstractHeatedMetal(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_RING = ITEMS.register("heated_ring", () -> new AbstractHeatedMetal(new Item.Properties()));

    public static final RegistryObject<Item> CRUDE_OIL_BUCKET = ITEMS.register("crude_oil_bucket", () -> new BucketItem(ModFluids.SOURCE_CRUDE_OIL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

//    public static final RegistryObject<Item> M0_BEATS_RECORD = ITEMS.register("mo_beats_record", () -> new RecordItem(4, ModSound.MO_BEATS, new Item.Properties().stacksTo(1), 3280));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
