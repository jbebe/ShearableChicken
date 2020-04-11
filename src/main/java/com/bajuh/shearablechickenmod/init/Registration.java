package com.bajuh.shearablechickenmod.init;

import com.bajuh.shearablechickenmod.Constants;
import com.bajuh.shearablechickenmod.entity.ShearableChickenEggItem;
import com.bajuh.shearablechickenmod.entity.ShearableChickenEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration {

    private static final DeferredRegister<EntityType<?>> ENTITIES =
        new DeferredRegister<>(ForgeRegistries.ENTITIES, Constants.ModID);
    private static final DeferredRegister<Item> ITEMS =
        new DeferredRegister<>(ForgeRegistries.ITEMS, Constants.ModID);

    public static void init() {
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<EntityType<ShearableChickenEntity>> SHEARABLE_CHICKEN =
        ENTITIES.register(
            Constants.ShearableChickenId,
            () -> {
                EntitySize size = EntityType.CHICKEN.getSize();
                return EntityType.Builder
                    .create(ShearableChickenEntity::new, EntityClassification.CREATURE)
                    .size(size.width, size.height)
                    .setShouldReceiveVelocityUpdates(false)
                    .build(Constants.ShearableChickenId);
            });

    public static final RegistryObject<ShearableChickenEggItem> SHEARABLE_CHICKEN_EGG =
        ITEMS.register(Constants.ShearableChickenEggId, ShearableChickenEggItem::new);
}
