package dev.kezuk;

import dev.kezuk.config.ConfigLoader;
import dev.kezuk.listener.JoinListener;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class CloudBot extends ListenerAdapter {

    @Setter @Getter
    private static String token;
    @Setter @Getter
    public static String welcomeChannelId;
    @Setter @Getter
    public static String autoRankId;

    public static void main(String[] arguments) throws Exception  {
        new ConfigLoader("config.yml");
        JDA api = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .build();
        api.addEventListener(new JoinListener());
    }
}
