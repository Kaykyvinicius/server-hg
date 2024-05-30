package HG.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Dano implements CommandExecutor {
	public static boolean dano = true;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("dano")) {
			if (!sender.hasPermission("hg.comando.dano")) {
				Character c = '"';
				sender.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			if (args.length == 0) {
				sender.sendMessage("§cUse: /dano <on | off>");
				return false;
			}
			if (args[0].equalsIgnoreCase("on")) {
				if (dano == false) {
					sender.sendMessage("§aVocê ativou o dano global");
					Bukkit.broadcastMessage("§cDano global ativado");
					dano = true;
				} else {
					sender.sendMessage("§cO dano global já está ativado");
				}
			} else if (args[0].equalsIgnoreCase("off")) {
				if (dano == true) {
					sender.sendMessage("§aVocê desativou o dano global");
					Bukkit.broadcastMessage("§cDano global desativado");
					dano = false;
				} else {
					sender.sendMessage("§cO dano global já está desativado");
				}
			} else {
				sender.sendMessage("§cUse: /dano <on | off>");
			}
		}
		return false;
	}
}