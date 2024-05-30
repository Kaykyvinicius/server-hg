package HG.listeners;

import java.util.ArrayList;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import HG.Main;
import HG.comandos.Dano;
import HG.comandos.PvP;
import HG.manager.GameManager;
import HG.manager.PlayerManager;
import HG.utils.Status;

public class EntityDamage implements Listener {
	public static ArrayList<Player> pvp = new ArrayList<>();

	@EventHandler
	void EntityDamagee(EntityDamageEvent e) {
		if (GameManager.status == Status.PréJogo) {
			e.setCancelled(true);
		}
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if ((GameManager.status != Status.Jogo) || (PlayerManager.inSpec(p))) {
				e.setCancelled(true);
			}
		}
		if (Dano.dano == false) {
			e.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	void EntityDamageByEntitye(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player) && (!e.isCancelled())) {
			final Player p = (Player) e.getEntity();
			final Player damager = (Player) e.getDamager();
			if ((GameManager.status == Status.Jogo) && (PlayerManager.inGame(p)) && (PlayerManager.inGame(damager))) {
				pvp.add(damager);
				PlayerQuit.combatLog.put(p, damager);
				PlayerQuit.combatLog.put(damager, p);
				new BukkitRunnable() {
					public void run() {
						PlayerQuit.combatLog.remove(p);
						PlayerQuit.combatLog.remove(damager);
					}
				}.runTaskLater(Main.instance, 200);
				new BukkitRunnable() {
					public void run() {
						pvp.remove(damager);
					}
				}.runTaskLater(Main.instance, 20);
			}
			/*
			 * if (pManager.inGame(damager)) { listinha.remove(damager);
			 * hitted.remove(damager);
			 * Bukkit.getServer().getScheduler().getActiveWorkers().remove(
			 * damager); if (!hitted.containsKey(damager)) { hitted.put(damager,
			 * p); listinha.add(damager); new BukkitRunnable() { public void
			 * run() { listinha.remove(damager); hitted.remove(damager);
			 * cancel(); } }.runTaskTimer(Main.instance, 0, 1); } }
			 */
		}
		if (e.getEntity() instanceof Player) {
			if (PvP.pvp == false) {
				e.setCancelled(true);
			}
		}
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if ((PlayerManager.inAdmin(p)) && (!p.hasPermission("hg.build"))) {
				e.setCancelled(true);
			}
			if (PlayerManager.inSpec(p)) {
				e.setCancelled(true);
			}
			if (p.getItemInHand().getType().name().contains("SWORD")) {
				e.setDamage(e.getDamage() - 2.0D);
			}
			if ((p.getFallDistance() > 0.0F) && (!p.isOnGround()) && (p.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)) && (p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))) {
				if (p.getItemInHand().getType().name().contains("SWORD")) {
					e.setDamage(e.getDamage() - 1.0D);
				}
			}
			if ((p.getFallDistance() > 0.0F) && (!p.isOnGround()) && (p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))) {
				if (p.getItemInHand().getType().name().contains("SWORD")) {
					e.setDamage(e.getDamage() - 1.0D);
				}
			}
			if ((p.getFallDistance() > 0.0F) && (!p.isOnGround()) && (p.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL))) {
				if (p.getItemInHand().getType().name().contains("SWORD")) {
					e.setDamage(e.getDamage() - 1.0D);
				}
			}
			if ((p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) && (p.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL))) {
				if (p.getItemInHand().getType().name().contains("SWORD")) {
					e.setDamage(e.getDamage() - 0.5D);
				}
			}
			if (p.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)) {
				if (p.getItemInHand().getType().name().contains("SWORD")) {
					e.setDamage(e.getDamage() - 1.0D);
				}
			}
			if ((p.getFallDistance() > 0.0F) && (!p.isOnGround())) {
				if (p.getItemInHand().getType().name().contains("SWORD")) {
					e.setDamage(e.getDamage() - 1.0D);
				}
			}
			if (p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
				if (p.getItemInHand().getType().name().contains("SWORD")) {
					e.setDamage(e.getDamage() - 1.0D);
				}
			}
		}
	}

	@EventHandler
	void EntityTargete(EntityTargetEvent e) {
		if ((GameManager.status == Status.PréJogo) || (!PlayerManager.inGame((Player) e.getTarget()))) {
			e.setCancelled(true);
		}
	}
}