package net.blackkillerking.minetorio.loot;

import com.mojang.serialization.Codec;
import net.blackkillerking.minetorio.Minetorio;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Minetorio.MOD_ID);



    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM =
            LOOT_MODIFIERS_SERIALIZERS.register("item", AddItemModifier.CODEC);

    public static final RegistryObject<Codec<EntityLootModifier>> EXTRA_ENTITY_DROP =
            LOOT_MODIFIERS_SERIALIZERS.register("entity_add", () -> EntityLootModifier.CODEC);

    public static void register(IEventBus eventBus){
        LOOT_MODIFIERS_SERIALIZERS.register(eventBus);
    }
}
