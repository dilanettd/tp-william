public class Piece {
   int axisX;
   int axisY;
   int base;
   int hauteur;

    public Piece(int axisX, int axisY, int base, int hauteur) {
        this.axisX = axisX;
        this.axisY = axisY;
        this.base = base;
        this.hauteur = hauteur;
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

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }
}
