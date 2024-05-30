package HG.comandos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Score implements CommandExecutor {
	public static ArrayList<Player> score = new ArrayList<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("score")) {
			if (!score.contains(p)) {
				score.add(p);
				ScoreboardManager manager = Bukkit.getScoreboardManager();
				Scoreboard board = manager.getNewScoreboard();
				board.clearSlot(DisplaySlot.SIDEBAR);
				p.setScoreboard(board);
				p.sendMessage("§cVocê desativou sua ScoreBoard!");
			} else {
				score.remove(p);
				p.sendMessage("§aVocê ativou sua ScoreBoard!");
			}
		}
		return false;
	}
}