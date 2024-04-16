import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
    private static int temperature = 100;
    private static int coolingRate = 2; 
    /**
     * @param args
     */
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
       
        int mapWidth=30 , mapHeight = 20;  

        Movimiento right = new Movimiento("RIGHT", -1);
        Movimiento left = new Movimiento("LEFT", -1);
        Movimiento down = new Movimiento("DOWN", -1);
        Movimiento up = new Movimiento("UP", -1);
        
        Movimiento mov= new Movimiento("", 0);
        Movimiento lastmov= new Movimiento("", 0);

        int xEnemigo=-1 , yEnemigo=-1, xplayer=-1, yplayer=-1;
        boolean[][] map = new boolean[mapHeight][mapWidth];

        iniciarMap(mapWidth,mapHeight,map);
        // game loop
        while (true) {
            int N = in.nextInt(); // total number of players (2 to 4).
            int P = in.nextInt(); // your player number (0 to 3).
            for (int i = 0; i < N; i++) {
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)
                map[Y0][X0]=true;
                map[Y1][X1]=true;
                    if(P==i){
                        xplayer=X1;
                        yplayer=Y1;
                    }else{
                        xEnemigo=X1;
                        yEnemigo=Y1;
                    }
            }
                        right.setPuntaje(manhattan(xplayer+1,yplayer , xEnemigo,  yEnemigo , map));
                        left.setPuntaje(manhattan(xplayer-1,yplayer , xEnemigo,  yEnemigo , map));
                        up.setPuntaje(manhattan(xplayer,yplayer-1 , xEnemigo,  yEnemigo , map));
                        down.setPuntaje(manhattan(xplayer,yplayer+1 , xEnemigo,  yEnemigo , map));
                        
                        //mov= decidirMovimientoHC(right,left,up,down);
                        mov=decidirMovimientoSA(right,left,up,down,lastmov);

            System.out.println(mov.getNombre()); // A single line with UP, DOWN, LEFT or RIGHT
            lastmov=mov;
        }
    }


    private static void iniciarMap(int mapWidth, int mapHeight, boolean[][] map ){
        for (int j = 0; j < mapHeight ; j++) {
        for (int i = 0; i < mapWidth; i++) {
        map[j][i] = false;}
        }
    }

    private static int manhattan (int myX ,int myY , int enX, int enY, boolean[][] map){
        int man= Math.abs(myX-enX) + Math.abs(myY-enY);
            if(myX==-1 || myY==-1 || myY==map.length || myX==map[0].length || map[myY][myX] ){
                man=man+50;
            }
        return man;
    }

    private static Movimiento decidirMovimientoSA(Movimiento r , Movimiento l, Movimiento u, Movimiento d, Movimiento lastMov){
        Random rand = new Random();
        int numeroAleatorio = rand.nextInt(4);
        Movimiento res= new Movimiento("", 0);
        Movimiento[] movs= {r,l,u,d};

        int energyDifference = movs[numeroAleatorio].getPuntaje() - lastMov.getPuntaje();

        if (energyDifference < 0 || rand.nextDouble() < Math.exp(-energyDifference / temperature)) {
            res= movs[numeroAleatorio];
        } else {
            res= lastMov;
        }
       
        if (temperature > 2){
        temperature= temperature - coolingRate;
        }
        
        return res;
    }


    private static Movimiento decidirMovimientoHC (Movimiento r , Movimiento l, Movimiento u, Movimiento d){
        //String mov="";
        Movimiento mov = new Movimiento("", 0);
            if(r.getPuntaje()==Math.min(r.getPuntaje(), l.getPuntaje()) && r.getPuntaje()==Math.min(r.getPuntaje(), u.getPuntaje()) && r.getPuntaje()==Math.min(r.getPuntaje(), d.getPuntaje())){
                //mov=r.getNombre();
                mov=r;
            }else{
                if(l.getPuntaje()==Math.min(u.getPuntaje(), l.getPuntaje()) && l.getPuntaje()==Math.min(l.getPuntaje(), d.getPuntaje())){
                     //mov=l.getNombre();
                     mov=l;
                }else{
                    if (u.getPuntaje()==Math.min(u.getPuntaje(), d.getPuntaje())) {
                        // mov=u.getNombre();
                        mov=u;

                    }else{//mov=d.getNombre();
                         mov=d; }
                }
            }
        return mov;
    }
}

class Movimiento {

    private  String nombre;
    private  int puntaje; 

    public Movimiento(String n, int p) {
        this.nombre = n;
        this.puntaje = p;
    }

    public int getPuntaje() {
        return this.puntaje;
    }

    public void setPuntaje(int p){
        this.puntaje = p;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String n){
        this.nombre = n;
    }
}