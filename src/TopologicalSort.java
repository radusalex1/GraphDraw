import java.io.*;
import java.util.*;

public class TopologicalSort {
    private int V;
    private ArrayList<ArrayList<Integer> > adj;
    TopologicalSort(int v)
    {
        V = v;
        adj = new ArrayList<ArrayList<Integer> >(v);
        for (int i = 0; i < v; ++i)
            adj.add(new ArrayList<Integer>());
    }
    void addEdge(int v, int w) {
        adj.get(v).add(w);
    }
    void topologicalSortUtil(int v, boolean visited[],
                             Stack<Integer> stack)
    {
        visited[v] = true;
        Integer i;

        Iterator<Integer> it = adj.get(v).iterator();
        while (it.hasNext())
        {
            i = it.next();
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);
        }

        stack.push(v);
    }
    void topologicalSort()
    {
        Stack<Integer> stack = new Stack<Integer>();

        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;


        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                topologicalSortUtil(i, visited, stack);

        // Print contents of stack
        try{
            BufferedWriter output =  new BufferedWriter(new FileWriter("sortat.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (stack.empty() == false)
        {
            System.out.print(stack.pop() + " ");
        }
        System.out.println();
    }
}
