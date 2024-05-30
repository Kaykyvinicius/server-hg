package HG.habilidades;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import HG.manager.KitManager;

public class Achilles implements Listener {
	@EventHandler
	void Damage(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player)
				&& (e.getDamager() instanceof Player)) {
			Player p = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			if (KitManager.getKit(p).equals("Achilles")) {
				if ((damager.getItemInHand().getType() != Material.AIR)
						|| (damager.getItemInHand().getType() != Material.WOOD_PICKAXE)
						|| (damager.getItemInHand().getType() != Material.WOOD_AXE)
						|| (damager.getItemInHand().getType() != Material.WOOD_SWORD)) {
					damager.sendMessage("§cYou're attacking a Achilles, use a wooden sword for extra damage.");
				}
				if (damager.getItemInHand().getType() == Material.STONE_SWORD) {
					e.setDamage(2.0D);
				} else if (damager.getItemInHand().getType() == Material.IRON_SWORD) {
					e.setDamage(3.0D);
				} else if (damager.getItemInHand().getType() == Material.DIAMOND_SWORD) {
					e.setDamage(4.0D);
				} else if ((damager.getItemInHand().getType() == Material.WOOD_PICKAXE)
						|| (damager.getItemInHand().getType() == Material.WOOD_AXE)
						|| (damager.getItemInHand().getType() == Material.WOOD_SWORD)) {
					e.setDamage(7.0D);
				}
			}
		}
	}
}