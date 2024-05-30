package HG.habilidades;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.manager.PlayerManager;
import HG.utils.Status;
import HG.utils.TimeSecondEvent;

public class Camel implements Listener {
	@EventHandler
	public void CamelKit(TimeSecondEvent e) {
		for (Player p : PlayerManager.getGame()) {
			if ((KitManager.getKit(p).equals("Camel")) && (GameManager.status != Status.PréJogo)) {
				if (p.getLocation().add(0, -0.5, 0).getBlock().getType() == Material.SAND) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 0));
				}
			}
		}
	}
}