
import java.awt.Point;
import java.util.StringTokenizer;


public class CheckMove {
    BoardStructure boardStructure;

    public CheckMove(BoardStructure bs) {
        boardStructure=bs;
    }


    public void setNextMove(int playerId , String move ){ //Kaysar



        
        StringTokenizer stok = new StringTokenizer( move  );

        /*
        int Map[]= new int[26];

        Map['L'-'A']=constant.left;
        Map['R'-'A']=constant.right;
        Map['U'-'A']=constant.up;
        Map['D'-'A']=constant.down;
        */

        for (int pacid = 0; pacid < constant.noPacman; pacid++)
        {
            boardStructure.pacs[playerId][pacid].setDir(Integer.valueOf(stok.nextToken()));
            boardStructure.pacs[playerId][pacid].updateDir();
            boardStructure.pacs[playerId][pacid].updatePosition();
        }

        for (int gid = 0; gid < constant.noGhost; gid++)
        {
            boardStructure.ghosts[playerId][gid].setDir(Integer.valueOf(stok.nextToken()));
            boardStructure.ghosts[playerId][gid].updateDir();
            boardStructure.ghosts[playerId][gid].updatePosition();
        }

        for (int gid = 0; gid < constant.noGhost; gid++) {
            boardStructure.ghosts[playerId][gid].setBomb(Integer.valueOf(stok.nextToken()));
            boardStructure.ghosts[playerId][gid].updateBomb();
        }
    }
}
