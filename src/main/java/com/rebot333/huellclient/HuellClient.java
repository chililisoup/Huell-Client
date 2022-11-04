package com.rebot333.huellclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class HuellClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("HuellClient");

    @Override public void onInitializeClient() {
        LOGGER.info("Alls I'm sayin'...");
        ScreenEvents.AFTER_INIT.register(HuellClient::afterScreenInit);
    }

    public static void afterScreenInit(MinecraftClient client, Screen screen, int scaledWidth, int scaledHeight) {
        if (screen instanceof GameMenuScreen) {
            final List<ClickableWidget> buttons = Screens.getButtons(screen);
            buttons.add(new ButtonWidget(
                    0,
                    0,
                    98,
                    20,
                    Text.literal("Huell Babineaux"),
                    (button) -> client.setScreen(new HuellMenuScreen())
            ));
        }
    }
}
