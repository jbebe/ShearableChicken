package com.bajuh.shearablechickenmod.helper;

import net.minecraft.util.LazyValue;

import java.util.Random;

// Random helper to generate between two integers
// This could be an extension method in C# :(
public class RandomUtils {

    private static final LazyValue<Random> RAND = new LazyValue<>(Random::new);

    public static int nextInt(int min, int max, Random random){
        if (random == null)
            random = RAND.getValue();

        return random.nextInt(max-min) + min;
    }
}
