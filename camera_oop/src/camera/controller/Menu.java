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


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
 
public class Menu {
 
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
 
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
    
    public Menu() {
        prepareGUI();
    }
 
    public static void main(String[] args) {
        Menu demo = new Menu();
        demo.showDialogDemo();
    }
 
    private void prepareGUI() {
        mainFrame = new JFrame("Menu chức năng");
        mainFrame.setSize(500, 400);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }
 
    public void showDialogDemo() {
    	
        headerLabel.setText("Chọn chức năng");
        JButton creatRoom = new JButton("a. Lập phòng");
        JButton creatCamera = new JButton("b. Gắn camera");
        JButton calculateArea = new JButton("c. Tính toán vùng sáng tối");
        JButton checkPoint = new JButton("c. Kiểm tra điểm nằm trong vùng nào");
        JButton showButton = new JButton("d. Show hình chiếu vùng tối");
        creatRoom.addActionListener(new ActionListener() {
        		 public void actionPerformed(ActionEvent e) {
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
			} catch (Exception noti) {
				// bắn ngoại lệ đọc file
				System.out.println("Loi doc file: " + noti);
				JOptionPane.showMessageDialog(mainFrame, "Loi doc file: " + noti);
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
					JOptionPane.showMessageDialog(mainFrame, 
							"Vật thứ " + i + " - Lỗi: " + listErrItems);
				}

			}
			if (!isHasErr) {
				//System.out.println("Tạo phòng thành công");
				JOptionPane.showMessageDialog(mainFrame, 
                        "Tạo phòng thành công");
				//tính thể tích phòng
				room.thetich = volume.calculateVolumeRoom(room);
			} else {
				//System.out.println("Tạo phòng thất bại");
				JOptionPane.showMessageDialog(mainFrame, 
                        "Tạo phòng thất bại");
			}
        }

        });
          
            
        creatCamera.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
					JOptionPane.showMessageDialog(mainFrame, 
	                        "Gắn camera không thành công");

				} else {
					System.out.println("Gắn camera thành công");
					JOptionPane.showMessageDialog(mainFrame, 
	                        "Gắn camera thành công");
				}
            }
        });
        calculateArea.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Thực hiện chức năng 3 - Tính toán vùng sáng vùng tối");
				
				//chia phòng thành các điểm
				PhanTach phanTach = new PhanTach();
				listPointOfRoom = phanTach.tachPhongThanhCacDiem(room, 0.1); //rate 0,1
				System.out.println("Tổng số điểm trong phòng: " +listPointOfRoom.size());
				JOptionPane.showMessageDialog(mainFrame, 
						"Tổng số điểm trong phòng: " +listPointOfRoom.size());
				
				//lấy danh sách các điểm thuộc vật thể
				listDiermThuocVatThe = diemThuocVatThe.getListDiemThuocVatThe(listPointOfRoom, listItems);
				//loại bớt các điểm đã thuộc vật thể
				listPointOfRoom.removeAll(listDiermThuocVatThe);
				//lấy danh sách các điểm thuộc vùng chiếu sáng
				listDiemDuocChieuSang = diemDuocChieuSang.getListDiemDuocChieuSang(listPointOfRoom, room, listCameras, listItems);
				System.out.println("Tổng số điểm được chiếu sáng: " +listDiemDuocChieuSang.size());
				JOptionPane.showMessageDialog(mainFrame,"Tổng số điểm được chiếu sáng: " +listDiemDuocChieuSang.size());
				
				listPointOfRoom.addAll(listDiermThuocVatThe);
				//List<String> list2 = list1.stream().collect(Collectors.toList());
				listDiemKhongDuocChieuSang = listPointOfRoom.stream().collect(Collectors.toList());
				listDiemKhongDuocChieuSang.removeAll(listDiemDuocChieuSang);
				System.out.println("Tổng số điểm không được chiếu sáng: " +listDiemKhongDuocChieuSang.size());
				JOptionPane.showMessageDialog(mainFrame,
						"Tổng số điểm không được chiếu sáng: " +listDiemKhongDuocChieuSang.size());
				
				double phantramVungToi = (listDiemKhongDuocChieuSang.size())*100/ listPointOfRoom.size();
				System.out.println("Phần trăm vùng tối :" + phantramVungToi);
				JOptionPane.showMessageDialog(mainFrame,"Phần trăm vùng tối :" + phantramVungToi);
            }
        });
        
        checkPoint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        
        controlPanel.add(creatRoom);
        controlPanel.add(creatCamera);
        controlPanel.add(calculateArea);
        controlPanel.add(showButton);
        controlPanel.add(checkPoint);
        mainFrame.setVisible(true);
    }
}