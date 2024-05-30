package HG.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import HG.manager.GameManager;
import HG.manager.KitManager;

public class Itens {
	public static void dropItems(Player p, Location l) {
		if (GameManager.status != Status.PréJogo) {
			List<ItemStack> items = new ArrayList<ItemStack>();
			for (ItemStack item : p.getPlayer().getInventory().getContents()) {
				if ((item != null) && (item.getType() != Material.AIR))
					items.add(item.clone());
			}
			for (ItemStack item : p.getPlayer().getInventory().getArmorContents()) {
				if ((item != null) && (item.getType() != Material.AIR))
					items.add(item.clone());
			}
			if ((p.getPlayer().getItemOnCursor() != null)
					&& (p.getPlayer().getItemOnCursor().getType() != Material.AIR))
				items.add(p.getPlayer().getItemOnCursor().clone());
			for (ItemStack item : items) {
				if ((item == null) || (item.getType() == Material.AIR)) {
					continue;
				}
				if ((!KitManager.getKit(p).equals("Blink")) || (item.getType() != Material.NETHER_STAR)) {
					if ((!KitManager.getKit(p).equals("Demoman"))
							|| (item.getType() != Material.STONE_PLATE) && (item.getType() != Material.GRAVEL)) {
						if ((!KitManager.getKit(p).equals("Endermage")) || (item.getType() != Material.PORTAL)) {
							if ((!KitManager.getKit(p).equals("Fisherman"))
									|| (item.getType() != Material.FISHING_ROD)) {
								if ((!KitManager.getKit(p).equals("Gambler"))
										|| (item.getType() != Material.STONE_BUTTON)) {
									if ((!KitManager.getKit(p).equals("Gladiator"))
											|| (item.getType() != Material.IRON_FENCE)) {
										if ((!KitManager.getKit(p).equals("Grandpa"))
												|| (item.getType() != Material.STICK)) {
											if ((!KitManager.getKit(p).equals("Grappler"))
													|| (item.getType() != Material.LEASH)) {
												if ((!KitManager.getKit(p).equals("Jackhammer"))
														|| (item.getType() != Material.STONE_AXE)) {
													if ((!KitManager.getKit(p).equals("Kangaroo"))
															|| (item.getType() != Material.FIREWORK)) {
														if ((!KitManager.getKit(p).equals("Kaya"))
																|| (item.getType() != Material.GRASS)) {
															if ((!KitManager.getKit(p).equals("Miner"))
																	|| (item.getType() != Material.STONE_PICKAXE)
																			&& (item.getType() != Material.APPLE)) {
																if ((!KitManager.getKit(p).equals("Reaper"))
																		|| (item.getType() != Material.WOOD_HOE)) {
																	if ((!KitManager.getKit(p).equals("Redstoner"))
																			|| (item.getType() != Material.SLIME_BALL)
																					&& (item.getType() != Material.DISPENSER)
																					&& (item.getType() != Material.DIODE)
																					&& (item.getType() != Material.REDSTONE)
																					&& (item.getType() != Material.PISTON_BASE)) {
																		if ((!KitManager.getKit(p).equals("Specialist"))
																				|| (item.getType() != Material.BOOK)) {
																			if ((!KitManager.getKit(p)
																					.equals("Switcher"))
																					|| (item.getType() != Material.SNOW_BALL)) {
																				if ((!KitManager.getKit(p)
																						.equals("Titan"))
																						|| (item.getType() != Material.BEDROCK)) {
																					if ((!KitManager.getKit(p)
																							.equals("Urgal"))
																							|| (item.getType() != Material.POTION)) {
																						if ((item
																								.getType() == Material.POTION)
																								&& (item.getDurability() == 8201)) {
																							continue;
																						}
																						if (item.hasItemMeta())
																							p.getWorld().dropItemNaturally(l, item.clone()).getItemStack().setItemMeta(item.getItemMeta());
																						else
																							p.getWorld().dropItemNaturally(l, item);
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			p.getPlayer().getInventory().setArmorContents(new ItemStack[4]);
			p.getPlayer().getInventory().clear();
			p.getPlayer().setItemOnCursor(null);
			Iterator<PotionEffect> i = p.getActivePotionEffects().iterator();
			if (i.hasNext()) {
				PotionEffect pot = i.next();
				p.removePotionEffect(pot.getType());
			}
		}
	}
}