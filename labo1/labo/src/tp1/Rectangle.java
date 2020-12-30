/*
 * Implementation de la classe Rectangle
 * file Rectangle.java
 * authors Alexis Foulon et Florence Cloutier
 *
 * Ce programme contient les methodes qui ont pour but la construction de la classe
 * Rectangle et l'implementation des methodes de cette derniere.
 */

package tp1;

import java.util.Set;

public class Rectangle extends BaseShape {

    //! Constructeur par parametres
    public Rectangle(Double width, Double height) {
        super(); //Appel du constructeur par defaut  de BaseShape
        for (Double x = -width/2; x <= width/2; x++) {
            for (Double y = -height/2; y <= height/2; y++) {
                add(new Point2d(x, y));
            }
        }
    }

    //! Constructeur par parametres
    public Rectangle(Point2d dimensions) {
        this(dimensions.X(), dimensions.Y()); //Appel du constructeur par parametres precedant de Rectangle
    }

    //! Constructeur par parametres
    private Rectangle(Set<Point2d> coords) {
        super(coords);
    } //Appel du constructeur par parametres de BaseShape

    //! Methode qui effectue la translation du rectangle selon un point
    @Override
    public Rectangle translate(Point2d point) {
        super.translate(point); //Appel de la methode translate de BaseShape
        return this;
    }

    //! Methode qui effectue la rotation du rectangle selon un angle
    @Override
    public Rectangle rotate(Double angle) {
        super.rotate(angle); //Appel de la methode rotate de BaseShape
        return this;
    }

    //! Methode qui clone un rectangle
    @Override
    public Rectangle clone() {
        return new Rectangle(this.getCoords());
    }
}
