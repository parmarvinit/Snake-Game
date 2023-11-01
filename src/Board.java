import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener{
    private Image dot;
    private Image apple;
    private Image head;

    private final int All_Dots = 900;
    private final int Dot_Size = 10;
    private final int Random_Position = 29;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private int apple_x;
    private int apple_y;

    private boolean in_game = true;

    private int dots;
    private Timer timer;
    private final int x[] = new int[All_Dots];
    private final int y[] = new int[All_Dots];

    Board(){

        addKeyListener(new TAdapter());

        setBackground(new Color(0,0,0));
        setFocusable(true);
        setPreferredSize(new Dimension(400,400));

        loadImages();

        initGame();

    }

    public void loadImages(){

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("apple.png"));
        apple = i1.getImage();

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("dot.png"));
        dot = i2.getImage();
        
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("head.png"));
        head = i3.getImage();

    }

    public void initGame(){
        dots = 3;

        for(int i=0;i<dots;i++){
            y[i] = 50;
            x[i]=50 - i*Dot_Size;

        }

        locateApple();

        timer= new Timer(140,this);
        timer.start();
    }

    public void locateApple(){
        int  r =(int) (Math.random()*Random_Position);
        apple_x = r*Dot_Size;

        r =(int) (Math.random()*Random_Position);
        apple_y = r*Dot_Size;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        draw(g);
    }

    public void draw(Graphics g){
       
       if(in_game){
        g.drawImage(apple, apple_x, apple_y, this);

        for(int i=0; i<dots;i++){
            if(i == 0){
                g.drawImage(head, x[i], y[i],this);
            }else{
                g.drawImage(dot, x[i], y[i],this);
            }
        }

        Toolkit.getDefaultToolkit().sync();

    }else{
        gameOver(g);

        }
    }

    public void gameOver(Graphics g){
        String msg = "Game Over!";
        Font font = new Font("SAN_SERIF",Font.BOLD,16);
        FontMetrics metrices = getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (400 - metrices.stringWidth(msg))/2, 400/2);
    }
    
    public void move(){

        for(int i=dots;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
         
        if(leftDirection){
            x[0]=x[0] - Dot_Size;
        }
        if(rightDirection){
            x[0]=x[0] + Dot_Size;
        }
        if(upDirection){
            y[0]=y[0] - Dot_Size;
        }
        if(downDirection){
            y[0]=y[0] + Dot_Size;
        }

    }


    public void checkApple(){
        if((x[0]==apple_x) && (y[0]==apple_y)){
            dots++;
            locateApple();

        }
    }

    public void checkCollision(){

        for(int i=dots;i>0;i--){
            if((i>4) && (x[0] == x[i])  && (y[0] == y[i])){
                    in_game = false;
            }
        }

        if(y[0]>=400){
            in_game = false ;
        }
        if(x[0]>=400){
            in_game = false ;
        }
        if(y[0]<0){
            in_game = false ;
        }
        if(x[0]<0){
            in_game = false ;
        }

        if(!in_game){
            timer.stop();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(in_game){
        checkApple();

        checkCollision();

        move();
       }
        repaint();

    }
        public class TAdapter extends KeyAdapter{


            @Override
            public void keyPressed(KeyEvent e){

                int key  = e.getKeyCode();

                if(key == KeyEvent.VK_LEFT && (!rightDirection) ){
                    leftDirection = true;
                    upDirection = false;
                    downDirection = false;
                    
                }

                if(key == KeyEvent.VK_RIGHT && (!leftDirection) ){
                    rightDirection = true;
                    upDirection = false;
                    downDirection = false;
                    
                }

                if(key == KeyEvent.VK_UP && (!downDirection) ){
                    leftDirection = false;
                    upDirection = true;
                    rightDirection = false;
                    
                }

                if(key == KeyEvent.VK_DOWN && (!upDirection) ){
                    leftDirection = false;
                    rightDirection = false;
                    downDirection = true;
                    
                }
            }
    }

}
