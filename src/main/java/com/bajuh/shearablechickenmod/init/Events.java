package com.bajuh.shearablechickenmod.init;

import com.bajuh.shearablechickenmod.Constants;
import com.bajuh.shearablechickenmod.Entry;
import com.bajuh.shearablechickenmod.helper.ReflectionUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class Events {

    public static String SIDE = Entry.SIDE_PROXY.isRemote() ? "CLIENT" : "SERVER";

    @Mod.EventBusSubscriber(modid = Constants.ModID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

        @SubscribeEvent
        public static void init1(final net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));

            try {
                // Replace chicken fields with fake chicken fields
                ReflectionUtils.copyFields(EntityType.class, Registration.FAKE_CHICKEN.get(), EntityType.CHICKEN);

                // Replace egg
                ReflectionUtils.setInstanceFinalField(SpawnEggItem.class.getDeclaredField("typeIn"), Registration.FAKE_CHICKEN.get(), Items.CHICKEN_SPAWN_EGG);

                Entry.LOGGER.info("Chicken entity replaced with fake one");
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Entry.LOGGER.fatal("Unable to replace chicken entity");
            }
        }

        @SubscribeEvent
        public static void init2(final net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        @SubscribeEvent
        public static void init3(final net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        @SubscribeEvent
        public static void init4(final net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        @SubscribeEvent
        public static void init5(final net.minecraftforge.fml.event.lifecycle.FMLModIdMappingEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        @SubscribeEvent
        public static void init6(final net.minecraftforge.fml.event.lifecycle.GatherDataEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        @SubscribeEvent
        public static void init7(final net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        @SubscribeEvent
        public static void init8(final net.minecraftforge.fml.event.lifecycle.InterModProcessEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        @SubscribeEvent
        public static void init9(final net.minecraftforge.fml.event.lifecycle.ModLifecycleEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        @SubscribeEvent
        public static void init10(final RegistryEvent.Register<EntityType<?>> event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
            if (event.getRegistry().containsValue(EntityType.CHICKEN)){
                Entry.LOGGER.info(String.format("Found the chicken register event in: %s", event.getName()));
            }
        }

    }

    @Mod.EventBusSubscriber(modid = Constants.ModID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void init11(final net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event){

            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        @SubscribeEvent
        public static void init12(final net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        @SubscribeEvent
        public static void init13(final net.minecraftforge.fml.event.server.FMLServerStartedEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        @SubscribeEvent
        public static void init14(final net.minecraftforge.fml.event.server.FMLServerStartingEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        @SubscribeEvent
        public static void init15(final net.minecraftforge.fml.event.server.FMLServerStoppedEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        @SubscribeEvent
        public static void init16(final net.minecraftforge.fml.event.server.FMLServerStoppingEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        @SubscribeEvent
        public static void init17(final net.minecraftforge.fml.event.server.ServerLifecycleEvent event){
            Entry.LOGGER.info(String.format("[%s] Event: %s", SIDE, event.getClass().getName()));
        }

        public static boolean CHICKEN_REPLACED = false;

        /*@SubscribeEvent
        public static void onEntityJoinWorld(EntityJoinWorldEvent event)
        {
            if (event.getWorld().isRemote){
                return;
            }

            if (!CHICKEN_REPLACED){
                try {
                    // Replace chicken
                    ReflectionUtils.setStaticFinalField(EntityType.class.getField("CHICKEN"), Registration.FAKE_CHICKEN.get());

                    // Replace egg
                    ReflectionUtils.setFinalField(SpawnEggItem.class.getDeclaredField("typeIn"), Registration.FAKE_CHICKEN.get(), Items.CHICKEN_SPAWN_EGG);

                    CHICKEN_REPLACED = true;
                    Entry.LOGGER.info("Chicken entity replaced with fake one");
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    Entry.LOGGER.fatal("Unable to replace chicken entity");
                }
            }
        }*/
    }
}
