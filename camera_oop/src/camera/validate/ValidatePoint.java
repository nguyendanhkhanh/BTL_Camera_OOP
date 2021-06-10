package camera.validate;

import java.util.List;

import camera.entity.Camera;
import camera.entity.Coordinates;
import camera.entity.Items;
import camera.entity.Room;
import camera.logic.GiaiPhuongTrinh;
import camera.logic.PhuongTrinhDuongThang;
import camera.logic.PhuongTrinhMatPhang;
import camera.logic.TinhToanVoiVector;
import camera.logic.Volume;

public class ValidatePoint {

	/**
	 * Method check 1 điểm có thuộc phòng không
	 * 
	 * @param M
	 * @param room
	 * @return
	 */

	public boolean checkPointInRoom(Coordinates M, Room room) {
		boolean check = false;
		// the tich M. ABCD, M.EFGH, M.AEHD, M.BFGC, M.ABFE, M.DCGH
		Volume volume = new Volume();

		double thetichMABCD = volume.tinhTheTichHinhChopTuGiac(M, room.A, room.B, room.C, room.D);
		double thetichMEFGH = volume.tinhTheTichHinhChopTuGiac(M, room.E, room.F, room.G, room.H);
		double thetichMAEHD = volume.tinhTheTichHinhChopTuGiac(M, room.A, room.E, room.H, room.D);
		double thetichMBFGC = volume.tinhTheTichHinhChopTuGiac(M, room.B, room.F, room.G, room.C);
		double thetichMABFE = volume.tinhTheTichHinhChopTuGiac(M, room.A, room.B, room.F, room.E);
		double thetichMDCGH = volume.tinhTheTichHinhChopTuGiac(M, room.D, room.C, room.G, room.H);
		// tính thể tích của phòng
		double thetichPhong = volume.calculateVolumeRoom(room);
		// so sánh tổng thể tích 6 hình con và 1 hình to, hiệu hai thể tích 1 sai số cho
		// phép
		if ((thetichMABCD + thetichMEFGH + thetichMAEHD + thetichMBFGC + thetichMABFE + thetichMDCGH)
				- thetichPhong <= 0.5) {
			check = true;

		}
		if (!check) {
			System.out.println("Điểm " + M + " Không thuộc phòng");
		}
		return check;
	}

	/**
	 * Method dùng để kiểm tra điểm N có nằm trong không gian hình chóp tứ giác
	 * I.HKK'H'
	 * 
	 * @param N
	 * @param I
	 * @param H
	 * @param K
	 * @param K1
	 * @param H1
	 * @return
	 */

	public boolean checkPointInHinhChopTuGiac(Coordinates N, Coordinates I, Coordinates H, Coordinates K,
			Coordinates K1, Coordinates H1) {
		boolean check = false;
		// chia hình chóp tứ giác thành 5 hình N.HKK'H' , N.IKK' , N. K'IH', N.IHH',
		// N.IHK
		Volume volume = new Volume();
		double thetichNHKK1H1 = volume.tinhTheTichHinhChopTuGiac(N, H, K, K1, H1);
		double thetichNIKK1 = volume.tinhTheTichHinhChopTamGiac(N, I, K, K1);
		double thetichNK1IH1 = volume.tinhTheTichHinhChopTamGiac(N, I, H1, K1);
		double thetichIHH1 = volume.tinhTheTichHinhChopTamGiac(N, I, H, H1);
		double thetichNIHK = volume.tinhTheTichHinhChopTamGiac(N, I, H, K);

		double thetichIHKK1H1 = volume.tinhTheTichHinhChopTuGiac(I, H, K, K1, H1);

		if ((thetichNHKK1H1 + thetichNIKK1 + thetichNK1IH1 + thetichIHH1 + thetichNIHK) - thetichIHKK1H1 < 0.5) {
			check = true;
		}

		return check;
	}

	/**
	 * Method dùng để kiểm tra 1 điểm có nằm trong vật hình hộp không
	 * 
	 * @param M     : điểm cần check
	 * @param items
	 * @return check : true nếu điểm nằm trong vật thể
	 */

