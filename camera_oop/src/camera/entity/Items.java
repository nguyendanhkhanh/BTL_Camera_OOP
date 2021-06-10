package camera.entity;

public class Items {
	// tọa độ các đỉnh của vật, với giả sử các vật đều là hình hộp chữ nhật

	// mặt dưới
	public Coordinates A;
	public Coordinates B;
	public Coordinates C;
	public Coordinates D;

	// mặt trên
	public Coordinates E;
	public Coordinates F;
	public Coordinates G;
	public Coordinates H;
	
	double dienTichMatDay;
	double chieuCao;
	/**
	 * Method dùng để
	 * 
	 * @return
	 */
	
	
	@Override
	public String toString() {
		return "Items [A=" + A + ", B=" + B + ", C=" + C + ", D=" + D + ", E=" + E + ", F=" + F + ", G=" + G + ", H="
				+ H + "]";
	}
	public Items(Coordinates a, Coordinates b, Coordinates c, Coordinates d, Coordinates e, Coordinates f,
			Coordinates g, Coordinates h) {
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
	
	

}
