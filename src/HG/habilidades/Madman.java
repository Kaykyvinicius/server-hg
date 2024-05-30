package HG.habilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import HG.barapi.BarAPI;
import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.manager.PlayerManager;
import HG.manager.VersionManager;
import HG.utils.Status;
import HG.utils.TimeSecondEvent;

public class Madman implements Listener {
	public static HashMap<Player, Integer> madman = new HashMap<>();
	static List<Player> entity = new ArrayList<>();

	@EventHandler
	void MadmanKit(TimeSecondEvent e) {
		if (GameManager.status == Status.Jogo) {
			for (final Player p : PlayerManager.getGame()) {
				if (KitManager.getKit(p).equals("Madman")) {
					for (Entity ent : p.getNearbyEntities(20, 20, 20)) {
						if ((ent instanceof Player) && (PlayerManager.inGame((Player) ent))) {
							Player target = (Player) ent;
							entity.clear();
							entity.add((Player) ent);
							if (madman.containsKey(target)) {
								madman.put(target, madman.get(target) + 3);
							} else {
								madman.put(target, 3);
								target.sendMessage("um madman está por perto");
							}
							BarAPI.setMessage(target,"Madman Effect -+ " + madman.get(target) + "% Damage taken. (+3% / seg)");
							VersionManager.sendActionBar(target,"Madman Effect -+ " + madman.get(target) + "% Damage taken. (+3% / seg)");
						}
					}
					/*
					 * if (entity.size() > 1) { for (Player target : entity) {
					 * target.sendMessage("teste"); if
					 * (madman.containsKey(target)) { madman.put(target,
					 * madman.get(target) + 3); } else { madman.put(target, 3);
					 * target.sendMessage("um madman está por perto"); } if
					 * (((CraftPlayer)
					 * p).getHandle().playerConnection.networkManager
					 * .getVersion() < VersionManager.VERSION) {
					 * BarAPI.setMessage(target, "Madman Effect -+ " +
					 * madman.get(target) + "% Damage taken. (+3% / seg)"); }
					 * else { VersionManager.sendActionBar(target,
					 * "Madman Effect -+ " + madman.get(target) +
					 * "% Damage taken. (+3% / seg)"); } } }
					 */
				}
				if (madman.containsKey(p)) {
					boolean hasmadman = false;
					for (Entity ent : p.getNearbyEntities(20, 20, 20)) {
						if ((ent instanceof Player) && (PlayerManager.inGame((Player) ent))) {
							if (KitManager.getKit((Player) ent).equals("Madman")) {
								hasmadman = true;
								break;
							}
						}
					}
					if (!hasmadman) {
						if (madman.get(p) < 1) {
							madman.remove(p);
							p.sendMessage("Madman fora do seu alcance");
							BarAPI.removeBar(p);
							VersionManager.clear(p);
						} else {
							madman.put(p, madman.get(p) - 5);
							BarAPI.setMessage(p, "Madman Effect -+ " + madman.get(p) + "% Damage taken. (-5% / seg)");
							VersionManager.sendActionBar(p, "Madman Effect -+ " + madman.get(p) + "% Damage taken. (-5% / seg)");
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void MadmanKit(EntityDamageEvent e) {
		if (e.isCancelled()) {
			return;
		}
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			if ((madman.containsKey(p)) && (madman.get(p) > 1)) {
				// Madman mt op
				// set e.setDamage(e.getDamage() * madman.get(p) / 30);
				e.setDamage(e.getDamage() * madman.get(p) / 20);
			}
		}
	}
}