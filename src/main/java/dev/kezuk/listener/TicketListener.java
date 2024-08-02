package dev.kezuk.listener;

import dev.kezuk.CloudBot;
import dev.kezuk.ticket.SendTicket;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.EnumSet;
import java.util.List;

public class TicketListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        JDA api = event.getJDA();
        new SendTicket(api);
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getButton().getId().equals("create_ticket_support") || event.getButton().getId().equals("create_ticket_recrutement")) {
            Guild guild = event.getGuild();
            Member member = event.getMember();
            final String categ = event.getButton().getId().equals("create_ticket_support") ? CloudBot.getSupportCategoryId() : CloudBot.getRecrutementCategoryId();
            Category category = guild.getCategoryById(categ);
            List<TextChannel> channels = category.getTextChannels();
            for (TextChannel channel : channels) {
                if (channel.getName().startsWith("ticket-" + member.getEffectiveName())) {
                    event.reply("Vous avez déjà un ticket ouvert dans cette catégorie.").setEphemeral(true).queue();
                    return;
                }
            }

            TextChannel ticketChannel = guild.createTextChannel("ticket-" + member.getEffectiveName(), category)
                    .addPermissionOverride(member, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_ADD_REACTION), null)
                    .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                    .complete();
            MessageCreateData welcomeMessage = new MessageCreateBuilder()
                    .setContent("> Bienvenue dans votre ticket, " + member.getEffectiveName() + "!\nUn membre de notre équipe de support va vous prendre en charge, veuillez patienter.")
                    .addActionRow(Button.danger("close_ticket", "Clôturer le ticket"))
                    .build();
            ticketChannel.sendMessage(welcomeMessage).queue();
            event.reply("Votre ticket a été créé: " + ticketChannel.getAsMention()).setEphemeral(true).queue();
        } else if (event.getButton().getId().equals("close_ticket")) {
            TextChannel ticketChannel = event.getChannel().asTextChannel();
            ticketChannel.delete().queue();
            event.reply("Le ticket a été clôturé.").setEphemeral(true).queue();
        }
    }
}
