package HG.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import HG.Main;
import HG.manager.GameManager;
import HG.manager.PlayerManager;
import HG.mysql.SQLStatus;

public class Win {
	
	public static void checarGanhador() {
		if (PlayerManager.getGame().length == 0) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.kickPlayer("§cNenhum vencedor, obrigado por jogar");
				Bukkit.getServer().getScheduler().cancelAllTasks();
				Bukkit.getServer().shutdown();
			}
			return;
		}
		if (PlayerManager.getGame().length == 1) {
			if (GameManager.status == Status.Vitória) {
				return;
			}
			for (final Player p : PlayerManager.getGame()) {
				GameManager.status = Status.Vitória;
				Location bolo = new Location(p.getWorld(), p.getLocation().getX(), 120, p.getLocation().getZ());
				for (int x = -1; x <= 2; x++) {
					for (int z = -1; z <= 2; z++) {
						bolo.clone().add(x, 1, z).getBlock().setType(Material.GLASS);
						bolo.clone().add(x, 2, z).getBlock().setType(Material.CAKE_BLOCK);
					}
				}
				p.teleport(bolo.clone().add(0, 4, 0));
				p.sendMessage("§cVocê venceu o torneio!");
				SQLStatus.addStatus(p, "Wins");
				p.setItemInHand(new ItemStack(Material.MAP));
				new BukkitRunnable() {
					public void run() {
						Bukkit.broadcastMessage("§c" + p.getName() + " venceu!");
					}
				}.runTaskTimer(Main.instance, 0, 40);
				new BukkitRunnable() {
					public void run() {
						if (p.isOnline()) {
							ByteArrayDataOutput out = ByteStreams.newDataOutput();
							out.writeUTF("Connect");
							out.writeUTF("lobby");
							p.sendPluginMessage(Main.instance, "BungeeCord", out.toByteArray());
						}
						for (Player p2 : Bukkit.getOnlinePlayers()) {
							if (p2 != p) {
								ByteArrayDataOutput out = ByteStreams.newDataOutput();
								out.writeUTF("Connect");
								out.writeUTF("lobby");
								p2.sendPluginMessage(Main.instance, "BungeeCord", out.toByteArray());
							}
						}
					}
				}.runTaskLater(Main.instance, 600);
				new BukkitRunnable() {
					public void run() {
						SQLStatus.setStatusServer(0);
						Bukkit.getServer().shutdown();
					}
				}.runTaskLater(Main.instance, 620);
			}
		}
	}
}
