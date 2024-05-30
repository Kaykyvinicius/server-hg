package HG.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

public class KillMobs implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("killmobs")) {
			if (!sender.hasPermission("hg.comando.killmobs")) {
				Character c = '"';
				sender.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			if (args.length > 0) {
				sender.sendMessage("§cUse: /killmobs");
				return false;
			}
			int mob = 0;
			for (Entity e : Bukkit.getWorld("world").getEntities()) {
				if (e instanceof Monster) {
					e.remove();
					mob++;
				}
			}
			if (mob == 0) {
				sender.sendMessage("§cNão há monstros para matar");
				return false;
			}
			String s = "";
			if (mob > 1) {
				s = "s";
			}
			sender.sendMessage("§aVocê matou " + mob + " monstro" + s);
		}
		return false;
	}
}