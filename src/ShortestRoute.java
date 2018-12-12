import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.lang.Math;

public class ShortestRoute extends JFrame{
    public static int[][] S;
    public static int n;
    
    public static void main(String[] args) throws Exception{
        readfile();
        for(int i = 0; i<n; i++){
            System.out.println(S[i][0] + "  " + S[i][1]);
        }
        nearest_neighbor();
        cross();
        
        double totdist = 0.0;
        for(int i = 0; i<n-1; i++){
            totdist+= dist(i,i+1);
        }
        System.out.println();
        System.out.println("TOTAL DISTANCE = " + totdist);
        ShortestRoute frame = new ShortestRoute();
        frame.setTitle("Shortest Route");
        frame.setSize(680,550);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public ShortestRoute(){
        add(new NewPanel());
    }
    
    public static void readfile(){
        String strline;
        String[] strarray = new String[2];
        try{
            Scanner in = new Scanner(new File("/Users/johnchandlerphillips/Desktop/THT2/problem1/problem1output2.txt"));
            strline = in.nextLine();
            n = Integer.parseInt(strline);
            S = new int [n][2];
            System.out.println("n =  " + n);
            for(int i = 0; i<n; i++){
                strline = in.nextLine();
                strarray = strline.split(",");
                S[i][0] = Integer.parseInt(strarray[0].trim());
                S[i][1] = Integer.parseInt(strarray[1].trim());
            }
            in.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void nearest_neighbor(){
        double mindist;
        int jmin;
        
        for(int i = 0; i<n-1; i++){
            mindist = 1.0E6;
            jmin = -1;
            for(int j = i+1; j<n; j++){
                if(dist(i,j)<mindist){
                    mindist = dist(i,j);
                    jmin = j;
                }
            }
            swap(i+1,jmin);
        }
    }
    
    public static void cross(){
        boolean done = false;
        
        while(!done){
            done = true;
            for(int i = 0; i<n-2; i++){
                for(int j = i+2; j<n-1; j++){
                    if(dist(i,i+1)+dist(j,j+1) > dist(i,j) + dist(i+1, j+1)){
                        reverse_all(i+1,j);
                        done = false;
                    }
                }
            }
        }
    }
    
    public static void reverse_all(int p, int q){
        while(p<q){
            swap(p,q);
            p+=1;
            q-=1;
        }
    }
    
    public static void swap(int p, int q){
        int tmpX;
        int tmpY;
        
        tmpX = S[p][0];
        tmpY = S[p][1];
        S[p][0] = S[q][0];
        S[p][1] = S[q][1];
        S[q][0] = tmpX;
        S[q][1] = tmpY;
    }
    
    public static double dist(int p, int q){
        return Math.sqrt(Math.pow((double)(S[p][0]-S[q][0]),2.0) +
                Math.pow((double)(S[p][1]-S[q][1]),2.0));
    }
    
    class NewPanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g;
            g.setColor(Color.BLUE);
            
            for(int i = 0;i<n-1; i++){
                g.drawLine(S[i][0], S[i][1], S[i+1][0], S[i+1][1]);
            }
            
            for(int i=0; i<n; i++){
                g.fillRect(S[i][0]-2,S[i][1]-2,4,4);
            }
        }
    }
    
}
