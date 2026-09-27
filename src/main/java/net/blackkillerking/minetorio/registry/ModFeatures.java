package net.blackkillerking.minetorio.registry;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.worldgen.custom.config.OldTrunkConfig;
import net.blackkillerking.minetorio.worldgen.custom.features.OldTrunkFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, Minetorio.MOD_ID);

    public static final RegistryObject<Feature<OldTrunkConfig>> OLD_TRUNK =
            FEATURES.register("old_trunk", () -> new OldTrunkFeature(OldTrunkConfig.CODEC));


    public static void register(IEventBus bus) {
        FEATURES.register(bus);
    }
}
