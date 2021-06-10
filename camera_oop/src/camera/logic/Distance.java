package camera.logic;

import camera.entity.Coordinates;

public class Distance {

	// chứa hàm tính khoảng cách giữa hai điểm
	public double calculateDistance(Coordinates A, Coordinates B) {
		double distance = 0;
		distance = Math.sqrt(Math.pow((A.x - B.x), 2) + Math.pow((A.y - B.y), 2) + Math.pow((A.z - B.z), 2));
		return distance;
	}

	// chứa hàm tính khoảng cách từ điểm đến một mặt phẳng chứa 3 điểm A,B,C
	public double khoangCachDenMatPhang(Coordinates M, Coordinates A, Coordinates B, Coordinates C) {
		double distance = 0.0;
		// tinh vector phap tuyến bắng tính có hướng của hai vector AB,AC
		Coordinates vectorAB = new Coordinates(B.x - A.x, B.y - A.y, B.z - A.z);
		Coordinates vectorAC = new Coordinates(C.x - A.x, C.y - A.y, C.z - A.z);
		// AB(a1,a2,a3) AC(b1,b2,b3)
		Coordinates vectorN = new Coordinates(vectorAB.y * vectorAC.z - vectorAB.z * vectorAC.y,
				vectorAB.z * vectorAC.x - vectorAB.x * vectorAC.z, vectorAB.x * vectorAC.y - vectorAB.y * vectorAC.x);
		// tính các hệ số của PT mặt phẳng ax + by + cz + d = 0
		
		double a = vectorN.x;
		double b = vectorN.y;
		double c = vectorN.z;
		
		//vì A thuộc mặt phẳng nên có phương trình a(x - XA) + b(y - yA) + c(z - zA) = 0;
		//d = (-1)*a*XA + (-1)*b*yB +(-1)*c*yC
		double d = (-1)*(a*A.x + b*A.y + c*A.z);
		
		// công thức tính khoảng cách từ điểm M đến mặt phẳng ABC:
		distance = Math.abs(a*M.x + b*M.y + c*M.z + d)/ Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2) +Math.pow(c, 2));
		return distance;
	}
	
	

}
