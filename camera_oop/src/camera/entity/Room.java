package camera.entity;

import java.util.List;

public class Room {
	// các đỉnh của một hình hộp chữ nhật tương ứng với các góc phòng
	
	//mặt dưới
	public Coordinates A; //đây là các tọa độ ABCD, mỗi điểm có 3 tọa độ A(x,y,z)
	public Coordinates B;
	public Coordinates C;
	public Coordinates D;
	
	//mặt trên
	public Coordinates E;
	public Coordinates F;
	public Coordinates G;
	public Coordinates H;
	
	
	public double thetich;
	
	public List<Coordinates> listPointOfRoom;
	public Room(Coordinates a, Coordinates b, Coordinates c, Coordinates d, Coordinates e, Coordinates f, Coordinates g,
			Coordinates h) {
		super();
		A = a;
		B = b;
		C = c;
		D = d;
		E = e;
		F = f;
		G = g;
		H = h;
	}
	
	
	
	@Override
	public String toString() {
		return "Room [A=" + A + ", B=" + B + ", C=" + C + ", D=" + D + ", E=" + E + ", F=" + F + ", G=" + G + ", H=" + H
				+ "]";
	}
	
	
	
	

}
