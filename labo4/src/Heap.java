import java.util.ArrayList;
import java.util.List;

class Heap {
    private List<Integer> data;
    private boolean isMin;

    // O(1): construction sans donnees initiales
    public Heap(boolean isMin) {
        this.isMin = isMin;
        data = new ArrayList();
    }

    // O(n): construction avec donnees initiales, allez voir le lien dans la description pour vous aider
    public Heap(boolean isMin, List<Integer> data) {
        this.isMin = isMin;
        this.data = data;

        build();
    }

    // O(1): on retourne le nombre d'elements dans la liste
    public int size() {
        return data.size();
    }

    // O(log(n)): on ajoute un element et on preserve les proprietes du monceau
    public void insert(Integer element) {
        int elementIdx = data.size();
        int parentIdx = parentIdx(elementIdx);
        data.add(element);
        int parent = data.get(parentIdx);

        while(compare(element, parent) && elementIdx != 0) {
            swap(elementIdx, parentIdx);
            elementIdx = parentIdx;
            parentIdx = parentIdx(elementIdx);
        }

    }

    // O(log(n)): on retire le min ou le max et on preserve les proprietes du monceau
    public Integer pop() {
        int firstIdx = 0;
        int lastIdx = data.size() - 1;

        swap(firstIdx, lastIdx);
        Integer min = data.remove(lastIdx);

        if (lastIdx > 1)
            heapify(firstIdx);

        return min;
    }

    // O(n): on s'assure que tous les elements sont bien places dans le tableau,
    // allez voir le lien dans la description pour vous aider
    public void build() {
        int startIdx = data.size() / 2 - 1;
        for(; startIdx >= 0; startIdx--) {
            heapify(startIdx);
        }
    }

    // O(1): on compare deux elements en fonction du type de monceau
    private boolean compare(Integer first, Integer second) {
        if (isMin)
            return first < second;
        else
            return first > second;
    }

    // O(1): on donne l'indice du parent
    private int parentIdx(int idx) {
        return (idx - 1) / 2;
    }

    // O(1): on donne l'indice de l'enfant de gauche
    private int leftChildIdx(int idx) {
        return 2 * idx + 1;
    }

    // O(1): on donne l'indice de l'enfant de droite
    private int rightChildIdx(int idx) {
        return 2 * idx + 2;
    }

    // O(1): on echange deux elements dans le tableau
    private void swap(int firstIdx, int secondIdx) {
        Integer temp = data.get(firstIdx);
        data.set(firstIdx, data.get(secondIdx));
        data.set(secondIdx, temp);
    }

    // O(log(n)): l'index courant represente le parent, on s'assure que le parent soit le min/max avec ses enfants
    // De facon visuelle, ceci ammene un noeud le plus haut possible dans l'arbre
    // Par exemple: si le min/max est une feuille, on appelera resursivement log(n) fois la methode pour monter le noeud
    private void heapify(int idx) {
        int element = data.get(idx);

        int leftIdx = leftChildIdx(idx);
        int rightIdx = rightChildIdx(idx);
        int lastIdx = data.size() - 1;

        if(leftIdx > lastIdx)
            return;

        int left = data.get(leftIdx);

        if(leftIdx == lastIdx) {
            if (compare(data.get(leftIdx), data.get(idx))) {
                swap(leftIdx, idx);
            }
        }
        else {
            int right = data.get(rightIdx);

            if (compare(left, element) && compare(left, right)) {
                swap(leftIdx, idx);
                heapify(leftIdx);
            }
            else if (compare(right, element)) {
                swap(rightIdx, idx);
                heapify(rightIdx);
            }
        }
    }
}