
import java.awt.*;
import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.Soundbank;
import java.awt.Font;




	
public class BoardGui  extends JFrame  {

    
    public  BufferedImage  image,image2,pacp1,pacp1close,pacp2,pacp2close,ghostp1,ghostp2,obstacle,food,bombLarge,bombSmall,boardBackgroundImage;
   public  BufferedImage  normalFoodImage,negitiveFoodImage,background;
    public BufferedImage pacMouthOpenImg[][]=new BufferedImage[2][4];//2 player and  4 dirrectional image per player pac
   public BufferedImage pacMouthCloseImg[][]=new BufferedImage[2][4];
   int pacPrevDir[][]=new int[2][2]; 
   
    BoardStructure boardStructure;
    Controller  controller;
    boolean isMouthOpen=false;
    //public neo_calculation neoCalc=new neo_calculation();
    
    
    
    
  public int height;
  int width;
  
 // int obstacleArray[][];
          
     
   int positionX=0;
   int positionY=5;
     
  public BoardGui(BoardStructure bs,Controller controller) {
      
      boardStructure=bs;
      this.controller=controller;
      height=constant.gridHeight;
       width=constant.gridWidth;
       
       initGraphicsImg();
  /*
       Point pac11=new Point(width/2, 0);
   Point pac12=new Point(width/2, height-1);
   
   Point ghost11=new Point(0, 0);
   Point ghost12=new Point(0, 0);
   Point ghost13=new Point(0, 0);
   
   Point pac21=new Point(0, height/2);
   Point pac22=new Point(width-1,height/2);
   
   
   Point ghost21=new Point(0, 0);
   Point ghost22=new Point(0, 0);
   Point ghost23=new Point(0, 0);
   
   */
      
      
      
      
      new timerThread(this);
     
	showFrame();
         
  
    
  }
  private void initGraphicsImg(){
      
    

      try {
			String imageDir="images"+File.separator;
            
            background = ImageIO.read(new File(imageDir+"bgl.png"));
            ghostp1 = ImageIO.read(new File(imageDir+"ghostp1.png"));
            ghostp2=ImageIO.read(new File(imageDir+"ghostp2.png"));
            
            obstacle=ImageIO.read(new File(imageDir+"obstacle2.jpg"));
            
            food = ImageIO.read(new File(imageDir+"food.png"));
            bombLarge=ImageIO.read(new File(imageDir+"bombLarge.png"));
            bombSmall=ImageIO.read(new File(imageDir+"bombSmall.png"));
            normalFoodImage=ImageIO.read(new File(imageDir+"normalFood.png"));
            negitiveFoodImage=ImageIO.read(new File(imageDir+"negetiveFood1.png"));
            boardBackgroundImage=ImageIO.read(new File(imageDir+"carpet.jpg"));
            
            pacMouthOpenImg[0][0]=ImageIO.read(new File(imageDir+"cutepacp1l.png"));
            pacMouthOpenImg[0][1]=ImageIO.read(new File(imageDir+"cutepacp1r.png"));
            pacMouthOpenImg[0][2]=ImageIO.read(new File(imageDir+"cutepacp1u.png"));
            pacMouthOpenImg[0][3]=ImageIO.read(new File(imageDir+"cutepacp1d.png"));
            
            pacMouthOpenImg[1][0]=ImageIO.read(new File(imageDir+"cutepacp2l.png"));
            pacMouthOpenImg[1][1]=ImageIO.read(new File(imageDir+"cutepacp2r.png"));
            pacMouthOpenImg[1][2]=ImageIO.read(new File(imageDir+"cutepacp2u.png"));
            pacMouthOpenImg[1][3]=ImageIO.read(new File(imageDir+"cutepacp2d.png"));
          
             pacMouthCloseImg[0][0]=ImageIO.read(new File(imageDir+"cutepacp1closel.png"));
            pacMouthCloseImg[0][1]=ImageIO.read(new File(imageDir+"cutepacp1closer.png"));
            pacMouthCloseImg[0][2]=ImageIO.read(new File(imageDir+"cutepacp1closeu.png"));
            pacMouthCloseImg[0][3]=ImageIO.read(new File(imageDir+"cutepacp1closed.png"));
            
            pacMouthCloseImg[1][0]=ImageIO.read(new File(imageDir+"cutepacp2closel.png"));
            pacMouthCloseImg[1][1]=ImageIO.read(new File(imageDir+"cutepacp2closer.png"));
            pacMouthCloseImg[1][2]=ImageIO.read(new File(imageDir+"cutepacp2closeu.png"));
            pacMouthCloseImg[1][3]=ImageIO.read(new File(imageDir+"cutepacp2closed.png"));
          
            //0---left
        //1---right
        //3--up
        //2--down
    
                    
            
        } catch (IOException ex) {
            Logger.getLogger(BoardGui.class.getName()).log(Level.SEVERE, null, ex);
        }
      
      
  }
  
