
import java.awt.Point;
import java.io.File;


public class BoardStructure {

    public Field grid[][];
    public Pacman pacs[][];
    public Ghost ghosts[][];
    public int score[]=new int[2];

    ImageToRawData imgToData=new ImageToRawData("images"+File.separator+"board1.jpg");

    public BoardStructure() {
        initSomeConstant();
        initGrid();
        initPiece();
        initBoardWithObstacle();

    }

    void initGrid()
    {

      //  ImageToRawData imgToData=new ImageToRawData("board1.jpg");



        grid=new Field[constant.gridWidth][constant.gridHeight];
        for (int i = 0; i < constant.gridWidth; i++) {
            for (int j = 0; j < constant.gridWidth; j++) {
                grid[i][j]= new Field();
            }
        }
    }



    public void initSomeConstant(){

        constant.gridWidth=imgToData.imageArray.length;
        constant.gridHeight=imgToData.imageArray[0].length;

        int gridWidth=constant.gridWidth;
        int gridHeight=constant.gridHeight;

        constant.ghostCell[0][0] =new Point(gridWidth/2-2,gridHeight/2);
        constant.ghostCell[0][1]=new Point(gridWidth/2-1,gridHeight/2);
        constant.ghostCell[0][2]=new Point(gridWidth/2-0,gridHeight/2);

        constant.ghostCell[1][0] =new Point(gridWidth/2+2,gridHeight/2);
        constant.ghostCell[1][1]=new Point(gridWidth/2+3,gridHeight/2);
        constant.ghostCell[1][2]=new Point(gridWidth/2+4,gridHeight/2);



        constant.InitPacPos[0][0] = new Point(gridWidth/2,0+1);
        constant.InitPacPos[0][1]=new Point(0,gridHeight/2-1);

        constant.InitPacPos[1][0]=new Point(gridWidth-1,gridHeight/2-1);
        constant.InitPacPos[1][1]=new Point(gridWidth/2,gridHeight-1-1);

     //   constant.InitPacPos[0][0] = new Point(0,1);

    }



    void initPiece()
    {
       pacs=new Pacman[constant.noPlayer][constant.noPacman];
       for (int i = 0; i < constant.noPlayer; i++) {
            for (int j = 0; j < constant.noPacman; j++) {
                pacs[i][j]= new Pacman(this);
                pacs[i][j].currentPosition=new Point( constant.InitPacPos[i][j] );
                pacs[i][j].player=i;
            }
        }

        ghosts = new Ghost[constant.noPlayer][constant.noGhost];
        for (int i = 0; i < constant.noPlayer; i++) {
            for (int j = 0; j < constant.noGhost; j++) {

                ghosts[i][j]= new Ghost(this);
                ghosts[i][j].currentPosition= new Point(constant.ghostCell[i][j]);
                ghosts[i][j].player=i;
            }
       }
    }

    public void initBoardWithObstacle(){

     //   int boardWidth=imgToData.imageArray.length;
       // int boardHeight=imgToData.imageArray[0].length;

        for (int i = 0; i <constant.gridWidth ; i++) {
            for (int j = 0; j <constant.gridHeight; j++) {
                grid[i][j].obstacle=imgToData.imageArray[i][j];
            }
        }
    }


