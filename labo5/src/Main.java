import jdk.jshell.EvalException;

import java.awt.event.WindowAdapter;
import java.nio.charset.CoderResult;
import java.util.*;

public class Main {
    public static void main(String argc[]) {
        boolean isBfs = false;
        Integer[][] mapInt = {
                {1, 0, 1, 0, 0, 2},
                {1, 0, 0, 0, 2, 2},
                {3, 0, 3, 0, 0, 2}};

        List<List<Integer>> map = new ArrayList<>();

        for (int i = 0; i < mapInt.length; i++) {
            map.add(new ArrayList<>());
            for (int j = 0; j < mapInt[i].length; j++) {
                map.get(i).add(mapInt[i][j]);
                System.out.print(map.get(i).get(j) + " ");
            }
            System.out.println();
        }

        //Solutions
        Graph graph = new Graph(map);
        if (isBfs) {
            graph.findContinentsBFS();
        } else {
            graph.findContinentsDFS();
        }

        graph.printContinents();
    }
}

class Graph {
    static class Node {
        Integer x;
        Integer y;
        Integer value;
        Boolean visited = false;

        Node(Integer x, Integer y, Integer value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }

    List<List<Node>> nodeMap;
    Queue<Node> visitingQ = new LinkedList<>();
    Stack<Node> visitingS = new Stack<>();
    List<HashSet<Integer>> continents = new ArrayList<>();

    Integer rowLength = 0;
    Integer colLength = 0;

    Integer nElement = 0;
    Integer nElementVisited = 0;

    Graph(List<List<Integer>> map) {
        nodeMap = new ArrayList<>();

        //Creation of the nodes of the graph
        for (int i = 0; i < map.size(); i++) {
            nodeMap.add(new ArrayList<>());
            for (int j = 0; j < map.get(i).size(); j++) {
                nodeMap.get(i).add(new Node(j, i, map.get(i).get(j)));
            }
        }

        colLength = nodeMap.size();

        if (colLength > 0)
            rowLength = nodeMap.get(0).size();

        nElement = rowLength * colLength;
    }

    public void printContinents() {
        for (HashSet<Integer> continent : continents) {
            List<Integer> continentList = new ArrayList<>(continent);
            continentList.sort(Collections.reverseOrder());

            for (int i = continentList.size() - 1; i >= 0; i--)
                System.out.print(continentList.get(i) + " ");

            System.out.println();
        }
    }

    public void findContinentsBFS() {
        for (int y = 0; y < colLength; y++) {
            for (int x = 0; x < rowLength; x++) {
                if (!nodeMap.get(y).get(x).visited && nodeMap.get(y).get(x).value != 0) {
                    continents.add(new HashSet<>());
                    BFSContinent(x, y);
                }
            }
        }
    }

    public void findContinentsDFS() {
        for (int y = 0; y < colLength; y++) {
            for (int x = 0; x < rowLength; x++) {
                if (!nodeMap.get(y).get(x).visited && nodeMap.get(y).get(x).value != 0) {
                    continents.add(new HashSet<>());
                    DFSContinent(x, y);
                }
            }
        }
    }

    public void BFSContinent(int xStart, int yStart) {

        //Check that the starting node is in the graph
        if (isOutOfBounds(xStart, yStart))
            return;

        //Grab the first node
        Node first = nodeMap.get(yStart).get(xStart);
        visitingQ.add(first); //First node to visit

        while (!visitingQ.isEmpty()) { //Visit all adjacent nodes with DFS
            //Current node to visit
            Node current = visitingQ.poll();

            //If the node was already visited skip to next node
            if (current.visited)
                continue;

            //Mark node as visited
            current.visited = true;

            //Increment the number of nodes visited
            nElementVisited ++;

            //If the node is water go to next node
            if (current.value == 0)
                continue;

            //Add the country to the continent
            continents.get(continents.size() - 1).add(current.value);

            //Add the nodes next to it to the list of nodes to visit
            if (!isOutOfBounds(current.x, current.y - 1))
                visitingQ.add(nodeMap.get(current.y - 1).get(current.x)); //Add Up node to visit

            if (!isOutOfBounds(current.x + 1, current.y))
                visitingQ.add(nodeMap.get(current.y).get(current.x + 1)); //Add Right node to visit

            if (!isOutOfBounds(current.x, current.y + 1))
                visitingQ.add(nodeMap.get(current.y + 1).get(current.x)); //Add Down node to visit

            if (!isOutOfBounds(current.x - 1, current.y))
                visitingQ.add(nodeMap.get(current.y).get(current.x - 1)); //Add Left node to visit
        }
    }

    public void DFSContinent(int xStart, int yStart) {

        //Check that the starting node is in the graph
        if (isOutOfBounds(xStart, yStart))
            return;

        //Grab the first node
        Node first = nodeMap.get(yStart).get(xStart);
        visitingS.push(first); //First node to visit

        while (!visitingS.isEmpty()) { //Visit all adjacent nodes with DFS
            //Current node to visit
            Node current = visitingS.pop();

            //If the node was already visited skip to next node
            if (current.visited)
                continue;

            //Mark node as visited
            current.visited = true;

            //Increment the number of nodes visited
            nElementVisited ++;

            //If the node is water go to next node
            if (current.value == 0)
                continue;

            //Add the country to the continent
            continents.get(continents.size() - 1).add(current.value);

            //Add the nodes next to it to the list of nodes to visit
            if (!isOutOfBounds(current.x, current.y - 1))
                visitingS.push(nodeMap.get(current.y - 1).get(current.x)); //Add Up node to visit

            if (!isOutOfBounds(current.x + 1, current.y))
                visitingS.push(nodeMap.get(current.y).get(current.x + 1)); //Add Right node to visit

            if (!isOutOfBounds(current.x, current.y + 1))
                visitingS.push(nodeMap.get(current.y + 1).get(current.x)); //Add Down node to visit

            if (!isOutOfBounds(current.x - 1, current.y))
                visitingS.push(nodeMap.get(current.y).get(current.x - 1)); //Add Left node to visit
        }
    }

    public boolean isOutOfBounds(int x, int y) {
        return x >= rowLength || x < 0 || y >= colLength || y < 0;
    }
}