package net.blackkillerking.minetorio.item.custom.leather;

import net.blackkillerking.minetorio.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;

public class TreatedHideItem extends Item {
    public TreatedHideItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide()){
            return InteractionResult.FAIL;
        }
        boolean isSneaking = pContext.getPlayer().isCrouching();
        boolean isMainHand = pContext.getHand().equals(InteractionHand.MAIN_HAND);
        boolean isStone = pContext.getLevel().getBlockState(pContext.getClickedPos()).is(Blocks.STONE);
        boolean isClear = pContext.getLevel().getBlockState(pContext.getClickedPos().above()).is(Blocks.AIR);
        if(isSneaking && isMainHand && isStone && isClear){
            pContext.getLevel().setBlock(pContext.getClickedPos().above(), ModBlocks.TREATED_HIDE.get().defaultBlockState(), 3);
            pContext.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
