package HG.kitselector;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import HG.manager.KitManager;
import HG.manager.Kits;
import HG.manager.PlayerManager;

public class KitSelector implements Listener {
	@EventHandler
	void Click(InventoryClickEvent e) {
		try {
			Player p = (Player) e.getWhoClicked();
			if ((PlayerManager.inAdmin(p)) && (!p.hasPermission("hg.build")) && (e.getInventory().getName().equalsIgnoreCase("Inventory")) && (e.getCurrentItem().getType() != Material.MUSHROOM_SOUP)) {
				e.setCancelled(true);
			}
			if (PlayerManager.inSpec(p)) {
				e.setCancelled(true);
			}
			if (e.getInventory().getName().equalsIgnoreCase("Hardcore games kit selection")) {
				if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType() == null)) {
					return;
				}
				e.setCancelled(true);
				if ((e.getCurrentItem().getType() == Material.THIN_GLASS)
						|| (e.getCurrentItem().getType() == Material.CARPET)
						|| (e.getCurrentItem().getType() == Material.SULPHUR)) {
					return;
				}
				if (KitManager.enabled == true) {
					if (e.getCurrentItem().getType() != Material.AIR) {
						p.chat("/kit " + ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
						p.closeInventory();
					}
				}
			}
		} catch (NullPointerException n) {
		}
	}

	public static void openKitSelector(Player p) {
		Inventory inv = Bukkit.createInventory(p, 54, "Hardcore games kit selection");
		for (int i = 0; i < 9; i++) {
			inv.setItem(4, new ItemStack(Material.SULPHUR));
			inv.setItem(8, new ItemStack(Material.CARPET));
			if (inv.getItem(i) == null) {
				inv.setItem(i, new ItemStack(Material.THIN_GLASS));
			}
		}
		if (KitManager.enabled == true) {
			for (Kits kit : Kits.values()) {
				if (p.hasPermission("kit." + kit)) {
					ItemStack itm = item(kit.getMaterial(), "§f" + kit.getName(), kit.quantidade(), (byte) kit.data(),
							null);
					inv.addItem(itm);
				}
			}
		}
		for (int i = 0; i < inv.getSize(); i++) {
			if (inv.getItem(i) == null) {
				inv.setItem(i, new ItemStack(Material.THIN_GLASS));
			}
		}
		p.openInventory(inv);
	}

	public static ItemStack item(Material m, String displayName, int amount, short data, String[] lore) {
		ItemStack item = new ItemStack(m, amount, data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayName);
		ArrayList<String> l = new ArrayList<>();
		if (lore != null) {
			for (int i = 0; i < lore.length; i++) {
				l.add(lore[i]);
			}
		}
		meta.setLore(l);
		item.setItemMeta(meta);
		return item;
	}
}