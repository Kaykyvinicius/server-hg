package HG.habilidades;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class MinerListener {
	@SuppressWarnings("deprecation")
	public MinerListener() {
		list.add(Integer.valueOf(Material.IRON_ORE.getId()));
		list.add(Integer.valueOf(Material.GOLD_ORE.getId()));
		list.add(Integer.valueOf(Material.COAL_ORE.getId()));
	}

	static HashSet<Integer> list = new HashSet<Integer>();
	static List<Block> unchecked = new LinkedList<Block>();
	static List<Block> checked = new LinkedList<Block>();
	static List<Block> confirmed = new LinkedList<Block>();

	@SuppressWarnings("deprecation")
	public static List<Block> getConnectedBlocks(Block block) {
		BlockFace bf = null;
		unchecked.clear();
		checked.clear();
		confirmed.clear();
		unchecked.add(block);
		while (unchecked.size() > 0) {
			if (!isChecked((Block) unchecked.get(0))) {
				for (int i = 0; i < 6; i++) {
					if (i == 0)
						bf = BlockFace.DOWN;
					else if (i == 1)
						bf = BlockFace.EAST;
					else if (i == 2)
						bf = BlockFace.NORTH;
					else if (i == 3)
						bf = BlockFace.SOUTH;
					else if (i == 4)
						bf = BlockFace.UP;
					else if (i == 5) {
						bf = BlockFace.WEST;
					}
					if ((list.contains(Integer.valueOf(((Block) unchecked.get(0)).getRelative(bf).getTypeId())))
							&& (!isChecked(((Block) unchecked.get(0)).getRelative(bf)))) {
						unchecked.add(((Block) unchecked.get(0)).getRelative(bf));
					}
				}
				checked.add((Block) unchecked.get(0));
			}
			unchecked.remove(0);
		}
		for (int i = 0; i < checked.size(); i++) {
			if (!((Block) checked.get(i)).getType().equals(Material.AIR)) {
				confirmed.add((Block) checked.get(i));
			}
		}
		return confirmed;
	}

	public static boolean isChecked(Block block) {
		for (int i = 0; i < checked.size(); i++) {
			if (((Block) checked.get(i)).equals(block)) {
				return true;
			}
		}
		return false;
	}
}