 /* private void initObstacleArray(){
    obstacleArray=new int[constant.gridWidth][constant.gridHeight];
    for (int i = 0; i < constant.gridWidth; i++) {
        for (int j = 0; j < constant.gridHeight; j++) {
            obstacleArray[i][j]=boardStructure.grid[i][j].obstacle;
        }
    }
  }
  
  */
 
  public static void main(String args[]) throws Exception {
		//new BoardGui(new BoardStructure());
		
  }
  
  void showFrame(){
    Dimension frameSize=new Dimension(1100,1000);  
    JFrame frame = new JFrame("BOT ENGINE");
    frame.setSize( frameSize);
    
    
    frame.addWindowListener(new SaveOnCloseWindowListener(controller));
    
    
    //Panel panel=new Project("projectkha.jpg");//new added
    Panel panel=new Panel();
     //ImageIcon icon1=new ImageIcon(image);
        
        JLabel label1=new DrawLabel();
        label1.setPreferredSize(frameSize);
        JScrollPane pane1=new JScrollPane(label1);
        frame.add(pane1,BorderLayout.CENTER);
       //pane1.setPreferredSize(new Dimension(frame.getWidth()/2-10,400));
	pane1.setPreferredSize(frameSize);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		//frame.getContentPane().add(panel);

    frame.setVisible(true);

  }
  

 

//drawclass is a internal class of BoardGui class

	 class DrawLabel extends JLabel {
	 	
	 	Color boardColor=new Color(50,50,50);
                Color boardBorderColor=new Color(0,250,50);
                Color boardBackGround=new Color(200,200,200);
                
                Color pac1Color= Color.ORANGE; //new Color(0,0,255);
                Color Ghos1tColor=Color.PINK;//new Color(255,100,50);
                
                Color pac2Color=Color.blue;//new Color(100,0,255);
                Color Ghos2tColor=Color.green;//new Color(200,100,0);
                
                Color obstacleColor=new Color(200,200,200);
                Color foodColor =new Color(200,200,0);
                Color specialFoodColor =Color.GRAY;
                Color specialFoodColor3= Color.MAGENTA;

                Color bombColor =new Color(255,0,0);
                Color specialPowerCol= Color.red;
                

             
             int width,height;
	 	Image image;
	 	int planeGap=10;
                int gridLength=constant.gridWidth;
                int cellSize=20;
                
                
	 	 
	 	
	 	layer_gui_data BoardData=new layer_gui_data("Battle field",1,gridLength,cellSize,100,20,planeGap);
	 	
                
	 	
	 	
	 	
	 	
	 	public DrawLabel(){
                   // obstacleArray=imgToData.obstacleArray;
                    
                  
                    
	 	}
	 	
	  
	
	 	public void paint(Graphics g) {
		    //g.drawImage( image, 0, 0, null);
		    //show line boundary
		   g.setColor(Color.BLACK);
		   //g.fillRect(0,0,2000,8000);
		    g.drawImage(background, TOP, TOP, this);
		    
		   // g.drawString("input layer",input.originX,input.originY-20);
		    //drawLayer(g,new Point(input.originX,input.originY),input.cellSize,input.numberOfPlane,input.cellPerPlane);
		    g.drawString("S1 layer",BoardData.originX,BoardData.originY-20);
		    
                    
                    drawBorard(g,new Point(BoardData.originX,BoardData.originY),BoardData.cellSize,BoardData.numberOfPlane,BoardData.cellPerPlane);
		    
					drawSpecialPower(g);
                   drawObstacle(1,g);
                   
		   
                   drawBomb(1,g);
                   drawFood(1,g);
                   drawAllGhost(g);
					drawAllPac(g);
                   
                   showScore(g);
                   
                }

