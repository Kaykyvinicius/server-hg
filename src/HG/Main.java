package HG;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import HG.barapi.BarAPI;
import HG.comandos.Admin;
import HG.comandos.Arena;
import HG.comandos.Chat;
import HG.comandos.ClearDrops;
import HG.comandos.Dano;
import HG.comandos.Desistir;
import HG.comandos.Feast;
import HG.comandos.Help;
import HG.comandos.IP;
import HG.comandos.KillMobs;
import HG.comandos.Kit;
import HG.comandos.PvP;
import HG.comandos.Random;
import HG.comandos.Score;
import HG.comandos.Skit;
import HG.comandos.Spawn;
import HG.comandos.Start;
import HG.comandos.TP;
import HG.comandos.Tempo;
import HG.comandos.ToggleKits;
import HG.comandos.Who;
import HG.habilidades.Achilles;
import HG.habilidades.Blink;
import HG.habilidades.Camel;
import HG.habilidades.Cannibal;
import HG.habilidades.Cookiemonster;
import HG.habilidades.Cultivator;
import HG.habilidades.Demoman;
import HG.habilidades.Endermage;
import HG.habilidades.Fireman;
import HG.habilidades.Fisherman;
import HG.habilidades.Forger;
import HG.habilidades.Gambler;
import HG.habilidades.Gladiator;
import HG.habilidades.Grappler;
import HG.habilidades.Jackhammer;
import HG.habilidades.Jellyfish;
import HG.habilidades.Kangaroo;
import HG.habilidades.Kaya;
import HG.habilidades.Launcher;
import HG.habilidades.Lumberjack;
import HG.habilidades.Madman;
import HG.habilidades.Magma;
import HG.habilidades.Miner;
import HG.habilidades.MinerListener;
import HG.habilidades.Ninja;
import HG.habilidades.Poseidon;
import HG.habilidades.Reaper;
import HG.habilidades.Snail;
import HG.habilidades.Specialist;
import HG.habilidades.Stomper;
import HG.habilidades.Switcher;
import HG.habilidades.Tank;
import HG.habilidades.Thor;
import HG.habilidades.Titan;
import HG.habilidades.Turtle;
import HG.habilidades.Viking;
import HG.habilidades.Viper;
import HG.kitselector.KitSelector;
import HG.listeners.AsyncPlayerChat;
import HG.listeners.Block;
import HG.listeners.EntityDamage;
import HG.listeners.FoodLevelChange;
import HG.listeners.Knockback;
import HG.listeners.MapInitialize;
import HG.listeners.PlayerDeath;
import HG.listeners.PlayerDrop;
import HG.listeners.PlayerInteract;
import HG.listeners.PlayerJoin;
import HG.listeners.PlayerLogin;
import HG.listeners.PlayerQuit;
import HG.listeners.ServerListPing;
import HG.listeners.TimeSecond;
import HG.manager.GameManager;
import HG.manager.Spectator;
import HG.mysql.SQLConnection;
import HG.mysql.SQLStatus;
import HG.utils.Sopa;
import HG.utils.TimeSecondEvent;

public class Main extends JavaPlugin {
	public static Main instance;
	public static String IP;
	public static boolean MySQL;
	public static String MySQL_IP;
	public static String MySQL_Porta;
	public static String MySQL_Database;
	public static String MySQL_User;
	public static String MySQL_Password;

