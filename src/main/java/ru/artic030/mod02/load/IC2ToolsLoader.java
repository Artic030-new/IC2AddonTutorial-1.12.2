package ru.artic030.mod02.load;

import ic2.core.ref.IItemModelProvider;
import ic2.core.ref.ItemName;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.artic030.mod02.items.Dehydrator;
import ru.artic030.mod02.items.FreezerCannon;
import ru.artic030.mod02.items.ItemEmeraldDrill;

public enum IC2ToolsLoader {

	EMERALD_DRILL, DEHYDRATOR;
	
	private Item instance;
	
	public Item getInstance() {
		return this.instance;
	}
	
	public void setInstance(Item instance) {
		this.instance = instance;
	}
	
	public static void buildItems(Side side) {
		EMERALD_DRILL.setInstance(new ItemEmeraldDrill());
		DEHYDRATOR.setInstance(new Dehydrator());
		if(side == Side.CLIENT) {
			doModelGuf();
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static void doModelGuf() { 
		IC2ToolsLoader[] tolz = values();
		int t  = tolz.length;
		for(int i = 0; i < t; i++ ) {
			IC2ToolsLoader item = tolz[i];
			((IItemModelProvider)item.getInstance()).registerModels((ItemName)null);
		}
	}
}
