package net.blackkillerking.minetorio.block.leather;

import net.blackkillerking.minetorio.registry.ModBlocks;
import net.blackkillerking.minetorio.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class TreatedHideBlock extends AbstractHideBlock {
    public TreatedHideBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.isClientSide()){
            return InteractionResult.FAIL;
        }
        boolean isSneaking = pPlayer.isCrouching();
        boolean isMainHand = pHand.equals(InteractionHand.MAIN_HAND);
        ItemStack itemUsed = pPlayer.getItemInHand(pHand);
        if(!isSneaking && isMainHand && itemUsed.is(ModItems.STIFF_STICK.get())) {
            pLevel.setBlock(pPos, ModBlocks.LEATHER.get().defaultBlockState(), 3);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }
}
