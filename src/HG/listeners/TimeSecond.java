package HG.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import HG.Main;
import HG.barapi.BarAPI;
import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.manager.PlayerManager;
import HG.manager.VersionManager;
import HG.utils.Status;
import HG.utils.TimeSecondEvent;

public class TimeSecond implements Listener {
	static ArrayList<Player> vendo = new ArrayList<>();

	@EventHandler
	public void TimeSecondEvent(TimeSecondEvent e) {
		for (Player p : PlayerManager.getGame()) {
			if (KitManager.cooldown.containsKey(p)) {
				KitManager.cooldown.put(p, KitManager.cooldown.get(p) - 1);
				if (KitManager.cooldown.get(p) < 1) {
					KitManager.cooldown.remove(p);
				}
			}
		}
		for (final Player p : Bukkit.getOnlinePlayers()) {
			if ((p.getLocation().getBlockX() > 500) || (p.getLocation().getBlockX() < -500)
					|| (p.getLocation().getBlockZ() > 500) || (p.getLocation().getBlockZ() < -500)) {
				if (GameManager.status == Status.PréJogo) {
					Random r = new Random();
					int x = r.nextInt(15 - r.nextInt(10));
					int z = r.nextInt(15 - r.nextInt(10));
					int y = p.getWorld().getHighestBlockYAt(x, z) + 15;
					p.teleport(new Location(p.getWorld(), x, y, z));
				} else {
					if (PlayerManager.inGame(p)) {
						p.damage(4.0D);
					}
					p.sendMessage("§4Você está fora da borda do mundo!");
				}
				return;
			} else if ((p.getLocation().getBlockX() > 479) || (p.getLocation().getBlockX() < -479)
					|| (p.getLocation().getBlockZ() > 479) || (p.getLocation().getBlockZ() < -479)) {
				p.sendMessage("§6Você está próximo a borda do mundo!");
				return;
			}
			if (GameManager.status == Status.Jogo) {
				if (!vendo.contains(p)) {
					vendo.add(p);
					new BukkitRunnable() {
						public void run() {
							if (vendo.contains(p)) {
								vendo.remove(p);
							}
						}
					}.runTaskLater(Main.instance, 60);
					for (Entity entity : p.getNearbyEntities(5, 5, 5)) {
						if (entity instanceof Player) {
							Player player = (Player) entity;
							if (PlayerManager.inGame(player)) {
								BarAPI.setMessage(p, player.getName() + " - " + KitManager.playerKit(player), 3);
								VersionManager.sendActionBar(p, player.getName() + " - " + KitManager.playerKit(player));
								new BukkitRunnable() {
									public void run() {
										VersionManager.clear(p);
									}
								}.runTaskLater(Main.instance, 60);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	void PlayerMoveEvent(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.getLocation().getY() > 139) {
			if (!PlayerManager.inGame(p)) {
				ArrayList<Player> online = new ArrayList<>();
				for (Player gamers : PlayerManager.getGame()) {
					if (!online.contains(gamers)) {
						online.add(gamers);
					}
				}
				p.teleport(online.get(new Random().nextInt(online.size())));
			}
		}
	}
}