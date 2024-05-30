package HG.habilidades;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import HG.manager.KitManager;

public class Switcher implements Listener {
	@EventHandler
	public void Bolas(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player hit = (Player) e.getEntity();
			Entity snowball = e.getDamager();
			if ((snowball instanceof Snowball)) {
				Player p = (Player) ((Snowball) snowball).getShooter();
				if (KitManager.getKit(p).equals("Switcher")) {
					Location l = p.getLocation();
					p.teleport(hit);
					hit.teleport(l);
					Bukkit.getWorld("world").playEffect(
							p.getLocation().add(0, 1, 0), Effect.ENDER_SIGNAL,
							1);
				}
			}
		}
	}
}