package HG.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import HG.kitselector.KitSelector;
import HG.manager.KitManager;
import HG.manager.Kits;
import HG.manager.PlayerManager;

public class Kit implements CommandExecutor, Listener {
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		if (label.equalsIgnoreCase("kit")) {
			Player p = (Player) sender;
			if (args.length != 1) {
				KitSelector.openKitSelector(p);
				return false;
			}
			if (!KitManager.isKit(args[0])) {
				p.sendMessage("§cO kit " + args[1] + " não existe.");
				return false;
			}
			for (Kits kits : Kits.values()) {
				if (args[0].toLowerCase().equalsIgnoreCase(kits.toString())) {
					KitManager.selectKit(p, kits);
					return false;
				}
			}
		}
		if (label.equalsIgnoreCase("setkit")) {
			if (!sender.hasPermission("hg.comando.setkit")) {
				Character c = '"';
				sender.sendMessage("Unknown command. Type " + c + "/help" + c + "for help.");
				return false;
			}
			if (args.length != 2) {
				sender.sendMessage("§cUse: /setkit (<jogador> | todos) <kit>");
				return false;
			}
			if ((!KitManager.isKit(args[1])) && (!(args[1].toLowerCase().equalsIgnoreCase("nenhum")))) {
				sender.sendMessage("§cO kit " + args[1] + " não existe.");
				return false;
			}
			if (args[0].equalsIgnoreCase("todos")) {
				for (Player gamer : PlayerManager.getGame()) {
					if (args[1].toLowerCase().equalsIgnoreCase("nenhum")) {
						KitManager.kits.remove(gamer.getUniqueId());
						sender.sendMessage("§aVocê removeu o kit de todos os jogadores.");
						return false;
					}
					for (Kits kit : Kits.values()) {
						if (args[1].toLowerCase().equalsIgnoreCase(kit.toString())) {
							KitManager.kits.put(gamer.getUniqueId(), kit);
							sender.sendMessage("§aVocê colocou o kit " + args[1] + " para todos os jogadores.");
							return false;
						}
					}
				}
				return false;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage("§c" + Bukkit.getOfflinePlayer(args[0]).getName() + " nao está online!");
				return false;
			}
			if (args[1].toLowerCase().equalsIgnoreCase("nenhum")) {
				KitManager.kits.remove(target.getUniqueId());
				sender.sendMessage("§aVocê removeu o kit de " + target.getName() + ".");
				return false;
			}
			for (Kits kit : Kits.values()) {
				if (args[1].toLowerCase().equalsIgnoreCase(kit.toString())) {
					KitManager.kits.put(target.getUniqueId(), kit);
					sender.sendMessage("§aVocê colocou o kit " + args[1] + " para " + target.getName() + ".");
					return false;
				}
			}
		}
		return false;
	}

	@EventHandler
	void CommandKit(PlayerCommandPreprocessEvent e) {
		/*
		Player player = e.getPlayer();
		String[] split = e.getMessage().split(" ");
		split[0] = split[0].substring(1);
		String label = split[0];
		String[] args = new String[split.length - 1];
		for (int i = 1; i < split.length; i++) {
			args[(i - 1)] = split[i];
		}
		*/
		Player p = e.getPlayer();
		for (Kits kit : Kits.values()) {
			if (e.getMessage().equals("/" + kit.toString().toLowerCase())) {
				e.setCancelled(true);
				KitManager.selectKit(p, kit);
			}
		}
	}
}