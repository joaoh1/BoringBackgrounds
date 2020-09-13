package io.github.joaoh1.boringbackgrounds.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.Identifier;

public class BackgroundUtils {
	public static final Logger logger = LogManager.getFormatterLogger("Boring Backgrounds");

	public static List<Identifier> textures = new ArrayList<>();
	public static Identifier backgroundTexture;
	public static boolean randomizeOnNewScreen;

	public static Identifier updateBackground() {
		if (textures.size() == 0) {
			return backgroundTexture = new Identifier("minecraft:textures/gui/options_background.png");
		}

		return backgroundTexture = textures.get(new Random().nextInt(textures.size()));
	}
}
