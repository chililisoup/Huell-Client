package com.rebot333.huellclient.mixin;

import com.rebot333.huellclient.HuellSettings;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Camera.class)
public class CameraMixin {

    @ModifyVariable(
            method = "clipToSpace",
            at = @At(value = "HEAD"),
            argsOnly = true)
    private double injectedDesiredDistance(double desiredDistance) {
        return desiredDistance * HuellSettings.playerScale.doubleValue;
    }

}
