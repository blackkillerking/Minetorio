package net.blackkillerking.minetorio.block.base;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class BasicInserterBlockEntityBlock extends BasicBlockEntityBlock{

    public static final IntegerProperty HORIZONTAL_REACH = IntegerProperty.create("horizontal_reach", 1, 3);
    public static final IntegerProperty VERTICAL_REACH = IntegerProperty.create("vertical_reach", 0, 4);
    public static final BooleanProperty ON = BooleanProperty.create("on");

    protected BasicInserterBlockEntityBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(defaultBlockState()
                .setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                .setValue(HORIZONTAL_REACH, 1)
                .setValue(VERTICAL_REACH, 2)
                .setValue(ON, false));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HorizontalDirectionalBlock.FACING, HORIZONTAL_REACH, VERTICAL_REACH, ON);
    }
}
