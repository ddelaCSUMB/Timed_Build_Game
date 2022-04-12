/*
 * Roberto Avevalo
 * Richard Garcia
 * Ali Bakar
 * David Delacalzada
 *
 * CST 338
 * 4/7/2022
 *
 * This program creates a JFrame with card icons and three playing fields. With
 * a computer hand and user hand. Contains methods interacting with multiple
 * decks of cards.
 *
 *
 */




import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

import java.util.EventListener;
import java.util.Random;
public class phase3 implements ActionListener
{








   public static void main(String[] args)
   {












      for (card = 0; card < NUM_CARDS_PER_HAND; card++)
      {
         computerLabels[card] = new JLabel(GUICard.getBackcardIcon());
         tempIcon = GUICard.getIcon(highCardGame.getHand(1).inspectCard(card));

         //humanLabels[card] = new JLabel(tempIcon);
         buttons[card] = new JButton(tempIcon);

         //creates new JLabel(string, horizontalAlignment)
         playLabelText[0] = new JLabel("Computer", 0);
         playLabelText[1] = new JLabel("You", 0);
      }


      for (card = 0; card < NUM_CARDS_PER_HAND; card++)
      {
         myCardTable.pnlComputerHand.add(computerLabels[card]);
         //myCardTable.pnlHumanHand.add(humanLabels[card]);
         myCardTable.pnlHumanHand.add(buttons[card]);
      }

      for (card = 0; card < NUM_PLAYERS; card++)
      {
         tempIcon = GUICard.getIcon(generateRandomCard());
         playedCardLabels[card] = new JLabel(tempIcon);
         myCardTable.pnlPlayArea.add(playedCardLabels[card]);

         //adds lables "computer" "you" under cards in playing field
         myCardTable.pnlPlayArea.add(playLabelText[0]);
         myCardTable.pnlPlayArea.add(playLabelText[1]);
      }
      myCardTable.setVisible(true);
   }

   static Card generateRandomCard()
   {
      Deck deck = new Deck();
      Random randomGen = new Random();
      return deck.inspectCard(randomGen.nextInt(deck.getNumCards()));
   }
}

class CardGameFramework
{
   private static final int MAX_PLAYERS = 50;

   private int numPlayers;
   private int numPacks;
   private int numJokersPerPack;
   private int numUnusedCardsPerPack;
   private int numCardsPerHand;
   private Deck deck;
   private Hand[] hand;
   private Card[] unusedCardsPerPack;

   public CardGameFramework( int numPacks, int numJokersPerPack,
      int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
      int numPlayers, int numCardsPerHand)
   {
      int k;

      if (numPacks < 1 || numPacks > 6)
         numPacks = 1;
      if (numJokersPerPack < 0 || numJokersPerPack > 4)
         numJokersPerPack = 0;
      if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50)
         numUnusedCardsPerPack = 0;
      if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
         numPlayers = 4;
      if  (numCardsPerHand < 1 ||
              numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack)
                                    / numPlayers )
         numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

      this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
      this.hand = new Hand[numPlayers];
      for (k = 0; k < numPlayers; k++)
         this.hand[k] = new Hand();
      deck = new Deck(numPacks);

      this.numPacks = numPacks;
      this.numJokersPerPack = numJokersPerPack;
      this.numUnusedCardsPerPack = numUnusedCardsPerPack;
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;
      for (k = 0; k < numUnusedCardsPerPack; k++)
         this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

      newGame();
   }
   public CardGameFramework()
   {
      this(1, 0, 0, null, 4, 13);
   }
   public Hand getHand(int k)
   {
      if (k < 0 || k >= numPlayers)
         return new Hand();

      return hand[k];
   }
   public Card getCardFromDeck()
   {
      return deck.dealCard();
   }
   public int getNumCardsRemainingInDeck()
   {
      return deck.getNumCards();
   }
   public void newGame()
   {
      int k, j;

      for (k = 0; k < numPlayers; k++)
         hand[k].resetHand();

      deck.init(numPacks);

      for (k = 0; k < numUnusedCardsPerPack; k++)
         deck.removeCard( unusedCardsPerPack[k] );

      for (k = 0; k < numPacks; k++)
         for ( j = 0; j < numJokersPerPack; j++)
            deck.addCard( new Card('X', Card.Suit.values()[j]) );

      deck.shuffle();
   }

   //method to deal cards
   public boolean deal()
   {
      int k, j;
      boolean enoughCards;

      for (j = 0; j < numPlayers; j++)
         hand[j].resetHand();

      enoughCards = true;
      for (k = 0; k < numCardsPerHand && enoughCards ; k++)
      {
         for (j = 0; j < numPlayers; j++)
            if (deck.getNumCards() > 0)
               hand[j].takeCard( deck.dealCard() );
            else
            {
               enoughCards = false;
               break;
            }
      }
      return enoughCards;
   }


   void sortHands()
   {
      int k;

      for (k = 0; k < numPlayers; k++)
         hand[k].sort();
   }

   //plays a card using the index of player array and card array
   Card playCard(int playerIndex, int cardIndex)
   {
      if (playerIndex < 0 ||  playerIndex > numPlayers - 1 ||
             cardIndex < 0 || cardIndex > numCardsPerHand - 1)
      {
         return new Card('M', Card.Suit.SPADES);
      }
      return hand[playerIndex].playCard(cardIndex);
   }

   //takecard from deck
   boolean takeCard(int playerIndex)
   {
      // returns false if either argument is bad
      if (playerIndex < 0 || playerIndex > numPlayers - 1)
         return false;

      // Are there enough Cards?
      if (deck.getNumCards() <= 0)
         return false;

      return hand[playerIndex].takeCard(deck.dealCard());
   }
}// end of CardGameFramework


