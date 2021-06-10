package camera.logic;

import java.util.ArrayList;
import java.util.List;

import camera.entity.Camera;
import camera.entity.Coordinates;
import camera.entity.Items;
import camera.entity.Room;
import camera.validate.ValidatePoint;

public class DiemDuocChieuSang {

	/**
	 * Method dùng để lấy danh sách các điểm thuộc vùng chiếu sáng, từ các điểm thuộc phòng và không thuộc vật thể
	 * 
	 * @param listPoint
	 * @param room
	 * @param listCameras
	 * @param listItems
	 * @return
	 */
	
	public List<Coordinates> getListDiemDuocChieuSang(List<Coordinates> listPoint, Room room, List<Camera> listCameras,
			List<Items> listItems) {
		ValidatePoint validatePoint = new ValidatePoint();
		List<Coordinates> listDiemDuocChieuSang = new ArrayList<Coordinates>();
		for (int i = 0; i < listPoint.size(); i++) {
			if (validatePoint.checkDiemThuocVungSangCuaNhieuCamera(listPoint.get(i), listCameras, room, listItems)) {
				listDiemDuocChieuSang.add(listPoint.get(i));
			}
		}
		return listDiemDuocChieuSang;
	}

}
