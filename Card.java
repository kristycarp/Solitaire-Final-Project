import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class Card extends Clickable
{
   //private boolean isClicked;
   private Suit suit;
   private int value;
   private Color color;
   public static final int CARD_WIDTH = 60;
   public static final int CARD_HEIGHT = 90;
   public static enum Suit {Spades, Clubs, Hearts, Diamonds}
   private BufferedImage suitGraphic;
   public static final int SMALL_SUIT_WIDTH = CARD_WIDTH / 4;
   public static final int SMALL_SUIT_HEIGHT = SMALL_SUIT_WIDTH; //just how the dimensions worked out
   private BufferedImage backside;
   private boolean faceUp;
   private boolean selected;
   public static enum Location {DECK, PILE, FOUNDATION}
   private Location location;
   
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
      
      try
      {
         backside = ImageIO.read(new File("cardback1.png"));
      }
      catch (IOException e)
      {
         System.out.println("Problem loading card back image");
      }
      
      faceUp = false;
      selected = false;
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
   
   public int getX()
   {
      return x;
   }
   
   public void setY(int y)
   {
      this.y = y;
   }
   
   public String toString()
   {
      return (value + " of " + suit);
   }
   
   public void draw(Graphics g)
   {
      if (faceUp)
      {
         if (selected)
         {
            g.setColor(Color.BLUE);
         }
         else
            g.setColor(Color.WHITE);
         g.fillRect(x, y, CARD_WIDTH, CARD_HEIGHT);
         g.drawImage(suitGraphic, x + CARD_WIDTH - SMALL_SUIT_WIDTH, y, null);
         g.drawImage(suitGraphic, x, y + CARD_HEIGHT - SMALL_SUIT_HEIGHT, null);
         g.setFont(new Font("Segoe UI Light", Font.PLAIN, 30));
         g.setColor(color);
         String valueStr = "";
         if (value == 1)
         {
            valueStr = "A";
         }
         else if (value == 11)
         {
            valueStr = "J";
         }
         else if (value == 12)
         {
            valueStr = "Q";
         }
         else if (value == 13)
         {
            valueStr = "K";
         }
         else
         {
            valueStr += value;
         }
         g.drawString(valueStr, x + 23, y + 50);
         g.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
         g.drawString(valueStr, x + 3, y + 15);
         //g.drawString(valueStr, x + CARD_WIDTH - 15, y + CARD_HEIGHT - 3);
         //System.out.println("I drew " + toString() + ", selected: " + selected + ", location: " + location);
      }
      else
      {
         g.drawImage(backside, x, y, null);
      }
      g.setColor(Color.BLACK);
      g.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
   }
   
   public void flip()
   {
      faceUp = !faceUp;
   }
   
   public void select()
   {
      selected = true;
   }
   
   public void unselect()
   {
      //System.out.println(fullToString());
      selected = false;
      //System.out.println(fullToString());
   }
   
   public boolean isFaceUp()
   {
      return faceUp;
   }
   
   public String fullToString()
   {
      String string = toString();
      string += ", face up: " + faceUp + ", location: (" + x + ", " + y + "), selected: " + selected;
      return string;
   }
   
   public boolean isSelected()
   {
      return selected;
   }
   
   public void setLocation(Location l)
   {
      location = l;
   }
   
   public Location getLocation()
   {
      return location;
   }
}