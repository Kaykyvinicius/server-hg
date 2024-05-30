package HG.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import HG.manager.GameManager;
import HG.utils.Status;

public class Tempo implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("tempo")) {
			if (!sender.hasPermission("hg.comando.tempo")) {
				Character c = '"';
				sender.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			if (args.length != 1) {
				sender.sendMessage("§cUse: /tempo <tempo>");
				return false;
			}
			if (args[0].length() > 9) {
				sender.sendMessage("§cNúmero muito grande");
				return false;
			}
			try {
				Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				sender.sendMessage("§cNúmero incorreto");
				return false;
			}
			if (GameManager.status == Status.PréJogo) {
				GameManager.pre = Integer.valueOf(Integer.parseInt(args[0]));
			} else if (GameManager.status == Status.Invencibilidade) {
				GameManager.inven = Integer.valueOf(Integer.parseInt(args[0]));
			} else if (GameManager.status == Status.Jogo) {
				GameManager.game = Integer.valueOf(Integer.parseInt(args[0]));
			}
			sender.sendMessage("§aTempo alterado para " + GameManager.time(Integer.valueOf(args[0])));
		}
		return false;
	}
}
