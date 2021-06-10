package camera.logic;

import camera.entity.Coordinates;

public class PhuongTrinhDuongThang {

	/**
	 * Method lấy vector chỉ phương của đường thẳng
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	
	public Coordinates layVectorChiPhuong(Coordinates A, Coordinates B) {
		// một đường thẳng viết theo kiểu tham số có dạng : x = x0 + at, y = y0 + bt, z
		// = z0+ct, với u(a,b,c) là vector chi phuong
		Coordinates u = new Coordinates(B.x - A.x, B.y - A.y, B.z - A.z);
		return u;
	}

}
