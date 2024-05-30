package HG.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import HG.listeners.PlayerDeath;
import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.manager.PlayerManager;
import HG.utils.Status;

public class Help implements CommandExecutor {
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		if (label.equalsIgnoreCase("help")) {
			Player p = (Player) sender;
			if (args.length > 1) {
				p.sendMessage("§cUse: /help");
				return false;
			}
			if (args.length == 0) {
				if (GameManager.status == Status.PréJogo) {
					p.sendMessage("§7Tournament will starts in " + GameManager.time(GameManager.pre));
				} else if (GameManager.status == Status.Invencibilidade) {
					p.sendMessage("§7Invencibility wears off in " + GameManager.time(GameManager.inven));
					p.sendMessage("§7" + PlayerManager.getGame().length + " players remain.");
				} else if (GameManager.status == Status.Jogo || GameManager.status == Status.Vitória) {
					p.sendMessage("§7Tournament started " + GameManager.time(GameManager.game) + " ago.");
					p.sendMessage("§7" + PlayerManager.getGame().length + " players remain.");
					p.sendMessage("§7" + KitManager.playerKit(p) + ", " + PlayerDeath.kills.get(p.getUniqueId()) + " kills!");
				}
			}
			if (args.length == 1) {
				if (!p.hasPermission("hg.comando.admin")) {
					p.sendMessage("§cUse: /help");
					return false;
				}
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					p.sendMessage("§c" + Bukkit.getOfflinePlayer(args[0]).getName() + " nao está online!");
					return false;
				}
				p.sendMessage("§cJogador: " + target.getDisplayName() + "§6(" + KitManager.playerKit(target) + ")");
			}
		}
		return false;
	}
}