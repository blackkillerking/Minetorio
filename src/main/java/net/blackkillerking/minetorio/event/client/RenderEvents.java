package net.blackkillerking.minetorio.event.client;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.registry.ModBlockEntites;
import net.blackkillerking.minetorio.renderers.blockentities.MechanicalInserterBlockEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Minetorio.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderEvents {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntites.MECHANICAL_INSERTER_BE.get(), MechanicalInserterBlockEntityRenderer::new);
    }
}
