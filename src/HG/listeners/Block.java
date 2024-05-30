package HG.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import HG.habilidades.Gladiator;
import HG.manager.GameManager;
import HG.manager.PlayerManager;
import HG.utils.Feast;
import HG.utils.Status;

public class Block implements Listener {
	@EventHandler
	void BlockBreakEvent(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if ((GameManager.status == Status.PréJogo) || (PlayerManager.inSpec(p))) {
			e.setCancelled(true);
		}
		if (Feast.fblocks.contains(e.getBlock())) {
			e.setCancelled(true);
		}
		if (Gladiator.b.contains(e.getBlock())) {
			e.setCancelled(true);
		}
		if ((PlayerManager.inAdmin(p)) && (!p.hasPermission("hg.build"))) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	void BlockPlaceEvent(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if ((GameManager.status == Status.PréJogo) || (PlayerManager.inSpec(p))) {
			e.setCancelled(true);
		}
		if (e.getBlock().getY() > 129) {
			e.setCancelled(true);
		}
		if (Feast.fblock.contains(e.getBlock())) {
			e.setCancelled(true);
		}
		if ((PlayerManager.inAdmin(p)) && (!p.hasPermission("hg.build"))) {
			e.setCancelled(true);
		}
		if (Gladiator.b.contains(e.getBlock())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void BlockCanBuildEvent(BlockCanBuildEvent e) {
		e.setBuildable(true);
	}
	/*
	 * @EventHandler void EntityExplodeEvent(EntityExplodeEvent e) { if
	 * (Feast.fblocks.contains(e.blockList())) { for (org.bukkit.block.Block b :
	 * e.blockList()) { b.getLocation().getBlock().setType(b.getType()); } } }
	 */
}