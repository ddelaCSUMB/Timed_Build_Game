import javax.swing.*;

public class CardGameView
{
   private JLabel[] computerLabels;
   private JLabel[] humanLabels;
   private JLabel[] playedCardLabels;
   private JLabel[] playLabelText;
   private JButton[] buttons;
   CardTableE myCardTable;
   Icon tempIcon;


   public void initView(int numCardsPerHand, int numPlayers)
   {

      computerLabels = new JLabel[numCardsPerHand];
      humanLabels = new JLabel[numCardsPerHand];
      playedCardLabels = new JLabel[numPlayers];
      playLabelText = new JLabel[numPlayers];
      buttons = new JButton[numCardsPerHand];



      GUICard.loadCardIcons();
      myCardTable = new CardTableE("CardTable", numCardsPerHand, numPlayers);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      myCardTable.setVisible(true);

   }
}
