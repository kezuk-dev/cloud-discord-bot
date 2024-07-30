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
            builder.setTitle("Bienvenue sur notre discord " + member.getEffectiveName());
            builder.setFooter("Cloud | Roleplay");
            builder.setTimestamp(OffsetDateTime.now());
            builder.setThumbnail("https://cdn.discordapp.com/attachments/1003259897959415951/1267460071642632232/Banner_512x128.png?ex=66aa2f2d&is=66a8ddad&hm=5d44680eccebe70fc4d60421a0249237ead83b3c4c57a51b282e27160b228156&");
            builder.setDescription("\n » Notre boutique: http://www.cloudrp.gg/shop\n » Pour te connecter regarde dans le canal se-connecter\n\n • Nous te souhaitons bon vivre dans notre ville " + member.getAsMention() + "!");
            welcomeChannel.sendMessageEmbeds(builder.build()).queue();
        }

        Role defaultRole = guild.getRoleById(DEFAULT_ROLE_ID);
        if (defaultRole != null) {
            guild.addRoleToMember(member, defaultRole).queue();
        }
    }
}