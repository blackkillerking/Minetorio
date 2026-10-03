package net.blackkillerking.minetorio.block.base;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class BasicMultiblockBlockEntityBlock extends BasicBlockEntityBlock {
    public static final BooleanProperty ON = BooleanProperty.create("on");

    protected BasicMultiblockBlockEntityBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH));
        this.registerDefaultState(this.defaultBlockState().setValue(ON, false));
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HorizontalDirectionalBlock.FACING);
        pBuilder.add(ON);
    }
}
