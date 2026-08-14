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
import org.jetbrains.annotations.Nullable;

public class PrimitiveSmeltingRecipe implements Recipe<SimpleContainer> {

    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final int cookingTime;
    private final float experience;
    private final ResourceLocation id;

    public PrimitiveSmeltingRecipe(NonNullList<Ingredient> ingredients, ItemStack result, int cookingTime, float experience, ResourceLocation id) {
        this.ingredients = ingredients;
        this.result = result;
        this.cookingTime = cookingTime;
        this.experience = experience;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) return false;
        for (int i = 0; i < 4; i++) {
            if(!this.ingredients.get(i).getItems()[0].is(pContainer.getItem(i).getItem())) return false;
        }
        return true;
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

    public int getCookingTime(){
        return this.cookingTime;
    }

    public float getExperience(){
        return this.experience;
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

    public static class Type implements RecipeType<PrimitiveSmeltingRecipe>{
        private Type() {}
        public static final PrimitiveSmeltingRecipe.Type INSTANCE = new PrimitiveSmeltingRecipe.Type();
        public static final String ID = "primitive_smelting";
    }

    public static class Serializer implements RecipeSerializer<PrimitiveSmeltingRecipe>{

        public static final PrimitiveSmeltingRecipe.Serializer INSTANCE = new PrimitiveSmeltingRecipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Minetorio.MOD_ID,"primitive_smelting");

        @Override
        public PrimitiveSmeltingRecipe fromJson(ResourceLocation id, JsonObject json) {

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(4, Ingredient.EMPTY);
            for (int i = 0; i < 4; i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            int cookingTime = GsonHelper.getAsInt(json, "cooking_time", 200);
            float experience = GsonHelper.getAsFloat(json, "experience", 0.0f);
            return new PrimitiveSmeltingRecipe(inputs, result, cookingTime, experience, id);
        }

        @Override
        public PrimitiveSmeltingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < 4; i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack result = buf.readItem();
            int cookingTime = buf.readVarInt();
            float experience = buf.readFloat();
            return new PrimitiveSmeltingRecipe(inputs, result, cookingTime, experience, id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, PrimitiveSmeltingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(null), false);
            buf.writeInt(recipe.cookingTime);
            buf.writeFloat(recipe.experience);
        }
    }
}
