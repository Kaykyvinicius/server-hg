package HG.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import HG.manager.GameManager;
import HG.utils.Status;

public class Start implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("start")) {
			if (!sender.hasPermission("hg.comando.start")) {
				Character c = '"';
				sender.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			if (GameManager.status != Status.PréJogo) {
				sender.sendMessage("§cO torneio já iniciou!");
				return false;
			}
			GameManager.cancelTask();
			GameManager.StartGame();
		}
		return false;
	}
}