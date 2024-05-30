package HG.habilidades;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import HG.manager.KitManager;

public class Fisherman implements Listener {

	@EventHandler
	public void fishar(PlayerFishEvent event) {
		Player p = event.getPlayer();
		if ((event.getCaught() instanceof Player)) {
			Player caught = (Player) event.getCaught();
			if (KitManager.getKit(p).equals("Fisherman")) {
				caught.teleport(p.getLocation());
			}
		}
	}
}