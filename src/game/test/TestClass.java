/*package game.test;

import game.control.EquipControl;
import game.control.EquipControl.AddAttrs;
import game.entity.Citiao;
import game.entity.Equip;

import java.util.Random;

public class TestClass {
	public static void main(String[] args) {
		TestClass s = new TestClass() ;
		//s.equipTest();
		//s.rdEquip();
		//SUtils.loadWeapon();
		
		*//** 加载资料 *//*
		Map<String, List<Equip>> map = SUtils.loadArmor();
		List<Equip> list1 = map.get("necklace");
		for (int i = 0; i < list1.size(); i++) {
			//System.out.println(list1.get(i).toString());
		}
		
		List<Equip> list = e.getSuitList(-1, 9);
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}
		*//** 自动生成装备 *//*
		for (int i = 0; i < 100; i++) {
			e.equipGenerate(new Random().nextInt(36)+3);
		}
		//SUtils.loadEquipSetting(1);
		
		//System.out.println(1.21*10/10);
		
		for (int i = 0; i < 10; i++) {
		Equip equip = e.equipGenerate(new Random().nextInt(36)+3);
		AddAttrs addAttrs = e.countAttr( equip.attrList);
		System.out.println(addAttrs.toString());
		}		
		
		AddAttrs addAttrs = e.newADdAttrs() ;
		Equip e1 = null ;
		for (int i = 0; i < 8; i++) {
			e1 = e.equipGenerate(20, i);
			if(e1!=null){
				e.countAttr(addAttrs, e1.getAttrList());
			}
		}
		System.out.println(addAttrs.toString());
		
		
		
		
		
		
		
		
		
		
		
		
		//s.playerAttr();
		Player p = new Player();
		p.rank=1;
		p.li=20;
		p.min=20;
		p.tili=25;
		p.jingli=5;
		p.lucky=10;
		for (int i = 0; i < 30; i++) {
			p.hp=p.tili*12;
			p.mp=p.jingli*3;
			p.attack=p.li*4-35;
			p.defense=p.min;
			p.baoji=(int) (p.lucky*0.4) ;
			p.hit = p.attack/(p.attack+p.defense);
			System.out.println(new Double(p.attack)/(p.attack+p.defense));
			System.out.println(p.toString());
			p.li+=5;
			p.min+=5;
			p.tili+=5;
			p.jingli+=5;
			p.lucky+=1;
			p.rank+=1;
		}
		
	int[] ary = new int[20];
	ary[0]=100;
	ary[1]=200;
	for (int i = 2; i < ary.length; i++) {
		ary[i] = ary[i-1] + ary[i-2] ;
	}
	for (int i = 0; i < ary.length; i++) {
		System.out.println(ary[i]);
	}
	System.out.println("--------------------------------------------------------");
	int[] exp = new int[40];
	int[] needExp = new int[40]; 
	for (int i = 1; i < 40; i++) {
		exp[i-1] = (int) Math.floor(((i-1)*(i-1)*20)/5*(i+50));
	}	
	for (int i = 2; i < exp.length; i++) {
		int nn = exp[i-1]-exp[i-2] ;
		needExp[i]=nn;
		System.out.println("等级:"+i+" , 总经验:"+exp[i-1]+" , 需要:"+(exp[i-1]-exp[i-2]));
	}
	int num = 8 ;
	for (int i = 0; i < exp.length; i++) {
		int x = needExp[i]/num ;
		num++;
		System.out.println( "升级需要:"+num+"只普通怪,提供:"+x );
	}
	
	}
	
	void  equipTest(){
		EquipControl e = new EquipControl();
		int rank = 5 ;//装备等级
		int type = 0 ;//装备品质
		int part = 0 ;//装备部位
		int num = new Random().nextInt(4);
		for (int i = 0; i < 10 ; i++) {
			System.out.println("装备)"+i);
			for (int j = 0; j < num; j++) {
				type = new Random().nextInt(4) ;
				System.out.println(rank+"级"+Equip.partDes[part]+Equip.typeAry[type]+":"+e.rdAddAttr(rank,part , j, type).attrValue);
			}
			
		}
		
		 for (int i = 0; i < 5; i++) {
			 for (int j = 0; j < 10; j++) {
				 part = new Random().nextInt(8);
				*//** 1级人物攻击45 一级 武器  *//*
				System.out.println(rank+"级"+Equip.partDes[part]+"基础攻击力为:"+Player.getBaseAck(rank)+","+rank+"级"+Equip.typeAry[type]+"攻击力为:"+e.rdAddAttr(rank, 0, 0, type).attrValue);

			}
			type++; 
		}
	}
//rank=40, li=215, min=215, tili=220, jingli=200, lucky=49, hp=2200, mp=500, attack=825, defense=322	
	public void rdEquip(){
		EquipControl e = new EquipControl() ;
		Random rd = new Random(System.currentTimeMillis()) ;
		int rank = 40 ;//装备等级
		int type = 0 ;//装备品质
		int part = 3 ;//装备部位
		Equip[] ary = new Equip[30]; 
		for (int i = 0; i < 30; i++) {
			ary[i] = new Equip();
			ary[i].name = "装备"+i ;
			ary[i].rank = rank ;
			ary[i].part = part;
			ary[i].type = rd.nextInt(5) ;
			ary[i].attrList = new Citiao[ary[i].type+1];
			//System.out.println("词条数量:"+ary[i].attrList.length+" , type:"+type+" , "+ary[i].type);
			for (int j = 0; j < ary[i].attrList.length; j++) {
				ary[i].attrList[j] = e.rdAddAttr(rank,part,j,ary[i].type);
				
			}
		}
		for (int i = 0; i < ary.length; i++) {
			System.out.println(ary[i].toString());
		}
	
	}
	
	public void playerAttr(){
		Player p = Player.getPlayer();
		p.rank=1;
		p.li=20;
		p.min=20;
		p.tili=25;
		p.jingli=5;
		p.lucky=10;
		for (int i = 0; i < 40; i++) {
			p.hp=p.tili*10;
			p.mp=p.jingli*5/2;
			p.attack=p.li*4-35;
			p.defense=p.min*3/2;
			p.baoji=(int) (p.lucky/4) ;
			p.hit = p.attack/(p.attack+p.defense);
			//System.out.println(new Double(p.attack)/(p.attack+p.defense));
			System.out.println(p.toString());
			p.li+=5;
			p.min+=5;
			p.tili+=5;
			p.jingli+=5;
			p.lucky+=1;
			p.rank+=1;
		}
	}
	
	
	
	public void rdPlayer(EquipControl e){
		int rank = 20 ;
		AddAttrs addAttrs = e.newADdAttrs() ;
		Equip e1 = null ;
		for (int i = 0; i < 8; i++) {
			e1 = e.equipGenerate(rank, i);
			e.countAttr(addAttrs, e1.attrList);
		}
		System.out.println(addAttrs.toString());
		
	}
	
}
*/