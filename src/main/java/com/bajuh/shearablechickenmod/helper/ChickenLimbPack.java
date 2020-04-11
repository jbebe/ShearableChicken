package com.bajuh.shearablechickenmod.helper;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ChickenLimbPack {
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer rightWing;
    public ModelRenderer leftWing;
    public ModelRenderer bill;
    public ModelRenderer chin;

    public static ChickenLimbPack CreateDefault(Model model){
        ChickenLimbPack lp = new ChickenLimbPack();
        lp.head = new ModelRenderer(model, 0, 0);
        lp.head.addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, 0.0F);
        lp.head.setRotationPoint(0.0F, 15.0F, -4.0F);
        lp.bill = new ModelRenderer(model, 14, 0);
        lp.bill.addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F);
        lp.bill.setRotationPoint(0.0F, 15.0F, -4.0F);
        lp.chin = new ModelRenderer(model, 14, 4);
        lp.chin.addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, 0.0F);
        lp.chin.setRotationPoint(0.0F, 15.0F, -4.0F);
        lp.body = new ModelRenderer(model, 0, 9);
        lp.body.addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F);
        lp.body.setRotationPoint(0.0F, 16.0F, 0.0F);
        lp.rightLeg = new ModelRenderer(model, 26, 0);
        lp.rightLeg.addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
        lp.rightLeg.setRotationPoint(-2.0F, 19.0F, 1.0F);
        lp.leftLeg = new ModelRenderer(model, 26, 0);
        lp.leftLeg.addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
        lp.leftLeg.setRotationPoint(1.0F, 19.0F, 1.0F);
        lp.rightWing = new ModelRenderer(model, 24, 13);
        lp.rightWing.addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F);
        lp.rightWing.setRotationPoint(-4.0F, 13.0F, 0.0F);
        lp.leftWing = new ModelRenderer(model, 24, 13);
        lp.leftWing.addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F);
        lp.leftWing.setRotationPoint(4.0F, 13.0F, 0.0F);

        return lp;
    }

    /**
     * @implNote
     *  - Y grows towards bottom
     *  - Y and Z is somehow mixed up, body Z is wing Y, etc.
     */
    public static ChickenLimbPack CreateSheared(Model model){
        ChickenLimbPack lp = CreateDefault(model);

        float bodyHeightOffset = -2;
        float bodyPosXOffset = 1;
        float bodyWidthOffset = -2;
        float bodyPosZOffset = 1;
        lp.body = new ModelRenderer(model, 0, 9);
        lp.body.addBox(-3.0F + bodyPosXOffset, -4.0F, -3.0F + bodyPosZOffset,
                6.0F + bodyWidthOffset, 8.0F, 6.0F + bodyHeightOffset,
                0.0F);
        lp.body.setRotationPoint(0.0F, 16.0F, 0.0F);

        float wingBodyOffset = 1F;
        float wingHeightOffset = -2;
        float wingPosYOffset = 1;
        lp.rightWing = new ModelRenderer(model, 24, 13);
        lp.rightWing.addBox(0.0F + wingBodyOffset, 0.0F + wingPosYOffset, -3.0F,
                1.0F, 4.0F + wingHeightOffset, 6.0F);
        lp.rightWing.setRotationPoint(-4.0F, 13.0F, 0.0F);
        lp.leftWing = new ModelRenderer(model, 24, 13);
        lp.leftWing.addBox(-1.0F - wingBodyOffset, 0.0F + wingPosYOffset, -3.0F,
                1.0F, 4.0F + wingHeightOffset, 6.0F);
        lp.leftWing.setRotationPoint(4.0F, 13.0F, 0.0F);

        float legBodyOffset = 1;
        float legPosYOffset = -1;
        lp.rightLeg = new ModelRenderer(model, 26, 0);
        lp.rightLeg.addBox(-1.0F, 0.0F + legPosYOffset, -3.0F,
                3.0F, 5.0F + legBodyOffset, 3.0F);
        lp.rightLeg.setRotationPoint(-2.0F, 19.0F, 1.0F);
        lp.leftLeg = new ModelRenderer(model, 26, 0);
        lp.leftLeg.addBox(-1.0F, 0.0F + legPosYOffset, -3.0F,
                3.0F, 5.0F + legBodyOffset, 3.0F);
        lp.leftLeg.setRotationPoint(1.0F, 19.0F, 1.0F);

        return lp;
    }
}