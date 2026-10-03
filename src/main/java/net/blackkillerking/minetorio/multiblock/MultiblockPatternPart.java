package net.blackkillerking.minetorio.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.Predicate;

public record MultiblockPatternPart(List<BlockPos> positions, Predicate<BlockState> check) {
}
