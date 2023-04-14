package me.balbucio.recado;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Evento implements Listener{

	@EventHandler
	public void OnlineToggle(PostLoginEvent e) {
		ProxiedPlayer p = e.getPlayer();
		if(!p.hasPermission("brecado.ignore")) {
			Main.getInstance().view++;
			if(!Main.getInstance().mensagem.isEmpty() || !(Main.getInstance().mensagem.length() == 0)) {
		p.sendMessage(new TextComponent(""));
		p.sendMessage(new TextComponent(Main.getInstance().mensagem));

		if(Main.getInstance().action) {
			if(Main.getInstance().type.equalsIgnoreCase("server")) {
				TextComponent message = new TextComponent(Main.getInstance().prefix + Main.getInstance().actions.get("serveraction"));
				message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/recado ir"));
				p.sendMessage(message);
			} else if(Main.getInstance().type.equalsIgnoreCase("tell")) {
				TextComponent message = new TextComponent(Main.getInstance().prefix + Main.getInstance().actions.get("tellaction"));
				message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell "+Main.getInstance().type_arguments));
				p.sendMessage(message);
			} else if(Main.getInstance().type.equalsIgnoreCase("link")) {
				TextComponent message = new TextComponent(Main.getInstance().prefix + Main.getInstance().actions.get("linkaction"));
				message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Main.getInstance().type_arguments));
				p.sendMessage(message);
			}
		}
		p.sendMessage(new TextComponent(""));
		}
		}
	}

}
