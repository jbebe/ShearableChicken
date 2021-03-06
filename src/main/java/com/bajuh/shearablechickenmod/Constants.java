package com.bajuh.shearablechickenmod;

import java.util.HashMap;
import java.util.Map;

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

    //
    // Obfuscated variable names
    //

    public static final String handleRotationFloat = "func_77044_a";
    public static final String CHICKEN_TEXTURES = "field_110920_a";
    public static final Map<String, String> chickenModelFields = new HashMap<String, String>() {
        {
            put("head", "field_78142_a");
            put("body", "field_78140_b");
            put("rightLeg", "field_78141_c");
            put("leftLeg", "field_78138_d");
            put("rightWing", "field_78139_e");
            put("leftWing", "field_78136_f");
            put("bill", "field_78137_g");
            put("chin", "field_78143_h");
        }
    };

}
