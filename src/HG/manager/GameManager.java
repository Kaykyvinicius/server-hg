package HG.manager;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import HG.Main;
import HG.listeners.PlayerDeath;
import HG.mysql.SQLStatus;
import HG.utils.ArenaSpawn;
import HG.utils.Feast;
import HG.utils.MiniFeast;
import HG.utils.ScoreBoard;
import HG.utils.Status;
import HG.utils.Win;

public class GameManager {
	public static Status status;
	public static Integer task = null;
	public static Integer pre = 301;
	public static Integer inven = 121;
	public static Integer game = 119;
	static ArrayList<Player> win = new ArrayList<>();

	public static void preparar() {
		status = Status.PréJogo;
		World world = Bukkit.getWorld("world");
		world.setDifficulty(Difficulty.HARD);
		for (int x = -501; x <= 500; x++) {
			if ((x == -501) || (x == 500)) {
				for (int z = -500; z <= 500; z++) {
					for (int y = 0; y <= 199; y++) {
						Location loc = new Location(world, x, y, z);
						if (!loc.getChunk().isLoaded()) {
							loc.getChunk().load();
						}
						loc.getBlock().setType(Material.BEDROCK);
					}
				}
			}
		}
		for (int z = -501; z <= 500; z++) {
			if ((z == -501) || (z == 500)) {
				for (int x = -500; x <= 500; x++) {
					for (int y = 0; y <= 199; y++) {
						Location loc = new Location(world, x, y, z);
						if (!loc.getChunk().isLoaded()) {
							loc.getChunk().load();
						}
						loc.getBlock().setType(Material.BEDROCK);
					}
				}
			}
		}
		ConsoleCommandSender s = Bukkit.getConsoleSender();
		s.sendMessage("§eIniciando carregamento de chunks [-500/500]");
		/*
		 * for (int x = -500; x < 500; x++) { for (int z = -500; z < 500; z++) {
		 * if (!world.getChunkAt(world.getBlockAt(x, 100,
		 * z).getLocation()).isLoaded()) { world.getChunkAt(world.getBlockAt(x,
		 * 100, z).getLocation()).load(true); } } }
		 */
		s.sendMessage("§aChunks carregadas! [-500/500]");
		SQLStatus.setStatusServer(1);
		preGame();
	}

