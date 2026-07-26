package net.blackkillerking.minetorio.item;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {

    public static final Tier COPPER = TierSortingRegistry.registerTier(
            new ForgeTier(2, 200, 7f, 1.5f, 10,
                    ModTags.Blocks.NEEDS_COPPER_TOOL, () -> Ingredient.of(Items.COPPER_INGOT)),
            new ResourceLocation(Minetorio.MOD_ID, "copper"), List.of(), List.of(Tiers.IRON));

    public static final Tier FLINT = TierSortingRegistry.registerTier(
            new ForgeTier(2, 40, 1f, 1f, 4,
                    ModTags.Blocks.NEEDS_FLINT_TOOL, () -> Ingredient.of(ModItems.SHARPENED_FLINT.get())),
            new ResourceLocation(Minetorio.MOD_ID, "flint"), List.of(), List.of(Tiers.WOOD));


}

