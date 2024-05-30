package HG.listeners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import HG.Main;
import HG.habilidades.Gladiator;
import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.manager.PlayerManager;
import HG.manager.Spectator;
import HG.mysql.SQLStatus;
import HG.utils.Itens;
import HG.utils.Name;
import HG.utils.Status;
import HG.utils.Win;

public class PlayerDeath implements Listener {
	public static HashMap<UUID, Integer> kills = new HashMap<>();

	@EventHandler(priority = EventPriority.HIGH)
	void PlayerDeathEvent(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		final Player p = (Player) e.getEntity();
		if (!PlayerManager.inGame(p)) {
			e.getDrops().clear();
			e.setDroppedExp(0);
			Win.checarGanhador();
			return;
		}
		if (GameManager.status != Status.PréJogo) {
			Player killer = null;
			if ((PlayerQuit.combatLog.containsKey(p)) && (PlayerQuit.combatLog.get(p) != null)
					&& (((Player) PlayerQuit.combatLog.get(p)).isOnline()) && (!p.isDead())) {
				killer = PlayerQuit.combatLog.get(p);
				PlayerQuit.combatLog.remove(p);
			}
			if (killer != null) {
				kills.put(killer.getUniqueId(), kills.get(killer.getUniqueId()) + 1);
				if (KitManager.getKit(killer).equals("Specialist")) {
					killer.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
				}
			}
			if (Gladiator.gladiator.contains(p)) {
				Player killerglad = Gladiator.batalha.get(p);
				killerglad.teleport(Gladiator.loc.get(killerglad));
				Gladiator.removeGladiator(p);
				Gladiator.removeGladiator(killerglad);
				Itens.dropItems(p, killerglad.getLocation());
				killerglad.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 99999));
			} else if ((killer != null) && (KitManager.getKit(killer).equals("Tank"))) {
				p.getWorld().createExplosion(p.getLocation(), 2.0F);
				Itens.dropItems(p, p.getLocation().add(0, 3, 0));
			} else {
				Itens.dropItems(p, p.getLocation());
			}
			e.getDrops().clear();
			if ((p.hasPermission("hg.respawn")) && (GameManager.game < 300)) {
				p.setHealth(20.0D);
				PlayerManager.respawn(p);
				return;
			}
			SQLStatus.addStatus(p, "Deaths");
			if (killer != null) {
				SQLStatus.addStatus(killer, "Kills");
			}
			// EntityDamageEvent cause = p.getLastDamageCause();
			EntityDamageEvent.DamageCause causa = p.getLastDamageCause().getCause();
			String player = "§c" + p.getName() + "§6(§e" + KitManager.playerKit(p) + "§6)§7";
			String s = "";
			String s2 = "";
			if (PlayerManager.getGame().length > 2) {
				s = "s";
				s2 = "es";
			}
			String players = "§c" + ((int) PlayerManager.getGame().length - 1) + " jogador" + s2 + " restante" + s
					+ ".";
			if (causa == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
				Bukkit.broadcastMessage(
						player + " não achava que os terroristas iriam colocar uma bomba naquele local.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.CONTACT) {
				Bukkit.broadcastMessage(player
						+ " estava no deserto e achava que o cacto era uma boa madeira de matar a sede.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.CUSTOM) {
				Bukkit.broadcastMessage(player + " acho que o mundo nunca iria acabar.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.DROWNING) {
				Bukkit.broadcastMessage(player + " tentou nadar para a terra firme mas não conseguio.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
				Bukkit.broadcastMessage(player + " estava no local alvo dos terroristas.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.FALL) {
				Bukkit.broadcastMessage(player + " caiu de um lugar muito alto e quebro as pernas.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
				Bukkit.broadcastMessage(player + " achou que aquela pedrinha não iria machucar.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.FIRE) {
				Bukkit.broadcastMessage(
						player + " não aprendeu com sua mãe que brincar com fogo é perigoso.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.FIRE_TICK) {
				Bukkit.broadcastMessage(
						player + " não aprendeu com sua mãe que brincar com fogo é perigoso.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.LAVA) {
				Bukkit.broadcastMessage(
						player + " não aprendeu com sua mãe que brincar com fogo é perigoso.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.LIGHTNING) {
				Bukkit.broadcastMessage(player + " saiu em um dia chuvoso e não levou o guarda-chuva.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.MAGIC) {
				Bukkit.broadcastMessage(player + " brincou com muitos poções perigosas.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.MELTING) {
				Bukkit.broadcastMessage(
						player + " não contava que o objeto seria transformado nesse momento.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.POISON) {
				Bukkit.broadcastMessage(player + " foi envenenado.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.PROJECTILE) {
				Bukkit.broadcastMessage(player + " morreu para uma bala perdida.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.STARVATION) {
				Bukkit.broadcastMessage(player + " era vegetariano e não queria matar os animais.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.SUFFOCATION) {
				Bukkit.broadcastMessage(player + " foi enterrado vivo.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.SUICIDE) {
				Bukkit.broadcastMessage(player + " não gostava da vida por isso suicidou-se.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.THORNS) {
				Bukkit.broadcastMessage(player + " achou que matar seu inimigo seria fácil.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.VOID) {
				Bukkit.broadcastMessage(player + " tentou chegar no núcleo da terra.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.WITHER) {
				Bukkit.broadcastMessage(
						player + " foi desovado e a policia não achou seu corpo rapidamente.\n" + players);
			} else if (causa == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
				Entity entity = ((EntityDamageByEntityEvent) p.getLastDamageCause()).getDamager();
				if (entity instanceof Player) {
					Player killers = (Player) entity;
					String color = "";
					if (killers.getUniqueId().equals("d1ae20bd-c354-4bc5-bdda-cccd3f9aab52")) {
						color = "§d";
					}
					Bukkit.broadcastMessage("§a" + color + killers.getName() + "§6(§e" + KitManager.playerKit(killers)
							+ "§6) §7matou " + player + " usando sua " + Name.getItemName(killers.getItemInHand())
							+ ".\n" + players);
				} else if (entity instanceof Creeper) {
					Bukkit.broadcastMessage(player + " foi desmaterializado por um Creeper.\n" + players);
				} else if (entity instanceof Zombie) {
					Bukkit.broadcastMessage(player + " foi comido vivo por um Zombie.\n" + players);
				} else {
					Bukkit.broadcastMessage(player + " morreu para um monstro.\n" + players);
				}
			} else {
				Bukkit.broadcastMessage(player + " morreu!\n" + players);
			}
			if ((!p.hasPermission("hg.spec")) && (!p.hasPermission("hg.comando.admin"))) {
				PlayerManager.addSpec(p);
				if (killer != null) {
					//Spectator.spectateOn(p, killer);
				}
				new BukkitRunnable() {
					public void run() {
						ByteArrayDataOutput out = ByteStreams.newDataOutput();
						out.writeUTF("Connect");
						out.writeUTF("lobby");
						p.sendPluginMessage(Main.instance, "BungeeCord", out.toByteArray());
					}
				}.runTaskLater(Main.instance, 100);
			} else {
				if (p.hasPermission("hg.comando.admin")) {
					PlayerManager.addAdmin(p);
				} else if (p.hasPermission("hg.spec")) {
					PlayerManager.addSpec(p);
					if (!p.hasPermission("spec.yt")) {
						if (killer != null) {
							//Spectator.spectateOn(p, killer);
						}
						new BukkitRunnable() {
							public void run() {
								Spectator.specateOff(p);
							}
						}.runTaskLater(Main.instance, 100);
					}
				}
			}
		}
		Win.checarGanhador();
	}
}