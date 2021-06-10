package camera.logic;

import java.awt.Checkbox;

public class GiaiPhuongTrinh {
	/**
	 * Method dùng để lấy nghiệm dương của PT bậc 2 có a.c đối dấu --> có 2 nghiệm đối dấu
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return nghiemduong : double
	 */
	
	public double giaiPhuongtrinhBacHai(double a, double b, double c) {
		double nghiemduong = 0;
		//tinh delta
		double delta = b*b - 4*a*c;
		 if (delta > 0) {
	            nghiemduong = (-b + Math.sqrt(delta)) / (2*a);
	          
		 }
		 return nghiemduong;	 
	}
	
	/**
	 * Method dùng để check pt bậc nhất có 1 nghiệm hay không
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	
	public Double giaiPhuongTrinhBacNhat( double a, double b) {
		Double nghiem = null;
		if (a != 0) {
			if (b != 0) {
				nghiem = (-1)*b/a;
			}
		}
		// pt có nghiệm khi kết quả trả về !=null
		return nghiem;
	}
	

}
