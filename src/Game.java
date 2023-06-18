import java.lang.Math;
import java.util.Random;
import java.util.Scanner;
public class Game {
    char player;
    char  opponent;

    static boolean  moneda;

    private static Game game;
    public Game(boolean eleccion){

           if (eleccion == true) {
               this.player = 'X';
               this.opponent = '0';
           } else {
               this.player = '0';
               this.opponent = 'X';
           }
           System.out.println( "El valor para mi es : "+ this.player +" tu valor es:  " + this.opponent);

    }
    public static Game getInstance(){
        if(game == null){
            Random numAleatorio = new Random();

            moneda = numAleatorio.nextBoolean();
            game= new Game(moneda);
        }
        return game;
    }
    public static void startGame(){

        Game game=Game.getInstance();
        IA   e=new IA();

        Move bestMove=null;


        char board[][]={{'_','_','_'},

                        {'_','_','_'},

                        {'_','_','_'}};
        printBoard(board);
        // Quien Empieza tirando boolean random
        if(moneda){
            board=game.tiradaOponente(board,game.opponent);
        }else {
            System.out.println("Yo comienzo");
            bestMove= game.findBestMove(board);
            board=game.makeMove(board,bestMove.row, bestMove.col,game.player);
            board=game.tiradaOponente(board,game.opponent);
        }


        while ( e.evaluate(board)==0) {

             bestMove= game.findBestMove(board);
             board=game.makeMove(board,bestMove.row, bestMove.col,game.player);
             if(e.evaluate(board)==10) System.out.println("Yo Gane");
             if(e.evaluate(board)==-10) System.out.println("Tu Ganaste");
             if(e.isMovesLeft(board)==false)System.out.println("Empate");
             if (e.evaluate(board)!=0) break;
             board=game.tiradaOponente(board,game.opponent);
             e.evaluate(board);
             if(e.evaluate(board)==10) System.out.println("Yo Gane");
             if(e.evaluate(board)==-10) System.out.println("Tu Ganaste");

       }

    }
    public  char[][] tiradaOponente(char[][] board,char caracter){
        Scanner lectura = new Scanner(System.in);
        int fila=-1;
        int col=-2;


        while (true) {

            System.out.println("Ingresa de nuevo, Ingresa Movmiento");
            System.out.println("Ingrese el numero fila");
            fila= lectura.nextInt();
            System.out.println("Ingrese el numero columna");
            col = lectura.nextInt();
            if(board[fila][col]=='_'){
                board=game.makeMove(board,fila,col,caracter);
                System.out.println("Movimiento Valido");
                break;
            }else {
                System.out.println("Movimiento Invalido");
            }
        }
        return board;

    }
    public static void printBoard(char[][] b){
        System.out.println("col 0 1 2 ");
        for (int indx=0;indx<3;indx++){
            for (int jx=0;jx<3;jx++){
                System.out.print( b[indx][jx]+" " );
            }
            System.out.println(" Fila  "+indx);
        }
        System.out.println(" ");
    }
    public char[][] makeMove(char[][] caracteres,int i,int j,char move){


        caracteres[i][j]=move;
        printBoard(caracteres);

        return caracteres;
    }
    public Move findBestMove(char board[][]){
        int bestVal     =    -1000; //tomar el valor mas negativo
        Move bestMove   =    new    Move();
        bestMove.row    =   -1;
        bestMove.col    =   -1;  //Inicializa los valores en lugares fuera del tablero

        IA  ia=new IA();

        //Checar todas las celdas y evaluar ka funcion minimax para todas las celdas vacias
        for (int i=0; i<3;i++){
            for (int j=0 ; j<3 ;j++ ){

                //Buscar si esta vacio
                if(board[i][j]  ==  '_'){
                    //escribe el valor del jugador

                    board[i][j]=player;

                    int moveVal  = ia.minimax(board,0,false);

                    //desacer el movimiento

                    board[i][j]  ='_';

                    //comparando movimientos y viendo el mas optimo

                    if(moveVal    >  bestVal){

                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;

                    }
                }
            }
        }



        return bestMove;
    }

}
