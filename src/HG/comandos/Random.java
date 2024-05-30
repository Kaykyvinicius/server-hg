package HG.comandos;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.manager.Kits;
import HG.utils.Status;

public class Random implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("random")) {
			if (GameManager.status != Status.PréJogo) {
				return false;
			}
			ArrayList<Kits> kitrandom = new ArrayList<>();
			for (Kits kit : Kits.values()) {
				if (p.hasPermission("kit." + kit.toString().toLowerCase())) {
					if (!kitrandom.contains(kit)) {
						kitrandom.add(kit);
					}
				}
			}
			KitManager.selectKit(p, kitrandom.get(new java.util.Random().nextInt(kitrandom.size())));
			kitrandom.clear();
		}
		return false;
	}
}