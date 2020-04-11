package com.bajuh.shearablechickenmod;

import com.bajuh.shearablechickenmod.init.ClientSetup;
import com.bajuh.shearablechickenmod.init.Registration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Constants.ModID)
public class Entry
{
    public static final Logger LOGGER = LogManager.getLogger();

    public Entry() {
        Registration.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
    }
}
