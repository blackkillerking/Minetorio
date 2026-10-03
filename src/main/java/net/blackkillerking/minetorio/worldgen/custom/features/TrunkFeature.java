package net.blackkillerking.minetorio.worldgen.custom.features;

import com.mojang.serialization.Codec;
import net.blackkillerking.minetorio.worldgen.custom.config.TrunkConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class TrunkFeature extends Feature<TrunkConfig> {
    public TrunkFeature(Codec<TrunkConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<TrunkConfig> pContext) {
        WorldGenLevel level = pContext.level();
        RandomSource random = pContext.random();
        TrunkConfig config = pContext.config();
        BlockPos origin = pContext.origin();

        Direction.Axis axis = random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
        Direction dir = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;

        int length = config.length().sample(random);

        BlockPos start = findGround(level, origin);
        if (start == null) return false;

        boolean placedAny = false;
        for (int i = 0; i < length; i++) {
            BlockPos pos = start.relative(dir, i);

            BlockPos below = pos.below();
            if (!level.getBlockState(below).isFaceSturdy(level, below, Direction.UP)) {
                break;
            }
            if (!canBeReplacedByLog(level, pos)) continue;

            BlockState logState = config.provider().getState(random, pos);
            if (logState.hasProperty(RotatedPillarBlock.AXIS)) {
                logState = logState.setValue(RotatedPillarBlock.AXIS, axis);
            }
            level.setBlock(pos, logState, Block.UPDATE_CLIENTS);
            placedAny = true;
        }
        return placedAny;
    }

    private BlockPos findGround(WorldGenLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos mut = pos.mutable();
        for (int i = 0; i < 16; i++) {
            if (level.getBlockState(mut).isAir() && level.getBlockState(mut.below()).isFaceSturdy(level, mut.below(), Direction.UP)) {
                return mut.immutable();
            }
            mut.move(Direction.DOWN);
        }
        return null;
    }

    private boolean canBeReplacedByLog(WorldGenLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.isAir() || state.is(BlockTags.REPLACEABLE_BY_TREES) || state.is(BlockTags.LEAVES);
    }
}
