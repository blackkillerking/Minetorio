package net.blackkillerking.minetorio.item;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.fluid.ModFluids;
import net.blackkillerking.minetorio.item.custom.*;
import net.blackkillerking.minetorio.item.custom.leather.AnimalHideItem;
import net.blackkillerking.minetorio.item.custom.leather.HideItem;
import net.blackkillerking.minetorio.item.custom.leather.SoakedHideItem;
import net.blackkillerking.minetorio.item.custom.leather.TreatedHideItem;
import net.blackkillerking.minetorio.item.custom.tools.*;
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
    public static final RegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget", () -> new Item(new Item.Properties()));

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
    public static final RegistryObject<Item> ZINC_NUGGET = ITEMS.register("zinc_nugget", () -> new Item(new Item.Properties()));

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
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(new Item.Properties()));

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

    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties()));
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

    /** NEW ITEMS, DON'T FORGET MODELS, TEXTURES, AND TOOL FUNCTIONALITY (DONE)*/

    public static final RegistryObject<Item> WOOD_BARK = ITEMS.register("wood_bark", () -> new WoodBarkItem(new Item.Properties()));
    public static final RegistryObject<Item> ANIMAL_HIDE = ITEMS.register("animal_hide", () -> new AnimalHideItem(new Item.Properties()));
    public static final RegistryObject<Item> HIDE = ITEMS.register("hide", () -> new HideItem(new Item.Properties()));
    public static final RegistryObject<Item> SOAKED_HIDE = ITEMS.register("soaked_hide", () -> new SoakedHideItem(new Item.Properties()));
    public static final RegistryObject<Item> DRIED_HIDE = ITEMS.register("dried_hide", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TREATED_HIDE = ITEMS.register("treated_hide", () -> new TreatedHideItem(new Item.Properties()));
    public static final RegistryObject<Item> OLIVE = ITEMS.register("olive", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OLIVE_OIL = ITEMS.register("olive_oil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLANT_FIBER = ITEMS.register("plant_fiber", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIRM_PLANT_FIBER = ITEMS.register("firm_plant_fiber", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STIFF_STICK = ITEMS.register("stiff_stick", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FLINT_FRAGMENT = ITEMS.register("flint_fragment", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BASALT_ROCK = ITEMS.register("basalt_rock", () -> new BasaltRockItem(new Item.Properties()));
    public static final RegistryObject<Item> SHARPENED_FLINT = ITEMS.register("sharpened_flint", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SHARPENED_BASALT = ITEMS.register("sharpened_basalt", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WOODEN_AXE_HEAD = ITEMS.register("wooden_axe_head", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLINT_PICKAXE = ITEMS.register("flint_pickaxe", () -> new PickaxeItem(ModToolTiers.FLINT, 1, 1, new Item.Properties().durability(128)));
    public static final RegistryObject<Item> FLINT_PICKAXE_HEAD = ITEMS.register("flint_pickaxe_head", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLINT_KNIFE = ITEMS.register("flint_knife", () -> new SwordItem(ModToolTiers.FLINT, 1, 1, new Item.Properties().durability(128)));

    public static final RegistryObject<Item> FLINT_AXE = ITEMS.register("flint_axe", () -> new AxeItem(ModToolTiers.FLINT, 1, 1, new Item.Properties().durability(128)));
    public static final RegistryObject<Item> FLINT_AXE_HEAD = ITEMS.register("flint_axe_head", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLINT_SHOVEL = ITEMS.register("flint_shovel", () -> new ShovelItem(ModToolTiers.FLINT, 1, 1, new Item.Properties().durability(128)));
    public static final RegistryObject<Item> FLINT_SHOVEL_HEAD = ITEMS.register("flint_shovel_head", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLINT_HOE = ITEMS.register("flint_hoe", () -> new HoeItem(ModToolTiers.FLINT, 1, 1, new Item.Properties().durability(128)));
    public static final RegistryObject<Item> FLINT_HOE_HEAD = ITEMS.register("flint_hoe_head", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BASALT_PICKAXE = ITEMS.register("basalt_pickaxe", () -> new PickaxeItem(Tiers.STONE, 1, 1, new Item.Properties().durability(192)));
    public static final RegistryObject<Item> BASALT_PICKAXE_HEAD = ITEMS.register("basalt_pickaxe_head", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BASALT_KNIFE = ITEMS.register("basalt_knife", () -> new SwordItem(Tiers.STONE, 1, 1, new Item.Properties().durability(192)));

    public static final RegistryObject<Item> BASALT_AXE = ITEMS.register("basalt_axe", () -> new AxeItem(Tiers.STONE, 1, 1, new Item.Properties().durability(192)));
    public static final RegistryObject<Item> BASALT_AXE_HEAD = ITEMS.register("basalt_axe_head", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BASALT_SHOVEL = ITEMS.register("basalt_shovel", () -> new ShovelItem(Tiers.STONE, 1, 1, new Item.Properties().durability(192)));
    public static final RegistryObject<Item> BASALT_SHOVEL_HEAD = ITEMS.register("basalt_shovel_head", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BASALT_HOE = ITEMS.register("basalt_hoe", () -> new HoeItem(Tiers.STONE, 1, 1, new Item.Properties().durability(192)));
    public static final RegistryObject<Item> BASALT_HOE_HEAD = ITEMS.register("basalt_hoe_head", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BASALT_HAMMER_BODY = ITEMS.register("basalt_hammer_body", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRACKED_BASALT_HAMMER = ITEMS.register("cracked_basalt_hammer", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BASALT_HAMMER = ITEMS.register("basalt_hammer", () -> new HammerItem(Tiers.STONE, 1, 1, 1, ModItems.CRACKED_BASALT_HAMMER.get(), new Item.Properties().durability(192)));


    public static final RegistryObject<Item> FLINT_BASALT_PICKAXE_BODY = ITEMS.register("flint_basalt_pickaxe_body", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FLINT_BASALT_AXE_BODY = ITEMS.register("flint_basalt_axe_body", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FLINT_BASALT_SHOVEL_HOE_BODY = ITEMS.register("flint_basalt_shovel_hoe_body", () -> new Item(new Item.Properties()));

//    public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(ModToolTiers.COPPER, 1, 1, new Item.Properties().durability(512)));
//    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(ModToolTiers.COPPER, 1, 1, new Item.Properties().durability(512)));
//    public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(ModToolTiers.COPPER, 1, 1, new Item.Properties().durability(512)));
//    public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(ModToolTiers.COPPER, 1, 1, new Item.Properties().durability(512)));
//    public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(ModToolTiers.COPPER, 1, 1, new Item.Properties().durability(512)));

    // END OF NEW ITEMS //

    public static final RegistryObject<Item> BLUNT_COPPER_HAMMER = ITEMS.register("blunt_copper_hammer", () -> new Item((new Item.Properties())));
    public static final RegistryObject<Item> COPPER_HAMMER = ITEMS.register("copper_hammer", () -> new HammerItem(ModToolTiers.COPPER, 1, 1, 1, ModItems.BLUNT_COPPER_HAMMER.get(), new Item.Properties().durability(512)));

    public static final RegistryObject<Item> BLUNT_COPPER_CLIPPER = ITEMS.register("blunt_copper_clipper", () -> new Item((new Item.Properties())));
    public static final RegistryObject<Item> COPPER_CLIPPER = ITEMS.register("copper_clipper", () -> new ClipperItem(ModItems.BLUNT_COPPER_CLIPPER.get(), new Item.Properties().durability(32)));

    public static final RegistryObject<Item> BLUNT_COPPER_ROLLER = ITEMS.register("blunt_copper_roller", () -> new Item((new Item.Properties())));
    public static final RegistryObject<Item> COPPER_ROLLER = ITEMS.register("copper_roller", () -> new RollerItem(ModItems.BLUNT_COPPER_ROLLER.get(), new Item.Properties().durability(32)));

    public static final RegistryObject<Item> BLUNT_COPPER_SOLID_CONE = ITEMS.register("blunt_copper_solid_cone", () -> new Item((new Item.Properties())));
    public static final RegistryObject<Item> COPPER_SOLID_CONE = ITEMS.register("copper_solid_cone", () -> new SolidConeItem(ModItems.BLUNT_COPPER_SOLID_CONE.get(), new Item.Properties().durability(32)));

    public static final RegistryObject<Item> BLUNT_COPPER_HOLLOW_CONE = ITEMS.register("blunt_copper_hollow_cone", () -> new Item((new Item.Properties())));
    public static final RegistryObject<Item> COPPER_HOLLOW_CONE = ITEMS.register("copper_hollow_cone", () -> new HollowConeItem(ModItems.BLUNT_COPPER_HOLLOW_CONE.get(), new Item.Properties().durability(32)));

    public static final RegistryObject<Item> HEATED_INGOT = ITEMS.register("heated_ingot", () -> new HeatedMetalItem(new Item.Properties()));

    public static final RegistryObject<Item> HEATED_SHEET = ITEMS.register("heated_sheet", () -> new HeatedMetalItem(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_BAR = ITEMS.register("heated_bar", () -> new HeatedMetalItem(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_STRIPE = ITEMS.register("heated_stripe", () -> new HeatedMetalItem(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_ROD = ITEMS.register("heated_rod", () -> new HeatedMetalItem(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_PANEL = ITEMS.register("heated_panel", () -> new HeatedMetalItem(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_WIRE = ITEMS.register("heated_wire", () -> new HeatedMetalItem(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_SCREWS = ITEMS.register("heated_screws", () -> new HeatedMetalItem(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_COLUMN = ITEMS.register("heated_column", () -> new HeatedMetalItem(new Item.Properties()));
    public static final RegistryObject<Item> HEATED_RING = ITEMS.register("heated_ring", () -> new HeatedMetalItem(new Item.Properties()));

    public static final RegistryObject<Item> CRUDE_OIL_BUCKET = ITEMS.register("crude_oil_bucket", () -> new BucketItem(ModFluids.SOURCE_CRUDE_OIL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> TANNIN_BUCKET = ITEMS.register("tannin_bucket", () -> new BucketItem(ModFluids.SOURCE_TANNIN, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<Item> OLIVE_SEEDS = ITEMS.register("olive_seeds", () -> new ItemNameBlockItem(ModBlocks.OLIVE_CROP.get(), new Item.Properties().craftRemainder(Items.BUCKET)));

//    public static final RegistryObject<Item> M0_BEATS_RECORD = ITEMS.register("mo_beats_record", () -> new RecordItem(4, ModSound.MO_BEATS, new Item.Properties().stacksTo(1), 3280));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
