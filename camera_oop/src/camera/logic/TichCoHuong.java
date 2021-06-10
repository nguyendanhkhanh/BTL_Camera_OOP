package camera.logic;

import camera.entity.Coordinates;

public class TichCoHuong {
	/**
	 * Method tính độ lớn của vector là tích có hướng của hai vector AB, AC
	 * 
	 * @param A
	 * @param B
	 * @param C
	 * @return
	 */
	
	public double layDoLonCuaTichCoHuong(Coordinates A, Coordinates B, Coordinates C) {
		double dolon = 0;
		Coordinates vectorAB = new Coordinates(B.x - A.x, B.y - A.y, B.z - A.z);
		Coordinates vectorAC = new Coordinates(C.x - A.x, C.y - A.y, C.z - A.z);
		// AB(a1,a2,a3) AC(b1,b2,b3)
		Coordinates vectorN = new Coordinates(vectorAB.y * vectorAC.z - vectorAB.z * vectorAC.y,
				vectorAB.z * vectorAC.x - vectorAB.x * vectorAC.z, vectorAB.x * vectorAC.y - vectorAB.y * vectorAC.x);
		
		dolon = Math.sqrt(Math.pow(vectorN.x, 2) + Math.pow(vectorN.y, 2) + Math.pow(vectorN.z, 2)  );
		return dolon;
	}
	

}
