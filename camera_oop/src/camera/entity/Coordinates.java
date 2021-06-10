package camera.entity;

public class Coordinates {
	// Một điểm trong Oxyz
	public double x;
	public double y;
	public double z;
	public Coordinates(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	
	@Override
	public String toString() {
		return "Coordinates [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	

}