            void showScore( Graphics g )
             {
                 int fontSize=20;
                 
				 g.setFont(new Font("TimesRoman", Font.ITALIC, fontSize));
                 //new Font("TimesRoman", Font.PLAIN, fontSize)
                 g.setColor(new Color(0,0,0));
                 g.drawString("Player1: "+boardStructure.score[0],gridLength*cellSize+120,(gridLength*cellSize)/2+20);
                 g.setColor(new Color(0,0,0));
                 g.drawString("Player2: "+boardStructure.score[1],gridLength*cellSize+120,(gridLength*cellSize)/2+fontSize+20);
				 g.drawString("Remaining ",gridLength*cellSize+120,(gridLength*cellSize)/2+2*fontSize+20);
				 g.drawString("Move: "+(constant.totalMove-Controller.totalMove),gridLength*cellSize+120,(gridLength*cellSize)/2+3*fontSize+20);
             }

	    
	    void drawBorard(Graphics g,Point origin,int cellSize,int totalPlane,int totalCell ){
                g.setColor(boardBackGround);
                //g.drawImage(obstacle, BoardData.originX,BoardData.originY, this);
                
                fill_board_with_img(totalCell, g);
                
                //g.fillRect(BoardData.originX,BoardData.originY, BoardData.cellPerPlane*BoardData.cellSize, BoardData.cellPerPlane*BoardData.cellSize);
                
                g.setColor(boardColor);
	    	int originX=origin.x;
		    int originY=origin.y;
		    int size=cellSize;
		    
                    
		    	 int j=originY;
                         g.setColor(boardColor);
                         for(int i=0;i<=totalCell;i++){
			    	g.drawLine(originX,j,originX+size*(totalCell),j);
			    	j+=size;
			 }
                        
                         g.drawLine(originX,originY,originX+size*(totalCell),originY);
			 g.drawLine(originX,originY+totalCell*size+1,originX+size*(totalCell),originY+totalCell*size+1);
                         
                        
                        j=originY;
                        for(int i=0;i<=totalCell;i++){
                            g.drawLine(originX+i*size,j,originX+i*size,j+(totalCell)*size);

                        }
                        
		    	 g.drawLine(originX,j,originX,j+(totalCell)*size);
                         g.drawLine(originX+totalCell*size+1,j,originX+totalCell*size+1,j+(totalCell)*size);
	    	
	    
            }

             void drawSpecialPower(Graphics g)
             {
				Point cellOrigin=new Point();

                 for(int i=0;i<BoardData.cellPerPlane;i++){
                        for(int j=0;j<BoardData.cellPerPlane;j++){
                                if(boardStructure.grid[i][j].power!=0){
                                        //fill_cell(i+1,j+1,specialPowerCol,g);
									cellOrigin.x=BoardData.originX+BoardData.cellSize*(i);
									cellOrigin.y=BoardData.originY+BoardData.cellSize*(j);
									if(isMouthOpen){
										g.drawImage(bombLarge,cellOrigin.x+1,cellOrigin.y+1,this);
									}
									else{
										g.drawImage(bombSmall,cellOrigin.x+1,cellOrigin.y+1,this);
									}

									repaint();
                                }
                        }
                }

             }

	    void drawObstacle(int layerInadex ,Graphics g){//find and save all output of cell of a layer
                Point cellOrigin=new Point();
                for(int i=0;i<BoardData.cellPerPlane;i++){
                        for(int j=0;j<BoardData.cellPerPlane;j++){
                                if(boardStructure.grid[i][j].obstacle==1){
                                   
                                    cellOrigin.x=BoardData.originX+BoardData.cellSize*(i);
                                    cellOrigin.y=BoardData.originY+BoardData.cellSize*(j);
                                    g.drawImage(obstacle, cellOrigin.x+1,cellOrigin.y+1, this);    
                                    //fill_cell(i+1,j+1,Color.CYAN,g);
                                }
                        }
                }

	    	
	    	
	    }

