package io.github.joaoh1.boringbackgrounds;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class BoringBackgroundsMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		//Registers the background loader
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new BoringBackgroundsLoader());
	}
}
