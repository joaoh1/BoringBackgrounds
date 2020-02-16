package io.github.joaoh1.boringbackgrounds;

import java.util.Random;

import io.github.cottonmc.cotton.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

public class BoringBackgroundsMod implements ClientModInitializer {
	public static BoringBackgroundsConfig config;
	public static int backgroundToPick;
	public static Identifier backgroundTexture;

	@Override
	public void onInitializeClient() {
		config = ConfigManager.loadConfig(BoringBackgroundsConfig.class);
		backgroundToPick = new Random().nextInt(config.identifiers.length);
		backgroundTexture = new Identifier(config.identifiers[backgroundToPick]);
	}
}
