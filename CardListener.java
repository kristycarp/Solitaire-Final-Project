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
   
   public CardListener(ArrayList<Card> allCards, DrawingPanel p)
   {
      somethingSelected = false;
      cards = allCards;
      g = p.getGraphics();
   }
   
   public void mouseClicked(MouseEvent event)
   {
      //System.out.println("you clicked");
      for (Card c : cards)
      {
         if (c.isFaceUp() && c.isHit(event.getX(), event.getY()))
         {
            //System.out.println("you clicked " + c.toString());
            if (!c.isSelected())
            {
               if (somethingSelected)
               {
                  if (c.getLocation().equals(Card.Location.PILE))
                  {
                     //System.out.println(selectedCard.toString() + " has been selected first");
                     Pile pileToLoseCard = Solitaire.whichPileIsHit(selectedCard.getX());
                     //System.out.println("The pile losing a card has x = " + pileToLoseCard.getX());
                     //System.out.println(selectedCard.fullToString());
                     Pile pileToGainCard = Solitaire.whichPileIsHit(event.getX());
                     //System.out.println("The pile gaining a card has x = " + pileToGainCard.getX());
                     //System.out.println(selectedCard.fullToString());
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
                     Solitaire.drawScreen();
                     //selectedCard.draw(g);
                     //c.draw(g);
                     //System.out.println("I recognize that the right things have happened. i tried.");
                  }
                  else if (c.getLocation().equals(Card.Location.FOUNDATION))
                  {
                  
                  }
                  else if (c.getLocation().equals(Card.Location.DECK))
                  {
                  
                  }
                  else
                  {
                     System.out.println("something weird happened...");
                  }
               }
               else
               {
                  c.select();
                  somethingSelected = true;
                  selectedCard = c;
               }
            }
            else
            {
               c.unselect();
               somethingSelected = false;
               selectedCard = null;
            }
            c.draw(g);
            break;
         }
         //if (c.isHit(event.getX(), event.getY()))
         //   System.out.println(c.fullToString());
      }
   }
}