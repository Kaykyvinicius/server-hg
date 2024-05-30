package HG.habilidades;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import HG.manager.KitManager;
import HG.manager.PlayerManager;

public class Ninja implements Listener {
	static HashMap<Player, Player> tp = new HashMap<>();

	@EventHandler
	void NinjaKit(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player)) {
			Player p = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			if (KitManager.getKit(damager).equals("Ninja")) {
				tp.put(damager, p);
			}
		}
	}

	@EventHandler
	void NinjaKit(PlayerToggleSneakEvent e) {
		final Player p = e.getPlayer();
		if ((KitManager.getKit(p).equals("Ninja")) && (p.isSneaking())) {
			if (KitManager.hasCooldown(p)) {
				KitManager.messageCooldown(p);
				return;
			}
			Player target = (Player) tp.get(p);
			if ((target == null) || (target.isDead()) || (!PlayerManager.inGame(target)) || (p.getLocation().distance(target.getLocation()) > 100)) {
				return;
			}
			tp.remove(p);
			p.teleport(target);
			p.sendMessage("§aTeleportado");
			KitManager.addCooldown(p, 7);
		}
	}
}