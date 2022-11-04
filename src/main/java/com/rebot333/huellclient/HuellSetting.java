package com.rebot333.huellclient;

public class HuellSetting {
    public enum HuellSettingType {
        LIST,
        BOOL,
        DOUBLE
    }

    private final String id;
    private final String display;
    private final String defaultStringValue;
    private final String[] stringOptions;
    private final boolean defaultBooleanValue;
    private final double defaultDoubleValue;
    private final HuellSettingType type;

    public String stringValue;
    public boolean booleanValue;
    public double doubleValue;
    public double min;
    public double max;

    private HuellSetting(String id, String display, String stringValue, String[] stringOptions, boolean booleanValue, double doubleValue, double min, double max, HuellSettingType type) {
        this.id = id;
        this.display = display;
        this.stringValue = stringValue;
        this.defaultStringValue = stringValue;
        this.stringOptions = stringOptions;
        this.booleanValue = booleanValue;
        this.defaultBooleanValue = booleanValue;
        this.doubleValue = doubleValue;
        this.defaultDoubleValue = doubleValue;
        this.min = min;
        this.max = max;
        this.type = type;
    }
    HuellSetting(String id, String display, String value, String[] options) {
        this(id, display, value, options, false, 0.0, 0.0, 0.0, HuellSettingType.LIST);
        this.stringValue = value;
    }
    HuellSetting(String id, String display, boolean value) {
        this(id, display, null, null, value, 0.0, 0.0, 0.0, HuellSettingType.BOOL);
        this.booleanValue = value;
    }
    HuellSetting(String id, String display, double value, double min, double max) {
        this(id, display, null, null, false, value, min, max, HuellSettingType.DOUBLE);
        this.doubleValue = value;
    }


    public String getId() { return id; }
    public String getDisplay() { return display; }
    public HuellSettingType getType() { return this.type; }
    public String getDefaultStringValue() { return defaultStringValue; }
    public String[] getStringOptions() { return stringOptions; }
    public boolean getDefaultBooleanValue() { return defaultBooleanValue; }
    public double getDefaultDoubleValue() { return defaultDoubleValue; }
    public void setStringValue(String value) { this.stringValue = value; }
    public void setBooleanValue(boolean value) { this.booleanValue = value; }
    public void setDoubleValue(double value) { this.doubleValue = value; }
}