	public boolean checkPointInVatThe(Coordinates M, Items items) {
		boolean check = false;
		// the tich M. ABCD, M.EFGH, M.AEHD, M.BFGC, M.ABFE, M.DCGH
		Volume volume = new Volume();

		double thetichMABCD = volume.tinhTheTichHinhChopTuGiac(M, items.A, items.B, items.C, items.D);
		double thetichMEFGH = volume.tinhTheTichHinhChopTuGiac(M, items.E, items.F, items.G, items.H);
		double thetichMAEHD = volume.tinhTheTichHinhChopTuGiac(M, items.A, items.E, items.H, items.D);
		double thetichMBFGC = volume.tinhTheTichHinhChopTuGiac(M, items.B, items.F, items.G, items.C);
		double thetichMABFE = volume.tinhTheTichHinhChopTuGiac(M, items.A, items.B, items.F, items.E);
		double thetichMDCGH = volume.tinhTheTichHinhChopTuGiac(M, items.D, items.C, items.G, items.H);
		// tính thể tích của vật thể
		double thetichVatThe = volume.calculateVolumeItems(items);
		// so sánh tổng thể tích 6 hình con và 1 hình to, hiệu hai thể tích 1 sai số cho
		// phép
		if ((thetichMABCD + thetichMEFGH + thetichMAEHD + thetichMBFGC + thetichMABFE + thetichMDCGH)
				- thetichVatThe <= 0.5) {
			check = true;

		}

		return check;
	}

	/**
	 * Method dùng để kiểm tra đường thẳng nối điểm camera I với điểm cần check N có
	 * đi qua vật thể hay không ( có bị cản bởi vật thể hình hộp không)
	 * 
	 * @param I
	 * @param N
	 * @param items
	 * @return true nếu đường nối giữa điểm camera và điểm cần xét đi qua vật thể (
	 *         bị chặn)
	 */

	public boolean kiemTraDuongThangGiaoVatthe(Coordinates I, Coordinates N, Items items) {
		// tư tưởng chính là đường thẳng nối điểm và vật có cắt một mặt phẳng nào đó của
		// vật, và điểm đó thuộc vật thể
		// viet pt đường thẳng nối điểm và vật, pt giao có nghiệm + điều kiện điểm thuộc
		// vật thể
		boolean check = false;
		ValidatePoint validatePoint = new ValidatePoint();

		PhuongTrinhMatPhang phuongTrinhMatPhang = new PhuongTrinhMatPhang();
		PhuongTrinhDuongThang phuongTrinhDuongThang = new PhuongTrinhDuongThang();
		Coordinates vectorChiPhuongDuongThang = phuongTrinhDuongThang.layVectorChiPhuong(I, N);
		GiaiPhuongTrinh giaiPhuongTrinh = new GiaiPhuongTrinh();
		List<Double> hesoMp = null;
		// pt duong thang la x = x0 + at, y = y0 + bt, z = z0+bt
		// pt mặt phẳng là Ax + By + Cz + D = 0
		// --> AxK + ByK + CzK + D = 0 --> A (xI + at) + B(yI + bt ) + C(zI + ct) + D= 0
		// có nghiệm
		// -- > giat Pt bac nhat : (Aa+Bb+ Cc)t + A.xI + B.yI + C.zI + D= 0
		double a = vectorChiPhuongDuongThang.x;
		double b = vectorChiPhuongDuongThang.y;
		double c = vectorChiPhuongDuongThang.z;

		// mặt ADHE của vật
		hesoMp = phuongTrinhMatPhang.layHeSoPtMatPhang(items.A, items.D, items.E);
		double A = hesoMp.get(0);
		double B = hesoMp.get(1);
		double C = hesoMp.get(2);
		double D = hesoMp.get(3);
		double hesoPtbacNhata = A * a + B * b + C * c;
		double hesoPtbacNhatb = A * I.x + B * I.y + C * I.z + D;
		Double t1 = giaiPhuongTrinh.giaiPhuongTrinhBacNhat(hesoPtbacNhata, hesoPtbacNhatb);
		Coordinates giaoDiem1 = null;
		if (t1 != null) {
			giaoDiem1 = new Coordinates(I.x + a * t1, I.y + b * t1, I.z + c * t1);
			if (validatePoint.checkPointInVatThe(giaoDiem1, items)) {
				check = true;
			}
		}

		// mặt BCGF của vật
		hesoMp = phuongTrinhMatPhang.layHeSoPtMatPhang(items.B, items.C, items.F);
		A = hesoMp.get(0);
		B = hesoMp.get(1);
		C = hesoMp.get(2);
		D = hesoMp.get(3);
		hesoPtbacNhata = A * a + B * b + C * c;
		hesoPtbacNhatb = A * I.x + B * I.y + C * I.z + D;
		Double t2 = giaiPhuongTrinh.giaiPhuongTrinhBacNhat(hesoPtbacNhata, hesoPtbacNhatb);
		Coordinates giaoDiem2 = null;
		if (t2 != null) {
			giaoDiem2 = new Coordinates(I.x + a * t2, I.y + b * t2, I.z + c * t2);
			if (validatePoint.checkPointInVatThe(giaoDiem2, items)) {
				check = true;
			}
		}

		// mặt ABFE của vật
		hesoMp = phuongTrinhMatPhang.layHeSoPtMatPhang(items.A, items.B, items.E);
		A = hesoMp.get(0);
		B = hesoMp.get(1);
		C = hesoMp.get(2);
		D = hesoMp.get(3);
		hesoPtbacNhata = A * a + B * b + C * c;
		hesoPtbacNhatb = A * I.x + B * I.y + C * I.z + D;
		Double t3 = giaiPhuongTrinh.giaiPhuongTrinhBacNhat(hesoPtbacNhata, hesoPtbacNhatb);
		Coordinates giaoDiem3 = null;
		if (t3 != null) {
			giaoDiem3 = new Coordinates(I.x + a * t3, I.y + b * t3, I.z + c * t3);
			if (validatePoint.checkPointInVatThe(giaoDiem3, items)) {
				check = true;
			}
		}

		// mặt DCGH của vật
		hesoMp = phuongTrinhMatPhang.layHeSoPtMatPhang(items.D, items.C, items.H);
		A = hesoMp.get(0);
		B = hesoMp.get(1);
		C = hesoMp.get(2);
		D = hesoMp.get(3);
		hesoPtbacNhata = A * a + B * b + C * c;
		hesoPtbacNhatb = A * I.x + B * I.y + C * I.z + D;
		Double t4 = giaiPhuongTrinh.giaiPhuongTrinhBacNhat(hesoPtbacNhata, hesoPtbacNhatb);
		Coordinates giaoDiem4 = null;
		if (t4 != null) {
			giaoDiem4 = new Coordinates(I.x + a * t4, I.y + b * t4, I.z + c * t4);
			if (validatePoint.checkPointInVatThe(giaoDiem4, items)) {
				check = true;
			}
		}

		// mặt EFGH của vật
		hesoMp = phuongTrinhMatPhang.layHeSoPtMatPhang(items.E, items.F, items.H);
		A = hesoMp.get(0);
		B = hesoMp.get(1);
		C = hesoMp.get(2);
		D = hesoMp.get(3);
		hesoPtbacNhata = A * a + B * b + C * c;
		hesoPtbacNhatb = A * I.x + B * I.y + C * I.z + D;
		Double t5 = giaiPhuongTrinh.giaiPhuongTrinhBacNhat(hesoPtbacNhata, hesoPtbacNhatb);
		Coordinates giaoDiem5 = null;
		if (t5 != null) {
			giaoDiem5 = new Coordinates(I.x + a * t5, I.y + b * t5, I.z + c * t5);
			if (validatePoint.checkPointInVatThe(giaoDiem5, items)) {
				check = true;
			}
		}

		return check;
	}

