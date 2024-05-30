package HG.utils;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import HG.Main;
import HG.manager.GameManager;

public class Feast {

	public static ArrayList<Block> fblocks = new ArrayList<>();
	public static ArrayList<Block> fblock = new ArrayList<>();
	public static Integer sheld = null;
	public static Block b = Bukkit.getWorld("world").getHighestBlockAt(0, 0);

	public static void create(int X, int Z) {
		int BX = new Random().nextInt(100);
		int BZ = new Random().nextInt(100);
		if (new Random().nextBoolean()) {
			BX = -BX;
		}
		if (new Random().nextBoolean()) {
			BZ = -BZ;
		}
		b = Bukkit.getWorld("world").getHighestBlockAt(BX, BZ);
		Location centro = b.getLocation().clone();
		for (int x = -20; x <= 20; x++) {
			for (int z = -20; z <= 20; z++) {
				Block cilindro = centro.clone().add(x, 0, z).getBlock();
				if (cilindro.getLocation().distance(centro) <= 20.0D) {
					cilindro.setType(Material.GRASS);
					fblocks.add(cilindro);
				}
				for (int y = -5; y <= 5; y++) {
					Block cilindro2 = centro.clone().add(x, y, z).getBlock();
					if (cilindro2.getLocation().distance(centro) <= 22.0D) {
						fblock.add(cilindro2);
					}
				}
				for (int y = 1; y <= 20; y++) {
					Block limpeza = centro.clone().add(x, y, z).getBlock();
					limpeza.setType(Material.AIR);
				}
			}
		}
		sheld = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
			Integer contagem = 301;

			public void run() {
				contagem--;
				if (contagem == 300 || contagem == 240 || contagem == 180 || contagem == 120 || contagem == 60
						|| contagem == 45 || contagem == 30 || contagem == 15 || contagem == 10
						|| contagem < 6 && contagem > 0) {
					Bukkit.broadcastMessage("§cFeast começará em (" + b.getX() + ", " + b.getY() + ", " + b.getZ()
							+ ") em " + GameManager.time(contagem) + ".");
				}
				if (contagem == 0) {
					spawnChests(b.getLocation().clone().add(0, 1, 0));
					fblocks.clear();
					fblock.clear();
					cancel();
				}
			}
		}, 0L, 20L);
	}

	protected static void spawnChests(Location loc) {
		Block b = loc.getBlock();
		b.setType(Material.ENCHANTMENT_TABLE);

		for (int x = -2; x <= 2; x++) {
			if (x != 0) {
				loc.clone().add(x, 0, x).getBlock().setType(Material.CHEST);
				fillChest(loc.clone().add(x, 0, x).getBlock());
			}
		}
		for (int x = -2; x <= 2; x++) {
			if (x != 0 && x != 1 && x != -1) {
				loc.clone().add(0, 0, x).getBlock().setType(Material.CHEST);
				fillChest(loc.clone().add(0, 0, x).getBlock());
				loc.clone().add(x, 0, 0).getBlock().setType(Material.CHEST);
				fillChest(loc.clone().add(x, 0, 0).getBlock());
			}
		}
		loc.clone().add(-1, 0, 1).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(-1, 0, 1).getBlock());

		loc.clone().add(-2, 0, 2).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(-2, 0, 2).getBlock());

		loc.clone().add(1, 0, -1).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(1, 0, -1).getBlock());

		loc.clone().add(2, 0, -2).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(2, 0, -2).getBlock());
	}

	private static void fillChest(Block block) {
		if (!(block.getState() instanceof Chest)) {
			Bukkit.getLogger().info(
					"§cThe block at " + block.getX() + "," + block.getY() + "," + block.getZ() + " dont is chest!");
		} else {
			Inventory inv = ((Chest) block.getState()).getInventory();
			Random r = new Random();
			if (r.nextInt(100) <= 16) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.DIAMOND_SWORD, 1));
			}
			if (r.nextInt(100) <= 16) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.DIAMOND_HELMET, 1));
			}
			if (r.nextInt(100) <= 13) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
			}
			if (r.nextInt(100) <= 14) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.DIAMOND_LEGGINGS, 1));
			}
			if (r.nextInt(100) <= 15) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.DIAMOND_BOOTS, 1));
			}
			if (r.nextInt(100) <= 45) {
				inv.setItem(new Random().nextInt(inv.getSize()),
						new ItemStack(Material.COOKED_BEEF, MiniFeast.Int(32)));
			}
			if (r.nextInt(100) <= 45) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.ENDER_PEARL, MiniFeast.Int(5)));
			}
			if (r.nextInt(100) <= 40) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.POTION, 1, (short) 8201));
			}
			if (r.nextInt(100) <= 40) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.POTION, 1, (short) 16420));
			}
			if (r.nextInt(100) <= 40) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.POTION, 1, (short) 8227));
			}
			if (r.nextInt(100) <= 40) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.POTION, 1, (short) 16424));
			}
			if (r.nextInt(100) <= 40) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.POTION, 1, (short) 16426));
			}
			if (r.nextInt(100) <= 40) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.POTION, 1, (short) 16428));
			}
			if (r.nextInt(100) <= 20) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.POTION, 1, (short) 16418));
			}
			if (r.nextInt(100) <= 33) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.WEB, MiniFeast.Int(15)));
			}
			if (r.nextInt(100) <= 33) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.BOW, 1));
			}
			if (r.nextInt(100) <= 33) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.ARROW, MiniFeast.Int(32)));
			}
			if (r.nextInt(100) <= 50) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.EXP_BOTTLE, MiniFeast.Int(8)));
			}
			if (r.nextInt(100) <= 75) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.FLINT_AND_STEEL, 1));
			}
			if (r.nextInt(100) <= 60) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.WATER_BUCKET, 1));
			}
			if (r.nextInt(100) <= 60) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(Material.LAVA_BUCKET, 1));
			}

		}
	}

	protected static void cancel() {
		if (sheld != null) {
			Bukkit.getServer().getScheduler().cancelTask(sheld);
			sheld = null;
		}
	}
}
