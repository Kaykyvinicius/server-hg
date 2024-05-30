package HG.comandos;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Feast implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("feast")) {
			if (p.getInventory().contains(Material.COMPASS)) {
				p.setCompassTarget(new Location(p.getWorld(), HG.utils.Feast.b.getX(),
						HG.utils.Feast.b.getY(), HG.utils.Feast.b.getZ()));
				p.sendMessage("�eB�ssola apontada para o Feast");
			} else {
				p.sendMessage("�cVoc� n�o tem uma b�ssola no seu invent�rio!");
			}
		}
		return false;
	}
}