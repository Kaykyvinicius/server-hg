package HG.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import HG.manager.PlayerManager;

public class TP implements CommandExecutor {
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player p = (Player) sender;
		if ((label.equalsIgnoreCase("teleport")) || (label.equalsIgnoreCase("ir"))) {
			if (!p.hasPermission("hg.comando.ir")) {
				Character c = '"';
				p.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			if (PlayerManager.inGame(p)) {
				p.sendMessage("§cVocê tem que ser um spectador para usar esse comando");
				return false;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				p.sendMessage("§c" + Bukkit.getOfflinePlayer(args[0]).getName() + " nao está online!");
				return false;
			}
			p.teleport(target);
		}
		if (label.equalsIgnoreCase("tpall")) {
			if (!p.hasPermission("hg.comando.tpall")) {
				Character c = '"';
				p.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			for (Player gamer : PlayerManager.getGame()) {
				if (gamer != p) {
					gamer.teleport(p);
				}
			}
		}
		if (label.equalsIgnoreCase("tphere")) {
			if (!p.hasPermission("hg.comando.tphere")) {
				Character c = '"';
				p.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				p.sendMessage("§c" + Bukkit.getOfflinePlayer(args[0]).getName() + " nao está online!");
				return false;
			}
			target.teleport(p);
		}
		return false;
	}
}