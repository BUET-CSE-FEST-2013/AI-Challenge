import java.awt.image.BufferedImage;

public class RGBToBlackAndWhite {
    
 	private BufferedImage rgbImage,output;
	private int width,height;
	public int threshold;
	private boolean[][] bwArray;
        private int[][] RGBArray;
	private  int []counts;
	private float totalCount;
	private float []intensity;
	private float[] omega;
	private float []mu;
	private float muLast;
	private int [][]GrayArray;

	final float redModifier = (float)0.298;
	final float greenModifier = (float)0.587;
	final float blueModifier = (float)0.114;

 	public RGBToBlackAndWhite(BufferedImage img){
 		rgbImage=img;
 		width=rgbImage.getWidth();
 		height=rgbImage.getHeight();
                initRGBArray();
 		threshold=GetThresholed();
 		GetBinaryArray(threshold);
 	}
        
        public RGBToBlackAndWhite(double imgMapArray[][]){
 		if(imgMapArray==null)
                    System.exit(1);
 		width=imgMapArray.length;
 		height=imgMapArray[0].length;
                initRGBArray(imgMapArray);
 		threshold=GetThresholed();
                System.out.println("threshosld: "+threshold);
 		GetBinaryArray(threshold+10);
 	}
        
        
        private void initRGBArray(double imgMapArray[][]){
           RGBArray=new int[width][width]; 
           for( int i = 0; i < width; i++ ){
            for ( int j = 0; j < height; j++ ){
                int grayC=(int)imgMapArray[i][j];
                grayC= 0xff000000|grayC<<16|grayC<<8|grayC;
                
                RGBArray[i][j] =grayC;
             
            }
            
           }
        }
        
        private void initRGBArray(){
           RGBArray=new int[width][width]; 
           for( int i = 0; i < width; i++ ){
            for ( int j = 0; j < height; j++ ){

               RGBArray[i][j] =rgbImage.getRGB(i,j) ;
            }
           }
        }
        
        
        
	public int getThreshold(){
		return threshold;
	}
        
        
        private void getHistogramData()
        {
                counts = new int[256];

                for (int i = 0; i < 256; i++ ){
                        counts[i] = 0;
                }

                int max = 0;
                for( int i = 0; i < width; i++ )
                        for ( int j = 0; j < height; j++ ){

                                
                                int pixel =RGBArray[i][j] ;
                                counts[(pixel >> 16) & 0xff]++; //red
                                counts[(pixel >>  8) & 0xff]++;	//green
                                counts[(pixel      ) & 0xff]++; //blue
                        }
        }

        private void processHistogramData()
        {
                int i;
                totalCount = 0;


                for( i = 0; i < 256; i++ ){
                        totalCount += counts[i];
                }

                intensity = new float[256];
                omega = new float[256];
                mu = new float[256];

                intensity[0] = counts[0]/totalCount;
                omega[0] = intensity[0];
                mu[0] = intensity[0]*1;

                for( i = 1; i < 256; i++ ){
                        intensity[i] = counts[i]/totalCount;
                        omega[i] = omega[i-1] + intensity[i];
                        mu[i] = mu[i-1] + intensity[i]*(i+1);
                }
                muLast = mu[255];
        }


        private int GetThresholed()
        {
                getHistogramData();

                processHistogramData();

                float level = 0;

                double []sigma_b_squared = new double[256];
                double maxval = 0;

                for (int i = 0; i < 255; i++ ){
                        float div= omega[i] * (1 - omega[i]);
                        if (div == 0)
                                sigma_b_squared[i] = -1;
                        else
                        {
                                float tmp = (muLast * omega[i] - mu[i]);
                                sigma_b_squared[i] = (tmp*tmp)/div;
                        }

                        if (maxval < sigma_b_squared[i] )
                                maxval = sigma_b_squared[i];
                }

		int count = 0;
		int sumI = 0;

		if (maxval > -1 ){
			for (int i = 0; i < 256; i++){
				if(maxval == sigma_b_squared[i] ){
					sumI += i+1;
					count++;
				}
			}
			float idx = sumI/count;
			level = (idx - 1)/255;
		}
		else{
			level = 0;
		}
		return (int)(level*255);
	}

        public void getGrayImageArray ()
        {
                int[][] GrayArray=new int[width][height];

                for( int i = 0; i < width; i++ ){

                        for ( int j = 0; j <height; j++ ){

                                int pixel =RGBArray[i][j] ;
                                int red = (pixel >> 16) & 0xff;
                                int green = (pixel >>  8) & 0xff;
                                int blue = (pixel      ) & 0xff;

                                int grayC = (int)(redModifier*red + greenModifier*green + blueModifier*blue);

                                GrayArray[i][j] = grayC;
                        }
                }
                this.GrayArray= GrayArray;
        }

        public void GetBinaryArray( float level )
        {

                boolean [][]bwarray = new boolean [width][height];
                getGrayImageArray ();
                int grayC;
                for ( int xVal = 0; xVal < width; xVal++ )
                {
                        for( int yVal = 0; yVal < height; yVal++ )
                        {
                                grayC = GrayArray[xVal][yVal];
                                if ( grayC >= level ){
                                        bwarray[xVal][yVal] = true;
                                }
                                else{
                                        bwarray[xVal][yVal] = false;
                                }
                        }
                }
                this.bwArray=bwarray;
        }


	public BufferedImage getBlackAndWhiteImage(){
	  	 output=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY);
	  	for(int x=0;x<width;x++){
	  		for(int y=0;y<height;y++){
	  			if(bwArray[x][y]==true)
	  				output.setRGB(x,y,mixColor(255,255,255));
	  			else output.setRGB(x,y,mixColor(0,0,0));
	  		}
	  	}

	  	return output;
  	}
    private  int mixColor(int red, int green, int blue) {
		return red<<16|green<<8|blue;
	}

}




    

