package net.blackkillerking.minetorio.block.custom;

import net.blackkillerking.minetorio.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class MudBlockBlock extends Block {
    public MudBlockBlock(Properties pProperties) {
        super(pProperties);
    }

    private static BlockPos rotate(BlockPos offset, Direction facing) {
        return switch (facing) {
            case NORTH -> offset;
            case SOUTH -> new BlockPos(-offset.getX(), offset.getY(), -offset.getZ());
            case WEST  -> new BlockPos(offset.getZ(), offset.getY(), -offset.getX());
            case EAST  -> new BlockPos(-offset.getZ(), offset.getY(), offset.getX());
            default -> offset;
        };
    }

    public static final List<BlockPos> NORTH_BLOCK_PATTERN = List.of(
            new BlockPos(-1, 0, -1),
            new BlockPos(0, 0, -1),
            new BlockPos(1, 0, -1),
            new BlockPos(-1, 0, -2),
            new BlockPos(0, 0, -2),
            new BlockPos(1, 0, -2),
            new BlockPos(-1, 0, -3),
            new BlockPos(0, 0, -3),
            new BlockPos(1, 0, -3),

            new BlockPos(0, 1, -1),
            new BlockPos(0, 1, -3),
            new BlockPos(-1, 1, -2),
            new BlockPos(1, 1, -2),

            new BlockPos(0, 2, -1),
            new BlockPos(0, 2, -3),
            new BlockPos(-1, 2, -2),
            new BlockPos(1, 2, -2),

            new BlockPos(0, 3, -1),
            new BlockPos(0, 3, -3),
            new BlockPos(-1, 3, -2),
            new BlockPos(1, 3, -2)
    );

    public static final List<BlockPos> SOUTH_BLOCK_PATTERN = NORTH_BLOCK_PATTERN.stream().map(offset -> rotate(offset, Direction.SOUTH)).toList();
    public static final List<BlockPos> EAST_BLOCK_PATTERN = NORTH_BLOCK_PATTERN.stream().map(offset -> rotate(offset, Direction.EAST)).toList();
    public static final List<BlockPos> WEST_BLOCK_PATTERN = NORTH_BLOCK_PATTERN.stream().map(offset -> rotate(offset, Direction.WEST)).toList();

    private boolean matchesStructure(Level level, BlockPos anchor, List<BlockPos> pattern) {
        for (BlockPos offset : pattern) {
            BlockPos check = anchor.offset(offset);
            if (!level.getBlockState(check).is(this)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.isClientSide()) return InteractionResult.FAIL;

        boolean isSneaking = pPlayer.isCrouching();
        boolean isStick = pPlayer.getMainHandItem().is(Items.STICK);
        boolean isDirectionPlane = Direction.Plane.HORIZONTAL.test(pPlayer.getDirection()) ;

        if(pHand.equals(InteractionHand.MAIN_HAND) && isStick && isDirectionPlane && !isSneaking){
            boolean patternMatches =
                    switch (pPlayer.getDirection()){
                        case NORTH -> matchesStructure(pLevel, pPos, NORTH_BLOCK_PATTERN);
                        case SOUTH -> matchesStructure(pLevel, pPos, SOUTH_BLOCK_PATTERN);
                        case EAST -> matchesStructure(pLevel, pPos, EAST_BLOCK_PATTERN);
                        case WEST -> matchesStructure(pLevel, pPos, WEST_BLOCK_PATTERN);
                        default -> false;
            };
            if(patternMatches){
                pLevel.setBlock(pPos, ModBlocks.PRIMITIVE_OVEN.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, pPlayer.getDirection()), 3);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.FAIL;
    }
}
