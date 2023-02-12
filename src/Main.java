import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;



public class Main {
    static ArrayList<Mur>  listeMurValide = new ArrayList<Mur>();

    static ArrayList<PieceSimple>  listePieceSimple = new ArrayList<PieceSimple>();

    static ArrayList<Piece>  listPiece = new ArrayList<Piece>();

    public static void ajouterMur(ArrayList<Mur> liste, int axisX, int axisY, int base, int hauteur){
        Mur mur1 = new Mur(axisX,axisY,hauteur, Mur.Direction.GAUCHE);
        liste.add(mur1);
        Mur mur2 = new Mur(axisX+base,axisY,base, Mur.Direction.HAUT);
        liste.add(mur2);
        Mur mur3 = new Mur(axisX+base,axisY+hauteur,hauteur, Mur.Direction.DROITE);
        liste.add(mur3);
        Mur mur4 = new Mur(axisX,axisY+hauteur,base, Mur.Direction.BAS);
        liste.add(mur4);
    }
    public static void ajouterPiece( ArrayList<Piece> listPiece,int axisX,int axisY,int base,int hauteur){
        Piece piece = new Piece(axisX,axisY,base,hauteur);
        listPiece.add(piece);
    }


     public static int tirageMur(){
        int index=0;
        for (int i = 0; i < listeMurValide.size(); i++){
             index = (int)(Math.random() * listeMurValide.size());
        }
        return index;
    }

    public static int tiragePiece(){
        int index=0;
        for (int i = 0; i < listePieceSimple.size(); i++){
            index = (int)(Math.random() * listePieceSimple.size());
        }
        return index;
    }

    public static int nombreHasard(int inferieur,int superieur){
        return  (int) (Math.random() * (superieur - inferieur)) + inferieur;
    }
    public static boolean verificationPiece(String[][] Souterrain){
        for (int y = 0; y < listPiece.size(); y++){
            Piece piece = listPiece.get(y);
            for(int i=piece.axisX; i<piece.axisX+piece.base;i++){
                for(int j=piece.axisY;j<piece.axisY+piece.hauteur;j++){
                    if(Souterrain [i][j] =="x"){
                        return true;
                    }

                }

            }
        }
        return false;
    }


