package camera.logic;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import camera.entity.Camera;
import camera.entity.Coordinates;
import camera.entity.Room;

public class TinhToanVoiVector {

	/**
	 * Method tính tọa độ trọng tâm đáy hình chóp I.HKK'H' là điểm Q
	 * 
	 * @param I : Đỉnh chóp, hay tọa độ camera
	 * @param A : A,B cùng tạo vector AB cùng hướng, cùng độ dài với vector IQ
	 * @param B
	 * @return Q : tọa độ điểm Q tương ứng với hình chiếu của I xuống đáy hình chóp
	 */

	public Coordinates layToaDoTrongTamDayHinhChop(Coordinates I, Coordinates A, Coordinates B) {
		Coordinates daychopQ = new Coordinates(0, 0, 0);
		daychopQ.x = B.x - A.x + I.x;
		daychopQ.y = B.y - A.y + I.y;
		daychopQ.z = B.z - A.z + I.z;
		return daychopQ; 
	}

	/**
	 * Method dùng để tính tọa độ của 1 điểm M, biết vector QM cùng hướng với vector
	 * DA, và tỉ số độ dài QM/DA =k
	 * 
	 * @param Q
	 * @param A
	 * @param D
	 * @return M : tọa độ điểm M
	 */

	public Coordinates layToaDoTheoTiSoHaiVector(Coordinates Q, Coordinates A, Coordinates D, double k) {
		Coordinates M = new Coordinates(0, 0, 0);
		M.x = k * (A.x - D.x) + Q.x;
		M.y = k * (A.y - D.y) + Q.y;
		M.z = k * (A.z - D.z) + Q.z;
		return M;
	}

	/**
	 * Method tính tọa độ điểm M1 đối xứng điểm M qua trung điểm Q
	 * 
	 * @param Q
	 * @param M
	 * @return M1
	 */

	public Coordinates laylayToaDoTheoDiemDoiXung(Coordinates Q, Coordinates M) {
		Coordinates M1 = new Coordinates(0, 0, 0);
		M1.x = 2 * Q.x - M.x;
		M1.y = 2 * Q.y - M.y;
		M1.z = 2 * Q.z - M.z;
		return M1;
	}

	/**
	 * Method tính tọa độ các đỉnh đáy hỉnh chóp, là giao của vùng chiếu camera với
	 * mặt phẳng đối diện là sàn nhà ABCD ứng với HKK'H'. (sàn là chiều nhìn từ trên xuống)
	 * 
	 * @param I
	 * @param A
	 * @param B
	 * @param C
	 * @param D
	 * @return
	 */

	public List<Coordinates> layToaDoDayHinhChopTuTranNha(Camera camera, Room room) {
		Coordinates A = room.A;
		Coordinates B = room.B;
		Coordinates C = room.C;
		Coordinates D = room.D;
		Coordinates E = room.E;
		//hàm thực hiện với hình chóp I.HKK'H', với I là tọa độ camera
		Coordinates I = camera.toadoCamera;
		List<Coordinates> listPointH_K_K1_H1 = new ArrayList<Coordinates>();
		GiaiPhuongTrinh giaiPhuongTrinh = new GiaiPhuongTrinh();
		Distance distance = new Distance();
		TinhToanVoiVector tinhToanVoiVector = new TinhToanVoiVector();
		//góc H0IK
		double gocHIK = camera.gocRong;
		//goc KIK'
		double gocKIK1 = camera.gocCao;
		
		double sinMotNuaGocRong = Math.sin(Math.toRadians(gocHIK/2));
		double sinMotNuaGocCao = Math.sin(Math.toRadians(gocKIK1/2));
		double cosMotNuaGocRong = Math.cos(Math.toRadians(gocHIK/2));
		double cosMotNuaGocCao = Math.cos(Math.toRadians(gocKIK1/2));
		
		
		//tính tọa độ Q biết vector IQ = vector EA	
		Coordinates Q = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(I, A, E, 1);
		//tính đoạn IQ
		double IQ = distance.calculateDistance(I, Q);
		//giai pt bậc 2 lấy nghiệm dương để lấy chiều dài h = IH là cạnh hình chóp
		
		double a = 1 - ((Math.pow(sinMotNuaGocRong, 2) + Math.pow(sinMotNuaGocCao, 2)));
		double b = 0;
		double c = (-1)*Math.pow(IQ, 2);
		double h = giaiPhuongTrinh.giaiPhuongtrinhBacHai(a, b, c);
		
		//tính tỉ số k = QM/DA
		double k = (Math.sqrt((Math.pow((h*cosMotNuaGocRong), 2) - Math.pow(IQ, 2))))/(distance.calculateDistance(A, D));
		//tính tọa độ điểm M là trung điểm HK
		Coordinates M = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(Q, A, D, k);
		
		//tính tọa độ điểm H theo M và vector BA
		double k1 = (h*sinMotNuaGocRong)/(distance.calculateDistance(B, A));
		Coordinates H = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(M, A, B, k1);
		//tính tọa độ điểm K theo H và trung điểm M
		Coordinates K = tinhToanVoiVector.laylayToaDoTheoDiemDoiXung(M, H);
		
		//tính tọa độ K' khi biết KK' = 2MQ
		Coordinates K1 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(K, Q, M, 2);
		//tính tọa độ H' khi biết HH' = 2MQ
		Coordinates H1 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(H, Q, M, 2);
		
		//nhét 4 điểm H,K,K',H'vào list
		listPointH_K_K1_H1.add(H);
		listPointH_K_K1_H1.add(K);
		listPointH_K_K1_H1.add(K1);
		listPointH_K_K1_H1.add(H1);
		return listPointH_K_K1_H1;

	}
	
