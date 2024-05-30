package HG.habilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import HG.Main;
import HG.manager.KitManager;

public class Jellyfish implements Listener {
	static HashMap<Player, Block> water = new HashMap<>();



	@EventHandler
	void JellyfishKit(final PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (((e.getAction() == Action.RIGHT_CLICK_BLOCK)) && (KitManager.getKit(p).equals("Jellyfish"))
				&& (p.getItemInHand().getType() == Material.AIR)) {
			final Block b = e.getClickedBlock();
			if (b.getType() != Material.AIR) {
				List<Block> blocks = new ArrayList<>();
				for (int i = 0; i < blocks.size(); i++) {
					blocks.get(i).setType(Material.STATIONARY_WATER);
					//water.put(p, blocks);
				}
			}
			new BukkitRunnable() {
				public void run() {
					water.get(p).setType(Material.AIR);
				}
			}.runTaskLater(Main.instance, 60);
		}
	}

	@EventHandler
	void JellyfishKit(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if ((!KitManager.getKit(p).equals("Jellyfish")) && (water.containsValue(p.getLocation().getBlock()))
				|| (water.containsValue(p.getLocation().getBlock().getRelative(BlockFace.UP)))) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
		}
	}

	@EventHandler
	void JellyfishKit(BlockFormEvent e) {
		if (water.containsValue(e.getBlock())) {
			e.getBlock().setType(Material.AIR);
			e.setCancelled(true);
		}
	}
}