class CardTableE extends JFrame
{
   /**
    *
    */
   private static final long serialVersionUID = 1L;
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games
   private int numCardsPerHand;
   private int numPlayers;
   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;
   //constructor filters input, adds any panels to the JFrame, and
   //establishs layouts
   CardTableE(String title, int numCardsPerHand, int numPlayers)
   {
      //this.numCardsPerHand = numCardsPerHand;
      super(title); // look up jframe and how to instantiate it, and set layout,
      //if statement later
      //make sure greater than 0 and less than equal to 56
      // make sure max players of 2 and only 2, set things for the frame
      // add panels to frame from this constructor, set everything for the panel
      //add panels to the frame
      setLayout(new BorderLayout());
      //Sets values
      this.numCardsPerHand = numCardsPerHand;
      this.numPlayers = numPlayers;
      //debugging strategies.
      pnlComputerHand = new JPanel(new GridLayout(1, numCardsPerHand));
      pnlHumanHand = new JPanel(new GridLayout(1, numCardsPerHand));
      pnlPlayArea = new JPanel(new GridLayout(2, numPlayers));

      add(pnlComputerHand, BorderLayout.NORTH);
      add(pnlHumanHand, BorderLayout.SOUTH);
      add(pnlPlayArea, BorderLayout.CENTER);

      pnlComputerHand.setBorder(new TitledBorder("Computer Hand"));
      pnlHumanHand.setBorder(new TitledBorder("Your Hand"));
      pnlPlayArea.setBorder(new TitledBorder("Playing Field"));
   }
   public int getNumCardsPerHand()
   {
      return numCardsPerHand;
   }
   public int getNumPlayers()
   {
      return numPlayers;
   }
}//end of CardTable extends JFrame

class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4];
   private static Icon iconBack;

   static boolean iconsLoaded = false;

   public static void loadCardIcons()
   {
      if (iconsLoaded)
         return;
      for (int cardValue = 0; cardValue < iconCards.length; cardValue++)
      {
         for (int cardSuit = 0; cardSuit < iconCards[cardValue].length; cardSuit++)
         {
            String filename = numCard(cardValue) + numSuit(cardSuit) + ".gif";
            ImageIcon cardImage = new ImageIcon("images/" + filename);
            iconCards[cardValue][cardSuit] = cardImage;
         }
      }
      iconBack = new ImageIcon("images/BK.gif");
      iconsLoaded = true;
   }
   static String numCard(int cardNum)
   {
      String[] cardValues = {"A", "2", "3", "4", "5", "6",
         "7", "8", "9", "T", "J", "Q", "K", "X"};
      return cardValues[cardNum];
   }
   static String numSuit(int suitNum)
   {
      if (suitNum < 0 || suitNum > 3)
         return "invalid";
      return Card.Suit.values()[suitNum]
         .toString().toUpperCase().substring(0, 1);
   }
   private static int valueToInt(Card card)
   {
      return Card.valueOfCard(card);
   }
   private static int suitToNum(Card card)
   {
      Card.Suit cardSuit = card.getSuit();

      switch (cardSuit)
      {
      case SPADES:
         return 0;
      case HEARTS:
         return 1;
      case DIAMONDS:
         return 2;
      case CLUBS:
         return 3;
      default:
         return -1;
      }
   }
   public static Icon getIcon(Card card)
   {
      loadCardIcons();
      return iconCards[valueToInt(card)][suitToNum(card)];
   }
   public static Icon getBackcardIcon()
   {
      loadCardIcons();
      return iconBack;
   }
}// end of GUICard
class Card
{
   public enum Suit
   {
      SPADES, HEARTS, DIAMONDS, CLUBS;
   }
   public static char[] cardPosition = {'A', '2', '3', '4', '5', '6', '7', '8',
      '9', 'T', 'J', 'Q', 'K', 'X'};

