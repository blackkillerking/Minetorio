package net.blackkillerking.minetorio.event.client;


import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.blockentity.KilnControllerBlockEntity;
import net.blackkillerking.minetorio.blockentity.PrimitiveOvenBlockEntity;
import net.blackkillerking.minetorio.multiblock.MultiblockPatternRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Minetorio.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientBusEvent {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            MultiblockPatternRegistry.register("primitive_oven", PrimitiveOvenBlockEntity.PRIMITIVE_OVEN_BASE_STRUCTURE);
            MultiblockPatternRegistry.register("kiln_oven", KilnControllerBlockEntity.KILN_BASE_STRUCTURE);
        });
    }
}
