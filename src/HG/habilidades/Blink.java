package HG.habilidades;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import HG.manager.KitManager;

public class Blink implements Listener {
	public static HashMap<Player, Integer> use = new HashMap<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void BlinkKit(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if ((e.getAction().name().contains("RIGHT")) && (p.getItemInHand().getType() == Material.NETHER_STAR)
				&& (KitManager.getKit(p).equals("Blink"))) {
			if (KitManager.hasCooldown(p)) {
				KitManager.messageCooldown(p);
				return;
			}
			if (use.get(p) == null) {
				use.put(p, Integer.valueOf(0));
			}
			final Block b = p.getTargetBlock(null, 3);
			if (b.getType() == Material.AIR) {
				b.getLocation().getBlock().setType(Material.LEAVES);
				Location loc = b.getLocation().add(0.5, 0, 0.5);
				loc.setPitch(p.getLocation().getPitch());
				loc.setYaw(p.getLocation().getYaw());
				p.teleport(loc.add(0, 1, 0));
			} else {
				Block down = p.getEyeLocation().getBlock();
				Location loc = down.getLocation().add(0.5, -6, 0.5);
				loc.setPitch(p.getLocation().getPitch());
				loc.setYaw(p.getLocation().getYaw());
				p.teleport(loc.add(0, 0, 0));
			}
			p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 1.0F, 1.0F);
			use.put(p, use.get(p) + 1);
			if (use.get(p) > 2) {
				KitManager.addCooldown(p, 31);
				use.put(p, Integer.valueOf(0));
			}
		}
	}
}