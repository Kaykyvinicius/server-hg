package HG.habilidades;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

import HG.Main;
import HG.manager.KitManager;

public class Demoman implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		if (event.getBlock().getType() == Material.STONE_PLATE && KitManager.getKit(p).equals("Demoman")) {
			event.getBlock().removeMetadata("Demoman", Main.instance);
			event.getBlock().setMetadata("Demoman", new FixedMetadataValue(Main.instance, event.getPlayer().getName()));
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		final Block b = e.getClickedBlock();
		if (e.getAction() == Action.PHYSICAL && b != null && b.hasMetadata("Demoman")
				&& b.getType() == Material.STONE_PLATE && b.getRelative(BlockFace.DOWN).getType() == Material.GRAVEL) {
			b.removeMetadata("Demoman", Main.instance);
			b.setType(Material.AIR);
			b.getWorld().createExplosion(b.getLocation().clone().add(0.5D, 0.5D, 0.5D), 5.0F);
		}
	}
}