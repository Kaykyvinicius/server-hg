package HG.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import HG.manager.GameManager;
import HG.manager.PlayerManager;
import HG.utils.Status;

public class PlayerDrop implements Listener {
	@EventHandler
	void PlayerDropItemEvent(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if ((GameManager.status == Status.PréJogo) || (!PlayerManager.inGame(p))) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	void PlayerPickupItemEvent(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		if ((GameManager.status == Status.PréJogo) || (!PlayerManager.inGame(p))) {
			e.setCancelled(true);
		}
	}
}