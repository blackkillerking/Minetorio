package net.blackkillerking.minetorio.datagen.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.recipe.PrimitiveSmeltingRecipe;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class PrimitiveSmeltingRecipeBuilder implements RecipeBuilder {
    private final List<Ingredient> ingredients;
    private final int count;
    private final int coolingTime;
    private final String type;
    private final Item result;
    private final float experience;
    private final int cookingTime;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    @Nullable
    private String group;

    public PrimitiveSmeltingRecipeBuilder(List<Ingredient> ingredients, int count, int coolingTime, String type, Item result, float experience, int cookingTime) {
        this.ingredients = ingredients;
        this.count = count;
        this.coolingTime = coolingTime;
        this.type = type;
        this.result = result;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new PrimitiveSmeltingRecipeBuilder.Result(pRecipeId, this.group == null ? "" : this.group, this.ingredients,  this.count, this.coolingTime, this.type, this.result, this.experience, this.cookingTime, this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/" + pRecipeId.getPath())));

    }

    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }

    private static class Result implements FinishedRecipe{
        private final ResourceLocation id;
        private final String group;
        private final List<Ingredient> ingredients;
        private final int count;
        private final int coolingTime;
        private final String type;
        private final Item result;
        private final float experience;
        private final int cookingTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        private Result(ResourceLocation id, String group, List<Ingredient> ingredients, int count, int coolingTime, String type, Item result, float experience, int cookingTime, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.group = group;
            this.ingredients = ingredients;
            this.count = count;
            this.coolingTime = coolingTime;
            this.type = type;
            this.result = result;
            this.experience = experience;
            this.cookingTime = cookingTime;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            if(!this.group.isEmpty()){
                pJson.addProperty("group", this.group);
            }

            JsonObject nbtJsonObject = new JsonObject();
            nbtJsonObject.addProperty("metal_type", this.type);
            nbtJsonObject.addProperty("cooling_time", this.coolingTime);

            JsonObject resultJsonObject = new JsonObject();
            resultJsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            resultJsonObject.addProperty("count", this.count);
            resultJsonObject.add("nbt", nbtJsonObject);

            JsonArray ingredientsArray = new JsonArray();
            for (Ingredient ingredient : this.ingredients) {
                ingredientsArray.add(ingredient.toJson());
            }

            pJson.add("ingredients", ingredientsArray);
            pJson.add("result", resultJsonObject);
            pJson.addProperty("experience", this.experience);
            pJson.addProperty("cooking_time", this.cookingTime);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return PrimitiveSmeltingRecipe.Serializer.INSTANCE;
        }

        @Override
        public @Nullable JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Override
        public @Nullable ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
