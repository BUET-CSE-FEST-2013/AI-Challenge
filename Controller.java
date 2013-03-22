
import com.sun.org.apache.xalan.internal.templates.Constants;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.io.CharToByteCp1006;



public class Controller {

    BoardStructure boardStructure=new BoardStructure();
    CheckMove checkMove = new CheckMove( boardStructure );
    BoardGui boardGui;
    Update update = new Update( boardStructure );
    Runtime rt = Runtime.getRuntime();
    Process processes[] = new Process[constant.noPlayer];
    BufferedReader input[]=new BufferedReader[constant.noPlayer];
    BufferedWriter output[]=new BufferedWriter[constant.noPlayer];
    File logFile;
    PrintWriter filePrintWriter;

    public Controller()  {
        boardGui=new BoardGui(boardStructure,this);
        myRun();
    }

    static int totalMove;

    public void myRun()
    {
       // openLogFile();

        boardStructure.setRandomFood(  );

        for(int playerId=0;playerId<constant.noPlayer;playerId++)
        {
            setConnection(playerId);
        }


        Scanner sc = new Scanner(System.in);



        for(totalMove=0;totalMove<constant.totalMove;totalMove++)
        {


            if(constant.debugMode==1)
            {
                sc.nextLine();
            }

            for(int playerId=0;playerId<2;playerId++)
            {

                //writeToLogFile();

                String gameConfigaration = totalMove + "\n" + boardStructure.getGameConfigaration(playerId);
              //  gameConfigaration+="end\n";
               // gameConfigaration+="77\n";


            //    System.out.println(gameConfigaration);

                sendConfigarationToPlayer( playerId , gameConfigaration  );
				
				if(playerId==0 && constant.debugMode==1)System.out.println("Input to Player"+playerId+":\n"+gameConfigaration);
                String nextMove = getNextMove(playerId);
                
                
                

                //     System.out.println(nextMove);

                checkMove.setNextMove(playerId, nextMove);
                update.updateFood(playerId);
                update.updateWaitTimeToSetBomb(playerId);

            }

            update.updateFreezSlot();
          //  update.updateObstacleSlot();
            update.specialFoodSlot();

        }

        closeAllControl();

      //  closeLogFile();

    }
	
	void closeAllControl()
    {
        for(int playerId=0;playerId<constant.noPlayer;playerId++)
        {
            try {
                input[playerId].close();
                output[playerId].close();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

            processes[playerId].destroy();
        }
    }
	


    void openLogFile()
    {
        logFile = new File( "log.txt" );
        try {
            filePrintWriter = new PrintWriter(logFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void closeLogFile()
    {
        filePrintWriter.flush();
        filePrintWriter.close();
    }


    void writeToLogFile()
    {


        for(int playerId=0;playerId<constant.noPlayer;playerId++)
        {
            for(int pacId=0;pacId<constant.noPacman;pacId++)
            {
                Pacman pac= boardStructure.pacs[playerId][pacId];
                filePrintWriter.println( pac.currentPosition.x + " " + pac.currentPosition.y );
            }

            for(int ghostId=0;ghostId<constant.noGhost;ghostId++)
            {
                Ghost ghost= boardStructure.ghosts[playerId][ghostId];
                filePrintWriter.println( ghost.currentPosition.x + " " + ghost.currentPosition.y );
            }

        }

        filePrintWriter.println();


        for(int i=0;i<constant.gridWidth;i++)
        {
            for(int j=0;j<constant.gridHeight;j++)
            {

                Field field = boardStructure.grid[ i ][ j ];
                filePrintWriter.print( field.obstacle +" " );

            }

            filePrintWriter.println();
        }

        filePrintWriter.println();


        for(int i=0;i<constant.gridWidth;i++)
        {
            for(int j=0;j<constant.gridHeight;j++)
            {

                Field field = boardStructure.grid[ i ][ j ];
                filePrintWriter.print( field.food +" " );

            }

            filePrintWriter.println();
        }


        filePrintWriter.println();


        for(int i=0;i<constant.gridWidth;i++)
        {
            for(int j=0;j<constant.gridHeight;j++)
            {

                Field field = boardStructure.grid[ i ][ j ];
                filePrintWriter.print( field.bomb +" " );

            }

            filePrintWriter.println();
        }


        filePrintWriter.println();



        for(int i=0;i<constant.gridWidth;i++)
        {
            for(int j=0;j<constant.gridHeight;j++)
            {

                Field field = boardStructure.grid[ i ][ j ];
                filePrintWriter.print( field.power +" " );

            }

            filePrintWriter.println();
        }


        filePrintWriter.println();


    }


     private void setConnection(int playerId)
    {
//        String fileDir = "build"+File.separator+"classes";//+File.separator+"player"+playerId;
        String fileDir =constant.playerDir[playerId].getName();

     //   System.out.println(fileDir);

//        String fileName = "Main";

  /*      String compileCmd = "javac " + fileDir + File.separator + fileName+".java";
        try {
            process = rt.exec(compileCmd);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        process.destroy();
*/
        String runCmd="";

        if(constant.language[playerId].compareToIgnoreCase("java")==0)
        {
            runCmd = "java -classpath " + fileDir + " " + "Main";
        }
        else if( constant.language[playerId].compareToIgnoreCase("cpp")==0 )
        {
            runCmd = fileDir + File.separator + "Main";
        }
    //    String runCmd = fileDir + "\\" + "Main";

        try {
            processes[playerId] = rt.exec(runCmd);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        input[playerId] = new BufferedReader(new InputStreamReader(processes[playerId].getInputStream()));
        output[playerId] = new BufferedWriter(new OutputStreamWriter(processes[playerId].getOutputStream()));
    }
    void sendConfigarationToPlayer(int playerId , String gameConfigaration)
    {
        try {
            output[playerId].write(gameConfigaration);
            output[playerId].flush();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String move;
    boolean alive;

    String getNextMove(int playerId)
    {
        move="";
        alive=true;

        ReadThread rt= new ReadThread("player"+playerId+"read", input[playerId]);
        rt.t.start();

        try {
            Thread.sleep(constant.timeOut);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        alive=false;

        String ret="";


        char []Map= new char[256];

        Map['L']='0';
        Map['R']='1';
        Map['U']='2';
        Map['D']='3';
        Map['F']='4';

        Map['Y']='1';
        Map['N']='0';
		
		if(playerId==0 && constant.debugMode==1)System.out.println("Response from Player"+playerId+":\n"+move);
		
		

        for (int i = 0; i < move.length(); i++) {
            char c = move.charAt(i);

            if(c>='A' && c<='Z')c=Map[c];

            ret+=c;

        }

       // System.out.println(move);
       // System.out.println(ret);


        
        return ret;
    }

    class ReadThread implements Runnable {
        String name;
        Thread t;
        BufferedReader br;

        ReadThread(String threadname,BufferedReader br) {
            name = threadname;
            t = new Thread(this, name);
            this.br=br;
        }

        public   void run() {

            move="F F F F F F F F";

            while( alive==true )
            {
                try {
                    if (br.ready() == false) {
                        continue;
                    }
                    move = br.readLine();
                    alive=false;
                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
           // if( move.compareTo("")==0 )

          //  System.out.println(move);
        }
    }


    class MemoryThread implements Runnable {
        String name;
        Thread t;
        int playerId;

        public MemoryThread(String name, int playerId) {
            this.name = name;
            this.playerId = playerId;
            t=new Thread( this,name );
        }




        public   void run() {


            while(  true  )
            {



            }


        }
    }


    public static void main(String[] args) {
        new Controller();
    }
}



