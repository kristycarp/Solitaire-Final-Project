import java.util.*;
import java.awt.*;

public class Foundation extends Pile //referred to as DonePile in design doc
{
   /**
     *the designated suit for this foundation. set only when the first card (ace) is moved to this foundation
     */
   private Card.Suit suit;
   
   /**
     *the list of all the cards in this foundation
     */
   private ArrayList<Card> cardList;
   
   /**
     *the constructor for a Foundation
     *
     *@param x - the x coordinate of the top left corner of this foundation
     *@param y - the y coordinate of the top left corner of this foundation
     */
   public Foundation(int x, int y)
   {
      super(x, y);
      cardList = new ArrayList<Card>();
   }
   
   /**
     *the default constructor for a Foundation. Creates a Foundation in the top left corner of the panel
     */
   public Foundation()
   {
      this(0, 0);
   }

   /**
     *this method checks to see if this foundation has been completed. it assumes that the Cards are
     *being correctly added to the list of cards, i.e. that if a King is added then all the other
     *cards preceding it have been added correctly
     *
     *@return true if this foundation is complete, false if it is not
     */
   public boolean isComplete()
   {
      if (!cardList.isEmpty() && cardList.get(cardList.size() - 1).getValue() == 13)
      {
         return true;
      }
      return false;
   }
   
   /**
     *if it is legal, this method adds a card to the foundation and changes the necessary variables to
     *truly add the card
     *
     *@param c - the card to be added
     */
   public void addCard(Card c)
   {
      if (c.getValue() == 1 && cardList.isEmpty())
      {
         c.setLocation(Card.Location.FOUNDATION);
         c.setX(x);
         c.setY(y);
         cardList.add(c);
         suit = c.getSuit();
      }
      else if (!cardList.isEmpty() && c.getSuit().equals(suit) && c.getValue() == cardList.get(cardList.size() - 1).getValue() + 1)
      {
         c.setLocation(Card.Location.FOUNDATION);
         c.setX(x);
         c.setY(y);
         cardList.add(c);
      }
   }
   
   /**
     *this method checks to see if a given card can be legally added to this foundation
     *
     *@param c - the card to be checked
     *@return true if the card can be legally added, false otherwise
     */
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
   
   /**
     *this method removes the topmost (highest value and highest index) card in the cardList of this
     *foundation. if the cardList is empty, it throws an IndexOutOfBounds exception
     *
     *@return the card that was removed
     */
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
   
   /**
     *accessor method for the list of all cards in the foundation
     *
     *@return cardList - all the cards in the foundation, in order from least to greatest value
     */
   public ArrayList<Card> getCards()
   {
      return cardList;
   }
   
   /**
     *accessor method for the y coordinate of the top left corner of this foundation
     *
     *@return y - the top left y coordinate
     */
   public int getY()
   {
      return y;
   }
}