	/**
	 * Method láy tọa độ đáy hình chóp được chiếu từ camera, cắt với mặt phẳng DCGH ( chiều từ ngoài vào trong)
	 * 
	 * @param camera
	 * @param room
	 * @return list
	 */
	
	public List<Coordinates> layToaDoDayHinhChopTuNgoaiVaoTrong(Camera camera, Room room) {
		Coordinates D = room.D;
		Coordinates C = room.C;
		Coordinates G = room.G;
		Coordinates H = room.H;
		Coordinates E = room.E;
		//hàm thực hiện với hình chóp I.H0KK'H', với I là tọa độ camera
		Coordinates I = camera.toadoCamera;
		List<Coordinates> listPointH_K_K1_H1 = new ArrayList<Coordinates>();
		GiaiPhuongTrinh giaiPhuongTrinh = new GiaiPhuongTrinh();
		Distance distance = new Distance();
		TinhToanVoiVector tinhToanVoiVector = new TinhToanVoiVector();
		//góc HIK
		double gocHIK = camera.gocRong;
		//goc KIK'
		double gocKIK1 = camera.gocCao;
		
		double sinMotNuaGocRong = Math.sin(Math.toRadians(gocHIK/2));
		double sinMotNuaGocCao = Math.sin(Math.toRadians(gocKIK1/2));
		double cosMotNuaGocRong = Math.cos(Math.toRadians(gocHIK/2));
		double cosMotNuaGocCao = Math.cos(Math.toRadians(gocKIK1/2));
		
		//tính tọa độ Q biết vector IQ = vector EH	
		Coordinates Q = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(I, H, E, 1);
		//tính đoạn IQ
		double IQ = distance.calculateDistance(I, Q);
		//giai pt bậc 2 lấy nghiệm dương để lấy chiều dài h = IH0 là cạnh hình chóp
		
		double a = 1 - ((Math.pow(sinMotNuaGocRong, 2) + Math.pow(sinMotNuaGocCao, 2)));
		double b = 0;
		double c = (-1)*Math.pow(IQ, 2);
		double h = giaiPhuongTrinh.giaiPhuongtrinhBacHai(a, b, c); 
		
		//tính tỉ số k = QM/HD
		double k = (Math.sqrt((Math.pow((h*cosMotNuaGocRong), 2) - Math.pow(IQ, 2))))/(distance.calculateDistance(H, D));
		//QM = cbh(IM^2 - IQ^2)
		//tính tọa độ điểm M là trung điểm H0K
		Coordinates M = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(Q, D, H, k);
		
		//tính tọa độ điểm H0 theo M và vector CD biết MH0 cùng hướng CD
		double k1 = (h*sinMotNuaGocRong)/(distance.calculateDistance(C, D));
		//sai K1= MH0/CD, MH0= h*sinMotNuaGocRong, CD bị tính *2
		Coordinates H0 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(M, D, C, k1);
		//tính tọa độ điểm K theo H0 và trung điểm M
		Coordinates K = tinhToanVoiVector.laylayToaDoTheoDiemDoiXung(M, H0);
		
		//tính tọa độ K' khi biết KK' = 2MQ
		Coordinates K1 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(K, Q, M, 2);
		//tính tọa độ H' khi biết H0H' = 2MQ
		Coordinates H1 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(H0, Q, M, 2);
		
		//nhét 4 điểm H0,K,K',H'vào list
		listPointH_K_K1_H1.add(H0);
		listPointH_K_K1_H1.add(K);
		listPointH_K_K1_H1.add(K1);
		listPointH_K_K1_H1.add(H1);
		return listPointH_K_K1_H1;

	}
	
