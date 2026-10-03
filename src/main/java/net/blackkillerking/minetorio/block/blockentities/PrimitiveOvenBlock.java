package net.blackkillerking.minetorio.block.blockentities;

import net.blackkillerking.minetorio.block.base.BasicMultiblockBlockEntityBlock;
import net.blackkillerking.minetorio.registry.ModBlockEntites;
import net.blackkillerking.minetorio.blockentity.PrimitiveOvenBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class PrimitiveOvenBlock extends BasicMultiblockBlockEntityBlock {

    public PrimitiveOvenBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {

        if (pLevel.isClientSide) {
            return null;
        }

        return createTickerHelper(
                pBlockEntityType,
                ModBlockEntites.PRIMITIVE_OVEN_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof PrimitiveOvenBlockEntity) {
                ((PrimitiveOvenBlockEntity) blockEntity).drops();
            }
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof PrimitiveOvenBlockEntity) {
                NetworkHooks.openScreen((ServerPlayer) pPlayer, (PrimitiveOvenBlockEntity) blockEntity, pPos);
            } else {
                throw new IllegalStateException("Container missing");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PrimitiveOvenBlockEntity(pPos, pState);
    }
}
