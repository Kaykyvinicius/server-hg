package HG.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import HG.kitselector.KitSelector;
import HG.manager.Kits;

public class MiniFeast {

	public static void create() {
		int BX = new Random().nextInt(400);
		int BZ = new Random().nextInt(400);
		if (BX < 150) {
			BX = BX + 100;
		}
		if (BZ < 150) {
			BZ = BZ + 100;
		}
		if (new Random().nextBoolean()) {
			BX = -BX;
		}
		if (new Random().nextBoolean()) {
			BZ = -BZ;
		}
		final Block b = Bukkit.getWorld("world").getHighestBlockAt(BX, BZ);
		Location centro = b.getLocation().clone();
		for (int x = -2; x <= 2; x++) {
			for (int z = -2; z <= 2; z++) {
				centro.clone().add(x, 0, z).getBlock().setType(Material.GLASS);
				centro.clone().add(x, 1, z).getBlock().setType(Material.AIR);
				centro.clone().add(x, 2, z).getBlock().setType(Material.AIR);
				centro.clone().add(x, 3, z).getBlock().setType(Material.AIR);
			}
		}
		centro.clone().add(-2, 0, 2).getBlock().setType(Material.AIR);
		centro.clone().add(-2, 0, -2).getBlock().setType(Material.AIR);
		centro.clone().add(2, 0, 2).getBlock().setType(Material.AIR);
		centro.clone().add(2, 0, -2).getBlock().setType(Material.AIR);
		spawnChests(centro.add(0, 1, 0));
		DecimalFormat df = new DecimalFormat("##");
		Bukkit.broadcastMessage("§cUm minifeast apareceu em (X: " + df.format(centro.getX() - 50.0D) + " e "
				+ df.format(centro.getX() + 50.0D) + ") e (Z: " + df.format(centro.getZ() - 50.0D) + " e "
				+ df.format(centro.getZ() + 50.0D) + ")");
	}

	protected static void spawnChests(Location loc) {
		Block b = loc.getBlock();
		b.setType(Material.ENCHANTMENT_TABLE);
		loc.clone().add(-1, 0, -1).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(-1, 0, -1).getBlock());
		loc.clone().add(-1, 0, 1).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(-1, 0, 1).getBlock());
		loc.clone().add(1, 0, 1).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(1, 0, 1).getBlock());
		loc.clone().add(1, 0, -1).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(1, 0, -1).getBlock());
	}

	private static void fillChest(Block block) {
		if (!(block.getState() instanceof Chest)) {
			Bukkit.getLogger().info(
					"§cThe block at " + block.getX() + "," + block.getY() + "," + block.getZ() + " dont is chest!");
		} else {
			Inventory inv = ((Chest) block.getState()).getInventory();
			Random r = new Random();
			if (r.nextInt(100) <= 15) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.DIAMOND, Int(4)));
			}
			if (r.nextInt(100) <= 20) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.POTION, 1, (short) 16388));
			}
			if (r.nextInt(100) <= 20) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.POTION, 1, (short) 16426));
			}
			if (r.nextInt(100) <= 70) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.EXP_BOTTLE, Int(8)));
			}
			if (r.nextInt(100) <= 60) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.IRON_INGOT, Int(4)));
			}
			if (r.nextInt(100) <= 70) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.GOLD_INGOT, Int(6)));
			}
			if (r.nextInt(100) <= 80) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.MUSHROOM_SOUP, Int(5)));
			}
			if (r.nextInt(100) <= 80) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.INK_SACK, Int(10), (short) 3));
			}
			if (r.nextInt(100) <= 90) {
				ArrayList<Kits> kitrandom = new ArrayList<>();
				kitrandom.add(Kits.Blink);
				kitrandom.add(Kits.Demoman);
				kitrandom.add(Kits.Endermage);
				kitrandom.add(Kits.Fisherman);
				kitrandom.add(Kits.Gambler);
				//kitrandom.add(Kits.Gladiator);
				kitrandom.add(Kits.Grandpa);
				kitrandom.add(Kits.Grappler);
				kitrandom.add(Kits.Jackhammer);
				kitrandom.add(Kits.Kangaroo);
				kitrandom.add(Kits.Kaya);
				kitrandom.add(Kits.Launcher);
				kitrandom.add(Kits.Lumberjack);
				kitrandom.add(Kits.Miner);
				kitrandom.add(Kits.Reaper);
				kitrandom.add(Kits.Redstoner);
				kitrandom.add(Kits.Specialist);
				kitrandom.add(Kits.Switcher);
				kitrandom.add(Kits.Thor);
				kitrandom.add(Kits.Titan);
				kitrandom.add(Kits.Urgal);
				inv.setItem(new Random().nextInt(inv.getSize()),
						KitSelector.item(Material.WOOL,
								"§aPresente do kit " + kitrandom.get(new Random().nextInt(kitrandom.size())), Int(2),
								(byte) Int(15), null));
			}
		}
	}

	static int Int(int i) {
		int returnI = new Random().nextInt(i);
		if (returnI == 0) {
			returnI = 1;
		}
		return returnI;
	}
}