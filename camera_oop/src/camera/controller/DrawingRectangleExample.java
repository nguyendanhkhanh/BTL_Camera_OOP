package camera.controller;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JFrame;

import camera.entity.Coordinates;

public class DrawingRectangleExample extends JFrame{

	public List<Coordinates> coordinates = new ArrayList<Coordinates>();
	public DrawingRectangleExample() {
		getContentPane().setBackground(Color.WHITE);
        setSize(2000, 2000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        coordinates.add(new Coordinates(1, 0, 1));
        coordinates.add(new Coordinates(2, 0, 1));
	}
	
	public DrawingRectangleExample(List<Coordinates> coordinates) {
		getContentPane().setBackground(Color.YELLOW);
        setSize(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.coordinates.clear();
        this.coordinates.addAll(coordinates);
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		//draw rect
		//0, 0, 0) (8, 0, 0) (8, 4, 0) (0, 4, 0)
		//kich thuoc la 8 4 thi x 100 la 800 400 con gi b
//		Graphics2D g2 = (Graphics2D) g;
//		g2.setStroke(new BasicStroke(2f));
		g.setColor(Color.BLACK);
		g.drawRect(40, 40, 800, 400);;
		
		
		//draw point
		g.setColor(Color.BLACK);
		for(Coordinates c : coordinates) {
			System.out.println(c.x + "  y " + c.y);
			g.fillOval((int) ((c.x )*100 + 40),(int) ((c.y) * 100) + 40,20,20);
		}
		
		
	}
	
	public static void main(String[] args) {
		DrawingRectangleExample drawingRectangleExample = new DrawingRectangleExample();
		drawingRectangleExample.setVisible(true);
	}

}
