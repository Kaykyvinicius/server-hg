package HG.habilidades;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import HG.Main;
import HG.manager.KitManager;
import HG.manager.PlayerManager;

public class Endermage implements Listener {

	public static ArrayList<Player> invenc = new ArrayList<Player>();

	public void TeleportP(Location portal, final Player p1, final Player p2) {
		p1.teleport(portal.clone().add(0.0D, 1.0D, 0.0D));
		p2.teleport(portal.clone().add(0.0D, 1.0D, 0.0D));
		invenc.add(p1);
		invenc.add(p2);
		p1.sendMessage("§cTeleport successful. You are invincible for  5 seconds. Prepare to fight!!!");
		p2.sendMessage("§c§lAn Endermage has teleported you!!! You are invincible for 5 seconds. Prepare to fight!!!");
		Bukkit.getServer().getScheduler()
				.scheduleSyncDelayedTask(Main.instance, new Runnable() {
					public void run() {
						invenc.remove(p1);
						p1.sendMessage("§cYou are no longer invincible.");
						invenc.remove(p2);
						p2.sendMessage("§cYou are no longer invincible.");
					}
				}, 5 * 20);
		p2.getWorld().playEffect(p2.getLocation(), Effect.ENDER_SIGNAL, 9);
		p1.getWorld().playEffect(portal, Effect.ENDER_SIGNAL, 9);
		p2.playSound(p2.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.2F);
		p1.playSound(portal, Sound.ENDERMAN_TELEPORT, 1.0F, 1.2F);
	}

	private boolean isEnderable(Location portal, Location player) {
		return (Math.abs(portal.getX() - player.getX()) < 3.0D)
				&& (Math.abs(portal.getZ() - player.getZ()) < 3.0D)
				&& (Math.abs(portal.getY() - player.getY()) >= 3.5D);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void EndermageInteract(PlayerInteractEvent e) {
		final Player mage = e.getPlayer();
		if (KitManager.getKit(mage).equals("Endermage")
				&& e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& mage.getItemInHand().getType() == Material.PORTAL) {
			e.setCancelled(true);
			mage.updateInventory();
			final Block b = e.getClickedBlock();

			final Location bLoc = b.getLocation();
			if (bLoc.add(0, 1, 0).getBlock().getType() != Material.AIR) {
				mage.sendMessage("§cYou can't place here!");
				return;
			}
			mage.setItemInHand(new ItemStack(Material.AIR));
			mage.updateInventory();
			final BlockState bs = b.getState();

			b.setType(Material.ENDER_PORTAL_FRAME);
			for (Player nearby : Bukkit.getOnlinePlayers()) {
				final Player target = nearby.getPlayer();
				new BukkitRunnable() {
					int time = 5;

					public void run() {
						this.time -= 1;
						if (!KitManager.getKit(target).equals("Endermage")
								&& isEnderable(bLoc, target.getLocation())
								&& target != mage && !target.isDead()
								&& PlayerManager.inGame(target)) {
							b.setType(bs.getType());
							b.setData(bs.getBlock().getData());
							cancel();
							TeleportP(bLoc, mage, target);
							if (!mage.getInventory().contains(
									new ItemStack(Material.PORTAL))) {
								mage.getInventory().addItem(
										new ItemStack[] { new ItemStack(
												Material.PORTAL) });
								mage.updateInventory();
							}
						} else if (this.time == 0) {
							cancel();
							b.setType(bs.getType());
							b.setData(bs.getBlock().getData());
							if (!mage.getInventory().contains(
									new ItemStack(Material.PORTAL))) {
								mage.getInventory().addItem(
										new ItemStack[] { new ItemStack(
												Material.PORTAL) });
								mage.updateInventory();
							}
						}
					}
				}.runTaskTimer(Main.instance, 0L, 20L);
			}
		}
	}

	@EventHandler
	public void onDmg(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (invenc.contains(p)) {
				e.setCancelled(true);
			}
		}
	}
}
