package com.bajuh.shearablechickenmod.init;

import com.bajuh.shearablechickenmod.Constants;
import com.bajuh.shearablechickenmod.Entry;
import com.bajuh.shearablechickenmod.helper.ReflectionUtils;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class Events {

    @Mod.EventBusSubscriber(modid = Constants.ModID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

        @SubscribeEvent
        public static void onCommonSetup(final FMLCommonSetupEvent event){
            try {
                // Most important part: replace chicken entity with fake chicken entity
                // Since EntityType.CHICKEN is already referenced in many parts of the Minecraft source code,
                // we can only modify it's properties. To replace the whole chicken, we need to replace every field.
                // That's what this reflection helper method does.
                ReflectionUtils.copyInstanceFields(EntityType.class, ObjectRegistration.FAKE_CHICKEN.get(), EntityType.CHICKEN);

                Entry.LOGGER.info("Chicken entity replaced with fake one");
            } catch (NoSuchFieldException | IllegalAccessException e) {

                Entry.LOGGER.fatal("Unable to replace chicken entity");
            }
        }
    }
}
