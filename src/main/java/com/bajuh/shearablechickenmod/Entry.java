package com.bajuh.shearablechickenmod;

import com.bajuh.shearablechickenmod.init.ClientSetup;
import com.bajuh.shearablechickenmod.init.ObjectRegistration;
import com.bajuh.shearablechickenmod.proxy.ClientProxy;
import com.bajuh.shearablechickenmod.proxy.IProxy;
import com.bajuh.shearablechickenmod.proxy.ServerProxy;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Constants.ModID)
public class Entry
{
    // Global logger instance, called from everywhere in the mod
    public static final Logger LOGGER = LogManager.getLogger();

    public static final IProxy SIDE_PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public Entry() {
        // Registration on both sides
        ObjectRegistration.init();

        // Registration on client
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::onClientSetup);
    }
}
