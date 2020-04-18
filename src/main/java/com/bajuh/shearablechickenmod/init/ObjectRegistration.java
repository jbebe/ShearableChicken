package com.bajuh.shearablechickenmod.init;

import com.bajuh.shearablechickenmod.Constants;
import com.bajuh.shearablechickenmod.entity.ShearableChickenEggItem;
import com.bajuh.shearablechickenmod.entity.ShearableChickenEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ObjectRegistration {

    // Entity registration hook
    private static final DeferredRegister<EntityType<?>> ENTITIES =
        new DeferredRegister<>(ForgeRegistries.ENTITIES, Constants.ModID);

    // Item registration hook
    private static final DeferredRegister<Item> ITEMS =
        new DeferredRegister<>(ForgeRegistries.ITEMS, Constants.ModID);

    // Register hooks
    public static void init() {
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Fake chicken will be replaced with the real chicken
    public static final RegistryObject<EntityType<ChickenEntity>> FAKE_CHICKEN =
        ENTITIES.register(
            Constants.ShearableChickenId,
            () -> {
                EntitySize size = EntityType.CHICKEN.getSize();
                return EntityType.Builder
                    .create((EntityType.IFactory<ChickenEntity>)ShearableChickenEntity::new, EntityClassification.CREATURE)
                    .size(size.width, size.height)
                    .setShouldReceiveVelocityUpdates(false)
                    .build(Constants.ShearableChickenId);
            });

    //
    // Debug only
    //

    public static final RegistryObject<EntityType<ShearableChickenEntity>> SHEARABLE_CHICKEN = Constants.DebugMode
        ? ENTITIES.register(
            Constants.StandaloneShearableChickenId,
            () -> {
                EntitySize size = EntityType.CHICKEN.getSize();
                return EntityType.Builder
                    .create(ShearableChickenEntity::new, EntityClassification.CREATURE)
                    .size(size.width, size.height)
                    .setShouldReceiveVelocityUpdates(false)
                    .build(Constants.StandaloneShearableChickenId);
            })
        : null;

    public static final RegistryObject<ShearableChickenEggItem> SHEARABLE_CHICKEN_EGG = Constants.DebugMode
        ? ITEMS.register(Constants.ShearableChickenEggId, ShearableChickenEggItem::new)
        : null;
}
