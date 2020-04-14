package com.bajuh.shearablechickenmod.init;

import com.bajuh.shearablechickenmod.Constants;
import com.bajuh.shearablechickenmod.Entry;
import com.bajuh.shearablechickenmod.entity.ShearableChickenRenderer;
import com.bajuh.shearablechickenmod.helper.ReflectionUtils;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.ModID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        Entry.LOGGER.info(String.format("[%s] Event: %s", Entry.SIDE_PROXY.isRemote() ? "CLIENT" : "SERVER", event.getClass().getName()));

        RenderingRegistry.registerEntityRenderingHandler(
            Registration.SHEARABLE_CHICKEN.get(),
            ShearableChickenRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(
            Registration.FAKE_CHICKEN.get(),
            ChickenRenderer::new);

        // RenderingRegistry.INSTANCE.entityRenderers[EntityType]
        try {
            RenderingRegistry registry =
                (RenderingRegistry)ReflectionUtils.getStaticField(RenderingRegistry.class.getDeclaredField("INSTANCE"));
            Map<EntityType<? extends Entity>, IRenderFactory<? extends Entity>> renderers =
                (Map<EntityType<? extends Entity>, IRenderFactory<? extends Entity>>)
                    ReflectionUtils.getInstanceField(RenderingRegistry.class.getDeclaredField("entityRenderers"), registry);
            renderers.put(EntityType.CHICKEN, manager -> new ShearableChickenRenderer(manager));
            Entry.LOGGER.info("Chicken renderer replaced with fake one");
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Entry.LOGGER.fatal("Unable to replace chicken renderer");
        }
    }
}
