package com.bajuh.shearablechickenmod.entity;

import com.bajuh.shearablechickenmod.Constants;
import com.bajuh.shearablechickenmod.Entry;
import com.bajuh.shearablechickenmod.helper.ReflectionUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.MathHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.function.Supplier;

public class ShearableChickenRenderer<T extends ShearableChickenEntity>
    extends MobRenderer<T, ShearableChickenModelBase<T>> {

    private static final ResourceLocation DEFAULT_TEXTURE =
        new ResourceLocation(Constants.ModID, "textures/entity/chicken.png");
    private static final ResourceLocation SHEARED_TEXTURE =
        new ResourceLocation(Constants.ModID, "textures/entity/shearedchicken.png");

    public ShearableChickenRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ShearableChickenModelBase.ShearableChickenModel<>(), 0.3F);
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
        IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        Tuple<Type, Supplier<ShearableChickenModelBase<T>>> requiredModel = entityIn.isSheared()
            ? new Tuple<>(
                ShearableChickenModelBase.DefaultChickenModel.class,
                ShearableChickenModelBase.DefaultChickenModel::new)
            : new Tuple<>(
                ShearableChickenModelBase.ShearableChickenModel.class,
                ShearableChickenModelBase.ShearableChickenModel::new);

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

    /*@Override
    public float handleRotationFloat(T livingBase, float partialTicks) {
        try {
            ReflectionUtils.InstanceMethod method = new ReflectionUtils.InstanceMethod(
                ChickenRenderer.class, "handleRotationFloat", ChickenEntity.class, float.class);
            EntityRenderer<?> chickenRenderer = renderManager.renderers.get(EntityType.CHICKEN);
            return (float)method.invoke(chickenRenderer, livingBase, partialTicks);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Entry.LOGGER.error("Failed to call ChickenRenderer.handleRotationFloat");
            return 0F;
        }
    }*/

    @Override
    protected float handleRotationFloat(T livingBase, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
        float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
        return (MathHelper.sin(f) + 1.0F) * f1;
    }
}
