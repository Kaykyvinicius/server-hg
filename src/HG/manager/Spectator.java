package HG.manager;

import java.util.HashMap;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Spectator implements Listener {
	public static HashMap<Player, Player> spec = new HashMap<>();

	public static void spectateOn(Player p, Player target) {
		spec.put(p, target);
		p.showPlayer(target);
		p.hidePlayer(p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 99999));
		updatePlayer(target);
		p.sendMessage("§aVocê está espectando: " + target.getDisplayName()
				+ "\n§cAperte a tecla Shift para sair do modo espectador");
	}

	public static void specateOff(Player p) {
		p.teleport(spec.get(p));
		spec.remove(p);
		p.setHealth(20.0D);
		p.setFoodLevel(20);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.showPlayer(p);
		for (PotionEffect potion : p.getActivePotionEffects()) {
			p.removePotionEffect(potion.getType());
		}
	}

	public static Player getSpectatorFrom(Player t) {
		Player p = null;
		for (Player s : spec.keySet()) {
			if (!spec.containsKey(s)) {
				break;
			}
			if (!((Player) spec.get(s)).getName().equals(t.getName())) {
				break;
			}
			p = s;
		}
		return p;
	}

	public static void updatePlayer(Player target) {
		if (!spec.containsValue(target)) {
			return;
		}
		Player p = null;
		for (Player s : spec.keySet()) {
			if (!spec.containsKey(s)) {
				break;
			}
			if (!((Player) spec.get(s)).getName().equals(target.getName())) {
				break;
			}
			p = s;
		}
		Damageable heal = target;
		p.getInventory().setArmorContents(target.getInventory().getArmorContents());
		p.getInventory().setContents(target.getInventory().getContents());
		p.getInventory().setHeldItemSlot(target.getInventory().getHeldItemSlot());
		p.setFoodLevel(target.getFoodLevel());
		p.setHealth(heal.getHealth());
		p.teleport(target.getLocation());
	}

	@EventHandler
	public void PlayerMoveEvent(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (spec.containsValue(p)) {
			updatePlayer(p);
		}
		if (spec.get(p) != null) {
			updatePlayer(spec.get(p));
		}
	}

	@EventHandler
	public void InventoryClickEvent(InventoryClickEvent e) {
		try {
			Player p = (Player) e.getWhoClicked();
			if (spec.containsValue(p)) {
				updatePlayer(p);
			}
			if (spec.get(p) != null) {
				updatePlayer(spec.get(p));
			}
		} catch (NullPointerException n) {
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void PlayerQuitEvent(PlayerQuitEvent e) {
		Player t = e.getPlayer();
		if (!spec.containsValue(t)) {
			return;
		}
		Player p = null;
		for (Player s : spec.keySet()) {
			if (!spec.containsKey(s)) {
				break;
			}
			if (!((Player) spec.get(s)).getName().equals(t.getName())) {
				break;
			}
			p = s;
		}
		specateOff(p);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void PlayerDeathEvent(PlayerDeathEvent e) {
		if (e.getEntity() instanceof Player) {
			Player t = (Player) e.getEntity();
			if (!spec.containsValue(t)) {
				return;
			}
			Player p = null;
			for (Player s : spec.keySet()) {
				if (!spec.containsKey(s)) {
					break;
				}
				if (!((Player) spec.get(s)).getName().equals(t.getName())) {
					break;
				}
				p = s;
			}
			specateOff(p);
			p.sendMessage("Player death...");
		}
	}

	@EventHandler
	void PlayerToggleSneakEvent(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if ((PlayerManager.inSpec(p)) && (p.isSneaking()) && (spec.get(p) != null)) {
			specateOff(p);
		}
	}
}