            void drawFood(int layerInadex ,Graphics g){//find and save all output of cell of a layer
                Point cellOrigin=new Point();

                for(int i=0;i<BoardData.cellPerPlane;i++){
                        for(int j=0;j<BoardData.cellPerPlane;j++){
                            cellOrigin.x=BoardData.originX+BoardData.cellSize*(i);
                            cellOrigin.y=BoardData.originY+BoardData.cellSize*(j);
                            if(boardStructure.grid[i][j].food==3){
                                       // fill_cellRound(i+1,j+1,foodColor,g);
                                        g.drawImage(normalFoodImage, cellOrigin.x+1,cellOrigin.y+1, this);

                                }
                                if(boardStructure.grid[i][j].food==2){
                                        //fill_cellRound(i+1,j+1,specialFoodColor,g);

                                    g.drawImage(negitiveFoodImage, cellOrigin.x+1,cellOrigin.y+1, this);
                                }

                                if(boardStructure.grid[i][j].food==1){
                                        //fill_cellRound(i+1,j+1,specialFoodColor3,g);
                                    g.drawImage(food, cellOrigin.x+1,cellOrigin.y+1, this);
                                }

                        }
                }
             }

              void drawBomb(int layerInadex ,Graphics g){//find and save all output of cell of a layer
                  
                Point cellOrigin=new Point();  
                for(int i=0;i<BoardData.cellPerPlane;i++){
                        for(int j=0;j<BoardData.cellPerPlane;j++){
                                if(boardStructure.grid[i][j].bomb!=0){
                                        //fill_cell(i+1,j+1,bombColor,g);
                                  
	  		
	  		cellOrigin.x=BoardData.originX+BoardData.cellSize*(i);
	  		cellOrigin.y=BoardData.originY+BoardData.cellSize*(j);
	  		if(isMouthOpen){
                            g.drawImage(bombLarge,cellOrigin.x+1,cellOrigin.y+1,this);
                        }
                        else{
                            g.drawImage(bombSmall,cellOrigin.x+1,cellOrigin.y+1,this);
                        }
	  		
	  		repaint();
                                }
                        }
                }
             }

              

	    

            
            void drawAllPac(Graphics g){
               
             

                for(int pacId=0;pacId<constant.noPacman;pacId++)
                {
                    
                    //fill_cell(boardStructure.pacs[0][pacId].currentPosition.x+1,boardStructure.pacs[0][pacId].currentPosition.y+1,pac1Color,g);
                    //fill_cell(boardStructure.pacs[1][pacId].currentPosition.x+1,boardStructure.pacs[1][pacId].currentPosition.y+1,pac2Color,g);
                    drawPac(boardStructure.pacs[0][pacId].currentPosition.x+1,boardStructure.pacs[0][pacId].currentPosition.y+1,0,pacId,g);
                    drawPac(boardStructure.pacs[1][pacId].currentPosition.x+1,boardStructure.pacs[1][pacId].currentPosition.y+1,1,pacId,g);
                }

            

            }
            void drawAllGhost(Graphics g){
                
                
                for(int ghostId=0;ghostId<constant.noGhost;ghostId++)
                {
                    //fill_cell(boardStructure.ghosts[0][ghostId].currentPosition.x+1,boardStructure.ghosts[0][ghostId].currentPosition.y+1,Ghos1tColor,g);
                    //fill_cell(boardStructure.ghosts[1][ghostId].currentPosition.x+1,boardStructure.ghosts[1][ghostId].currentPosition.y+1,Ghos2tColor,g);
                    drawGhost(boardStructure.ghosts[0][ghostId].currentPosition.x+1,boardStructure.ghosts[0][ghostId].currentPosition.y+1, 0, g);
                    drawGhost(boardStructure.ghosts[1][ghostId].currentPosition.x+1,boardStructure.ghosts[1][ghostId].currentPosition.y+1, 1, g);
                }

            
                
            }
	    
	    
	    
	  	void fill_cell(int x,int y,Color color,Graphics g){
                        g.setColor(color);
	  		Point cellOrigin=new Point();
	  		cellOrigin.x=BoardData.originX+BoardData.cellSize*(x-1);
	  		cellOrigin.y=BoardData.originY+BoardData.cellSize*(y-1);
	  		
	  		g.fillRect(cellOrigin.x+1,cellOrigin.y+1,BoardData.cellSize,BoardData.cellSize);
	  		repaint();
	  	}
                
                void fill_board_with_img(int totalCell,Graphics g){
                        
                    Point cellOrigin=new Point();
                    for(int i=0;i<totalCell;i++){
                        for(int j=0;j<totalCell;j++){
                               
                                    cellOrigin.x=BoardData.originX+BoardData.cellSize*(i);
                                    cellOrigin.y=BoardData.originY+BoardData.cellSize*(j);
                                    g.drawImage(boardBackgroundImage, cellOrigin.x+1,cellOrigin.y+1, this);    
                                    //fill_cell(i+1,j+1,obstacleColor,g);
                               
                        }
                }
                
	  	}

