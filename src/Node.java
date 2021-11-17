import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Node
{
	private int coordX;
	private int coordY;
	private int number;
	private Boolean selected=false;
	private float r;
	private float gr;
	private float b;
	public Node(int coordX, int coordY, int number)
	{
		this.coordX = coordX;
		this.coordY = coordY;
		this.number = number;
	}

	public void setR(float r) {
		this.r = r;
	}
	public void setG(float g) {
		this.gr = g;
	}
	public void setB(float b) {
		this.b = b;
	}
	public float getR() {
		return r;
	}
	public float getG() {
		return gr;
	}
	public float getB() {
		return b;
	}
	public int getMiddleX(){
		return (this.getCoordX()*2+30)/2;
	}
	public int getMiddleY(){
		return (this.getCoordY()*2+30)/2;
	}
	public int getCoordX() {
		return coordX;
	}
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}
	public int getCoordY() {
		return coordY;
	}
	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public void drawNode(Graphics g, int node_diam)
	{
		g.setColor(new Color(r,gr,b));//fill color

		g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.fillOval(coordX, coordY, node_diam, node_diam);

        g.setColor(Color.blue);//content color
        g.drawOval(coordX, coordY, node_diam, node_diam);

        if(number < 10)
        	g.drawString(((Integer)number).toString(), coordX+13, coordY+20);
        else
        	g.drawString(((Integer)number).toString(), coordX+8, coordY+20);	
	}
}
