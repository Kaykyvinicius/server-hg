package HG.habilidades;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.utils.Status;

public class Launcher implements Listener {

	@SuppressWarnings("static-access")
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if ((event.getEntity() instanceof Player)) {
			Player player = (Player) event.getEntity();
			if ((this.nofalldamage.contains(player.getName()))
					&& (event.getCause()
							.equals(EntityDamageEvent.DamageCause.FALL))) {
				event.setCancelled(true);
				if ((KitManager.getKit(player).equals("Stomper"))) {
					return;
				}
				this.nofalldamage.remove(player.getName());

			}
		}
	}

	public static ArrayList<String> nofalldamage = new ArrayList<String>();
	ArrayList<String> nofalldamagewait = new ArrayList<String>();

	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (!(GameManager.status == Status.Vitória)) {
			Location standBlock = player.getWorld()
					.getBlockAt(player.getLocation().add(0.0D, -0.01D, 0.0D))
					.getLocation();
			if (standBlock.getBlock().getType() == Material.SPONGE) {
				int xblock = 0;
				double xvel = 0.0D;
				int yblock = -1;
				double yvel = 0.0D;
				int zblock = 0;
				double zvel = 0.0D;
				while (standBlock.getBlock().getLocation()
						.add(xblock - 2, -1.0D, 0.0D).getBlock().getType() == Material.SPONGE) {
					xblock--;
					xvel += 1.0D;
				}
				while (standBlock.getBlock().getLocation()
						.add(0.0D, yblock, 0.0D).getBlock().getType() == Material.SPONGE) {
					yblock--;
					yvel += 1.2D;
				}
				while (standBlock.getBlock().getLocation()
						.add(0.0D, -1.0D, zblock - 1).getBlock().getType() == Material.SPONGE) {
					zblock--;
					zvel += 1.0D;
				}
				xblock = 0;
				zblock = 0;
				while (standBlock.getBlock().getLocation()
						.add(xblock + 2, -1.0D, 0.0D).getBlock().getType() == Material.SPONGE) {
					xblock++;
					xvel -= 1.0D;
				}
				while (standBlock.getBlock().getLocation()
						.add(0.0D, -1.0D, zblock + 2).getBlock().getType() == Material.SPONGE) {
					zblock++;
					zvel -= 1.0D;
				}
				if ((xvel != 0.0D) || (yvel != 0.0D) || (zvel != 0.0D)) {
					player.setVelocity(new Vector(xvel, yvel, zvel));

					if (!this.nofalldamage.contains(player.getName())) {
						this.nofalldamage.add(player.getName());
					}

					if (standBlock.getBlock().getLocation().add(0, 1, 0)
							.getBlock().getType() == Material.WOOD_PLATE) {
						nofalldamage.remove(player.getName());
						return;
					}
				}
			}
		}
	}
}
