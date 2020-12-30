/*
 * Implementation de la classe Ellipse
 * file Ellipse.java
 * authors Alexis Foulon et Florence Cloutier
 *
 * Ce programme contient les methodes qui ont pour but la construction de la classe
 * Ellipse et l'implementation des methodes de cette derniere.
 */

package tp1;

import java.util.Set;
import java.lang.Math;

public class Ellipse extends BaseShape {

    //! Constructeur par parametres
    public Ellipse(Double widthRadius, Double heightRadius) {
        super(); //Appel du constructeur par defaut de BaseShape
        for (Double y = -heightRadius; y < heightRadius; y++) {
            for (Double x = -widthRadius; x < widthRadius; x++) {
                if (Math.pow(x, 2.0) * Math.pow(heightRadius, 2.0) + Math.pow(y, 2.0) * Math.pow(widthRadius, 2.0) <= Math.pow(widthRadius, 2.0) * Math.pow(heightRadius, 2.0))
                    add(new Point2d(x, y));
            }
        }
    }

    //! Constructeur par parametres
    public Ellipse(Point2d dimensions) {
        this(dimensions.X(), dimensions.Y());//Appel du constructeur par parametres precedant d'Ellipse
    }

    //! Constructeur par parametres
    private Ellipse(Set<Point2d> coords) { super(coords); }//Appel du constructeur par param de BaseShape

    //! Methode qui effectue la translation de l'ellipse selon un point
    @Override
    public Ellipse translate(Point2d point) {
        super.translate(point); //Appel de la methode translate de BaseShape
        return this;
    }

    //! Methode qui effectue la rotation de l'ellipse selon un angle
    @Override
    public Ellipse rotate(Double angle) {
        super.rotate(angle); //Appel de la methode rotate de BaseShape
        return this;
    }

    //! Methode qui clone une ellipse
    @Override
    public Ellipse clone() { return new Ellipse(this.getCoords()); }
}
