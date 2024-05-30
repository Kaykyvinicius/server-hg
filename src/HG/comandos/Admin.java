package HG.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import HG.manager.GameManager;
import HG.manager.PlayerManager;
import HG.utils.Itens;
import HG.utils.Status;

public class Admin implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("admin")) {
			if (!p.hasPermission("hg.comando.admin")) {
				Character c = '"';
				p.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			if (args.length > 0) {
				p.sendMessage("§cUse: /admin");
				return false;
			}
			if (PlayerManager.inAdmin(p)) {
				PlayerManager.removeAdmin(p);
			} else {
				if ((PlayerManager.inGame(p)) && (GameManager.status != Status.PréJogo)) {
					Itens.dropItems(p, p.getLocation());
				}
				PlayerManager.addAdmin(p);
			}
		}
		return false;
	}
}