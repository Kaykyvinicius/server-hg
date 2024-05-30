package HG.habilidades;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import HG.Main;
import HG.manager.KitManager;

public class Kangaroo implements Listener {

	ArrayList<Player> cos = new ArrayList<Player>();

	HashMap<Player, Integer> jumps = new HashMap<Player, Integer>();

	@EventHandler
	public void kang(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (KitManager.getKit(p).equals("Kangaroo")
				&& (p.getItemInHand().getType() == Material.FIREWORK)) {
			e.setCancelled(true);
			if (cos.contains(p)) {
				return;
			}
			if ((this.jumps.containsKey(p))
					&& (((Integer) this.jumps.get(p)).intValue() >= 1)) {
				if (p.isSneaking()) {
					Vector v1 = p.getLocation().getDirection().multiply(1.5D)
							.setY(0.45D);
					p.setVelocity(v1);

					this.jumps.put(p, Integer.valueOf(((Integer) this.jumps
							.get(p)).intValue() - 1));
				} else {
					Vector v2 = p.getLocation().getDirection().multiply(0.35D)
							.setY(0.9D);
					p.setVelocity(v2);

					this.jumps.put(p, Integer.valueOf(((Integer) this.jumps
							.get(p)).intValue() - 1));
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void Move(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		if (KitManager.getKit(p).equals("Kangaroo")) {
			if ((this.jumps.containsKey(p))
					&& (((Integer) this.jumps.get(p)).intValue() > 2)) {
				this.jumps.put(p, Integer.valueOf(2));
			}

			if (!this.jumps.containsKey(p)) {
				this.jumps.put(p, Integer.valueOf(2));
				return;
			}

			if (p.isOnGround()) {
				if (((Integer) this.jumps.get(p)).intValue() != 2) {
					this.jumps.put(p, Integer.valueOf(2));
				}

			} else if (((Integer) this.jumps.get(p)).intValue() == 2) {
				if (!p.isOnGround()) {
					this.jumps.remove(p);
					this.jumps.put(p, Integer.valueOf(1));
				}
			}
		}
	}

	@EventHandler
	public void dmg(EntityDamageEvent e) {
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			if (KitManager.getKit(p).equals("Kangaroo")) {
				if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
					if (e.getDamage() > 7.0D) {
						e.setDamage(7.0D);
					}
				}
			}
		}
	}

	@EventHandler
	public void Cooldown(EntityDamageByEntityEvent e) {
		if (((e.getEntity() instanceof Player))
				&& ((e.getDamager() instanceof Player))) {
			final Player player = (Player) e.getEntity();

			if (KitManager.getKit(player).equals("Kangaroo")) {
				this.cos.add(player);

				Bukkit.getServer().getScheduler()
						.runTaskLater(Main.instance, new Runnable() {
							public void run() {
								cos.remove(player);
							}
						}, 65L);
			}
		}
	}
}
