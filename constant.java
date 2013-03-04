import java.awt.Point;
import java.io.File;
public  class constant {
    static int gridHeight=70;
    static int gridWidth=70;


    //constant for food
    static int foodPos=1;
    static int foodNeg=2;
    static int foodPow=3;

    //constant for obstacle
    static int obstacle=1;
    static int open=0;

    static int up=0;
    static int down=1;
    static int left=2;
    static int right=3;


    static  int noPlayer=2;
    static  int noPacman=2;
    static  int noGhost=3;


    static int visibilityRange = 3;

    static int bombDuration=8;
    static int bombRange=2;
    static int waitTimeToSetBomb=20;
    static int obstacleFreezDuration=15;
    static int powerFieldDuration=5;


  //  static Point ghostCell[][] = {{new Point(gridWidth/2-30,gridHeight/2),new Point(gridWidth/2-20,gridHeight/2),new Point(gridWidth/2-15,gridHeight/2)},{new Point(gridWidth/2+30,gridHeight/2),new Point(gridWidth/2+20,gridHeight/2),new Point(gridWidth/2+15,gridHeight/2)}};
   // static Point InitPacPos[][] = {{new Point(gridWidth/2,0),new Point(0,gridHeight/2)},{new Point(gridWidth-1,gridHeight/2),new Point(gridWidth/2,gridHeight-1)}};

    static Point ghostCell[][] =new Point[2][3];
    static Point InitPacPos[][]=new Point[2][2];



    static int foodPosPoint=5; //increase power
    static int foodNegPoint=2; //decrease power
    static int foodPowPoint=20; //increase power
    static int InvalidMovePenalty=0;// add penalty if an invalid move occures
    static int timeOut=500;


    static int totalMove=101;



    static File playerDir[]= new File[2];
    static String language[]=new String[2];

}
