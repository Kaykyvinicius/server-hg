package HG.habilidades;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import HG.manager.KitManager;

public class Miner implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void MinerKit(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if ((KitManager.getKit(p).equals("Miner")) && (p.getItemInHand().getType() == Material.STONE_PICKAXE)
				&& (p.getItemInHand().containsEnchantment(Enchantment.DURABILITY))
				&& (p.getItemInHand().containsEnchantment(Enchantment.DIG_SPEED))) {
			Block b = e.getBlock();
			if (MinerListener.list.contains(Integer.valueOf(b.getTypeId()))) {
				List<Block> blocks = MinerListener.getConnectedBlocks(b);
				for (int i = 0; i < blocks.size(); i++) {
					blocks.get(i).breakNaturally();
				}
			}
		}
	}
}