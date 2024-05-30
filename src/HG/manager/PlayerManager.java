package HG.manager;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import HG.habilidades.Madman;
import HG.listeners.PlayerQuit;
import HG.utils.Invisivel;

public class PlayerManager {
	static ArrayList<Player> spectador = new ArrayList<>();
	static ArrayList<Player> admin = new ArrayList<>();

	public static Player[] getGame() {
		ArrayList<Player> gamers = new ArrayList<Player>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if ((!inSpec(p)) && (!inAdmin(p))) {
				gamers.add(p);
			}
		}
		return (Player[]) gamers.toArray(new Player[0]);
	}

	public static boolean inGame(Player p) {
		if ((inAdmin(p)) || (inSpec(p))) {
			return false;
		}
		return true;
	}

	public static void addSpec(Player p) {
		if (!spectador.contains(p)) {
			spectador.add(p);
		}
		Invisivel.Invisibilidade(p);
		p.setGameMode(GameMode.ADVENTURE);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.updateInventory();
		p.setAllowFlight(true);
		p.setFlying(true);
		p.sendMessage("§cVocê morreu!\nAgora você está espectando");
		p.spigot().setCollidesWithEntities(false);
	}

	public static boolean inSpec(Player p) {
		return spectador.contains(p);
	}

	public static void addAdmin(Player p) {
		if (!admin.contains(p)) {
			admin.add(p);
		}
		Invisivel.Invisibilidade(p);
		p.sendMessage("§aVocê entrou no modo admin\n§fVocê está invisível para os jogadores");
		p.setGameMode(GameMode.CREATIVE);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
		p.getInventory().addItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 1));
		p.getInventory().addItem(new ItemStack(Material.SLIME_BALL));
		p.spigot().setCollidesWithEntities(false);
	}

	public static boolean inAdmin(Player p) {
		return admin.contains(p);
	}

	public static void removeAdmin(Player p) {
		admin.remove(p);
		Invisivel.Visibilidade(p);
		p.sendMessage("§cVocê saiu do modo admin\n§fVocê está visivel para todos");
		p.setGameMode(GameMode.SURVIVAL);
		p.getInventory().removeItem(new ItemStack(Material.DIAMOND_SWORD));
		p.getInventory().removeItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 1));
		p.getInventory().removeItem(new ItemStack(Material.SLIME_BALL));
		p.spigot().setCollidesWithEntities(true);
	}

	public static void respawn(final Player p) {
		int x = new Random().nextInt(500);
		int z = new Random().nextInt(500);
		if (x < 300) {
			x = 300;
		}
		if (z < 300) {
			z = 300;
		}
		if (new Random().nextBoolean()) {
			x = -x;
		}
		if (new Random().nextBoolean()) {
			z = -z;
		}
		p.teleport(p.getWorld().getHighestBlockAt(x, z).getLocation().add(0, 5, 0));
		p.setFoodLevel(20);
		p.setFireTicks(0);
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 100000));
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 9));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 9));
		p.getInventory().setArmorContents(null);
		p.getInventory().clear();
		p.updateInventory();
		PlayerQuit.combatLog.remove(p);
		Madman.madman.remove(p);
	}
}