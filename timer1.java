package timers;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class timer1 extends JFrame implements ActionListener, Runnable
{
 JLabel display;
 JButton button;
 boolean stop = false;
 int i, j, k, l;
 
 public timer1()
 {
  display = new JLabel();
  button = new JButton("start");
  display.setFont(new Font("Helena",Font.PLAIN,33));
  display.setBackground(Color.yellow);
  display.setForeground(Color.black);
  Container layout = getContentPane();
  layout.setLayout(new FlowLayout());
  layout.add(display); layout.add(button);
  button.addActionListener(this);
 }
 
 public void run()
 {
  for(i=0;;i++)
  {
    for(k=0;k< 60;k++)
    {
     for(l=0;l< 100;l++)
     {
      //if hits 60s will stop  
      if(k == 60)
      {
       break; 
      }
      
      NumberFormat numFormat = new DecimalFormat("00");
      display.setText(numFormat.format(i) + ":" +
      numFormat.format(k) + ":" + numFormat.format(l));
      try
      {
         Thread.sleep(10);
      }
      catch (Exception e)
      {       
      }
     }   
    }      
  } 
 }
 
 public void doNothing(Thread threadT, ActionEvent actionEvent)
 {
    boolean clicked = false;
    while(clicked == false)
    {
       if(actionEvent.getActionCommand().equals("wait"))
       {
        
        button.setText("wait");
        clicked = true;
        break;
       }
    }
    
 }
 
 public void actionPerformed(ActionEvent actionEvent)
 {
  Thread threadT = new Thread(this);
  if(actionEvent.getActionCommand().equals("start"))
  {
   threadT.start();
   button.setText("pause 5 seconds");
  }
  if(actionEvent.getActionCommand().equals("pause 5 seconds"))
  {
     try
   {
      Thread.sleep(5000);
   } catch (InterruptedException e)
   {
      // TODO Auto-generated catch block
      e.printStackTrace();
   }
     button.setText("restart");
  }
  if(actionEvent.getActionCommand().equals("restart"))
  {
   //threadT.doNothing(threadT, actionEvent);
   button.setText("you sure?");
  }
 
  if(actionEvent.getActionCommand().equals("you sure?"))
  {
   threadT.start();
   button.setText("pause 5 seconds");
  }
  else
  {
   stop = true;
  }
 }
 
 public static void main(String[] args) 
 {
  timer1 t1 = new timer1();
  t1.setSize(200,100);
  t1.setVisible(true);
  t1.setTitle("timer: 60 seconds");
  t1.setDefaultCloseOperation(EXIT_ON_CLOSE);
 }
}
