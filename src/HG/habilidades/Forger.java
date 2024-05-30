package HG.habilidades;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import HG.manager.KitManager;

public class Forger implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		ItemStack currentItem = event.getCurrentItem();
		Player p = (Player) event.getWhoClicked();
		if ((currentItem != null) && (currentItem.getType() != Material.AIR)
				&& (KitManager.getKit(p).equals("Forger"))) {
			int coalAmount = 0;
			Inventory inv = event.getView().getBottomInventory();
			for (ItemStack item : inv.getContents()) {
				if ((item != null) && (item.getType() == Material.COAL)) {
					coalAmount += item.getAmount();
				}
			}
			if (coalAmount == 0) {
				return;
			}
			int hadCoal = coalAmount;
			if (currentItem.getType() == Material.COAL) {
				for (int slot = 0; slot < inv.getSize(); slot++) {
					ItemStack item = inv.getItem(slot);
					if ((item != null)
							&& (item.getType().name().contains("ORE"))) {
						while ((item.getAmount() > 0)
								&& (coalAmount > 0)
								&& ((item.getType() == Material.IRON_ORE) || (item
										.getType() == Material.GOLD_ORE))) {
							item.setAmount(item.getAmount() - 1);
							coalAmount--;
							if (item.getType() == Material.IRON_ORE) {
								p.getInventory().addItem(
										new ItemStack[] { new ItemStack(
												Material.IRON_INGOT) });
							} else if (item.getType() == Material.GOLD_ORE) {
								p.getInventory().addItem(
										new ItemStack[] { new ItemStack(
												Material.GOLD_INGOT) });
							}
						}
						if (item.getAmount() == 0) {
							inv.setItem(slot, new ItemStack(0));
						}
					}
				}
				p.updateInventory();
			} else if (currentItem.getType().name().contains("ORE")) {
				while ((currentItem.getAmount() > 0)
						&& (coalAmount > 0)
						&& ((currentItem.getType() == Material.IRON_ORE) || (currentItem
								.getType() == Material.GOLD_ORE))) {
					currentItem.setAmount(currentItem.getAmount() - 1);
					coalAmount--;
					if (currentItem.getType() == Material.IRON_ORE) {
						p.getInventory().addItem(
								new ItemStack[] { new ItemStack(
										Material.IRON_INGOT) });
					} else if (currentItem.getType() == Material.GOLD_ORE) {
						p.getInventory().addItem(
								new ItemStack[] { new ItemStack(
										Material.GOLD_INGOT) });
					}
				}
				if (currentItem.getAmount() == 0) {
					event.setCurrentItem(new ItemStack(0));
				}
				p.updateInventory();
			}
			if (coalAmount != hadCoal) {
				for (int slot = 0; slot < inv.getSize(); slot++) {
					ItemStack item = inv.getItem(slot);
					if ((item != null) && (item.getType() == Material.COAL)) {
						while ((coalAmount < hadCoal) && (item.getAmount() > 0)) {
							item.setAmount(item.getAmount() - 1);
							coalAmount++;
						}
						if (item.getAmount() == 0) {
							inv.setItem(slot, new ItemStack(0));
						}
					}
				}
				p.updateInventory();
			}
		}
	}
}