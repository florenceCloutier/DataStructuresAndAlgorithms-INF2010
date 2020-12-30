/*
 * Implementation de la classe LetterFactory
 * file LetterFactory.java
 * authors Alexis Foulon et Florence Cloutier
 *
 * Ce programme contient les methodes qui ont pour but la construction de la classe
 * LetterFactory et l'implementation des methodes de cette derniere.
 */

package tp1;

public final class LetterFactory {
    final static Double maxHeight = 200.0;
    final static Double maxWidth = maxHeight / 2;
    final static Double halfMaxHeight = maxHeight / 2;
    final static Double halfMaxWidth = maxWidth / 2;
    final static Double stripeThickness = maxHeight / 10;

    //! Methode qui cree graphiquement la lettre H
    public static BaseShape create_H() {
        BaseShape mainStripe = new Rectangle(maxWidth, stripeThickness);

        BaseShape leftStripe = new Rectangle(stripeThickness, maxHeight);
        BaseShape rightStripe = new Rectangle(stripeThickness, maxHeight);

        leftStripe = leftStripe.translate(new Point2d(-halfMaxWidth, 0.0)); //Placement dans l'espace 2D
        rightStripe = rightStripe.translate(new Point2d(halfMaxWidth, 0.0)); //Placement dans l'espace 2D

        mainStripe.add(leftStripe);
        mainStripe.add(rightStripe);

        return mainStripe;
    }

    //! Methode qui cree graphiquement la lettre e
    public static BaseShape create_e() {
        BaseShape mainHook = new Ellipse(halfMaxWidth, halfMaxHeight);
        BaseShape mainHookToRemove = new Ellipse(halfMaxWidth - stripeThickness, halfMaxHeight-stripeThickness);
        mainHook.remove(mainHookToRemove);

        BaseShape mainStripe = new Rectangle(maxWidth, stripeThickness);
        mainHook.add(mainStripe);

        BaseShape stripeToRemove = new Rectangle(halfMaxWidth, stripeThickness);
        stripeToRemove = stripeToRemove.translate(new Point2d(halfMaxWidth/2, stripeThickness + 1)); //Placement dans l'espace 2D
        mainHook.remove(stripeToRemove);

        return mainHook;
    }

    //! Methode qui cree graphiquement la lettre l
    public static BaseShape create_l() {
        BaseShape mainStripe = new Rectangle(stripeThickness, maxHeight);
        return mainStripe;
    }

    //! Methode qui cree graphiquement la lettre o
    public static BaseShape create_o() {
        BaseShape mainHook = new Ellipse(halfMaxWidth, halfMaxHeight);
        BaseShape mainHookToRemove = new Ellipse(halfMaxWidth - stripeThickness, halfMaxHeight-stripeThickness);
        mainHook.remove(mainHookToRemove);
        return mainHook;
    }

    //! Methode qui cree graphiquement la lettre W
    public static BaseShape create_W() {
        Double angle = 7.5; //Angle approprie pour que le W ressemble le plus possible a celui de l'image a suivre
        Double distance = Math.tan(Math.toRadians(angle))*halfMaxHeight;

        BaseShape w = new BaseShape();
        BaseShape leftStripe1 = new Rectangle(stripeThickness, maxHeight);
        BaseShape leftStripe2 = new Rectangle(stripeThickness, maxHeight);
        BaseShape rightStripe1 = new Rectangle(stripeThickness, maxHeight);
        BaseShape rightStripe2 = new Rectangle(stripeThickness, maxHeight);

        leftStripe1 = leftStripe1.rotate(Math.toRadians(-angle)).translate(new Point2d(-3*distance, 0.0));
        leftStripe2 = leftStripe2.rotate(Math.toRadians(-angle)).translate(new Point2d(distance, 0.0));
        rightStripe1 = rightStripe1.rotate(Math.toRadians(angle)).translate(new Point2d(3*distance, 0.0));
        rightStripe2 = rightStripe2.rotate(Math.toRadians(angle)).translate(new Point2d(-distance, 0.0));

        w.add(leftStripe1);
        w.add(leftStripe2);
        w.add(rightStripe1);
        w.add(rightStripe2);

        return w;
    }

    //! Methode qui cree graphiquement la lettre r
    public static BaseShape create_r() {
        BaseShape mainHook = new Circle(maxWidth/2);
        BaseShape mainHookToRemove = new Circle(maxWidth/2 - stripeThickness);
        mainHook.remove(mainHookToRemove);

        BaseShape halfHookToRemove = new Square(maxWidth);
        halfHookToRemove = halfHookToRemove.translate(new Point2d(0.0, maxWidth/2)); //Placement dans l'espace 2D
        mainHook.remove(halfHookToRemove);

        BaseShape mainStripe = new Rectangle(stripeThickness, maxHeight);
        mainStripe = mainStripe.translate(new Point2d(-maxWidth/2 + stripeThickness/2, maxHeight/4 - stripeThickness/2));//Placement dans l'espace 2D
        mainHook.add(mainStripe);

        mainHook = mainHook.translate(new Point2d(0.0, -maxHeight/4 + stripeThickness/2)); //Placement dans l'espace 2D

        return mainHook;
    }

    // On vous donne la lettre d comme exemple.
    public static BaseShape create_d() {
        Double hookRadius = maxWidth / 2;
        BaseShape mainHook = new Circle(hookRadius);
        BaseShape mainHookToRemove = new Circle(hookRadius - stripeThickness);
        BaseShape mainStripe = new Rectangle(stripeThickness, maxHeight)
                .translate(new Point2d(hookRadius - stripeThickness / 2, 0.0));
        mainHook.remove(mainHookToRemove);
        mainHook = mainHook.translate(new Point2d(0.0, maxHeight / 2.1 - hookRadius)); //Placement dans l'espace 2D
        mainHook.add(mainStripe);
        return mainHook;
    }
}
