package game.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.entity.NPC;
import game.utils.SUtils;

public class TalkLner implements ActionListener{
	private NPC npc ;
	
	public TalkLner(NPC npc) {
		this.npc = npc ;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.err.println(npc+"的msg");
		SUtils.npcMsg(npc, npc.getMsgs());
	}
	
}
