package net.blackkillerking.minetorio.block.custom;

import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.particle.ModParticals;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class OldLogBlock extends RotatedPillarBlock {
    public OldLogBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.isClientSide()){
            return InteractionResult.FAIL;
        }
        ServerLevel level = (ServerLevel) pLevel;
        boolean isSneaking = pPlayer.isCrouching();
        boolean isMainHand = pHand.equals(InteractionHand.MAIN_HAND);
        boolean isEmptyHand = pPlayer.getItemInHand(pHand).isEmpty();
        Random rand = new Random();
        ItemEntity woodBark = new ItemEntity(pLevel, pPos.getX(), pPos.getY() + 1, pPos.getZ(), new ItemStack(ModItems.WOOD_BARK.get(), rand.nextInt(1,3)));
        if(!isSneaking && isMainHand && isEmptyHand){
            pLevel.addFreshEntity(woodBark);
            level.sendParticles(ModParticals.HEATED_METAL_PARTICLES.get(),
                    pPos.getX(), pPos.getY() + 1, pPos.getZ(), 4, 1 ,1 ,1 , 0.1);
            pLevel.removeBlock(pPos, false);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

}
