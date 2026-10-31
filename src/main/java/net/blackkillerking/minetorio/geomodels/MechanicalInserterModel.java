package net.blackkillerking.minetorio.geomodels;

import net.blackkillerking.minetorio.blockentity.MechanicalInserterBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.model.GeoModel;

public class MechanicalInserterModel extends GeoModel<MechanicalInserterBlockEntity> {
    private final ResourceLocation model = new ResourceLocation(GeckoLib.MOD_ID, "geo/mechanical_inserter.geo.json");
    private final ResourceLocation texture = new ResourceLocation(GeckoLib.MOD_ID, "textures/block/mechaincal_arm.png");
    private final ResourceLocation animations = new ResourceLocation(GeckoLib.MOD_ID, "animations/mechanical_inserter_swing.animation.json");

    @Override
    public ResourceLocation getModelResource(MechanicalInserterBlockEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(MechanicalInserterBlockEntity object) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(MechanicalInserterBlockEntity object) {
        return this.animations;
    }
}
