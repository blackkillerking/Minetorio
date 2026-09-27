package net.blackkillerking.minetorio.screen.KilnController;

import com.mojang.blaze3d.systems.RenderSystem;
import net.blackkillerking.minetorio.Minetorio;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class KilnControllerScreen extends AbstractContainerScreen<KilnControllerMenu> {

    private static final ResourceLocation FORMED_TEXTURE =
            new ResourceLocation(Minetorio.MOD_ID, "textures/gui/formed_kiln_controller_gui.png");
    private static final ResourceLocation DEFAULT_TEXTURE =
            new ResourceLocation(Minetorio.MOD_ID, "textures/gui/default_kiln_controller_gui.png");
    private Button start_kiln;
    private Button switch_layer;

    public KilnControllerScreen(KilnControllerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        inventoryLabelY = 10000;

        int x = leftPos;
        int y = topPos;

        start_kiln = Button.builder(
                Component.literal("Start Kiln"),
                button -> this.minecraft.player.connection.send(new ServerboundContainerButtonClickPacket(menu.containerId, 0))
        ).bounds(x + 112, y + 60, 60, 20).build();

        switch_layer = Button.builder(
                Component.literal("Switch Layer"),
                button -> this.minecraft.player.connection.send(new ServerboundContainerButtonClickPacket(menu.containerId, 1))
        ).bounds(x + 5, y + 60, 60, 20).build();

    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        ResourceLocation CURRENT_TEXTURE = menu.isFormed() ? FORMED_TEXTURE : DEFAULT_TEXTURE;
        RenderSystem.setShaderTexture(0, CURRENT_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(CURRENT_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        if (menu.isFormed()) {
            addRenderableWidget(start_kiln);
            addRenderableWidget(switch_layer);
            renderRecipe(pGuiGraphics, x, y);
        } else {
            removeWidget(start_kiln);
            removeWidget(switch_layer);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    private void renderRecipe(GuiGraphics pGuiGraphics, int x, int y){
        List<ItemStack> stacks = menu.getRecipeVisual();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pGuiGraphics.renderItem(stacks.get(i*3+j+(9*menu.getLayer())), x+60+i*16, y+5+j*16);
            }
        }
    }
}
