package net.blackkillerking.minetorio.item.custom;

import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class WoodBarkItem extends Item {
    public WoodBarkItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if(entity.level().isClientSide()){
            return false;
        }
        ServerLevel level = (ServerLevel) entity.level();
        BlockPos blockPos = entity.getOnPos().above();
        boolean isWaterSourceBlock = level.getBlockState(blockPos).is(Blocks.WATER);
        if(isSurroundedBySolid(level, blockPos) && isWaterSourceBlock){
            level.removeBlock(blockPos, false);
            level.setBlock(blockPos, ModBlocks.TANNIN_BLOCK.get().defaultBlockState(), 3);
            stack.shrink(1);
        }
        return false;
    }

    private boolean isSurroundedBySolid(ServerLevel level, BlockPos blockPos) {
        return !(level.getBlockState(blockPos.north()).is(ModTags.Blocks.NON_SOLID)) &&
                !(level.getBlockState(blockPos.south()).is(ModTags.Blocks.NON_SOLID)) &&
                !(level.getBlockState(blockPos.east()).is(ModTags.Blocks.NON_SOLID)) &&
                !(level.getBlockState(blockPos.west()).is(ModTags.Blocks.NON_SOLID)) &&
                !(level.getBlockState(blockPos.below()).is(ModTags.Blocks.NON_SOLID));

    }
}
