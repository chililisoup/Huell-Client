package com.rebot333.huellclient;

import java.util.ArrayList;

public class HuellSettings {
    public static ArrayList<HuellSetting> settingsList = new ArrayList<>();
    private static HuellSetting add(String id, String display, String value, String[] options) {
        HuellSetting setting = new HuellSetting(id, display, value, options);
        settingsList.add(setting);
        return setting;
    }
    private static HuellSetting add(String id, String display, double value) {
        HuellSetting setting = new HuellSetting(id, display, value, 0.0, 1.0);
        settingsList.add(setting);
        return setting;
    }
    private static HuellSetting add(String id, String display, double value, double max) {
        HuellSetting setting = new HuellSetting(id, display, value, 0.0, max);
        settingsList.add(setting);
        return setting;
    }
    private static HuellSetting add(String id, String display, double value, double min, double max) {
        HuellSetting setting = new HuellSetting(id, display, value, min, max);
        settingsList.add(setting);
        return setting;
    }
    private static HuellSetting add(String id, String display, boolean value) {
        HuellSetting setting = new HuellSetting(id, display, value);
        settingsList.add(setting);
        return setting;
    }

    public static HuellSetting flyMode = add("flyMode", "Huell Fly", "OFF", new String[]{"OFF", "CREATIVE"});
    public static HuellSetting flySpeed = add("flySpeed", "Huell Fly Speed", 0.05, 0.6);
    public static HuellSetting boatFly = add("boatFly", "Huell Boats", false);
    public static HuellSetting noHunger = add("noHunger", "Huell Food", false);
    public static HuellSetting stepHeight = add("stepHeight", "Huell Legs", 0.6, 25.0);
    public static HuellSetting jumpStrength = add("jumpStrength", "Jumpin' Huell", 1.0, 20.0);
    public static HuellSetting speed = add("speed", "Huell Meth", 1.0, 25.0);
    public static HuellSetting mountJumpStrength = add("mountJumpStrength", "Jumpin' Pets", 1.0, 20.0);
    public static HuellSetting mountSpeed = add("mountSpeed", "Pet Meth", 1.0, 25.0);
    public static HuellSetting noFall = add("noFall", "Chell Boots", false);
    public static HuellSetting jesus = add("jesus", "Huell Swim", "OFF", new String[]{"OFF", "SOLID"});
    public static HuellSetting blockBounce = add("blockBounce", "Bouncy Huell", 0.0, 2.0);
    public static HuellSetting clientTime = add("clientTime", "Huell Time", "SYNCED", new String[]{"SYNCED", "DAY", "NOON", "NIGHT", "MIDNIGHT"});
    public static HuellSetting playerScale = add("playerScale", "Huell Size", 1.0, 0.02, 3.0);
    public static HuellSetting xray = add("xray", "Huell Vision", false);
}