   private char value;
   private Suit suit;
   private boolean cardError;

   public Card()//default constructor
   {
      value = 'A';
      suit = Suit.SPADES;
   }
   public Card(char value, Suit suit)
   {
      set(value,suit);
   }
   public String toString()
   {
      if(cardError == true)
      {
         return "** illegal **";
      }
      else
         return value + " of " + suit;//
   }
   public boolean set(char value, Suit suit)//mutator
   {
      if (isValid(value,suit) == true)
      {
         this.value = value;
         this.suit = suit;
         cardError = false;
         return true;
      }
      cardError = true;
      return false;

   }
   public char getValue()//accessors
   {
      return this.value;
   }
   public Suit getSuit()
   {
      return this.suit;
   }
   public boolean getCardError()
   {
      return cardError;
   }
   public boolean equals(Card otherCard)
   {
      return(suit == otherCard.getSuit() && value == otherCard.getValue() &&
                cardError == otherCard.getCardError() );

   }
   private boolean isValid(char value,Suit suit)
   {
      if ((value == '2') || (value == '3') || (value == '4') || (value == '5')
             || (value == '6') || (value == '7') || (value == '8') ||
             (value == '9') || (value == 'T') || (value == 'J') || (value == 'Q')
             || (value == 'K') || (value == 'A'))
      {
         return true;
      }
      else
      {
         return false;
      }

   }
   public static void arraySort(Card[] cardArray, int arraySize)
   {
      Card temp;
      // Bubble sort algorithm
      for (int card = 0; card < arraySize; card++)
      {
         for (int nextCard = 1; nextCard < (arraySize - card); nextCard++)
         {
            int previousCard = valueOfCard(cardArray[nextCard - 1]);
            int currentCard = valueOfCard(cardArray[nextCard]);

            if (previousCard > currentCard)
            {
               temp = cardArray[nextCard - 1];
               cardArray[nextCard - 1] = cardArray[nextCard];
               cardArray[nextCard] = temp;
            }
         }
      }
   }
   static int valueOfCard(Card card)
   {
      for (int value = 0; value < cardPosition.length; value++)
      {
         if (card.getValue() == cardPosition[value])
         {
            return value;
         }
      }
      return -1;
   }
}// end of Card Class

class Hand
{
   public static final int MAX_CARDS = 100;
   private Card[] myCards;
   private int numCards;

   public Hand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }
   public void resetHand()
   {
      numCards = 0;
   }
   public boolean takeCard(Card card)
   {
      if (numCards < MAX_CARDS)
      {
         myCards[numCards] = new Card(card.getValue(), card.getSuit());
         numCards++;
         return true;
      }
      return false;
   }
   public Card playCard()
   {
      if (numCards > 0)
      {
         numCards--;
         System.out.println(myCards[numCards]);
         return myCards[numCards];
      }
      else
      {
         Card badCard = new Card('0', Card.Suit.SPADES);
         return badCard;
      }
   }
   public int getNumCards()
   {
      return numCards;
   }
   public String toString()
   {
      String str;
      str = "Hand = ( ";
      int card;
      for (card = 0; card < numCards; card++)
      {
         str += (myCards[card].toString());
         if (card < numCards - 1)
         {
            str += ", ";
         }
      }
      str += " )";
      System.out.println(str);
      return str;
   }
   public Card inspectCard(int k)
   {
      if (k > numCards || k < 0)
      {
         return new Card('0', Card.Suit.SPADES);
      }
      return myCards[k];
   }
   public void sort()
   {
      Card.arraySort(myCards, numCards);
   }
   public Card playCard(int cardIndex)
   {
      if (numCards == 0) //error
      {
         return new Card('M', Card.Suit.SPADES);
      }
      Card card = myCards[cardIndex];
      numCards--;

      for (int cardPosition = cardIndex; cardPosition < numCards; cardPosition++)
      {
         myCards[cardPosition] = myCards[cardPosition + 1];
      }
      myCards[numCards] = null;
      return card;
   }
}//end of Hand Class
class Deck
{
   public static final int DECK_SIZE = 52;
   public static final int MAX_CARDS = 6 * DECK_SIZE;
   public static final int NUM_OF_VALUES = 14;

