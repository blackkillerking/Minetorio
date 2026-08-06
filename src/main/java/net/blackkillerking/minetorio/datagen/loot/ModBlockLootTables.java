package net.blackkillerking.minetorio.datagen.loot;

import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.block.crops.OliveCropBlock;
import net.blackkillerking.minetorio.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.RAW_TIN_BLOCK.get());
        this.dropSelf(ModBlocks.RAW_ZINC_BLOCK.get());
        this.dropSelf(ModBlocks.RAW_SILVER_BLOCK.get());

        this.dropSelf(ModBlocks.TIN_BLOCK.get());
        this.dropSelf(ModBlocks.ZINC_BLOCK.get());
        this.dropSelf(ModBlocks.SILVER_BLOCK.get());

        this.dropSelf(ModBlocks.MUD_LAYER_BLOCK.get());

        this.add(ModBlocks.TIN_ORE.get(), block -> createOreDrop(ModBlocks.TIN_ORE.get(), ModItems.RAW_TIN.get()));
        this.add(ModBlocks.ZINC_ORE.get(), block -> createOreDrop(ModBlocks.ZINC_ORE.get(), ModItems.RAW_ZINC.get()));
        this.add(ModBlocks.SILVER_ORE.get(), block -> createOreDrop(ModBlocks.SILVER_ORE.get(), ModItems.RAW_SILVER.get()));

        this.add(ModBlocks.FLINT_BLOCK.get(), block -> createSingleItemTable(ModItems.FLINT_FRAGMENT.get()));
        this.add(ModBlocks.BASALT_BLOCK.get(), block -> createSingleItemTable(ModItems.BASALT_ROCK.get()));

        this.add(ModBlocks.ANIMAL_HIDE.get(), block -> createSingleItemTable(ModItems.ANIMAL_HIDE.get()));
        this.add(ModBlocks.HIDE.get(), block -> createSingleItemTable(ModItems.HIDE.get()));
        this.add(ModBlocks.TREATED_HIDE.get(), block -> createSingleItemTable(ModItems.TREATED_HIDE.get()));
        this.add(ModBlocks.LEATHER.get(), block -> createSingleItemTable(Items.LEATHER));
        this.add(ModBlocks.MUD_BLOCK.get(), block -> createSingleItemTable(ModItems.MUD_BALLS.get()));

        this.dropSelf(ModBlocks.POLISHER.get());
        this.dropSelf(ModBlocks.BROKEN_POLISHER.get());

        this.dropSelf(ModBlocks.METAL_SHAPING_STATION.get());
        this.add(ModBlocks.PRIMITIVE_OVEN.get(), block -> createSingleItemTable(ModBlocks.MUD_BLOCK.get()));

        LootItemCondition.Builder lootitemcondition$builder1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.OLIVE_CROP.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(OliveCropBlock.AGE, 3));
        this.add(ModBlocks.OLIVE_CROP.get(), this.createCropDrops(ModBlocks.OLIVE_CROP.get(), ModItems.OLIVE.get(), ModItems.OLIVE_SEEDS.get(), lootitemcondition$builder1));

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
