package game.test;

import game.control.EquipControl;
import game.control.EquipControl.AddAttrs;
import game.control.GameControl;
import game.entity.Equip;
import game.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.text.PlainDocument;

public class RDtest {
	GameControl gameControl = GameControl.getInstance();
	EquipControl equipControl = gameControl.equipControl;
	public static void main(String[] args) {
		RDtest rd = new RDtest();
		GameControl gameControl = GameControl.getInstance();
		
		//rd.compare();
		//rd.citiaoAdd();
		
		for (int i = 2; i < 10; i++) {
			System.out.println(gameControl.getNpcExp(i, i, 0));
		}
		
		
		
		/*Player player = new Player();
		player.setBaolv(0);
		EquipControl equipControl = GameControl.getInstance().equipControl;
		for (int i = 0; i < 100; i++) {
			equipControl.dropPredict(1, 10, player);
			System.out.println("---------------------------------");
		}*/
		
	}
	
	
	
	/** 测试词条转为为属性加成方法是否成功 */
	public void citiaoAdd(){
		Equip equip = equipControl.equipGenerate(1, 0, 4);
		System.out.println(equip);
		AddAttrs addAttrs  = equipControl.countAttr(equip.getAttrList());
		System.out.println(addAttrs);
	}
	
	public void compare(){
		GameControl gameControl = GameControl.getInstance();
		EquipControl equipControl = gameControl.equipControl;
		List<Equip> list = new ArrayList<>();
		list.add(equipControl.equipGenerate(1, 0, 1));
		list.add(equipControl.equipGenerate(1, 0, 0));
		list.add(equipControl.equipGenerate(1, 0, 4));
		list.add(equipControl.equipGenerate(1, 0, 2));
		list.add(equipControl.equipGenerate(1, 3, 0));
		list.add(equipControl.equipGenerate(1, 3, 1));
		list.add(equipControl.equipGenerate(1, 3, 2));
		list.add(equipControl.equipGenerate(1, 1, 0));
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}
		Collections.sort(list);
		System.out.println("排序后!");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}
	}
	
	public void t(){
		GameControl gameControl = GameControl.getInstance();
		EquipControl equipControl = gameControl.equipControl;
		for (int i = 0; i < 8; i++) {
			Equip equip = equipControl.equipGenerate(1, i);
			if(equip!=null){
				System.out.println(equip);
			}
		}
		System.out.println(".................................");
		for (int i = 0; i < 8; i++) {
			Equip equip = equipControl.equipGenerate(1, i);
			if(equip!=null){
				System.out.println(equip);
			}
		}
	}
	
}
