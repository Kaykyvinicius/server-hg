package HG.listeners;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;

public class Knockback implements Listener {
	HashMap<Player, Player> bt = new HashMap<>();
	HashMap<Player, Boolean> asd = new HashMap<>();
	HashMap<Player, Enchantment> asd2 = new HashMap<>();

	@EventHandler
	public void dmg(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player)) {
			if ((e.getDamager() instanceof Player)) {
				Player p = (Player) e.getEntity();
				Player b = (Player) e.getDamager();
				bt.remove(p);
				asd.remove(b);
				asd2.remove(b);
				bt.put(p, b);
				if (bt.get(p).isSprinting()) {
					asd.put(bt.get(p), true);
				}
				if (b.getItemInHand().getEnchantments().containsKey(Enchantment.KNOCKBACK)) {
					asd2.put(b, Enchantment.KNOCKBACK);
				}
			}
		}
	}

	@EventHandler
	public void asdasd(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			asd.remove(p);
			asd2.remove(p);
			asd.remove(bt.get(p));
			asd2.remove(bt.get(p));
			bt.remove(p);
		}
	}

	@EventHandler
	public void kb(PlayerVelocityEvent e) {
		if (bt.get(e.getPlayer()) != null) {
			Location loc = bt.get(e.getPlayer()).getLocation();
			Vector vector = loc.getDirection().multiply(0.44);
			vector.setY(0.3);
			if (asd.get(bt.get(e.getPlayer())) != null) {
				if (asd.get(bt.get(e.getPlayer()))) {
					if (asd2.containsKey(bt.get(e.getPlayer()))) {
						Vector vector1 = loc.getDirection().multiply(1.03);
						vector1.setY(0.4);
						e.getPlayer().setVelocity(vector1);
						return;
					}
					Vector vector1 = loc.getDirection().multiply(0.83);
					vector1.setY(0.4);
					e.getPlayer().setVelocity(vector1);
					return;
				}
			}
			if (asd2.containsKey(bt.get(e.getPlayer()))) {
				Vector vector1 = loc.getDirection().multiply(0.73);
				vector1.setY(0.4);
				e.getPlayer().setVelocity(vector1);
				return;
			}
			e.getPlayer().setVelocity(vector);
		}
		// Bukkit.broadcastMessage("kb: "
		// + String.valueOf(e.getVelocity().toString()).substring(0, 6));
	}
}