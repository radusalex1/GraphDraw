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
    private boolean isCyclicUtil(int i, boolean[] visited,boolean[] recStack)
    {
        if (recStack[i])
            return true;

        if (visited[i])
            return false;

        visited[i] = true;

        recStack[i] = true;
        List<Integer> children = adj.get(i);

        for (Integer c: children)
            if (isCyclicUtil(c, visited, recStack))
                return true;

        recStack[i] = false;

        return false;
    }
    private boolean isCyclic()
    {
        boolean[] visited = new boolean[V];
        boolean[] recStack = new boolean[V];


        for (int i = 0; i < V; i++)
            if (isCyclicUtil(i, visited, recStack))
                return true;

        return false;
    }
    void topologicalSort()
    {
        if (!isCyclic())
        {
            Stack<Integer> stack = new Stack<Integer>();
            boolean visited[] = new boolean[V];
            for (int i = 0; i < V; i++)
                visited[i] = false;

            for (int i = 0; i < V; i++)
                if (!visited[i])
                    topologicalSortUtil(i, visited, stack);

            while (!stack.empty()) {
                System.out.print(stack.pop() + " ");
            }
            System.out.println();
        }
        else
        {
            System.out.println("Graful are cicluri");
            System.out.println();
        }
    }
}
