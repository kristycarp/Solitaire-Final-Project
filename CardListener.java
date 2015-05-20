import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class CardListener extends MouseInputAdapter
{
   private static boolean somethingSelected;
   private ArrayList<Card> cards;
   private Graphics g;
   private static Card selectedCard;
   private Foundation[] foundations;
   private Pile[] piles;
   
   public CardListener(ArrayList<Card> allCards, DrawingPanel p, Foundation[] foundationArray, Pile[] pileArray)
   {
      somethingSelected = false;
      cards = allCards;
      g = p.getGraphics();
      foundations = foundationArray;
      piles = pileArray;
   }
   
   public void mouseClicked(MouseEvent event)
   {
      //System.out.println("you clicked");
      //Collections.sort(cards); 
      for (Card c : cards)
      {
         if (c.isFaceUp() && c.isTopAt(event.getX(), event.getY()))
         {
            //System.out.println("you clicked " + c.toString());
            if (!c.isSelected())
            {
               if (somethingSelected)
               {
                  if (c.getLocation().equals(Card.Location.PILE) && selectedCard.getLocation().equals(Card.Location.PILE))
                  {
                     //System.out.println(selectedCard.toString() + " has been selected first");
                     Pile pileToLoseCard = Solitaire.whichPileIsHit(selectedCard.getX());
                     //System.out.println("The pile losing a card has x = " + pileToLoseCard.getX());
                     //System.out.println(selectedCard.fullToString());
                     Pile pileToGainCard = Solitaire.whichPileIsHit(event.getX());
                     //System.out.println("The pile gaining a card has x = " + pileToGainCard.getX());
                     //System.out.println(selectedCard.fullToString());
                     if (pileToGainCard.canAddCard(selectedCard) && pileToLoseCard != null)
                     {
                        pileToLoseCard.removeLastCard();
                        //System.out.println("The pile losing a card has x = " + pileToLoseCard.getX());
                        //System.out.println(selectedCard.fullToString());
                        pileToGainCard.addCard(selectedCard);
                        //System.out.println("The pile losing a card has x = " + pileToLoseCard.getX());
                        //System.out.println("before flipping last card: " + selectedCard.fullToString());
                        pileToLoseCard.flipLastCard();
                        //System.out.println("The pile losing a card has x = " + pileToLoseCard.getX());
                        //System.out.println("after flipping last card: " + selectedCard.fullToString());
                        somethingSelected = false;
                        selectedCard.unselect();
                        selectedCard = null;
                        Solitaire.drawScreen();
                        //selectedCard.draw(g);
                        //c.draw(g);
                        //System.out.println("I recognize that the right things have happened. i tried.");
                     }
                  }
                  else if (c.getLocation().equals(Card.Location.PILE) && selectedCard.getLocation().equals(Card.Location.FOUNDATION))
                  {
                     Foundation foundationToLoseCard = Solitaire.whichFoundationIsHit(selectedCard.getY());
                     Pile pileToGainCard = Solitaire.whichPileIsHit(event.getX());
                     if (pileToGainCard.canAddCard(selectedCard) && foundationToLoseCard != null)
                     {
                        foundationToLoseCard.removeCard();
                        pileToGainCard.addCard(selectedCard);
                        somethingSelected = false;
                        selectedCard.unselect();
                        selectedCard = null;
                        Solitaire.drawScreen();
                     }
                  }
                  /**else if (c.getLocation().equals(Card.Location.FOUNDATION))
                  {
                  
                  }**/
                  else if (c.getLocation().equals(Card.Location.DECK))
                  {
                  
                  }
                  /**else
                  {
                     System.out.println("something weird happened...");
                  }**/
               }
               else
               {
                  c.select();
                  somethingSelected = true;
                  selectedCard = c;
                  c.draw(g);
                  //System.out.println(c.fullToString() + "asdf");
               }
            }
            else
            {
               c.unselect();
               somethingSelected = false;
               selectedCard = null;
               c.draw(g);
               //System.out.println(c.fullToString() + "fdsa");
            }
            break;
         }
         //if (c.isHit(event.getX(), event.getY()))
         //   System.out.println(c.fullToString());
      }
      for (Foundation f : foundations)
      {
         if (f.isHit(event.getX(), event.getY()) && somethingSelected && f.canAddCard(selectedCard))
         {
            if (selectedCard.getLocation().equals(Card.Location.PILE))
            {
               Pile pileToLoseCard = Solitaire.whichPileIsHit(selectedCard.getX());
               pileToLoseCard.removeLastCard();
               f.addCard(selectedCard);
               pileToLoseCard.flipLastCard();
            //selectedCard = null;
            }
            else if (selectedCard.getLocation().equals(Card.Location.FOUNDATION))
            {
               Foundation foundationToLoseCard = Solitaire.whichFoundationIsHit(selectedCard.getY());
               foundationToLoseCard.removeCard();
               f.addCard(selectedCard);
            }
            //if you're adding an else case for the deck make sure this stuff still applies
            somethingSelected = false;
            selectedCard.unselect();
            Solitaire.drawScreen();
         }
      }
      for (Pile p : piles)
      {
         if (p.isEmpty() && p.isHit(event.getX(), event.getY()) && somethingSelected && p.canAddCard(selectedCard))
         {
            Pile pileToLoseCard = Solitaire.whichPileIsHit(selectedCard.getX());
            pileToLoseCard.removeLastCard();
            p.addCard(selectedCard);
            pileToLoseCard.flipLastCard();
            somethingSelected = false;
            selectedCard.unselect();
            Solitaire.drawScreen();
         }
      }  
   }
}