	public static void preGame() {
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
			public void run() {
				pre--;
				for (Player p : Bukkit.getOnlinePlayers()) {
					ScoreBoard.Score(p);
					VersionManager.sendTab(p);
				}
				if (pre == 300 || pre == 240 || pre == 180 || pre == 120 || pre == 60 || pre == 45 || pre == 30
						|| pre == 15 || pre == 10 || pre < 6 && pre > 0) {
					Bukkit.broadcastMessage("§cTorneio iniciará em " + time(pre));
				}
				if (pre == 20) {
					if (PlayerManager.getGame().length < 2) {
						pre = 301;
						Bukkit.broadcastMessage("§cJogadores insuficientes para criar a arena!");
					} else {
						for (Player p : PlayerManager.getGame()) {
							ArenaSpawn.AreaBattle(
									new Location(p.getWorld(), 0, p.getWorld().getHighestBlockYAt(0, 0) + 30, 0), 20,
									Material.GLASS, 1);
							ArenaSpawn.AreaBattle(
									new Location(p.getWorld(), 0, p.getWorld().getHighestBlockYAt(0, 0) + 30, 0), 20,
									Material.GLASS, 6);
							ArenaSpawn.AreaBattle(
									new Location(p.getWorld(), 0, p.getWorld().getHighestBlockYAt(0, 0) + 31, 0), 19,
									Material.AIR, 6);
							p.teleport(new Location(p.getWorld(), 0, p.getWorld().getHighestBlockYAt(0, 0) + 32, 0));
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 99999));
							p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 99999));
						}
					}
				}
				if (pre == 0) {
					for (Player p : PlayerManager.getGame()) {
						for (PotionEffect potion : p.getActivePotionEffects()) {
							p.removePotionEffect(potion.getType());
						}
						ArenaSpawn.AreaBattle(
								new Location(p.getWorld(), 0, p.getWorld().getHighestBlockYAt(0, 0) + 30, 0), 20,
								Material.AIR, 6);
					}
					if (PlayerManager.getGame().length < 2) {
						pre = 301;
						Bukkit.broadcastMessage("§cJogadores insuficientes!");
					} else {
						cancelTask();
						StartGame();
					}
				}
			}
		}, 0, 20);
	}

	public static void StartGame() {
		for (Player p : PlayerManager.getGame()) {
			p.spigot().setCollidesWithEntities(true);
			for (PotionEffect potion : p.getActivePotionEffects()) {
				p.removePotionEffect(potion.getType());
			}
			ArenaSpawn.AreaBattle(new Location(p.getWorld(), 0, p.getWorld().getHighestBlockYAt(0, 0) + 30, 0), 20,
					Material.AIR, 6);
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.setFoodLevel(20);
			p.setHealth(20.0D);
			p.setAllowFlight(false);
			p.setFlying(false);
			KitManager.giveKit(p);
			p.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
			p.closeInventory();
			p.playSound(p.getLocation(), Sound.WITHER_DEATH, 1f, 1f);
			/*
			 * Random r = new Random(); int x = r.nextInt(15 - r.nextInt(10));
			 * int z = r.nextInt(15 - r.nextInt(10)); int y =
			 * p.getWorld().getHighestBlockYAt(x, z) + 15; p.teleport(new
			 * Location(p.getWorld(), x, y, z));
			 */
			VersionManager.sendTitle(p, "§cO torneio começou!");
		}
		((World) Bukkit.getServer().getWorlds().get(0)).setTime(0L);
		Bukkit.broadcastMessage("§cA partida começou!");
		Bukkit.broadcastMessage("§c" + PlayerManager.getGame().length + " jogadores participantes.");
		Bukkit.broadcastMessage("§cTodos estão invencíveis por 2 minutos.");
		Bukkit.broadcastMessage("§cBoa sorte!");
		startInvencibility();
		SQLStatus.setStatusServer(2);
	}

	public static void startInvencibility() {
		status = Status.Invencibilidade;
		task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
			public void run() {
				inven--;
				for (Player p : Bukkit.getOnlinePlayers()) {
					ScoreBoard.Score(p);
					VersionManager.sendTab(p);
				}
				if (inven == 300 || inven == 240 || inven == 180 || inven == 120 || inven == 60 || inven == 45
						|| inven == 30 || inven == 15 || inven == 10 || inven < 6 && inven > 0) {
					Bukkit.broadcastMessage("§cA invencibilidade acaba em " + time(inven));
				}
				if (inven == 0) {
					Bukkit.broadcastMessage("§cA invencibilidade acabou.");
					for (Player on : PlayerManager.getGame()) {
						on.playSound(on.getLocation(), Sound.ANVIL_LAND, 1f, 1f);
					}
					cancelTask();
					contagemJogo();
					SQLStatus.setStatusServer(3);
					status = Status.Jogo;
					Win.checarGanhador();
				}
			}
		}, 0L, 20);
	}

	public static void contagemJogo() {
		task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
			public void run() {
				game++;
				for (Player p : Bukkit.getOnlinePlayers()) {
					ScoreBoard.Score(p);
					VersionManager.sendTab(p);
				}
				if (game == 300) {
					MiniFeast.create();
				}
				if (game == 720) {
					MiniFeast.create();
				}
				if (game == 900) {
					Feast.create(0, 0);
				}
				if (game == 1140) {
					MiniFeast.create();
				}
				if (game == 1560) {
					MiniFeast.create();
				}
				if (game == 2700) {
					ArenaSpawn.create();
				}
				if (game == 3600) {
					int kills = 0;
					for (int i : PlayerDeath.kills.values()) {
						if (i > kills) {
							kills = i;
						}
					}
					for (final Player gamers : PlayerManager.getGame()) {
						win.add(gamers);
						if (PlayerDeath.kills.get(gamers.getUniqueId()) != kills) {
							PlayerManager.addSpec(gamers);
							win.remove(gamers);
							Win.checarGanhador();
						}
					}
					Player vencedor = win.get(new Random().nextInt(win.size()));
					for (final Player gamers : PlayerManager.getGame()) {
						if (gamers != vencedor) {
							PlayerManager.addSpec(gamers);
							Win.checarGanhador();
						}
					}
					Bukkit.broadcastMessage("§c" + vencedor.getName() + " ganhou por ter o maior número de kills!");
				}
			}
		}, 0, 20);
	}

	public static void cancelTask() {
		Bukkit.getScheduler().cancelTask(task);
	}

	public static String time(Integer i) {
		String add = "";
		if (i.intValue() >= 60) {
			Integer time = Integer.valueOf(i.intValue() / 60);
			if (time.intValue() > 1) {
				add = "s";
			}
			return time + " minuto" + add;
		}
		if (i > 1) {
			add = "s";
		}
		return i + " segundo" + add;
	}
}