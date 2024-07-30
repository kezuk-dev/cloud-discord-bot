package dev.kezuk.listener;

import java.time.OffsetDateTime;

import dev.kezuk.CloudBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinListener extends ListenerAdapter {

    private static final String WELCOME_CHANNEL_ID = CloudBot.getWelcomeChannelId();
    private static final String DEFAULT_ROLE_ID = CloudBot.getAutoRankId();

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member member = event.getMember();
        Guild guild = event.getGuild();

        TextChannel welcomeChannel = guild.getTextChannelById(WELCOME_CHANNEL_ID);
        if (welcomeChannel != null) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(0xf22613);
            builder.setThumbnail("http://image.png");
            builder.setTitle("Bienvenue sur notre discord");
            builder.setFooter("Cloud | Roleplay");
            builder.setTimestamp(OffsetDateTime.now());
            builder.setDescription(" » Nos règles " + guild.getRulesChannel() + "\n » Notre boutique: cloudrp.gg/shop\n » Pour te connecter regarde dans le canal se-connecter\n\n • Nous te souhaitons bon vivre dans notre ville!");

            welcomeChannel.sendMessageEmbeds(builder.build()).queue();
        }

        Role defaultRole = guild.getRoleById(DEFAULT_ROLE_ID);
        if (defaultRole != null) {
            guild.addRoleToMember(member, defaultRole).queue();
        }
    }
}