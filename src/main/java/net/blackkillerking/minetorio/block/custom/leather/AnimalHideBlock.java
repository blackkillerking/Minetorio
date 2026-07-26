package net.blackkillerking.minetorio.block.custom.leather;

import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AnimalHideBlock extends Block {
    public AnimalHideBlock(Properties pProperties) {
        super(pProperties);
    }
    public VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return SHAPE;
    }

    @Override
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
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
            pLevel.setBlock(pPos, ModBlocks.HIDE.get().defaultBlockState(), 3);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }
}
