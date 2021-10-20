import java.awt.*;

public class Arc
{
	private Point start;
	private Point end;
	private java.lang.Object Color;
	private Node endNode;
	private Node startNode;
	private Boolean TwoWayLine;

	public Boolean getTwoWayLine() {
		return TwoWayLine;
	}
	public void setTwoWayLine(Boolean twoWayLine) {
		TwoWayLine = twoWayLine;
	}
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}
	public Node getStartNode() {
		return startNode;
	}
	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}
	public Node getEndNode() {
		return endNode;
	}

	public Point getStart() {
		return start;
	}

	public Point getEnd() {
		return end;
	}

	public void setStart(int x,int y) {
		this.start.x = x;
		this.start.y=y;
	}

	public void setEnd(int x,int y) {
		this.end.x = x;
		this.end.y=y;

	}

	public Arc(Point start, Point end)
	{
		this.start = start;
		this.end = end;
	}

	public Point getPointStart()
	{
		return start;
	}
	public Point getPointEnd()
	{
		return end;
	}


	public void drawArc(Graphics g,Node start,Node end)
	{
		if (start != null)
		{
            g.setColor(java.awt.Color.RED);
            g.drawLine(start.getMiddleX(), start.getMiddleY(), end.getMiddleX(), end.getMiddleY());
        }
	}

	public void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx*dx + dy*dy);
		double xm = D - d, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;

		x = xm*cos - ym*sin + x1;
		ym = xm*sin + ym*cos + y1;
		xm = x;

		x = xn*cos - yn*sin + x1;
		yn = xn*sin + yn*cos + y1;
		xn = x;

		int[] xpoints = {x2, (int) xm, (int) xn};
		int[] ypoints = {y2, (int) ym, (int) yn};
		g.setColor(java.awt.Color.RED);
		g.drawLine(x1, y1, x2, y2);
		g.fillPolygon(xpoints, ypoints, 3);
	}

}
