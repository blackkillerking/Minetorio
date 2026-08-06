package net.blackkillerking.minetorio.block.entity;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntites {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Minetorio.MOD_ID);


    public final static RegistryObject<BlockEntityType<MetalShapingStationBlockEntity>> METAL_SHAPING_STATION_BE =
            BLOCK_ENTITIES.register("metal_shaping_station_be", () ->
                    BlockEntityType.Builder.of(MetalShapingStationBlockEntity::new,
                    ModBlocks.METAL_SHAPING_STATION.get()).build(null));

    public final static RegistryObject<BlockEntityType<PrimitiveOvenBlockEntity>> PRIMITIVE_OVEN_BE =
            BLOCK_ENTITIES.register("primitive_oven_be", () ->
                    BlockEntityType.Builder.of(PrimitiveOvenBlockEntity::new,
                            ModBlocks.PRIMITIVE_OVEN.get()).build(null));

    public static void register (IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
