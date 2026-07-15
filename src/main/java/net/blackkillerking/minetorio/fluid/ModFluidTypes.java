package net.blackkillerking.minetorio.fluid;

import net.blackkillerking.minetorio.Minetorio;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Minetorio.MOD_ID);

    public static final RegistryObject<FluidType> CRUDE_OIL_FLUID_TYPE =
            registerFluid("crude_oil_fluid", new BaseFluidType(
                    new ResourceLocation(Minetorio.MOD_ID, "block/crude_oil_still"),
                    new ResourceLocation(Minetorio.MOD_ID, "block/crude_oil_flow"),
                    new ResourceLocation(Minetorio.MOD_ID, "block/crude_oil_overlay"),
                    808080,
                    new Vector3f(0.5F,0.5F,0.5F),
                    FluidType.Properties.create().canDrown(false).density(1500).lightLevel(0).canSwim(false).viscosity(2000)));




    private static RegistryObject<FluidType> registerFluid (String name, FluidType type){
        return FLUID_TYPES.register(name, () -> type);
    }

    public static void register (IEventBus eventBus){
        FLUID_TYPES.register(eventBus);
    }
}
