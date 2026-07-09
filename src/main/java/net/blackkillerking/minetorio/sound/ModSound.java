package net.blackkillerking.minetorio.sound;

import net.blackkillerking.minetorio.Minetorio;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSound {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Minetorio.MOD_ID);

    public static final RegistryObject<SoundEvent> HAMMER_CRAFTING = registerSoundEvent("hammer_crafting");

    public static final RegistryObject<SoundEvent> MO_BEATS = registerSoundEvent("mo_beats");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(Minetorio.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }


    public static void register (IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
