package com.rebot333.huellclient.mixin;

import com.rebot333.huellclient.HuellSettings;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "getEyeHeight(Lnet/minecraft/entity/EntityPose;Lnet/minecraft/entity/EntityDimensions;)F", at = @At("RETURN"), cancellable = true)
    private void injectedEyeHeight(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValue() * (float) HuellSettings.playerScale.doubleValue);
    }

    @Inject(method = "getStandingEyeHeight", at = @At("RETURN"), cancellable = true)
    private void injectedStandingEyeHeight(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValue() * (float) HuellSettings.playerScale.doubleValue);
    }
}
