package HG.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

public class ClearDrops implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("cleardrops")) {
			if (!sender.hasPermission("hg.comando.cleardrops")) {
				Character c = '"';
				sender.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			if (args.length > 0) {
				sender.sendMessage("§cUse: /cleardrops");
				return false;
			}
			int item = 0;
			for (Entity e : Bukkit.getWorld("world").getEntities()) {
				if (e instanceof Item) {
					e.remove();
					item++;
				}
			}
			if (item == 0) {
				sender.sendMessage("§cNão há items para limpar");
				return false;
			}
			String s = "";
			if (item > 1) {
				s = "s";
			}
			sender.sendMessage("§aVocê limpou " + item + " item" + s);
		}
		return false;
	}
}