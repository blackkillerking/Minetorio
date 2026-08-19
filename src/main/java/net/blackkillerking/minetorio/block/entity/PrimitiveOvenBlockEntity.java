package net.blackkillerking.minetorio.block.entity;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.block.custom.PrimitiveOvenBlock;
import net.blackkillerking.minetorio.block.multiblock.MultiBlockPatternPart;
import net.blackkillerking.minetorio.block.multiblock.MultiBlockPattern;
import net.blackkillerking.minetorio.recipe.PrimitiveSmeltingRecipe;
import net.blackkillerking.minetorio.screen.PrimitiveOven.PrimitiveOvenMenu;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrimitiveOvenBlockEntity extends BlockEntity implements MenuProvider {

    private static final Logger log = LoggerFactory.getLogger(PrimitiveOvenBlockEntity.class);
    public Logger LOGGER = Minetorio.LOGGER;
    protected final ContainerData data;

    private boolean isContentsChanged = false;
    private int changedSlot;

    private int displayed_items = 0;
    private int result_item_count = 0;
    private ItemStack result_item = ItemStack.EMPTY;
    private int progress = 0;
    private int max_progress = 0;
    private float experience = 0;

    private int structure_check_cd = 0;

    private static final int INPUT_FUEL = 0;
    private static final int INPUT_ORE = 1;
    private static final int INPUT_FIRE_STARTER = 2;
    private static final int OUTPUT_METAL = 3;
    private final List<Integer> INPUT_SLOTS = List.of(INPUT_FUEL,INPUT_ORE);

    private final int STRUCTURE_CHECK_INTERVAL = 5;

    private static final List<BlockPos> BLOCK_PATTERN = List.of(
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

    private static final List<BlockPos> EMPTY_PATTERN = List.of(
            new BlockPos(1, 1, -1),
            new BlockPos(-1, 1, -1),
            new BlockPos(1, 1, -3),
            new BlockPos(-1, 1, -3),
            new BlockPos(0, 1, -2),
            new BlockPos(0, 2, -2),
            new BlockPos(0, 3, -2)
    );

    private static final List<BlockPos> NORTH_HOLE = List.of(
            EMPTY_PATTERN.get(4),
            EMPTY_PATTERN.get(5),
            EMPTY_PATTERN.get(6)
    );

    public static final MultiBlockPattern STRUCTURE = new MultiBlockPattern(
            new MultiBlockPatternPart(BLOCK_PATTERN, state -> state.is(Blocks.MUD)),
            new MultiBlockPatternPart(EMPTY_PATTERN, state -> state.isAir())
    );

    private final ItemStackHandler itemHandler = new ItemStackHandler(16){
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
                case 1 -> stack.is(ModTags.Items.ORES); // Ores
                case 2 -> stack.is(Items.STICK); // Fire starter
                case 3,4,5,6,7,8,9,10,11,12,13,14,15 -> false; // output and test slots
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    /// Base block entity stuff
    public PrimitiveOvenBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntites.PRIMITIVE_OVEN_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> PrimitiveOvenBlockEntity.this.progress;
                    case 1 -> PrimitiveOvenBlockEntity.this.max_progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> PrimitiveOvenBlockEntity.this.progress = pValue;
                    case 1 -> PrimitiveOvenBlockEntity.this.max_progress = pValue;
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
        return Component.literal("Primitive Oven");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PrimitiveOvenMenu(pContainerId, pPlayerInventory, this, data);
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
        pTag.putInt("displayed_items", displayed_items);
        pTag.putInt("result_item_count", result_item_count);
        pTag.putInt("progress", progress);
        pTag.putInt("max_progress", max_progress);
        pTag.putFloat("experience", experience);
        pTag.put("result_item", result_item.save(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound( "inv"));
        displayed_items = pTag.getInt("displayed_items");
        result_item_count = pTag.getInt("result_item_count");
        progress = pTag.getInt("progress");
        max_progress = pTag.getInt("max_progress");
        experience = pTag.getFloat("experience");
        result_item = ItemStack.of(pTag.getCompound("result_item"));
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
        switch (id){
            case 0 -> smeltingHandler(player);
        }
    }

    public void drops() {
        if(level.isClientSide){
            return;
        }
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(level, worldPosition, container);
        displayed_items = 0;
        result_item_count = 0;
        result_item = ItemStack.EMPTY;
        progress = 0;
        max_progress = 0;
        experience = 0;
        isContentsChanged = false;
    }


    /// Multi-Block structure checker
    private static BlockPos rotatePattern(BlockPos offset, Direction facing) {
        return switch (facing) {
            case NORTH -> offset;
            case SOUTH -> new BlockPos(-offset.getX(), offset.getY(), -offset.getZ());
            case WEST  -> new BlockPos(offset.getZ(), offset.getY(), -offset.getX());
            case EAST  -> new BlockPos(-offset.getZ(), offset.getY(), offset.getX());
            default -> offset;
        };
    }

    private boolean isValidStructure(Direction pDirection, Level pLevel, BlockPos pPos){
        return STRUCTURE.structureMatches(pLevel, pPos, pDirection);
    }


    /// Hole position for PO logic
    private List<ItemEntity> getItemEntitiesInHole(Direction pFacing, Level pLevel, BlockPos pPos){
        List<BlockPos> hole = getHolePosition(pFacing, pPos);
        BlockPos bottom = hole.get(0);
        BlockPos top = hole.get(hole.size() - 1);
        AABB holeBox = new AABB(bottom.getX(), bottom.getY(), bottom.getZ(), top.getX() + 1, top.getY() + 1, top.getZ() + 1);

        return pLevel.getEntitiesOfClass(ItemEntity.class, holeBox);
    }

    private List<BlockPos> getHolePosition(Direction pFacing, BlockPos pPos){
        List<BlockPos> referenceHole = switch (pFacing) {
            case NORTH -> NORTH_HOLE;
            case SOUTH, WEST, EAST -> NORTH_HOLE.stream().map(offset -> rotatePattern(offset, pFacing)).toList();
            default -> List.of();
        };
        List<BlockPos> hole = new ArrayList<>();
        for (BlockPos offset : referenceHole) {
            BlockPos pos = pPos.offset(offset);
            hole.add(pos);
        }
        return hole;
    }


    /// Primitive Oven logic
    public void tick(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);

        if(structure_check_cd <= 0){
            structure_check_cd = STRUCTURE_CHECK_INTERVAL;
            if(!isValidStructure(facing, level, pos)){
                LOGGER.info("Primitive oven not complete");
                drops();
                setChanged();
                level.setBlock(pos, Blocks.MUD.defaultBlockState(), 3);
                return;
            }
        } else {
            structure_check_cd--;
        }

        if(isContentsChanged){
            extractFuelOrOre(changedSlot);
        }

        List<ItemEntity> item_entities = getItemEntitiesInHole(facing, level, pos);
        if(isFuelOrOre(item_entities)){
            takeFuelOrOre(item_entities);
        }

        if(max_progress > 0){
            increaseProgress();
            if((int) level.getGameTime() % 20 == 0){
                LOGGER.info(level.getGameTime() + "");
                List<BlockPos> hole = getHolePosition(facing, pos);
                spawnParticles(hole.get(hole.size() - 1));
            }
            if(isFinished()){
                finishCraft();
            }
        }
    }


    /// Check if ingredients are dropped, add if so.
    private boolean isFuelOrOre(List<ItemEntity> item_entities) {
        for(ItemEntity item : item_entities){
            ItemStack pStack = item.getItem();
            if((itemHandler.isItemValid(INPUT_FUEL, pStack) || itemHandler.isItemValid(INPUT_ORE, pStack))) return true;
        }
        return false;
    }

    private void takeFuelOrOre(List<ItemEntity> item_entities) {
        if(level.isClientSide()) return;

        for(ItemEntity item : item_entities){
            ItemStack stack = item.getItem();
            if(canInsertItemIntoSlot(INPUT_FUEL, stack.getItem())){
                addOreOrFuel(INPUT_FUEL, stack, item);
            } else if(canInsertItemIntoSlot(INPUT_ORE, stack.getItem())){
                addOreOrFuel(INPUT_ORE, stack, item);
            }
        }
    }

    private boolean canInsertItemIntoSlot(int pSlot, Item pItem){
        ItemStack stack = itemHandler.getStackInSlot(pSlot);
        return (stack.is(pItem) && stack.getCount() + 1 <= itemHandler.getSlotLimit(pSlot)) || stack.isEmpty();
    }

    private void addOreOrFuel(int slot, ItemStack pStack, ItemEntity item){
        if (pStack.getCount() == 1) {
            item.kill();
        } else {
            item.setItem(new ItemStack(pStack.getItem(), pStack.getCount() - 1));
        }
        ItemStack pNewSlotStack = new ItemStack(pStack.getItem(), itemHandler.getStackInSlot(slot).getCount() + 1);
        itemHandler.setStackInSlot(slot, pNewSlotStack);
        LOGGER.info("Took 1 from ground item" + pStack.getItem() + ". Original amount " + pStack.getCount() + ". Filled to " + displayed_items);
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }


    /// Extract 1 item every tick
    private void extractFuelOrOre(int slot) {
        if(displayed_items >= 12) return;
        if(level.isClientSide()) return;
        ItemStack pStack = itemHandler.getStackInSlot(slot);
        if((pStack.isEmpty() || pStack.is(Items.AIR)) || !(INPUT_SLOTS.contains(slot))){
            isContentsChanged = false;
            changedSlot = -1;
            return;
        }
        itemHandler.setStackInSlot(slot, pStack.getCount()-1 <= 0 ? ItemStack.EMPTY : new ItemStack(pStack.getItem(), pStack.getCount() - 1));
        for (int i = 4; i < 16; i++) {
            if(!itemHandler.getStackInSlot(i).isEmpty()){
                continue;
            }
            itemHandler.setStackInSlot(i, new ItemStack(pStack.getItem(), 1));
            break;
        }
        displayed_items++;;
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }


    /// Start the smelting process
    private void smeltingHandler(ServerPlayer player) {
        if(player.level().isClientSide()) return;
        if(this.progress != 0){
            LOGGER.info("Another recipe is in progress, please wait");
            return;
        }
        if(displayed_items <= 1){
            LOGGER.info("Not enough items for any recipe");
            return;
        }
        if(itemHandler.getStackInSlot(INPUT_FIRE_STARTER).isEmpty()){
            LOGGER.info("No fire starter");
            return;
        }

        if(startCraftOfSize(2)) return;
        if(startCraftOfSize(3)) return;
        if(startCraftOfSize(4)) return;

        LOGGER.info("No recipe was found");
    }

    private boolean startCraftOfSize(int size){
        if(!foundRecipeOfSize(size)) return false;
        LOGGER.info(getRecipeOfSize(size).get().getIngredients().toString());
        consumeRecipeOfSize(size, getRecipeOfSize(size));

        for (int i = 0; i < 12/size; i++) {
            if(!foundRecipeOfSize(size) || !canOutput()) break;
            consumeRecipeOfSize(size, getRecipeOfSize(size));
        }
        return true;
    }

    private void finishCraft(){
        ItemStack resultStack = new ItemStack(result_item.getItem(), result_item_count);

        if(result_item.hasTag()){
            CompoundTag data = new CompoundTag();
            data.putString("metal_type", result_item.getTag().getString("metal_type"));
            data.putInt("cooling_time", result_item.getTag().getInt("cooling_time"));
            resultStack.setTag(data);
        }

        resultStack.grow(itemHandler.getStackInSlot(OUTPUT_METAL).getCount());
        itemHandler.setStackInSlot(OUTPUT_METAL, resultStack);

        if(level instanceof ServerLevel serverLevel){
            ExperienceOrb.award(serverLevel, Vec3.atCenterOf(getBlockPos()), Mth.ceil(experience));
        }

        result_item = ItemStack.EMPTY;
        result_item_count = 0;
        experience = 0;
        max_progress = 0;
        resetProgress();
        if (getBlockState().getValue(PrimitiveOvenBlock.ON)) {
            level.setBlock(worldPosition, getBlockState().setValue(PrimitiveOvenBlock.ON, false), 3);
        }
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    private boolean foundRecipeOfSize(int size){
        Optional<PrimitiveSmeltingRecipe> recipe = getRecipeOfSize(size);
        LOGGER.info("Getting recipe");
        return !recipe.isEmpty();
    }

    private void consumeRecipeOfSize(int size, Optional<PrimitiveSmeltingRecipe> recipe){
        LOGGER.info("Consuming recipe");
        for (int i = 4; i < 4 + size; i++) {
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }

        for (int i = 0;i < itemHandler.getSlots() - size - 4; i++) {
            ItemStack temp = itemHandler.getStackInSlot(4 + size + i);
            if(temp.isEmpty()) break;
            itemHandler.setStackInSlot(4 + size + i, ItemStack.EMPTY);
            itemHandler.setStackInSlot(4 + i, temp);
        }
        displayed_items -= size;
        result_item_count++;
        max_progress += recipe.get().getCookingTime();
        experience += recipe.get().getExperience();
        result_item = recipe.get().getResultItem(getLevel().registryAccess());
        if (!getBlockState().getValue(PrimitiveOvenBlock.ON)) {
            level.setBlock(worldPosition, getBlockState().setValue(PrimitiveOvenBlock.ON, true), 3);
        }
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    private boolean canOutput(){
        ItemStack outputSlotStack = itemHandler.getStackInSlot(OUTPUT_METAL);

        if(outputSlotStack.isEmpty()) return true;
        if(!(ItemStack.isSameItemSameTags(result_item, outputSlotStack))) return false;

        return outputSlotStack.getCount() + result_item_count + 1 <= outputSlotStack.getMaxStackSize();
    }

    private Optional<PrimitiveSmeltingRecipe> getRecipeOfSize(int size) {
        SimpleContainer inv = new SimpleContainer(4);
        for (int i = 0; i < size; i++) {
            inv.setItem(i, itemHandler.getStackInSlot(4+i));
        }
        if(size < 4){
            if(size == 3){
                inv.setItem(3, ItemStack.EMPTY);
            }
            if(size == 2){
                inv.setItem(2, ItemStack.EMPTY);
                inv.setItem(3, ItemStack.EMPTY);
            }
        }

        return level.getRecipeManager().getRecipeFor(PrimitiveSmeltingRecipe.Type.INSTANCE, inv, level);
    }


    /// Progressing progress
    private boolean isFinished(){
        return progress >= max_progress;
    }

    private void increaseProgress() {
        progress++;
    }

    private void resetProgress(){
        progress = 0;
    }

    private void spawnParticles(BlockPos pPos){
        if(level.isClientSide()) return;
        level.addParticle(ParticleTypes.LAVA, pPos.getX() + 0.1, pPos.getY() + 0.5, pPos.getZ() + 0.1, 0.02, 0.05, 0.02);
    }


    public ItemStack getItemInSlot(int slot){
        return itemHandler.getStackInSlot(slot);
    }
}
