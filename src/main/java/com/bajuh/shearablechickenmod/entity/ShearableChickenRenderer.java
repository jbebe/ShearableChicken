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
import net.minecraft.util.Tuple;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.function.Supplier;

public class ShearableChickenRenderer<T extends ShearableChickenEntity>
    extends MobRenderer<T, ShearableChickenModelBase<T>> {

    private static ResourceLocation DEFAULT_TEXTURE = null;
    private static final ResourceLocation SHEARED_TEXTURE =
        new ResourceLocation(Constants.ModID, "textures/entity/shearedchicken.png");

    // Trying to keep as much as possible from the original Chicken
    // Here I load the default texture so that I don't need to keep a copy in the resource folder
    static {
        try {
            DEFAULT_TEXTURE = (ResourceLocation)ReflectionUtils.getStaticField(
                ChickenRenderer.class.getDeclaredField("CHICKEN_TEXTURES"));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public ShearableChickenRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ShearableChickenModelBase.DefaultChickenModel<>(), 0.3F);
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
        IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        // Save the next required 3d model with its type
        Tuple<Type, Supplier<ShearableChickenModelBase<T>>> requiredModel = entityIn.isSheared()
            ? new Tuple<>(
                ShearableChickenModelBase.ShearedChickenModel.class,
                ShearableChickenModelBase.ShearedChickenModel::new)
            : new Tuple<>(
                ShearableChickenModelBase.DefaultChickenModel.class,
                ShearableChickenModelBase.DefaultChickenModel::new);

        // If the required entity type is different from the current one,
        // replace the underlying entityModel
        if (!this.getEntityModel().getClass().equals(requiredModel.getA())){
            this.entityModel = requiredModel.getB().get();
        }

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(ShearableChickenEntity entity) {
        return entity.isSheared()
            ? SHEARED_TEXTURE
            : DEFAULT_TEXTURE;
    }

    // Once again, trying to use the original code so there won't be regression related bugs (at least, here)
    @Override
    protected float handleRotationFloat(T livingBase, float partialTicks) {
        ReflectionUtils.InstanceMethod<Float> shearableChickenRenderer = new ReflectionUtils.InstanceMethod<>(
            ChickenRenderer.class, "handleRotationFloat", ChickenEntity.class, float.class);
        try {
            ChickenRenderer chickenRenderer = new ChickenRenderer(null);
            return shearableChickenRenderer.invoke(chickenRenderer, livingBase, partialTicks);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return 0F;
        }
    }
}
