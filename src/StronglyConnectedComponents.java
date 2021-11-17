import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.LinkedList;


public class StronglyConnectedComponents {
    private int V;
    private LinkedList<Integer> adj[];

    StronglyConnectedComponents(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }

    void addEdge(int v, int w)  { adj[v].add(w); }

    void DFSUtil(int v,boolean visited[],Vector<Node> listaNoduri,float r,float g,float b) throws IOException {
        visited[v] = true;
        System.out.print(v+1 + " ");
        for(Node n:listaNoduri)
        {
            if(n.getNumber()==v+1)
            {
                n.setR(r);
                n.setG(g);
                n.setB(b);
            }
        }
        int n;
        for (Integer integer : adj[v]) {
            n = integer;
            if (!visited[n])
                DFSUtil(n,visited,listaNoduri,r,g,b);
        }
    }

    StronglyConnectedComponents getTranspose()
    {
        StronglyConnectedComponents g = new StronglyConnectedComponents(V);
        for (int v = 0; v < V; v++)
        {
            // Recur for all the vertices adjacent to this vertex
            Iterator<Integer> i =adj[v].listIterator();
            while(i.hasNext())
                g.adj[i.next()].add(v);
        }
        return g;
    }

    void fillOrder(int v, boolean visited[], Stack stack)
    {
        visited[v] = true;

        Iterator<Integer> i = adj[v].iterator();
        while (i.hasNext())
        {
            int n = i.next();
            if(!visited[n])
                fillOrder(n, visited, stack);
        }
        stack.push(v);
    }

    void printSCCs(Vector<Node> listaNoduri) throws IOException {
        Stack stack = new Stack();

        boolean visited[] = new boolean[V];
        for(int i = 0; i < V; i++)
            visited[i] = false;

        for (int i = 0; i < V; i++)
            if (!visited[i])
                fillOrder(i, visited, stack);

        StronglyConnectedComponents gr = getTranspose();

        for (int i = 0; i < V; i++)
            visited[i] = false;


        while (!stack.empty())
        {
            int v = (int)stack.pop();

            if (!visited[v])
            {
                Random rand =  new Random();
                float r = rand.nextFloat();
                float g = rand.nextFloat();
                float b = rand.nextFloat();

                for(Node n:listaNoduri)
                {
                    if(n.getNumber()==v+1)
                    {
                        n.setR(r);
                        n.setG(g);
                        n.setB(b);
                    }
                }
                gr.DFSUtil(v, visited,listaNoduri,r,g,b);
                System.out.println();
            }
        }
        System.out.print('\r');
    }
}
