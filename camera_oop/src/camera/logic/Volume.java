package camera.logic;

import camera.entity.Coordinates;
import camera.entity.Items;
import camera.entity.Room;

public class Volume {
	
	/**
	 * Method dùng để tính thể tích của phòng
	 * 
	 * @param room
	 * @return
	 */
	
	public double calculateVolumeRoom(Room room) {
		double volumeOfRoom = 0;
		Distance distance = new Distance();
		double AB = distance.calculateDistance(room.A, room.B);
		double AD = distance.calculateDistance(room.A, room.D);
		double AE = distance.calculateDistance(room.A, room.E);
		
		volumeOfRoom = AB*AD*AE;
		
		return volumeOfRoom;
	}
	
	/**
	 * Method dùng để tính thể tích của vật thể hình hộp
	 * 
	 * @param items
	 * @return
	 */
	
	public double calculateVolumeItems(Items items) {
		double volumeOfItem = 0;
		Distance distance = new Distance();
		double AB = distance.calculateDistance(items.A, items.B);
		double AD = distance.calculateDistance(items.A, items.D);
		double AE = distance.calculateDistance(items.A, items.E);
		
		volumeOfItem = AB*AD*AE;
		
		return volumeOfItem;
	}
	
	/**
	 * Method tính thể tích của hình chóp đáy là hình chữ nhật
	 * 
	 * @param M - điểm cần tính khoảng cách 
	 * @param A  
	 * @param B
	 * @param C
	 * @param D
	 * @return thetich : double
	 */
	
	public double tinhTheTichHinhChopTuGiac(Coordinates M, Coordinates A, Coordinates B, Coordinates C, Coordinates D) {
		double thetich = 0.0;
		Distance distance = new Distance();
		double AB = distance.calculateDistance(A, B);
		double AD = distance.calculateDistance(A, D); 
		double MH = distance.khoangCachDenMatPhang(M, A, B, C);
		thetich = (AB*AD*MH)/3;
		return thetich;
		
	}
	
	/**
	 * Method dùng để tính thể tích hình chóp N.IHK
	 * 
	 * @param N
	 * @param I
	 * @param H
	 * @param K
	 * @return thetich : thể tích hình chóp
	 */
	
	public double tinhTheTichHinhChopTamGiac(Coordinates N, Coordinates I, Coordinates H, Coordinates K) {
		TichCoHuong tichCoHuong = new TichCoHuong();
		Distance distance = new Distance();
		double dientich = tichCoHuong.layDoLonCuaTichCoHuong(I, H, K)*0.5;
		double chieucao = distance.khoangCachDenMatPhang(N, I, H, K);
		double thetich = (dientich*chieucao)/3;
		return thetich;
	}
   
}
