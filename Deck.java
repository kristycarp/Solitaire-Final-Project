import java.util.*;
import java.awt.*;

public class Deck
{
   private ArrayList<Card> fullDeck;
   private ArrayList<Card> holding;
   private Stack<Card> undealt;
   private Stack<Card> dealtUnseen;
   private int undealtX;
   private int undealtY;
   
   public Deck(ArrayList<Card> deck, int x, int y)
   {
      fullDeck = deck;
      for (int ii = 0; ii < fullDeck.size(); ii++)
      {
         Card c = fullDeck.get(ii);
         c.setX(x);
         c.setY(y);
         fullDeck.set(ii, c);
      }
      holding = new ArrayList<Card>();
      undealt = new Stack<Card>();
      dealtUnseen = new Stack<Card>();
      undealtX = x;
      undealtY = y;
   }
   
   public void deal()
   {
      if (fullDeck.size() != 0)
      {
         if (undealt.size() != 0)
         {
            for (int ii = 0; ii < holding.size(); ii++)
            {
               dealtUnseen.push(holding.get(ii));
            }
            if (undealt.size() > 2)
            {
               for (int jj = 1; jj <= 3; jj++)
               {
                  holding.add(undealt.pop());
               }
            }
            else
            {
               int undealtCards = undealt.size();
               for (int kk = 0; kk < undealtCards; kk++)
               {
                  holding.add(undealt.pop());
               }
            }
         }
         else
         {
            int nCardsLeft = dealtUnseen.size(); //to avoid diminishing size
            for (int a = 0; a < nCardsLeft; a++)
            {
               undealt.push(dealtUnseen.pop());
            }
         }
      }
   }
   
   public boolean holdingHasStuff()
   {
      return (holding.size() != 0);
   }
   
   public void draw(Graphics g)
   {
      g.setColor(Color.BLACK);
      g.fillRect(undealtX, undealtY, Card.CARD_WIDTH, Card.CARD_HEIGHT);
   }
   
   public String toString()
   {
      String string = "";
      for (int ii = 0; ii < fullDeck.size(); ii++)
      {
         string += fullDeck.get(ii).toString() + "\n";
      }
      
      return string;
   }
}