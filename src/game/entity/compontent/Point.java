package game.entity.compontent;


public class Point {
	
	public int x;
	
	public int y;
	
	public Point(Point p) {
        this(p.x, p.y);
    }
	
	public Point(int x, int y) {
		this.x = x ;
		this.y = y ;
	}
	
	public void move(int x, int y) {
		this.x = x ;
		this.y = y ;
	}
	
	public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
	
	public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point pt = (Point)obj;
            return (x == pt.x) && (y == pt.y);
        }
        return super.equals(obj);
    }
}