   private static Card[] masterPack = new Card[DECK_SIZE];
   private Card[] cards = new Card[MAX_CARDS];
   private int topCard = 0;

   public Deck()
   {
      int card;
      allocateMasterPack();
      for (card = 0; card < DECK_SIZE; card++)
      {
         cards[card] = masterPack[card % DECK_SIZE];
         topCard++;
      }
   }
   public Deck(int numPacks)
   {
      int card;
      allocateMasterPack();
      for (card = 0; card < numPacks * DECK_SIZE; card++)
      {
         cards[card] = masterPack[card % DECK_SIZE];
         topCard++;
      }
   }
   public void init(int numPacks)
   {
      if (numPacks <= 6)
      {
         int card;
         topCard = 0;

         for (card = 0; card < numPacks * DECK_SIZE; card++)
         {
            cards[card] = masterPack[card % DECK_SIZE];
            topCard++;
         }
      }
   }
   public void shuffle()
   {
      for (int card = 0; card < topCard; card++)
      {
         int second = (int) (Math.random() * topCard);
         Card temp = cards[card];
         cards[card] = cards[second];
         cards[second] = temp;
      }
   }
   public Card dealCard()
   {
      if (topCard > 0)
      {
         topCard--;
         Card tempCard = cards[topCard];
         return tempCard;
      }
      return new Card('-', Card.Suit.SPADES);
   }
   public int getTopCard()
   {
      return topCard;
   }
   public Card inspectCard(int k)
   {
      if (k > topCard)
      {
         return new Card('0', Card.Suit.SPADES);
      }
      return cards[k];
   }
   private static void allocateMasterPack()
   {
      int masterPackIndex;

      String cardValues = "A23456789TJQKX";

      if (masterPack[0] == null)
      {
         for (masterPackIndex = 0; masterPackIndex < DECK_SIZE; masterPackIndex++)
         {
            if (masterPackIndex / NUM_OF_VALUES == 0)
            {
               masterPack[masterPackIndex] =
                  new Card(cardValues.charAt(masterPackIndex % NUM_OF_VALUES),
                     Card.Suit.SPADES);
            }
            if (masterPackIndex / NUM_OF_VALUES == 1)
            {
               masterPack[masterPackIndex] =
                  new Card(cardValues.charAt(masterPackIndex % NUM_OF_VALUES),
                     Card.Suit.CLUBS);
            }
            if (masterPackIndex / NUM_OF_VALUES == 2)
            {
               masterPack[masterPackIndex] =
                  new Card(cardValues.charAt(masterPackIndex % NUM_OF_VALUES),
                     Card.Suit.HEARTS);
            }
            if (masterPackIndex / NUM_OF_VALUES == 3)
            {
               masterPack[masterPackIndex] =
                  new Card(cardValues.charAt(masterPackIndex % NUM_OF_VALUES),
                     Card.Suit.DIAMONDS);
            }
         }
      }
   }
   public int getNumCards()
   {
      return topCard;
   }
   public boolean addCard(Card card)
   {
      int deckNum = topCard / DECK_SIZE;
      int cardInstances = 0;


      for (int cardPosition = 0; cardPosition < topCard; cardPosition++)
      {
         if (card.equals(cards[cardPosition]))
         {
            cardInstances++;
         }
      }
      //System.out.println("Card instances is: " + cardInstances);

      if (cardInstances >= deckNum)
      {
         //System.out.println("Did not add card");
         return false;
      }
      //System.out.println("Added the card to the deck");
      cards[topCard] = card;

      topCard++;

      return true;
   }
   public boolean removeCard(Card card)
   {
      for (int cardsIndex = 0; cardsIndex < topCard; cardsIndex++)
      {
         if (cards[cardsIndex].equals(card))
         {
            System.out.println("Removed Card Successfully");
            cards[cardsIndex] = cards[topCard - 1];
            topCard--;
            return true;
         }
      }
      System.out.println("Did not remove card, none left");
      System.out.println(topCard);
      return false;
   }
   public void sort()
   {
      Card.arraySort(cards, topCard);
   }
   public String toString()
   {
      String str;
      str = "Deck = ( ";
      for (int card = 0; card < getNumCards(); card++)
      {
         str += (cards[card].toString());
         if (card < getNumCards() - 1)
         {
            str += ", ";
         }
      }
      str += " )";
      System.out.println(str);
      return str;
   }

   //end of deck class


