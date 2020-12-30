import java.io.PipedOutputStream;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Main {
    /*
    public static void main(String[] args) {
        // TODO: 2009665 matricule2
        String ascending = "1";
        String listString = "-42 4 700 0 3 3 2 8 5 9";

        // TODO prendre les donnees en entree, les convertir et les utiliser dans le monceau
        boolean isMin = "1".equals(ascending);
        List<Integer> data = Arrays.stream(listString.split("\\s"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Heap heap2 = new Heap(true);
        for (int i = 0; i < 20; i++) {
            int rand = ThreadLocalRandom.current().nextInt() % 100;
            heap2.insert(rand);
            System.out.print(rand + " ");
        }
        System.out.println();
        while (heap2.size() > 0) {
            System.out.print(heap2.pop() + " ");
        }
        System.out.println();

        Heap heap = new Heap(isMin, data);

        while (heap.size() > 0) {
            System.out.print(heap.pop() + " ");
        }
        System.out.println();
    }
    */

    public static void main(String[] args) {
        // TODO: matricule1 matricule2
        Integer circleSize = 3;

        List<Integer> centers = new ArrayList<>();
        centers.add(9);
        centers.add(0);
        centers.add(8);

        List<Point> points = new ArrayList<>();
        points.add(new Point("0 9", 0));
        points.add(new Point("4 7", 1));
        points.add(new Point("2 2", 2));
        points.add(new Point("7 7", 3));
        points.add(new Point("9 6", 4));
        points.add(new Point("0 7", 5));
        points.add(new Point("0 7", 6));
        points.add(new Point("3 8", 7));
        points.add(new Point("2 8", 8));
        points.add(new Point("0 9", 9));

        // TODO modifiez ceci pour inclure votre algorithme
        // Expliquez votre complexite en fonctione de 'a', 'c' et 'n'
        // La complexite est de O(a*c*n*log(n))

        HashMap<Integer, Integer> allFriends = new HashMap<>();
        List<Point> centerPoints = new ArrayList<>();
        List<Integer> badFriendsId = new ArrayList<>();

        for (Integer center : centers) // O(c)
            centerPoints.add(points.get(center));

        for (Point center : centerPoints) { // O(c)
            PointComparator comparator = new PointComparator(center);
            points.sort(comparator); // O(nlog(n))
            for (int i = 1; i <= circleSize && i < points.size(); i++) { //O(a)
                int friendId = points.get(i).id;

                if (allFriends.containsKey(friendId))
                    allFriends.put(friendId, allFriends.get(friendId) + 1);
                else
                    allFriends.put(friendId, 1);
            }
        }

        for (Map.Entry<Integer, Integer> entry : allFriends.entrySet()) { //O(n)
            if (entry.getValue() > 1)
                badFriendsId.add(entry.getKey());
        }

        badFriendsId.sort(Collections.reverseOrder()); //O(nlog(n))

        for (int i = badFriendsId.size() - 1; i >= 0; i--) //O(n)
            System.out.print(badFriendsId.get(i) + " ");

        if (badFriendsId.isEmpty())
            System.out.println("-1");

    }
}

class PointComparator implements Comparator<Point> {
    Point center;

    PointComparator(Point center) {
        this.center = center;
    }

    @Override
    public int compare(Point p1, Point p2) {
        int d1 = Math.abs(center.x - p1.x) + Math.abs(center.y - p1.y);
        int d2 = Math.abs(center.x - p2.x) + Math.abs(center.y - p2.y);

        if (p1 == center)
            return -1;
        else if (p2 == center)
            return 1;

        if (d1 == d2)
            return p1.id - p2.id;

        return d1 - d2;
    }
}

class Point {
    public Integer x;
    public Integer y;
    public Integer id;

    public Point(String xy, Integer id) {
        String[] xAndY = xy.split(" +");
        this.id = id;
        this.x = Integer.parseInt(xAndY[0]);
        this.y = Integer.parseInt(xAndY[1]);
    }

    @Override
    public String toString() {
        return String.format("{Id: %d, X: %d, Y: %d}", id, x, y);
    }

    public int distanceTo(Point point) {
        return Math.abs(point.x - this.x) + Math.abs(point.y - this.y);
    }
}