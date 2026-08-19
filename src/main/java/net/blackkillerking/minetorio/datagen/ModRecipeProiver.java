package net.blackkillerking.minetorio.datagen;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.datagen.builders.HeatedMetalCookingBuilder;
import net.blackkillerking.minetorio.datagen.builders.MetalShapingRecipeBuilder;
import net.blackkillerking.minetorio.datagen.builders.PrimitiveSmeltingRecipeBuilder;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;

import java.util.ArrayList;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.PartialNBTIngredient;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProiver extends RecipeProvider {

    public ModRecipeProiver(PackOutput pOutput) {
        super(pOutput);
    }
    private final List<String> metalTypes = List.of(
            "tin",
            "zinc",
            "silver",
            "iron",
            "copper",
            "gold"
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

        twoByTwoPacker(pWriter, RecipeCategory.MISC, Items.FLINT, ModItems.FLINT_FRAGMENT.get());
        twoByTwoPacker(pWriter, RecipeCategory.MISC, ModBlocks.BASALT_BLOCK.get(), ModItems.BASALT_ROCK.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIRM_PLANT_FIBER.get(), 1).pattern("SP ").pattern("P  ").pattern("   ").define('S', ModTags.Items.STICK_BARK).define('P', ModTags.Items.PLANT_FIBERS).unlockedBy("has_plant_fiber_and_sticks", has(ModTags.Items.PLANT_FIBERS)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CRAFTING_TABLE, 1).pattern("SPS").pattern("APH").pattern("SSS").define('A', ModTags.Items.AXES).define('S', ItemTags.WOODEN_SLABS).define('P', ItemTags.PLANKS).define('H', ModTags.Items.HAMMERS).unlockedBy("has_flint_axe", has(ModItems.FLINT_AXE.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.OLIVE_OIL.get(), 1).pattern("OHO").pattern("OBO").pattern("OOO").define('O', ModItems.OLIVE.get()).define('H', ModTags.Items.HAMMERS).define('B', ModItems.WOOD_BARK.get()).unlockedBy("has_olive", has(ModItems.OLIVE.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TREATED_HIDE.get(), 1).pattern("OOO").pattern(" L ").pattern("   ").define('O', ModItems.OLIVE_OIL.get()).define('L', ModItems.DRIED_HIDE.get()).unlockedBy("has_dried_hide", has(ModItems.DRIED_HIDE.get())).save(pWriter);
        //WOODEN TOOLS
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.WOODEN_PICKAXE, 1).pattern("PB ").pattern("SP ").pattern("   ").define('P', ModTags.Items.PLANT_FIBERS).define('B', ModItems.WOOD_BARK.get()).define('S', Items.STICK).unlockedBy("has_plant_fiber_and_sticks", has(ModTags.Items.PLANT_FIBERS)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WOODEN_AXE_HEAD.get(), 1).pattern("BB ").pattern("PB ").pattern("   ").define('P', ModTags.Items.PLANT_FIBERS).define('B', ModItems.WOOD_BARK.get()).unlockedBy("has_plant_fiber_and_sticks", has(ModTags.Items.PLANT_FIBERS)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.WOODEN_AXE, 1).pattern("PH ").pattern("S  ").pattern("   ").define('P', ModTags.Items.PLANT_FIBERS).define('H', ModItems.WOODEN_AXE_HEAD.get()).define('S', Items.STICK).unlockedBy("has_axe_head", has(ModItems.WOODEN_AXE_HEAD.get())).save(pWriter);
        //FLINT/BASALT TOOLS
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_PICKAXE_HEAD.get(), 1).pattern("SF ").pattern("P  ").pattern("   ").define('P', ModItems.FIRM_PLANT_FIBER.get()).define('F', ModItems.SHARPENED_FLINT.get()).define('S', ModItems.STIFF_STICK.get()).unlockedBy("has_sharpened_flint", has(ModItems.SHARPENED_FLINT.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_AXE_HEAD.get(), 1).pattern("SF ").pattern("PP ").pattern("   ").define('P', ModItems.FIRM_PLANT_FIBER.get()).define('F', ModItems.SHARPENED_FLINT.get()).define('S', ModItems.STIFF_STICK.get()).unlockedBy("has_sharpened_flint", has(ModItems.SHARPENED_FLINT.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_SHOVEL_HEAD.get(), 1).pattern("FF ").pattern("PP ").pattern("   ").define('P', ModItems.FIRM_PLANT_FIBER.get()).define('F', Items.FLINT).unlockedBy("has_flint", has(Items.FLINT)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_HOE_HEAD.get(), 1).pattern("PF ").pattern("P  ").pattern("   ").define('P', ModItems.FIRM_PLANT_FIBER.get()).define('F', Items.FLINT).unlockedBy("has_flint", has(Items.FLINT)).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BASALT_PICKAXE_HEAD.get(), 1).pattern("SB ").pattern("P  ").pattern("   ").define('P', ModItems.FIRM_PLANT_FIBER.get()).define('B', ModItems.SHARPENED_BASALT.get()).define('S', ModItems.STIFF_STICK.get()).unlockedBy("has_sharpened_basalt", has(ModItems.SHARPENED_BASALT.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BASALT_AXE_HEAD.get(), 1).pattern("SB ").pattern("PP ").pattern("   ").define('P', ModItems.FIRM_PLANT_FIBER.get()).define('B', ModItems.SHARPENED_BASALT.get()).define('S', ModItems.STIFF_STICK.get()).unlockedBy("has_sharpened_basalt", has(ModItems.SHARPENED_BASALT.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BASALT_SHOVEL_HEAD.get(), 1).pattern("BB ").pattern("PP ").pattern("   ").define('P', ModItems.FIRM_PLANT_FIBER.get()).define('B', ModItems.BASALT_ROCK.get()).unlockedBy("has_basalt_rock", has(ModItems.BASALT_ROCK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BASALT_HOE_HEAD.get(), 1).pattern("PB ").pattern("P  ").pattern("   ").define('P', ModItems.FIRM_PLANT_FIBER.get()).define('B', ModItems.BASALT_ROCK.get()).unlockedBy("has_basalt_rock", has(ModItems.BASALT_ROCK.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_BASALT_PICKAXE_BODY.get(), 1).pattern("PP ").pattern("S  ").pattern("   ").define('P', ModItems.FIRM_PLANT_FIBER.get()).define('S', ModItems.STIFF_STICK.get()).unlockedBy("has_stiff_stick", has(ModItems.STIFF_STICK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_BASALT_AXE_BODY.get(), 1).pattern("P  ").pattern("S  ").pattern("   ").define('P', ModItems.FIRM_PLANT_FIBER.get()).define('S', ModItems.STIFF_STICK.get()).unlockedBy("has_stiff_stick", has(ModItems.STIFF_STICK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_BASALT_SHOVEL_HOE_BODY.get(), 1).pattern("S  ").pattern("S  ").pattern("   ").define('S', ModItems.STIFF_STICK.get()).unlockedBy("has_stiff_stick", has(ModItems.STIFF_STICK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BASALT_HAMMER_BODY.get(), 1).pattern("SP ").pattern("SP ").pattern("   ").define('S', ModItems.STIFF_STICK.get()).define('P', ModItems.FIRM_PLANT_FIBER.get()).unlockedBy("has_stiff_stick", has(ModItems.STIFF_STICK.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_PICKAXE.get(), 1).pattern("SS ").pattern("s  ").pattern("   ").define('S', ModItems.FLINT_PICKAXE_HEAD.get()).define('s', ModItems.FLINT_BASALT_PICKAXE_BODY.get()).unlockedBy("has_stiff_stick", has(ModItems.STIFF_STICK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_KNIFE.get(), 1).pattern("FP ").pattern("S  ").pattern("   ").define('P', ModItems.FIRM_PLANT_FIBER.get()).define('S', ModItems.STIFF_STICK.get()).define('F', ModItems.SHARPENED_FLINT.get()).unlockedBy("has_stiff_stick", has(ModItems.STIFF_STICK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_AXE.get(), 1).pattern("S  ").pattern("s  ").pattern("   ").define('S', ModItems.FLINT_AXE_HEAD.get()).define('s', ModItems.FLINT_BASALT_AXE_BODY.get()).unlockedBy("has_stiff_stick", has(ModItems.STIFF_STICK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_SHOVEL.get(), 1).pattern("S  ").pattern("s  ").pattern("   ").define('S', ModItems.FLINT_SHOVEL_HEAD.get()).define('s', ModItems.FLINT_BASALT_SHOVEL_HOE_BODY.get()).unlockedBy("has_stiff_stick", has(ModItems.STIFF_STICK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_HOE.get(), 1).pattern("S  ").pattern("s  ").pattern("   ").define('S', ModItems.FLINT_HOE_HEAD.get()).define('s', ModItems.FLINT_BASALT_SHOVEL_HOE_BODY.get()).unlockedBy("has_stiff_stick", has(ModItems.STIFF_STICK.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BASALT_PICKAXE.get(), 1).pattern("SS ").pattern("s  ").pattern("   ").define('S', ModItems.BASALT_PICKAXE_HEAD.get()).define('s', ModItems.FLINT_BASALT_PICKAXE_BODY.get()).unlockedBy("has_stiff_stick", has(ModItems.STIFF_STICK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BASALT_KNIFE.get(), 1).pattern("BP ").pattern("S  ").pattern("   ").define('P', ModItems.FIRM_PLANT_FIBER.get()).define('S', ModItems.STIFF_STICK.get()).define('B', ModItems.SHARPENED_BASALT.get()).unlockedBy("has_stiff_stick", has(ModItems.STIFF_STICK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BASALT_AXE.get(), 1).pattern("S  ").pattern("s  ").pattern("   ").define('S', ModItems.BASALT_AXE_HEAD.get()).define('s', ModItems.FLINT_BASALT_AXE_BODY.get()).unlockedBy("has_stiff_stick", has(ModItems.STIFF_STICK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BASALT_SHOVEL.get(), 1).pattern("S  ").pattern("s  ").pattern("   ").define('S', ModItems.BASALT_SHOVEL_HEAD.get()).define('s', ModItems.FLINT_BASALT_SHOVEL_HOE_BODY.get()).unlockedBy("has_stiff_stick", has(ModItems.STIFF_STICK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BASALT_HOE.get(), 1).pattern("S  ").pattern("s  ").pattern("   ").define('S', ModItems.BASALT_HOE_HEAD.get()).define('s', ModItems.FLINT_BASALT_SHOVEL_HOE_BODY.get()).unlockedBy("has_stiff_stick", has(ModItems.STIFF_STICK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BASALT_HAMMER.get(), 1).pattern("B  ").pattern("s  ").pattern("   ").define('B', ModBlocks.BASALT_BLOCK.get()).define('s', ModItems.BASALT_HAMMER_BODY.get()).unlockedBy("has_basalt_block", has(ModBlocks.BASALT_BLOCK.get())).save(pWriter);

        //METALLURGY RECIPES

        primitiveOvenRecipeBuilder(1, 1800, "tin", ModItems.HEATED_INGOT.get(), 50, 1200, pWriter, "has_raw_tin", ModItems.RAW_TIN.get(), "_from_smelting",Ingredient.of(Items.COAL), Ingredient.of(Items.COAL), Ingredient.of(ModItems.RAW_TIN.get()), Ingredient.EMPTY);
        primitiveOvenRecipeBuilder(1, 1800, "copper", ModItems.HEATED_INGOT.get(), 50, 1200, pWriter, "has_raw_copper", Items.RAW_COPPER, "_from_smelting", Ingredient.of(Items.COAL), Ingredient.of(Items.COAL), Ingredient.of(Items.RAW_COPPER), Ingredient.EMPTY);
        primitiveOvenRecipeBuilder(1, 2400, "zinc", ModItems.HEATED_INGOT.get(), 100, 1800, pWriter, "has_raw_zinc", ModItems.RAW_ZINC.get(), "_from_smelting", Ingredient.of(Items.COAL), Ingredient.of(Items.COAL), Ingredient.of(Items.COAL), Ingredient.of(ModItems.RAW_ZINC.get()));
        primitiveOvenRecipeBuilder(1, 2400, "silver", ModItems.HEATED_INGOT.get(), 100, 1800, pWriter, "has_raw_silver", ModItems.RAW_SILVER.get(), "_from_smelting", Ingredient.of(Items.COAL), Ingredient.of(Items.COAL), Ingredient.of(Items.COAL), Ingredient.of(ModItems.RAW_SILVER.get()));
        primitiveOvenRecipeBuilder(1, 3000, "iron", ModItems.HEATED_INGOT.get(), 200, 2100, pWriter, "has_raw_iron", Items.RAW_IRON, "_from_smelting", Ingredient.of(Items.COAL), Ingredient.of(Items.COAL), Ingredient.of(Items.COAL), Ingredient.of(Items.RAW_IRON));
        primitiveOvenRecipeBuilder(1, 2000, "gold", ModItems.HEATED_INGOT.get(), 250, 1800, pWriter, "has_raw_gold", Items.RAW_GOLD, "_from_smelting", Ingredient.of(Items.COAL), Ingredient.of(Items.COAL), Ingredient.of(Items.COAL), Ingredient.of(Items.RAW_GOLD));

        for(String metalType : metalTypes){

            metalShapingIngotBasedRecipe(ModItems.HEATED_SHEET.get(), ModTags.Items.HAMMERS, null, 2,metalType, 1200, pWriter);
            metalShapingIngotBasedRecipe(ModItems.HEATED_BAR.get(), ModTags.Items.CLIPPERS, null, 4,metalType, 800, pWriter);
            metalShapingIngotBasedRecipe(ModItems.HEATED_STRIPE.get(), ModTags.Items.ROLLERS, null, 2,metalType, 800, pWriter);
            metalShapingIngotBasedRecipe(ModItems.HEATED_ROD.get(), ModTags.Items.HAMMERS, ModTags.Items.CLIPPERS, 8,metalType, 400, pWriter);
            metalShapingIngotBasedRecipe(ModItems.HEATED_PANEL.get(), ModTags.Items.HAMMERS, ModTags.Items.ROLLERS, 1,metalType, 1200, pWriter);
            metalShapingIngotBasedRecipe(ModItems.HEATED_WIRE.get(), ModTags.Items.CLIPPERS, ModTags.Items.ROLLERS, 16,metalType, 200, pWriter);

            metalShapingBarBasedRecipe(ModItems.HEATED_SCREWS.get(), ModTags.Items.HAMMERS, ModTags.Items.HOLLOW_CONES, 32,metalType, 40, pWriter);
            metalShapingBarBasedRecipe(ModItems.HEATED_COLUMN.get(), ModTags.Items.HAMMERS, ModTags.Items.SOLID_CONES, 1,metalType, 800, pWriter);

            metalShapingSheetBasedRecipe(ModItems.HEATED_RING.get(), ModTags.Items.HAMMERS, ModTags.Items.HOLLOW_CONES, 32,metalType, 40, pWriter);

            primitiveOvenRecipeBuilder(1, 1200, metalType, ModItems.HEATED_SHEET.get(), 0, 200, pWriter, "has_" + metalType + "_sheet", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_sheet")), "_from_reheating", Ingredient.of(Items.COAL), Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_sheet"))), Ingredient.EMPTY, Ingredient.EMPTY);
            primitiveOvenRecipeBuilder(1, 800, metalType, ModItems.HEATED_STRIPE.get(), 0, 200, pWriter, "has_" + metalType + "_stripe", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_stripe")), "_from_reheating", Ingredient.of(Items.COAL), Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_stripe"))), Ingredient.EMPTY, Ingredient.EMPTY);
            primitiveOvenRecipeBuilder(1, 1200, metalType, ModItems.HEATED_PANEL.get(), 0, 300, pWriter, "has_" + metalType + "_panel", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_panel")), "_from_reheating", Ingredient.of(Items.COAL), Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_panel"))), Ingredient.EMPTY, Ingredient.EMPTY);
            primitiveOvenRecipeBuilder(1, 200, metalType, ModItems.HEATED_WIRE.get(), 0, 50, pWriter, "has_" + metalType + "_wire", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_wire")), "_from_reheating", Ingredient.of(Items.COAL), Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_wire"))), Ingredient.EMPTY, Ingredient.EMPTY);
            primitiveOvenRecipeBuilder(1, 400, metalType, ModItems.HEATED_ROD.get(), 0, 100, pWriter, "has_" + metalType + "_rod", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_rod")), "_from_reheating", Ingredient.of(Items.COAL), Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_rod"))), Ingredient.EMPTY, Ingredient.EMPTY);
            primitiveOvenRecipeBuilder(1, 40, metalType, ModItems.HEATED_SCREWS.get(), 0, 20, pWriter, "has_" + metalType + "_screws", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_screws")), "_from_reheating", Ingredient.of(Items.COAL), Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_screws"))), Ingredient.EMPTY, Ingredient.EMPTY);
            primitiveOvenRecipeBuilder(1, 800, metalType, ModItems.HEATED_COLUMN.get(), 0, 200, pWriter, "has_" + metalType + "_column", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_column")), "_from_reheating", Ingredient.of(Items.COAL), Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_column"))), Ingredient.EMPTY, Ingredient.EMPTY);
            primitiveOvenRecipeBuilder(1, 40, metalType, ModItems.HEATED_RING.get(), 0, 20, pWriter, "has_" + metalType + "_ring", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_ring")), "_from_reheating", Ingredient.of(Items.COAL), Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_ring"))), Ingredient.EMPTY, Ingredient.EMPTY);

            if(!(metalType == "iron" || metalType == "gold" || metalType == "copper")){
                primitiveOvenRecipeBuilder(1, 1800, metalType, ModItems.HEATED_INGOT.get(), 0, 400, pWriter, "has_" + metalType + "_ingot", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_ingot")), "_from_reheating", Ingredient.of(Items.COAL),Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_ingot"))), Ingredient.EMPTY, Ingredient.EMPTY);
                nuggetByHammering(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_nugget")), 9, ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_ingot")), metalType, pWriter);
            }
        }

        primitiveOvenRecipeBuilder(1, 1800, "iron", ModItems.HEATED_INGOT.get(), 0, 400, pWriter, "has_iron_ingot", Items.IRON_INGOT, "_from_reheating", Ingredient.of(Items.COAL), Ingredient.of(Items.IRON_INGOT), Ingredient.EMPTY, Ingredient.EMPTY);
        primitiveOvenRecipeBuilder(1, 1800, "gold", ModItems.HEATED_INGOT.get(), 0, 400, pWriter, "has_gold_ingot", Items.GOLD_INGOT, "_from_reheating", Ingredient.of(Items.COAL), Ingredient.of(Items.GOLD_INGOT), Ingredient.EMPTY, Ingredient.EMPTY);
        primitiveOvenRecipeBuilder(1, 1800, "copper", ModItems.HEATED_INGOT.get(), 0, 400, pWriter, "has_copper_ingot", Items.COPPER_INGOT, "_from_reheating", Ingredient.of(Items.COAL), Ingredient.of(Items.COPPER_INGOT), Ingredient.EMPTY, Ingredient.EMPTY);
        nuggetByHammering(Items.IRON_NUGGET, 9, Items.IRON_INGOT, "iron", pWriter);
        nuggetByHammering(Items.GOLD_NUGGET, 9, Items.IRON_INGOT, "gold", pWriter);
        nuggetByHammering(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, "copper_nugget")), 9, Items.COPPER_INGOT, "copper", pWriter);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {

            SimpleCookingRecipeBuilder
                    .generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, Minetorio.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

    private static void nuggetByHammering(ItemLike pResult, int pCount, ItemLike pIngredient, String pGroup, Consumer<FinishedRecipe> pWriter){
        new ShapelessRecipeBuilder(RecipeCategory.MISC, pResult, pCount)
                .requires(ModTags.Items.HAMMERS).requires(pIngredient).unlockedBy("has_" + pGroup + "_ingot", has(pIngredient)).group(pGroup).save(pWriter);
    }

    private static void heatedIngotFromSmeltingBuilder(RecipeCategory pCategory, String pRecipeName, ItemLike pIngredient, Item pResult, int pCount, String pType, int pCoolingTime, float pExperience, int pCookingTime, Consumer<FinishedRecipe> FinishedRecipeConsumer, String pGroup){
        HeatedMetalCookingBuilder
                .smelting(pCategory, Ingredient.of(pIngredient), pResult, pCount,  pType, pCoolingTime,pExperience, pCookingTime)
                .group(pGroup)
                .unlockedBy("has_raw_tin", has(pIngredient))
                .save(FinishedRecipeConsumer, Minetorio.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(pIngredient));
    }

    private static void heatedIngotFromBlastingBuilder(RecipeCategory pCategory, String pRecipeName, ItemLike pIngredient, Item pResult, int pCount, String pType, int pCoolingTime, float pExperience, int pCookingTime, Consumer<FinishedRecipe> FinishedRecipeConsumer, String pGroup){
        HeatedMetalCookingBuilder
                .blasting(pCategory, Ingredient.of(pIngredient), pResult, pCount,  pType, pCoolingTime, pExperience, pCookingTime)
                .group(pGroup)
                .unlockedBy("has_raw_tin", has(pIngredient))
                .save(FinishedRecipeConsumer, Minetorio.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(pIngredient));
    }

    private void metalShapingIngotBasedRecipe(Item pItem, TagKey<Item> pTools_1, @Nullable TagKey<Item> pTools_2, int pCount, String pMetalType, int pCoolingTime, Consumer<FinishedRecipe> pWriter) {
        CompoundTag metalData = new CompoundTag();
        metalData.putString("metal_type", pMetalType);

        List<Ingredient> heatedIngotShapingRecipe = new ArrayList<>();
        heatedIngotShapingRecipe.add(Ingredient.of(pTools_1));
        heatedIngotShapingRecipe.add(pTools_2 == null ? Ingredient.EMPTY : Ingredient.of(pTools_2));
        heatedIngotShapingRecipe.add(PartialNBTIngredient.of(metalData, ModItems.HEATED_INGOT.get()));

        new MetalShapingRecipeBuilder(pItem, heatedIngotShapingRecipe, pCount, pCoolingTime)
                .unlockedBy("has_" + pMetalType + "_ingot", has(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, pMetalType + "_ingot"))))
                .save(pWriter);
    }

    private void metalShapingBarBasedRecipe(Item pItem, TagKey<Item> pTools_1, @Nullable TagKey<Item> pTools_2, int pCount, String pMetalType, int pCoolingTime, Consumer<FinishedRecipe> pWriter) {
        CompoundTag metalData = new CompoundTag();
        metalData.putString("metal_type", pMetalType);

        List<Ingredient> heatedBarShapingRecipe = new ArrayList<>();
        heatedBarShapingRecipe.add(Ingredient.of(pTools_1));
        heatedBarShapingRecipe.add(pTools_2 == null ? Ingredient.EMPTY : Ingredient.of(pTools_2));
        heatedBarShapingRecipe.add(PartialNBTIngredient.of(metalData, ModItems.HEATED_BAR.get()));

        new MetalShapingRecipeBuilder(pItem, heatedBarShapingRecipe, pCount, pCoolingTime)
                .unlockedBy("has_" + pMetalType + "_bar", has(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, pMetalType + "_bar"))))
                .save(pWriter);
    }

    private void metalShapingSheetBasedRecipe(Item pItem, TagKey<Item> pTools_1, @Nullable TagKey<Item> pTools_2, int pCount, String pMetalType, int pCoolingTime, Consumer<FinishedRecipe> pWriter) {

        CompoundTag metalData = new CompoundTag();
        metalData.putString("metal_type", pMetalType);

        List<Ingredient> heatedSheetShapingRecipe = new ArrayList<>();
        heatedSheetShapingRecipe.add(Ingredient.of(pTools_1));
        heatedSheetShapingRecipe.add(pTools_2 == null ? Ingredient.EMPTY : Ingredient.of(pTools_2));
        heatedSheetShapingRecipe.add(PartialNBTIngredient.of(metalData, ModItems.HEATED_SHEET.get()));

        new MetalShapingRecipeBuilder(pItem, heatedSheetShapingRecipe, pCount, pCoolingTime)
                .unlockedBy("has_" + pMetalType + "_sheet", has(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, pMetalType + "_sheet"))))
                .save(pWriter);
    }

    private void primitiveOvenRecipeBuilder(int pCount, int pCoolingTime, String pMetalType, Item pResult, float pExperience, int pCookingTime, Consumer<FinishedRecipe> pWriter, String pUnlockKey, Item pUnlockItem, String pRecipeName, Ingredient... ingredients){
        List<Ingredient> primitiveOvenRecipe = Arrays.stream(ingredients).toList();

        new PrimitiveSmeltingRecipeBuilder(primitiveOvenRecipe, pCount, pCoolingTime, pMetalType, pResult, pExperience, pCookingTime)
                .unlockedBy(pUnlockKey, has(pUnlockItem))
                .save(pWriter, Minetorio.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(getLastNonEmptyItem(primitiveOvenRecipe)) + "_in_primitive_furnace");
    }

    private Item getLastNonEmptyItem(List<Ingredient> ingredients){
        for (int i = ingredients.size()-1; i >= 0; i--) {
            if (ingredients.get(i).isEmpty()) continue;
            return ingredients.get(i).getItems()[0].getItem();
        }
        return Items.AIR;
    }
}