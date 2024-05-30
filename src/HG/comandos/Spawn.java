package HG.comandos;

import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import HG.manager.GameManager;
import HG.utils.Status;

public class Spawn implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("spawn")) {
			if ((GameManager.status != Status.PréJogo) || (GameManager.pre < 21)) {
				p.sendMessage("§cO torneio já começou!");
				return false;
			}
			int x = new Random().nextInt(499);
			int z = new Random().nextInt(499);
			if (new Random().nextBoolean()) {
				x = -x;
			}
			if (new Random().nextBoolean()) {
				z = -z;
			}
			p.teleport(p.getWorld().getHighestBlockAt(x, z).getLocation().add(0, 10, 0));
		}
		return false;
	}
}