package camera.validate;

import java.util.ArrayList;
import java.util.List;

import camera.entity.Camera;
import camera.entity.Coordinates;
import camera.entity.Room;
import camera.logic.Distance;

public class ValidateCamera {

	/**
	 * Method check camera thuộc không gian phòng
	 * 
	 * @param camera
	 * @param room
	 * @return checkInRoom
	 */

	boolean checkCameraInRoom(Camera camera, Room room) {
		boolean checkInRoom = false; 
		boolean checkPointInRoom = false;
		ValidatePoint validatePoint = new ValidatePoint();
		Coordinates toaDoCamera = camera.toadoCamera;

		checkPointInRoom = validatePoint.checkPointInRoom(toaDoCamera, room);
		if ( checkPointInRoom ) {
			checkInRoom = true;
		}
		return checkInRoom;
	}

	/**
	 * Method dùng để kiểm tra camera có nằm trên tường không
	 * 
	 * @param camera
	 * @param room
	 * @return checkGanTrenTuong : boolean
	 */

	boolean checkCameraGanTrenTuong(Camera camera, Room room) {
		boolean checkGanTrenTuong = false;
		Coordinates toaDoCamera = camera.toadoCamera;
		Distance distance = new Distance();
		// khoảng cách đến bốn mặt tường
		double dist1 = distance.khoangCachDenMatPhang(toaDoCamera, room.A, room.B, room.E);
		double dist2 = distance.khoangCachDenMatPhang(toaDoCamera, room.B, room.C, room.F);
		double dist3 = distance.khoangCachDenMatPhang(toaDoCamera, room.D, room.C, room.H);
		double dist4 = distance.khoangCachDenMatPhang(toaDoCamera, room.A, room.D, room.E);

		// khoảng cách đến trần nhà EFGH
		double dist5 = distance.khoangCachDenMatPhang(toaDoCamera, room.E, room.F, room.G);
		// check vật gắn trên tường hoặc trần nhà:
		if (dist1 == 0 || dist2 == 0 || dist3 == 0 || dist4 == 0 || dist5 == 0) {
			checkGanTrenTuong = true;
		}

		return checkGanTrenTuong;
	}
	
	/**
	 * Method check góc rộng của camera > 90
	 * 
	 * @param camera
	 * @return
	 */
	
	boolean checkGocRong(Camera camera) {
		boolean checkGocRong = false;
		if ( camera.gocRong <= 90 && camera.gocRong > 0) {
			checkGocRong = true;
		}
		return checkGocRong;
	}
	
	
	boolean checkGocCao(Camera camera ) {
		boolean checkGocCao = false;
		if (camera.gocCao <= 90 && camera.gocCao > 0) {
			checkGocCao = true;
		}
		return checkGocCao;
	}
	
	public List<String> getErrOfCamera(Camera camera, Room room){
		List<String> listErr = new ArrayList<String>();
		ValidateCamera validateCamera = new ValidateCamera();
		if(!validateCamera.checkCameraInRoom(camera, room)) {
			listErr.add("Camera không thuộc phòng");
		}
		if (!validateCamera.checkCameraGanTrenTuong(camera, room)) {
			listErr.add("Camera không được gắn trên tường hoặc trần nhà");
		}
		if (!validateCamera.checkGocRong(camera)) {
			listErr.add("Camera có góc rộng ngoài khoảng [0, 90]");
		}
		if (!validateCamera.checkGocCao(camera)) {
			listErr.add("Camera có góc cao ngoài khoảng [0, 90]");
		}
		return listErr;
	}

}
