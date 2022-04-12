import javax.swing.*;
import java.awt.event.ActionEvent;

public class CardGameController
{
   private CardGameModel theModel;
   private CardGameView theView;

   public CardGameController(CardGameModel theModel, CardGameView theView)
   {
      this.theView = theView;
      this.theModel = theModel;
      this.theModel.initModel();
      this.theView.initView(CardGameModel.NUM_CARDS_PER_HAND, CardGameModel.NUM_PLAYERS);

      //add listeners
      // this.theView.addCalculateListener(new CalculateListener());

   }



   public void loseAction(ActionEvent l)
   {
      System.out.println("You Lose, try another card or press re-deal");
   }

   public void winAction(JButton buttons2)
   {
      System.out.println("You win!");
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      // TODO Auto-generated method stub

   }
}
