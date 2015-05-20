import java.util.*;
import java.awt.*;

public class Deck
{
   private ArrayList<Card> fullDeck;
   //private ArrayList<Card> holding;
   private Stack<Card> undealt;
   private ArrayList<Card> dealt;
   private int x;
   private int undealtY;
   private int dealtY;
   private static final int SPACING = Card.CARD_HEIGHT + 20;
   
   public Deck(ArrayList<Card> deck, int x, int undealtY)
   {
      fullDeck = deck;
      undealt = new Stack<Card>();
      for (int ii = fullDeck.size() - 1; ii >= 0; ii--)
      {
         Card c = fullDeck.get(ii);
         c.setX(x);
         c.setY(undealtY);
         c.setLocation(Card.Location.DECK);
         undealt.push(c);  
      }
      dealt = new ArrayList<Card>();
      this.x = x;
      this.undealtY = undealtY;
      dealtY = undealtY + SPACING;
   }
   
   public void deal()
   {
      if (fullDeck.size() != 0)
      {
         if (undealt.size() != 0)
         {
            for (int ii = 0; ii < dealt.size(); ii++)
            {
               Card movingCard = dealt.get(ii);
               movingCard.setY(dealtY);
               dealt.set(ii, movingCard);
            }
            if (undealt.size() > 2)
            {
               for (int jj = 1; jj <= 3; jj++)
               {
                  Card movingCard = undealt.pop();
                  movingCard.setY(dealtY + (jj - 1) * Card.SMALL_SUIT_HEIGHT);
                  movingCard.flip();
                  dealt.add(movingCard);
               }
            }
            else
            {
               int undealtCards = undealt.size();
               for (int kk = 0; kk < undealtCards; kk++)
               {
                  Card movingCard = undealt.pop();
                  movingCard.setY(dealtY + (kk - 1) * Card.SMALL_SUIT_HEIGHT);
                  movingCard.flip();
                  dealt.add(movingCard);
               }
            }
         }
         else
         {
            int nCardsLeft = dealt.size() - 1; //to avoid diminishing size
            for (int a = nCardsLeft; a >= 0; a--)
            {
               Card movingCard = dealt.remove(a);
               movingCard.setY(undealtY);
               movingCard.flip();
               undealt.push(movingCard);
            }
         }
         Solitaire.drawScreen();
      }
   }
   
   /**public boolean holdingHasStuff()
   {
      return (holding.size() != 0);
   }**/
   
   public void draw(Graphics g)
   {
      g.setColor(Color.BLACK);
      g.fillRect(x, undealtY, Card.CARD_WIDTH, Card.CARD_HEIGHT);
      for (Card c : fullDeck)
      {
         c.draw(g);
      }
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
   
   public ArrayList<Card> getDealt()
   {
      return dealt;
   }
   
   public void removeTop()
   {
      if (!dealt.isEmpty())
      {
         Card c = dealt.remove(dealt.size() - 1);
         fullDeck.remove(c);
      }
   }
}