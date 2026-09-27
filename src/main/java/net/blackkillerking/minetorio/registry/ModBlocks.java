package net.blackkillerking.minetorio.registry;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.MudLayerBlock;
import net.blackkillerking.minetorio.block.OldLogBlock;
import net.blackkillerking.minetorio.block.PolisherBlock;
import net.blackkillerking.minetorio.block.blockentities.KilnControllerBlock;
import net.blackkillerking.minetorio.block.blockentities.MetalShapingStationBlock;
import net.blackkillerking.minetorio.block.blockentities.PrimitiveOvenBlock;
import net.blackkillerking.minetorio.block.crops.OliveBushBlock;
import net.blackkillerking.minetorio.block.leather.AnimalHideBlock;
import net.blackkillerking.minetorio.block.leather.HideBlock;
import net.blackkillerking.minetorio.block.leather.LeatherBlock;
import net.blackkillerking.minetorio.block.leather.TreatedHideBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Minetorio.MOD_ID);


    public static final RegistryObject<Block> TIN_ORE = registerBlock(
            "tin_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> ZINC_ORE = registerBlock(
            "zinc_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)));
    public static final RegistryObject<Block> SILVER_ORE = registerBlock(
            "silver_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)));

    public static final RegistryObject<Block> RAW_TIN_BLOCK = registerBlock(
            "raw_tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));
    public static final RegistryObject<Block> RAW_ZINC_BLOCK = registerBlock(
            "raw_zinc_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = registerBlock(
            "raw_silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));

    public static final RegistryObject<Block> TIN_BLOCK = registerBlock(
            "tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ZINC_BLOCK = registerBlock(
            "zinc_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> SILVER_BLOCK = registerBlock(
            "silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> STEEL_BLOCK = registerBlock(
            "steel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));

    public static final RegistryObject<Block> ASH_BLOCK = registerBlock(
            "ash_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BASALT).noLootTable()));
    public static final RegistryObject<Block> CHARCOAL_BLOCK = registerBlock(
            "charcoal_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BASALT)));
    public static final RegistryObject<Block> COKE_BLOCK = registerBlock(
            "coke_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BASALT)));

    public static final RegistryObject<Block> FLINT_BLOCK = registerBlock(
            "flint_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> BASALT_BLOCK = registerBlock(
            "basalt_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> OLD_LOG = registerBlock(
            "old_log", () -> new OldLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).noLootTable()));
    public static final RegistryObject<Block> ANIMAL_HIDE = registerBlock(
            "animal_hide_block", () -> new AnimalHideBlock(BlockBehaviour.Properties.of().strength(1,0).instabreak()));
    public static final RegistryObject<Block> HIDE = registerBlock(
            "hide_block", () -> new HideBlock(BlockBehaviour.Properties.of().strength(1,0).instabreak()));
    public static final RegistryObject<Block> TREATED_HIDE = registerBlock(
            "treated_hide_block", () -> new TreatedHideBlock(BlockBehaviour.Properties.of().strength(1,0).instabreak()));
    public static final RegistryObject<Block> LEATHER = registerBlock(
            "leather_block", () -> new LeatherBlock(BlockBehaviour.Properties.of().strength(1,0).instabreak()));

    public static final RegistryObject<Block> IN_PROGRESS = registerBlock(
            "in_progress", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noLootTable()));

    public static final RegistryObject<Block> POLISHER = registerBlock(
            "polisher", () -> new PolisherBlock(BlockBehaviour.Properties.copy(Blocks.SMITHING_TABLE)));
    public static final RegistryObject<Block> BROKEN_POLISHER = registerBlock(
            "broken_polisher", () -> new PolisherBlock(BlockBehaviour.Properties.copy(Blocks.SMITHING_TABLE)));

    public static final RegistryObject<Block> METAL_SHAPING_STATION = registerBlock(
            "metal_shaping_station", () -> new MetalShapingStationBlock(BlockBehaviour.Properties.copy(Blocks.ANVIL).noOcclusion()));
    public static final RegistryObject<Block> PRIMITIVE_OVEN = registerBlock(
            "primitive_oven", () -> new PrimitiveOvenBlock(BlockBehaviour.Properties.copy(Blocks.ANVIL).noOcclusion()));
    public static final RegistryObject<Block> KILN_CONTROLLER = registerBlock(
            "kiln_controller", () -> new KilnControllerBlock(BlockBehaviour.Properties.copy(Blocks.ANVIL).noOcclusion()));

    public static final RegistryObject<LiquidBlock> CRUDE_OIL_BLOCK = BLOCKS.register(
            "crude_oil_block", () -> new LiquidBlock(ModFluids.SOURCE_CRUDE_OIL, BlockBehaviour.Properties.copy(Blocks.LAVA).noLootTable()));
    public static final RegistryObject<LiquidBlock> TANNIN_BLOCK = BLOCKS.register(
            "tannin_block", () -> new LiquidBlock(ModFluids.SOURCE_TANNIN, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));

    public static final RegistryObject<Block> OLIVE_BUSH = BLOCKS.register(
            "olive_bush", () -> new OliveBushBlock(BlockBehaviour.Properties.copy(Blocks.BEETROOTS).noOcclusion().noCollission()));

    public static final RegistryObject<Block> BRICK_DOOR = registerBlock(
            "brick_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR), BlockSetType.IRON));

    public static final RegistryObject<Block> MUD_LAYER_BLOCK = registerBlock(
            "mud_layer_block", () -> new MudLayerBlock(BlockBehaviour.Properties.of().instabreak().friction(0.4f).mapColor(MapColor.COLOR_BROWN)));

    private static <T extends Block> RegistryObject<T> registerBlock (String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static  <T extends Block> RegistryObject<Item> registerBlockItem (String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
