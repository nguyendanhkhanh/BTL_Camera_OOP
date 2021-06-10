package camera.entity;

public class Camera {
   // các thuộc tính của camera bao gồm tọa độ, góc rộng, góc cao
	public Coordinates toadoCamera;
	
	public double gocRong;
	
	public double gocCao;

	public Camera(Coordinates toadoCamera, double gocRong, double gocCao) {
		super();
		this.toadoCamera = toadoCamera;
		this.gocRong = gocRong;
		this.gocCao = gocCao;
	}

	
	
	
	@Override
	public String toString() {
		return "Camera [toadoCamera=" + toadoCamera + ", gocRong=" + gocRong + ", gocCao=" + gocCao + "]";
	}
	
	

}
