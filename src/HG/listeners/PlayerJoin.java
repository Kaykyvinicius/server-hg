package HG.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import HG.Main;
import HG.kitselector.KitSelector;
import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.manager.PlayerManager;
import HG.mysql.SQLStatus;
import HG.utils.Invisivel;
import HG.utils.Status;

public class PlayerJoin implements Listener {
	
	@EventHandler
	void PlayerJoinEvent(PlayerJoinEvent e) {
		Invisivel.Retomar();
		e.setJoinMessage(null);
		final Player p = e.getPlayer();
		p.spigot().setCollidesWithEntities(false);
		SQLStatus.setStatus(p);
		if (!PlayerDeath.kills.containsKey(p.getUniqueId())) {
			PlayerDeath.kills.put(p.getUniqueId(), 0);
		}
		if (GameManager.status == Status.PréJogo) {
			p.getInventory().setHeldItemSlot(0);
			if (Bukkit.getOnlinePlayers().equals(Bukkit.getMaxPlayers())) {
				Bukkit.broadcastMessage("§cServidor Cheio!\n§eTempo alterado para 30 segundos.");
				GameManager.pre = 31;
			}
			if (p.hasPermission("hg.comando.admin")) {
				PlayerManager.addAdmin(p);
			}
			KitManager.showAllKits(p);
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.getInventory().addItem(KitSelector.item(Material.CHEST, "§aKit Selector", 1, (short) 0, null));
			p.teleport(new Location(p.getWorld(), 0, p.getWorld().getHighestBlockYAt(0, 0) + 32, 0));
			if (GameManager.pre < 21) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 99999));
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 99999));
			}
		} else if ((PlayerQuit.relog.contains(p.getName())) && (PlayerManager.inGame(p))) {
			PlayerQuit.reloged.add(p.getName());
			new BukkitRunnable() {
				public void run() {
					PlayerQuit.relog.remove(p.getName());
				}
			}.runTaskLater(Main.instance, 400);
			return;
		} else if (GameManager.game < 300) {
			if ((PlayerManager.inGame(p)) && (p.hasPermission("hg.join"))) {
				PlayerManager.respawn(p);
				p.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
			}
		} else if (GameManager.game > 300) {
			if (p.hasPermission("hg.comando.admin")) {
				PlayerManager.addAdmin(p);
			} else if (p.hasPermission("hg.spec")) {
				PlayerManager.addSpec(p);
			}
		}
	}
}