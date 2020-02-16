package io.github.joaoh1.boringbackgrounds;

import java.util.Random;

import io.github.cottonmc.cotton.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

public class BoringBackgroundsMod implements ClientModInitializer {
	public static BoringBackgroundsConfig config;
	public static Identifier backgroundTexture;

	@Override
	public void onInitializeClient() {
		config = ConfigManager.loadConfig(BoringBackgroundsConfig.class);
		// Pick the background texture with a randomly generated number, it will be used by the mixin
		backgroundTexture = new Identifier(config.identifiers[new Random().nextInt(config.identifiers.length)]);
	}
}
