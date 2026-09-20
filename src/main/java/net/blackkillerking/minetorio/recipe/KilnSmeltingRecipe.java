package net.blackkillerking.minetorio.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.blackkillerking.minetorio.Minetorio;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.PartialNBTIngredient;

public class KilnSmeltingRecipe implements Recipe<SimpleContainer> {

    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final int cookingTime;
    private final ResourceLocation id;

    public KilnSmeltingRecipe(NonNullList<Ingredient> ingredients, ItemStack result, int cookingTime, ResourceLocation id) {
        this.ingredients = ingredients;
        this.result = result;
        this.cookingTime = cookingTime;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return false;
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
        return result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
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

    public static class Type implements RecipeType<KilnSmeltingRecipe>{
        private Type() {}
        public static final KilnSmeltingRecipe.Type INSTANCE = new KilnSmeltingRecipe.Type();
        public static final String ID = "kiln_smelting";
    }

    public static class Serializer implements RecipeSerializer<KilnSmeltingRecipe> {
        public static final KilnSmeltingRecipe.Serializer INSTANCE = new KilnSmeltingRecipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Minetorio.MOD_ID,"kiln_smelting");

        @Override
        public KilnSmeltingRecipe fromJson(ResourceLocation id, JsonObject json) {

            JsonArray ingredientsJson = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
            for (int i = 0; i < 2; i++) {
                inputs.set(i, Ingredient.fromJson(ingredientsJson.get(i)));
            }

            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            int cookingTime = GsonHelper.getAsInt(json, "cooking_time", 200);
            return new KilnSmeltingRecipe(inputs, result, cookingTime, id);
        }

        @Override
        public KilnSmeltingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < 2; i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack result = buf.readItem();
            int cookingTime = buf.readVarInt();
            return new KilnSmeltingRecipe(inputs, result, cookingTime, id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, KilnSmeltingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(null), false);
            buf.writeInt(recipe.cookingTime);
        }

    }
}
