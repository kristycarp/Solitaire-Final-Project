//Kristy Carpenter, Computer Science III, String 2015, Section B (5th period)
//Final project--Solitaire
//
//This class contains all the information for a listener for a complete button. Whenever is senses
//that a point inside the complete button has been clicked, it "completes" foundations as much as
//possible. That is, it checks all moveable cards (the topmost card of the deck and the topmost cards
//of each pile) to see if they can be legally moved to any of the foundations. It continues to check
//and move as is legal until no cards are moved, so as to ensure that all moveable cards are in fact
//moved. This functionality is especially helpful to the user during the endgame, when it becomes
//tedious to move all the cards in the piles to the foundations.

import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;

public class CompleteButtonListener extends MouseInputAdapter
{
   /**
     *the button to listen to
     */
   private Button b;
   
   /**
     *all the cards in the game
     */
   private ArrayList<Card> cards;
   
   /**
     *an array of all the foundations in the game
     */
   private Foundation[] foundations;
   
   /**
     *the constructor for a new CompleteButtonListener. sets all the instance variables.
     *
     *@param b - the button to listen to
     *@param allCards - all the cards in the game
     *@param f - an array with all the foundations in the game
     */
   public CompleteButtonListener(Button b, ArrayList<Card> allCards, Foundation[] f)
   {
      this.b = b;
      cards = allCards;
      foundations = f;
   }
   
   /**
     *called whenever the mouse is clicked. If the game has not been won and the button that is being
     *listened to is hit, it goes through all the cards and moves them to a foundation is possible. It
     *continues going through all the cards until there are no more possible moves, just in case moving
     *one card enables a card that was previously not moved to be moved.
     *
     *@param event - the mouseevent
     */
   public void mouseClicked(MouseEvent event)
   {
      if (b.isHit(event.getX(), event.getY()) && !CardListener.isWon())
      {
         int cardsAdded;
         do
         {
            cardsAdded = 0;
            for (Card c : cards)
            {
               //System.out.println(c.fullToString());
               if (c.isFaceUp() && c.isTopAt(c.getX() + Card.CARD_WIDTH, c.getY() + Card.CARD_HEIGHT) && (c.getLocation().equals(Card.Location.DECK) || c.getLocation().equals(Card.Location.PILE)))
               {
                  //System.out.println(c.toString() + " is eligible");
                  for (Foundation f : foundations)
                  {
                     if (f.canAddCard(c))
                     {
                        if (c.getLocation().equals(Card.Location.DECK))
                        {
                           Deck deck = Solitaire.getDeck();
                           deck.removeTop();
                        }
                        else
                        {
                           Pile p = Solitaire.whichPileIsHit(c.getX());
                           p.removeLastCard();
                           p.flipLastCard();
                        }
                        f.addCard(c);
                        cardsAdded++;
                        break;
                     }
                  }
               }
            }
            //System.out.println("---------------------------------");
            Solitaire.drawScreen();
            CardListener.checkForWin();
         } while (cardsAdded != 0);
      }
   }
}