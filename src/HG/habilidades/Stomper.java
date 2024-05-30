package HG.habilidades;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.manager.PlayerManager;
import HG.utils.Status;

public class Stomper implements Listener {

	@EventHandler
	public void stomper(EntityDamageEvent event) {
		if (GameManager.status == Status.PréJogo || GameManager.status == Status.Invencibilidade
				|| event.isCancelled()) {
			return;
		}
		if (event.getCause() == DamageCause.FALL && event.getEntity() instanceof Player
				&& KitManager.getKit((Player) event.getEntity()).equals("Stomper")) {
			Player p = (Player) event.getEntity();
			if (((Damageable) p).getHealth() > 4.0D) {
				event.setCancelled(true);
				p.damage(4.0D);
				for (Entity e : p.getNearbyEntities(5, 2, 5)) {
					if (e instanceof Player && PlayerManager.inGame((Player) e)) {
						if (((Player) e).isSneaking()) {
							((Player) e).damage(4.0D, p);
						} else {
							((Player) e).damage(event.getDamage(), p);
						}
					}
				}
			} else {
				event.setCancelled(false);
			}
		}
	}
}
