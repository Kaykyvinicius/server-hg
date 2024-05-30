package HG.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import HG.Main;
import HG.manager.GameManager;
import HG.manager.PlayerManager;

public class ArenaSpawn {
	public static Integer arenasheld = null;

	public static void create() {
		arenasheld = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
			Integer contagem = 301;
			public void run() {
				contagem--;
				if (contagem == 300 || contagem == 240 || contagem == 180 || contagem == 120 || contagem == 60 || contagem == 45
						|| contagem == 30 || contagem == 15 || contagem == 10 || contagem < 6 && contagem > 0) {
					Bukkit.broadcastMessage("§cArena final começa em " + GameManager.time(contagem) + ".");
				}
				if (contagem == 0) {
					criarAreaBatle();
					cancel();
				}
			}
		}, 0L, 20L);
	}

	public static void AreaBattle(Location loc, int r, Material mat, int alturaY) {
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();
		World w = loc.getWorld();
		int rSquared = r * r;
		for (int x = cx - r; x <= cx + r; x++) {
			for (int z = cz - r; z <= cz + r; z++) {
				for (int y = cy + 1; y <= cy + alturaY; y++) {
					if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared) {
						w.getBlockAt(x, y, z).setType(mat);
					}
				}
			}
		}
	}

	public static void criarAreaBatle() {
		World world = Bukkit.getWorld("world");
		for (int x = -41; x <= 41; x++) {
			if ((x == -41) || (x == 41)) {
				for (int z = -41; z <= 41; z++) {
					for (int y = 0; y <= 199; y++) {
						world.getBlockAt(x, y, z).setType(Material.BEDROCK);
					}
				}
			}
		}
		for (int z = -41; z <= 41; z++) {
			if ((z == -41) || (z == 41)) {
				for (int x = -41; x <= 41; x++) {
					for (int y = 0; y <= 199; y++) {
						world.getBlockAt(x, y, z).setType(Material.BEDROCK);
					}
				}
			}
		}
		AreaBattle(new Location(world, 0, 6, 0), 30, Material.AIR, 140);
		AreaBattle(new Location(world, 0, 5, 0), 7, Material.STONE, 1);
		world.getChunkAt(world.getBlockAt(0, 64, 0).getLocation()).load(true);
		for (Player gamers : PlayerManager.getGame()) {
			gamers.teleport(new Location(world, 0, 8, 0));
			world.setTime(20000L);
			for (Entity e : world.getEntities()) {
				if ((e instanceof Monster) || (e instanceof Item)) {
					e.remove();
				}
			}
			gamers.showPlayer(gamers);
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!PlayerManager.inGame(player)) {
				player.teleport(new Location(world, 0, 20, 0));
			}
			Invisivel.Retomar();
		}
	}

	protected static void cancel() {
		if (arenasheld != null) {
			Bukkit.getServer().getScheduler().cancelTask(arenasheld);
			arenasheld = null;
		}
	}
}
