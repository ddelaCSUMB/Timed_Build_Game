public class assig6
{
   public static void main(String[] args)
   {
      CardGameView theView = new CardGameView();
      CardGameModel theModel = new CardGameModel();
      CardGameController theController = new CardGameController(theModel, theView);



   }
}
