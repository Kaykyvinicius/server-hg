package HG.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import HG.Main;
import HG.kitselector.KitSelector;
import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.manager.Kits;
import HG.manager.PlayerManager;
import HG.manager.Spectator;
import HG.utils.Invisivel;
import HG.utils.KitItens;
import HG.utils.Status;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class PlayerInteract implements Listener {
	@EventHandler
	void PlayerInteractEvent(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if ((e.getClickedBlock().getType() == Material.CHEST) && (GameManager.status != Status.PréJogo)
					&& (PlayerManager.inAdmin(p)) || ((PlayerManager.inSpec(p)) && (p.hasPermission("spec.yt")))) {
				e.setCancelled(true);
				Chest c = (Chest) e.getClickedBlock().getState();
				Inventory inv = Bukkit.createInventory(null, c.getInventory().getSize(), "Chest");
				for (int i = 0; i < c.getInventory().getSize(); i++) {
					inv.setItem(i, c.getInventory().getItem(i));
				}
				p.openInventory(inv);
			}
			for (Kits kit : Kits.values()) {
				if (p.getItemInHand().equals(KitSelector.item(Material.WOOL, "§aPresente do kit " + kit, 1,
						(short) new Random().nextInt(15), null))) {
					e.setCancelled(true);
					p.setItemInHand(null);
					KitItens.giveItens(p, kit);
				}
			}
		}
		if (((e.getAction().name().contains("RIGHT")) || (e.getAction().name().contains("LEFT")))
				&& (GameManager.status == Status.PréJogo) && (p.getItemInHand().getType() == Material.CHEST)) {
			e.setCancelled(true);
			KitSelector.openKitSelector(p);
		}
		if ((KitManager.getKit(p).equals("Gladiator")) && (p.getItemInHand().getType() == Material.IRON_FENCE)) {
			e.setCancelled(true);
		}
		if ((GameManager.status == Status.PréJogo) || (PlayerManager.inSpec(p))) {
			e.setCancelled(true);
		}
		if ((p.getItemInHand().getType() == Material.SLIME_BALL) && (e.getAction().name().contains("RIGHT"))
				&& (PlayerManager.inAdmin(p))) {
			Invisivel.Visibilidade(p);
			p.setGameMode(GameMode.SURVIVAL);
			new BukkitRunnable() {
				public void run() {
					Invisivel.Invisibilidade(p);
					p.setGameMode(GameMode.CREATIVE);
					p.setFlying(true);
					p.setAllowFlight(true);
				}
			}.runTaskLater(Main.instance, 10);
		}
		if ((p.getItemInHand().getType() == Material.DIAMOND_SWORD) && (e.getAction().name().contains("RIGHT"))
				&& (PlayerManager.inAdmin(p))) {
			e.setCancelled(true);
			if (EntityDamage.pvp.size() == 0) {
				p.sendMessage("§cNão há nenhum jogar em pvp!");
				return;
			}
			Player target = EntityDamage.pvp.get(new Random().nextInt(EntityDamage.pvp.size()));
			p.sendMessage(target.getDisplayName() + " §7attacked " + PlayerQuit.combatLog.get(target).getDisplayName());
			p.teleport(target);
		}
		if ((p.getItemInHand().equals(new ItemStack(Material.SKULL_ITEM, 1, (short) 1)))
				&& (e.getAction().name().contains("RIGHT")) && (PlayerManager.inAdmin(p))) {
			e.setCancelled(true);
			ArrayList<Player> online = new ArrayList<>();
			for (Player gamers : PlayerManager.getGame()) {
				if (!online.contains(gamers)) {
					online.add(gamers);
				}
			}
			Player player = online.get(new Random().nextInt(online.size()));
			p.teleport(player);
			p.sendMessage(
					"§7Teleportado para: " + player.getDisplayName() + "§6(" + KitManager.playerKit(player) + ")");
			online.clear();
		}
		if (((e.getAction().name().contains("RIGHT")) || (e.getAction().name().contains("LEFT")))
				&& (p.getItemInHand().getType() == Material.COMPASS) && (GameManager.status != Status.PréJogo)) {
			if (GameManager.inven > 60) {
				p.sendMessage("§cVocê não pode usar a bússola antes de um minuto de invencibilidade!");
				return;
			}
			Boolean found = false;
			for (Entity entity : p.getNearbyEntities(1000, 130.0D, 1000)) {
				if (entity instanceof Player) {
					Player target = (Player) entity;
					if ((PlayerManager.inGame(target)) && (target.getLocation().distance(p.getLocation()) >= 10.0D)) {
						p.setCompassTarget(target.getLocation());
						p.sendMessage("§eBússola apontada para " + target.getName());
						found = true;
						return;
					}
				}
			}
			if (!found) {
				p.sendMessage("§cNenhum jogador foi encontrado, bússola apontando para o spawn.");
				p.setCompassTarget(p.getWorld().getSpawnLocation());
			}
		}
		ItemStack bowl = new ItemStack(Material.BOWL, 1);
		ItemMeta meta = bowl.getItemMeta();
		EntityPlayer p2 = ((CraftPlayer) p).getHandle();
		if (p2.getHealth() <= 19 && e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& p.getItemInHand().getType() == Material.MUSHROOM_SOUP && p2.getHealth() < p2.getMaxHealth()) {
			int heal = 7;
			if (e.getPlayer().getItemInHand().getType() == Material.MUSHROOM_SOUP) {
				if (p2.getHealth() >= 13) {
					p.setHealth(20.0D);
				} else {
					p.setHealth(p2.getHealth() + heal > p2.getMaxHealth() ? p2.getMaxHealth() : p2.getHealth() + heal);
				}
				p.getItemInHand().setType(Material.BOWL);
				p.getItemInHand().setItemMeta(meta);
				p.setItemInHand(bowl);
				p.updateInventory();
			}
		} else if (p.getFoodLevel() <= 19 && e.getAction() == Action.RIGHT_CLICK_AIR
				|| e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getItemInHand().getType() == Material.MUSHROOM_SOUP
						&& p.getFoodLevel() < 20) {
			if (!(p.getFoodLevel() == 20)) {
				if (e.getPlayer().getItemInHand().getType() == Material.MUSHROOM_SOUP) {
					if (p.getFoodLevel() >= 15) {
						p.setFoodLevel(20);
					} else {
						int tofeed = 20 - e.getPlayer().getFoodLevel();
						tofeed = 5;
						e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel() + tofeed);
					}
					p.getItemInHand().setType(Material.BOWL);
					p.getItemInHand().setItemMeta(meta);
					p.setItemInHand(bowl);
					p.updateInventory();
				}
			}
		}
	}

	@EventHandler
	void PlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked() instanceof Player) {
			Player target = (Player) e.getRightClicked();
			if (PlayerManager.inAdmin(p)) {
				p.openInventory(target.getInventory());
			} else if ((PlayerManager.inSpec(p)) && (p.hasPermission("spec.yt")) && (PlayerManager.inGame(target))) {
				if (Spectator.spec.get(p) == null) {
					//Spectator.spectateOn(p, target);
				}
			}
		}
	}
}