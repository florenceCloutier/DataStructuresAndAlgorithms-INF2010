/*
 * Implementation de l'algorithme pour le probleme pays et continents
 * file PaysEtContinent.java
 * authors Alexis Foulon (2009665) et Florence Cloutier (2028284)
 *
 * Ce programme contient la classe Solution et la classe Graph qui contient les methodes qui permettent de retrouver
 * les pays et leurs continents respectifs sur une carte du monde
 *
 * Nom d'utilisateur HackerRank: Alexis_Foulon
 *
 */
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Solution {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean isBfs = scanner.nextLine().equals("1");
        List<List<Integer>> world = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String listString = scanner.nextLine();
            List<Integer> row = Arrays
                    .stream(listString.split(" +"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            world.add(row);
        }

        Graph graph = new Graph(world);
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
