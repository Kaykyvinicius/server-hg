package HG.utils;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import HG.Main;
import HG.kitselector.KitSelector;
import HG.manager.KitManager;
import HG.manager.Kits;

public class KitItens {

	public static void giveKitsItens(final Player p) {
		if (KitManager.getKit(p).equals("Urgal")) {
			p.getInventory().addItem(new ItemStack(Material.POTION, 3, (short) 8201));
		}
		if (KitManager.getKit(p).equals("Fisherman")) {
			p.getInventory().addItem(new ItemStack(Material.FISHING_ROD, 1, (short) 0));
		}
		if (KitManager.getKit(p).equals("Blink")) {
			ItemStack i = KitSelector.item(Material.NETHER_STAR, "§fBlink", 1, (short) 0,
					new String[] { "§fUsed to blink forward" });
			p.getInventory().addItem(i);
		}
		if (KitManager.getKit(p).equals("Kaya")) {
			ItemStack kaya = new ItemStack(Material.GRASS, 16);
			p.getInventory().addItem(kaya);
		}
		if (KitManager.getKit(p).equals("Cannibal")) {
			ItemStack i1 = new ItemStack(Material.MONSTER_EGG, 1, (short) 98);
			ItemStack i2 = new ItemStack(Material.RAW_FISH, 1);
			p.getInventory().addItem(i1);
			p.getInventory().addItem(i2);
		}
		if (KitManager.getKit(p).equals("Titan")) {
			p.getInventory().addItem(KitSelector.item(Material.BEDROCK, "§aTitan mode", 1, (short) 0, null));
		}
		if (KitManager.getKit(p).equals("Lumberjack")) {
			p.getInventory().addItem(new ItemStack(Material.WOOD_AXE, 1));
		}
		if (KitManager.getKit(p).equals("Thor")) {
			p.getInventory().addItem(new ItemStack(Material.WOOD_AXE, 1));
		}
		if (KitManager.getKit(p).equals("Gambler")) {
			p.getInventory().addItem(new ItemStack(Material.STONE_BUTTON, 1));
		}
		if (KitManager.getKit(p).equals("Reaper")) {
			p.getInventory().addItem(new ItemStack(Material.WOOD_HOE, 1));
		}
		if (KitManager.getKit(p).equals("Demoman")) {
			p.getInventory().addItem(new ItemStack(Material.GRAVEL, 6));
			p.getInventory().addItem(new ItemStack(Material.STONE_PLATE, 6));
		}
		if (KitManager.getKit(p).equals("Fireman")) {
			p.getInventory().addItem(new ItemStack(Material.WATER_BUCKET, 1));
		}
		if (KitManager.getKit(p).equals("Endermage")) {
			p.getInventory().addItem(new ItemStack(Material.PORTAL, 1));
		}
		if (KitManager.getKit(p).equals("Grappler")) {
			p.getInventory().addItem(new ItemStack(Material.LEASH, 1));
		}
		if (KitManager.getKit(p).equals("Switcher")) {
			p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 10));
		}
		if (KitManager.getKit(p).equals("Backup")) {
			p.getInventory().addItem(new ItemStack(Material.BOOK));
		}
		if (KitManager.getKit(p).equals("Castaway")) {
			Random r = new Random();
			final int x = r.nextInt(200 - r.nextInt(100));
			final int z = r.nextInt(200 - r.nextInt(100));
			final int y = p.getWorld().getHighestBlockYAt(x, z) + 15;
			new BukkitRunnable() {
				public void run() {
					p.teleport(new Location(p.getWorld(), x, y, z));
				}
			}.runTaskLater(Main.instance, 5L);

		}
		if (KitManager.getKit(p).equals("Forger")) {
			p.getInventory().addItem(KitSelector.item(Material.COAL, "§fCoal", 3, (byte) 0,
					new String[] { "Click with this coal on an ore", "And the ore will be instantly smelted." }));
		}
		if (KitManager.getKit(p).equals("Launcher")) {
			p.getInventory().addItem(KitSelector.item(Material.SPONGE, "", 20, (byte) 0, null));
		}
		if (KitManager.getKit(p).equals("Jackhammer")) {
			p.getInventory().addItem(KitSelector.item(Material.STONE_AXE, "", 1, (byte) 0, null));
		}
		if (KitManager.getKit(p).equals("Grandpa")) {
			ItemStack stick = KitSelector.item(Material.STICK, "Grandpa's Stick", 1, (byte) 0, null);
			stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
			p.getInventory().addItem(stick);
		}
		if (KitManager.getKit(p).equals("Redstoner")) {
			ItemStack slime = new ItemStack(Material.SLIME_BALL, 16);
			ItemStack slime2 = new ItemStack(Material.DISPENSER, 4);
			ItemStack slime3 = new ItemStack(Material.DIODE, 16);
			ItemStack slime4 = new ItemStack(Material.REDSTONE, 64);
			ItemStack slime5 = new ItemStack(Material.REDSTONE, 64);
			ItemStack slime6 = new ItemStack(Material.PISTON_BASE, 32);
			ItemStack[] as = { slime, slime2, slime3, slime4, slime5, slime6 };
			p.getInventory().addItem(as);
		}
		if (KitManager.getKit(p).equals("Hermit")) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
				public void run() {
					Location loc = p.getWorld().getBlockAt(0, 128, 0).getLocation().add(0, 10, 0);
					for (int random = 0; random <= 50; random++) {
						Location cu = p.getWorld()
								.getHighestBlockAt(new Random().nextInt(500), new Random().nextInt(500)).getLocation();
						if (cu.getBlock().getBiome() == Biome.SWAMPLAND) {
							loc = cu.add(0, 10, 0);
						} else {
							if (cu.getBlock().getBiome() == Biome.JUNGLE) {
								loc = cu.add(0, 10, 0);
							}
						}
						p.teleport(loc);
					}
				}
			}, 20L);
		}
		if (KitManager.getKit(p).equals("Surprise")) {
			ArrayList<Kits> kitrandom = new ArrayList<>();
			for (Kits kit : Kits.values()) {
				kitrandom.add(kit);
			}
			kitrandom.remove(Kits.Surprise);
			final Kits kitsurprise = kitrandom.get(new Random().nextInt(kitrandom.size()));
			p.sendMessage("§aYour surprise kit gave you the " + kitsurprise.toString() + " kit!");
			KitManager.surprise.put(p.getUniqueId(), "Surprise " + kitsurprise);
			KitManager.kits.put(p.getUniqueId(), kitsurprise);
			giveKitsItens(p);
		}
		if (KitManager.getKit(p).equals("Kangaroo")) {
			p.getInventory().addItem(KitSelector.item(Material.FIREWORK, "§6Double-Jump Rocket", 1, (byte) 0, null));
		}
		if (KitManager.getKit(p).equals("Specialist")) {
			p.getInventory().addItem(new ItemStack(Material.BOOK));
		}
		if (KitManager.getKit(p).equals("Magma")) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 99999));
		}
		if (KitManager.getKit(p).equals("Miner")) {
			ItemStack i = KitSelector.item(Material.STONE_PICKAXE, "§f§lMiner pickaxe!", 1, (byte) 0, null);
			i.addEnchantment(Enchantment.DIG_SPEED, 2);
			i.addEnchantment(Enchantment.DURABILITY, 1);
			p.getInventory().addItem(i);
			p.getInventory().addItem(new ItemStack(Material.APPLE, 5));
		}
		if (KitManager.getKit(p).equals("Gladiator")) {
			ItemStack i = KitSelector
					.item(Material.IRON_FENCE, "§cStart Shadow Game", 1, (byte) 0,
							new String[] { "§fRight click the fence to", "§ftrap an opponent in a deathmatch",
									"§farena, where you have 1 minute to fight",
									"§fOr the game will eliminate one of you." });
			p.getInventory().addItem(i);
		}
	}
	public static void giveItens(Player p, Kits kit) {
		if ((KitManager.getKit(p).equals("Urgal")) && (kit.equals("Urgal"))) {
			p.getInventory().addItem(new ItemStack(Material.POTION, 3, (short) 8201));
		}
		if ((KitManager.getKit(p).equals("Fisherman")) && (kit.equals("Fisherman"))) {
			p.getInventory().addItem(new ItemStack(Material.FISHING_ROD, 1, (short) 0));
		}
		if ((KitManager.getKit(p).equals("Blink")) && (kit.equals("Blink"))) {
			ItemStack i = KitSelector.item(Material.NETHER_STAR, "§fBlink", 1, (short) 0,
					new String[] { "§fUsed to blink forward" });
			p.getInventory().addItem(i);
		}
		if ((KitManager.getKit(p).equals("Kaya")) && (kit.equals("Kaya"))) {
			ItemStack kaya = new ItemStack(Material.GRASS, 16);
			p.getInventory().addItem(kaya);
		}
		if ((KitManager.getKit(p).equals("Titan")) && (kit.equals("Titan"))) {
			p.getInventory().addItem(KitSelector.item(Material.BEDROCK, "§aTitan mode", 1, (short) 0, null));
		}
		if ((KitManager.getKit(p).equals("Demoman")) && (kit.equals("Demoman"))) {
			p.getInventory().addItem(new ItemStack(Material.GRAVEL, 6));
			p.getInventory().addItem(new ItemStack(Material.STONE_PLATE, 6));
		}
		if ((KitManager.getKit(p).equals("Endermage")) && (kit.equals("Endermage"))) {
			p.getInventory().addItem(new ItemStack(Material.PORTAL, 1));
		}
		if ((KitManager.getKit(p).equals("Grappler")) && (kit.equals("Grappler"))) {
			p.getInventory().addItem(new ItemStack(Material.LEASH, 1));
		}
		if ((KitManager.getKit(p).equals("Launcher")) && (kit.equals("Launcher"))) {
			p.getInventory().addItem(KitSelector.item(Material.SPONGE, "", 20, (byte) 0, null));
		}
		if ((KitManager.getKit(p).equals("Grandpa")) && (kit.equals("Grandpa"))) {
			ItemStack stick = KitSelector.item(Material.STICK, "Grandpa's Stick", 1, (byte) 0, null);
			stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
			p.getInventory().addItem(stick);
		}
		if ((KitManager.getKit(p).equals("Kangaroo")) && (kit.equals("Kangaroo"))) {
			p.getInventory().addItem(KitSelector.item(Material.FIREWORK, "§6Double-Jump Rocket", 1, (byte) 0, null));
		}
		if ((KitManager.getKit(p).equals("Specialist")) && (kit.equals("Specialist"))) {
			p.getInventory().addItem(new ItemStack(Material.BOOK));
		}
		if ((KitManager.getKit(p).equals("Miner")) && (kit.equals("Miner"))) {
			ItemStack i = KitSelector.item(Material.STONE_PICKAXE, "§f§lMiner pickaxe!", 1, (byte) 0, null);
			i.addEnchantment(Enchantment.DIG_SPEED, 2);
			i.addEnchantment(Enchantment.DURABILITY, 1);
			p.getInventory().addItem(i);
			p.getInventory().addItem(new ItemStack(Material.APPLE, 5));
		}
		if ((KitManager.getKit(p).equals("Gladiator")) && (kit.equals("Gladiator"))) {
			ItemStack i = KitSelector
					.item(Material.IRON_FENCE, "§cStart Shadow Game", 1, (byte) 0,
							new String[] { "§fRight click the fence to", "§ftrap an opponent in a deathmatch",
									"§farena, where you have 1 minute to fight",
									"§fOr the game will eliminate one of you." });
			p.getInventory().addItem(i);
		}
	}
}
