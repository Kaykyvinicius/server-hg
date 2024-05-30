package HG.comandos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import HG.manager.PlayerManager;

public class Skit implements CommandExecutor {
	static HashMap<String, ItemStack[]> inv = new HashMap<>();
	static HashMap<String, ItemStack[]> armor = new HashMap<>();

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("skit")) {
			if (!p.hasPermission("hg.comando.skit")) {
				Character c = '"';
				sender.sendMessage("Unknown command. Type " + c + "/help" + c + " for help.");
				return false;
			}
			if (args.length == 0) {
				p.sendMessage("§cUse: /skit <criar | aplicar | remover>");
				return false;
			}
			if (args[0].equalsIgnoreCase("criar")) {
				if (args.length != 2) {
					p.sendMessage("§cUse: /skit criar <kit>");
					return false;
				}
				if (inv.containsKey(args[1])) {
					p.sendMessage("§cKit '" + args[1] + "' já existe!");
					return false;
				}
				inv.put(args[1], p.getInventory().getContents());
				armor.put(args[1], p.getInventory().getArmorContents());
				p.sendMessage("§aKit '" + args[1] + "' criado!");
			} else if (args[0].equalsIgnoreCase("aplicar")) {
				if ((args.length < 3) || (args.length > 4)) {
					p.sendMessage("§cUse: /skit aplciar (<jogador> | todos | area) <kit>");
					return false;
				}
				if (args[1].equalsIgnoreCase("todos")) {
					if (args.length < 3) {
						p.sendMessage("§cUse: /skit aplciar todos <kit>");
						return false;
					}
					if (!inv.containsKey(args[2])) {
						p.sendMessage("§cKit '" + args[2] + "' não existe!");
						return false;
					}
					for (Player gamer : PlayerManager.getGame()) {
						gamer.getInventory().setContents(inv.get(args[2]));
						gamer.getInventory().setArmorContents(armor.get(args[2]));
					}
					p.sendMessage("§aKit '" + args[2] + "' foi aplicado para todos os jogadores.");
					return false;
				}
				if (args[1].equalsIgnoreCase("area")) {
					if (args.length != 4) {
						p.sendMessage("§cUse: /skit aplicar area <alcance> <kit>");
						return false;
					}
					if (args[2].length() > 9) {
						p.sendMessage("§cNúmero muito grande");
						return false;
					}
					int area = 0;
					try {
						area = Integer.parseInt(args[2]);
					} catch (NumberFormatException e) {
						p.sendMessage("§cNúmero incorreto");
						return false;
					}
					if (!inv.containsKey(args[3])) {
						p.sendMessage("§cKit '" + args[3] + "' não existe!");
						return false;
					}
					String s = "";
					if (area > 1) {
						s = "s";
					}
					List<Player> entity = new ArrayList<>();
					for (Entity e : p.getNearbyEntities(area, area, area)) {
						if (e instanceof Player) {
							Player target = (Player) e;
							entity.clear();
							entity.add(target);
							target.getInventory().setContents(inv.get(args[3]));
							target.getInventory().setArmorContents(armor.get(args[3]));
							p.sendMessage("§aKit '" + args[3] + "' foi aplicado para todos os jogadores em um raio de "
									+ area + " bloco" + s + ".");
						}
					}
					if (entity.size() == 0) {
						p.sendMessage("§cNão existe nenhum jogador em um raio de " + area + " bloco" + s + ".");
						return false;
					}
					return false;
				}
				if (args.length != 3) {
					p.sendMessage("§cUse: /skit aplicar <jogador>");
					return false;
				}
				Player target = Bukkit.getPlayer(args[1]);
				if (target == null) {
					p.sendMessage("§c" + Bukkit.getOfflinePlayer(args[1]).getName() + " nao está online!");
					return false;
				}
				if (!inv.containsKey(args[2])) {
					p.sendMessage("§cKit '" + args[2] + "' não existe!");
					return false;
				}
				target.getInventory().setContents(inv.get(args[2]));
				target.getInventory().setArmorContents(armor.get(args[2]));
				p.sendMessage("§aKit '" + args[2] + "' foi aplicado para " + target.getName() + ".");
			} else if (args[0].equalsIgnoreCase("remover")) {
				if (args.length != 2) {
					p.sendMessage("§cUse: /skit criar <kit>");
					return false;
				}
				if (!inv.containsKey(args[1])) {
					p.sendMessage("§cKit '" + args[1] + "' não existe!");
					return false;
				}
				inv.remove(args[1]);
				armor.remove(args[1]);
				p.sendMessage("§cKit '" + args[1] + "' removido!");
			} else {
				p.sendMessage("§cUse: /skit <criar | aplicar | remover>");
				return false;
			}
		}
		return false;
	}
}