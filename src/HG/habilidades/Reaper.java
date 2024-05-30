package HG.habilidades;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import HG.manager.KitManager;

public class Reaper implements Listener {

	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent event) {
		if ((event.getDamager() instanceof Player)) {
			Player p = (Player) event.getDamager();
			if (KitManager.getKit(p).equals("Reaper")) {
				if (p.getItemInHand().getType() == Material.WOOD_HOE) {
					if (event.getEntity() instanceof LivingEntity) {
						((LivingEntity) event.getEntity()).addPotionEffect(
								new PotionEffect(PotionEffectType.WITHER,
										4 * 20, 0), true);
					}
				}
			}
		}
	}
}
