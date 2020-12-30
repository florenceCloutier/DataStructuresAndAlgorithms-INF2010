/*
 * Implementation de la classe BaseShape
 * file BaseShape.java
 * authors Alexis Foulon et Florence Cloutier
 *
 * Ce programme contient les methodes qui ont pour but la construction de la classe
 * BaseShape et l'implementation des methodes de cette derniere.
 */

package tp1;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BaseShape implements Cloneable {
    // Vous aller apprendre plus en details le fonctionnement d'un Set lors
    // du cours sur les fonctions de hashage, vous pouvez considerez ceci comme une liste normale.
    // Les Sets ont la propriete qu'un element ne peux se retrouver qu'une seule fois dans la liste
    private Set<Point2d> coords; //Coordonnee des points contenus dans le BaseShape

    //! Constructeur par defaut
    public BaseShape() {
        this.coords = new HashSet<>();
    }

    //! Constructeur par parametres
    public BaseShape(Collection<Point2d> coords) {
        this.coords = new HashSet<>();
        this.coords.addAll(coords);
    }

    //! Methode qui permet d'ajouter un point dans le set de points de BaseShape
    public void add(Point2d coord) {
        this.coords.add(new Point2d(coord.X(), coord.Y()));
    }

    //! Methode qui permet d'ajouter les points d'un BaseShape dans le set de points de BaseShape
    public void add(BaseShape shape) {
        this.coords.addAll(shape.getCoordsDeepCopy());
    }

    //! Methode qui permet d'ajouter les points d'une collection dans le set de points de BaseShape
    public void addAll(Collection<Point2d> coords) {
        for (Point2d coord : coords) {
            add(coord);
        }
    }

    //! Methode qui permet de retirer un point dans le set de points de BaseShape
    public void remove(Point2d coord) {
        this.coords.remove(coord);
    }

    //! Methode qui permet de retirer les points d'un BaseShape dans le set de points de BaseShape
    public void remove(BaseShape shape) {
        this.coords.removeAll(shape.getCoords());
    }

    //! Methode qui permet de retirer les points d'une collection dans le set de points de BaseShape
    public void removeAll(Collection<Point2d> coords) {
        this.coords.removeAll(coords);
    }

    //! Methode qui retourne les coordonnees du set de points de BaseShape
    public Set<Point2d> getCoords() {
        Set<Point2d> set = new HashSet<>(this.coords);
        return set;
    }

    //! Methode qui retourne les coordonnees du set de points de BaseShape par copie
    public Set<Point2d> getCoordsDeepCopy() {
        Set<Point2d> copy = new HashSet<>();
        for (Point2d coord : coords)
            copy.add(new Point2d(coord.X(), coord.Y()));
        return copy;
    }

    //! Methode qui effectue la translation de la forme selon un vecteur dans un point donnee
    public BaseShape translate(Point2d point) {
        for (Point2d coord : this.coords)
            coord.translate(point); //Appel de la methode translate de Point2D
        return this;
    }

    //! Methode qui effectue la rotation de la forme selon un certain angle
    public BaseShape rotate(Double angle) {
        for (Point2d coord : this.coords)
            coord.rotate(angle); //Appel de la methode rotate de Point2D
        return this;
    }

    //! Methode qui clone la forme
    public BaseShape clone() {
        return new BaseShape(this.coords);
    }
}
