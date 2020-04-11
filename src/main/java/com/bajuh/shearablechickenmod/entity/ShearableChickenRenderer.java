package com.bajuh.shearablechickenmod.entity;

import com.bajuh.shearablechickenmod.Constants;
import com.bajuh.shearablechickenmod.Entry;
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
        System.out.println("DEBUG: WeirdMobRenderer ctor");
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

    @Override
    public float handleRotationFloat(T livingBase, float partialTicks) {
        try {
            Method m = ChickenRenderer.class
                .getDeclaredMethod("handleRotationFloat", ChickenEntity.class, float.class);
            m.setAccessible(true);
            EntityRenderer<?> chickenRenderer = renderManager.renderers.get(EntityType.CHICKEN);
            float result = (float)m.invoke(chickenRenderer, livingBase, partialTicks);
            return result;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Entry.LOGGER.error("Failed to call ChickenRenderer.handleRotationFloat");
            return 0F;
        }
    }
}
