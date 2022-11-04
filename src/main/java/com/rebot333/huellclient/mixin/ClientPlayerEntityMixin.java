package com.rebot333.huellclient.mixin;

import com.rebot333.huellclient.HuellSettings;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "getMountJumpStrength()F", at = @At("RETURN"), cancellable = true)
    private void injectedMountJumpStrength(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValue() * (float)HuellSettings.mountJumpStrength.doubleValue);
    }
}
