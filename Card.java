import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.util.*;

public class Card extends Clickable
{
   /**
     *the suit of this card (Spades, Hearts, Clubs, or Diamonds)
     */
   private final Suit suit;
   
   /**
     *the value of this card. 1 is Ace, 11 is Jack, 12 is Queen, 13 is King, 2-10 are themselves
     */
   private final int value;
   
   /**
     *the color of this card (red or black). depends on the suit
     */
   private final Color color;
   
   /**
     *the width, in pixels, of any card
     */
   public static final int CARD_WIDTH = 60;
   
   /**
     *the height, in pixels, of any card
     */
   public static final int CARD_HEIGHT = 90;
   
   /**
     *this enum contains all four suits. accessed by many classes within the overall program.
     */
   public static enum Suit {Spades, Clubs, Hearts, Diamonds}
   
   /**
     *the image for this card's suit
     */
   private BufferedImage suitGraphic;
   
   /**
     *the width of all possible suit graphics. also 1/4 of the card width
     */
   public static final int SMALL_SUIT_WIDTH = 15;
   
   /**
     *the height of all possible suit graphics. also the width (suit graphics are square).
     */
   public static final int SMALL_SUIT_HEIGHT = 15;
   
   /**
     *the graphic that appears on the back side of the card
     */
   private BufferedImage backside;
   
   /**
     *true if this card is face up, false if this card is face down
     */
   private boolean faceUp;
   
   /**
     *true if this card is selected, false otherwise
     */
   private boolean selected;
   
   /**
     *this enum contains the three different locations which the card can be in
     */
   public static enum Location {DECK, PILE, FOUNDATION}
   
   /**
     *the location in the card, as given in the Location enum. Should be changed whenever the card is
     *moved from one location to another.
     */
   private Location location;
   
