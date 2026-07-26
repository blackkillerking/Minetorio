package net.blackkillerking.minetorio.utils;

import net.blackkillerking.minetorio.Minetorio;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Items{

        public static final TagKey<Item> POLISHABLE_TOOLS = tag("polishable_tools");
        public static final TagKey<Item> CLIPPERS = tag("clippers");
        public static final TagKey<Item> HAMMERS = tag("hammers");
        public static final TagKey<Item> ROLLERS = tag("rollers");
        public static final TagKey<Item> SOLID_CONES = tag("solid_cones");
        public static final TagKey<Item> HOLLOW_CONES = tag("hollow_cones");
        public static final TagKey<Item> AXES = tag("axes");

        public static final TagKey<Item> METAL_WORKING_TOOLS = tag("metal_working_tools");
        public static final TagKey<Item> HEATED_METALS = tag("heated_metals");

        public static final TagKey<Item> PLANT_FIBERS = tag("plant_fibers");
        public static final TagKey<Item> STICK_BARK = tag("plant_fibers");

        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(Minetorio.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name){
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Blocks{

        public static final TagKey<Block> NEEDS_COPPER_TOOL = tag("need_copper_tool");
        public static final TagKey<Block> NEEDS_FLINT_TOOL = tag("need_flint_tool");

        public static final TagKey<Block> NON_SOLID = tag("non_solid");

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(Minetorio.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name){
            return BlockTags.create(new ResourceLocation("forge", name));
        }

    }
}
