package io.github.joaoh1.boringbackgrounds;

import java.util.Random;

import io.github.joaoh1.boringbackgrounds.config.BoringBackgroundsConfig;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

public class BoringBackgroundsMod implements ClientModInitializer {
	public static Identifier backgroundTexture;
	public static String[] textureIdentifierStrings;

	public static void updateBackground() {
		BoringBackgroundsConfig.loadJanksonConfig();
		textureIdentifierStrings = BoringBackgroundsConfig.textureIdentifiers.getValue();
		backgroundTexture = new Identifier(textureIdentifierStrings[new Random().nextInt(textureIdentifierStrings.length)]);
	}
	
	@Override
	public void onInitializeClient() {
		updateBackground();
	}
}
