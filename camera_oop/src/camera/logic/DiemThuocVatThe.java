package camera.logic;

import java.util.ArrayList;
import java.util.List;

import camera.entity.Coordinates;
import camera.entity.Items;
import camera.validate.ValidatePoint;

public class DiemThuocVatThe {
	
	/**
	 * Method dùng để láy danh sách các điểm thuộc một vật thể nào đó có trong phòng
	 * 
	 * @param listPoint
	 * @param listItems
	 * @return listDiemThuocVatThe
	 */
	
	public List<Coordinates> getListDiemThuocVatThe(List<Coordinates> listPoint, List<Items> listItems){
		List<Coordinates> listDiemThuocVatThe = new ArrayList<Coordinates>();
		ValidatePoint validatePoint = new ValidatePoint();
		for (int i = 0; i < listPoint.size(); i++) {
			for (int j = 0; j < listItems.size(); j++) {
				if (validatePoint.checkPointInVatThe(listPoint.get(i), listItems.get(j))) {
					listDiemThuocVatThe.add(listPoint.get(i));
				}
			}
		}
		return listDiemThuocVatThe;
	}

}