	/**
	 * Method dùng lấy tọa độ đáy hình chóp cắt với mặt phẳng chứa tường ABFE theo hướng từ trong ra ngoài
	 * 
	 * @param camera
	 * @param room
	 * @return
	 */
	
	public List<Coordinates> layToaDoDayHinhChopTuTrongRaNgoai(Camera camera, Room room) {
		Coordinates A = room.A;
		Coordinates B = room.B;
		Coordinates F = room.F;
		Coordinates E = room.E;
		Coordinates H = room.H;
		//hàm thực hiện với hình chóp I.H0KK'H', với I là tọa độ camera
		Coordinates I = camera.toadoCamera;
		List<Coordinates> listPointH_K_K1_H1 = new ArrayList<Coordinates>();
		GiaiPhuongTrinh giaiPhuongTrinh = new GiaiPhuongTrinh();
		Distance distance = new Distance();
		TinhToanVoiVector tinhToanVoiVector = new TinhToanVoiVector();
		//góc HIK
		double gocHIK = camera.gocRong;
		//goc KIK'
		double gocKIK1 = camera.gocCao;
		
		double sinMotNuaGocRong = Math.sin(Math.toRadians(gocHIK/2));
		double sinMotNuaGocCao = Math.sin(Math.toRadians(gocKIK1/2));
		double cosMotNuaGocRong = Math.cos(Math.toRadians(gocHIK/2));
		double cosMotNuaGocCao = Math.cos(Math.toRadians(gocKIK1/2));
		
		//tính tọa độ Q biết vector IQ = vector HE	
		Coordinates Q = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(I, E, H, 1);
		//tính đoạn IQ
		double IQ = distance.calculateDistance(I, Q);
		//giai pt bậc 2 lấy nghiệm dương để lấy chiều dài h = IH0 là cạnh hình chóp
		
		double a = 1 - ((Math.pow(sinMotNuaGocRong, 2) + Math.pow(sinMotNuaGocCao, 2)));
		double b = 0;
		double c = (-1)*Math.pow(IQ, 2);
		double h = giaiPhuongTrinh.giaiPhuongtrinhBacHai(a, b, c);
		
		//tính tỉ số k = QM/EA
		double k = (Math.sqrt((Math.pow((h*cosMotNuaGocRong), 2) - Math.pow(IQ, 2))))/(distance.calculateDistance(E, A));
		//QM nhu tren
		//tính tọa độ điểm M là trung điểm H0K
		Coordinates M = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(Q, A, E, k);
		
		//tính tọa độ điểm H0 theo M và vector BA biết MH0 cùng hướng BA
		double k1 = (h*sinMotNuaGocRong)/(distance.calculateDistance(B, A));
		
		Coordinates H0 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(M, A, B, k1);
		//tính tọa độ điểm K theo H0 và trung điểm M
		Coordinates K = tinhToanVoiVector.laylayToaDoTheoDiemDoiXung(M, H0);
		
		//tính tọa độ K' khi biết KK' = 2MQ
		Coordinates K1 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(K, Q, M, 2);
		//tính tọa độ H' khi biết H0H' = 2MQ
		Coordinates H1 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(H0, Q, M, 2);
		
		//nhét 4 điểm H0,K,K',H'vào list
		listPointH_K_K1_H1.add(H0);
		listPointH_K_K1_H1.add(K);
		listPointH_K_K1_H1.add(K1);
		listPointH_K_K1_H1.add(H1);
		return listPointH_K_K1_H1;

	}
	
	/**
	 * Method dùng lấy tọa độ đáy hình chóp camera cắt với mặt phẳng chứa tường BCGF theo hướng trái sang phải
	 * 
	 * @param camera
	 * @param room
	 * @return
	 */
	
