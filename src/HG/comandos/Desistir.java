package HG.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import HG.Main;
import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.manager.PlayerManager;
import HG.utils.Itens;
import HG.utils.Status;
import HG.utils.Win;

public class Desistir implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("desistir")) {
			if (args.length > 0) {
				p.sendMessage("§cUse: /desistir");
				return false;
			}
			if (GameManager.status == Status.PréJogo) {
				p.sendMessage("§cO torneio ainda não iniciou!");
				return false;
			}
			if (!PlayerManager.inGame(p)) {
				p.sendMessage("§Você não está no torneio!");
				return false;
			}
			Itens.dropItems(p, p.getLocation());
			String s = "";
			String s2 = "";
			if (PlayerManager.getGame().length > 2) {
				s = "s";
				s2 = "es";
			}
			String players = PlayerManager.getGame().length - 1 + " jogador" + s2 + " restante" + s + ".";
			Bukkit.broadcastMessage("§c" + p.getName() + "§6(§e" + KitManager.playerKit(p) + "§6) §7desistiu do torneio!\n§c" + players);
			if (p.hasPermission("hg.comando.admin")) {
				PlayerManager.addAdmin(p);
			} else if (p.hasPermission("hg.spec")) {
				PlayerManager.addSpec(p);
			} else {
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("Connect");
				out.writeUTF("lobby");
				p.sendPluginMessage(Main.instance, "BungeeCord", out.toByteArray());
			}
			Win.checarGanhador();
		}
		return false;
	}
}