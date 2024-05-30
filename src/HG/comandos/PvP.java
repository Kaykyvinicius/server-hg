package HG.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PvP implements CommandExecutor {
	public static boolean pvp = true;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("pvp")) {
			if (!sender.hasPermission("hg.comando.pvp")) {
				Character c = '"';
				sender.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			if (args.length == 0) {
				sender.sendMessage("§cUse: /pvp <on | off>");
				return false;
			}
			if (args[0].equalsIgnoreCase("on")) {
				if (pvp == false) {
					sender.sendMessage("§aVocê ativou o pvp");
					Bukkit.broadcastMessage("§cPvP ativado");
					pvp = true;
				} else {
					sender.sendMessage("§cO pvp já está ativado");
				}
			}
			if (args[0].equalsIgnoreCase("off")) {
				if (pvp == true) {
					sender.sendMessage("§aVocê desativou o pvp");
					Bukkit.broadcastMessage("§cPvP desativado");
					pvp = false;
				} else {
					sender.sendMessage("§cO pvp já está desativado");
				}
			}
		}
		return false;
	}
}