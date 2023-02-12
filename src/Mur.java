
public class Mur {

    public enum Direction {
        DROITE, GAUCHE, HAUT, BAS
    }

    int axisX;
    int axisY;
    int longueur;
    Direction direction;


    public Mur(int axisX, int axisY, int longueur, Direction direction) {
        this.axisX = axisX;
        this.axisY = axisY;
        this.longueur = longueur;
        this.direction = direction;
    }

    public int getAxisX() {
        return axisX;
    }

    public void setAxisX(int axisX) {
        this.axisX = axisX;
    }

    public int getAxisY() {
        return axisY;
    }

    public void setAxisY(int axisY) {
        this.axisY = axisY;
    }

    public int getLongueur() {
        return longueur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


}
