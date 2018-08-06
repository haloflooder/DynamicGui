package me.virustotal.dynamicgui.listener;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.event.block.PlayerInteractEvent;
import me.virustotal.dynamicgui.event.player.Action;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.world.LocationWrapper;

public class PlayerInteractListener implements Listener {
	
	@EventHandler
	public void interact(final PlayerInteractEvent e)
	{
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK)
		{
			for(GUI gui : GuiApi.getGuis())
			{
				if(gui.getLocations() != null)
				{
					
					for(LocationWrapper<?> guiLocation : gui.getLocations())
					{
						if(e.getLocationWrapper().equals(guiLocation))
						{
							e.getPlayerWrapper().chat("/gui " + gui.getName());
							break;
						}
					}
				}
			}
		}
	}
}