   public class CardGameOutline
   {
      private static final int MAX_PLAYERS = 50;
      private int numPlayers;
      private int numPacks;            // # standard 52-card packs per deck
      // ignoring jokers or unused cards
      private int numJokersPerPack;    // if 2 per pack & 3 packs per deck,
      // get 6
      private int numUnusedCardsPerPack;  // # cards removed from each pack
      private int numCardsPerHand;        // # cards to deal each player
      private Deck deck;               // holds the initial full deck and
      //gets
      // smaller (usually) during play
      private Hand[] hand;             // one Hand for each player
      private Card[] unusedCardsPerPack;   // an array holding the cards not
      //used
      // in the game.  e.g. pinochle
      //does not
      // use cards 2-8 of any suit
      CardGameOutline( int numPacks, int numJokersPerPack,
         int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
         int numPlayers, int numCardsPerHand)
      {
         int k;
         // filter bad values
         if (numPacks < 1 || numPacks > 6)
            numPacks = 1;
         if (numJokersPerPack < 0 || numJokersPerPack > 4)
            numJokersPerPack = 0;
         if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1
            //card
            numUnusedCardsPerPack = 0;
         if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
            numPlayers = 4;
         // one of many ways to assure at least one full deal to all players
         if  (numCardsPerHand < 1 || numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack)
                                                           / numPlayers )
            numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;
         // allocate
         this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
         this.hand = new Hand[numPlayers];
         for (k = 0; k < numPlayers; k++)
            this.hand[k] = new Hand();
         deck = new Deck(numPacks);
         // assign to members
         this.numPacks = numPacks;
         this.numJokersPerPack = numJokersPerPack;
         this.numUnusedCardsPerPack = numUnusedCardsPerPack;
         this.numPlayers = numPlayers;
         this.numCardsPerHand = numCardsPerHand;
         for (k = 0; k < numUnusedCardsPerPack; k++)
            this.unusedCardsPerPack[k] = unusedCardsPerPack[k];
         // prepare deck and shuffle
         newGame();
      }
      // constructor overload/default for game like bridge
      public CardGameOutline()
      {
         this(1, 0, 0, null, 4, 13);
      }
      public Hand getHand(int k)
      {
         // hands start from 0 like arrays
         // on error return automatic empty hand
         if (k < 0 || k >= numPlayers)
            return new Hand();
         return hand[k];
      }
      public Card getCardFromDeck() { return deck.dealCard(); }
      public int getNumCardsRemainingInDeck() { return deck.getNumCards(); }
      public void newGame()
      {
         int k, j;
         // clear the hands
         for (k = 0; k < numPlayers; k++)
            hand[k].resetHand();
         // restock the deck
         deck.init(numPacks);
         // remove unused cards
         for (k = 0; k < numUnusedCardsPerPack; k++)
            deck.removeCard( unusedCardsPerPack[k] );
         // add jokers
         for (k = 0; k < numPacks; k++)
            for ( j = 0; j < numJokersPerPack; j++)
               deck.addCard( new Card('X', Card.Suit.values()[j]) );
         // shuffle the cards
         deck.shuffle();
      }
      public boolean deal()
      {
         // returns false if not enough cards, but deals what it can
         int k, j;
         boolean enoughCards;
         // clear all hands
         for (j = 0; j < numPlayers; j++)
            hand[j].resetHand();
         enoughCards = true;
         for (k = 0; k < numCardsPerHand && enoughCards ; k++)
         {
            for (j = 0; j < numPlayers; j++)
               if (deck.getNumCards() > 0)
                  hand[j].takeCard( deck.dealCard() );
               else
               {
                  enoughCards = false;
                  break;
               }
         }
         return enoughCards;
      }
      void sortHands()
      {
         int k;
         for (k = 0; k < numPlayers; k++)
            hand[k].sort();
      }
      Card playCard(int playerIndex, int cardIndex)
      {
         // returns bad card if either argument is bad
         if (playerIndex < 0 ||  playerIndex > numPlayers - 1 ||
                cardIndex < 0 || cardIndex > numCardsPerHand - 1)
         {
            //Creates a card that does not work
            return new Card('M', Card.Suit.SPADES);
         }
         // return the card played
         return hand[playerIndex].playCard(cardIndex);
      }
      boolean takeCard(int playerIndex)
      {
         // returns false if either argument is bad
         if (playerIndex < 0 || playerIndex > numPlayers - 1)
            return false;
         // Are there enough Cards?
         if (deck.getNumCards() <= 0)
            return false;
         return hand[playerIndex].takeCard(deck.dealCard());
      }

   }




}