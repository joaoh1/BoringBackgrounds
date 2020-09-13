<img src="./src/main/resources/assets/boringbackgrounds/icon.png" align="right" width="128px"/>

# Boring Backgrounds

A mod that allows for further customization of the background texture. Meant to be used by modpacks and resource packs. This mod depends on the [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api).

## Download

This mod is available for download on the following services:

- [CurseForge (recommended)](https://www.curseforge.com/minecraft/mc-mods/boring-backgrounds)
- [GitHub Releases (alternative)](https://github.com/joaoh1/BoringBackgrounds/releases)

## Usage

This mod reads input from both in `asset/boringbackgrounds/backgrounds/background_settings.json` inside resource packs or from `.minecraft/config/boringbackgrounds.json`, which overrides the resource pack input if found. Both files share the same format, comments were added in order to explain the format:
```jsonc
{
    //An array of identifiers and its weight, a bigger weight means more chances to be chosen compared to others.
    "textures": {
        "minecraft:textures/gui/options_background.png": 1
    },
    //If this is enabled, a new background is chosen if a screen is opened, else, it's only chosen after a texture reload.
    "randomize_on_new_screen": false
}
```

## License

This mod is licensed under the MIT license. You can freely include the mod on any modpack with no permission. Use of this mod's code on other projects is allowed as long as attribution is given.
