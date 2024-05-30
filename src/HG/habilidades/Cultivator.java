package HG.habilidades;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import HG.manager.KitManager;

public class Cultivator implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		if (KitManager.getKit(p).equals("Cultivator")) {
			Block b = event.getBlock();
			if (b.getType() == Material.SAPLING) {
				int data = b.getData();
				b.setType(Material.AIR);
				boolean success;
				if (data == 1) {
					success = b.getWorld().generateTree(b.getLocation(), TreeType.REDWOOD);
				} else {
					if (data == 2) {
						success = b.getWorld().generateTree(b.getLocation(), TreeType.BIRCH);
					} else {
						if (data == 3)
							success = b.getWorld().generateTree(b.getLocation(), TreeType.SMALL_JUNGLE);
						else
							success = b.getWorld().generateTree(b.getLocation(), TreeType.TREE);
					}
				}
				if (!success)
					b.setTypeIdAndData(Material.SAPLING.getId(), (byte) data, false);
			} else if (b.getType() == Material.CROPS) {
				b.setData((byte) 7);
			} else if (b.getType() == Material.CARROT) {
				b.setData((byte) 7);
			} else if (b.getType() == Material.POTATO) {
				b.setData((byte) 7);
			}
		}
	}
}