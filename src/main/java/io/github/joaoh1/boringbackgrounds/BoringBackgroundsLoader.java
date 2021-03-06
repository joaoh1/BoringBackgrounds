package io.github.joaoh1.boringbackgrounds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import io.github.joaoh1.boringbackgrounds.utils.BackgroundUtils;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;

public class BoringBackgroundsLoader implements SimpleResourceReloadListener<Map<Map<Identifier, Integer>, Boolean>> {
    private Identifier FABRIC_ID = new Identifier("boringbackgrounds", "data_loader");
    
    @Override
    public CompletableFuture<Map<Map<Identifier, Integer>, Boolean>> load(ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            Gson gson = new GsonBuilder().create();
            Collection<Identifier> resources = manager.findResources("backgrounds", filename -> filename.contentEquals("background_settings.json"));
            Map<Identifier, Integer> map = new HashMap<>();
            boolean randomizeOnNewScreen = false;
            for (Identifier identifier : resources) {
                try {
                    Reader reader;
                    if (BackgroundUtils.globalConfigPath.toFile().exists()) {
                        BackgroundUtils.logger.warn("[Boring Backgrounds] Found a global background settings file in minecraft/config/boringbackgrounds.json. Settings provided by resource packs will be ignored!");
                        reader = Files.newBufferedReader(BackgroundUtils.globalConfigPath, StandardCharsets.UTF_8);
                    } else {
                        reader = new BufferedReader(new InputStreamReader(manager.getResource(identifier).getInputStream(), StandardCharsets.UTF_8));
                    }
                    JsonObject json = JsonHelper.deserialize(gson, reader, JsonObject.class, true);
                    json.get("textures").getAsJsonObject().entrySet().forEach((entry) -> {
                        Identifier textureId = new Identifier(entry.getKey());
                        Integer weight = entry.getValue().getAsInt();
                        map.put(textureId, weight);
                    });
                    if (json.get("randomize_on_new_screen") != null) {
                        randomizeOnNewScreen = json.get("randomize_on_new_screen").getAsBoolean();
                    } else {
                        randomizeOnNewScreen = false;
                    }
                } catch (IOException | JsonParseException e) {
                    BackgroundUtils.logger.error("[Boring Backgrounds] Failed to load the background settings! The following error has been printed: " + e);
                }
            }
            Map<Map<Identifier, Integer>, Boolean> returnedMap = new HashMap<>();
            returnedMap.put(map, randomizeOnNewScreen);
            return returnedMap;
        }, executor);
    }

    @Override
    public CompletableFuture<Void> apply(Map<Map<Identifier, Integer>, Boolean> data, ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.runAsync(() -> {
            List<Identifier> textures = new ArrayList<>();
            data.forEach((map, randomizeOnNewScreen) -> {
                map.forEach((key, value) -> {
                    for (int i = 0; i < value; i++) {
                        textures.add(key);
                    }
                });
                BackgroundUtils.randomizeOnNewScreen = randomizeOnNewScreen;
            });

            BackgroundUtils.textures = textures;
            BackgroundUtils.backgroundTexture = BackgroundUtils.updateBackground();
        });
    }
    
    @Override
    public Identifier getFabricId() {
        return FABRIC_ID;
    }
}