package camera.logic;

import java.util.ArrayList;
import java.util.List;

import camera.entity.Coordinates;

public class PhuongTrinhMatPhang {
	/**
	 * Method lấy các hệ số a,b,c,d của pt mặt phẳng đi qua 3 điể, A,B,C
	 * 
	 * @param A
	 * @param B
	 * @param C
	 * @return listheso : lưu các số theo thứ tự a,b,c,d
	 */
	
	public List<Double> layHeSoPtMatPhang(Coordinates A, Coordinates B, Coordinates C) {
		List<Double> listHeSo = new ArrayList<Double>();
		// goi pt mat phang la ax + by + cz + d = 0;
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

		// vì A thuộc mặt phẳng nên có phương trình a(x - XA) + b(y - yA) + c(z - zA) =
		// 0;
		// d = (-1)*a*XA + (-1)*b*yB +(-1)*c*yC
		double d = (-1) * (a * A.x + b * A.y + c * A.z);
		
		listHeSo.add(a);
		listHeSo.add(b);
		listHeSo.add(c);
		listHeSo.add(d);
		
		return listHeSo;

	}
	
}