   /**
     *the constructor for a Card. Initializes all necessary instance variables, and gets the images for
     *the suit and the backside. The Card is by default made facedown and not selected.
     *
     *@param s - the suit of this card
     *@param v - the value of this card. must be between 1 and 13, inclusive
     *@param x - the x coordinate of the top left corner of this card
     *@param y - the y coordinate of the top left corner of this card
     */
   public Card(Suit s, int v, int x, int y)
   {
      super(x, y);
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
   
   /**
     *the default constructor for a Card. Creates an Ace of Spades in the top left corner of the panel.
     */
   public Card()
   {
      this(Suit.Spades, 1, 0, 0);
   }
   
   /**
     *accessor method for the color of this card
     *
     *@return color - the color of this card
     */
   public Color getColor()
   {
      return color;
   }
   
   
   /**
     *accessor method for the suit of this card
     *
     *@return suit - the suit of this card
     */
   public Suit getSuit()
   {
      return suit;
   }
   
   /**
     *accessor method for the value of this card
     *
     *@return value - the value of this card
     */
   public int getValue()
   {
      return value;
   }
   
   /**
     *mutator method for the x coordinate of the top left corner of this card
     *
     *@param x - the new top left x coordinate
     */
   public void setX(int x)
   {
      this.x = x;
   }
   
   /**
     *accessor method for the x coordinate of the top left corner of this card
     *
     *@return x - the x coordinate of the top left corner of this card
     */
   public int getX()
   {
      return x;
   }
   
   /**
     *mutator method for the y coordinate of the top left corner of this card
     *
     *@param y - the new top left y coordinate
     */
   public void setY(int y)
   {
      this.y = y;
   }
   
   /**
     *accessor method for the y coordinate of the top left corner of this card
     *
     *@return y - the top left y coordinate
     */
   public int getY()
   {
      return y;
   }
   
   /**
     *returns a string representation of this card, giving the value and the suit
     *
     *@return the string representation of this card
     */
   public String toString()
   {
      return (value + " of " + suit);
   }
   
   /**
     *draws this card on the drawingpanel.
     *
     *@param g - the graphics context for the drawingpanel on which this card will be drawn
     */
   public void draw(Graphics g)
   {
      if (faceUp)
      {
         if (selected)
         {
            g.setColor(Color.BLUE);
         }
         else
         {
            g.setColor(Color.WHITE);
         }
         g.fillRect(x, y, CARD_WIDTH, CARD_HEIGHT);
         g.drawImage(suitGraphic, x + CARD_WIDTH - SMALL_SUIT_WIDTH, y, null);
         g.drawImage(suitGraphic, x, y + CARD_HEIGHT - SMALL_SUIT_HEIGHT, null);
         g.setFont(new Font(Solitaire.fontName, Font.PLAIN, 30));
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
         g.setFont(new Font(Solitaire.fontName, Font.PLAIN, 15));
         g.drawString(valueStr, x + 3, y + 15);
      }
      else
      {
         g.drawImage(backside, x, y, null);
      }
      g.setColor(Color.BLACK);
      g.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
   }
   
   /**
     *flips this card. if it's face up, it becomes face down, and vice versa.
     */
   public void flip()
   {
      faceUp = !faceUp;
   }
   
   /**
     *selects this card by setting the boolean selected to true
     */
   public void select()
   {
      selected = true;
   }
   
   /**
     *unselects this card by setting the boolean selected to false
     */
   public void unselect()
   {
      selected = false;
   }
   
   /**
     *returns true if this card is face up, false if not
     *
     *@return faceUp - the boolean that tells whether or not the card is face up
     */
   public boolean isFaceUp()
   {
      return faceUp;
   }
   
   /**
     *gives a more extensive string representation of this card. includes its faceup status, location,
     *and selected status in addition to the suit and the value
     *
     *@return string - the full string representation of this card
     */
   public String fullToString()
   {
      String string = toString();
      string += ", face up: " + faceUp + ", location: (" + x + ", " + y + "), selected: " + selected;
      return string;
   }
   
   /**
     *returns true if this card is selected, false if not
     *
     *@return selected - the boolean that tells whether or not this card is selected
     */
   public boolean isSelected()
   {
      return selected;
   }
   
   /**
     *mutator method for the location of this card
     *
     *@param l - the new location of the card
     */
   public void setLocation(Location l)
   {
      location = l;
   }
   
   /**
     *accessor method for the location of this card
     *
     *@return location - the location of this card
     */
   public Location getLocation()
   {
      return location;
   }
   
   /**
     *this method checks to see if this card is the top card at the given coordinates
     *
     *@param x - the x coordinate of the point to be checked
     *@param y - the y coordinate of the point to be checked
     *@return true if this card is the top card at the given point, false if not
     */   
   public boolean isTopAt(int x, int y)
   {
      if (isHit(x, y))
      {
         if (getLocation().equals(Location.PILE))
         {
            Pile p = Solitaire.whichPileIsHit(x);
            ArrayList<Card> seeable = p.getSeeable();
            Card top = null;
            for (int ii = seeable.size() - 1; ii >= 0; ii--)
            {
               if (seeable.get(ii).isHit(x, y))
               {
                  top = seeable.get(ii);
                  break;
               }
            }
            if (top != null)
            {
               return top.equals(this);
            }
            else
            {
               return false;
            }
         }
         else if (getLocation().equals(Location.FOUNDATION))
         {
            Foundation f = Solitaire.whichFoundationIsHit(y);
            ArrayList<Card> cards = f.getCards();
            return cards.get(cards.size() - 1).equals(this);
         }
         else //deck
         {
            if (faceUp)
            {
               Deck deck = Solitaire.getDeck();
               ArrayList dealt = deck.getDealt();
               if (dealt.get(dealt.size() - 1).equals(this))
               {
                  return true;
               }
               else
               {
                  return false;
               }
            }
            else
            {
               return false;
            }
         }
      }
      else
      {
         return false;
      }
   }
}