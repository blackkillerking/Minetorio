package net.blackkillerking.minetorio.event;


import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.particle.HeatedMetalParticle;
import net.blackkillerking.minetorio.particle.ModParticals;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Minetorio.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvent {

    @SubscribeEvent
    public static void registerParticalFactories (RegisterParticleProvidersEvent event){
        event.registerSpriteSet(ModParticals.HEATED_METAL_PARTICLES.get(), HeatedMetalParticle.Provider::new);
    }
}
