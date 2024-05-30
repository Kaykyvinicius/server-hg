package HG.habilidades;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import HG.manager.KitManager;

public class Tank implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if ((KitManager.getKit((Player) e.getEntity()).equals("Tank")) && (e.getCause() == DamageCause.ENTITY_EXPLOSION) || (e.getCause() == DamageCause.BLOCK_EXPLOSION)) {
				e.setCancelled(true);
			}
		}
	}
}
