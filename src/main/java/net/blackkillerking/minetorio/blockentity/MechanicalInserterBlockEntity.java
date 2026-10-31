package net.blackkillerking.minetorio.blockentity;

import net.blackkillerking.minetorio.block.blockentities.MechanicalInserterBlock;
import net.blackkillerking.minetorio.blockentity.inserter.InserterAccess;
import net.blackkillerking.minetorio.blockentity.inserter.InsertionPoint;
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
import net.minecraft.world.level.block.Block;
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
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Predicate;

public class MechanicalInserterBlockEntity extends BlockEntity implements MenuProvider, GeoBlockEntity {
    private static final int MOVE_TIME = 12;
    private static final int DELAY = 2;

    private int swing_time = 10;
    private int timer;
    private int hand_size = 1;

    private boolean isWorking;
    private Predicate<ItemStack> filter = stack -> true;

    private enum PHASE{
        IDLE,
        PICKUP,
        DROPOFF
    }
    private PHASE phase = PHASE.IDLE;


    protected static final RawAnimation SWING_ANIMATION = RawAnimation.begin().thenLoop("mechanical_inserter_swing.animation.json");
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MechanicalInserterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntites.MECHANICAL_INSERTER_BE.get(), pPos, pBlockState);
    }

    protected <E extends MechanicalInserterBlockEntity>PlayState deployAnimationController(final AnimationState<E> state){
        return state.setAndContinue(SWING_ANIMATION);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, state -> {
            if (isWorking) {
                return state.setAndContinue(SWING_ANIMATION);
            }
            return PlayState.STOP;
        }));

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
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
        if (timer > 0 && --timer > 0) return;

        InsertionPoint sender = new InsertionPoint(level, getTarget(pos, state, false));
        InsertionPoint receiver = new InsertionPoint(level, getTarget(pos, state, true));

        switch(phase){
            case IDLE -> idle(sender, receiver);
            case PICKUP -> pickup(sender, receiver);
            case DROPOFF -> dropOff(receiver);
        }
    }

    private void idle(InsertionPoint pSender, InsertionPoint pReceiver) {
        if(!itemHandler.getStackInSlot(0).isEmpty()){
            setPhase(PHASE.DROPOFF, MOVE_TIME);
        } else if(getPickup(pSender, pReceiver) != null){
            setPhase(PHASE.PICKUP, MOVE_TIME);
        } else {
            timer = DELAY;
        }
    }

    private void pickup(InsertionPoint pSender, InsertionPoint pReceiver) {
        InsertionPoint.Extractable extractable = getPickup(pSender, pReceiver);
        if(extractable == null){
            setPhase(PHASE.IDLE, DELAY);
            return;
        }

        int amount = Math.min(extractable.stack().getCount(), pReceiver.accepts(extractable.stack()));
        ItemStack taken = extractable.itemHandler().extractItem(extractable.slot(), amount, false);
        if(taken.isEmpty()){
            setPhase(PHASE.IDLE, DELAY);
        }

        itemHandler.insertItem(0, taken, false);
        setPhase(PHASE.DROPOFF, MOVE_TIME);
    }

    private void dropOff(InsertionPoint pReceiver) {
        ItemStack hand = itemHandler.extractItem(0, itemHandler.getStackInSlot(0).getCount(), false);
        if(hand.isEmpty()){
            setPhase(PHASE.IDLE, DELAY);
            return;
        }

        ItemStack remainder = pReceiver.insertStack(hand, false);
        if(!remainder.isEmpty()){
            itemHandler.insertItem(0, remainder, false);
            timer = MOVE_TIME;
            setChanged();
        } else{
            setPhase(PHASE.IDLE, DELAY);
        }
    }

    private InsertionPoint.Extractable getPickup(InsertionPoint pSender, InsertionPoint pReceiver) {
        return pSender.getExtractable(hand_size, s -> filter.test(s) && pReceiver.accepts(s) > 0);
    }

    private void setPhase(PHASE pPhase, int pTicks) {
        boolean changed = phase != pPhase;
        phase = pPhase;
        timer = pTicks;
        setChanged();
        if (changed && level != null)
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    private BlockPos getTarget(BlockPos pPos, BlockState pState, boolean pIsForward) {
        Direction facing = pState.getValue(HorizontalDirectionalBlock.FACING);
        int h = pState.getValue(MechanicalInserterBlock.HORIZONTAL_REACH);
        int v = pState.getValue(MechanicalInserterBlock.VERTICAL_REACH) - 2;
        Direction dir = pIsForward ? facing : facing.getOpposite();
        return pPos.relative(dir, h).above(v);
    }


}
