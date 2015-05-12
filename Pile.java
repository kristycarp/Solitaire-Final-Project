import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Pile extends JPanel
{
   protected int x;
   protected int y;
   protected Stack<Card> seeableCardList;
   private ArrayList<Card> unseenCardList;
   
   public Pile(int x, int y)
   {
      this.x = x;
      this.y = y;
      seeableCardList = new Stack<Card>();
      unseenCardList = new ArrayList<Card>();
   }
   
   public Pile()
   {
      this(0, 0);
   }
   
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      g.setColor(Color.ORANGE);
      g.fillRect(x, y, Card.CARD_WIDTH, Card.CARD_HEIGHT);
   }
   
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
   
   public boolean addCard(Card c)
   {
      if (seeableCardList.size() == 0 && c.getValue() == 13)
      {
         seeableCardList.push(c);
         return true;
      }
      else if ((seeableCardList.peek().getColor().equals(Color.RED) && c.getColor().equals(Color.BLACK)) || (seeableCardList.peek().getColor().equals(Color.BLACK) && c.getColor().equals(Color.RED)) && seeableCardList.peek().getValue() == c.getValue() + 1)
      {
         seeableCardList.push(c);
         return true;
      }
      return false;
   }
   
   public boolean isEmpty()
   {
      return (seeableCardList.size() == 0);
   }
   
   public void dealCard(Card c)
   {
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
}