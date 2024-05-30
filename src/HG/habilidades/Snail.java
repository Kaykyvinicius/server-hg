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

public class Snail implements Listener {
	@EventHandler
	public void evenenandoOCara(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (((event.getDamager() instanceof Player)) && ((event.getEntity() instanceof LivingEntity))) {
			LivingEntity entity = (LivingEntity) event.getEntity();
			Player p = (Player) event.getDamager();
			if ((KitManager.getKit(p).equals("Snail")) && (new Random().nextInt(3) == 1)) {
				entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, 0), true);
			}
		}
	}
}