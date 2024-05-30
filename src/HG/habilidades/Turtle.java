package HG.habilidades;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import HG.manager.KitManager;

public class Turtle implements Listener {

	public static ArrayList<String> Dano = new ArrayList<String>();

	@EventHandler
	public void viper(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player d = (Player) e.getDamager();
			if (KitManager.getKit(d).equals("Turtle")) {
				if (Dano.contains(d.getName())) {
					e.setDamage(1.0D);
					return;
				}
			}
		}
	}

	@EventHandler
	public void De4(PlayerToggleSneakEvent event) {
		Player p = event.getPlayer();
		if (KitManager.getKit(p).equals("Turtle")) {
			if ((!p.isSneaking())) {
				if (!Dano.contains(p.getName())) {
					Dano.add(p.getName());
				}
			} else {
				if (Dano.contains(p.getName())) {
					Dano.remove(p.getName());
				}

			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		if (KitManager.getKit(p).equals("Turtle")) {
			if (p.isSneaking()) {
				e.setDamage(1.0D);
				return;
			}
		}
	}

}
