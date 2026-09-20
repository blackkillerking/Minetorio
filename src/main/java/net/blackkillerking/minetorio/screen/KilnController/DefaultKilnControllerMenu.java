package net.blackkillerking.minetorio.screen.KilnController;

import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.block.entity.KilnControllerBlockEntity;
import net.blackkillerking.minetorio.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DefaultKilnControllerMenu extends AbstractContainerMenu {

    public final KilnControllerBlockEntity blockEntity;
    private final Level level;

    public DefaultKilnControllerMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }
    public DefaultKilnControllerMenu(int pContainerId, Inventory inv , BlockEntity blockEntity) {
        super(ModMenuTypes.DEFAULT_KILN_CONTROLLER_MENU.get(), pContainerId);
        checkContainerSize(inv, 3);
        this.blockEntity = (KilnControllerBlockEntity) blockEntity;
        this.level = inv.player.level();
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.KILN_CONTROLLER.get());
    }
}