    String getGameConfigaration( int playerId )
    {


        int oponent=playerId^1;
        String gameConfigaration="";

        String pieceConfigaration="";



        gameConfigaration+=score[playerId]+" "+score[oponent]+"\n";


        for(int pacId=0;pacId<constant.noPacman;pacId++)
        {
            int x = pacs[ playerId ][ pacId ].currentPosition.x;
            int y = pacs[ playerId ][ pacId ].currentPosition.y;


            pieceConfigaration+=x+" "+y+"\n";
        }

        for(int ghostId=0;ghostId<constant.noGhost;ghostId++)
        {

            int x = ghosts[ playerId ][ ghostId ].currentPosition.x;
            int y = ghosts[ playerId ][ ghostId ].currentPosition.y;

            pieceConfigaration+=x+" "+y+"\n";
        }

        for(int pacId=0;pacId<constant.noPacman;pacId++)
        {
            int x = pacs[ oponent ][ pacId ].currentPosition.x;
            int y = pacs[ oponent ][ pacId ].currentPosition.y;


            pieceConfigaration+=x+" "+y+"\n";
        }

        for(int ghostId=0;ghostId<constant.noGhost;ghostId++)
        {

            int x = ghosts[ oponent ][ ghostId ].currentPosition.x;
            int y = ghosts[ oponent ][ ghostId ].currentPosition.y;

            pieceConfigaration+=x+" "+y+"\n";
        }



        gameConfigaration+=pieceConfigaration;


        for(int pacId=0;pacId<constant.noPacman;pacId++)
        {

            int x = pacs[ playerId ][ pacId ].currentPosition.x;
            int y = pacs[ playerId ][ pacId ].currentPosition.y;


            String boardConfigaration="";


            String foodConfigaration = "";
            String bombConfigaration = "";
            String powerConfigaration = "";
            String obstacleConfigaration = "";



            for (int j = -constant.visibilityRange; j <= +constant.visibilityRange ; j++) {
                for (int i = -constant.visibilityRange ; i <= +constant.visibilityRange ; i++) {

                    if(i!=-constant.visibilityRange)
                    {
                        foodConfigaration += ' ';
                        bombConfigaration+= ' ';
                        powerConfigaration+= ' ';
                        obstacleConfigaration+= ' ';
                    }


                    int xx=(x+i+constant.gridWidth)%constant.gridWidth;
                    int yy=(y+j+constant.gridHeight)%constant.gridHeight;
/*
                    if(isInGrid(new Point( x + i ,y + j  )) == false)
                 //     if(isInGrid(new Point( y + i ,x + j  )) == false)
                    {


                        foodConfigaration+=String.valueOf(-1);
                        bombConfigaration+=String.valueOf(-1);
                        powerConfigaration+=String.valueOf(-1);
                        obstacleConfigaration+=String.valueOf(-1);
                        continue;
                    }
  */


                    Field field = grid[ xx ][ yy ];
                   //  Field field = grid[ y+i ][ x+j ];



                    foodConfigaration+=field.food;
                    bombConfigaration+=field.bomb;
                    powerConfigaration+=field.power;
                    obstacleConfigaration+=String.valueOf(field.obstacle);

                }

                foodConfigaration += '\n';
                bombConfigaration+= '\n';
                powerConfigaration+='\n';
                obstacleConfigaration+= '\n';

        }


        boardConfigaration+= foodConfigaration+"\n";
        boardConfigaration+= bombConfigaration+"\n";
        boardConfigaration+=powerConfigaration+"\n";
        boardConfigaration+=obstacleConfigaration+"\n";


        gameConfigaration+=boardConfigaration+"\n";

     }






        for(int ghostId=0;ghostId<constant.noGhost;ghostId++)
        {

            int x = ghosts[ playerId ][ ghostId ].currentPosition.x;
            int y = ghosts[ playerId ][ ghostId ].currentPosition.y;


            String boardConfigaration="";

            String foodConfigaration = "";
            String bombConfigaration = "";
            String powerConfigaration = "";

            String obstacleConfigaration = "";



            for (int j = -constant.visibilityRange; j <= +constant.visibilityRange ; j++) {
                for (int i = -constant.visibilityRange ; i <= +constant.visibilityRange ; i++) {

                    if(i!=-constant.visibilityRange)
                    {
                        foodConfigaration += ' ';
                        bombConfigaration+= ' ';
                        powerConfigaration+= ' ';
                        obstacleConfigaration+= ' ';
                    }


                    int xx=(x+i+constant.gridWidth)%constant.gridWidth;
                    int yy=(y+j+constant.gridHeight)%constant.gridHeight;
/*
                    if(isInGrid(new Point( x + i ,y + j  )) == false)
                  //  if(isInGrid(new Point( y + i ,x + j  )) == false)
                    {


                        foodConfigaration+= String.valueOf(-1);
                        bombConfigaration+= String.valueOf(-1);
                        powerConfigaration+= String.valueOf(-1);
                        obstacleConfigaration+= String.valueOf(-1);
                        continue;
                    }
*/
                    Field field = grid[ xx ][ yy ];
                //     Field field = grid[ y+i ][ x+j ];


                    foodConfigaration+=field.food;
                    bombConfigaration+=field.bomb;
                    powerConfigaration+=field.power;
                    obstacleConfigaration+=String.valueOf(field.obstacle);

                }

                foodConfigaration += '\n';
                bombConfigaration+= '\n';
                powerConfigaration+='\n';
                obstacleConfigaration+= '\n';

        }





        boardConfigaration+= foodConfigaration+"\n";
        boardConfigaration+= bombConfigaration+"\n";
        boardConfigaration+= powerConfigaration+"\n";
        boardConfigaration+=obstacleConfigaration+"\n";

        gameConfigaration+=boardConfigaration+"\n";

        }








     //   System.out.println("start\n"+gameConfigaration+"end");

        return gameConfigaration;
    }

