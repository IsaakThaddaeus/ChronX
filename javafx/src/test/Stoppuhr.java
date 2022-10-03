package test;
import java.awt.*;
import java.awt.event.*;

public class Stoppuhr extends Frame {

  private Button start;
  private Button stop;
  private Label ausgabe;
  private boolean running = false;

  public Stoppuhr(){
    super();
    setLayout(null);
    setTitle("Stoppuhr");
    setResizable(false);
    start = new Button("Start");
    stop = new Button("Stop");
    ausgabe = new Label();

    start.setBounds(100,100,100,50);
    stop.setBounds(220, 100, 100, 50);
    ausgabe.setBounds(130, 175, 250, 50);

    add(start);
    add(stop);
    add(ausgabe);
    ausgabe.setFont(new Font(Font.SERIF,Font.PLAIN, 35));

    start.addActionListener(new ButtonListenerStart());
    stop.addActionListener(new ButtonListenerStop());

    addWindowListener(new WindowLauscher());

  }

  class ButtonListenerStart implements ActionListener{
    public void actionPerformed(ActionEvent e){
      if(!running){
        running = true;
        UhrzeitThread uhr = new UhrzeitThread();
        uhr.start();
      }
    }
  }

  class ButtonListenerStop implements ActionListener{
    public void actionPerformed(ActionEvent e){
      running = false;
    }
  }

  class WindowLauscher extends WindowAdapter{
    public void windowClosing(WindowEvent e){
      running = false;
      System.exit(0);
    }
  }

  class UhrzeitThread extends Thread{
    private int HS = 0;
    private int sek = 0;
    private int min = 0;
    private int h = 0;

    public void run(){
      while(running){
        try{Thread.sleep(9);}catch(Exception e){}
        if(HS <= 99){
          HS++;
        } else  {
          HS = 0;
          if(sek <= 59){
            sek++;
          }else {
            sek = 0;
            if(min <= 59){
              min++;
            } else {
              min = 0;
              h++;
            }
          }
        }
        ausgabe.setText(h + " : " + min + " : " + sek + " : " + HS);
      }
    }
  }

  public static void main(String[] args) {
    Stoppuhr uhr = new Stoppuhr();
    uhr.setBounds(0, 0, 500, 300);
    uhr.setVisible(true);
  }
}