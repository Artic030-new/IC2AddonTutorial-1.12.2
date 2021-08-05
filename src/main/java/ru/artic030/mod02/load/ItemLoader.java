package ru.artic030.mod02.load;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import ru.artic030.mod02.items.Cesium137;
import ru.artic030.mod02.items.CustomReactorPlating;
import ru.artic030.mod02.items.ElectricPotion;
import ru.artic030.mod02.items.EnderCannon;
import ru.artic030.mod02.items.FreezerCannon;
import ru.artic030.mod02.items.IngeneerBattery;
import ru.artic030.mod02.items.ItemIronTreetap;
import ru.artic030.mod02.items.ItemSteelWrench;

public class ItemLoader {

		public static final List <Item> ITEMS = new ArrayList<Item>();
		public static final Item FREEZE_CANNON = new FreezerCannon("freezer_cannon");
		public static final Item ENDER_CANNON = new EnderCannon("ender_cannon");
		public static final Item IRON_TREETAP = new ItemIronTreetap("iron_treetap");
		public static final Item STEEL_WRENCH = new ItemSteelWrench("steel_wrench");	
		public static final Item CESIUM137_CELL = (Item) new Cesium137("cesium_137");
		public static final Item CUSTOM_REACTOR_PLATING = (Item) new CustomReactorPlating("custom_contaitment");
		public static final Item INGENEER_BATTERY = (Item) new IngeneerBattery("ingeneer_battery");
		public static final Item ELECTRIC_POTION = (Item) new ElectricPotion("electric_potion");


}
