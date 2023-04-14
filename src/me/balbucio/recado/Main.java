package me.balbucio.recado;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin{
	private static Main instance;
	public String mensagem = "";
	public String autor;
	public int view = 0;
	String msg_1 = "";
	String msg_2 = "";
	String msg_3 = "";
	String msg_4 = "";
	String msg_5 = "";
	String msg_6 = "";
	public String msg_7 = "";
	String msg_8 = "";
	String msg_9 = "";
	public String prefix = "";
	public String comando = "rcd";
	boolean anunciar = false;
	boolean trocar = false;
	public boolean action = false;
	String type = "";
	String type_arguments = "";
	public static File backup_file;
	public HashMap<String, String> actions = new HashMap<>();
	
	public void onEnable() {
		setInstance(this);
		backup_file = new File(getDataFolder().getPath(), "backup.yml");
		BungeeCord.getInstance().pluginManager.registerCommand(this, new Recado());
		File file = new File(getDataFolder().getPath(), "config.yml");
		try {
			if(!getDataFolder().exists() || !file.exists()) {
				getDataFolder().mkdir();
				file.createNewFile();
				file.mkdir();
				Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
				config.set("stats", "§eTotal de players que viram o anúncio: ");
	            config.set("sucesso", "§aO recado foi setado com sucesso!");
	            config.set("reset", "§cO recado foi resetado com sucesso!");
	            config.set("null", "§cAo momento não há nenhum recado!");
	            config.set("autor", "§aCriador do último recado: ");
	            config.set("backup", "§aBackup criado com sucesso!");
	            config.set("reenviado", "§7Este recado está sendo reenviado!");
	            config.set("reenviadosucess", "§aReenviado com sucesso!");
	            config.set("actionerror", "§cUse corretamente o Action, você informou um argumento inválido!");
	            config.set("prefix", "§f[§aRECADO§f] ");
	            config.set("anunciar", false);
				ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
			} 
			Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			this.msg_1 = (String) config.get("stats");
	        this.msg_2 = (String) config.get("sucesso");
	        this.msg_3 = (String) config.get("reset");
	        this.msg_4 = (String) config.get("null");
	        this.msg_5 = (String) config.get("autor");
	        this.msg_6 = (String) config.get("backup");
	        this.msg_7 = config.getString("reenviado");
	        this.msg_8 = config.getString("reenviadosucess");
	        this.msg_9 = config.getString("actionerror");
	        this.prefix = (String) config.get("prefix");
	        this.anunciar = (boolean) config.getBoolean("anunciar");
			createActionsMessages();
		} catch(IOException e) {
			BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§c[BalbucioRecado] Ocorreu um erro grave ao criar os arquivos!"));
			e.printStackTrace();
		}
		BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("[BalbucioRecado] §2Ativado com sucesso!"));
	}
	public void createActionsMessages() {
		File file = new File(getDataFolder().getPath(), "actions.yml");
		try {
			if(!getDataFolder().exists() || !file.exists()) {
				getDataFolder().mkdir();
				file.createNewFile();
				file.mkdir();
				Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
				config.set("serveraction", "§e§lCLIQUE AQUI PARA IR PARA O SERVIDOR!");
				config.set("tellaction", "§e§lCLIQUE AQUI CHAMAR NO TELL!");
				config.set("linkaction", "§e§lCLIQUE AQUI PARA IR PARA O LINK!");
				config.set("notexists", "§cEsse Action não existe!");
				config.set("addsucess", "§aAction adicionada com sucesso!");
				ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
			} 
			Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			for(String s : config.getKeys()) {
				this.actions.put(s, config.getString(s));
			}
			
		} catch(IOException e) {
			BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§c[BalbucioRecado] Ocorreu um erro grave ao criar os arquivos!"));
			e.printStackTrace();
		}
	}

public static Main getInstance() {
    return instance;
}

private static void setInstance(final Main instance) {
    Main.instance = instance;
}
}