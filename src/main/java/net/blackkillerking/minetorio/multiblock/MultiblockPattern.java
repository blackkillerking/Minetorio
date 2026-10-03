package net.blackkillerking.minetorio.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class MultiblockPattern {

    private final List<MultiblockPatternPart> parts;

    public MultiblockPattern(MultiblockPatternPart... part) {
        this.parts = List.of(part);
    }

    public List<BlockPos> rotate(List<BlockPos> positions, Direction facing){
        return positions.stream().map(offset -> rotate(offset, facing)).toList();
    }

    public BlockPos rotate(BlockPos offset, Direction facing){
        return switch (facing){
            case SOUTH -> offset;
            case NORTH -> new BlockPos(-offset.getX(), offset.getY(), -offset.getZ());
            case WEST -> new BlockPos(-offset.getZ(), offset.getY(), offset.getX());
            case EAST -> new BlockPos(offset.getZ(), offset.getY(), offset.getX());
            default -> offset;
        };
    }

    public boolean structureMatches(Level pLevel, BlockPos pAnchor, Direction pFacing){
        for(MultiblockPatternPart part : parts){
            for(BlockPos offset : rotate(part.positions(), pFacing)){
                BlockState state = pLevel.getBlockState(pAnchor.offset(offset));
                if(!part.check().test(state)) return false;
            }
        }
        return true;
    }
}
