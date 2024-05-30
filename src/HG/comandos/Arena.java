package HG.comandos;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Arena implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("arena")) {
			if (!p.hasPermission("hg.comando.arena")) {
				Character c = '"';
				sender.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			Location centro = p.getLocation().add(0, -1, 0);
			p.sendMessage("§cVocê criou uma arena em seu local!");
			for (int x = -15; x <= 15; x++) {
				for (int z = -15; z <= 15; z++) {
					Block chao = centro.clone().add(x, 0, z).getBlock();
					chao.setType(Material.BEDROCK);
					for (int y = 1; y < 5; y++) {
						Block resto = centro.clone().add(x, y, z).getBlock();
						resto.setType(Material.AIR);
						if (x == -15 || x == 15) {
							Block parede = centro.clone().add(x, y, z).getBlock();
							parede.setType(Material.BEDROCK);
						}
						if (z == -15 || z == 15) {
							Block parede = centro.clone().add(x, y, z).getBlock();
							parede.setType(Material.BEDROCK);
						}
						if (y == 4 || y == 2) {
							Block parede = centro.clone().add(x, y, z).getBlock();
							parede.setType(Material.AIR);
						}
					}
				}
			}
		}
		return false;
	}
}