package net.blackkillerking.minetorio.worldgen;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.registry.ModBlocks;
import net.blackkillerking.minetorio.block.crops.OliveBushBlock;
import net.blackkillerking.minetorio.registry.ModFeatures;
import net.blackkillerking.minetorio.worldgen.custom.config.OldTrunkConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> OLD_TRUNK = registerKey("old_trunk");
    public static final ResourceKey<ConfiguredFeature<?,?>> EARTH_TIN_ORE = registerKey("earth_tin_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> OLIVE_BUSH_PATCH = registerKey("olive_bush_patch");
    public static final ResourceKey<ConfiguredFeature<?,?>> FLINT_DEPOSIT = registerKey("flint_deposit");
    public static final ResourceKey<ConfiguredFeature<?,?>> BASALT_DEPOSIT = registerKey("basalt_deposit");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?,?>> context){
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> earth_tin_ore = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.TIN_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> basalt_rock = List.of(
                OreConfiguration.target(stoneReplaceables, Blocks.BASALT.defaultBlockState())
        );

        register(context, OLD_TRUNK,  ModFeatures.OLD_TRUNK.get(), new OldTrunkConfig(
                BlockStateProvider.simple(ModBlocks.OLD_LOG.get()),
                UniformInt.of(4,10)
        ));

        register(context, EARTH_TIN_ORE, Feature.ORE, new OreConfiguration(earth_tin_ore, 8));

        register(context, OLIVE_BUSH_PATCH, Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                6, 5, 2,
                PlacementUtils.onlyWhenEmpty(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.OLIVE_BUSH.get().defaultBlockState().setValue(OliveBushBlock.AGE, 3)))
                )
        ));

        register(context, FLINT_DEPOSIT, Feature.DISK, new DiskConfiguration(
                RuleBasedBlockStateProvider.simple(ModBlocks.FLINT_BLOCK.get()),
                BlockPredicate.matchesBlocks(List.of(Blocks.SAND, Blocks.GRAVEL, Blocks.RED_SAND, Blocks.DIRT, Blocks.CLAY)),
                UniformInt.of(4,6),
                1
        ));

        register(context, BASALT_DEPOSIT, Feature.ORE, new OreConfiguration(basalt_rock, 16));
    }

    public static ResourceKey<ConfiguredFeature<?,?>> registerKey (String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Minetorio.MOD_ID, name));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?,?>> context, ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
