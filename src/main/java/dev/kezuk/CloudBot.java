package dev.kezuk;

import dev.kezuk.config.ConfigLoader;
import dev.kezuk.listener.JoinListener;
import dev.kezuk.listener.TicketListener;
import dev.kezuk.ticket.SendTicket;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CloudBot extends ListenerAdapter {

    @Setter @Getter
    private static String token;
    @Setter @Getter
    public static String welcomeChannelId;
    @Setter @Getter
    public static String autoRankId;
    @Setter @Getter
    public static String recrutementCategoryId;
    @Setter @Getter
    public static String supportCategoryId;
    @Setter @Getter
    public static String ticketChannelId;

    public static void main(String[] arguments) throws Exception  {
        new ConfigLoader("config.yml");
        JDA api = JDABuilder.createDefault(token)
                .build();
        api.getPresence().setActivity(Activity.watching("les tickets!"));
        api.addEventListener(
                new JoinListener(),
                new TicketListener()
        );
    }
}
