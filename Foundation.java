import java.util.*;
import java.awt.*;

public class Foundation extends Pile //referred to as DonePile in design doc
{
   private Card.Suit suit;
   private ArrayList<Card> cardList;
   
   public Foundation(int x, int y)
   {
      super(x, y);
      cardList = new ArrayList<Card>();
   }
   
   public Foundation()
   {
      this(0, 0);
   }
   
   //this method assumes that the Cards are being correctly added to the stack of cards, i.e. that
   //if a King is added then all the other cards have been added correctly
   public boolean isComplete()
   {
      if (cardList.get(cardList.size() - 1).getValue() == 13)
         return true;
      return false;
   }
   
   //returns true if successful add, false if unsuccessful
   public void addCard(Card c)
   {
      if (c.getValue() == 1 && cardList.isEmpty())
      {
         c.setLocation(Card.Location.FOUNDATION);
         c.setX(x);
         c.setY(y);
         cardList.add(c);
         suit = c.getSuit();
         //System.out.println("I added an ace");
         //return true;
      }
      else if (!cardList.isEmpty() && c.getSuit().equals(suit) && c.getValue() == cardList.get(cardList.size() - 1).getValue() + 1)
      {
         c.setLocation(Card.Location.FOUNDATION);
         c.setX(x);
         c.setY(y);
         cardList.add(c);
         //System.out.println("I added " + c.fullToString());
         //return true;
      }
      //return false;
   }
   
   public boolean canAddCard(Card c)
   {
      if (c.getValue() == 1 && cardList.isEmpty())
      {
         return true;
      }
      else if (!cardList.isEmpty() && c.getSuit().equals(suit) && c.getValue() == cardList.get(cardList.size() - 1).getValue() + 1)
      {
         return true;
      }
      return false;
   }
   
   //will throw exception if no cards to remove, so make sure you call it after making sure there is
   //something to remove
   public Card removeCard()
   {
      if (!cardList.isEmpty())
      {
         return cardList.remove(cardList.size() - 1);
      }
      else
      {
         throw new IndexOutOfBoundsException();
      }
   }
   
   public ArrayList<Card> getCards()
   {
      return cardList;
   }
}