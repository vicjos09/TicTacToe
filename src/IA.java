public class IA {

    char player;
    char opponent;
    public IA(){
        if(Game.getInstance().player=='X') {
            this.player = 'X';
            this.opponent = '0';
        } else {
            this.player = '0';
            this.opponent = 'X';
        }
    }
    public int minimax(char board[][],int depth, Boolean isMax){
        //checar el estado del board
        int score = evaluate(board);

        if(score == 10) return score;  // si gana el max
        if(score == -10) return score; // si gana el min
        if(isMovesLeft(board) == false) return 0;  //empate


        if(isMax){
                    int best=-1000;
                    for(int i  = 0  ; i<3 ; i++){
                        for(int j = 0;  j<3 ;j++){

                            if(board[i][j]=='_'){

                                board[i][j]  = player;

                                best = Math.max(best, minimax(board, depth + 1, !isMax));

                               // Deshacer Movmiento
                                board[i][j] = '_';
                            }



                        }
                    }
                return best;

        }
        else
        {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {

                    if (board[i][j] == '_')
                    {

                        board[i][j] = opponent;

                        best = Math.min(best, minimax(board, depth + 1, !isMax));


                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }


    }


    public int evaluate(char b[][])
    {
        // Victoria
        for (int row = 0; row < 3; row++)
        {
            if (b[row][0] == b[row][1] &&
                    b[row][1] == b[row][2])
            {
                if (b[row][0] == player)
                    return +10;
                else if (b[row][0] == opponent)
                    return -10;
            }
        }

        // Victoria en columna
        for (int col = 0; col < 3; col++)
        {
            if (b[0][col] == b[1][col] &&
                    b[1][col] == b[2][col])
            {
                if (b[0][col] == player)
                    return +10;

                else if (b[0][col] == opponent)
                    return -10;
            }
        }

        // victoria en diagonal
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2])
        {
            if (b[0][0] == player)
                return +10;
            else if (b[0][0] == opponent)
                return -10;
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0])
        {
            if (b[0][2] == player)
                return +10;
            else if (b[0][2] == opponent)
                return -10;
        }

        // si nadie gano cero
        return 0;
    }
    Boolean isMovesLeft(char board[][])
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '_')
                    return true;
        return false;
    }
}
