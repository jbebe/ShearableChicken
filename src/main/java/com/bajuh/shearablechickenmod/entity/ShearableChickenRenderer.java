package com.bajuh.shearablechickenmod.entity;

import com.bajuh.shearablechickenmod.Constants;
import com.bajuh.shearablechickenmod.helper.ReflectionUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.InvocationTargetException;

public class ShearableChickenRenderer<T extends ShearableChickenEntity>
    extends MobRenderer<T, ShearableChickenModelBase<T>>
{
    // Textures (static)

    private static ResourceLocation DEFAULT_TEXTURE = null;
    private static final ResourceLocation SHEARED_TEXTURE =
        new ResourceLocation(Constants.ModID, "textures/entity/shearedchicken.png");

    static {
        try {
            // Trying to keep as much as possible from the original Chicken
            // Here I load the default texture so that I don't need to keep a copy in the resource folder
            DEFAULT_TEXTURE = (ResourceLocation)ReflectionUtils.getStaticField(
                ChickenRenderer.class, Constants.CHICKEN_TEXTURES, false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    // Fields

    // We "cache" the two models so that they are ready when render uses one of them
    public final ShearableChickenModelBase.ShearedChickenModel<T> shearedModel =
        new ShearableChickenModelBase.ShearedChickenModel<>();
    public final ShearableChickenModelBase.DefaultChickenModel<T> defaultModel =
        new ShearableChickenModelBase.DefaultChickenModel<>();

    public ShearableChickenRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ShearableChickenModelBase.DefaultChickenModel<>(), 0.3F);
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
        IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        ShearableChickenModelBase<T> requiredModel = entityIn.isSheared()
            ? shearedModel
            : defaultModel;

        // If the required entity is different from the currently used one,
        // replace the underlying entityModel. That's how the chicken look different when sheared.
        if (this.entityModel != requiredModel){
            this.entityModel = requiredModel;
        }

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(ShearableChickenEntity entity) {
        return entity.isSheared()
            ? SHEARED_TEXTURE
            : DEFAULT_TEXTURE;
    }

    // Once again, trying to use the original code so there won't be* regression related bugs (*less)
    @Override
    protected float handleRotationFloat(T livingBase, float partialTicks) {
        ReflectionUtils.InstanceMethod<Float> shearableChickenRenderer = new ReflectionUtils.InstanceMethod<>(
            ChickenRenderer.class, Constants.handleRotationFloat, ChickenEntity.class, float.class);
        try {
            ChickenRenderer chickenRenderer = new ChickenRenderer(null);
            return shearableChickenRenderer.invoke(chickenRenderer, livingBase, partialTicks);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return 0F;
        }
    }
}
