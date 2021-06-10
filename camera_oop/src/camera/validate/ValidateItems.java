package camera.validate;

import java.util.ArrayList;
import java.util.List;


import camera.entity.Coordinates;
import camera.entity.Items;
import camera.entity.Room;
import camera.logic.Distance;

public class ValidateItems {

	/**
	 * Kiểm tra 4 điểm có tạo thành 1 hình chữ nhật không 
	 * 
	 * @return checkHinhChuNhat : boolean
	 */

	public boolean checkIsHinhChuNhat(Coordinates A, Coordinates B, Coordinates C, Coordinates D) {
		boolean checkHinhChuNhat = false;
		// toa do vectorAB theo truc ox, oy, oz

		Coordinates vertorAB = new Coordinates(B.x - A.x, B.y - A.y, B.z - A.z);
		Coordinates vectorAD = new Coordinates(D.x - A.x, D.y - A.y, D.z - A.z);

		Coordinates vectorBA = new Coordinates(A.x - B.x, A.y - B.y, A.z - B.z);
		Coordinates vectorBC = new Coordinates(C.x - B.x, C.y - B.y, C.z - B.z);

		Coordinates vectorCB = new Coordinates(B.x - C.x, B.y - C.y, B.z - C.z);
		Coordinates vectorCD = new Coordinates(D.x - C.x, D.y - C.y, D.z - C.z);

		// hình chữ nhật là hình có 3 góc vuông trở lên
		// tích vô hướng = 0 thì hai đường thẳng vuông góc
		double tichAB_AD = vertorAB.x * vectorAD.x + vertorAB.y * vectorAD.y + vertorAB.z * vectorAD.z;
		double tichBA_BC = vectorBA.x * vectorBC.x + vectorBA.y * vectorBC.y + vectorBA.z * vectorBC.z;
		double tichCB_CD = vectorCB.x * vectorCD.x + vectorCB.y * vectorCD.y + vectorCB.z * vectorCD.z;

		if (tichAB_AD == 0.0 && tichBA_BC == 0.0 && tichCB_CD == 0.0) {
			checkHinhChuNhat = true;
		}
		return checkHinhChuNhat;
	}

	/**
	 * Method check vật có phải hình hộp chữ nhật không
	 * 
	 * @param item
	 * @return checkHinhHop : boolean
	 */

	public boolean checkIsHinhHopChuNhat(Items item) {
		boolean checkHinhHop = false;

		// hình hộp chữ nhật có 6 mặt đều là hình chữ nhật
		boolean check1 = checkIsHinhChuNhat(item.A, item.B, item.C, item.D);
		boolean check2 = checkIsHinhChuNhat(item.E, item.F, item.G, item.H);
		boolean check3 = checkIsHinhChuNhat(item.A, item.B, item.F, item.E);
		boolean check4 = checkIsHinhChuNhat(item.D, item.C, item.G, item.H);
		boolean check5 = checkIsHinhChuNhat(item.A, item.D, item.H, item.E);
		boolean check6 = checkIsHinhChuNhat(item.B, item.C, item.G, item.F);
		if (check1 && check2 && check3 && check4 && check5 && check6) {
			// trả về true nếu cả 6 mặt đều là hình chữ nhật
			checkHinhHop = true;
		}
		return checkHinhHop;
	}

	/**
	 * Method check vật có nằm trong phòng không không
	 * 
	 * @param items
	 * @param room
	 * @return checkInRom : boolean
	 */

	public boolean checkItemsInRoom(Items items, Room room) {
		boolean checkInRoom = false;
		ValidatePoint validatePoint = new ValidatePoint();
		// 8 điểm đỉnh của vật đều nằm trong không gian hình hộp của phòng
		Coordinates A = items.A;
		Coordinates B = items.B;
		Coordinates C = items.C;
		Coordinates D = items.D;
		Coordinates E = items.E;
		Coordinates F = items.F;
		Coordinates G = items.G;
		Coordinates H = items.H;

		boolean check1 = validatePoint.checkPointInRoom(A, room);
		boolean check2 = validatePoint.checkPointInRoom(B, room);
		boolean check3 = validatePoint.checkPointInRoom(C, room);
		boolean check4 = validatePoint.checkPointInRoom(D, room);
		boolean check5 = validatePoint.checkPointInRoom(E, room);
		boolean check6 = validatePoint.checkPointInRoom(F, room);
		boolean check7 = validatePoint.checkPointInRoom(G, room);
		boolean check8 = validatePoint.checkPointInRoom(H, room);

		if (check1 && check2 && check3 && check4 && check5 && check6 && check7 && check8) {
			checkInRoom = true;
		}

		return checkInRoom;
	}

	/**
	 * Method check đáy của vật có chạm đáy sàn không
	 * 
	 * @param items
	 * @param room
	 * @return
	 */

	public boolean checkDayChamSan(Items items, Room room) {
		boolean checkChamSan = false;
		Distance distance = new Distance();
		// 3 đỉnh đáy của vật thuộc vào mặt sàn của phòng --> khoảng cách đến mặt đáy
		// của 3 điểm = 0
		Coordinates A = items.A;
		Coordinates B = items.B;
		Coordinates C = items.C;
		double dist1 = distance.khoangCachDenMatPhang(A, room.A, room.B, room.C);
		double dist2 = distance.khoangCachDenMatPhang(B, room.A, room.B, room.C);
		double dist3 = distance.khoangCachDenMatPhang(C, room.A, room.B, room.C);
		if (dist1 == 0.0 && dist2 == 0.0 && dist3 == 0.0) {
			checkChamSan = true;
		}
		return checkChamSan;

	}
	
	/**
	 * Method dùng để lấy các lỗi có thể có khi tạo tọa độ của vật
	 * 
	 * @param items
	 * @param room
	 * @return listErr - mảng lỗi
	 */
	
	public List<String> getlistErrOfItems(Items items, Room room){
		List<String> listErr = new ArrayList<String>();
		ValidateItems validateItems = new ValidateItems();
		//nếu không phải hình hộp thì thêm lỗi vào mảng lỗi
		if (!validateItems.checkIsHinhHopChuNhat(items)) {
			listErr.add("Vật không phải hình hộp chữ nhật");
		}
		//nếu không thuộc không gian phòng thì thêm vào mảng lỗi
		if (!validateItems.checkItemsInRoom(items, room)) {
			listErr.add("Vật không thuộc không gian phòng");
		}
		
		if (!validateItems.checkDayChamSan(items, room)) {
			listErr.add("Vật không có đáy chạm sàn");	
		}
		
		return listErr;
	}

}
