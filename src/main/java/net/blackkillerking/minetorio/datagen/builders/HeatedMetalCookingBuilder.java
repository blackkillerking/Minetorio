package net.blackkillerking.minetorio.datagen.builders;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class HeatedMetalCookingBuilder implements RecipeBuilder {

    private final RecipeCategory category;
    private final CookingBookCategory bookCategory;
    private final Ingredient ingredient;
    private final Item result;
    private final int count;
    private final String value;
    private final int coolingTime;
    private final float experience;
    private final int cookingTime;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    @javax.annotation.Nullable
    private String group;
    private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;


    public HeatedMetalCookingBuilder(RecipeCategory category, CookingBookCategory bookCategory, Ingredient ingredient, Item result, int count, String value, int coolingTime, float experience, int cookingTime, RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
        this.category = category;
        this.bookCategory = bookCategory;
        this.ingredient = ingredient;
        this.result = result;
        this.count = count;
        this.value = value;
        this.coolingTime = coolingTime;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.serializer = serializer;
    }

    public static HeatedMetalCookingBuilder blasting(RecipeCategory pCategory, Ingredient pIngredient, Item pResult, int pCount, String pValue, int pCoolingTime, float pExperience, int pCookingTime) {
        return new HeatedMetalCookingBuilder(pCategory, determineBlastingRecipeCategory(pResult),pIngredient, pResult, pCount, pValue, pCoolingTime, pExperience, pCookingTime, RecipeSerializer.BLASTING_RECIPE);
    }

    public static HeatedMetalCookingBuilder smelting(RecipeCategory pCategory, Ingredient pIngredient, Item pResult, int pCount, String pValue, int pCoolingTime, float pExperience, int pCookingTime) {
        return new HeatedMetalCookingBuilder(pCategory, determineBlastingRecipeCategory(pResult),pIngredient, pResult, pCount, pValue, pCoolingTime, pExperience, pCookingTime, RecipeSerializer.SMELTING_RECIPE);
    }

    private static CookingBookCategory determineSmeltingRecipeCategory(ItemLike pResult) {
        return pResult.asItem() instanceof BlockItem ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
    }
    private static CookingBookCategory determineBlastingRecipeCategory(ItemLike pResult) {
        return pResult.asItem() instanceof BlockItem ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
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
        pFinishedRecipeConsumer.accept(new HeatedMetalCookingBuilder.Result(pRecipeId, this.group == null ? "" : this.group, this.bookCategory, this.ingredient, this.result, this.count, this.value, this.coolingTime, this.experience, this.cookingTime, this.advancement, pRecipeId.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.serializer));

    }

    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }

    private static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final CookingBookCategory category;
        private final Ingredient ingredient;
        private final Item result;
        private final int count;
        private final String value;
        private final int coolingTime;
        private final float experience;
        private final int cookingTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

        public Result(ResourceLocation pId, String pGroup, CookingBookCategory pCategory, Ingredient pIngredient, Item pResult, int pCount, String pValue, int pCoolingTime, float pExperience, int pCookingTime, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
            this.id = pId;
            this.group = pGroup;
            this.category = pCategory;
            this.ingredient = pIngredient;
            this.result = pResult;
            this.count = pCount;
            this.value = pValue;
            this.coolingTime = pCoolingTime;
            this.experience = pExperience;
            this.cookingTime = pCookingTime;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
            this.serializer = pSerializer;
        }

        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }

            JsonObject nbtJsonObject = new JsonObject();
            nbtJsonObject.addProperty("metal_type", this.value);
            nbtJsonObject.addProperty("cooling_time", this.coolingTime);

            JsonObject resultJsonObject = new JsonObject();
            resultJsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            resultJsonObject.addProperty("count", this.count);
            resultJsonObject.add("nbt", nbtJsonObject);

            pJson.addProperty("category", this.category.getSerializedName());
            pJson.add("ingredient", this.ingredient.toJson());
            pJson.add("result", resultJsonObject);
            pJson.addProperty("experience", this.experience);
            pJson.addProperty("cookingtime", this.cookingTime);
        }

        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        /**
         * Gets the ID for the recipe.
         */
        public ResourceLocation getId() {
            return this.id;
        }

        /**
         * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
         */
        @javax.annotation.Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @javax.annotation.Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
