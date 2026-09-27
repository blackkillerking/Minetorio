package net.blackkillerking.minetorio.worldgen;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.utils.worldgen.OrePlacement;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> OLD_TRUNK_PLACED = registerKey("old_trunk_placed");
    public static final ResourceKey<PlacedFeature> EARTH_TIN_ORE_PLACED = registerKey("earth_tin_ore_placed");
    public static final ResourceKey<PlacedFeature> OLIVE_PATCH_PLACED = registerKey("olive_patch_placed");
    public static final ResourceKey<PlacedFeature> FLINT_DEPOSIT_PLACED = registerKey("flint_deposit_placed");
    public static final ResourceKey<PlacedFeature> BASALT_DEPOSIT_PLACED = registerKey("basalt_deposit_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context){
        HolderGetter<ConfiguredFeature<?,?>> configured_features = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, OLD_TRUNK_PLACED, configured_features.getOrThrow(ModConfiguredFeatures.OLD_TRUNK), List.of(
                RarityFilter.onAverageOnceEvery(20),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        ));

        register(context, EARTH_TIN_ORE_PLACED, configured_features.getOrThrow(ModConfiguredFeatures.EARTH_TIN_ORE),
                OrePlacement.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(50), VerticalAnchor.absolute(70))));

        register(context, OLIVE_PATCH_PLACED, configured_features.getOrThrow(ModConfiguredFeatures.OLIVE_BUSH_PATCH), List.of(
                RarityFilter.onAverageOnceEvery(4),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        ));

        register(context, FLINT_DEPOSIT_PLACED, configured_features.getOrThrow(ModConfiguredFeatures.FLINT_DEPOSIT), List.of(
                CountPlacement.of(15),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        ));
        register(context, BASALT_DEPOSIT_PLACED, configured_features.getOrThrow(ModConfiguredFeatures.BASALT_DEPOSIT),
                OrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(30), VerticalAnchor.absolute(70))));
    }

    public static ResourceKey<PlacedFeature> registerKey (String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Minetorio.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
