//Kristy Carpenter, Computer Science III, String 2015, Section B (5th period)
//Final project--Solitaire
//
//This class contains the information for a CardListener. It handles every case in which a Card
//is being moved to a different part of the panel. It also checks if the game has been won whenever
//a card is moved to a Foundation, and if the game has been won, it triggers the win screen and
//prevents the user from interacting with any of the cards or buttons.

import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class CardListener extends MouseInputAdapter
{
   /**
     *true if something is currently selected, false if nothing is currently selected
     */
   private static boolean somethingSelected;
   
   /**
     *all the cards in the game
     */
   private ArrayList<Card> cards;
   
   /**
     *the graphics context for the panel on which everything is drawn
     */
   private static Graphics g;
   
   /**
     *if something is currently selected, then this is that card that is selected. otherwise, it's null
     */
   private static Card selectedCard;
   
   /**
     *the array containing all the foundations
     */
   private static Foundation[] foundations;
   
   /**
     *the array containing all the piles
     */
   private Pile[] piles;
   
   /**
     *true if multiple cards are selected at once (by selecting a card in the middle of a pile), false
     *if only one card is selected or if no cards are selected
     */
   private static boolean multipleSelected;
   
   /**
     *the deck being used
     */
   private static Deck deck;
   
   /**
     *true if the game has been won, false otherwise
     */
   private static boolean win;
   
   /**
     *the constructor for a CardListener.
     *
     *@param allCards - all the cards in the game
     *@param p - the drawingpanel onto which everything is drawn
     *@param foundationArray - the array containing all the foundations in the game
     *@param pileArray - the array containing all the piles in the game
     *@param deck - the game's deck
     */
   public CardListener(ArrayList<Card> allCards, DrawingPanel p, Foundation[] foundationArray, Pile[] pileArray, Deck deck)
   {
      somethingSelected = false;
      cards = allCards;
      g = p.getGraphics();
      foundations = foundationArray;
      piles = pileArray;
      multipleSelected = false;
      this.deck = deck;
      win = false;
   }
   
   /**
     *this method is called whenever the mouse is clicked. If the game has not yet been won, it runs
     *through all the different possibilities of card clicking, checking things such as whether any
     *card is selected, whether multiple cards have been selected, if the click hit a card, foundation
     *or pile, if the clicked card is in the deck, a pile, or a foundation, etc.
     *
     *@param event - the mouseevent
     */
   public void mouseClicked(MouseEvent event)
   { 
      if (!win)
      {
         for (Card c : cards)
         {
            if (c.isFaceUp() && c.isTopAt(event.getX(), event.getY()))
            {
               if (!c.isSelected())
               {
                  if (somethingSelected)
                  {
                     if (c.getLocation().equals(Card.Location.PILE) && selectedCard.getLocation().equals(Card.Location.PILE) && !multipleSelected)
                     {
                        Pile pileToLoseCard = Solitaire.whichPileIsHit(selectedCard.getX());
                        Pile pileToGainCard = Solitaire.whichPileIsHit(event.getX());
                        if (pileToGainCard.canAddCard(selectedCard) && pileToLoseCard != null)
                        {
                           pileToLoseCard.removeLastCard();
                           pileToGainCard.addCard(selectedCard);
                           pileToLoseCard.flipLastCard();
                           deselect();
                        }
                     }
                     else if (c.getLocation().equals(Card.Location.PILE) && selectedCard.getLocation().equals(Card.Location.FOUNDATION) && !multipleSelected)
                     {
                        Foundation foundationToLoseCard = Solitaire.whichFoundationIsHit(selectedCard.getY());
                        Pile pileToGainCard = Solitaire.whichPileIsHit(event.getX());
                        if (pileToGainCard.canAddCard(selectedCard) && foundationToLoseCard != null)
                        {
                           foundationToLoseCard.removeCard();
                           pileToGainCard.addCard(selectedCard);
                           deselect();
                        }
                     }
                     else if (selectedCard.getLocation().equals(Card.Location.DECK) && c.getLocation().equals(Card.Location.PILE))
                     {
                        Pile pileToGainCard = Solitaire.whichPileIsHit(event.getX());
                        if (pileToGainCard.canAddCard(selectedCard))
                        {
                           deck.removeTop();
                           pileToGainCard.addCard(selectedCard);
                           deselect();
                        }
                     }
                     else if (multipleSelected && c.getLocation().equals(Card.Location.PILE))
                     {
                        Pile pileToLoseCards = Solitaire.whichPileIsHit(selectedCard.getX());
                        Pile pileToGainCards = Solitaire.whichPileIsHit(event.getX());
                        if (pileToGainCards.canAddCard(selectedCard) && pileToLoseCards != null)
                        {
                           ArrayList<Card> losingPileSeeable = pileToLoseCards.getSeeable();
                           int index = losingPileSeeable.indexOf(selectedCard);
                           ArrayList<Card> losingPileSeeableImmutable = (ArrayList<Card>) losingPileSeeable.clone();
                           for (int cc = index; cc < losingPileSeeableImmutable.size(); cc++)
                           {
                              Card movingCard = losingPileSeeableImmutable.get(cc);
                              movingCard.unselect();
                              pileToLoseCards.removeLastCard();
                              pileToGainCards.addCard(movingCard);
                           }
                           pileToLoseCards.flipLastCard(); //only will do something if necessary (hopefully)
                           deselect();
                        }
                     }
                  }
                  else
                  {
                     if (c.isTopAt(c.getX(), c.getY() + Card.CARD_HEIGHT)) //top of pile or on foundation or top of deck
                     {
                        c.select();
                        somethingSelected = true;
                        selectedCard = c;
                        c.draw(g);
                     }
                     else if (c.getLocation().equals(Card.Location.PILE))//midstack of pile
                     {
                        somethingSelected = true;
                        multipleSelected = true;
                        selectedCard = c;
                        Pile p = Solitaire.whichPileIsHit(c.getX());
                        ArrayList<Card> allSeeable = p.getSeeable();
                        int index = allSeeable.indexOf(c);
                        for (int aa = index; aa < allSeeable.size(); aa++)
                        {
                           Card cardInStack = allSeeable.get(aa);
                           cardInStack.select();
                           cardInStack.draw(g);
                        }
                     }
                  }
               }
               else
               {
                  if (!multipleSelected)
                  {
                     deselect();
                  }
                  else //multiple are selected
                  {
                     somethingSelected = false;
                     multipleSelected = false;
                     Pile p = Solitaire.whichPileIsHit(selectedCard.getX());
                     ArrayList<Card> allSeeable = p.getSeeable();
                     int index = allSeeable.indexOf(selectedCard);
                     for (int bb = index; bb < allSeeable.size(); bb++)
                     {
                        Card cardInStack = allSeeable.get(bb);
                        cardInStack.unselect();
                        cardInStack.draw(g);
                     }
                     selectedCard = null;
                  }
               }
               break;
            }
         }
         for (Foundation f : foundations)
         {
            if (f.isHit(event.getX(), event.getY()) && somethingSelected && f.canAddCard(selectedCard) && !multipleSelected)
            {
               if (selectedCard.getLocation().equals(Card.Location.PILE))
               {
                  Pile pileToLoseCard = Solitaire.whichPileIsHit(selectedCard.getX());
                  pileToLoseCard.removeLastCard();
                  pileToLoseCard.flipLastCard();
               }
               else if (selectedCard.getLocation().equals(Card.Location.FOUNDATION))
               {
                  Foundation foundationToLoseCard = Solitaire.whichFoundationIsHit(selectedCard.getY());
                  foundationToLoseCard.removeCard();
               }
               else //deck
               {
                  deck.removeTop();
               }
               f.addCard(selectedCard);
               deselect();
               checkForWin();
            }
         }
         for (Pile p : piles) //clicking on empty pile, adding a king
         {
            if (p.isEmpty() && p.isHit(event.getX(), event.getY()) && somethingSelected && p.canAddCard(selectedCard))
            {
               if (multipleSelected)
               {
                  Pile pileToLoseCards = Solitaire.whichPileIsHit(selectedCard.getX());
                  ArrayList<Card> losingPileSeeable = pileToLoseCards.getSeeable();
                  int index = losingPileSeeable.indexOf(selectedCard);
                  ArrayList<Card> losingPileSeeableImmutable = (ArrayList<Card>) losingPileSeeable.clone();
                  for (int cc = index; cc < losingPileSeeableImmutable.size(); cc++)
                  {
                     Card movingCard = losingPileSeeableImmutable.get(cc);
                     movingCard.unselect();
                     pileToLoseCards.removeLastCard();
                     p.addCard(movingCard);
                  }
                  multipleSelected = false;
                  pileToLoseCards.flipLastCard();
               }
               else if (selectedCard.getLocation().equals(Card.Location.PILE))
               {
                  Pile pileToLoseCard = Solitaire.whichPileIsHit(selectedCard.getX());
                  pileToLoseCard.removeLastCard();
                  p.addCard(selectedCard);
                  pileToLoseCard.flipLastCard();
               }
               else //deck
               {
                  deck.removeTop();
                  p.addCard(selectedCard);
               }
               deselect();
            }
         }
      }  
   }
   
   /**
     *triggers the win screen and sets the boolean win to true, preventing the user from selecting
     *or moving any more cards
     */
   private static void win()
   {
      g.setFont(new Font(Solitaire.fontName, Font.PLAIN, 100));
      g.setColor(Color.BLACK);
      g.drawString("YOU WIN!!!!! :D", 50, 400);
      win = true;
   }
   
   /**
     *accessor method for the boolean that tells whether or not the game has been won
     *
     *@return win - true if the game has been won, false if not
     */
   public static boolean isWon()
   {
      return win;
   }
   
   /**
     *accessor method for the boolean that tells wether or not something is currently selected
     *
     *@return somethingSelected - true if something is currently selected, false otherwise
     */
   public static boolean somethingSelected()
   {
      return somethingSelected;
   }
   
   /**
     *checks to see if the game has been won by running through all foundations and seeing if they
     *are complete. If they all are complete, the game has been won and the win method is called
     *
     */
   public static void checkForWin()
   {
      int counter = 0; 
      for (Foundation fo : foundations)
      {
         if (fo.isComplete())
         {
            counter++;
         }
      }
      if (counter == foundations.length)
      {
         win();
      }
   }
   
   /**
     *called by many different cases in the mouseClicked method. Clears or changes any necessary
     *variables whenever a card is unselected. Draws the screen so any updates can be seen by
     *the user
     */
   private void deselect()
   {
      somethingSelected = false;
      multipleSelected = false;
      selectedCard.unselect();
      selectedCard = null;
      Solitaire.drawScreen();
   }
}