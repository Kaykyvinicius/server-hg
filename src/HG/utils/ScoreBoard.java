package HG.utils;

import org.bukkit.entity.Player;

import HG.Main;
import HG.comandos.Score;
import HG.manager.GameManager;
import HG.manager.KitManager;
import HG.manager.ScoreManager;

public class ScoreBoard {
	public static void Score(Player p) {
		if (Score.score.contains(p)) {
			return;
		}
		ScoreManager scoreboard = new ScoreManager("§6§lHG");
		scoreboard.add("§2", 5);
		scoreboard.add("§1", 3);
		scoreboard.add("§aKit: §f" + KitManager.playerKit(p), 2);
		scoreboard.add("§0", 1);
		scoreboard.add("§aIP: §f" + Main.IP, 0);
		if (GameManager.status == Status.PréJogo) {
			scoreboard.add("§aInicia em: §f" + time(GameManager.pre), 4);
		} else if (GameManager.status == Status.Invencibilidade) {
			scoreboard.add("§cInvencibilidade acaba em: §f" + time(GameManager.inven), 4);
		} else if (GameManager.status == Status.Jogo) {
			scoreboard.add("§aTempo: §f" + time(GameManager.game), 4);
		}
		scoreboard.build();
		scoreboard.send(p);
	}

	public static String time(int i) {
		int min = i / 60;
		int segs = i % 60;
		return min + ":" + (segs < 10 ? "0" : "") + segs;
	}
}