package me.balbucio.api;
import me.balbucio.recado.Main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class RecadoAPI {
	
	public static String getRecado() {
		return Main.getInstance().mensagem;
	}
	public static boolean hasRecado() {
		if(Main.getInstance().mensagem.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	public static void resetRecado() {
		Main.getInstance().mensagem = null;
	}
	public static String getAuthor() {
		return Main.getInstance().autor;
	}
	public static int getViews() {
		return Main.getInstance().view;
	}
	public static void setRecado(String recado) {
		Main.getInstance().mensagem = recado;
	}
	public static void setAuthor(String autor) {
		Main.getInstance().autor = autor;
	}
	public static boolean hasAction() {
		return Main.getInstance().action;
	}
	public static void sendRecado() {
		for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
			Main.getInstance().view++;
			all.sendMessage(new TextComponent(Main.getInstance().msg_7));
			all.sendMessage(new TextComponent(Main.getInstance().prefix + Main.getInstance().mensagem));
		}
	}
	public static String getPrefix() {
		return Main.getInstance().prefix;
	}
	public static void setPrefix(String prefix) {
		Main.getInstance().prefix = prefix;
	}
	public static void resetPrefix() {
		Main.getInstance().prefix = "§f[§aRECADO§f]";
		
	}
	public static String getSecondCommand() {
		return Main.getInstance().comando;
	}
 
}
