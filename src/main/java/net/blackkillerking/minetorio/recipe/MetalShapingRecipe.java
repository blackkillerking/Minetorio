package net.blackkillerking.minetorio.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.blackkillerking.minetorio.Minetorio;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.PartialNBTIngredient;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.List;

public class MetalShapingRecipe implements Recipe<SimpleContainer> {

    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final ResourceLocation id;
    public Logger logger= Minetorio.LOGGER;

    public MetalShapingRecipe(NonNullList<Ingredient> ingredients, ItemStack result, ResourceLocation id) {
        this.ingredients = ingredients;
        this.result = result;
        this.id = id;
    }


    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide){
            return false;
        }

        List<ItemStack> toolInputs = List.of(
                pContainer.getItem(0),
                pContainer.getItem(1)
        );
        List<Ingredient> toolIngredients = List.of(
                this.ingredients.get(0),
                this.ingredients.get(1)
        );

        boolean toolsMatch = RecipeMatcher.findMatches(toolInputs, toolIngredients) != null;
        boolean heatedIngotMatches = pContainer.getItem(2).getTag() != null && this.ingredients.get(2).getItems()[0].getTag() != null && this.ingredients.get(2).getItems()[0].getTag().getString("metal_type").equals(pContainer.getItem(2).getTag().getString("metal_type"));

        return toolsMatch && heatedIngotMatches;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }


    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<MetalShapingRecipe>{
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "metal_shaping";
    }

    public static class Serializer implements RecipeSerializer<MetalShapingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Minetorio.MOD_ID,"metal_shaping");

        @Override
        public MetalShapingRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(3, Ingredient.EMPTY);

            for (int i = 0; i < 2; i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            inputs.set(2, PartialNBTIngredient.fromJson(ingredients.get(2), false));

            return new MetalShapingRecipe(inputs, result, id);
        }

        @Override
        public MetalShapingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < 2; i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }
            inputs.set(2, PartialNBTIngredient.fromNetwork(buf));

            ItemStack result = buf.readItem();
            return new MetalShapingRecipe(inputs, result, id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MetalShapingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(null), false);
        }

    }
}

