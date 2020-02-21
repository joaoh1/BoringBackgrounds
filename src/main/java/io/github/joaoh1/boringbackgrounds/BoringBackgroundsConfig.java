package io.github.joaoh1.boringbackgrounds;

import blue.endless.jankson.Comment;
import io.github.cottonmc.cotton.config.annotations.ConfigFile;

@ConfigFile(name = "boringbackgrounds")
public class BoringBackgroundsConfig {
	@Comment(value="The array of texture IDs which will replace the dirt background, one will be picked.")
	// "minecraft:" was added here in order to make the config slightly more easier to understand
	public String[] textureIds = new String[]{"minecraft:textures/gui/options_background.png"};
}