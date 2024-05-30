package HG.habilidades;

import java.util.Random;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.utils.Status;

public class Magma implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getDamager() instanceof Player
				&& event.getEntity() instanceof Player) {
			LivingEntity entity = (LivingEntity) event.getEntity();
			if (KitManager.getKit((Player)event.getDamager()).equals("Magma")) {
				if (new Random().nextInt(2) == 0) {
					entity.setFireTicks(5 * 20);
				}
			}
		}
	}
	@EventHandler
	public void move(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Block b = p.getLocation().getBlock();
		if ((b.getType().name().contains("WATER") || b.getRelative(BlockFace.UP).getType().name().contains("WATER"))
				&& GameManager.status == Status.Jogo && KitManager.getKit(p).equals("Magma")) {
			p.damage(1.0D);
		}
	}
}
