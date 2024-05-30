package HG.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import HG.manager.PlayerManager;

public class Invisivel {
	public static void Invisibilidade(Player p) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!player.getName().equals(p.getName())) {
				if (PlayerManager.inSpec(p)) {
					player.hidePlayer(p);
				} else if (PlayerManager.inAdmin(p)) {
					player.hidePlayer(p);
					// } else if (Specs.specs.contains(p)) {
					// p.showPlayer(player);
				}
			}
		}
	}

	public static void Retomar() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!PlayerManager.inGame(player)) {
				Invisibilidade(player);
			} else {
				Visibilidade(player);
			}
		}
	}

	public static void Visibilidade(Player p) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.showPlayer(p);
		}
	}
}