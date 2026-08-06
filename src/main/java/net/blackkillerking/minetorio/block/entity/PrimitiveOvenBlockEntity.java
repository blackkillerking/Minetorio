package net.blackkillerking.minetorio.block.entity;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.block.custom.PrimitiveOvenBlock;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.screen.PrimitiveOven.PrimitiveOvenMenu;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrimitiveOvenBlockEntity extends BlockEntity implements MenuProvider {

    private static final Logger log = LoggerFactory.getLogger(PrimitiveOvenBlockEntity.class);
    private boolean isContentsChanged = false;
    private int changedSlot;
    private final ItemStackHandler itemHandler = new ItemStackHandler(5){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            isContentsChanged = true;
            changedSlot = slot;
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot){
                case 0 -> stack.is(Items.COAL); // Fuel
                case 1,2 -> stack.is(ModTags.Items.ORES); // Ores
                case 3 -> stack.is(Items.STICK); // Fire starter
                case 4 -> false; // Output
                default -> super.isItemValid(slot, stack);
            };
        }
    };



    private List<ItemStack> INSERTED_ITEMS = new ArrayList<>();
    private int DISPLAYED_ITEMS = 0;
    private List<ItemStack> QUEUED_ITEMS = new ArrayList<>();
    private List<Integer> INPUT_SLOTS = List.of(INPUT_FUEL,INPUT_ORE_ONE,INPUT_ORE_TWO);

    private static final int INPUT_FUEL = 0;
    private static final int INPUT_ORE_ONE = 1;
    private static final int INPUT_ORE_TWO = 2;
    private static final int INPUT_FIRE_STARTER = 3;
    private static final int OUTPUT_HEATED_INGOT_ALLOY = 4;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public PrimitiveOvenBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntites.PRIMITIVE_OVEN_BE.get(), pPos, pBlockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Primitive Oven");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PrimitiveOvenMenu(pContainerId, pPlayerInventory, this);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
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
        pTag.putInt("displayed_items", DISPLAYED_ITEMS);

        ListTag inserted_items = new ListTag();
        for (ItemStack stack : INSERTED_ITEMS) {
            CompoundTag entry = new CompoundTag();
            stack.save(entry);
            inserted_items.add(entry);
        }
        pTag.put("inserted_items", inserted_items);

        ListTag queued_items = new ListTag();
        for (ItemStack stack : QUEUED_ITEMS) {
            CompoundTag entry = new CompoundTag();
            stack.save(entry);
            inserted_items.add(entry);
        }
        pTag.put("queued_items", queued_items);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound( "inv"));
        DISPLAYED_ITEMS = pTag.getInt("displayed_items");

        INSERTED_ITEMS.clear();
        ListTag inserted_items = pTag.getList("inserted_items", Tag.TAG_COMPOUND);
        for (int i = 0; i < inserted_items.size(); i++) {
            ItemStack stack = ItemStack.of(inserted_items.getCompound(i));
            INSERTED_ITEMS.add(stack);
        }

        QUEUED_ITEMS.clear();
        ListTag queued_items = pTag.getList("queued_items", Tag.TAG_COMPOUND);
        for (int i = 0; i < queued_items.size(); i++) {
            ItemStack stack = ItemStack.of(queued_items.getCompound(i));
            QUEUED_ITEMS.add(stack);
        }
    }


    public void drops() {
        if(this.level.isClientSide){
            return;
        }
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots()-1; i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);

        if(!isValidStructure(facing, level, pos)){
            Minetorio.LOGGER.info("Primitive oven not complete");
            level.setBlock(pos, ModBlocks.MUD_BLOCK.get().defaultBlockState(), 3);
            drops();
            setChanged();
        }

        if(level.isClientSide()) return;
        if(isContentsChanged){
            extractFuelOrOre(changedSlot);
        }

        if(isFuelOrOre(facing, level)){
            takeFuelOrOre(facing, level);
        }
    }

    private void extractFuelOrOre(int slot) {
        if(this.level.isClientSide()) return;
        if(!(INPUT_SLOTS.contains(slot))) return;
        if(DISPLAYED_ITEMS >= 12) return;
        ItemStack pStack = itemHandler.getStackInSlot(slot);
        if(pStack.isEmpty() || pStack.is(Items.AIR)){
            isContentsChanged = false;
            changedSlot = -1;
        };
        DISPLAYED_ITEMS++;
        itemHandler.extractItem(slot, 1, false);
        INSERTED_ITEMS.add(pStack);
        Minetorio.LOGGER.info("Took 1 from " + pStack.getItem() + ". Original amount " + pStack.getCount() + ". Filled to " + DISPLAYED_ITEMS);
        setChanged();
    }

    private boolean isFuelOrOre(Direction pFacing, Level pLevel) {
        if(this.level.isClientSide()) return false;

        for(ItemEntity item : getItemEntitiesInHole(pFacing, pLevel)){
            ItemStack pStack = item.getItem();
            if((itemHandler.isItemValid(INPUT_FUEL, pStack) || itemHandler.isItemValid(INPUT_ORE_ONE, pStack))) return true;
        }
        return false;
    }

    private void takeFuelOrOre(Direction pFacing, Level pLevel) {
        if(this.level.isClientSide()) return;
        if(DISPLAYED_ITEMS >= 12) return;

        for(ItemEntity item : getItemEntitiesInHole(pFacing, pLevel)){
            ItemStack pStack = item.getItem();
            if(itemHandler.getStackInSlot(INPUT_FUEL).getCount() + 1 <= itemHandler.getSlotLimit(INPUT_FUEL) && itemHandler.isItemValid(INPUT_FUEL, pStack)){
                addOreOrFuel(INPUT_FUEL, pStack, item);
            } else if((itemHandler.getStackInSlot(INPUT_ORE_ONE).getCount() + 1 <= itemHandler.getSlotLimit(INPUT_ORE_ONE) || (itemHandler.getStackInSlot(INPUT_ORE_TWO).getCount() + 1 <= itemHandler.getSlotLimit(INPUT_ORE_TWO)) && itemHandler.isItemValid(INPUT_ORE_ONE, pStack))){
                addOreOrFuel(itemHandler.getStackInSlot(INPUT_ORE_ONE).getCount() + 1 <= itemHandler.getSlotLimit(INPUT_ORE_ONE) ? INPUT_ORE_ONE : INPUT_ORE_TWO, pStack, item);
            }
        }
    }

    private List<ItemEntity> getItemEntitiesInHole(Direction facing, Level pLevel){
        List<BlockPos> hole = getHolePosition(facing);
        BlockPos bottom = hole.get(0);
        BlockPos top = hole.get(hole.size() - 1);
        AABB holeBox = new AABB(bottom, top);
        return pLevel.getEntitiesOfClass(ItemEntity.class, holeBox);
    }

    private List<BlockPos> getHolePosition(Direction facing){
        return switch (facing) {
            case NORTH -> NORTH_HOLE;
            case SOUTH -> SOUTH_HOLE;
            case EAST -> EAST_HOLE;
            case WEST -> WEST_HOLE;
            default -> List.of();
        };
    }

    private void addOreOrFuel(int slot, ItemStack pStack, ItemEntity item){
        if(pStack.getCount() == 1){
            item.kill();
        } else {
            ItemStack pNewStack = new ItemStack(pStack.getItem(), pStack.getCount() - 1);
            ItemEntity pNewItem = new ItemEntity(level, item.getX(), item.getY(), item.getZ(), pNewStack);
            level.addFreshEntity(pNewItem);
        }
        ItemStack pNewSlotStack = new ItemStack(pStack.getItem(), itemHandler.getStackInSlot(slot).getCount() + 1);
        DISPLAYED_ITEMS++;
        INSERTED_ITEMS.add(pStack);
        itemHandler.setStackInSlot(slot, pNewSlotStack);
        Minetorio.LOGGER.info("Took 1 from ground item" + pStack.getItem() + ". Original amount " + pStack.getCount() + ". Filled to " + DISPLAYED_ITEMS);
        setChanged();
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

    private static final List<BlockPos> NORTH_HOLE = List.of(
            new BlockPos(0, 1, -2),
            new BlockPos(0, 2, -2),
            new BlockPos(0, 3, -2)
    );

    private static final List<BlockPos> NORTH_EMPTY_PATTERN = List.of(
            new BlockPos(1, 1, -1),
            new BlockPos(-1, 1, -1),
            new BlockPos(1, 1, -3),
            new BlockPos(-1, 1, -3),
            NORTH_HOLE.get(0),
            NORTH_HOLE.get(1),
            NORTH_HOLE.get(2)
    );

    private static final List<BlockPos> SOUTH_BLOCK_PATTERN = NORTH_BLOCK_PATTERN.stream().map(offset -> rotate(offset, Direction.SOUTH)).toList();
    private static final List<BlockPos> EAST_BLOCK_PATTERN = NORTH_BLOCK_PATTERN.stream().map(offset -> rotate(offset, Direction.EAST)).toList();
    private static final List<BlockPos> WEST_BLOCK_PATTERN = NORTH_BLOCK_PATTERN.stream().map(offset -> rotate(offset, Direction.WEST)).toList();

    private static final List<BlockPos> SOUTH_HOLE = NORTH_HOLE.stream().map(offset -> rotate(offset, Direction.SOUTH)).toList();
    private static final List<BlockPos> EAST_HOLE = NORTH_HOLE.stream().map(offset -> rotate(offset, Direction.EAST)).toList();
    private static final List<BlockPos> WEST_HOLE = NORTH_HOLE.stream().map(offset -> rotate(offset, Direction.WEST)).toList();

    private static final List<BlockPos> SOUTH_EMPTY_PATTERN = NORTH_EMPTY_PATTERN.stream().map(offset -> rotate(offset, Direction.SOUTH)).toList();
    private static final List<BlockPos> EAST_EMPTY_PATTERN = NORTH_EMPTY_PATTERN.stream().map(offset -> rotate(offset, Direction.EAST)).toList();
    private static final List<BlockPos> WEST_EMPTY_PATTERN = NORTH_EMPTY_PATTERN.stream().map(offset -> rotate(offset, Direction.WEST)).toList();

    private static BlockPos rotate(BlockPos offset, Direction facing) {
        return switch (facing) {
            case NORTH -> offset;
            case SOUTH -> new BlockPos(-offset.getX(), offset.getY(), -offset.getZ());
            case WEST  -> new BlockPos(offset.getZ(), offset.getY(), -offset.getX());
            case EAST  -> new BlockPos(-offset.getZ(), offset.getY(), offset.getX());
            default -> offset;
        };
    }

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
}
