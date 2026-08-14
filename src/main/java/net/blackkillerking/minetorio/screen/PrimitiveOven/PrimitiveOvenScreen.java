package net.blackkillerking.minetorio.screen.PrimitiveOven;

import com.mojang.blaze3d.systems.RenderSystem;
import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.entity.PrimitiveOvenBlockEntity;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.network.ButtonPacket;
import net.blackkillerking.minetorio.network.ModNetwork;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.Map;

public class PrimitiveOvenScreen extends AbstractContainerScreen<PrimitiveOvenMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Minetorio.MOD_ID, "textures/gui/primitive_oven_gui.png");

    private Map<Item, Integer> renderableIndex = Map.of(
            Items.COAL, 0,
            Items.RAW_COPPER, 1,
            ModItems.RAW_TIN.get(), 2,
            Items.RAW_IRON, 3,
            //ModItems.BRONZE_INGOT.get(), 5,
            ModItems.RAW_ZINC.get(), 5,
            ModItems.RAW_SILVER.get(), 6
    );

    public PrimitiveOvenScreen(PrimitiveOvenMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    private int getIndex(Item item){
        return renderableIndex.getOrDefault(item, 1);
    }

    private static final int LIST_X = 35;   // relative to leftPos — right edge of main texture
    private static final int LIST_Y = 18;     // relative to topPos — matches red box's vertical start
    private static final int ROW_HEIGHT = 6;
    private static final int ROW_WIDTH = 68; // adjust to match red box's actual width
    private static final int MAX_ROWS = 7;

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        this.imageHeight = 210;
        this.titleLabelY = -16;
        this.inventoryLabelY = 90;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderFireProgress(pGuiGraphics, x, y);
        renderInputLayers(pGuiGraphics, x, y);
    }

    private void renderInputLayers(GuiGraphics pGuiGraphics, int x, int y) {
        List<ItemStack> input_list = menu.getInputList();
        if(input_list.isEmpty()) return;
        for(int i = 0; i < input_list.size()-1; i++){
            ItemStack stack = input_list.get(i);
            if(stack.isEmpty()) break;
            pGuiGraphics.blit(TEXTURE, x + 35, y + 86 - 6 * i, x + 176, y + 31 + 6 * getIndex(stack.getItem()), 68, 6);
        }
    }

    private void renderFireProgress(GuiGraphics pGuiGraphics, int x, int y) {
        if(menu.isCrafting()){
            for (int i = 0; i < 4; i++) {
                pGuiGraphics.blit(TEXTURE, x + 34 + 16 * i, y + 95, x + 176, 0, 13,menu.getScaledProgress());
            }

        }
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
                Component.literal("Start fire"),
                button -> onButtonPress(0)
        ).bounds(x + 112, y + 51, 60, 20).build());
    }

    private void onButtonPress(int id) {
        ModNetwork.INSTANCE.sendToServer(new ButtonPacket(menu.blockEntity.getBlockPos(), id));
    }
}
