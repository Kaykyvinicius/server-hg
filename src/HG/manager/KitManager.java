package HG.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import HG.Main;
import HG.utils.KitItens;
import HG.utils.Status;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class KitManager {

	public static boolean enabled = true;
	public static HashMap<UUID, Kits> kits = new HashMap<>();
	public static HashMap<UUID, String> surprise = new HashMap<>();
	public static HashMap<Player, Integer> cooldown = new HashMap<>();

	public static void selectKit(Player p, Kits kit) {
		if (GameManager.status == Status.PréJogo) {
			if (enabled == false) {
				p.sendMessage("§cTodos os kits estão desativados!");
				return;
			}
			if (p.hasPermission("kit." + kit.toString().toLowerCase())) {
				p.sendMessage("§aVocê escolheu o kit " + kit + "!");
				kits.put(p.getUniqueId(), kit);
				return;
			} else {
				p.sendMessage("§cVocê não tem o kit " + kit + ".");
			}
		} else {
			if (GameManager.game < 300) {
				if (p.hasPermission("hg.kit") && getKit(p).equals("None")) {
					if (p.hasPermission("kit." + kit.toString().toLowerCase())) {
						p.sendMessage("§aVocê escolheu o kit " + kit + "!");
						kits.put(p.getUniqueId(), kit);
						giveKit(p);
						return;
					}
				}
			}
		}
	}

	public static String getKit(Player p) {
		if (!PlayerManager.inGame(p)) {
			return "Nenhum";
		}
		if (enabled == false) {
			return "Nenhum";
		}
		if (!kits.containsKey(p.getUniqueId())) {
			return "Nenhum";
		}
		return kits.get(p.getUniqueId()).toString();
	}

	public static String playerKit(Player p) {
		if (surprise.containsKey(p.getUniqueId())) {
			return "Surprise " + getKit(p);
		}
		return getKit(p);
	}

	public static void giveKit(final Player p) {
		if (enabled == false) {
			p.sendMessage("§cTodos os kits estão desativados.");
			return;
		}
		p.getInventory().setArmorContents(null);
		p.setExp(0.0F);
		p.setLevel(0);
		p.setAllowFlight(false);
		p.setFlying(false);
		KitItens.giveKitsItens(p);
		p.setGameMode(GameMode.SURVIVAL);
	}

	public static void showAllKits(Player p) {
		String seusKits = "";
		String outrosKits = "";
		ArrayList<String> StringList = new ArrayList<String>();
		List<String> kit = new ArrayList<String>();
		for (Kits kits : Kits.values()) {
			StringList.add(kits.toString());
			kit.add(kits.toString());
		}
		for (String kits : StringList) {
			if (p.hasPermission("kit." + kits.toLowerCase())) {
				seusKits = seusKits + kits + ", ";
			} else {
				outrosKits = outrosKits + kits + ", ";
			}
		}
		if (seusKits.length() != 0) {
			seusKits = seusKits.substring(0, seusKits.length() - 2);
		}
		p.sendMessage("§fEscolha um kit usando /kit <kit> ou /<kit> ou clicando em sobre o nome dele no chat.");
		if (!seusKits.equals("")) {
			IChatBaseComponent comp = ChatSerializer.a("{\"text\":\"§aSeus kits: \",\"extra\":[{\"text\":\"§f" + seusKits + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§aClique para escolher este kit!\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/kit " + kit + "\"}}]}");
			PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			/*
			IChatBaseComponent comp = ChatSerializer.a("{\"text\":\"§aSeus kits: \",\"extra\":[{\"text\":\"§f" + seusKits
							+ "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§aClique para escolher este kit!\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/kit "
							+ StringList + "\"}}]}");
			PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			*/
		} else {
			p.sendMessage("§aSeus kits: §fVocê não tem nenhum kit");
		}
		if (!outrosKits.equals("")) {
			IChatBaseComponent comp = ChatSerializer
					.a("{\"text\":\"§cOutros kits: \",\"extra\":[{\"text\":\"§f" + outrosKits
							+ "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§cVocê não possui este kit!\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/kit "
							+ outrosKits + "\"}}]}");
			PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		} else {
			p.sendMessage("§cOutros kits: §fVocê tem todos os kits");
		}
		// p.sendMessage("§bMore kits available at: ");
	}

	public static boolean isKit(String args) {
		for (Kits kits : Kits.values()) {
			if (args.toLowerCase().equals(kits.toString().toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static void addCooldown(final Player p, int tempo) {
		cooldown.remove(p);
		cooldown.put(p, Integer.valueOf(tempo));
		new BukkitRunnable() {
			public void run() {
				if (!cooldown.containsKey(p)) {
					return;
				}
				cooldown.remove(p);
			}
		}.runTaskLater(Main.instance, tempo * 20);
	}

	public static boolean hasCooldown(Player p) {
		return cooldown.containsKey(p);
	}

	public static void messageCooldown(Player p) {
		p.sendMessage("§7A habilidade do kit " + KitManager.playerKit(p) + " está em cooldown, espere "
				+ cooldown.get(p) + " para usar novamente");
	}

	public static void removeCooldown(Player p) {
		cooldown.remove(p);
		Bukkit.getServer().getScheduler().getActiveWorkers().remove(p);
	}
}