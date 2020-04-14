package com.bajuh.shearablechickenmod;

import com.bajuh.shearablechickenmod.helper.ReflectionUtils;
import com.bajuh.shearablechickenmod.init.ClientSetup;
import com.bajuh.shearablechickenmod.init.Registration;
import com.bajuh.shearablechickenmod.proxy.ClientProxy;
import com.bajuh.shearablechickenmod.proxy.IProxy;
import com.bajuh.shearablechickenmod.proxy.ServerProxy;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Constants.ModID)
public class Entry
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final IProxy SIDE_PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    static {
        LOGGER.info("Hoy");
    }

    public Entry() {
        Registration.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
    }
}
