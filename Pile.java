import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Pile extends Clickable
{
   /**
     *contains all cards that are face up, going from back (higher value) to front (lower value)
     */
   private ArrayList<Card> seeableCardList;
   
   /**
     *contains all cards that are face down, going from back (higher value) to front (lower value)
     */
   private ArrayList<Card> unseenCardList;
   
   /**
     *the amount of space left between the bottom of the suit graphic of a back card and the top of
     *a front card that's layered on top of it
     */
   private final int SPACING = 10;
   
   /**
     *the constructor for a Pile.
     *
     *@param x - the x coordinate for the top left corner of this pile
     *@param y - the y coordinate for the top left corner of this pile
     */
   public Pile(int x, int y)
   {
      super(x, y);
      seeableCardList = new ArrayList<Card>();
      unseenCardList = new ArrayList<Card>();
   }
   
   /**
     *the default constructor for a Pile. Creates a Pile in the top left corner of the panel.
     *
     */
   public Pile()
   {
      this(0, 0);
   }
   
   /**
     *draws this pile
     *
     *@param g - the graphics context for the drawingpanel on which this pile will be drawn
     */
   public void draw(Graphics g)
   {
      g.setColor(Color.BLACK);
      g.fillRect(x, y, Card.CARD_WIDTH, Card.CARD_HEIGHT);
   }
   
   /**
     *mutator method for the x coordinate of the top left corner of this pile
     *
     *@param x - the new top left x coordinate
     */
   public void setX(int x)
   {
      this.x = x;
   }
   
   /**
     *mutator method for the y coordinate of the top left corner of this pile
     *
     *@param y - the new top left y coordinate
     */
   public void setY(int y)
   {
      this.y = y;
   }
   
   /**
     *this method adds a card to the end of the seeableCardList if the card can be legally moved there. It
     *also sets some variables of the card so as to properly add it.
     *
     *@param c - the card to be added
     */
   public void addCard(Card c)
   {
      if (seeableCardList.isEmpty() && c.getValue() == 13) //king on empty piile
      {
         c.setLocation(Card.Location.PILE);
         c.setX(x);
         c.setY(y);
         seeableCardList.add(c);
      }
      else if ((seeableCardList.get(seeableCardList.size() - 1).getColor().equals(Color.RED) && c.getColor().equals(Color.BLACK)) || (seeableCardList.get(seeableCardList.size() - 1).getColor().equals(Color.BLACK) && c.getColor().equals(Color.RED)) && seeableCardList.get(seeableCardList.size() - 1).getValue() == c.getValue() + 1)
      {
         c.setLocation(Card.Location.PILE);
         c.setX(x);
         c.setY(y + (SPACING + Card.SMALL_SUIT_HEIGHT) * (seeableCardList.size() + unseenCardList.size()));
         seeableCardList.add(c);
      }
   }
   
   /**
     *this method returns true if the given card can be legally added to this pile's seeableCardList.
     *if it can't be legally added, it returns false
     *
     *@param c - the card that is being checked
     *@return true if the card can be legally added, false if it cannot
     */
   public boolean canAddCard(Card c)
   {
      if (seeableCardList.isEmpty() && c.getValue() == 13) //king on empty piile
      {
         return true;
      }
      else if (!seeableCardList.isEmpty() && ((seeableCardList.get(seeableCardList.size() - 1).getColor().equals(Color.RED) && c.getColor().equals(Color.BLACK)) || (seeableCardList.get(seeableCardList.size() - 1).getColor().equals(Color.BLACK) && c.getColor().equals(Color.RED))) && seeableCardList.get(seeableCardList.size() - 1).getValue() == c.getValue() + 1)
      {
         return true;
      }
      return false;
   }
   
   /**
     *returns true if the seeableCardList of this pile is empty. It is assumed that if the
     *seeableCardList is empty that the unseenCardList will also be empty.
     *
     *@return true if this pile is empty, false otherwise
     */
   public boolean isEmpty()
   {
      return (seeableCardList.isEmpty());
   }
   
   /**
     *adds a card to the unseenCardList of this pile, and changes values of the card to properly add it.
     *this method only gets called during the setup of a game.
     *
     *@param c - the card to be added
     */
   public void dealCard(Card c)
   {
      c.setX(x);
      c.setY(y + (unseenCardList.size() * (SPACING + Card.SMALL_SUIT_HEIGHT)));
      c.setLocation(Card.Location.PILE);
      unseenCardList.add(c);
   }
   
   /**
     *this method returns a string representation of this pile. Said representation has all of the
     *facedown cards in the pile. Used only for debugging purposes.
     *
     *@return string - the string representation of this pile
     */
   public String toString()
   {
      String string = "";
      for (int ii = 0; ii < unseenCardList.size(); ii++)
      {
         string += unseenCardList.get(ii).toString() + "\n";
      }  
      return string;
   }
   
   /**
     *accessor method for the x coordinate of the top left corner of this pile
     *
     *@return x - the top left x coordinate
     */
   public int getX()
   {
      return x;
   }
   
   /**
     *this method flips the last card in the unseenCardList from face down to face up, then adds it
     *to the seeableCardList. Will only be called if a card has to be flipped.
     */
   public void flipLastCard()
   {
      if (unseenCardList.size() > 0 && seeableCardList.isEmpty())
      {
         Card c = unseenCardList.remove(unseenCardList.size() - 1);
         c.flip();
         seeableCardList.add(c);
      }
   }
   
   /**
     *accessor method for the list of all face up cards in this pile
     *
     *@return seeableCardList - the arraylist containing all face up cards in the pile
     */
   public ArrayList<Card> getSeeable()
   {
      return seeableCardList;
   }
   
   /**
     *accessor method for the list of all face down cards in this pile
     *
     *@return unseenCardList - the arraylist containing all face down cards in the pile
     */
   public ArrayList<Card> getUnseen()
   {
      return unseenCardList;
   }

   /**
     *removes the topmost card in the seeableCardList
     */
   public void removeLastCard()
   {
      if (!seeableCardList.isEmpty())
      {
         seeableCardList.remove(seeableCardList.size() - 1);
      }
   }
   
   /**
     *checks to see if this pile is equal to another, given as a parameter. Does this by checking the
     *x coordinate of both piles against each other, since that is what differentiates piles, being that
     *they all have the same y coordinate.
     *
     *@param p - the pile that will be checked to see if it is equal to this one
     *@return true if the two piles are equal, false if they are not
     */
   public boolean equals(Pile p)
   {
      return (x == p.getX());
   }
}