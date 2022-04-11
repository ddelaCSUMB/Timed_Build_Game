package stopwatch;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Stopwatch implements ActionListener
{
   //global variables
   JFrame frame = new JFrame();
   JButton startButton = new JButton("start");
   JButton resetButton = new JButton("reset");
   JLabel timeLabel = new JLabel();
   int timePassed = 0;
   int sec = 0;
   int min = 0;
   int hr = 0;
   boolean started = false;
   
   Timer timer = new Timer(1000, new ActionListener() 
      {
         public void actionPerformed(ActionEvent e) 
         {
            timePassed = timePassed + 1000;
            hr = (timePassed / 3600000); //3,600,000 miliseconds in hour
            min = (timePassed / 60000) % 60; //60,000 mili sec in min                                           
            sec = (timePassed / 1000) % 60;  // mod 60 so 1 doesnt show up
            
            String seconds_string =  String.format("%02d", sec);
            String minutes_string =  String.format("%02d", min);
            String hours_string =  String.format("%02d", hr);
            
            //displays the hour min and sec strings
            timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
            
            
         }
      });
   
   //strings to display hours mins and secs
   String seconds_string =  String.format("%02d", sec);
   String minutes_string =  String.format("%02d", min);
   String hours_string =  String.format("%02d", hr);
   
   
   Stopwatch()
   {
      
      timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
      timeLabel.setBounds(50, 50, 100, 50);
      timeLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
      timeLabel.setBorder(BorderFactory.createBevelBorder(2));
      timeLabel.setOpaque(true);
      timeLabel.setHorizontalAlignment(JTextField.CENTER);
      
      startButton.setBounds(50, 100, 50, 25);
      startButton.setFont(new Font("Ink Free", Font.PLAIN, 10));
      startButton.setFocusable(false);
      startButton.addActionListener(this);
      
      
      resetButton.setBounds(100, 100, 50, 25);
      resetButton.setFont(new Font("Ink Free", Font.PLAIN, 10));
      resetButton.setFocusable(false);
      resetButton.addActionListener(this);
      
      
      frame.add(startButton);
      frame.add(resetButton);
      frame.add(timeLabel);
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(200,200);
      frame.setLayout(null);
      frame.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      //if the start button clicked 
      if(e.getSource() == startButton)
      {
         start(); //calls method to start
         
         
         if(started == false)
         {
            //if program started changes boolean to true and start button to stop
            started = true;
            startButton.setText("stop");
            start();
         }
         else
         {
            started = false;
            startButton.setText("start");
            stop();
         }
      }
      
      //if reset button is clicked
      if(e.getSource() == resetButton)
      {
         started = false;
         startButton.setText("start");
         reset();
      }
      
   }
   
   void start()
   {
      timer.start();
   }
   
   void stop()
   {
      timer.stop();
   }
   
   
   void reset()
   {
      timer.stop();
      
      //set back to 0
      timePassed = 0;
      min = 0;
      sec = 0;
      hr = 0;
      
      //updating the strings
      String seconds_string =  String.format("%02d", sec);
      String minutes_string =  String.format("%02d", min);
      String hours_string =  String.format("%02d", hr);
      
      //update timelabel to display strings
      timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
   }
   
   //main method
    public static void main(String[] args)
   {
      Stopwatch stopwatch = new Stopwatch();
   }

}
