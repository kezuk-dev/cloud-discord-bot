package dev.kezuk.config;

import dev.kezuk.CloudBot;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

public class ConfigLoader {

    private static final String DEFAULT_CONFIG_CONTENT =
                    "token: \"VOTRE_TOKEN_ICI\"\n" +
                    "welcome-channel: \"ID_CHANNEL_ICI\"\n" +
                    "auto-role: \"ROLE_ID_ICI\"\n" +
                    "support-category: \"CATEGORY_ID_ICI\"\n" +
                    "recrutement-category: \"CATEGORY_ID_ICI\"\n" +
                    "ticket-channel: \"CHANNEL_ID_ICI\"\n";

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
            if (config != null) {
                CloudBot.setToken((String) config.get("token"));
                CloudBot.setWelcomeChannelId((String) config.get("welcome-channel"));
                CloudBot.setAutoRankId((String) config.get("auto-role"));
                CloudBot.setRecrutementCategoryId((String) config.get("recrutement-category"));
                CloudBot.setSupportCategoryId((String) config.get("support-category"));
                CloudBot.setTicketChannelId((String) config.get("ticket-channel"));
            } else {
                throw new IllegalArgumentException("Fichier de configuration érronée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
