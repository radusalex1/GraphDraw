import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MyPanel extends JPanel {

	private int nodeNr = 1;
	private int node_diam = 30;
	private Node Selected;
	private Vector<Node> listaNoduri;
	private Vector<Arc> listaArce;

	Point pointStart = null;
	Point pointEnd = null;

	boolean isDragging = false;
	boolean moving=false;

	public MyPanel()
	{
		listaNoduri = new Vector<Node>();
		listaArce = new Vector<Arc>();

		// borderul panel-ului
		setBorder(BorderFactory.createLineBorder(Color.black));

		addMouseListener(new MouseAdapter()
		{
			//evenimentul care se produce la apasarea mousse-ului
			public void mousePressed(MouseEvent e)
			{
				pointStart = e.getPoint();
				if(e.getClickCount()==2 && !e.isConsumed() && moving==false)
				{
					for(Node n:listaNoduri)
					{
						if(ReturnDistancePoints(pointStart.x,pointStart.y,n.getMiddleX(),n.getMiddleY())<=15)
						{
							n.setSelected(true);
							moving=true;
							//Selected=n;
							//System.out.println(Selected.getSelected()+" "+ n.getSelected());
							//System.out.println(moving +" "+ n.getNumber());
						}
					}
				}
				if(e.getButton()==MouseEvent.BUTTON3 && moving ==true)
				{
					moving=false;
					for(Node n:listaNoduri)
					{
						n.setSelected(false);
						//System.out.println(n.getNumber()+" "+n.getSelected());
					}
				}
			}

			//evenimentul care se produce la eliberarea mousse-ului
			public void mouseReleased(MouseEvent e) {

				if (!isDragging)
				{
					if(!CheckForNotOverleaping(e) && moving==false && e.getButton()==MouseEvent.BUTTON1)
					{
						addNode(e.getX(), e.getY());
					}
				}
				else
				{
					if(ICanDrawArc() && moving==false)
					{
						Arc arc = new Arc(pointStart, pointEnd);
						if(e.getButton()==MouseEvent.BUTTON1)
						{
							//System.out.println("left click");
							arc.setTwoWayLine(true);
						}
						if(e.getButton()==MouseEvent.BUTTON3)
						{
							//System.out.println("right click");
							arc.setTwoWayLine(false);
						}

						SearchForStartFinishVertex(arc,pointStart,pointEnd);
						listaArce.add(arc);
					}
				}

				pointStart = null;
				isDragging = false;

				repaint();

			}
		});

		addMouseMotionListener(new MouseMotionAdapter()
		{
			//evenimentul care se produce la drag&drop pe mousse
			public void mouseDragged(MouseEvent e) {
				pointEnd = e.getPoint();
				isDragging = true;
				repaint();
			}
		});
		addMouseMotionListener(new MouseMotionHandler());
	}

	private class MouseMotionHandler extends MouseMotionAdapter {

		Point delta = new Point();
		public void mouseDragged(MouseEvent e)
		{
			Node node=null;
			if(moving)
			{
				for(Node n:listaNoduri)
				{
					if(n.getSelected())
					{
						node=n;
					}
				}
				node.setCoordX(e.getX());
				node.setCoordY(e.getY());
			}
			else
			{
				pointEnd=e.getPoint();
			}
			repaint();
		}
	}

	private double ReturnDistancePoints(double x1,double y1, double x2,double y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}
	private void SearchForStartFinishVertex(Arc arc, Point pointStart, Point pointEnd) {
		for(Node n:listaNoduri)
		{
			double d1 = ReturnDistancePoints(n.getMiddleX(),n.getMiddleY(),arc.getPointStart().x,
					arc.getPointStart().y);
			if(d1<=node_diam/2)
			{
				arc.setStartNode(n);
				arc.setStart(n.getMiddleX(),n.getMiddleY());
			}

			double d2 = ReturnDistancePoints(n.getMiddleX(),n.getMiddleY(),arc.getPointEnd().x,
					arc.getPointEnd().y);

			if(d2<=node_diam/2)
			{
				arc.setEndNode(n);
				arc.setEnd(n.getMiddleX(),n.getMiddleY());
			}
		}
	}
	private boolean ICanDrawArc() {

		double dStartMin=10000;
		double dFinisMin=10000;

		for(Node n: listaNoduri)
		{
			double dStart = ReturnDistancePoints(pointStart.x,pointStart.y,n.getMiddleX(),n.getMiddleY());
			double dFinish = ReturnDistancePoints(pointEnd.x,pointEnd.y,n.getMiddleX(),n.getMiddleY());

			if(dStart<dStartMin)
			{
				dStartMin=dStart;
			}
			if(dFinish<dFinisMin)
			{
				dFinisMin=dFinish;
			}
		}
		return dFinisMin <= 15 && dStartMin <= 15;
	}
	private Boolean CheckForNotOverleaping(MouseEvent e) {

		boolean IsOverlap=false;
		double minDist=10000;

		for (Node n : listaNoduri)
		{
			double Xmiddle = (2*n.getCoordX()+30) /2 ;
			double Ymiddle = (2*n.getCoordY()+30) /2 ;

			double dis=Math. sqrt((e.getX()-Xmiddle)*(e.getX()-Xmiddle) + (e.getY()-Ymiddle)*(e.getY()-Ymiddle));

			if(minDist>dis)
			{
				minDist=dis;
			}

			if(n.getCoordX() == e.getX() && n.getCoordY() == e.getY())
			{
				IsOverlap =true;
			}
		}
		if(minDist<55)
		{
			IsOverlap=true;
		}
		return IsOverlap;

	}

	//metoda care se apeleaza la eliberarea mouse-ului
	private void addNode(int x, int y)
	{
		Node node = new Node(x, y, nodeNr);
		listaNoduri.add(node);
		nodeNr++;
		repaint();
	}
	
	//se executa atunci cand apelam repaint()
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);//apelez metoda paintComponent din clasa de baza
		g.drawString("This is my Graph! - click stanga arc dublu, click dreapta arc cu un singur sens", 10, 20);
		if(moving)
		{
			for(Node n:listaNoduri)
			{
				if(n.getSelected())
				{
					g.drawString("Ai selectat nodul" + n.getNumber(),10,34);
				}
			}
		}
		else
		{
			g.drawString("Niciun nod nu e selectat",10,34);
		}
		//deseneaza arcele existente in lista

			for (Arc a : listaArce) {
				Node start = a.getStartNode();
				Node end = a.getEndNode();
				if (a.getTwoWayLine()) {
					a.drawArc(g, start, end);
				} else {
					a.drawArrowLine(g, start.getMiddleX(), start.getMiddleY(), end.getMiddleX(), end.getMiddleY(), 40, 5);
				}
			}

		//deseneaza arcul curent; cel care e in curs de desenare
		if (pointStart != null&&moving!=true)
		{
			g.setColor(Color.RED);
			g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
		}
		//deseneaza lista de noduri
		for(int i=0; i<listaNoduri.size(); i++) {
			listaNoduri.elementAt(i).drawNode(g, node_diam);
		}
		displayMatrix(CreateAdjacencyMatrix());

	}

	private int[][] CreateAdjacencyMatrix()
	{
		int[][] adjMatrix = new int[listaNoduri.size()+1][listaNoduri.size()+1];
		for(Arc a:listaArce)
		{
			Node n;
			Node m;
			n=a.getStartNode();
			m=a.getEndNode();
			Boolean TwoWayArc=a.getTwoWayLine();
			if(TwoWayArc)
			{
				adjMatrix[m.getNumber()][n.getNumber()]=1;
			}
			adjMatrix[n.getNumber()][m.getNumber()]=1;
		}
		return adjMatrix;
	}
	private void displayMatrix(int[][] adjMatrix)
	{
		try {
			BufferedWriter output =  new BufferedWriter(new FileWriter("matrix.txt"));
			output.write("Indexarea este de la 1!");
			output.newLine();
			int nrNod=listaNoduri.size();
			output.write(String.valueOf(nrNod));
			output.newLine();
			for(int i=1;i<listaNoduri.size()+1;i++)
			{
				for(int j=1;j<listaNoduri.size()+1;j++)
				{
					if(adjMatrix[i][j]==1)
					{
						output.write(1+" ");
					}
					else
					{
						output.write(0+" ");
					}
				}
				output.newLine();
			}
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(isDragging!=true)
		{
			TopologicalSortDisplayInConsole(adjMatrix);
		}
	}
	private void TopologicalSortDisplayInConsole(int[][] adjMatrix)
	{
		if(listaNoduri.size()!=0) {
			TopologicalSort ts = new TopologicalSort(listaNoduri.size() + 1);

			for (int i = 1; i < listaNoduri.size() + 1; i++) {
				for (int j = 1; j < listaNoduri.size() + 1; j++) {
					if (adjMatrix[i][j] == 1) {
						ts.addEdge(i, j);
					}
				}
			}
			ts.topologicalSort();
		}
	}


}
