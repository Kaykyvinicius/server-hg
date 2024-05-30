package HG.habilidades;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import HG.Main;
import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.utils.Status;

public class Jackhammer implements Listener {

	public static HashMap<String, Integer> Usou = new HashMap<String, Integer>();

	@EventHandler
	public void Jack(BlockBreakEvent e) {
		Player p = e.getPlayer();
		final Block bloco = e.getBlock();
		final Block cima = bloco.getRelative(BlockFace.UP);
		final Block baixo = bloco.getRelative(BlockFace.DOWN);
		if (p.getItemInHand().getType() == Material.STONE_AXE) {
			if (KitManager.getKit(p).equals("Jackhammer")) {
				if (!(GameManager.status == Status.Invencibilidade)) {
					if (KitManager.hasCooldown(p)) {
						e.setCancelled(true);
					} else {
						if (Usou.get(p.getName()) == null)
							Usou.put(p.getName(), Integer.valueOf(0));

						new BukkitRunnable() {
							Block b = cima;

							public void run() {
								if (b.getType() != Material.AIR) {
									b.setType(Material.AIR);
									b = b.getRelative(BlockFace.UP);
								} else
									cancel();
							}
						}.runTaskTimer(Main.instance, 2, 1);
						new BukkitRunnable() {
							Block b = baixo;

							public void run() {
								if (b.getType() != Material.BEDROCK) {
									b.setType(Material.AIR);
									b = b.getRelative(BlockFace.DOWN);
								} else
									cancel();
							}
						}.runTaskTimer(Main.instance, 2, 1);
						Usou.put(p.getName(), Usou.get(p.getName()) + 1);
						if (Usou.get(p.getName()) >= 6) {
							KitManager.addCooldown(p, 10);
							Usou.put(p.getName(), Integer.valueOf(0));
						}
					}
				} else {
					p.sendMessage("§cYou can't use this in invencibility!");
				}
			}
		}
	}
}