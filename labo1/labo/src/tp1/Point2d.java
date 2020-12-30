/*
 * Implementation de la classe Point2D
 * file Point2D.java
 * authors Alexis Foulon et Florence Cloutier
 *
 * Ce programme contient les methodes qui ont pour but la construction de la classe
 * Point2D et l'implementation des methodes de cette derniere.
 */

package tp1;

import java.util.Arrays;

public class Point2d extends AbstractPoint {
    private final Integer X = 0; //Position de la coordonnee x dans le vecteur du point2D
    private final Integer Y = 1; //Position de la coordonnee y dans le vecteur du point2D


    //! Constructeur par parametres
    public Point2d(Double x, Double y) {
        super(new Double[] {x, y}); //Appel du constructeur par param de la classe AbstractPoint
    }

    //! Constructeur par parametre
    public Point2d(Double[] vector) {
        super(vector.clone()); //Appel du constructeur par param de la classe AbstractPoint
    }

    //! Getters des coordonnees du point2D
    public Double X() { return vector[X];}
    public Double Y() { return vector[Y];}

    //! Methode qui effectue la translation du point selon un vecteur de translation
    @Override
    public Point2d translate(Double[] translateVector) {
        PointOperator.translate(this.vector, translateVector); //Appel de la methode translate de PointOperator
        return this;
    }

    //! Methode qui effectue la translation du point selon un vecteur dans un point donnee
    public Point2d translate(Point2d translateVector) {
        PointOperator.translate(this.vector, translateVector.vector); //Appel de la methode translate de PointOperator
        return this;
    }

    //! Methode qui effectue la rotation du point selon une matrice de rotation
    @Override
    public Point2d rotate(Double[][] rotationMatrix) {
        PointOperator.rotate(this.vector, rotationMatrix); //Appel de la methode rotate de PointOperator
        return this;
    }

    //! Methode qui effectue la rotation du point selon un angle donnee
    public Point2d rotate(Double angle) {
        Double[][] rotationMatrix = {{Math.cos(angle), -Math.sin(angle)},{Math.sin(angle), Math.cos(angle)}};
        PointOperator.rotate(this.vector, rotationMatrix); //Appel de la methode rotate de PointOperator
        return this;
    }

    //! Methode qui applique une division sur les coordonnees du point2D selon un diviseur donnee
    @Override
    public Point2d divide(Double divider) {
        PointOperator.divide(this.vector, divider); //Appel de la methode divide de PointOperator
        return this;
    }

    //! Methode qui applique une multiplication sur les coordonnees du point2D selon un multiple donnee
    @Override
    public Point2d multiply(Double multiplier) {
        PointOperator.multiply(this.vector, multiplier); //Appel de la methode multiply de PointOperator
        return this;
    }

    //! Methode qui applique une addition sur les coordonnees du point2D selon un nombre donnee
    @Override
    public Point2d add(Double adder) {
        PointOperator.add(this.vector, adder); //Appel de la methode add de PointOperator
        return this;
    }

    //! Methode qui clone un point2D
    @Override
    public Point2d clone() {
        return new Point2d(this.vector);
    }
}
