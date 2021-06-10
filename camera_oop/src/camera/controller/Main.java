package camera.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import camera.dao.XuLiXauToaDo;
import camera.entity.Camera;
import camera.entity.Coordinates;
import camera.entity.Items;
import camera.entity.Room;
import camera.logic.DiemDuocChieuSang;
import camera.logic.DiemThuocVatThe;
import camera.logic.Distance;
import camera.logic.HinhChieuToi;
import camera.logic.PhanTach;
import camera.logic.Volume;
import camera.validate.ValidateCamera;
import camera.validate.ValidateItems;
import camera.validate.ValidatePoint;
import camera.validate.ValidateRoom;

public class Main {

	/**
	 * hàm thực hiện chính
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		// Khai báo và khởi tạo một số biến dùng chung cho các case
		Scanner input = new Scanner(System.in);
		boolean cont = true;
		Room room = null;
		int numberItems = 0;
		int numberCameras = 0;
		List<Items> listItems = new ArrayList<Items>();
		List<Camera> listCameras = new ArrayList<Camera>();
		ValidateRoom validateRoom = new ValidateRoom();
		ValidateItems validateItems = new ValidateItems();
		ValidateCamera validateCamera = new ValidateCamera();
		DiemThuocVatThe diemThuocVatThe = new DiemThuocVatThe();
		DiemDuocChieuSang diemDuocChieuSang = new DiemDuocChieuSang();
		List<Coordinates> listPointOfRoom = new ArrayList<Coordinates>();
		List<Coordinates> listDiermThuocVatThe = new ArrayList<Coordinates>();
		List<Coordinates> listDiemDuocChieuSang = new ArrayList<Coordinates>();
		List<Coordinates> listDiemKhongDuocChieuSang = new ArrayList<Coordinates>();
		List<Coordinates> listHinhChieuToi = new ArrayList<Coordinates>();
		ValidatePoint validatePoint = new ValidatePoint();
		Distance distance = new Distance();
		Volume volume = new Volume();
		HinhChieuToi hinhChieuToi = new HinhChieuToi();
		Coordinates toado ;
		// biến check có lỗi hay không
		boolean isHasErr = false;
		
		
		do {
			System.out.println("----------------Menu------------------");
			System.out.println("1. a/Lập phòng");
			System.out.println("2. b/Gắn camera");
			System.out.println("3. c/Tính vùng khuất");
			System.out.println("4. c/Kiểm tra 1 điểm bất kỳ được chiếu sáng hay không");
			System.out.println("5. d/Hiển thị vùng khuất");
			System.out.println("6. e/Xác định số camera tối thiểu");
			System.out.println("7. f/Xác định vị trí camera để ít góc khuất nhất");
			System.out.println("8. Chọn nút khác để Exit chương trình");
			System.out.println("Chọn chức năng : [1- 7]");

			int chon = input.nextInt();
			switch (chon) {
			case 1:
				System.out.println("Thực hiện chức năng 1 - Lập phòng");
				try {
					JFileChooser jFileChooser = new JFileChooser();
					int rVal = jFileChooser.showOpenDialog(null);
					String filename = null;
					String dir = null;
					String filePath = null;
					if (rVal == JFileChooser.APPROVE_OPTION) {
					     filename = jFileChooser.getSelectedFile().getName();
					     dir = jFileChooser.getCurrentDirectory().toString();
					     filePath = dir+"\\"+filename;
					 }
					System.out.println(filePath);
					//Bước 1: Tạo đối tượng luồng và liên kết nguồn dữ liệu
					File f = new File(filePath);
					FileReader fr = new FileReader(f);
					// Bước 2: Đọc dữ liệu
					BufferedReader br = new BufferedReader(fr);
					String line;
					int countLine = 1;
					while ((line = br.readLine()) != null) {
						XuLiXauToaDo xuLiXauToaDo = new XuLiXauToaDo();
						if (countLine == 1) {
							room = xuLiXauToaDo.getCoordinatesRoom(line);

						}
						// đọc dòng chứa số vật thể
						if (countLine == 2) {
							numberItems = Integer.parseInt(line);
						}

						if (countLine > 2 && countLine <= 2 + numberItems) {
							Items oneItem = xuLiXauToaDo.getCoordinatesItems(line);
							listItems.add(oneItem);
						}

						// đọc dòng chứa số camera
						if (countLine == 2 + numberItems + 1) {
							numberCameras = Integer.parseInt(line);
						}
						if (countLine > 2 + numberItems + 1) {
							Camera oneCamera = xuLiXauToaDo.getInforCamera(line);
							listCameras.add(oneCamera);
						}
						countLine++;

					}
					// Bước 3: Đóng luồng đọc file
					fr.close();
					br.close();
				} catch (Exception e) {
					// bắn ngoại lệ đọc file
					System.out.println("Loi doc file: " + e);
				}

				// check điều kiện phòng
				List<String> listErrRoomList = validateRoom.getListErrOfRoom(room);
				if (listErrRoomList.size() > 0) {
					// đặt cờ hiệu là có lỗi
					isHasErr = true;
					System.out.println(listErrRoomList);
				}
				// check điều kiện của danh sách đồ vật
				for (int i = 0; i < listItems.size(); i++) {
					List<String> listErrItems = validateItems.getlistErrOfItems(listItems.get(i), room);
					if (listErrItems.size() > 0) {
						// cờ hiệu là có lỗi
						isHasErr = true;
						System.out.println("Vật thứ " + i + " - Lỗi: " + listErrItems);
					}

				}
				if (!isHasErr) {
					System.out.println("Tạo phòng thành công");
					//tính thể tích phòng
					room.thetich = volume.calculateVolumeRoom(room);
				} else {
					System.out.println("Tạo phòng thất bại");
				}

				break;
			case 2:
				System.out.println("Thực hiện chức năng 2- Check Camera hợp lệ");

				if (isHasErr) {
					isHasErr = false;
				}
				// check điều kiện của danh sách camera
				for (int i = 0; i < listCameras.size(); i++) {
					List<String> listErrCamera = validateCamera.getErrOfCamera(listCameras.get(i), room);
					if (listErrCamera.size() > 0) {
						System.out.println("Camera thứ " + i + " - Lỗi: " + listErrCamera);
						isHasErr = true;
					}

				}

				if (isHasErr) {
					System.err.println("Gắn camera không thành công");

				} else {
					System.out.println("Gắn camera thành công");
				}

				break;
			case 3:
				// test
				System.out.println("Thực hiện chức năng 3 - Tính toán vùng sáng vùng tối");
				
				//chia phòng thành các điểm
				PhanTach phanTach = new PhanTach();
				listPointOfRoom = phanTach.tachPhongThanhCacDiem(room, 0.1); //rate 0,1
				System.out.println("Tổng số điểm trong phòng: " +listPointOfRoom.size());
				
				//lấy danh sách các điểm thuộc vật thể
				listDiermThuocVatThe = diemThuocVatThe.getListDiemThuocVatThe(listPointOfRoom, listItems);
				//loại bớt các điểm đã thuộc vật thể
				listPointOfRoom.removeAll(listDiermThuocVatThe);
				//lấy danh sách các điểm thuộc vùng chiếu sáng
				listDiemDuocChieuSang = diemDuocChieuSang.getListDiemDuocChieuSang(listPointOfRoom, room, listCameras, listItems);
				System.out.println("Tổng số điểm được chiếu sáng: " +listDiemDuocChieuSang.size());
				listPointOfRoom.addAll(listDiermThuocVatThe);
				//List<String> list2 = list1.stream().collect(Collectors.toList());
				listDiemKhongDuocChieuSang = listPointOfRoom.stream().collect(Collectors.toList());
				listDiemKhongDuocChieuSang.removeAll(listDiemDuocChieuSang);
				System.out.println("Tổng số điểm không được chiếu sáng: " +listDiemKhongDuocChieuSang.size());
				double phantramVungToi = (listDiemKhongDuocChieuSang.size())*100/ listPointOfRoom.size();
				System.out.println("Phần trăm vùng tối :" + phantramVungToi);
				
				break;
			case 5:
				System.out.println("Thực hiện chức năng 4");
				break;
			case 4:
				System.out.println("Thực hiện chức năng 5 - Kiểm tra một điểm nhập vào có được chiếu sáng không");
				double a, b, c;
				Scanner sc = new Scanner(System.in);
				System.out.print("Nhập tọa độ Ox của điểm: "); // print thay vì println, nó sẽ in ra, nhưng không xuống
																// dòng

				a = sc.nextDouble(); // sc.nextInt() là cách để lấy giá trị từ bàn phím, nó sẽ chờ tới khi chúng ta
										// nhập một số.
				System.out.print("Nhập tọa độ Oy của điểm: ");
				b = sc.nextDouble();
				System.out.print("Nhập tọa độ Oz của điểm: ");
				c = sc.nextDouble();
				Coordinates Z = new Coordinates(a, b, c);
				// tìm điểm gần nhất trong tập điểm
				double minDistance = 10000;
				int index = 0;
				for (int i = 0; i < listPointOfRoom.size(); i++) {
					double distcaneOf2Point = distance.calculateDistance(Z, listPointOfRoom.get(i));
					if (distcaneOf2Point < minDistance) {
						index = i;
						minDistance = distcaneOf2Point;
					}
				}
				
				Z = listPointOfRoom.get(index);
				///////////
				if (!validatePoint.checkPointInRoom(Z, room)) {
					System.out.println("Điểm không thuộc phòng");
				} else {
					boolean check1 = false;
					for (int i = 0; i < listItems.size(); i++) {
						if (validatePoint.checkPointInVatThe(Z, listItems.get(i))) {
							System.out.println("Điểm thuộc vật thể có tọa độ "+listItems.get(i));
							check1 = true;
						}
					}
					if (!check1) {
						if (validatePoint.checkDiemThuocVungSangCuaNhieuCamera(Z, listCameras, room, listItems)) {
							System.out.println("Điểm được chiếu sáng");
						} else {
							System.out.println("Điểm không được chiếu sáng");
						}
					}
				}
				break;
			case 6:
				System.out.println("Thực hiện chức năng 6");
				//vẽ ở đây
				//lấy ra list các điểm
				//lấy ra tọa độ ABCD
				
				//vẽ theo tọa độ
				listHinhChieuToi = hinhChieuToi.getListHinhChieuTuTuongTrenXuong(listDiemKhongDuocChieuSang, room);
				for(int i=0; i < listHinhChieuToi.size(); i++) {
					toado = listHinhChieuToi.get(i);
					System.out.println("x : " + toado.x + "  y : " + toado.y);
				}
				
				DrawingRectangleExample draw = new DrawingRectangleExample(listHinhChieuToi);
				draw.setVisible(true);
				
				DrawingRectangleExample draw1 = new DrawingRectangleExample(listHinhChieuToi);
				draw1.setVisible(true);
				
				DrawingRectangleExample draw2 = new DrawingRectangleExample(listHinhChieuToi);
				draw2.setVisible(true);
				
				DrawingRectangleExample draw3 = new DrawingRectangleExample(listHinhChieuToi);
				draw3.setVisible(true);
				
				break;
			case 7:
				System.out.println("Thực hiện chức năng 7");
				break;
			default:
				System.out.println("Tạm biệt");
				cont = false;
				break;
			}
		} while (cont);

	}

}
