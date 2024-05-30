package HG.habilidades;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import HG.manager.KitManager;
import HG.manager.PlayerManager;

public class Kaya implements Listener {
	ArrayList<Block> block = new ArrayList<>();

	@EventHandler
	public void asdas(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Block bloco = p.getLocation().add(0, -0.5, 0).getBlock();
		if ((PlayerManager.inGame(p)) && (!KitManager.getKit(p).equals("Kaya"))) {
			if (bloco.getType() == Material.GRASS) {
				if (block.contains(bloco)) {
					p.getLocation().add(0, -1, 0).getBlock().setType(Material.AIR);
					block.remove(bloco);
				}
			}
		}
	}

	@EventHandler
	public void gramadoKaya(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (KitManager.getKit(p).equals("Kaya")) {
			if (e.getBlock().getType() == Material.GRASS) {
				block.add(e.getBlock());
			}
		}
	}
}