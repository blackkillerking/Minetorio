package net.blackkillerking.minetorio.block.entity;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.datagen.ModItemTagGenerator;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.screen.MetalShapingStationMenu;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.basic.BasicComboBoxUI;

public class MetalShapingStationBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(4){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot){
                case 0, 1 -> stack.is(ModTags.Items.METAL_WORKING_TOOLS); // Tools input
                case 2 -> stack.getItem() == ModItems.HEATED_METAL.get(); // Heated metal input
                case 3 -> false; // Output
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static final int INPUT_TOOL_ONE = 0;
    private static final int INPUT_TOOL_TWO = 1;
    private static final int INPUT_HEATED_METAL = 2;
    private static final int OUTPUT_SHAPED_METAL = 3;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    public MetalShapingStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntites.METAL_SHAPING_STATION_BE.get(), pPos, pBlockState);

    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Metal Shaping Station");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        Minetorio.LOGGER.info("Menu Method Called");
        return new MetalShapingStationMenu(pContainerId, pPlayerInventory, this);
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
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound( "inv"));
    }

    public void tick(Level level, BlockPos pos, BlockState state) {

    }

    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, container);
    }
}
