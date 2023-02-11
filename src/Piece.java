public class Matrice {
    int base;
    int hauteur;
    int positionX;
    int positionY;

    public Matrice(int base, int hauteur) {
        this.base = base;
        this.hauteur = hauteur;
        this.positionX = positionX;
        this.positionY = positionY;
//        String mat [][] = new String[base][hauteur];
//        for(int i =0; i < hauteur; i++){
//            for(int j = 0; j < base; j++){
//                mat[hauteur][base]="o";
//        }
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


    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
