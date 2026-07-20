package net.blackkillerking.minetorio.recipe;

import net.blackkillerking.minetorio.Minetorio;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Minetorio.MOD_ID);

    public static final RegistryObject<RecipeSerializer<MetalShapingRecipe>> METAL_SHAPING_SERIALIZER =
            RECIPE_SERIALIZERS.register("metal_shaping", () -> MetalShapingRecipe.Serializer.INSTANCE);


    public static void register (IEventBus eventBus){
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
