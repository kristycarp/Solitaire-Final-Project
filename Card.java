import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class Card
{
   //private boolean isClicked;
   private Suit suit;
   private int value;
   private int x;
   private int y;
   private Color color;
   public static final int CARD_WIDTH = 60;
   public static final int CARD_HEIGHT = 90;
   public static enum Suit {Spades, Clubs, Hearts, Diamonds}
   private BufferedImage suitGraphic;
   
   public Card(Suit s, int v, int x, int y)
   {
      if (v < 1 || v > 13)
         throw new IllegalArgumentException();
      suit = s;
      String filename = "";
      if (suit.equals(Suit.Spades) || suit.equals(Suit.Clubs))
      {
         color = Color.BLACK;
         if (suit.equals(Suit.Spades))
            filename = "spade.png";
         else
            filename = "club.png";
      }
      else
      {
         if (suit.equals(Suit.Hearts))
            filename = "heart.png";
         else
            filename = "diamond.png";
         color = Color.RED;
      }
      value = v;
      this.x = x;
      this.y = y;
      
      try
      {
         suitGraphic = ImageIO.read(new File(filename));
      }
      catch (IOException e)
      {
         System.out.println("Problem loading suit image");
      }
   }
   
   public Card()
   {
      this(Suit.Spades, 1, 0, 0);
   }
   
   /**public boolean isHit(int x, int y)
   {
      return false; //change this
   }**/
   
   public Color getColor()
   {
      return color;
   }
   
   public Suit getSuit()
   {
      return suit;
   }
   
   public int getValue()
   {
      return value;
   }
   
   public void setX(int x)
   {
      this.x = x;
   }
   
   public void setY(int y)
   {
      this.y = y;
   }
   
   public String toString()
   {
      return (value + " of " + suit);
   }
}