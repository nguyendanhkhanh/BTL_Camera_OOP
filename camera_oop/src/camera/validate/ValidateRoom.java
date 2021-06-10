package camera.validate;

import java.util.ArrayList;
import java.util.List;

import camera.entity.Coordinates;
import camera.entity.Room;

public class ValidateRoom {
	
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
	 * Method check phòng có phải hình hộp chữ nhật không
	 * 
	 * @param room
	 * @return checkHinhHop : boolean
	 */

	public boolean checkIsHinhHopChuNhat(Room room) {
		boolean checkHinhHop = false;

		// hình hộp chữ nhật có 6 mặt đều là hình chữ nhật
		boolean check1 = checkIsHinhChuNhat(room.A, room.B, room.C, room.D);
		boolean check2 = checkIsHinhChuNhat(room.E, room.F, room.G, room.H);
		boolean check3 = checkIsHinhChuNhat(room.A, room.B, room.F, room.E);
		boolean check4 = checkIsHinhChuNhat(room.D, room.C, room.G, room.H);
		boolean check5 = checkIsHinhChuNhat(room.A, room.D, room.H, room.E);
		boolean check6 = checkIsHinhChuNhat(room.B, room.C, room.G, room.F);
		if (check1 && check2 && check3 && check4 && check5 && check6) {
			// trả về true nếu cả 6 mặt đều là hình chữ nhật
			checkHinhHop = true;
		}
		return checkHinhHop;
	}
	
	
	/**
	 * Method lấy về những lỗi có thể có khi nhập vào thông tin phòng
	 * 
	 * @param room
	 * @return listErr : danh sách lỗi
	 */
	
	public List<String>  getListErrOfRoom(Room room){
		List<String> listErr = new ArrayList<String>();
		ValidateRoom validateRoom = new ValidateRoom();
		if (!validateRoom.checkIsHinhHopChuNhat(room)) {
			listErr.add("Phòng không phải hình hộp chữ nhật");
		}
		
		return listErr;
	}


}
