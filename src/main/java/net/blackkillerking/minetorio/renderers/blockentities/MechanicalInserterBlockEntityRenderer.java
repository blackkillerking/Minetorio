package net.blackkillerking.minetorio.renderers.blockentities;

import net.blackkillerking.minetorio.blockentity.MechanicalInserterBlockEntity;
import net.blackkillerking.minetorio.geomodels.MechanicalInserterModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MechanicalInserterBlockEntityRenderer extends GeoBlockRenderer<MechanicalInserterBlockEntity> {

    public MechanicalInserterBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new MechanicalInserterModel());
    }
}
