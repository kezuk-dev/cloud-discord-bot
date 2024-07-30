package dev.kezuk.config;

import dev.kezuk.CloudBot;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

public class ConfigLoader {

    private static final String DEFAULT_CONFIG_CONTENT =
            "token: \"VOTRE_TOKEN_ICI\"\n" +
            "welcome-channel: \"ID_CHANNEL_ICI\"\n" +
            "auto-role: \"ROLE_ID_ICI\"\n";

    public ConfigLoader(String configFilePath) {
        Yaml yaml = new Yaml();
        File configFile = new File(configFilePath);

        if (!configFile.exists()) {
            try {
                try (FileWriter writer = new FileWriter(configFile)) {
                    writer.write(DEFAULT_CONFIG_CONTENT);
                }
                System.out.println("Fichier de configuration créé avec les valeurs par défaut.");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try (InputStream inputStream = new FileInputStream(configFile)) {
            Map<String, Object> config = yaml.load(inputStream);
            if (config != null && config.containsKey("bot")) {
                Map<String, Object> botConfig = (Map<String, Object>) config.get("bot");
                CloudBot.setToken((String) botConfig.get("token"));
                CloudBot.setWelcomeChannelId((String) botConfig.get("welcome-channel"));
                CloudBot.setAutoRankId((String) botConfig.get("auto-role"));
            } else {
                throw new IllegalArgumentException("Clé 'bot' manquante dans le fichier de configuration.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
