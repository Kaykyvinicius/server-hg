package HG.habilidades;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.utils.Status;

public class Thor implements Listener {
	@EventHandler
	public void Machado(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if ((KitManager.getKit(p).equals("Thor"))
				&& (e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& (p.getItemInHand().getType() == Material.WOOD_AXE)) {
			if (GameManager.status != Status.PréJogo) {
				if (KitManager.hasCooldown(p)) {
					KitManager.messageCooldown(p);
					return;
				}
				KitManager.addCooldown(p, 6);
				Location loc = p.getWorld()
						.getHighestBlockAt(e.getClickedBlock().getLocation())
						.getLocation().clone().add(0.5D, 0.5D, 0.5D);
				if (loc.getY() >= 80) {
					if (loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.NETHERRACK) {
						p.getWorld().createExplosion(loc, 2.0F);
					} else {
						loc.getBlock().setType(Material.NETHERRACK);
						loc.getBlock().getRelative(BlockFace.UP).setType(Material.FIRE);
					}
				}
				p.getWorld().strikeLightning(p.getWorld().getHighestBlockAt(e.getClickedBlock().getLocation()).getLocation().clone().add(0.0D, 1.5D, 0.0D));
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getCause() == DamageCause.LIGHTNING) {
				Player p = (Player) e.getEntity();
				if (KitManager.getKit(p).equals("Thor")) {
					e.setCancelled(true);
				} else {
					e.setDamage(4.0);
				}
			}
		}
	}
}
