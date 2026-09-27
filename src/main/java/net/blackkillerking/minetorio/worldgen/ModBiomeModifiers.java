package net.blackkillerking.minetorio.worldgen;

import net.blackkillerking.minetorio.Minetorio;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_OLD_TRUNK = registerKey("add_old_trunk");
    public static final ResourceKey<BiomeModifier> ADD_EARTH_TIN_ORE = registerKey("add_earth_tin_ore");
    public static final ResourceKey<BiomeModifier> ADD_OLIVE_PATCH = registerKey("add_olive_patch");
    public static final ResourceKey<BiomeModifier> ADD_FLINT_DEPOSIT = registerKey("add_flint_deposit");
    public static final ResourceKey<BiomeModifier> ADD_BASALT_DEPOSIT = registerKey("add_basalt_deposit");

    public static void bootstrap(BootstapContext context){
        HolderGetter<PlacedFeature> placedFeature = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biome = context.lookup(Registries.BIOME);

        register(context, ADD_OLD_TRUNK, BiomeTags.IS_FOREST, ModPlacedFeatures.OLD_TRUNK_PLACED, GenerationStep.Decoration.VEGETAL_DECORATION, biome, placedFeature);
        register(context, ADD_EARTH_TIN_ORE, BiomeTags.IS_OVERWORLD, ModPlacedFeatures.EARTH_TIN_ORE_PLACED, GenerationStep.Decoration.UNDERGROUND_ORES, biome, placedFeature);
        register(context, ADD_OLIVE_PATCH, Tags.Biomes.IS_PLAINS, ModPlacedFeatures.OLIVE_PATCH_PLACED, GenerationStep.Decoration.VEGETAL_DECORATION, biome, placedFeature);
        register(context, ADD_FLINT_DEPOSIT, BiomeTags.IS_RIVER, ModPlacedFeatures.FLINT_DEPOSIT_PLACED, GenerationStep.Decoration.LOCAL_MODIFICATIONS, biome, placedFeature);
        register(context, ADD_BASALT_DEPOSIT, BiomeTags.IS_OVERWORLD, ModPlacedFeatures.BASALT_DEPOSIT_PLACED, GenerationStep.Decoration.UNDERGROUND_ORES, biome, placedFeature);
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Minetorio.MOD_ID, name));
    }

    private static void register(BootstapContext context, ResourceKey<BiomeModifier> biomeKey, TagKey<Biome> tag, ResourceKey<PlacedFeature> placedKey, GenerationStep.Decoration step, HolderGetter<Biome> biome, HolderGetter<PlacedFeature> placedFeature){
        context.register(biomeKey, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biome.getOrThrow(tag),
                HolderSet.direct(placedFeature.getOrThrow(placedKey)),
                step
        ));
    }
}
