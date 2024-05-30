package HG.habilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import HG.Main;
import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.manager.PlayerManager;
import HG.utils.Status;

public class Gladiator implements Listener {
	public static HashMap<Player, Location> loc = new HashMap<>();
	public static HashMap<Player, Player> batalha = new HashMap<>();
	public static ArrayList<Player> gladiator = new ArrayList<>();
	public static ArrayList<Block> b = new ArrayList<>();
	static HashMap<Player, List<Block>> remove = new HashMap<>();

	@EventHandler
	void GladiatorKit(PlayerInteractEntityEvent e) {
		final Player p = e.getPlayer();
		if (e.getRightClicked() instanceof Player) {
			final Player target = (Player) e.getRightClicked();
			if ((KitManager.getKit(p).equals("Gladiator")) && (p.getItemInHand().getType() == Material.IRON_FENCE)
					&& (!gladiator.contains(p)) && (!gladiator.contains(target)) && (PlayerManager.inGame(target))) {
				if (GameManager.status == Status.Invencibilidade) {
					p.sendMessage("§cVocê não pode usar a habilidade do seu kit na invencibilidade!");
					return;
				}
				Block playerloc = p.getLocation().add(0, 70, 0).getBlock();
				List<Block> blocks = new ArrayList<>();
				int Y = 0;
				for (int x = -9; x <= 10; x++) {
					for (int y = 0; y <= 10; y++) {
						for (int z = -10; z <= 9; z++) {
							Location location = new Location(p.getWorld(), playerloc.getX() + x, playerloc.getY() + y,
									playerloc.getZ() + z);
							if (!location.getBlock().isEmpty()) {
								Y = 10;
								if (!location.add(0, Y, 0).getBlock().isEmpty()) {
									Y = Y + 10;
									if (!location.add(0, Y, 0).getBlock().isEmpty()) {
										Y = Y + 10;
										if (!location.add(0, Y, 0).getBlock().isEmpty()) {
											Y = Y + 10;
											if (!location.add(0, Y, 0).getBlock().isEmpty()) {
												Y = Y + 10;
											}
										}
									}
								}
							}
							Location location2 = new Location(p.getWorld(), playerloc.getX() + x,
									playerloc.getY() + Y + y, playerloc.getZ() + z);
							location2.getBlock().setType(Material.GLASS);
							b.add(location2.getBlock());
							blocks.add(location2.getBlock());
						}
					}
				}
				for (int x = -8; x <= 9; x++) {
					for (int y = 1; y <= 9; y++) {
						for (int z = -9; z <= 8; z++) {
							Location location = new Location(p.getWorld(), playerloc.getX() + x,
									playerloc.getY() + Y + y, playerloc.getZ() + z);
							location.getBlock().setType(Material.AIR);
						}
					}
				}
				loc.put(p, p.getLocation());
				loc.put(target, target.getLocation());
				gladiator.add(p);
				gladiator.add(target);
				batalha.put(p, target);
				batalha.put(target, p);
				remove.put(p, blocks);
				remove.put(target, blocks);
				p.teleport(playerloc.getLocation().add(8.5, 1 + Y, 7.5));
				p.getLocation().setYaw(135.0F);
				target.teleport(playerloc.getLocation().add(-6.5, 1 + Y, -7.5));
				target.getLocation().setYaw(315.0F);
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 99999));
				target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 99999));
				new BukkitRunnable() {
					public void run() {
						p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 99999, 5));
						target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 99999, 5));
					}
				}.runTaskLater(Main.instance, 2400);
				new BukkitRunnable() {
					public void run() {
						if ((gladiator.contains(p)) && (gladiator.contains(target))) {
							p.teleport(loc.get(p));
							target.teleport(loc.get(target));
							removeGladiator(p);
							removeGladiator(target);
							p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 99999));
							target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 99999));
						}
					}
				}.runTaskLater(Main.instance, 3600);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	void GladiatorKit(BlockDamageEvent e) {
		if (b.contains(e.getBlock())) {
			e.getPlayer().sendBlockChange(e.getBlock().getLocation(), Material.BEDROCK, (byte) 0);
		}
	}

	public static void removeGladiator(Player p) {
		if (remove.get(p) != null) {
			List<Block> blocks = (List<Block>) remove.get(p);
			for (Block block : blocks) {
				if ((block.getType() != null) && (block.getType() != Material.AIR)) {
					block.setType(Material.AIR);
				}
			}
			p.removePotionEffect(PotionEffectType.WITHER);
			loc.remove(p);
			batalha.remove(p);
			gladiator.remove(p);
			remove.remove(p);
		}
	}
}