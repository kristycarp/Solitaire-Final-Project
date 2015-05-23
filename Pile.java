import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Pile extends Clickable
{
   private ArrayList<Card> seeableCardList;
   private ArrayList<Card> unseenCardList;
   private final int SPACING = 10;
   public static int PILE_Y = 10;
   
   public Pile(int x, int y)
   {
      super(x, y);
      seeableCardList = new ArrayList<Card>();
      unseenCardList = new ArrayList<Card>();
   }
   
   public Pile()
   {
      this(0, 0);
   }
   
  /** public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      g.setColor(Color.ORANGE);
      g.fillRect(x, y, Card.CARD_WIDTH, Card.CARD_HEIGHT);
   }**/
   
   public void draw(Graphics g)
   {
      g.setColor(Color.BLACK);
      g.fillRect(x, y, Card.CARD_WIDTH, Card.CARD_HEIGHT);
   }
   
   public void setX(int x)
   {
      this.x = x;
   }
   
   public void setY(int y)
   {
      this.y = y;
   }
   
   public void addCard(Card c)
   {
      if (seeableCardList.isEmpty() && c.getValue() == 13) //king on empty piile
      {
         c.setLocation(Card.Location.PILE);
         c.setX(x);
         c.setY(y);
         seeableCardList.add(c);
         //return true;
      }
      else if ((seeableCardList.get(seeableCardList.size() - 1).getColor().equals(Color.RED) && c.getColor().equals(Color.BLACK)) || (seeableCardList.get(seeableCardList.size() - 1).getColor().equals(Color.BLACK) && c.getColor().equals(Color.RED)) && seeableCardList.get(seeableCardList.size() - 1).getValue() == c.getValue() + 1)
      {
         c.setLocation(Card.Location.PILE);
         c.setX(x);
         c.setY(y + (SPACING + Card.SMALL_SUIT_HEIGHT) * (seeableCardList.size() + unseenCardList.size()));
         seeableCardList.add(c);
         //return true;
      }
      //return false;
   }
   
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
   
   public boolean isEmpty()
   {
      return (seeableCardList.isEmpty());
   }
   
   public void dealCard(Card c)
   {
      c.setX(x);
      c.setY(y + (unseenCardList.size() * (SPACING + Card.SMALL_SUIT_HEIGHT)));
      c.setLocation(Card.Location.PILE);
      unseenCardList.add(c);
   }
   
   public String toString()
   {
      String string = "";
      for (int ii = 0; ii < unseenCardList.size(); ii++)
      {
         string += unseenCardList.get(ii).toString() + "\n";
      }
      
      return string;
   }
   
   public int getX()
   {
      return x;
   }
   
   public void flipLastCard()
   {
      if (unseenCardList.size() > 0 && seeableCardList.isEmpty())
      {
         //System.out.println("unseen before: " + unseenCardList.toString());
         //System.out.println("seen before: " + seeableCardList.toString());
         Card c = unseenCardList.remove(unseenCardList.size() - 1);
         //System.out.println(c.fullToString());
         c.flip();
         //System.out.println(c.fullToString());
         seeableCardList.add(c);
         //System.out.println("unseen after: " + unseenCardList.toString());
         //System.out.println("seen after: " + seeableCardList.toString());
         //System.out.println(c.toString());
      }
      /**else
      {
         System.out.println("don't call this method you fool");
         //System.out.println(seeableCardList.toString());
      }**/
   }
   
   /**public ArrayList<Card> allCardsInPile()
   {
      ArrayList<Card> allCards = new ArrayList<Card>(unseenCardList.size() + seeableCardList.size());
      for (Card c : unseenCardList)
      {
         allCards.add(c);
      }
      int index = unseenCardList.size() + seeableCardList.size() - 1;
      for (Card c : seeableCardList)
      {
         allCards.add(index, c);
         index--;
      }
      return allCards;
   }**/
   
   /**public ArrayList<Card> allCardsInPile()
   {
      ArrayList<Card> allCards = unseenCardList;
      for (Card c : seeableCardList)
      {
         allCards.add(c);
      }
      return allCards;
   }**/
   
   public ArrayList<Card> getSeeable()
   {
      return seeableCardList;
   }
   
   public ArrayList<Card> getUnseen()
   {
      return unseenCardList;
   }

   
   public void removeLastCard()
   {
      if (!seeableCardList.isEmpty())
         seeableCardList.remove(seeableCardList.size() - 1);
      //System.out.println(seeableCardList.toString());
   }
   
   public boolean equals(Pile p)
   {
      return (x == p.getX());
   }
}