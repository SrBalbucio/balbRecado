package me.balbucio.recado;
import java.io.IOException;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Recado extends Command{
	public Recado() {
		super("recado");
	}
	public void execute(CommandSender sender, String[] args) {
		if(args.length == 0) {
			Main.getInstance().view++;
			if(!(Main.getInstance().mensagem.length() == 0)) {
			sender.sendMessage(new TextComponent(Main.getInstance().prefix + Main.getInstance().mensagem));
			if(Main.getInstance().action) {
				if(Main.getInstance().type.equalsIgnoreCase("server")) {
					TextComponent message = new TextComponent(Main.getInstance().prefix + Main.getInstance().actions.get("serveraction"));
					message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/recado ir"));
					sender.sendMessage(message);
				} else if(Main.getInstance().type.equalsIgnoreCase("tell")) {
					TextComponent message = new TextComponent(Main.getInstance().prefix + Main.getInstance().actions.get("tellaction"));
					message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell "+Main.getInstance().type_arguments));
					sender.sendMessage(message);
				} else if(Main.getInstance().type.equalsIgnoreCase("link")) {
					TextComponent message = new TextComponent(Main.getInstance().prefix + Main.getInstance().actions.get("linkaction"));
					message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Main.getInstance().type_arguments));
					sender.sendMessage(message);
				}
			}
			} else {
				sender.sendMessage(new TextComponent(Main.getInstance().msg_4));
			}
		}
		else if(args.length > 1 && args[0].equalsIgnoreCase("criar")) {
			if(sender.hasPermission("brecado.use") || sender instanceof ProxiedPlayer) {
			String message =  "";
			for(int i= 1; i < args.length; i ++) {
				message = message +" "+ args[i];
				Main.getInstance().mensagem = message;
			}
			if(Main.getInstance().anunciar) {
			for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
				Main.getInstance().view++;
				all.sendMessage(new TextComponent(Main.getInstance().prefix + Main.getInstance().mensagem));
			}
			}
			Main.getInstance().autor = sender.getName();
			sender.sendMessage(new TextComponent(Main.getInstance().msg_2));
		}
		}
		else if(args.length == 1 && args[0].equalsIgnoreCase("reset")) {
			if(sender.hasPermission("brecado.use") || sender instanceof ProxiedPlayer) {
			Main.getInstance().mensagem = "";
			sender.sendMessage(new TextComponent(Main.getInstance().msg_3));
			}
		}
		else if(args.length == 1 && args[0].equalsIgnoreCase("stats")) {
			if(sender.hasPermission("brecado.use") || sender instanceof ProxiedPlayer) {
			sender.sendMessage(new TextComponent(Main.getInstance().msg_1 + " "+ Main.getInstance().view));
			}
			
		}
		else if(args.length == 1 && args[0].equalsIgnoreCase("backup")) {
			if(sender.hasPermission("brecado.use") || sender instanceof ProxiedPlayer) {
				try {
					    Main.backup_file.createNewFile();
					    Main.backup_file.mkdir();
						Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(Main.backup_file);
						config.set("BackupUser", sender.getName());
						config.set("autor", Main.getInstance().autor);
						config.set("mensagem", Main.getInstance().mensagem);
						ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, Main.backup_file);
						sender.sendMessage(new TextComponent(Main.getInstance().msg_6));
					
				} catch(IOException e) {
					sender.sendMessage(new TextComponent("§cOcorreu um erro ao criar o backup! (BACKUP ERROR)"));
					BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§c[BalbucioRecado] Ocorreu um erro grave ao criar os arquivos!"));
					e.printStackTrace();
				}
			
		}
		}
		else if(args.length == 1 && args[0].equalsIgnoreCase("autor")) {
			if(Main.getInstance().autor.length() == 0) {
			sender.sendMessage(new TextComponent(Main.getInstance().msg_4));
			} else {
				sender.sendMessage(new TextComponent(Main.getInstance().msg_5 + " "+ Main.getInstance().autor));
			}
		}
		else if(args.length == 1 && args[0].equalsIgnoreCase("anunciar")) {
			if(sender.hasPermission("brecado.use") || sender instanceof ProxiedPlayer) {
			if(!Main.getInstance().mensagem.isEmpty()) {
				sender.sendMessage(new TextComponent(Main.getInstance().msg_8));
			for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
				Main.getInstance().view++;
				all.sendMessage(new TextComponent(Main.getInstance().msg_7));
				all.sendMessage(new TextComponent(Main.getInstance().prefix + Main.getInstance().mensagem));
			}
			} else {
				sender.sendMessage(new TextComponent(Main.getInstance().msg_4));
			}
			}
		}
		else if(args.length >= 2 && args[0].equalsIgnoreCase("addaction")) {
			if(sender.hasPermission("brecado.use") || sender instanceof ProxiedPlayer) {
				String message;
				message = args[1].toString();
				if(message.equalsIgnoreCase("server")) {
					String server;
					server = args[2].toString();
					if(!server.isEmpty()) {
					ServerInfo servidor = BungeeCord.getInstance().getServerInfo(server);
						if(servidor == null){
							sender.sendMessage(new TextComponent(Main.getInstance().msg_9));
						} else {
							Main.getInstance().action = true;
							Main.getInstance().type = "server";
							Main.getInstance().type_arguments = server;
							sender.sendMessage(new TextComponent(Main.getInstance().actions.get("addsucess")));
						}
						} else {
							sender.sendMessage(new TextComponent(Main.getInstance().msg_9));
					}
				} else if(message.equalsIgnoreCase("tell")) {
					String tell;
					tell = args[2].toString();
					if(!tell.isEmpty()) {
						Main.getInstance().action = true;
						Main.getInstance().type = "tell";
						Main.getInstance().type_arguments = tell;
						sender.sendMessage(new TextComponent(Main.getInstance().actions.get("addsucess")));
					} else {
						sender.sendMessage(new TextComponent(Main.getInstance().msg_9));
				}
				} else if(message.equalsIgnoreCase("link")) {
					String link;
					link = args[2].toString();
					if(!link.isEmpty()) {
						Main.getInstance().action = true;
						Main.getInstance().type = "link";
						Main.getInstance().type_arguments = link;
						sender.sendMessage(new TextComponent(Main.getInstance().actions.get("addsucess")));
					} else {
						sender.sendMessage(new TextComponent(Main.getInstance().msg_9));
				}
				} else {
					sender.sendMessage(new TextComponent(Main.getInstance().actions.get("notexists")));
				}
			}
		}
		else if(args.length == 1 && args[0].equalsIgnoreCase("ir")){
			if(Main.getInstance().action && Main.getInstance().type.equalsIgnoreCase("server")){
			ServerInfo servidor = BungeeCord.getInstance().getServerInfo(Main.getInstance().type_arguments);
			ProxiedPlayer player = BungeeCord.getInstance().getPlayer(sender.getName());
			player.connect(servidor);
			}
			
		}
		else if(args.length == 1 && args[0].equalsIgnoreCase("actioninfo")){
			if(sender.hasPermission("brecado.use")) {
				if(Main.getInstance().action) {
				sender.sendMessage(new TextComponent("§bAction Type: " + Main.getInstance().type));
				sender.sendMessage(new TextComponent("§bType Arguments: " + Main.getInstance().type_arguments));
				} else {
					sender.sendMessage(new TextComponent("§cActions Null!"));
				}
			}
		} 
	}
}
