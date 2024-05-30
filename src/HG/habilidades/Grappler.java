package HG.habilidades;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.v1_7_R4.EntityPlayer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import HG.manager.KitManager;
import HG.utils.EntityGrapplingHook;
import HG.utils.Hooks;

public class Grappler implements Listener {

	Map<Player, Hooks> hooks = new HashMap<Player, Hooks>();

	@EventHandler
	public void onSlot(PlayerItemHeldEvent e) {
		if (this.hooks.containsKey(e.getPlayer())) {
			((Hooks) this.hooks.get(e.getPlayer())).remove();
			this.hooks.remove(e.getPlayer());
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if ((this.hooks.containsKey(e.getPlayer()))
				&& ((!e.getPlayer().getItemInHand().getType()
						.equals(Material.LEASH))
						|| (!e.getPlayer().getItemInHand().hasItemMeta())
						|| (!e.getPlayer().getItemInHand().getItemMeta()
								.hasDisplayName()) || (!e.getPlayer()
						.getItemInHand().getItemMeta().getDisplayName()
						.equals(ChatColor.GREEN + "Grappler")))) {
			((Hooks) this.hooks.get(e.getPlayer())).remove();
			this.hooks.remove(e.getPlayer());
		}
	}

	@EventHandler
	void Interact(PlayerInteractEntityEvent e) {
		if ((e.getPlayer().getItemInHand().getType().equals(Material.LEASH))
				&& (KitManager.getKit(e.getPlayer()).equals("Grappler"))) {
			e.setCancelled(true);
			e.getPlayer().updateInventory();
			return;
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if ((e.getPlayer().getItemInHand().getType().equals(Material.LEASH))) {
			if (KitManager.getKit(p).equals("Grappler")) {
				e.setCancelled(true);
				EntityPlayer nmsPlayer = ((CraftPlayer) p).getHandle();
				if ((e.getAction().equals(Action.LEFT_CLICK_AIR))
						|| (e.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
					e.setCancelled(true);
					if (nmsPlayer.hookedFish != null) {
						nmsPlayer.hookedFish.die();
						nmsPlayer.hookedFish = null;
					}
					EntityGrapplingHook egh = new EntityGrapplingHook(nmsPlayer.world, nmsPlayer);
					nmsPlayer.hookedFish = egh;
					nmsPlayer.world.addEntity(egh);
				} else if ((e.getAction().equals(Action.RIGHT_CLICK_AIR))
						|| (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
					e.setCancelled(true);
					if (nmsPlayer.hookedFish == null) {
					} else if ((!((EntityGrapplingHook) nmsPlayer.hookedFish).inGround)
							&& (nmsPlayer.hookedFish.hooked == null)) {
					} else {
						Location owner = p.getLocation();
						Location hook = nmsPlayer.hookedFish.getBukkitEntity()
								.getLocation();
						double dist = owner.distance(hook);
						double f = dist / 16.0D + 0.5D;
						double x = (hook.getX() - owner.getX()) / dist;
						double y = (hook.getY() - owner.getY()) / dist;
						double z = (hook.getZ() - owner.getZ()) / dist;
						x *= f;
						y *= f;
						z *= f;
						p.setVelocity(new Vector(x, y < 0.1D ? 0.1D : y, z));
						p.setFallDistance(0.0F);
					}
				}
			}
		}
	}

	@EventHandler
	public void entityDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Snowball) {
			if (e.getEntity().hasMetadata("grappler")) {
				e.setCancelled(true);
				e.setDamage(0.0);
			}
		}
	}
}
