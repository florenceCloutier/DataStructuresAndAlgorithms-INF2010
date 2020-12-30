package tp3;

import tests.Corrector;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Bienvenue au troisieme labo de INF2010!");
        //Corrector.executeTester("AllTesters", Corrector::start, 20.0);
        AvlTree<Integer> tree = new AvlTree();

        tree.insert(10);
        tree.insert(25);
        tree.insert(40);
        tree.insert(70);
        tree.insert(85);
        tree.insert(100);
        tree.insert(115);
        tree.insert(130);
        tree.insert(145);
        tree.insert(160);
        tree.insert(175);
        tree.insert(190);
        tree.insert(205);

        List<Integer> list = tree.levelOrder();
        for (Integer elem : list)
            System.out.println(elem);
    }
}
