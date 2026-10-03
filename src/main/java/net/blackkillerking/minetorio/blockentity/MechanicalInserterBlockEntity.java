package net.blackkillerking.minetorio.blockentity;

import net.blackkillerking.minetorio.block.blockentities.MechanicalInserterBlock;
import net.blackkillerking.minetorio.blockentity.base.InserterAccess;
import net.blackkillerking.minetorio.registry.ModBlockEntites;
import net.blackkillerking.minetorio.screen.MechanicalInserter.MechanicalInserterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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

public class MechanicalInserterBlockEntity extends BlockEntity implements MenuProvider {
    int swing_time = 10;
    int hand_size = 1;

    public MechanicalInserterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntites.MECHANICAL_INSERTER_BE.get(), pPos, pBlockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Mechanical Inserter");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MechanicalInserterMenu(pContainerId, pPlayerInventory, this);
    }

    private final IItemHandler itemHandler = new ItemStackHandler(1){
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return false;
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

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
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("swing_time", swing_time);
        pTag.putInt("hand_size", hand_size);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        swing_time = pTag.getInt("swing_time");
        hand_size = pTag.getInt("hand_size");
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
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

    public void drops(){
        if(this.level.isClientSide){
            return;
        }
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        container.setItem(0, itemHandler.getStackInSlot(0));
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    public void tick(Level level, BlockPos pos, BlockState state){
        if (level.isClientSide) return;
        if (swing_time > 0) { swing_time--; return; }

        BlockPos senderPos = getTarget(pos, state, false);
        BlockPos receiverPos = getTarget(pos, state, true);

        if(!level.isLoaded(senderPos) || !level.isLoaded(receiverPos)) return;
        if(!(level.getBlockEntity(senderPos) instanceof InserterAccess senderBE) || !(((InserterAccess)  level.getBlockEntity(senderPos)).isInserterAccessible())) return;
        if(!(level.getBlockEntity(receiverPos) instanceof InserterAccess receiverBE) || !(((InserterAccess) level.getBlockEntity(receiverPos)).isInserterAccessible())) return;

        ItemStack inserter_hand = itemHandler.getStackInSlot(0);

        if(inserter_hand.isEmpty()){
            int extraction_slot = senderBE.getTargetSlot(hand_size, receiverBE);
            if(extraction_slot == -1) return;
            ItemStack temp = new ItemStack(senderBE.getItemHandler().getStackInSlot(extraction_slot).getItem());
            senderBE.extractFromSlot(extraction_slot, 1);
            itemHandler.insertItem(0, temp, false);
        } else{
            int insertion_slot = receiverBE.getSlotToInsertOrThrow(inserter_hand);
            if(insertion_slot == -1) return;
            receiverBE.insertInSlot(insertion_slot, inserter_hand);
            itemHandler.extractItem(0, hand_size, false);
        }
        setChanged();
        swing_time = 20; //replace with animation time later

    }

    private BlockPos getTarget(BlockPos pPos, BlockState pState, boolean pIsForward) {
        Direction facing = pState.getValue(HorizontalDirectionalBlock.FACING);
        int h = pState.getValue(MechanicalInserterBlock.HORIZONTAL_REACH);
        int v = pState.getValue(MechanicalInserterBlock.VERTICAL_REACH) - 2;
        Direction dir = pIsForward ? facing : facing.getOpposite();
        return pPos.relative(dir, h).above(v);
    }
}
