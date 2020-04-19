package com.bajuh.shearablechickenmod.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy extends ProxyBase {

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public boolean isServer() {
        return false;
    }
}
