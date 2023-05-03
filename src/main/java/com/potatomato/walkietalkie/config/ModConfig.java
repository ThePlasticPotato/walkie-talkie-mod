package com.potatomato.walkietalkie.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class ModConfig {

    private static final Path CONFIG_FOLDER = FabricLoader.getInstance().getConfigDir();
    private static final File CONFIG_FILE = new File(CONFIG_FOLDER.toString(), "WalkieTalkie.properties");

    public static int maxCanal = 16;
    public static int speakerDistance = 32;
    public static int woodenWalkieTalkieRange = 128;
    public static int stoneWalkieTalkieRange = 256;
    public static int ironWalkieTalkieRange = 512;
    public static int diamondWalkieTalkieRange = 1024;
    public static int netheriteWalkieTalkieRange = 2048;

    public static void loadModConfig() {
        Properties properties = new Properties();

        if (CONFIG_FILE.exists()) {
            try {
                FileInputStream stream = new FileInputStream(CONFIG_FILE);
                properties.load(stream);
                stream.close();

                // Set values from loaded config
                maxCanal = Integer.parseInt(properties.getProperty("max-canal", "16"));
                speakerDistance = Integer.parseInt(properties.getProperty("speaker-distance", "32"));
                woodenWalkieTalkieRange = Integer.parseInt(properties.getProperty("wooden-walkietalkie-range", "128"));
                stoneWalkieTalkieRange = Integer.parseInt(properties.getProperty("stone-walkietalkie-range", "256"));
                ironWalkieTalkieRange = Integer.parseInt(properties.getProperty("iron-walkietalkie-range", "512"));
                diamondWalkieTalkieRange = Integer.parseInt(properties.getProperty("diamond-walkietalkie-range", "1024"));
                netheriteWalkieTalkieRange = Integer.parseInt(properties.getProperty("netherite-walkietalkie-range", "2048"));

                createConfig(mapConfig());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Create default config file
            createConfig(mapConfig());
        }
    }

    private static Map<String, String> mapConfig() {
        Map<String, String> config = new LinkedHashMap<>();

        config.put("# Walkie-Talkie Config File", "");
        config.put("\n# Channel settings", "");
        config.put("max-canal", String.valueOf(maxCanal));
        config.put("\n# Speaker setting", "");
        config.put("speaker-distance", String.valueOf(speakerDistance));
        config.put("\n# Walkie-Talkie settings", "");
        config.put("wooden-walkietalkie-range", String.valueOf(woodenWalkieTalkieRange));
        config.put("stone-walkietalkie-range", String.valueOf(stoneWalkieTalkieRange));
        config.put("iron-walkietalkie-range", String.valueOf(ironWalkieTalkieRange));
        config.put("diamond-walkietalkie-range", String.valueOf(diamondWalkieTalkieRange));
        config.put("netherite-walkietalkie-range", String.valueOf(netheriteWalkieTalkieRange));

        return config;
    }

    private static void createConfig(Map<String, String> config) {
        try {
            FileOutputStream output = new FileOutputStream(CONFIG_FILE);

            for (Map.Entry<String, String> entry : config.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                String line;
                if (value.matches("")) {
                    line = String.format("%s%s\n", key, value);
                } else {
                    line = String.format("%s=%s\n", key, value);
                }
                output.write(line.getBytes());
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
