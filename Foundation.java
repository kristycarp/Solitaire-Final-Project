import java.util.*;
import java.awt.*;

public class Foundation extends Pile //referred to as DonePile in design doc
{
   private Card.Suit suit;
   private Stack<Card> cardList;
   
   public Foundation(int x, int y)
   {
      super(x, y);
      cardList = new Stack<Card>();
   }
   
   public Foundation()
   {
      this(0, 0);
   }
   
   //this method assumes that the Cards are being correctly added to the stack of cards, i.e. that
   //if a King is added then all the other cards have been added correctly
   public boolean isComplete()
   {
      if (cardList.peek().getValue() == 13)
         return true;
      return false;
   }
   
   //returns true if successful add, false if unsuccessful
   public boolean addCard(Card c)
   {
      if (c.getValue() == 1 && cardList.empty())
      {
         c.setLocation(Card.Location.FOUNDATION);
         cardList.push(c);
         suit = c.getSuit();
         return true;
      }
      else if (!cardList.empty() && c.getSuit().equals(suit) && c.getValue() + 1 == cardList.peek().getValue())
      {
         c.setLocation(Card.Location.FOUNDATION);
         cardList.push(c);
         return true;
      }
      return false;
   }
   
   //will throw exception if no cards to remove, so make sure you call it after making sure there is
   //something to remove
   public Card removeCard()
   {
      if (!cardList.empty())
      {
         return cardList.pop();
      }
      else
      {
         throw new IndexOutOfBoundsException();
      }
   }
}