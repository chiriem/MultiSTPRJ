package kopo.poly.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class STbot extends ListenerAdapter {
    public static void main(String[] args)
            throws LoginException {
        JDA jda = JDABuilder.createDefault("OTc2MzUwOTAxNDQwNzQ5NTk5.GYdDjT.62TjTHqnsEVMhImyx5RM3Mqgr8u1C1Kb3GRdbo").build();
        //You can also add event listeners to the already built JDA instance
        // Note that some events may not be received if the listener is added after calling build()
        // This includes events such as the ReadyEvent
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.addEventListener(new STbot());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        TextChannel tc = event.getTextChannel();
        Message msg = event.getMessage();
        if(user.isBot()) return;
        if(msg.getContentRaw().contains("yt notify add")){
            String[] args = msg.getContentRaw().split("yt notify add ");
            if(args.length <= 0) return;
            tc.sendMessage(args[1] + "Channel Successfully Over! " + user.getAsMention()).queue();
        }
    }
}
