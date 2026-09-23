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

public class KilnControllerScreen extends AbstractContainerScreen<KilnControllerMenu> {

    private static final ResourceLocation FORMED_TEXTURE =
            new ResourceLocation(Minetorio.MOD_ID, "textures/gui/formed_kiln_controller_gui.png");
    private static final ResourceLocation DEFAULT_TEXTURE =
            new ResourceLocation(Minetorio.MOD_ID, "textures/gui/default_kiln_controller_gui.png");

    public KilnControllerScreen(KilnControllerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
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
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void init() {
        super.init();

        int x = leftPos;
        int y = topPos;

        addRenderableWidget(Button.builder(
                Component.literal("Start Kiln"),
                button -> this.minecraft.player.connection.send(new ServerboundContainerButtonClickPacket(menu.containerId, 0))
        ).bounds(x + 112, y + 51, 60, 20).build());
    }
}