    public static void main(String[] args)  {
        File file;
        int stape = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter le nom du fichier: ");
        String fileName = scanner.nextLine();
        char[][] souterrain = new char[0][];
        boolean siPieceCentrale=true;
        try{
            file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                int base = Integer.parseInt(line.split(" ")[0]);
                int hauteur = Integer.parseInt(line.split(" ")[1]);
                switch (stape) {
                    case 0-> {
                        souterrain = new char[base][hauteur];
                        for (int i = 0; i < hauteur; i++) {
                            for (int j = 0; j < base; j++) {
                                souterrain[j][i] = 'O';
                            }
                        }
                        stape++;
                    }
                    case 1->{
                        try {
                            int y = (int) ((souterrain[0].length - hauteur) / 2);
                            int x = (int) ((souterrain.length - base) / 2);
                            for (int i = x; i < x + base; i++) {
                                for (int j = y; j < y + hauteur; j++) {
                                    souterrain[i][j] = '.';
                                }
                            }
                            ajouterPiece(listPiece, x, y, base, hauteur);
                            ajouterMur(listeMurValide, x, y, base, hauteur);
                            stape++;
                        }catch (Exception e){
                            siPieceCentrale = false;
                        }

                    }
                    default -> {
                        PieceSimple piece = new PieceSimple(base, hauteur);
                        listePieceSimple.add(piece);
                    }
                }
            } }catch(Exception ex) {
            System.out.println("Une erreur s'est produite avec le fichier "+fileName+". Vérifier qu'il est bien présent dans le répertoire et est bien formaté. ");
        }
        if(siPieceCentrale){
            int nombreEchec=0;
            while ( listePieceSimple.size()>0 && nombreEchec<100){
                int indexMur = tirageMur();
                int indexPiece = tiragePiece();
                PieceSimple piece = listePieceSimple.get(indexPiece);
                int base = piece.getBase();
                int hauteur = piece.getHauteur();
                Mur mur = listeMurValide.get(indexMur);
                try {
                    switch (mur.getDirection()){
                        case BAS -> {
                            String[][] copieSouterrain = new String[souterrain.length][souterrain[0].length];
                            for(int i =0; i < souterrain[0].length; i++){
                                for(int j = 0; j < souterrain.length; j++){
                                    copieSouterrain[j][i] = Character.toString(souterrain[j][i]);
                                }
                            }
                            for(int i = mur.axisX-1 ; i<mur.axisX+base+1 ;i++){
                                for(int j=mur.axisY;j< mur.axisY+2+hauteur;j++){
                                    copieSouterrain [i][j]="x";
                                }
                            }
                            if(verificationPiece(copieSouterrain)){
                                nombreEchec++;
                            }
                            else {
                                for(int i = mur.axisX ; i<mur.axisX+base ;i++){
                                    for(int j=mur.axisY+1;j<mur.axisY+1+hauteur;j++){
                                        souterrain [i][j]='.';
                                    }
                                }
                                if(base<mur.longueur){
                                    int y = mur.axisY;
                                    int x = nombreHasard(mur.axisX,mur.axisX+base);
                                    souterrain [x][y]='-';
                                }else {
                                    int y = mur.axisY;
                                    int x = nombreHasard(mur.axisX,mur.axisX+mur.longueur);
                                    souterrain [x][y]='-';
                                }
                                listeMurValide.remove(indexMur);
                                listePieceSimple.remove(indexPiece);
                                ajouterPiece(listPiece,mur.axisX,mur.axisY+1,base,hauteur);
                                ajouterMur(listeMurValide,mur.axisX,mur.axisY+1,base,hauteur);
                            }
                        }
                        case HAUT -> {
                            String[][] copieSouterrain = new String[souterrain.length][souterrain[0].length];
                            for(int i =0; i < souterrain[0].length; i++){
                                for(int j = 0; j < souterrain.length; j++){
                                    copieSouterrain[j][i] = Character.toString(souterrain[j][i]);
                                }
                            }
                            for(int i = mur.axisX ; i>mur.axisX-2-base ;i--){
                                for(int j=mur.axisY-1;j>mur.axisY-3-hauteur;j--){
                                    copieSouterrain [i][j]="x";
                                }
                            }

                            if(verificationPiece(copieSouterrain)){
                                nombreEchec++;
                            }
                            else {
                                for(int i = mur.axisX-1 ; i>mur.axisX-1-base ;i--){
                                    for(int j=mur.axisY-2;j>mur.axisY-2-hauteur;j--){
                                        souterrain [i][j]='.';
                                    }
                                }
                                if(base<mur.longueur){
                                    int y = mur.axisY-1;
                                    int x = nombreHasard(mur.axisX-base,mur.axisX);
                                    souterrain [x][y]='-';
                                }else {
                                    int y = mur.axisY-1;
                                    int x = nombreHasard(mur.axisX-mur.longueur,mur.axisX);
                                    souterrain [x][y]='-';
                                }
                                listeMurValide.remove(indexMur);
                                listePieceSimple.remove(indexPiece);
                                ajouterPiece(listPiece,mur.axisX-base,(mur.axisY-1)-hauteur,base,hauteur);
                                ajouterMur(listeMurValide,mur.axisX-base,(mur.axisY-1)-hauteur,base,hauteur);
                            }
                        }

                        case DROITE -> {
                            String[][] copieSouterrain = new String[souterrain.length][souterrain[0].length];
                            for(int i =0; i < souterrain[0].length; i++){
                                for(int j = 0; j < souterrain.length; j++){
                                    copieSouterrain[j][i] = Character.toString(souterrain[j][i]);
                                }
                            }
                            for(int i = mur.axisX ; i<mur.axisX+2+base ;i++){
                                for(int j=mur.axisY;j>(mur.axisY)-hauteur;j--){
                                    copieSouterrain [i][j]="x";
                                }
                            }

                            if(verificationPiece(copieSouterrain)){
                                nombreEchec++;
                            }
                            else {
                                for(int i = mur.axisX+1 ; i<(mur.axisX+1)+base ;i++){
                                    for(int j=mur.axisY-1;j>(mur.axisY-1)-hauteur;j--){
                                        souterrain [i][j]='.';
                                    }
                                }
                                if(hauteur<mur.longueur){
                                    int y =nombreHasard(mur.axisY-hauteur,mur.axisY);
                                    int x = mur.axisX;
                                    souterrain [x][y]='|';
                                }else {
                                    int x = mur.axisX;
                                    int y = nombreHasard(mur.axisY-mur.longueur,mur.axisY);
                                    souterrain [x][y]='|';
                                }
                                listeMurValide.remove(indexMur);
                                listePieceSimple.remove(indexPiece);
                                ajouterPiece(listPiece,mur.axisX+1,mur.axisY-hauteur,base,hauteur);
                                ajouterMur(listeMurValide,mur.axisX+1,mur.axisY-hauteur,base,hauteur);
                            }
                        }
                        case GAUCHE -> {
                            String[][] copieSouterrain = new String[souterrain.length][souterrain[0].length];
                            for(int i =0; i < souterrain[0].length; i++){
                                for(int j = 0; j < souterrain.length; j++){
                                    copieSouterrain[j][i] = Character.toString(souterrain[j][i]);
                                }
                            }
                            for(int i = mur.axisX-1 ; i>(mur.axisX-3)-base ;i--){
                                for(int j=mur.axisY-1;j<mur.axisY+hauteur+1;j++){
                                    copieSouterrain [i][j]="x";
                                }
                            }

                            if(verificationPiece(copieSouterrain)){
                                nombreEchec++;
                            }
                            else {
                                for(int i = mur.axisX-2 ; i>(mur.axisX-2)-base ;i--){
                                    for(int j=mur.axisY;j<mur.axisY+hauteur;j++){
                                        souterrain [i][j]='.';
                                    }
                                }
                                if(hauteur<mur.longueur){
                                    int y =nombreHasard(mur.axisY,mur.axisY+hauteur);
                                    int x = mur.axisX-1;
                                    souterrain [x][y]='|';
                                }else {
                                    int x = mur.axisX-1;
                                    int y = nombreHasard(mur.axisY,mur.axisY+mur.longueur);
                                    souterrain [x][y]='|';
                                }
                                listeMurValide.remove(indexMur);
                                listePieceSimple.remove(indexPiece);
                                ajouterPiece(listPiece,(mur.axisX-1)-base,mur.axisY,base,hauteur);
                                ajouterMur(listeMurValide,(mur.axisX-1)-base,mur.axisY,base,hauteur);
                            }
                        }
                    }
                }catch (Exception ex){}

            }
        }

            for(int i =0; i < souterrain[0].length; i++){
                for(int j = 0; j < souterrain.length; j++){
                    System.out.print(souterrain[j][i]);
                }
                System.out.println("");
            }
        }

    }