package com.bajuh.shearablechickenmod.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public abstract class ProxyBase {

    public abstract World getClientWorld();

    public abstract PlayerEntity getClientPlayer();

    public boolean isClient(){
        return !isServer();
    }

    public abstract boolean isServer();

    public void runOnClient(Runnable action){
        if (isClient()){
            action.run();
        }
    }

    public void runOnServer(Runnable action){
        if (isServer()){
            action.run();
        }
    }
}
