package HG.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import HG.manager.KitManager;
import HG.manager.PlayerManager;

public class Sopa {
	@SuppressWarnings("deprecation")
	public Sopa() {
		ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP, 1);
		ItemMeta im = soup.getItemMeta();
		im.setDisplayName("§rCactus Stew");
		soup.setItemMeta(im);
		ShapelessRecipe recipe = new ShapelessRecipe(soup);
		recipe.addIngredient(1, Material.BOWL);
		recipe.addIngredient(2, Material.CACTUS);
		Bukkit.getServer().addRecipe(recipe);
		ItemStack soup2 = new ItemStack(Material.MUSHROOM_SOUP, 1);
		ItemMeta im2 = soup2.getItemMeta();
		im2.setDisplayName("§rCocoabeans Stew");
		soup2.setItemMeta(im2);
		ShapelessRecipe recipe2 = new ShapelessRecipe(soup2);
		recipe2.addIngredient(1, Material.BOWL);
		recipe2.addIngredient(1, Material.INK_SACK, 3);
		Bukkit.getServer().addRecipe(recipe2);
		ItemStack soup3 = new ItemStack(Material.MUSHROOM_SOUP, 1);
		ItemMeta im3 = soup3.getItemMeta();
		im3.setDisplayName("§rCamel Stew");
		soup3.setItemMeta(im3);
		ShapelessRecipe recipe3 = new ShapelessRecipe(soup3);
		recipe3.addIngredient(1, Material.SAND);
		recipe3.addIngredient(1, Material.CACTUS);
		for (Player gamer : PlayerManager.getGame()) {
			if (KitManager.getKit(gamer).equals("Camel")) {
				Bukkit.getServer().addRecipe(recipe3);
			}
		}
	}
}