                void fill_cellRound(int x,int y,Color color,Graphics g){
                        g.setColor(color);
	  		Point cellOrigin=new Point();
	  		cellOrigin.x=BoardData.originX+BoardData.cellSize*(x-1);
	  		cellOrigin.y=BoardData.originY+BoardData.cellSize*(y-1);

	  		g.fillArc(cellOrigin.x+1,cellOrigin.y+1,BoardData.cellSize-1,BoardData.cellSize-1,0,180);
	  		repaint();
	  	}
                
                
                void drawPac(int x,int y,int playerId,int pacId,Graphics g){
                        
	  		Point cellOrigin=new Point();
                        Pacman pacman=boardStructure.pacs[playerId][pacId];
                        
	  		cellOrigin.x=BoardData.originX+BoardData.cellSize*(x-1);
	  		cellOrigin.y=BoardData.originY+BoardData.cellSize*(y-1);
	  		
                        int nxtDir=pacman.nxtDir;
                        
                        if(pacman.nxtDir==4){
                            nxtDir=pacPrevDir[playerId][pacId];
                        }
                        else {
                            pacPrevDir[playerId][pacId]=nxtDir;
                        }
	  		
                            if (isMouthOpen) {
                                
                                g.drawImage(pacMouthOpenImg[playerId][nxtDir], cellOrigin.x+1,cellOrigin.y+1, this);
                            } else {
                                g.drawImage(pacMouthCloseImg[playerId][nxtDir], cellOrigin.x+1,cellOrigin.y+1, this);
                        
                            }
                         /*   
                        else {
                            if (isMouthOpen) {
                                g.drawImage(pacp2, cellOrigin.x+1,cellOrigin.y+1, this);
                            } else {
                                g.drawImage(pacp2close, cellOrigin.x+1,cellOrigin.y+1, this);
                            }
                    }
	  		*/
                        
                       
                        repaint();
	  	}

	
                void drawGhost(int x,int y,int playerId,Graphics g){
                        
	  		Point cellOrigin=new Point();
	  		cellOrigin.x=BoardData.originX+BoardData.cellSize*(x-1);
	  		cellOrigin.y=BoardData.originY+BoardData.cellSize*(y-1);
	  		
	  		if (playerId==0) {
                            
                                g.drawImage(ghostp1, cellOrigin.x+1,cellOrigin.y+1, this);
                            
                        } else {
                                g.drawImage(ghostp2, cellOrigin.x+1,cellOrigin.y+1, this);
                            }
                  
                        repaint();
	  	}


	   
	 }
}
	 class layer_gui_data{
    	String layerName;
    	int numberOfPlane;
    	int cellPerPlane;
    	int cellSize;
    	int originX,originY;
    	int layerWidth;
    	int layerHeight;
    	int planeGap;
    	
    	public layer_gui_data(String Layername,int numofPlane,int cellperplane,int cellsize,int originx,int originy,int planegap){
    		layerName=Layername;
    		numberOfPlane=numofPlane;
    		cellPerPlane=cellperplane;
    		cellSize=cellsize;
    		originX=originx;
    		originY=originy;
    		planeGap=planegap;
    		layerWidth=cellPerPlane*cellSize;
    		layerHeight=cellPerPlane*cellSize*numberOfPlane+(numberOfPlane-1)*planeGap;
    	}
    }

class timerThread extends Thread{
    BoardGui boardGui;
    public timerThread(BoardGui bg) {
        boardGui=bg;
        start();
    }

    @Override
    public void run() {
        super.run();
        while(true){
            if (boardGui.isMouthOpen) {
            boardGui.isMouthOpen=false;
        }
        else{
            boardGui.isMouthOpen=true;
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            Logger.getLogger(timerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
        
    }
    
}




class SaveOnCloseWindowListener extends WindowAdapter {

    Controller controller;
    public SaveOnCloseWindowListener() {
    }
    
    public SaveOnCloseWindowListener(Controller controller) {
        this.controller=controller;
    }
    
    
    
    public void windowClosing(WindowEvent e) {
       
        
       controller.processes[0].destroy();
       controller.processes[1].destroy();
        
       // controller.closeAllControl();
        
        System.out.println("window is closing");
            //frame.dispose();
    }
}

