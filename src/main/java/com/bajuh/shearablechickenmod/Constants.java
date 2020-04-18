package com.bajuh.shearablechickenmod;

public class Constants {

    public static final String ModID = "shearablechickenmod";

    public static final String ShearableChickenId = "shearablechicken";

    public static final int TICK_PER_MINUTE = 1200;

    // Shear timer min-max set to 2-3mins (Don't ask why.)
    public static final int SHEAR_TICK_MIN = TICK_PER_MINUTE * 2;
    public static final int SHEAR_TICK_MAX = TICK_PER_MINUTE * 3;

    //
    // Debug
    //

    // If debug mode is on, shearable chicken is available as a new entity with its own spawn egg
    public static final boolean DebugMode = false;
    // Debug only chicken-like entity with spawn egg
    public static final String StandaloneShearableChickenId = "shearablechicken_standalone";
    public static final String ShearableChickenEggId = "shearablechicken_egg";
}