    public boolean bombFieldCheck(Point p)
    {
      //  return false;
       return grid[p.x][p.y].bomb!=0;
    }
    public boolean  powerFieldCheck(Point p)
    {
        return grid[p.x][p.y].power!=0;
    }


    public boolean IsObstacle(Point p){
       return grid[p.x][p.y].obstacle==1 || grid[p.x][p.y].obstacle==2 ; // ekta slot por obstacle asbe .. to protect from overlapping
    }



    public boolean isInGrid( Point p )
    {

        if( p.x<0 )return false;
        if( p.y<0 )return false;
        if( p.x>=constant.gridWidth )return false;
        if( p.y>=constant.gridHeight )return false;

        return true;
    }

    public boolean isFree( Point p )
    {


        if( isInGrid(p) == false ) return false;
        if( IsObstacle(p)== true ) return false;


        return true;

    }

    public boolean isOccupied( Point p )
    {

        for(int playerId=0;playerId<constant.noPlayer;playerId++)
        {
            for(int pacId=0;pacId<constant.noPacman;pacId++)
            {
                Pacman pacman=pacs[playerId][pacId];
                if( pacman.currentPosition.x == p.x && pacman.currentPosition.y == p.y  )
                {
                    return true;
                }
            }
        }

        for(int playerId=0;playerId<constant.noPlayer;playerId++)
        {
            for(int ghostId=0;ghostId<constant.noGhost;ghostId++)
            {
                Ghost ghost=ghosts[playerId][ghostId];
                if( ghost.currentPosition.x == p.x && ghost.currentPosition.y == p.y  )
                {
                    return true;
                }
            }
        }

        return false;

    }



    public void setRandomFood()
    {

        for (int i = 0; i < grid.length; i++) {
            Field[] fields = grid[i];
            for (int j = 0; j < fields.length; j++) {

                Field field = fields[j];
                if( IsObstacle(new Point(i,j)) )
                {
                    field.food=0;
                    continue;
                }


                double randVal=Math.random();

                if(randVal>.85){
                    if( randVal<.94 )field.food=constant.foodPos;
                    else field.food=constant.foodNeg;
                }
                else if(randVal<.01)
                {
                    field.food = constant.foodPow;
                }


            }
        }

    }


    void updateScore(int playerId,int foodType,Pacman pacman)
    {
        if( foodType==constant.foodPow )
        {
            pacman.power=constant.powerFieldDuration;
            score[playerId]+=constant.foodPowPoint;
            return;
        }
        if( foodType==constant.foodPos )score[playerId]+=constant.foodPosPoint;
        if( foodType==constant.foodNeg )score[playerId]-=constant.foodNegPoint;
    }

    void addPenalty( int playerId )
    {
        score[playerId]-=constant.InvalidMovePenalty;
    }

    void updateFood(int playerId ,Point currentPosition, Pacman pacman )
    {
      //  System.out.println(currentPosition.x +" "+ currentPosition.y );
        if( grid[currentPosition.x][currentPosition.y].food !=0 )
        {
            updateScore(playerId,grid[currentPosition.x][currentPosition.y].food,pacman);
            grid[currentPosition.x][currentPosition.y].food=0;
        }
    }
}

class Field{

    int bomb=0;
    int pacman=0;
    int food=0;
    int obstacle=0;
    int ghost=0;
    int power=0;


    public Field() {

    }

    public Field(int b,int p, int f, int o,int g){
        this.bomb=b;
        this.pacman=p;
        this.food=f;
        this.obstacle=o;
        this.ghost=g;

    }
}
