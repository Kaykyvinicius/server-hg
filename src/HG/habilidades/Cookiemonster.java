package HG.habilidades;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import HG.manager.KitManager;

public class Cookiemonster implements Listener {
	ItemStack item = new ItemStack(Material.LONG_GRASS, 0, (byte) 1);

	@EventHandler
	public void quebrarCookie(BlockBreakEvent e) {
		Block b = e.getBlock();
		if (KitManager.getKit(e.getPlayer()).equals("Cookiemonster")) {
			if (e.getBlock().getType() == item.getType()) {
				if (new Random().nextInt(3) == 0) {
					b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.COOKIE));
				}
			}
		}
	}

	@EventHandler
	public void comerCookie(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		if (e.getPlayer().getItemInHand().getType() == Material.COOKIE) {
			if (KitManager.getKit(p).equals("Cookiemonster")) {
				if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
					e.setCancelled(true);
					if (p.getItemInHand().getAmount() == 1) {
						p.setItemInHand(new ItemStack(Material.AIR));
						p.updateInventory();
					} else {
						p.getInventory().removeItem(new ItemStack(Material.COOKIE, 1));
					}
					int fome = p.getFoodLevel();
					fome += 2;
					if (fome > 20) {
						fome = 20;
					}
					if (!(((Damageable) p).getHealth() > 18)) {
						((Damageable) p).setHealth(((Damageable) p).getHealth() + 2);
					} else {
						p.setHealth(20.0D);
					}
					p.setFoodLevel(fome);
					if (((Damageable) p).getHealth() == 20 && p.getFoodLevel() == 20) {
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 4 * 20, 1));
					}
				}
			}
		}
	}
}