package net.blackkillerking.minetorio.block.custom;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class MudLayerBlock extends Block {

    public static final int MAX_HEIGHT = 4;
    public static final IntegerProperty LAYERS = IntegerProperty.create("layers", 1, 4);
    public static final IntegerProperty DRYING = IntegerProperty.create("drying", 0, 3);
    public static final BooleanProperty SUPPORTED = BooleanProperty.create("supported");
    protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{Shapes.empty(),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    public static final int HEIGHT_IMPASSABLE = 2;

    public MudLayerBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, 1));
        this.registerDefaultState(this.stateDefinition.any().setValue(DRYING, 0));
        this.registerDefaultState(this.stateDefinition.any().setValue(SUPPORTED, false));
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {return SHAPE_BY_LAYER[pState.getValue(LAYERS)];}
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {return SHAPE_BY_LAYER[pState.getValue(LAYERS)];}
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {return SHAPE_BY_LAYER[pState.getValue(LAYERS)];}
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {return SHAPE_BY_LAYER[pState.getValue(LAYERS)];}

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());
        return Block.isFaceFull(blockstate.getCollisionShape(pLevel, pPos.below()), Direction.UP) || blockstate.is(this) && blockstate.getValue(LAYERS) == 4;
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        int i = pState.getValue(LAYERS);
        if (pUseContext.getItemInHand().is(this.asItem()) && i < 4) {
            if (pUseContext.replacingClickedOnBlock()) {
                return pUseContext.getClickedFace() == Direction.UP;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if(pLevel.isClientSide()){
            return;
        }
        int drying = pState.getValue(DRYING);
        int layers = pState.getValue(LAYERS);
        boolean supported = pState.getValue(SUPPORTED);
        if(drying >= 3){
            boolean isDried = drying == 3;
            boolean maxLayers = layers == 4;

            if(maxLayers && isDried && supported){
                BlockState blockState = ModBlocks.MUD_BLOCK.get().defaultBlockState();
                pLevel.setBlock(pPos, blockState, 3);
            }
            return;
        }

        pLevel.setBlock(pPos, pState.setValue(LAYERS, layers).setValue(DRYING, drying + 1).setValue(SUPPORTED, supported), 3);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.isClientSide()){
            return InteractionResult.FAIL;
        }
        boolean isFirmFiber = pPlayer.getMainHandItem().is(ModItems.FIRM_PLANT_FIBER.get());
        boolean isMainHand = pHand.equals(InteractionHand.MAIN_HAND);
        boolean isSneaking = pPlayer.isCrouching();
        boolean supported = pState.getValue(SUPPORTED);
        boolean isDried = pState.getValue(DRYING) >= 3;

        if(isFirmFiber && isMainHand && !isSneaking && !supported && !isDried){
            int layers = pState.getValue(LAYERS);
            int drying = pState.getValue(DRYING);
            pPlayer.getMainHandItem().shrink(1);
            pLevel.setBlock(pPos, pState.setValue(LAYERS, layers).setValue(DRYING, drying).setValue(SUPPORTED, true), 3);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        boolean supported = pState.getValue(SUPPORTED);
        boolean isDried = pState.getValue(DRYING) >= 3;
        if(!supported && isDried){
            pLevel.removeBlock(pPos, false);
            pLevel.playLocalSound(pPos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 2,1,false);
        }
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
            new BlockPos(1, 3, -2),

            new BlockPos(0, 4, -1),
            new BlockPos(0, 4, -3),
            new BlockPos(-1, 4, -2),
            new BlockPos(1, 4, -2)
    );

    public static final List<BlockPos> SOUTH_BLOCK_PATTERN = NORTH_BLOCK_PATTERN.stream().map(offset -> rotate(offset, Direction.SOUTH)).toList();
    public static final List<BlockPos> EAST_BLOCK_PATTERN = NORTH_BLOCK_PATTERN.stream().map(offset -> rotate(offset, Direction.EAST)).toList();
    public static final List<BlockPos> WEST_BLOCK_PATTERN = NORTH_BLOCK_PATTERN.stream().map(offset -> rotate(offset, Direction.WEST)).toList();

    private static List<BlockPos> getPatternForFacing(Direction facing) {
        return NORTH_BLOCK_PATTERN.stream()
                .map(offset -> rotate(offset, facing))
                .toList();
    }

    private boolean matchesStructure(Level level, BlockPos anchor, Direction facing) {
        for (BlockPos offset : getPatternForFacing(facing)) {
            BlockPos check = anchor.offset(offset);
            if (!level.getBlockState(check).is(this)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {
        if(pLevel.isClientSide()) return;

        for(Direction facing : Direction.Plane.HORIZONTAL){
            if(matchesStructure(pLevel, pPos, facing)){
                return;
            }
        }
    }


    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if (blockstate.is(this)) {
            int i = blockstate.getValue(LAYERS);
            Minetorio.LOGGER.info("" + Integer.valueOf(Math.min(4, i + 1)));
            return blockstate.setValue(LAYERS, Integer.valueOf(Math.min(4, i + 1))).setValue(DRYING, 0).setValue(SUPPORTED, blockstate.getValue(SUPPORTED));
        } else {
            return this.defaultBlockState();
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LAYERS);
        pBuilder.add(DRYING);
        pBuilder.add(SUPPORTED);
    }
}
