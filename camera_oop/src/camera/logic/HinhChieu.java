package camera.logic;

import camera.entity.Coordinates;
import camera.entity.Room;

public class HinhChieu {

	// lấy hình chiếu N theo hướng từ trái sang phải
	public Coordinates getHinhChieuTuTraiSangPhai(Coordinates N, Room room) {
		Distance distance = new Distance();
		TinhToanVoiVector tinhToanVoiVector = new TinhToanVoiVector();
		// tạo điểm return về là hình chiếu của N xuống mặt BCGF
		Coordinates N1 = new Coordinates(0, 0, 0);
		double k = (distance.khoangCachDenMatPhang(N, room.B, room.C, room.F))
				/ distance.calculateDistance(room.A, room.B);
		// vectorNN1 = k *vectorAB
		N1 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(N, room.B, room.A, k);
		return N1;

	}

	// lấy hình chiếu N theo hướng từ phải sang trái
	public Coordinates getHinhChieuTuPhaiSangTrai(Coordinates N, Room room) {
		Distance distance = new Distance();
		TinhToanVoiVector tinhToanVoiVector = new TinhToanVoiVector();
		// tạo điểm return về là hình chiếu của N xuống mặt ADHE
		Coordinates N1 = new Coordinates(0, 0, 0);
		double k = (distance.khoangCachDenMatPhang(N, room.A, room.D, room.E))
				/ distance.calculateDistance(room.B, room.A);
		// vectorNN1 = k *vectorBA
		N1 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(N, room.A, room.B, k);
		return N1;

	}

	// lấy hìn chiếu N theo hướng vào mặt tường DCGH
	public Coordinates getHinhChieuTuongDuoiLenTren(Coordinates N, Room room) {
		Distance distance = new Distance();
		TinhToanVoiVector tinhToanVoiVector = new TinhToanVoiVector();
		// tạo điểm return về là hình chiếu của N xuống mặt DCGH
		Coordinates N1 = new Coordinates(0, 0, 0);
		double k = (distance.khoangCachDenMatPhang(N, room.D, room.C, room.H))
				/ distance.calculateDistance(room.A, room.D);
		// vectorNN1 = k *vectorAD
		N1 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(N, room.D, room.A, k);
		return N1;
	}

	// lấy hìn chiếu N theo hướng vào mặt tường ABFE
	public Coordinates getHinhChieuTuongTrenXuongDuoi(Coordinates N, Room room) {
		Distance distance = new Distance();
		TinhToanVoiVector tinhToanVoiVector = new TinhToanVoiVector();
		// tạo điểm return về là hình chiếu của N xuống mặt ABFE
		Coordinates N1 = new Coordinates(0, 0, 0);
		double k = (distance.khoangCachDenMatPhang(N, room.A, room.B, room.E))
				/ distance.calculateDistance(room.D, room.A);
		// vectorNN1 = k *vectorDA
		N1 = tinhToanVoiVector.layToaDoTheoTiSoHaiVector(N, room.A, room.D, k);
		return N1;
	}
	
	

}
