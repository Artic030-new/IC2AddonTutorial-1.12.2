package ru.artic030.mod02;

import org.apache.logging.log4j.Logger;

import ic2.api.event.TeBlockFinalCallEvent;
import ic2.api.item.IC2Items;
import ic2.core.block.TeBlockRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.artic030.mod02.load.IC2ToolsLoader;
import ru.artic030.mod02.load.MachinesTE;
import ru.artic030.mod02.proxies.CommonProxy;
import ru.artic030.mod02.rezepte.Recipies;

@Mod(modid = Mod02.MODID, name = Mod02.NAME, version = Mod02.VERSION)

public class Mod02 {

@SidedProxy(clientSide = Mod02.CLIENT_PROXY, serverSide = Mod02.COMMON_PROXY)
	public static CommonProxy proxy;

	@Instance
	public static Mod02 instance;

	public static final String MODID = "mod02";
	public static final String NAME = "Mod 02";
	public static final String VERSION = "1";
	public static final String CLIENT_PROXY = "ru.artic030.mod02.proxies.ClientProxy";
	public static final String COMMON_PROXY = "ru.artic030.mod02.proxies.CommonProxy";

	public static final ItemStack platecarbon = IC2Items.getItem("crafting", "carbon_plate");
	public static final ItemStack iridium = IC2Items.getItem("misc_resource", "iridium_ore");

	private static String ACTIVATED = "activated";
	
	private Logger log;
	
	@EventHandler
	public void start(FMLConstructionEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void register(TeBlockFinalCallEvent event) {
		TeBlockRegistry.addAll(MachinesTE.class, MachinesTE.LOCATION);
	}
	
	@EventHandler
	public void preLoad(FMLPreInitializationEvent ev) {
		log = ev.getModLog();
		IC2ToolsLoader.buildItems(ev.getSide());
	}
	
	@EventHandler
	public void load(FMLInitializationEvent ev) {
		Recipies.addCraftingRecipes();
		Recipies.addMachineRecipe();	
		log.info("Mod is" + ACTIVATED);
	}
	
	@EventHandler
	public void postLoad(FMLPostInitializationEvent ev) {}
}
