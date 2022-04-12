public class CardGameModel
{
   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   private Card.Suit suit;
   int card;
   int numPacksPerDeck = 1;
   int numJokersPerPack = 2;
   int numUnusedCardsPerPack = 0;
   Card[] unusedCardsPerPack = null;
   CardGameFramework highCardGame = new CardGameFramework
                                       (numPacksPerDeck, numJokersPerPack,
                                          numUnusedCardsPerPack, unusedCardsPerPack,
                                          NUM_PLAYERS, NUM_CARDS_PER_HAND);

   public void initModel()
   {
      highCardGame.deal();

   }
}
