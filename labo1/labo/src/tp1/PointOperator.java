/*
 * Implementation de la classe PointOperator
 * file PointOperator.java
 * authors Alexis Foulon et Florence Cloutier
 *
 * Ce programme contient les methodes qui ont pour but la construction de la classe
 * PointOperator et l'implementation des methodes de cette derniere.
 */

package tp1;

import java.util.Arrays;
import java.util.Collection;

public final class PointOperator {

    final static Integer X = 0, Y = 1;

    //! Methode qui effectue la translation d'un vecteur selon un vecteur de translation
    public static void translate(Double[] vector, Double[] translateVector) {
        for (int i = 0; i < translateVector.length; i++) {
            vector[i] += translateVector[i];
        }
    }

    //! Methode qui effectue la rotation d'un vecteur selon une matrice de rotation
    public static void rotate(Double[] vector, Double[][] rotationMatrix) {
        Double[] vec = vector.clone();
        Arrays.fill(vector, 0.0); //Remet tout le contenu de vector a 0.0

        for (int row = 0; row < vector.length; ++row) {
            for (int col = 0; col < vector.length; ++col) {
                vector[row] += vec[col] * rotationMatrix[row][col];
            }
        }
    }

    //! Methode qui effectue la division d'un vecteur selon un diviseur
    public static void divide(Double[] vector, Double divider) {
        for (int elemVec = 0; elemVec < vector.length; ++elemVec)
            vector[elemVec] /= divider;
    }

    //! Methode qui effectue la multiplication d'un vecteur selon un multiple
    public static void multiply(Double[] vector, Double multiplier) {
        for (int elemVec = 0; elemVec < vector.length; ++elemVec)
            vector[elemVec] *= multiplier;
    }

    //! Methode qui applique une addition sur un vecteur selon un nombre donne
    public static void add(Double[] vector, Double adder) {
        for (int elemVec = 0; elemVec < vector.length; ++elemVec)
            vector[elemVec] += adder;
    }

    //! Methode qui retourne un point contenant la coordonnee maximale en x et en y d'une collection de points
    public static Point2d getMaxCoord(Collection<Point2d> coords) {
        Point2d max = coords.iterator().next().clone(); //Clone the first Point

        for (Point2d coord : coords) {
            if (coord.X() > max.X()) {
                max.vector[X] = coord.X();
            }
            if (coord.Y() > max.Y()) {
                max.vector[Y] = coord.Y();
            }
        }
        return max;
    }

    //! Methode qui retourne un point contenant la coordonnee minimale en x et en y d'une collection de points
    public static Point2d getMinCoord(Collection<Point2d> coords) {
        Point2d min = coords.iterator().next().clone(); //Clone the first Point

        for (Point2d coord : coords) {
            if (coord.X() < min.X()) {
                min.vector[X] = coord.X();
            }
            if (coord.Y() < min.Y()) {
                min.vector[Y] = coord.Y();
            }
        }
        return min;
    }
}
