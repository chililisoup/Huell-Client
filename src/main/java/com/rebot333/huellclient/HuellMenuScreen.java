package com.rebot333.huellclient;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class HuellMenuScreen extends Screen {
    public HuellMenuScreen() {
        super(Text.of("Huell Menu."));
    }

    private String getNextListValue(String value, String[] options) {
        for (int i = 0; i < options.length; i++) {
            if (value.equals(options[i])) return options[(i + 1) % options.length];
        }
        return options[0];
    }
    
    private enum WidgetTypes {
        BUTTON,
        SLIDER
    }

    private static class WidgetSkeleton {
        public String text;
        public Consumer<ButtonWidget> buttonUpdater;
        public Consumer<Double> sliderUpdater;
        public WidgetTypes widgetType;
        public double value;
        public double min;
        public double max;
        public double defaultValue;
        public WidgetSkeleton(String text, WidgetTypes widgetType, Consumer<ButtonWidget> buttonUpdater) {
            this.text = text;
            this.buttonUpdater = buttonUpdater;
            this.widgetType = widgetType;
        }
        WidgetSkeleton(String text, WidgetTypes widgetType, Consumer<Double> sliderUpdater, double value, double min, double max, double defaultValue) {
            this.text = text;
            this.sliderUpdater = sliderUpdater;
            this.widgetType = widgetType;
            this.value = value;
            this.max = max;
            this.min = min;
            this.defaultValue = defaultValue;
        }
    }
    private final List<WidgetSkeleton> widgets = new ArrayList<>();

    private static class HuellSliderWidget extends SliderWidget {
        public Consumer<Double> updater;
        public Function<Double, String> textGetter;
        public double min;
        public double max;
        public double defaultValue;
        public HuellSliderWidget(int x, int y, int width, int height, Function<Double, String> textGetter, Consumer<Double> updater, double value, double min, double max, double defaultValue) {
            super(x, y, width, height, Text.literal(textGetter.apply(value)), value);
            this.updater = updater;
            this.textGetter = textGetter;
            this.min = min;
            this.max = max;
            this.defaultValue = defaultValue;
        }

        public void updateMessage() { this.setMessage(Text.literal(textGetter.apply(this.value))); }

        public void applyValue() { updater.accept(this.value); }

        public void resetValue() {
            this.value = defaultValue;
            applyValue();
            updateMessage();
        }
    }

    private final Identifier WIDGETS_TEXTURES = new Identifier("huell_client", "textures/widgets.png");
    private void renderWidgets() {
        final int widgetsPerRow = Math.min(this.width / 150, widgets.size());
        final int widgetSpacing = (this.width - (widgetsPerRow * 150)) /  (widgetsPerRow + 1);

        for (int i = 0; i < widgets.size(); i++) {
            final int x = (i % widgetsPerRow) * (150 + widgetSpacing) + widgetSpacing;
            final int y =(i / widgetsPerRow) * 30 + 16;
            final WidgetSkeleton widget = widgets.get(i);

            if (widget.widgetType == WidgetTypes.BUTTON) {
                this.addDrawableChild(new ButtonWidget(
                        x,
                        y,
                        150,
                        20,
                        Text.literal(widget.text),
                        widget.buttonUpdater::accept
                ));
            } else {
                HuellSliderWidget slider = new HuellSliderWidget(
                        x,
                        y,
                        130,
                        20,
                        (value) -> String.format("%s: %.2f", widget.text, value * (widget.max - widget.min) + widget.min),
                        (nextSetting) -> widget.sliderUpdater.accept(nextSetting * (widget.max - widget.min) + widget.min),
                        (widget.value - widget.min) / (widget.max - widget.min),
                        widget.min,
                        widget.max,
                        (widget.defaultValue - widget.min) / (widget.max - widget.min)
                );
                this.addDrawableChild(slider);
                this.addDrawableChild(new TexturedButtonWidget(
                        x + 130,
                        y,
                        20,
                        20,
                        0,
                        0,
                        20,
                        WIDGETS_TEXTURES,
                        32,
                        64,
                        (button) -> slider.resetValue()
                ));
            }
        }
    }

    // Consider replacing with CyclingButtonWidget
    private void createRotatingSettingsWidget(String name, String[] options, Supplier<String> setting, Consumer<String> update) {
        widgets.add(new WidgetSkeleton(name + ": " + setting.get(), WidgetTypes.BUTTON, (widget) -> {
            String nextSetting = getNextListValue(setting.get(), options);
            widget.setMessage(Text.literal(name + ": " + nextSetting));
            update.accept(nextSetting);
        }));
    }

    private void createToggleSettingsWidget(String name, Supplier<Boolean> setting, Consumer<Boolean> update) {
        widgets.add(new WidgetSkeleton(name + ": " + setting.get(), WidgetTypes.BUTTON, (widget) -> {
            widget.setMessage(Text.literal(name + ": " + !setting.get()));
            update.accept(!setting.get());
        }));
    }

    private void createSliderSettingsWidget(String name, double min, double max, double defaultValue, Supplier<Double> setting, Consumer<Double> update) {
        widgets.add(new WidgetSkeleton(
                name,
                WidgetTypes.SLIDER,
                update,
                setting.get(),
                min,
                max,
                defaultValue
        ));
    }

    protected void init() {
        widgets.clear();

        for (HuellSetting setting : HuellSettings.settingsList) {
            if (setting.getType() == HuellSetting.HuellSettingType.BOOL)
                createToggleSettingsWidget(setting.getDisplay(), () -> setting.booleanValue, setting::setBooleanValue);
            else if (setting.getType() == HuellSetting.HuellSettingType.DOUBLE)
                createSliderSettingsWidget(setting.getDisplay(), setting.min, setting.max, setting.getDefaultDoubleValue(), () -> setting.doubleValue, setting::setDoubleValue);
            else if (setting.getType() == HuellSetting.HuellSettingType.LIST)
                createRotatingSettingsWidget(setting.getDisplay(), setting.getStringOptions(), () -> setting.stringValue, setting::setStringValue);
        }

        renderWidgets();
    }
}
