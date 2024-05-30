package HG.listeners;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import HG.Main;
import HG.habilidades.Gladiator;
import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.manager.PlayerManager;
import HG.mysql.SQLStatus;
import HG.utils.Invisivel;
import HG.utils.Itens;
import HG.utils.Status;
import HG.utils.Win;

public class PlayerQuit implements Listener {
	public static ArrayList<String> relog = new ArrayList<String>();
	public static ArrayList<String> reloged = new ArrayList<String>();
	public static HashMap<Player, Player> combatLog = new HashMap<Player, Player>();

	@EventHandler(priority = EventPriority.HIGH)
	void PlayerQuitEvent(PlayerQuitEvent e) {
		Invisivel.Retomar();
		e.setQuitMessage(null);
		final Player p = e.getPlayer();
		String s = "";
		String s2 = "";
		if (PlayerManager.getGame().length > 2) {
			s = "s";
			s2 = "es";
		}
		final String players = "§c" + ((int) PlayerManager.getGame().length - 1) + " jogador" + s2 + " restante" + s
				+ ".";
		if (Gladiator.gladiator.contains(p)) {
			Player killer = Gladiator.batalha.get(p);
			killer.teleport(Gladiator.loc.get(killer));
			p.damage(20.0D, killer);
			Gladiator.removeGladiator(p);
			Gladiator.removeGladiator(killer);
			Itens.dropItems(p, killer.getLocation());
			killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 99999));
		}
		if ((combatLog.containsKey(p)) && (combatLog.get(p) != null) && (((Player) combatLog.get(p)).isOnline())
				&& (!p.isDead())) {
			Player killer = combatLog.get(p);
			PlayerDeath.kills.put(killer.getUniqueId(), PlayerDeath.kills.get(killer.getUniqueId()) + 1);
			SQLStatus.addStatus(killer, "Kills");
			Itens.dropItems(p, p.getLocation());
			Bukkit.broadcastMessage("§c" + p.getName() + "§6(§e" + KitManager.playerKit(p) + "§6) §7deslogou em combat!\n" + players);
			Win.checarGanhador();
			p.damage(20.0D, killer);
			p.setHealth(0.0D);
			return;
		}
		if ((!relog.contains(p.getName())) && (PlayerManager.inGame(p)) && (!p.isDead())
				&& (GameManager.status != Status.PréJogo)) {
			relog.add(p.getName());
			new BukkitRunnable() {
				public void run() {
					if (!reloged.contains(p.getName())) {
						relog.remove(p.getName());
						Bukkit.broadcastMessage("§c" + p.getName() + "§6(§e" + KitManager.playerKit(p)
								+ "§6) §7demorou para voltar e foi desclassificado.\n" + players);
						Itens.dropItems(p, p.getLocation());
						Win.checarGanhador();
					} else {
						reloged.remove(p.getName());
					}
				}
			}.runTaskLater(Main.instance, 1200);
			return;
		}
	}
}