	public List<Coordinates> layToaDoDayHinhChopTuTraiSangPhai(Camera camera, Room room) {
		Coordinates B = room.B;
		Coordinates C = room.C;
		Coordinates G = room.G;
		Coordinates F = room.F;
		Coordinates A = room.A;
		//hàm thực hiện với hình chóp I.H0KK'H', với I là tọa độ camera
		Coordinates I = camera.toadoCamera;
		List<Coordinates> listPointH_K_K1_H1 = new ArrayList<Coordinates>();
		GiaiPhuongTrinh giaiPhuongTrinh = new GiaiPhuongTrinh();
		Distance distance = new Distance();
		TinhToanVoiVector tinhToanVoiVector = new TinhToanVoiVector();
		//góc HIK
		double gocHIK = camera.gocRong;
		//goc KIK'
		double gocKIK1 = camera.gocCao;
		
		double sinMotNuaGocRong = Math.sin(Math.toRadians(gocHIK/2));
		double sinMotNuaGocCao = Math.sin(Math.toRadians(gocKIK1/2));
		double cosMotNuaGocRong = Math.cos(Math.toRadians(gocHIK/2));
		double cosMotNuaGocCao = Math.cos(Math.toRadians(gocKIK1/2));
		
		//tính tọa độ Q biết vector IQ = vector AB	
		Coordinates Q = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(I, B, A, 1);
		//tính đoạn IQ
		double IQ = distance.calculateDistance(I, Q);
		//giai pt bậc 2 lấy nghiệm dương để lấy chiều dài h = IH0 là cạnh hình chóp
		
		double a = 1 - ((Math.pow(sinMotNuaGocRong, 2) + Math.pow(sinMotNuaGocCao, 2)));
		double b = 0;
		double c = (-1)*Math.pow(IQ, 2);
		double h = giaiPhuongTrinh.giaiPhuongtrinhBacHai(a, b, c);
		
		//tính tỉ số k = QM/FB
		double k = (Math.sqrt((Math.pow((h*cosMotNuaGocRong), 2) - Math.pow(IQ, 2))))/(distance.calculateDistance(F, B));
		//tính tọa độ điểm M là trung điểm HK
		Coordinates M = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(Q, B, F, k);
		
		//tính tọa độ điểm H0 theo M và vector CB biết MH0 cùng hướng CB
		double k1 = (h*sinMotNuaGocRong)/(distance.calculateDistance(C, B));
		// sai vì CB bị tính *2
		Coordinates H0 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(M, B, C, k1);
		//tính tọa độ điểm K theo H0 và trung điểm M
		Coordinates K = tinhToanVoiVector.laylayToaDoTheoDiemDoiXung(M, H0);
		
		//tính tọa độ K' khi biết KK' = 2MQ
		Coordinates K1 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(K, Q, M, 2);
		//tính tọa độ H' khi biết H0H' = 2MQ
		Coordinates H1 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(H0, Q, M, 2);
		
		//nhét 4 điểm H0,K,K',H'vào list
		listPointH_K_K1_H1.add(H0);
		listPointH_K_K1_H1.add(K);
		listPointH_K_K1_H1.add(K1);
		listPointH_K_K1_H1.add(H1);
		return listPointH_K_K1_H1;

	}
	
	
	/**
	 * Method dùng lấy tọa độ đáy hình chóp camera cắt với mặt phẳng chứa tường ADHE theo hướng phải sang trái
	 * 
	 * @param camera
	 * @param room
	 * @return
	 */
	
