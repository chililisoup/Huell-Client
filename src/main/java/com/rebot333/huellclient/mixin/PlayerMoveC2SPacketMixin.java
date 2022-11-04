package com.rebot333.huellclient.mixin;

import com.rebot333.huellclient.HuellSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerMoveC2SPacket.class)
public class PlayerMoveC2SPacketMixin {
    @ModifyVariable(method="<init>", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static boolean injectedOnGround(boolean current) {
        assert MinecraftClient.getInstance().player != null;
        return (MinecraftClient.getInstance().player.fallDistance >= 3.0f) ? HuellSettings.noFall.booleanValue : current;
    }
}
