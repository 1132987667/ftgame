package game.chapter;

public class Chapter1 {
	public int msgNum = 6 ;
	public String str = null ;
	public String msg1 = "\"嘶,这是哪里,我怎么会在这?\"";
	public String msg2 = "\"特喵的不会吧,我这TM就穿越啦!\"" ;
	public String msg3 = "昏睡的前几十分钟,我还在自己租来的小屋子,对着电脑无聊的看着直播。\n";
	public String msg4 = "突然,弹出一个游戏广告,《异界生存系统》,在等待它的主人,你只有10\n"
						+"秒钟选择是否拥有它,像烂大街的广告一样一闪一闪!" ;
	public String msg5 = "是的,极其中二的广告词,然而系统两个字吸引了我,我刚摁下了确定,突然\n"
						+"间,电脑主机嗡嗡作响,屏幕光华大作,我也是眼前一黑,失去知觉。";
	public String msg6 = "正在回忆之时，脑海中突然想起一个冷漠的机械声:\"宿主已到达异世界,\n"
						+"是否接受开启异世界生存任务？\"" ;
	
	public String msg7 = "你拒绝了系统的任务,嗡,你的脑子一顿,瞬间,你便发现系统离你而去,在\n"
						+"这个异世界你举目无亲,果然,几天后,在这个战斗力爆表的世界,你就被一\n"
						+"头不知名野兽给杀死了!" ;
	public String msg8 = "GAME OVER!";
	
	public String get(int curMsgNum) {
		switch (curMsgNum) {
		case 1:
			str = msg1 ;
			break;
		case 2:
			str = msg2 ;
			break;
		case 3:
			str = msg3 ;
			break;
		case 4:
			str = msg4 ;
			break;
		case 5:
			str = msg5 ;
			break;
		case 6:
			str = msg6 ;
			break;
		case 7:
			str = msg7;
			break;
		case 8:
			str = msg8 ;
			break;	
		default:
			break;
		}
		return str;
	}
}
