package io.github.joaoh1.boringbackgrounds.mixin;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

import java.util.Map;

import com.google.common.collect.Maps;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.joaoh1.boringbackgrounds.BoringBackgroundsMod;

@Mixin(TextureManager.class)
public class TextureManagerMixin {
	@Shadow
	private final Map<Identifier, AbstractTexture> textures = Maps.newHashMap();
	
	@Inject(method = "bindTextureInner(Lnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "net/minecraft/client/texture/TextureManager.registerTexture(Lnet/minecraft/util/Identifier;Lnet/minecraft/client/texture/AbstractTexture;)V"), cancellable = true)
	private void customBackgroundBindTextureInner(Identifier id, CallbackInfo info) {
		AbstractTexture abstractTexture = (AbstractTexture)this.textures.get(id);

		// If the identifier is the same as the background texture, hijack it and change it to the chosen texture
		if (id.equals(DrawableHelper.BACKGROUND_LOCATION)) {
			abstractTexture = new ResourceTexture(BoringBackgroundsMod.backgroundTexture);
			this.registerTexture(id, (AbstractTexture)abstractTexture);
			((AbstractTexture)abstractTexture).bindTexture();
			info.cancel();
		}
	}

	@Shadow
	private void registerTexture(Identifier id, AbstractTexture abstractTexture) {}
	
	@Shadow
	private void bindTextureInner(Identifier id) {}
}
