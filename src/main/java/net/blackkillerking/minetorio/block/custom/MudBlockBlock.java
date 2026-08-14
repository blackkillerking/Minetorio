package net.blackkillerking.minetorio.block.custom;

import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.block.multiblock.MultiBlockPattern;
import net.blackkillerking.minetorio.block.multiblock.MultiBlockPatternRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class MudBlockBlock extends Block {
    public MudBlockBlock(Properties pProperties) {
        super(pProperties);
    }

    private static final MultiBlockPattern STRUCTURE = MultiBlockPatternRegistry.get("primitive_oven");

    private boolean isValidStructure(Direction pDirection, Level pLevel, BlockPos pPos){
        return STRUCTURE.structureMatches(pLevel, pPos, pDirection);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.isClientSide()) return InteractionResult.FAIL;

        boolean isSneaking = pPlayer.isCrouching();
        boolean isStick = pPlayer.getMainHandItem().is(Items.STICK);
        boolean isDirectionPlane = Direction.Plane.HORIZONTAL.test(pPlayer.getDirection()) ;

        if(pHand.equals(InteractionHand.MAIN_HAND) && isStick && isDirectionPlane && !isSneaking){
            boolean patternMatches = isValidStructure(pPlayer.getDirection(), pLevel, pPos);
            if(patternMatches){
                pLevel.setBlock(pPos, ModBlocks.PRIMITIVE_OVEN.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, pPlayer.getDirection()), 3);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.FAIL;
    }
}