	public List<Coordinates> layToaDoDayHinhChopTuPhaiSangTrai(Camera camera, Room room) {
		Coordinates A = room.A;
		Coordinates D = room.D;
		Coordinates H = room.H;
		Coordinates E = room.E;
		Coordinates B = room.B;
		//hàm thực hiện với hình chóp I.H0KK'H', với I là tọa độ camera
		Coordinates I = camera.toadoCamera;
		List<Coordinates> listPointH_K_K1_H1 = new ArrayList<Coordinates>();
		GiaiPhuongTrinh giaiPhuongTrinh = new GiaiPhuongTrinh();
		Distance distance = new Distance();
		TinhToanVoiVector tinhToanVoiVector = new TinhToanVoiVector();
		//góc HIK
		double gocHIK = camera.gocRong;
		//goc KIK'
		double gocKIK1 = camera.gocCao;
		
		double sinMotNuaGocRong = Math.sin(Math.toRadians(gocHIK/2));
		double sinMotNuaGocCao = Math.sin(Math.toRadians(gocKIK1/2));
		double cosMotNuaGocRong = Math.cos(Math.toRadians(gocHIK/2));
		double cosMotNuaGocCao = Math.cos(Math.toRadians(gocKIK1/2));
		
		//tính tọa độ Q biết vector IQ = vector BA	
		Coordinates Q = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(I, A, B, 1);
		//tính đoạn IQ
		double IQ = distance.calculateDistance(I, Q);
		//giai pt bậc 2 lấy nghiệm dương để lấy chiều dài h = IH0 là cạnh hình chóp
		
		double a = 1 - ((Math.pow(sinMotNuaGocRong, 2) + Math.pow(sinMotNuaGocCao, 2)));
		double b = 0;
		double c = (-1)*Math.pow(IQ, 2);
		double h = giaiPhuongTrinh.giaiPhuongtrinhBacHai(a, b, c);
		
		//tính tỉ số k = QM/EA
		double k = (h*sinMotNuaGocCao)/(distance.calculateDistance(E, A));
		//tính tọa độ điểm M là trung điểm H0K
		Coordinates M = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(Q, A, E, k);
		
		//tính tọa độ điểm H0 theo M và vector DA biết MH0 cùng hướng DA
		double k1 = (h*sinMotNuaGocRong)/(2*distance.calculateDistance(D, A));
		// sai vì DA bị tính *2
		Coordinates H0 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(M, A, D, k1);
		//tính tọa độ điểm K theo H0 và trung điểm M
		Coordinates K = tinhToanVoiVector.laylayToaDoTheoDiemDoiXung(M, H0);
		
		//tính tọa độ K' khi biết KK' = 2MQ
		Coordinates K1 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(K, Q, M, 2);
		//tính tọa độ H' khi biết H0H' = 2MQ
		Coordinates H1 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(H0, Q, M, 2);
		
		//nhét 4 điểm H0,K,K',H'vào list
		listPointH_K_K1_H1.add(H0);
		listPointH_K_K1_H1.add(K);
		listPointH_K_K1_H1.add(K1);
		listPointH_K_K1_H1.add(H1);
		return listPointH_K_K1_H1;

	}
	
	
	/**
	 * Method dùng để lấy tọa độ đáy hình chóp giao với tường đối diện khi chiếu camera
	 * 
	 * @param camera
	 * @param room
	 * @return
	 */
	
	public List<Coordinates> layToaDoDayHinhChop(Camera camera, Room room){
		Distance distance = new Distance();
		TinhToanVoiVector tinhToanVoiVector = new TinhToanVoiVector();
		// nếu camera gắn ở EFGH
		List<Coordinates> listPointH_K_K1_H1 = new ArrayList<Coordinates>();
		if (distance.khoangCachDenMatPhang(camera.toadoCamera, room.E, room.F, room.H) == 0) {
			listPointH_K_K1_H1 = tinhToanVoiVector.layToaDoDayHinhChopTuTranNha(camera, room);
		}
		//nếu camera gắn ở tường ADHE
		if (distance.khoangCachDenMatPhang(camera.toadoCamera, room.A, room.D, room.E) == 0) {
			listPointH_K_K1_H1 = tinhToanVoiVector.layToaDoDayHinhChopTuTraiSangPhai(camera, room);
		}
		// nếu camera gắn ở tường BCGF
		if (distance.khoangCachDenMatPhang(camera.toadoCamera, room.B, room.C, room.F) == 0) {
			listPointH_K_K1_H1 = tinhToanVoiVector.layToaDoDayHinhChopTuPhaiSangTrai(camera, room);
		}
		//nếu camera gắn ở tường ABFE
		if (distance.khoangCachDenMatPhang(camera.toadoCamera, room.A, room.B, room.E) == 0) {
			listPointH_K_K1_H1 = tinhToanVoiVector.layToaDoDayHinhChopTuNgoaiVaoTrong(camera, room);
		}
		//nếu camera gắn ở tường CDHG
		if (distance.khoangCachDenMatPhang(camera.toadoCamera, room.C, room.G, room.D) == 0) {
			listPointH_K_K1_H1 = tinhToanVoiVector.layToaDoDayHinhChopTuTrongRaNgoai(camera, room);
		}
		return listPointH_K_K1_H1;
	}
	
	
	
	
	

}
