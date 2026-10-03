package net.blackkillerking.minetorio.multiblock;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blackkillerking.minetorio.blockentity.base.MultiblockBaseBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.Level;

public class MultiblockPatternRenderer implements BlockEntityRenderer<MultiblockBaseBlockEntity> {
    @Override
    public void render(MultiblockBaseBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Level level = pBlockEntity.getLevel();
    }
}
