package game.entity;

	/** 
	 * 用来储存词缀生成的设定 
	 */
	public class CitiaoSD {
		/**
		 * 什么位置的
		 * 第几条词条的位置
		 * 类型int
		 * 类型名 string
		 * 属性名 
		 * 比例
		 */
		public int part ;
		public int num;
		
		public int type = 0 ;
		public String typeName = "" ;
		//attack|suck|money|exp
		public String attrName = "" ;
		public double ratio;

		/**
		 * 
		 * @param part 部位
		 * @param num  这是xx装备的第几条词条
		 * @param type 词条的类型  0，1，2，3
		 * @param typeName 词条的名字  如:oneAttr
		 * @param attrName 属性具体信息 如: li|min|lucky
		 * @param ratio 比例因子大小
		 */
		public CitiaoSD(int part,int num, int type,String typeName, String attrName, double ratio) {
			super();
			this.part = part ;
			this.num = num;
			this.type = type;
			this.typeName = typeName ;
			this.attrName = attrName;
			this.ratio = ratio;
		}

		public CitiaoSD(int num, int type, String attrName) {
			super();
			this.num = num;
			this.type = type;
			this.attrName = attrName;
			this.ratio = -1;
		}

		@Override
		public String toString() {
			return "CitiaoSD [part=" + part + ", num=" + num + ", type=" + type
					+ ", typeName=" + typeName + ", attrName=" + attrName
					+ ", ratio=" + ratio + "]";
		}

		
	}
