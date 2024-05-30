package HG.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import HG.manager.GameManager;
import HG.manager.PlayerManager;
import HG.utils.Status;

public class FoodLevelChange implements Listener {
	@EventHandler
	void FoodLevelChangeEvent(FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if ((GameManager.status == Status.PréJogo) || (GameManager.status == Status.Invencibilidade) || (PlayerManager.inSpec(p))) {
				e.setCancelled(true);
			} else {
				p.setSaturation(3.0F);
			}
		}
	}
}