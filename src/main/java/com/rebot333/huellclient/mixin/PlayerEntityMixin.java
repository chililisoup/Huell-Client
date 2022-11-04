package com.rebot333.huellclient.mixin;

import com.rebot333.huellclient.HuellSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow @Final private PlayerAbilities abilities;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getAbilities()Lnet/minecraft/entity/player/PlayerAbilities;", at = @At("RETURN"), cancellable = true)
    private void injectedAbilities(CallbackInfoReturnable<PlayerAbilities> cir) {
        PlayerAbilities newAbilities = cir.getReturnValue();
        newAbilities.allowFlying = HuellSettings.flyMode.stringValue.equals("CREATIVE");
        cir.setReturnValue(newAbilities);
    }

    @Inject(method = "getMovementSpeed()F", at = @At("RETURN"), cancellable = true)
    private void injectedMovementSpeed(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValue() * (float)HuellSettings.speed.doubleValue);
    }

    @ModifyVariable(method="addExhaustion(F)V", at = @At("HEAD"), argsOnly = true)
    private float injectedOnGround(float current) {
        return HuellSettings.noHunger.booleanValue ? 0 : current;
    }

    @Inject(method = "travel(Lnet/minecraft/util/math/Vec3d;)V", at = @At("HEAD"))
    private void injectOnTravel(Vec3d movementInput, CallbackInfo ci) {
        this.stepHeight = (float)HuellSettings.stepHeight.doubleValue;
        this.abilities.setFlySpeed((float)HuellSettings.flySpeed.doubleValue);
    }

    @ModifyArg(method = "travel(Lnet/minecraft/util/math/Vec3d;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setVelocity(DDD)V"), index = 1)
    private double injectedVerticalFlySpeed(double y) {
        return y * (HuellSettings.flySpeed.doubleValue + 0.95);
    }
}
