package io.github.joaoh1.boringbackgrounds.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.joaoh1.boringbackgrounds.utils.BackgroundUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    //The mixin behind the "randomize_on_new_screen"'s behavior
    @Inject(at = @At("HEAD"), method = "openScreen(Lnet/minecraft/client/gui/screen/Screen;)V")
    public void changeBackgroundAfterOpenScreen(Screen screen, CallbackInfo info) {
        if (BackgroundUtils.randomizeOnNewScreen) {
            BackgroundUtils.updateBackground();
        }
    }
}