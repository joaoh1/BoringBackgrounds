package io.github.joaoh1.boringbackgrounds;

import blue.endless.jankson.Comment;
import io.github.cottonmc.cotton.config.annotations.ConfigFile;

@ConfigFile(name = "boringbackgrounds")
public class BoringBackgroundsConfig {
    @Comment(value="The array of identifiers which will replace the dirt background, one will be picked.")
    public String[] identifiers = new String[]{"minecraft:textures/gui/options_background.png"};
}