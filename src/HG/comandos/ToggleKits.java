package HG.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import HG.manager.KitManager;

public class ToggleKits implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("togglekits")) {
			if (!sender.hasPermission("hg.comando.togglekits")) {
				Character c = '"';
				sender.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			if (args.length == 0) {
				sender.sendMessage("§cUse: /togglekits <true | false>");
				return false;
			}
			if (args[0].equalsIgnoreCase("false")) {
				KitManager.enabled = false;
				Bukkit.broadcastMessage("§cTodos os kits foram desativados");
			} else if (args[0].equalsIgnoreCase("true")) {
				KitManager.enabled = true;
				Bukkit.broadcastMessage("§aTodos os kits foram ativados");
			}
		}
		return false;
	}
}