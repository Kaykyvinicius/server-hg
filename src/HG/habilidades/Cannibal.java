package HG.habilidades;

import java.util.Random;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import HG.manager.KitManager;

public class Cannibal implements Listener {
	@EventHandler
	public void CannibalKit(EntityDamageByEntityEvent e) {
		if (e.isCancelled()) {
			return;
		}
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			LivingEntity entity = (LivingEntity) e.getEntity();
			Player p = (Player) e.getDamager();
			if (KitManager.getKit(p).equals("Cannibal")) {
				if (new Random().nextInt(2) == 0) {
					entity.addPotionEffect(new PotionEffect(
							PotionEffectType.HUNGER, 5 * 20, 1), true);
					int fome = p.getFoodLevel();
					fome += 1;
					if (fome > 20) {
						fome = 20;
					}
					p.setFoodLevel(fome);
				}
			}
		}
	}

}
