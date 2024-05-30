package HG.habilidades;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import HG.manager.KitManager;

public class Gambler implements Listener {
	@EventHandler
	void gambler(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (((e.getAction() == Action.RIGHT_CLICK_BLOCK)) && (KitManager.getKit(p).equals("Gambler")) && (e.getClickedBlock().getType() == Material.STONE_BUTTON)) {
			Random r = new Random();
			int rand = r.nextInt(5);
			int random = r.nextInt(1000);
			if (random == 1000) {
				p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
				p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
				p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
				p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
				p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
				p.sendMessage("§9Você ganhou: §6Todas as armaduras de diamante");
				return;
			}
			if (rand == 1) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 0));
				p.sendMessage("§9Você ganhou: §6regeneração");
			} else if (rand == 2) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 0));
				p.sendMessage("§9Você ganhou: §6velocidade");
			} else if (rand == 3) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 0));
				p.sendMessage("§9Você ganhou: §6resistencia ao fogo");
			} else if (rand == 4) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 600, 0));
				p.sendMessage("§9Você ganhou: §6veneno");
			} else if (rand == 5) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 600, 0));
				p.sendMessage("§9Você ganhou: §6visão noturna");
			} else if (rand == 6) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 0));
				p.sendMessage("§9Você ganhou: §6fraqueza");
			} else if (rand == 7) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0));
				p.sendMessage("§9Você ganhou: §6força");
			} else if (rand == 8) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
				p.sendMessage("§9Você ganhou: §6lentidão");
			} else if (rand == 9) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 0));
				p.sendMessage("§9Você ganhou: §6invisibilidade");
			} else if (rand == 10) {
				p.sendMessage("§9Você ganhou: §6nada");
			}
		}
	}
}
