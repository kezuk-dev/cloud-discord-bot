package dev.kezuk.ticket;

import dev.kezuk.CloudBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;

public class SendTicket {

    public SendTicket(final JDA api) {
        TextChannel channel = api.getTextChannelById(CloudBot.getTicketChannelId());
        if (channel != null) {
            MessageCreateData messageCreateData = new MessageCreateBuilder()
                    .setEmbeds(createEmbed())
                    .addActionRow(Button.primary("create_ticket_support", "Assistance/Support"))
                    .addActionRow(Button.primary("create_ticket_recrutement", "Recrutement"))
                    .build();
            channel.sendMessage(messageCreateData).queue();
        }
    }

    private MessageEmbed createEmbed() {
        return new EmbedBuilder()
                .setTitle("☁ Cloud | Tickets ☁")
                .setDescription("Vous avez besoins d'assistance ou bien vous souhaitez vous faire recruter?\nVous êtes au bonne endroit, cliquez sur le bouton que vous avez besoin.\nN'ouvrez pas de ticket inutile sous peine de vous voir interdire de tickets.")
                .setColor(Color.CYAN)
                .setFooter("Cloud | Tickets » A consommé avec modération.")
                .setThumbnail("https://media.discordapp.net/attachments/1003259897959415951/1267460071642632232/Banner_512x128.png?ex=66aa2f2d&is=66a8ddad&hm=5d44680eccebe70fc4d60421a0249237ead83b3c4c57a51b282e27160b228156&=&format=webp&quality=lossless")
                .build();
    }
}
