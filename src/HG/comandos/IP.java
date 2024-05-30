package HG.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import HG.Main;

public class IP implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("ip")) {
			sender.sendMessage("§7IP do servidor: " + Main.IP);
		}
		return false;
	}
}
