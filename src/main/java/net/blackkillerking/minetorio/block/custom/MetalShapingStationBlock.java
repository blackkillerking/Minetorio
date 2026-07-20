package net.blackkillerking.minetorio.block.custom;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.entity.MetalShapingStationBlockEntity;
import net.blackkillerking.minetorio.block.entity.ModBlockEntites;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class MetalShapingStationBlock extends BaseEntityBlock {

    public MetalShapingStationBlock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()){
            Minetorio.LOGGER.info("Block removed");
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof MetalShapingStationBlockEntity){
                ((MetalShapingStationBlockEntity) blockEntity).drops();
            }
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        Minetorio.LOGGER.info("Block Used");
        if(!pLevel.isClientSide){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            Minetorio.LOGGER.info("On Server Side");
            if (blockEntity instanceof MetalShapingStationBlockEntity){
                Minetorio.LOGGER.info("Opened Screen");
                NetworkHooks.openScreen((ServerPlayer) pPlayer, (MetalShapingStationBlockEntity) blockEntity, pPos);
            }
            else {
                throw new IllegalStateException("Container missing");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MetalShapingStationBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {

        if (pLevel.isClientSide){
            return null;
        }

        return createTickerHelper(
                pBlockEntityType,
                ModBlockEntites.METAL_SHAPING_STATION_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}
