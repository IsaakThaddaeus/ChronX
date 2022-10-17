package test;

public class UhrzeitThread extends Thread {

    
        private int HS = 0;
        private int sek = 0;
        private int min = 0;
        private int h = 0;

        boolean running;

        public UhrzeitThread(boolean running){
                this.running = running;
        }
    
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
            System.out.println("" + h + " : " + min + " : " + sek + " : " + HS);
          }
        
      }
    
}
