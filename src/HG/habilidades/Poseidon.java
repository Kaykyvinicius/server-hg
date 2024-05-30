package HG.habilidades;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.utils.Status;

public class Poseidon implements Listener {

	@EventHandler
	public void poseidon(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		Block b = p.getLocation().getBlock();
		if ((b.getType().name().contains("WATER") || b.getRelative(BlockFace.UP).getType().name().contains("WATER"))
				&& GameManager.status == Status.Jogo && KitManager.getKit(p).equals("Poseidon")) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0), true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0), true);
		}
	}
}
