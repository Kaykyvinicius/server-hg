package HG.habilidades;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import HG.manager.KitManager;

public class Fireman implements Listener {

	@EventHandler
	public void onFireman(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (KitManager.getKit((Player)e.getEntity()).equals("Fireman")) {
				if (e.getCause() == DamageCause.FIRE_TICK || e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.LAVA) {
					e.setCancelled(true);
				}
			}
		}
	}
}
