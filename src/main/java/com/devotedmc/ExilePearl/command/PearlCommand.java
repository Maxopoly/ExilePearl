package com.devotedmc.ExilePearl.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.devotedmc.ExilePearl.ExilePearl;
import com.devotedmc.ExilePearl.ExilePearlApi;
import com.devotedmc.ExilePearl.ExileRule;

import vg.civcraft.mc.namelayer.GroupManager;
import vg.civcraft.mc.namelayer.NameAPI;

public abstract class PearlCommand extends BaseCommand<ExilePearlApi> {
	
	public PearlCommand(ExilePearlApi pearlApi) {
		super(pearlApi);
	}
	
	@Override
	protected List<String> getCustomAutoTab(String tabName, String pattern) {
		List<String> tabList = new ArrayList<String>();
		
		switch(tabName) {
		case "exile_rule":
			for(ExileRule v : new ArrayList<ExileRule>(Arrays.asList(ExileRule.values()))) {
				if (v.toString().toLowerCase().startsWith(pattern.toLowerCase())) {
					tabList.add(v.toString());
				}
			}
			break;
		case "pearled":
			for(ExilePearl pearl : plugin.getPearls()) {
				if (pearl.getPlayerName().toLowerCase().startsWith(pattern.toLowerCase())) {
					tabList.add(pearl.getPlayerName());
				}
			}
			break;
			
		case "group":
			GroupManager gm = NameAPI.getGroupManager();
			for(String group : gm.getAllGroupNames(player().getUniqueId())) {
				if(group.toLowerCase().startsWith(pattern.toLowerCase())) {
					tabList.add(group);
				}
			}
			break;
			
		case "group_or_player":
			List<String> groups = getAutoTab("group", pattern);
			List<String> players = getAutoTab("player", pattern);
			
			if (groups != null) {
				tabList.addAll(groups);
			}
			
			if (players != null) {
				tabList.addAll(players);
			}
			break;
			
		default:
			break;
		
		}
		return tabList;
	}
	
	protected final static CommandArg requiredPearlPlayer() {
		return required("player", autoTab("pearled", "No matching pearled player found."));
	}
	
	protected final static CommandArg requiredGroup() {
		return required("group", autoTab("group", "No matching group found."));
	}
}
