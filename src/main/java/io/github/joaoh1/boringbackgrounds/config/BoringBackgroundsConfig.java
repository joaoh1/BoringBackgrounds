package io.github.joaoh1.boringbackgrounds.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.github.fablabsmc.fablabs.api.fiber.v1.exception.FiberException;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.PropertyMirror;

public class BoringBackgroundsConfig {
	//Current config
	private static final Path boringBackgroundsConfigPath = Paths.get("./config/boring_backgrounds.json5");
	public static final PropertyMirror<String[]> textureIdentifiers = PropertyMirror.create(ConfigTypes.makeArray(ConfigTypes.STRING));
	public static final PropertyMirror<Boolean> changeAfterNewScreen = PropertyMirror.create(ConfigTypes.BOOLEAN);

	//TODO - Remove backward compatibility when 3.0.0 comes
	//Backward compatibility with Boring Backgrounds 2.0.0/1.0.1
	private static final Path legacyBoringBackgroundsConfigPath = Paths.get("./config/boringbackgrounds.json5");
	public static final PropertyMirror<String[]> legacyTextureIdentifiers = PropertyMirror.create(ConfigTypes.makeArray(ConfigTypes.STRING));

	public static final ConfigTree tree = ConfigTree.builder()
		.beginValue("texture_identifiers", ConfigTypes.makeArray(ConfigTypes.STRING), new String[]{"minecraft:textures/gui/options_background.png"})
			.withComment("The array of texture identifiers which will replace the dirt background, one will be picked once loaded.\nExample of a texture identifier: \"minecraft:textures/block/iron_block.png\"")
		.finishValue(textureIdentifiers::mirror)
		.beginValue("change_after_new_screen", ConfigTypes.BOOLEAN, false)
			.withComment("If enabled, when a screen is opened, a new background will be chosen from the texture identifier array.")
		.finishValue(changeAfterNewScreen::mirror)
		.build();
	
	public static final ConfigTree legacyTree = ConfigTree.builder()
		.beginValue("textureIds", ConfigTypes.makeArray(ConfigTypes.STRING), new String[]{"minecraft:textures/gui/options_background.png"})
			.withComment("The array of texture IDs which will replace the dirt background, one will be picked.")
		.finishValue(legacyTextureIdentifiers::mirror)
		.build();
	
	private static JanksonValueSerializer serializer = new JanksonValueSerializer(false);

	public static void loadJanksonConfig() {
		if (Files.exists(legacyBoringBackgroundsConfigPath)) {
			try {
				FiberSerialization.deserialize(legacyTree, Files.newInputStream(legacyBoringBackgroundsConfigPath), serializer);
				textureIdentifiers.setValue(legacyTextureIdentifiers.getValue());
				Files.delete(legacyBoringBackgroundsConfigPath);
				saveJanksonConfig();
			} catch (IOException | FiberException e) {
				e.printStackTrace();
			}
		} else if (Files.exists(boringBackgroundsConfigPath)) {
			try {
				FiberSerialization.deserialize(tree, Files.newInputStream(boringBackgroundsConfigPath), serializer);
			} catch (IOException | FiberException e) {
				e.printStackTrace();
			}
		} else {
			saveJanksonConfig();
		}
	}

	public static void saveJanksonConfig() {
		try {
			if (!Files.exists(Paths.get("./config/"))) {
				Files.createDirectories(Paths.get("./config/"));
			}
			FiberSerialization.serialize(tree, Files.newOutputStream(boringBackgroundsConfigPath), serializer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}