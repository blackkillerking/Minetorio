package net.blackkillerking.minetorio.datagen.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.recipe.MetalShapingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.List;
import java.util.function.Consumer;

public class MetalShapingRecipeBuilder implements RecipeBuilder {

    private final Item result;
    private final List<Ingredient> ingredients;
    private final int count;
    private final int coolingTime;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    @javax.annotation.Nullable
    private String group;

    public MetalShapingRecipeBuilder(Item result, List<Ingredient> ingredients, int count, int coolingTime) {
        this.result = result;
        this.ingredients = ingredients;
        this.count = count;
        this.coolingTime = coolingTime;
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
        pFinishedRecipeConsumer.accept(new MetalShapingRecipeBuilder.Result(pRecipeId, this.group == null ? "" : this.group, this.ingredients, this.result, this.count, this.coolingTime, this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/" + pRecipeId.getPath())));

    }

    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }

    private static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final List<Ingredient> ingredients;
        private final Item result;
        private final int count;
        private final int coolingTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, String pGroup, List<Ingredient> pIngredients, Item pResult, int pCount, int pCoolingTime, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId) {
            this.id = pId;
            this.group = pGroup;
            this.ingredients = pIngredients;
            this.result = pResult;
            this.count = pCount;
            this.coolingTime = pCoolingTime;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }


            JsonObject nbtJsonObject = new JsonObject();
            nbtJsonObject.addProperty("metal_type", this.ingredients.get(2).getItems()[0].getTag().getString("metal_type"));
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

        }

        public RecipeSerializer<?> getType() {
            return MetalShapingRecipe.Serializer.INSTANCE;
        }
        public ResourceLocation getId() {
            return new ResourceLocation(Minetorio.MOD_ID,
                    ForgeRegistries.ITEMS.getKey(this.result).getPath() + "_from_" + this.ingredients.get(2).getItems()[0].getTag().getString("metal_type") + "_metal_shaping");
        }

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
