package camera.logic;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.x500.X500Principal;

import camera.entity.Coordinates;
import camera.entity.Room;

public class PhanTach {
	
	/**
	 * Method dùng để chia phòng thành các điểm nhỏ theo sai số
	 * 
	 * @param room
	 * @return
	 */
	
	public List<Coordinates> tachPhongThanhCacDiem(Room room, double saiso){
		List<Coordinates> listPointOfRoom = new ArrayList<Coordinates>();
		double x = room.A.x;

		while (x < room.B.x) {
			double y = room.A.y;
			while ( y < room.D.y) {
				double z = room.A.z;
				while (z < room.E.z) {
					Coordinates tempPoint = new Coordinates(x, y, z);
					listPointOfRoom.add(tempPoint);
					z+=saiso;
					
				}
				y+=saiso;
			}
			x+=saiso;
			
		}
		System.out.println("Chia phong xong");
		return listPointOfRoom;
	}

}
