package HG.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import HG.comandos.Chat;
import HG.manager.PlayerManager;

public class AsyncPlayerChat implements Listener {
	
	@EventHandler
	void AsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
		if (e.isCancelled()) {
			return;
		}
		final Player p = e.getPlayer();
		if ((Chat.chat == false) && (!p.hasPermission("hg.chat"))) {
			e.setCancelled(true);
			p.sendMessage("§cO chat está desativado");
		}
		if (PlayerManager.inSpec(p)) {
			e.setCancelled(true);
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (!PlayerManager.inGame(players)) {
					players.sendMessage("§6[Spectador] " + p.getDisplayName() + " §r» " + e.getMessage());
				}
			}
			return;
		}
		if (e.getMessage().contains("%")) {
			e.setCancelled(true);
			Bukkit.broadcastMessage(p.getDisplayName() + " §r» " + e.getMessage());
			return;
		}
		e.setFormat(p.getDisplayName() + " §r» " + e.getMessage());
	}
}