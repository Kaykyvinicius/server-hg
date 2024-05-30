package HG.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import HG.manager.GameManager;
import HG.utils.Status;

public class PlayerLogin implements Listener {
	
	@EventHandler
	void PlayerLoginEvent(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		if (GameManager.status == Status.PréJogo) {
			if (Bukkit.getOnlinePlayers().equals(Bukkit.getMaxPlayers())) {
				if (!p.hasPermission("hg.full")) {
					e.disallow(PlayerLoginEvent.Result.KICK_FULL, "§cServidor Cheio");
				}
			}
		} else if (PlayerQuit.relog.contains(p.getName())) {
			return;
		} else if (GameManager.game < 300) {
			if (!p.hasPermission("hg.join")) {
				e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cTorneio em andamento");
			}
		} else if (GameManager.game > 300) {
			if (!p.hasPermission("hg.spec")) {
				e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cTorneio em andamento");
			}
		}
		if (e.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST) {
			if (!p.hasPermission("hg.comando.admin")) {
				e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "§cVocê não está listado para entrar no servidor!");
			}
		}
	}
}