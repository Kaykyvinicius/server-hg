package HG.comandos;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import HG.manager.PlayerManager;

public class Who implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("who")) {
			if (!sender.hasPermission("hg.comando.who")) {
				Character c = '"';
				sender.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			List<String> wholist = new ArrayList<>();
			for (Player players : PlayerManager.getGame()) {
				int health = (int) (((CraftPlayer) players).getHealth() / 0.2D);
				wholist.add(players.getName() + "(" + health + "%)");
			}
			sender.sendMessage("Jogadores Restantes (" + PlayerManager.getGame().length + "): "
					+ StringUtils.join(wholist, ", ") + ".");
		}
		return false;
	}
}