package com.bajuh.shearablechickenmod.entity;

import com.bajuh.shearablechickenmod.helper.ChickenLimbPack;
import net.minecraft.client.renderer.entity.model.ChickenModel;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// We need a base class so when we swap between the two 3d model, the base class remains the same
public abstract class ShearableChickenModelBase<T extends Entity> extends ChickenModel<T> {

    // Create specific model for default chicken
    @OnlyIn(Dist.CLIENT)
    public static class DefaultChickenModel<T extends Entity> extends ShearableChickenModelBase<T> {
        public DefaultChickenModel(){
            super();
            ChickenLimbPack pack = ChickenLimbPack.CreateDefault(this);
            pack.SetLimbs(this);
        }
    }

    // Create specific model for sheared chicken
    @OnlyIn(Dist.CLIENT)
    public static class ShearedChickenModel<T extends Entity> extends ShearableChickenModelBase<T> {
        public ShearedChickenModel(){
            super();
            ChickenLimbPack pack = ChickenLimbPack.CreateSheared(this);
            pack.SetLimbs(this);
        }
    }

    public ShearableChickenModelBase() {
        super();
    }
}
