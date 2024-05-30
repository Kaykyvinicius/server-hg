package HG.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Chat implements CommandExecutor {
	public static boolean chat = true;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("chat")) {
			if (!sender.hasPermission("hg.comando.chat")) {
				Character c = '"';
				sender.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			if (args.length == 0) {
				sender.sendMessage("�cUse: /chat <on | off>");
				return false;
			}
			if (args[0].equalsIgnoreCase("on")) {
				if (chat == false) {
					sender.sendMessage("�aVoc� ativou o chat");
					Bukkit.broadcastMessage("�cChat ativado");
					chat = true;
				} else {
					sender.sendMessage("�cO chat j� est� ativado");
				}
			} else if (args[0].equalsIgnoreCase("off")) {
				if (chat == true) {
					sender.sendMessage("�aVoc� desativou o chat");
					Bukkit.broadcastMessage("�cChat desativado");
					chat = false;
				} else {
					sender.sendMessage("�cO chat j� est� desativado");
				}
			} else {
				sender.sendMessage("�cUse: /chat <on | off>");
			}
		}
		return false;
	}
}