package com.bajuh.shearablechickenmod.entity;

import com.bajuh.shearablechickenmod.helper.ChickenLimbPack;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import java.util.function.Function;

public abstract class ShearableChickenModelBase<T extends Entity> extends AgeableModel<T> {

    // TODO: solve this mess. what's shearable what's sheared...
    public static class ShearableChickenModel<T extends Entity> extends ShearableChickenModelBase<T> {
        public ShearableChickenModel(){
            super(ChickenLimbPack::CreateDefault);
        }
    }

    public static class DefaultChickenModel<T extends Entity> extends ShearableChickenModelBase<T> {
        public DefaultChickenModel(){
            super(ChickenLimbPack::CreateSheared);
        }
    }

    private final ChickenLimbPack limbs;

    public ShearableChickenModelBase(Function<ShearableChickenModelBase<T>, ChickenLimbPack> callback) {
        super();
        limbs = callback.apply(this);
    }

    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(limbs.head, limbs.bill, limbs.chin);
    }

    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(limbs.body, limbs.rightLeg, limbs.leftLeg, limbs.rightWing, limbs.leftWing);
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        limbs.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        limbs.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        limbs.bill.rotateAngleX = limbs.head.rotateAngleX;
        limbs.bill.rotateAngleY = limbs.head.rotateAngleY;
        limbs.chin.rotateAngleX = limbs.head.rotateAngleX;
        limbs.chin.rotateAngleY = limbs.head.rotateAngleY;
        limbs.body.rotateAngleX = ((float)Math.PI / 2F);
        limbs.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        limbs.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        limbs.rightWing.rotateAngleZ = ageInTicks;
        limbs.leftWing.rotateAngleZ = -ageInTicks;
    }
}
