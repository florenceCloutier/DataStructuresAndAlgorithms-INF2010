import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Maze {
    public static void main(String[] argc) {
        Integer[][] mazeData = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 1, 0, 0, 0, 1, 0, 1},
                {0, 0, 1, 1, 0, 1, 1, 1, 0, 1},
                {2, 0, 1, 1, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 1, 0, 1, 1, 0, 0, 1},
                {1, 0, 0, 1, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 2}};

        List<List<MazeTile>> maze = new ArrayList<>();

        MazeTile[] mazeTiles = MazeTile.values();

        for (int i = 0; i < mazeData.length; i++){
            maze.add(new ArrayList<>());
            for (int j = 0; j < mazeData[i].length; j++) {
                maze.get(i).add(mazeTiles[mazeData[i][j]]);
                System.out.print(mazeTiles[mazeData[i][j]] + " ");
            }
            System.out.println();
        }

        //Solution Here
        GraphMaze graphMaze = new GraphMaze(maze);
        graphMaze.smallestDistance();
    }
}

class GraphMaze {
    static class Node implements Comparable<Node>{
        Integer x;
        Integer y;
        MazeTile value;
        Integer distance = 0;
        Boolean visited = false;

        Node(Integer x, Integer y, MazeTile value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        @Override
        public int compareTo(Node node) {
            return distance - node.distance;
        }
    }

    List<List<Node>> nodeMap;
    PriorityQueue<Node> queue = new PriorityQueue<>();

    Node start;
    Node end;

    Integer rowLength;
    Integer colLength;

    GraphMaze(List<List<MazeTile>> map) {
        nodeMap = new ArrayList<>();

        //Creation of the nodes of the graph
        for (int i = 0; i < map.size(); i++) {
            nodeMap.add(new ArrayList<>());
            for (int j = 0; j < map.get(0).size(); j++) {
                nodeMap.get(i).add(new Node(j, i, map.get(i).get(j)));
            }
        }

        colLength = nodeMap.size();
        rowLength = nodeMap.get(0).size();

        findStartEnd(); //Finds the Start and End
    }

    private void findStartEnd() {
        boolean foundStart = false;
        boolean foundEnd = false;

        //Checks the edges of the maze for a start and an end
        for (int i = 0; i < nodeMap.size() && (!foundEnd || !foundStart); i++) {
            if (nodeMap.get(i).get(0).value == MazeTile.Exit) {
                if (!foundStart) {
                    start = nodeMap.get(i).get(0);
                    foundStart = true;
                }
                else if (!foundEnd) {
                    end = nodeMap.get(i).get(0);
                    foundEnd = true;
                }
            }

            if (nodeMap.get(i).get(rowLength - 1).value == MazeTile.Exit) {
                if (!foundStart) {
                    start = nodeMap.get(i).get(rowLength - 1);
                    foundStart = true;
                }
                else if (!foundEnd) {
                    end = nodeMap.get(i).get(rowLength - 1);
                    foundEnd = true;
                }
            }

            if (nodeMap.get(0).get(i).value == MazeTile.Exit) {
                if (!foundStart) {
                    start = nodeMap.get(0).get(i);
                    foundStart = true;
                }
                else if (!foundEnd) {
                    end = nodeMap.get(0).get(i);
                    foundEnd = true;
                }
            }

            if (nodeMap.get(colLength - 1).get(i).value == MazeTile.Exit) {
                if (!foundStart) {
                    start = nodeMap.get(colLength - 1).get(i);
                    foundStart = true;
                }
                else if (!foundEnd) {
                    end = nodeMap.get(colLength - 1).get(i);
                    foundEnd = true;
                }
            }
        }

    }

    //Finds shortest Path with priority queue
    public void smallestDistance() {
        queue.add(start);

        for(;;) {
            Node current = queue.poll();

            if (current == end)
                break;

            if (current.value == MazeTile.Wall)
                continue;

            if (current.visited)
                continue;

            current.visited = true;

            //Add the nodes next to it to the list of nodes to visit
            if (current.y - 1 >= 0) {
                Node up = nodeMap.get(current.y - 1).get(current.x); //Add Up node to visit
                up.distance = current.distance + 1;
                queue.add(up);
            }
            if (current.x + 1 < rowLength) {
                Node right = nodeMap.get(current.y).get(current.x + 1); //Add Right node to visit
                right.distance = current.distance + 1;
                queue.add(right);
            }
            if (current.y + 1 < colLength) {
                Node down = nodeMap.get(current.y + 1).get(current.x); //Add Down node to visit
                down.distance = current.distance + 1;
                queue.add(down);
            }
            if (current.x - 1 >= 0) {
                Node left = nodeMap.get(current.y).get(current.x - 1); //Add Left node to visit
                left.distance = current.distance + 1;
                queue.add(left);
            }
        }

        System.out.println(end.distance);
    }
}

enum MazeTile {
    Floor,
    Wall,
    Exit;

    @Override
    public String toString() {
        switch (this) {
            case Exit: return "*";
            case Floor: return " ";
            case Wall: return "#";
            default: return "";
        }
    }
}