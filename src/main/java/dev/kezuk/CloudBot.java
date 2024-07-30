package dev.kezuk;

import dev.kezuk.config.ConfigLoader;
import dev.kezuk.listener.JoinListener;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class CloudBot {

    @Setter @Getter
    private static String token;
    @Setter @Getter
    public static String welcomeChannelId;
    @Setter @Getter
    public static String autoRankId;

    public static void main(String[] arguments) throws Exception  {
        new ConfigLoader("config.yml");
        JDA api = JDABuilder.createDefault(token).build();
        api.addEventListener(
                new JoinListener()
        );
    }
}
