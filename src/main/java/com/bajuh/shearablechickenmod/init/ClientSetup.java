package com.bajuh.shearablechickenmod.init;

import com.bajuh.shearablechickenmod.Constants;
import com.bajuh.shearablechickenmod.Entry;
import com.bajuh.shearablechickenmod.entity.ShearableChickenRenderer;
import com.bajuh.shearablechickenmod.helper.ReflectionUtils;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.ModID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static ChickenRenderer CHICKEN_RENDERER = null;

    public static void onClientSetup(final FMLClientSetupEvent event) {

        // Register the fake chicken as a new entity
        RenderingRegistry.registerEntityRenderingHandler(
            ObjectRegistration.FAKE_CHICKEN.get(),
            ChickenRenderer::new);

        // Run the following operation:
        // > RenderingRegistry.INSTANCE.entityRenderers[EntityType.CHICKEN] = new ShearableChickenRenderer
        // Note: We don't have to use the obfuscated field names because this is a Forge package, not from MC
        try {
            RenderingRegistry registry =
                (RenderingRegistry)ReflectionUtils.getStaticField(RenderingRegistry.class, "INSTANCE", false);
            Map<EntityType<? extends Entity>, IRenderFactory<? extends Entity>> renderers =
                (Map<EntityType<? extends Entity>, IRenderFactory<? extends Entity>>)
                    ReflectionUtils.getInstanceField(RenderingRegistry.class, "entityRenderers", registry, false);
            renderers.put(EntityType.CHICKEN, manager -> new ShearableChickenRenderer(manager));

            Entry.LOGGER.info("Chicken renderer replaced with fake one");
        } catch (IllegalAccessException | NoSuchFieldException e) {

            // The mod will not work, warn the user.
            Entry.LOGGER.fatal("Unable to replace chicken renderer");
        }

        // Register chicken-like animal in debug mode
        if (Constants.DebugMode){
            RenderingRegistry.registerEntityRenderingHandler(
                ObjectRegistration.SHEARABLE_CHICKEN.get(),
                ShearableChickenRenderer::new);
        }
    }
}
