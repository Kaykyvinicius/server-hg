package HG.habilidades;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import HG.Main;
import HG.kitselector.KitSelector;
import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.manager.PlayerManager;
import HG.utils.Status;
import HG.utils.TimeSecondEvent;

public class Titan implements Listener {
	static ArrayList<Player> titan = new ArrayList<>();
	static HashMap<Player, Integer> cooldown = new HashMap<>();

	@EventHandler
	void Titankit(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		ItemStack titanenabled = KitSelector.item(Material.BEDROCK, "§aTitan mode", 1, (short) 0, null);
		ItemStack titandisabled = KitSelector.item(Material.BEDROCK, "§cTitan mode - Needs recharging", 1, (short) 0, null);
		if (((e.getAction().name().contains("RIGHT"))) && (KitManager.getKit(p).equals("Titan"))
				&& (p.getItemInHand().equals(titandisabled))) {
			e.setCancelled(true);
		}
		if (((e.getAction().name().contains("RIGHT"))) && (KitManager.getKit(p).equals("Titan"))
				&& (p.getItemInHand().equals(titanenabled))) {
			e.setCancelled(true);
			if (GameManager.status == Status.Jogo) {
				p.setItemInHand(titandisabled);
				p.updateInventory();
				p.sendMessage("§aYou are now invincible for 10 seconds!");
				titan.add(p);
				new BukkitRunnable() {
					public void run() {
						p.sendMessage("§cYou are longer invincible!");
						titan.remove(p);
					}
				}.runTaskLater(Main.instance, 200);
			}
		}
	}

	@EventHandler
	void Titankit(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player)) {
			Player p = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			if (titan.contains(p)) {
				damager.sendMessage("Â§7This player is in Titan mode and is invnicible!");
			}
			if (titan.contains(damager)) {
				damager.sendMessage("Â§7You can't deal damage in Titan mode!");
			}
		}
	}

	@EventHandler
	void Titankit(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if ((titan.contains(p)) && (KitManager.getKit(p).equals("Titan"))) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void Titankit(TimeSecondEvent e) {
		for (final Player p : PlayerManager.getGame()) {
			ItemStack titanenabled = KitSelector.item(Material.BEDROCK, "§aTitan mode", 1, (short) 0, null);
			ItemStack titandisabled = KitSelector.item(Material.BEDROCK, "§cTitan mode - Needs recharging", 1, (short) 0, null);
			if ((!titan.contains(p)) && (KitManager.getKit(p).equals("Titan"))
					&& (p.getInventory().contains(titandisabled))) {
				if ((p.isSneaking()) && (p.isBlocking()) && (p.getItemInHand().getType().name().contains("SWORD"))) {
					if (!cooldown.containsKey(p)) {
						p.sendMessage("Â§aRecharging started...");
						cooldown.put(p, 1);
					} else {
						cooldown.put(p, cooldown.get(p) + 1);
					}
					if (cooldown.get(p) == 10) {
						p.getInventory().removeItem(titandisabled);
						p.getInventory().addItem(titanenabled);
						p.updateInventory();
						p.sendMessage("§aRecharge complete!");
						cooldown.remove(p);
					}
				} else {
					if (cooldown.containsKey(p)) {
						cooldown.remove(p);
					}
				}
			}
		}
	}
}