package net.blackkillerking.minetorio.block.entity;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.block.custom.KilnControllerBlock;
import net.blackkillerking.minetorio.block.multiblock.MultiBlockPattern;
import net.blackkillerking.minetorio.block.multiblock.MultiBlockPatternPart;
import net.blackkillerking.minetorio.recipe.KilnSmeltingRecipe;
import net.blackkillerking.minetorio.screen.KilnController.DefaultKilnControllerMenu;
import net.blackkillerking.minetorio.screen.KilnController.FormedKilnControllerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KilnControllerBlockEntity extends BlockEntity implements MenuProvider {

    public Logger LOGGER = Minetorio.LOGGER;
    protected final ContainerData data;

    private int progress = 0;
    private boolean is_formed = false;

    private int structure_check_cd = 0;
    private List<ItemStack> result_blocks = new ArrayList<>();

    private static final int INPUT_FIRE_STARTER = 0;
    private final int MAX_PROGRESS = 4800;

    private final int STRUCTURE_CHECK_INTERVAL = 5;

    private static final List<BlockPos> BLOCK_PATTERN = List.of(
            new BlockPos(0, -1, -1),
            new BlockPos(-1, -1, -2),
            new BlockPos(0, -1, -2),
            new BlockPos(1, -1, -2),
            new BlockPos(-2, -1, -3),
            new BlockPos(-1, -1, -3),
            new BlockPos(0, -1, -3),
            new BlockPos(1, -1, -3),
            new BlockPos(2, -1, -3),
            new BlockPos(-1, -1, -4),
            new BlockPos(0, -1, -4),
            new BlockPos(1, -1, -4),
            new BlockPos(0, -1, -5),

            new BlockPos(-1, 0, -1),
            new BlockPos(0, 0, -1),
            new BlockPos(1, 0, -1),
            new BlockPos(-1, 1, -1),
            new BlockPos(0, 1, -1),
            new BlockPos(1, 1, -1),
            new BlockPos(0, 2, -1),

            new BlockPos(-1, 0, -5),
            new BlockPos(1, 0, -5),
            new BlockPos(-1, 1, -5),
            new BlockPos(1, 1, -5),
            new BlockPos(0, 2, -5),

            new BlockPos(2, 0, -2),
            new BlockPos(2, 0, -4),
            new BlockPos(2, 1, -2),
            new BlockPos(2, 1, -4),
            new BlockPos(2, 2, -3),

            new BlockPos(-2, 0, -2),
            new BlockPos(-2, 0, -4),
            new BlockPos(-2, 1, -2),
            new BlockPos(-2, 1, -4),
            new BlockPos(-2, 2, -3),

            new BlockPos(-1, 2, -2),
            new BlockPos(0, 2, -2),
            new BlockPos(1, 2, -2),
            new BlockPos(-1, 2, -3),
            new BlockPos(1, 2, -3),
            new BlockPos(-1, 2, -4),
            new BlockPos(0, 2, -4),
            new BlockPos(1, 2, -4),

            new BlockPos(0, 3, -2),
            new BlockPos(-1, 3, -3),
            new BlockPos(1, 3, -3),
            new BlockPos(0, 3, -4),

            new BlockPos(0, 4, -2),
            new BlockPos(-1, 4, -3),
            new BlockPos(1, 4, -3),
            new BlockPos(0, 4, -4)


    );

    private static final List<BlockPos> EXAHST_PATTERN = List.of(
            new BlockPos(0, 4, -3)
    );

    private static final List<BlockPos> WOOL_PATTERN = List.of(
            new BlockPos(0, 2, -3),
            new BlockPos(0, 3, -3)
    );

    private static final List<BlockPos> BRICK_DOOR_OR_BRICK_PATTERN = List.of(
            new BlockPos(2, 0, -3),
            new BlockPos(2, 1, -3),
            new BlockPos(-2, 0, -3),
            new BlockPos(-2, 1, -3),
            new BlockPos(0, 0, -5),
            new BlockPos(0, 1, -5)
    );

    private static final List<BlockPos> NORTH_INPUT_BOX = List.of(
            new BlockPos(-1, 0, -2),
            new BlockPos(-1, 1, -2),

            new BlockPos(0, 0, -2),
            new BlockPos(0, 1, -2),

            new BlockPos(1, 0, -2),
            new BlockPos(1, 1, -2),

            new BlockPos(-1, 0, -3),
            new BlockPos(-1, 1, -3),

            new BlockPos(0, 0, -3),
            new BlockPos(0, 1, -3),

            new BlockPos(1, 0, -3),
            new BlockPos(1, 1, -3),

            new BlockPos(-1, 0, -4),
            new BlockPos(-1, 1, -4),

            new BlockPos(0, 0, -4),
            new BlockPos(0, 1, -4),

            new BlockPos(1, 0, -4),
            new BlockPos(1, 1, -4)
    );

    public static final MultiBlockPattern KILN_BASE_STRUCTURE = new MultiBlockPattern(
            new MultiBlockPatternPart(BLOCK_PATTERN, state -> state.is(Blocks.BRICKS)),
            new MultiBlockPatternPart(EXAHST_PATTERN, state -> state.isAir()),
            new MultiBlockPatternPart(WOOL_PATTERN, state -> state.is(Blocks.WHITE_WOOL)),
            new MultiBlockPatternPart(BRICK_DOOR_OR_BRICK_PATTERN, state -> state.is(Blocks.BRICKS) || state.is(Blocks.CRIMSON_DOOR))
    );

    private final ItemStackHandler itemHandler = new ItemStackHandler(3){
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot){
                case 0 -> stack.is(Items.STICK); // Fire starter
                case 1,2 -> false; // test slots
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public KilnControllerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntites.KILN_CONTROLLER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> KilnControllerBlockEntity.this.progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> KilnControllerBlockEntity.this.progress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Kiln Oven");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        if(is_formed){
            getRecipes(getInputBox(this.getBlockState().getValue(HorizontalDirectionalBlock.FACING), this.getBlockPos()));
            return new FormedKilnControllerMenu(pContainerId, pPlayerInventory, this);
        } else {
            return new DefaultKilnControllerMenu(pContainerId, pPlayerInventory, this);
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inv", itemHandler.serializeNBT());
        pTag.putInt("progress", progress);
        pTag.putBoolean("is_formed", is_formed);

        ListTag listTag = new ListTag();
        for (ItemStack stack : result_blocks) {
            CompoundTag itemTag = new CompoundTag();
            stack.save(itemTag);
            listTag.add(itemTag);
        }
        pTag.put("recipe_blocks", listTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound( "inv"));
        progress = pTag.getInt("progress");
        is_formed = pTag.getBoolean("is_formed");

        result_blocks.clear();
        ListTag listTag = pTag.getList("result_blocks", Tag.TAG_COMPOUND);
        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag itemTag = listTag.getCompound(i);
            result_blocks.add(ItemStack.of(itemTag));
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    public void handleButtonPress(int id, ServerPlayer player) {
        switch (id) {
            case 0 -> startRecipes();
        }
    }

    public void drops() {
        if(this.level.isClientSide){
            return;
        }
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        container.setItem(0, itemHandler.getStackInSlot(0));
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    private static BlockPos rotatePattern(BlockPos offset, Direction facing) {
        return switch (facing) {
            case NORTH -> offset;
            case SOUTH -> new BlockPos(-offset.getX(), offset.getY(), -offset.getZ());
            case WEST  -> new BlockPos(offset.getZ(), offset.getY(), -offset.getX());
            case EAST  -> new BlockPos(-offset.getZ(), offset.getY(), offset.getX());
            default -> offset;
        };
    }

    private List<BlockPos> getInputBox(Direction pFacing, BlockPos pAnchor){
        List<BlockPos> referenceBox = switch (pFacing) {
            case NORTH -> NORTH_INPUT_BOX;
            case SOUTH, WEST, EAST -> NORTH_INPUT_BOX.stream().map(offset -> rotatePattern(offset, pFacing)).toList();
            default -> List.of();
        };
        List<BlockPos> box = new ArrayList<>();
        for (BlockPos offset : referenceBox) {
            BlockPos pos = pAnchor.offset(offset);
            box.add(pos);
        }
        return box;
    }

    private void getRecipes(List<BlockPos> box){
        result_blocks.clear();
        SimpleContainer testInv = new SimpleContainer(2);
        for (int i = 0; i < box.size(); i+=2) {
            testInv.setItem(1, new ItemStack(level.getBlockState(box.get(i)).getBlock()));
            testInv.setItem(2, new ItemStack(level.getBlockState(box.get(i+1)).getBlock()));
            Optional<KilnSmeltingRecipe> recipe = level.getRecipeManager().getRecipeFor(KilnSmeltingRecipe.Type.INSTANCE, testInv, level);
            if(recipe.isEmpty()){
                testInv.clearContent();
                result_blocks.add(new ItemStack(ModBlocks.ASH_BLOCK.get()));
                result_blocks.add(new ItemStack(ModBlocks.ASH_BLOCK.get()));
                continue;
            }
            testInv.clearContent();
            result_blocks.add(new ItemStack(ModBlocks.ASH_BLOCK.get()));
            result_blocks.add(recipe.get().getResultItem(level.registryAccess()));

        }
        setChanged();
    }

    private void startRecipes(){
        if(getBlockState().getValue(KilnControllerBlock.ON)) return;
        if(itemHandler.getStackInSlot(INPUT_FIRE_STARTER).isEmpty()) return;

        itemHandler.extractItem(INPUT_FIRE_STARTER, 1, false);
        level.setBlock(getBlockPos(), ModBlocks.KILN_CONTROLLER.get().defaultBlockState().setValue(KilnControllerBlock.ON, true), 3);
        finishRecipes(getInputBox(this.getBlockState().getValue(HorizontalDirectionalBlock.FACING), this.getBlockPos()));
    }

    private void finishRecipes(List<BlockPos> box){
        for (int i = 0; i < box.size(); i++) {
            BlockState blockState = ((BlockItem) result_blocks.get(i).getItem()).getBlock().defaultBlockState();
            level.setBlock(box.get(i), blockState, 3);
        }
        level.setBlock(getBlockPos(), ModBlocks.KILN_CONTROLLER.get().defaultBlockState().setValue(KilnControllerBlock.ON, false), 3);
        result_blocks.clear();
        resetProgress();
    }

    private boolean isValidStructure(Level pLevel, BlockPos pPos, Direction pDirection){
        return KILN_BASE_STRUCTURE.structureMatches(pLevel, pPos, pDirection);
    }

    private boolean isFinished(){
        return progress >= MAX_PROGRESS;
    }

    private void increaseProgress() {
        progress++;
    }

    private void resetProgress(){
        progress = 0;
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if(level.isClientSide()) return;
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);

        if(structure_check_cd <= 0){
            structure_check_cd = STRUCTURE_CHECK_INTERVAL;
            if(!isValidStructure(level, pos, facing)){
                LOGGER.info("Kiln is not complete");
                is_formed = false;

                drops();
                resetProgress();
                setChanged();
                return;
            }
            is_formed = true;
            setChanged();
        } else {
            structure_check_cd--;
        }
        if(getBlockState().getValue(KilnControllerBlock.ON)){
            increaseProgress();
            if(isFinished()){
                finishRecipes(getInputBox(this.getBlockState().getValue(HorizontalDirectionalBlock.FACING), this.getBlockPos()));
            }
        }



    }
}
