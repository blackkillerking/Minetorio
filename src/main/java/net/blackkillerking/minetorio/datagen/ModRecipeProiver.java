package net.blackkillerking.minetorio.datagen;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;

import java.util.Map;
import java.util.Random;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProiver extends RecipeProvider {

    Random rand = new Random();

    public ModRecipeProiver(PackOutput pOutput) {
        super(pOutput);
    }

    private final List<ItemLike> tinSmeltables = List.of(
            ModItems.RAW_TIN.get(),
            ModBlocks.TIN_ORE.get()
    );
    private final List<ItemLike> zincSmeltables = List.of(
            ModItems.RAW_ZINC.get(),
            ModBlocks.ZINC_ORE.get()
    );

    private final List<ItemLike> silverSmeltables = List.of(
            ModItems.RAW_SILVER.get(),
            ModBlocks.SILVER_ORE.get()
    );

    private final List<ItemLike> rawTinBlock = List.of(
            ModBlocks.RAW_TIN_BLOCK.get()
    );
    private final List<ItemLike> rawZincBlock = List.of(
            ModBlocks.RAW_ZINC_BLOCK.get()
    );

    private final List<ItemLike> rawSilverBlock = List.of(
            ModBlocks.RAW_SILVER_BLOCK.get()
    );

    private final Map<RegistryObject<Item>, RegistryObject<Item>> INGOT_TO_ROD = Map.of(
            ModItems.TIN_INGOT, ModItems.TIN_ROD,
            ModItems.ZINC_INGOT, ModItems.ZINC_ROD,
            ModItems.SILVER_INGOT, ModItems.SILVER_ROD

    );

    private final Map<RegistryObject<Item>, RegistryObject<Item>> INGOT_TO_SHEET = Map.of(
            ModItems.TIN_INGOT, ModItems.TIN_SHEET,
            ModItems.ZINC_INGOT, ModItems.ZINC_SHEET,
            ModItems.SILVER_INGOT, ModItems.SILVER_SHEET
    );


    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.TIN_INGOT.get(), RecipeCategory.MISC, ModBlocks.TIN_BLOCK.get(),
                "minetorio:tin_ingot", "tin", "minetorio:tin_block", "tin");
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.ZINC_INGOT.get(), RecipeCategory.MISC, ModBlocks.ZINC_BLOCK.get(),
                "minetorio:zinc_ingot", "zinc", "minetorio:zinc_block", "zinc");
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.SILVER_INGOT.get(), RecipeCategory.MISC, ModBlocks.SILVER_BLOCK.get(),
                "minetorio:silver_ingot", "silver", "minetorio:silver_block", "silver");

        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.RAW_TIN.get(), RecipeCategory.MISC, ModBlocks.RAW_TIN_BLOCK.get(),
                "minetorio:raw_tin", "tin", "minetorio:raw_tin_block", "tin");
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.RAW_ZINC.get(), RecipeCategory.MISC, ModBlocks.RAW_ZINC_BLOCK.get(),
                "minetorio:raw_zinc", "zinc", "minetorio:raw_zinc_block", "zinc");
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.RAW_SILVER.get(), RecipeCategory.MISC, ModBlocks.RAW_SILVER_BLOCK.get(),
                "minetorio:raw_silver", "silver", "minetorio:raw_silver_block", "silver");

        oreSmelting(pWriter, tinSmeltables, RecipeCategory.MISC, ModItems.TIN_INGOT.get(), rand.nextFloat(2, 6), 1200, "tin");
        oreSmelting(pWriter, zincSmeltables, RecipeCategory.MISC, ModItems.ZINC_INGOT.get(), rand.nextFloat(4, 10), 1800, "zinc");
        oreSmelting(pWriter, silverSmeltables, RecipeCategory.MISC, ModItems.SILVER_INGOT.get(), rand.nextFloat(4, 10), 2400, "silver");

        oreBlasting(pWriter, tinSmeltables, RecipeCategory.MISC, ModItems.TIN_INGOT.get(), rand.nextFloat(3, 8), 800, "tin");
        oreBlasting(pWriter, zincSmeltables, RecipeCategory.MISC, ModItems.ZINC_INGOT.get(), rand.nextFloat(5, 12), 1200, "zinc");
        oreBlasting(pWriter, silverSmeltables, RecipeCategory.MISC, ModItems.SILVER_INGOT.get(), rand.nextFloat(5, 12), 1600, "silver");

        oreSmelting(pWriter, rawTinBlock, RecipeCategory.MISC, ModBlocks.TIN_BLOCK.get(), rand.nextFloat(18, 54), 3600, "tin");
        oreSmelting(pWriter, rawZincBlock, RecipeCategory.MISC, ModBlocks.ZINC_BLOCK.get(), rand.nextFloat(36, 90), 5400, "zinc");
        oreSmelting(pWriter, rawSilverBlock, RecipeCategory.MISC, ModBlocks.SILVER_BLOCK.get(), rand.nextFloat(36, 90), 7200, "silver");

        oreBlasting(pWriter, rawTinBlock, RecipeCategory.MISC, ModBlocks.TIN_BLOCK.get(), rand.nextFloat(27, 72), 2400, "tin");
        oreBlasting(pWriter, rawZincBlock, RecipeCategory.MISC, ModBlocks.ZINC_BLOCK.get(), rand.nextFloat(45, 108), 3600, "zinc");
        oreBlasting(pWriter, rawSilverBlock, RecipeCategory.MISC, ModBlocks.SILVER_BLOCK.get(), rand.nextFloat(45, 108), 4800, "silver");

        INGOT_TO_ROD.forEach((ingot, rod) -> {
            ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.MISC, rod.get())
                    .requires(ModTags.Items.CLIPPERS)
                    .requires(ingot.get())
                    .unlockedBy("has_" + ingot.getKey(), has(ingot.get()))
                    .save(pWriter);
        });

        INGOT_TO_SHEET.forEach((ingot, rod) -> {
            ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.MISC, rod.get())
                    .requires(ModTags.Items.HAMMERS)
                    .requires(ingot.get())
                    .unlockedBy("has_" + ingot.getKey(), has(ingot.get()))
                    .save(pWriter);
        });


    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(
                pFinishedRecipeConsumer,
                RecipeSerializer.SMELTING_RECIPE,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTIme,
                pGroup,
                "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(
                pFinishedRecipeConsumer,
                RecipeSerializer.BLASTING_RECIPE,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTime,
                pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder
                    .generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, Minetorio.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}