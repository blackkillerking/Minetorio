package net.blackkillerking.minetorio.block.entity;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.recipe.MetalShapingRecipe;
import net.blackkillerking.minetorio.screen.MetalShapingStationMenu;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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
                case 2 -> stack.is(ModTags.Items.HEATED_METALS); // Heated metal input
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
        updateOutputResult();
        if (hasRecipe() && canCraft()) {
            setChanged(level, pos, state);
        }
    }

    private Optional<MetalShapingRecipe> prevRecipe;
    public void updateOutputResult() {
        ItemStack output = this.itemHandler.getStackInSlot(OUTPUT_SHAPED_METAL);
        Optional<MetalShapingRecipe> currentRecipe = getRecipe();

        if(hasRecipe()){
            if(output.isEmpty()){
                ItemStack result = getRecipe().get().getResultItem(getLevel().registryAccess());
                this.itemHandler.setStackInSlot(OUTPUT_SHAPED_METAL, result);
            } else if(prevRecipe != currentRecipe){
                ItemStack result = currentRecipe.get().getResultItem(getLevel().registryAccess());
                this.itemHandler.setStackInSlot(OUTPUT_SHAPED_METAL, result);
            }

        } else if(!hasRecipe() && !output.isEmpty()) {
            this.itemHandler.setStackInSlot(OUTPUT_SHAPED_METAL, ItemStack.EMPTY);
        }
        prevRecipe = currentRecipe;
    }

    private boolean hasRecipe() {
        return getRecipe().isPresent();
    }

    private boolean canCraft() {
        Optional<MetalShapingRecipe> recipe = getRecipe();

        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());
        ItemStack output = this.itemHandler.getStackInSlot(OUTPUT_SHAPED_METAL);

        if (output.isEmpty()) return true;
        if (!ItemStack.isSameItemSameTags(output, result)) return false;
        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    public void craftItem() {
        Minetorio.LOGGER.info("Crafting Item");
        Optional<MetalShapingRecipe> recipe = getRecipe();
        if(recipe.isEmpty()) return;
        int extractCount = recipe.get().getIngredients().get(INPUT_HEATED_METAL).getItems()[0].getCount();

        hurtToolsOrBreak();
        this.itemHandler.extractItem(INPUT_HEATED_METAL, extractCount, false);
    }

    private void hurtToolsOrBreak(){
        ItemStack toolOne = this.itemHandler.getStackInSlot(INPUT_TOOL_ONE);
        ItemStack toolTwo = this.itemHandler.getStackInSlot(INPUT_TOOL_TWO);

        ItemStack bluntToolOne = toolOne.getItem().hasCraftingRemainingItem(toolOne)
                ? toolOne.getItem().getCraftingRemainingItem(toolOne)
                : toolOne;
        ItemStack bluntToolTwo = toolTwo.getItem().hasCraftingRemainingItem(toolTwo)
                ? toolTwo.getItem().getCraftingRemainingItem(toolTwo)
                : toolTwo;

        this.itemHandler.setStackInSlot(INPUT_TOOL_ONE, bluntToolOne);
        this.itemHandler.setStackInSlot(INPUT_TOOL_TWO, bluntToolTwo);
    }

    private Optional<MetalShapingRecipe> getRecipe() {
        SimpleContainer inv = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            inv.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(MetalShapingRecipe.Type.INSTANCE, inv, level);
    }
}
