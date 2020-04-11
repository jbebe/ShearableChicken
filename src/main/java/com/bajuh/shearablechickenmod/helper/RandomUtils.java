package com.bajuh.shearablechickenmod.helper;

import net.minecraft.util.LazyValue;

import java.util.Random;

public class RandomUtils {

    private static final LazyValue<Random> RAND = new LazyValue<>(Random::new);

    public static int nextInt(int min, int max, Random random){
        return (
            random == null
                ? RAND.getValue()
                : random
        ).nextInt(max-min) + min;
    }

}