	/**
	 * Method dùng để check điểm N có thuộc vùng sáng của 1 camera không ( khi xét
	 * tương quan với 1 list vật thế)
	 * 
	 * @param N
	 * @param camera
	 * @param items
	 * @return
	 */

	public boolean checkDiemThuocVungSangMotCamera(Coordinates N, Camera camera, Room room, List<Items> listItems) {
		boolean check = true;
		ValidatePoint validatePoint = new ValidatePoint();
		TinhToanVoiVector tinhToanVoiVector = new TinhToanVoiVector();
		// lấy đáy của hình chóp chiếu bởi camera
		List<Coordinates> listPoitHKK1H1 = tinhToanVoiVector.layToaDoDayHinhChop(camera, room);

		for (int i = 0; i < listItems.size(); i++) {
			if (!validatePoint.checkPointInHinhChopTuGiac(N, camera.toadoCamera, listPoitHKK1H1.get(0),
					listPoitHKK1H1.get(1), listPoitHKK1H1.get(2), listPoitHKK1H1.get(3))
					|| validatePoint.kiemTraDuongThangGiaoVatthe(camera.toadoCamera, N, listItems.get(i))) {
				check = false;

			}
		}
		return check;
	}
	
	/**
	 * Method dùng để check điểm có thuộc vùng sáng của camera nào không
	 * 
	 * @param N
	 * @param listCamera
	 * @param room
	 * @param listItems
	 * @return
	 */
	
	public boolean checkDiemThuocVungSangCuaNhieuCamera(Coordinates N, List<Camera> listCamera, Room room, List<Items> listItems) {
		boolean check = false;
		ValidatePoint validatePoint = new ValidatePoint();
		//điểm thuộc vùng chiếu sáng của ít nhất 1 camera là ok
		for (int i = 0; i < listCamera.size(); i++) {
			if (validatePoint.checkDiemThuocVungSangMotCamera(N, listCamera.get(i), room, listItems)) {
				check = true;
			}
		}
		
		return check;
	}

}
