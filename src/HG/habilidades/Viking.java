package HG.habilidades;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import HG.manager.KitManager;

public class Viking implements Listener {

	@EventHandler
	public void onDamageViking(EntityDamageByEntityEvent event) {
		if ((event.getDamager() instanceof Player)) {
			Player p = (Player) event.getDamager();
			if (KitManager.getKit(p).equals("Viking")) {
				ItemStack item = ((Player) event.getDamager()).getItemInHand();
				if ((item != null) && (item.getType().name().contains("AXE")))
					event.setDamage(event.getDamage() + 1.5D);
			}
		}
	}
}
