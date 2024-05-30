package HG.habilidades;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import HG.manager.KitManager;

public class Specialist implements Listener {
	@EventHandler
	public void asdasda(ExpBottleEvent e) {
		if (e.getEntity() instanceof Player) {
			if (KitManager.getKit((Player) e.getEntity()).equals("Specialist")) {
				e.setExperience(e.getExperience() * 2);
			}
		}
	}

	@EventHandler
	public void InteractOpen(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (KitManager.getKit(p).equals("Specialist")) {
			if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))) {
				if ((e.getPlayer().getItemInHand().getType().equals(Material.BOOK))) {
					e.setCancelled(true);
					p.openEnchanting(p.getLocation(), true);
				}
			}
		}
	}
}