	public void onEnable() {
		instance = this;
		new Sopa();
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		saveDefaultConfig();
		IP = getConfig().getString("IP");
		MySQL = getConfig().getBoolean("MySQL");
		MySQL_IP = getConfig().getString("MySQL_IP");
		MySQL_Porta = getConfig().getString("MySQL_Porta");
		MySQL_Database = getConfig().getString("MySQL_Database");
		MySQL_User = getConfig().getString("MySQL_User");
		MySQL_Password = getConfig().getString("MySQL_Password");
		SQLConnection.connectSQL();
		GameManager.preparar();
		new MinerListener();
		getCommand("admin").setExecutor(new Admin());
		getCommand("arena").setExecutor(new Arena());
		getCommand("chat").setExecutor(new Chat());
		getCommand("cleardrops").setExecutor(new ClearDrops());
		getCommand("dano").setExecutor(new Dano());
		getCommand("desistir").setExecutor(new Desistir());
		getCommand("feast").setExecutor(new Feast());
		getCommand("help").setExecutor(new Help());
		getCommand("ip").setExecutor(new IP());
		getCommand("ir").setExecutor(new TP());
		getCommand("killmobs").setExecutor(new KillMobs());
		getCommand("kit").setExecutor(new Kit());
		getCommand("pvp").setExecutor(new PvP());
		getCommand("random").setExecutor(new Random());
		getCommand("score").setExecutor(new Score());
		getCommand("setkit").setExecutor(new Kit());
		getCommand("skit").setExecutor(new Skit());
		getCommand("spawn").setExecutor(new Spawn());
		getCommand("start").setExecutor(new Start());
		getCommand("teleport").setExecutor(new TP());
		getCommand("tempo").setExecutor(new Tempo());
		getCommand("togglekits").setExecutor(new ToggleKits());
		getCommand("tpall").setExecutor(new TP());
		getCommand("tphere").setExecutor(new TP());
		getCommand("who").setExecutor(new Who());
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new Kit(), this);
		pm.registerEvents(new KitSelector(), this);
		pm.registerEvents(new AsyncPlayerChat(), this);
		pm.registerEvents(new Block(), this);
		pm.registerEvents(new EntityDamage(), this);
		pm.registerEvents(new FoodLevelChange(), this);
		pm.registerEvents(new Knockback(), this);
		pm.registerEvents(new MapInitialize(), this);
		pm.registerEvents(new PlayerDeath(), this);
		pm.registerEvents(new PlayerDrop(), this);
		pm.registerEvents(new PlayerInteract(), this);
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerLogin(), this);
		pm.registerEvents(new PlayerQuit(), this);
		pm.registerEvents(new ServerListPing(), this);
		pm.registerEvents(new TimeSecond(), this);
		pm.registerEvents(new Spectator(), this);
		pm.registerEvents(new BarAPI(), this);
		// Kits
		pm.registerEvents(new Achilles(), this);
		pm.registerEvents(new Blink(), this);
		pm.registerEvents(new Camel(), this);
		pm.registerEvents(new Cannibal(), this);
		pm.registerEvents(new Cookiemonster(), this);
		pm.registerEvents(new Cultivator(), this);
		pm.registerEvents(new Demoman(), this);
		pm.registerEvents(new Endermage(), this);
		pm.registerEvents(new Fireman(), this);
		pm.registerEvents(new Fisherman(), this);
		pm.registerEvents(new Forger(), this);
		pm.registerEvents(new Gambler(), this);
		pm.registerEvents(new Gladiator(), this);
		pm.registerEvents(new Grappler(), this);
		pm.registerEvents(new Jackhammer(), this);
		pm.registerEvents(new Jellyfish(), this);
		pm.registerEvents(new Kangaroo(), this);
		pm.registerEvents(new Kaya(), this);
		pm.registerEvents(new Launcher(), this);
		pm.registerEvents(new Lumberjack(), this);
		pm.registerEvents(new Madman(), this);
		pm.registerEvents(new Magma(), this);
		pm.registerEvents(new Miner(), this);
		pm.registerEvents(new Ninja(), this);
		pm.registerEvents(new Poseidon(), this);
		pm.registerEvents(new Reaper(), this);
		pm.registerEvents(new Snail(), this);
		pm.registerEvents(new Specialist(), this);
		pm.registerEvents(new Stomper(), this);
		pm.registerEvents(new Switcher(), this);
		pm.registerEvents(new Tank(), this);
		pm.registerEvents(new Thor(), this);
		pm.registerEvents(new Titan(), this);
		pm.registerEvents(new Turtle(), this);
		pm.registerEvents(new Viking(), this);
		pm.registerEvents(new Viper(), this);
		new BukkitRunnable() {
			public void run() {
				Bukkit.getPluginManager().callEvent(new TimeSecondEvent());
			}
		}.runTaskTimer(this, 0, 20);
	}

	public void onDisable() {
		SQLStatus.setStatusServer(0);
	}

	public void onLoad() {
		Bukkit.getServer().unloadWorld("world", false);
		deleteDir(new File("world"));
		File cake = new File(this.getDataFolder(), "Cake.png");
		if (!cake.exists()) {
			cake.getParentFile().mkdirs();
			copy(this.getResource("Cake.png"), cake);
		}
	}

	public static void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				deleteDir(new File(dir, children[i]));
			}
		}
		dir.delete();
	}
}