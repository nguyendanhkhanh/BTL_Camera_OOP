package camera.dao;

import camera.entity.Camera;
import camera.entity.Coordinates;
import camera.entity.Items;
import camera.entity.Room;

public class XuLiXauToaDo {

	/**
	 * Method đọc mảng chuỗi để trích xuất tọa độ và trả về 1 đối tượng Room
	 * 
	 * @param inputString
	 * @return room : Room
	 */
	
	public Room getCoordinatesRoom(String inputString) {
		//String[] arrOfStr = inputString.split("\\)");
		String[] arrOfStr = inputString.split("[\\)]");
		
		//khởi tạo mảng 8 điểm lưu tọa độ 8 đỉnh của hình hộp
		Coordinates [] arrCoordinates = new Coordinates[8];
		for(int i = 0; i < arrOfStr.length; i++) {
			String  oneCoordinatesString = arrOfStr[i].replace("(", "");
			String  [] array = oneCoordinatesString.split(",");
			double xCoordinates = Double.parseDouble(array[0]);
			double yCoordinates = Double.parseDouble(array[1]);
			double zCoordinates = Double.parseDouble(array[2]);
			Coordinates item = new Coordinates(xCoordinates, yCoordinates, zCoordinates);
			arrCoordinates[i] = item;
		}
		
		Coordinates pointA = arrCoordinates[0];
		Coordinates pointB = arrCoordinates[1];
		Coordinates pointC = arrCoordinates[2];
		Coordinates pointD = arrCoordinates[3];
		Coordinates pointE = arrCoordinates[4];
		Coordinates pointF = arrCoordinates[5];
		Coordinates pointG = arrCoordinates[6];
		Coordinates pointH = arrCoordinates[7];
		
		Room room = new Room(pointA, pointB, pointC, pointD, pointE, pointF, pointG, pointH);
		
		return room;
	}
	
	
	
	
	/**
	 * Method đọc và trích xuất tọa độ của 1 vật hình hộp
	 * 
	 * @param inputString
	 * @return item : Items
	 */
	
	public Items getCoordinatesItems(String inputString) {
		//String[] arrOfStr = inputString.split("\\)");
		String[] arrOfStr = inputString.split("[\\)]");
		
		//khởi tạo mảng 8 điểm lưu tọa độ 8 đỉnh của hình hộp
		Coordinates [] arrCoordinates = new Coordinates[8];
		for(int i = 0; i < arrOfStr.length; i++) {
			String  oneCoordinatesString = arrOfStr[i].replace("(", "");
			String  [] array = oneCoordinatesString.split(",");
			double xCoordinates = Double.parseDouble(array[0]);
			double yCoordinates = Double.parseDouble(array[1]);
			double zCoordinates = Double.parseDouble(array[2]);
			Coordinates item = new Coordinates(xCoordinates, yCoordinates, zCoordinates);
			arrCoordinates[i] = item;
		}
		
		Coordinates pointA = arrCoordinates[0];
		Coordinates pointB = arrCoordinates[1];
		Coordinates pointC = arrCoordinates[2];
		Coordinates pointD = arrCoordinates[3];
		Coordinates pointE = arrCoordinates[4];
		Coordinates pointF = arrCoordinates[5];
		Coordinates pointG = arrCoordinates[6];
		Coordinates pointH = arrCoordinates[7];
		
		Items items = new Items(pointA, pointB, pointC, pointD, pointE, pointF, pointG, pointH);
		
		return items;
	}
	
	/**
	 * Method đọc, trích xuất 1 đối tượng camera từ xâu có định dạng
	 * 
	 * @param inputString
	 * @return
	 */
	
	public Camera getInforCamera(String inputString) {
		
		String[] arrOfStr = inputString.split("[\\)]");
		String oneCoordinatesString = arrOfStr[0].replace("(", "");
		String  [] array = oneCoordinatesString.split(",");
		double xCoordinates = Double.parseDouble(array[0]);
		double yCoordinates = Double.parseDouble(array[1]);
		double zCoordinates = Double.parseDouble(array[2]);
		Coordinates toadoCamera = new Coordinates(xCoordinates, yCoordinates, zCoordinates);
		//lấy về string các góc rộng và cao
		String[] strGoc = arrOfStr[1].split(" ");
		double gocRong = Integer.parseInt(strGoc[1]);
		double gocCao = Integer.parseInt(strGoc[2]);
		
		Camera camera = new Camera(toadoCamera, gocRong, gocCao);
		return camera;
		
	}
	
}
