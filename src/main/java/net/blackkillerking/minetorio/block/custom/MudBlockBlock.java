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

    private static BlockPos rotate(BlockPos offset, Direction facing) {
        return switch (facing) {
            case NORTH -> offset;
            case SOUTH -> new BlockPos(-offset.getX(), offset.getY(), -offset.getZ());
            case WEST  -> new BlockPos(offset.getZ(), offset.getY(), -offset.getX());
            case EAST  -> new BlockPos(-offset.getZ(), offset.getY(), offset.getX());
            default -> offset;
        };
    }

    private static final List<BlockPos> NORTH_BLOCK_PATTERN = List.of(
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

    private static final List<BlockPos> NORTH_EMPTY_PATTERN = List.of(
            new BlockPos(1, 1, -1),
            new BlockPos(-1, 1, -1),
            new BlockPos(1, 1, -3),
            new BlockPos(-1, 1, -3),
            new BlockPos(0, 1, -2),
            new BlockPos(0, 2, -2),
            new BlockPos(0, 3, -2),
            new BlockPos(0, 0, -4),
            new BlockPos(2, 0, -2),
            new BlockPos(-2, 0, -2)
    );


    private static final List<BlockPos> SOUTH_BLOCK_PATTERN = NORTH_BLOCK_PATTERN.stream().map(offset -> rotate(offset, Direction.SOUTH)).toList();
    private static final List<BlockPos> EAST_BLOCK_PATTERN = NORTH_BLOCK_PATTERN.stream().map(offset -> rotate(offset, Direction.EAST)).toList();
    private static final List<BlockPos> WEST_BLOCK_PATTERN = NORTH_BLOCK_PATTERN.stream().map(offset -> rotate(offset, Direction.WEST)).toList();

    private static final List<BlockPos> SOUTH_EMPTY_PATTERN = NORTH_EMPTY_PATTERN.stream().map(offset -> rotate(offset, Direction.SOUTH)).toList();
    private static final List<BlockPos> EAST_EMPTY_PATTERN = NORTH_EMPTY_PATTERN.stream().map(offset -> rotate(offset, Direction.EAST)).toList();
    private static final List<BlockPos> WEST_EMPTY_PATTERN = NORTH_EMPTY_PATTERN.stream().map(offset -> rotate(offset, Direction.WEST)).toList();


    private boolean isValidStructure(Direction pDirection, Level pLevel, BlockPos pPos){
        return switch (pDirection){
            case NORTH -> matchesMudStructure(pLevel, pPos, NORTH_BLOCK_PATTERN) && matchesAirStructure(pLevel, pPos, NORTH_EMPTY_PATTERN);
            case SOUTH -> matchesMudStructure(pLevel, pPos, SOUTH_BLOCK_PATTERN) && matchesAirStructure(pLevel, pPos, SOUTH_EMPTY_PATTERN);
            case EAST -> matchesMudStructure(pLevel, pPos, EAST_BLOCK_PATTERN) && matchesAirStructure(pLevel, pPos, EAST_EMPTY_PATTERN);
            case WEST -> matchesMudStructure(pLevel, pPos, WEST_BLOCK_PATTERN) && matchesAirStructure(pLevel, pPos, WEST_EMPTY_PATTERN);
            default -> false;
        };
    }

    private boolean matchesAirStructure(Level level, BlockPos anchor, List<BlockPos> emptyPattern){
        for (BlockPos offset : emptyPattern) {
            BlockPos check = anchor.offset(offset);
            if (!level.getBlockState(check).is(Blocks.AIR)) {
                return false;
            }
        }
        return true;
    }

    private boolean matchesMudStructure(Level level, BlockPos anchor, List<BlockPos> pattern) {
        for (BlockPos offset : pattern) {
            BlockPos check = anchor.offset(offset);
            if (!level.getBlockState(check).is(ModBlocks.MUD_BLOCK.get())) {
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
