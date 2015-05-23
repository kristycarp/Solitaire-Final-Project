import javax.swing.*; //for gui components
import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;


public class Solitaire
{
   /**
     *the width of the drawingpanel that contains all the graphics
     */
   public static final int PANEL_WIDTH = 800;
   
   /**
     *the height of the drawingpanel that contains all the graphics
     */
   public static final int PANEL_HEIGHT = 600;
   
   /**
     *the spacing constant. represents the pixels between each pile and foundation
     */
   public static final int PILES_SPACE = 20;
   
   /**
     *the array containing all the piles that will be created
     */
   private static Pile[] pileArray;
   
   /**
     *the background image of the drawingpanel
     */
   private static BufferedImage background;
   
   /**
     *the drawingpanel used
     */
   private static DrawingPanel panel;
   
   /**
     *the graphics context for the drawingpanel
     */
   private static Graphics g;
   
   /**
     *the deck, containing all the cards that are not in a pile or a foundation
     */
   private static Deck deck;
   
   /**
     *the array containing all the foundations that will be created
     */
   private static Foundation[] foundationArray;
   
   /**
     *the button that, when pushed, triggers the deal function of the deck
     */
   private static Button dealButton;
   
   /**
     *the button that, when pushed, moves all possible cards to the foundation
     */
   private static Button completeButton;
   
   /**
     *the button that, when pushed, restarts the game
     */
   private static Button restartButton;
   
   /**
     *the name of the font that is used on the cards and when the user wins
     */
   public static final String fontName = "Segoe UI Light";
   
   /**
     *the y coordinate of the tops of all piles
     */
   public static int PILE_Y = 10;
   
   /**
     *the main method, where the program begins
     *
     *@param args
     */
   public static void main(String[] args)
   {
      
      panel = new DrawingPanel(PANEL_WIDTH, PANEL_HEIGHT);
      g = panel.getGraphics();
      try
      {
         background = ImageIO.read(new File("background1.png"));
      }
      catch (IOException e)
      {
         System.out.println("Problem loading background image");
      }
      
      setup();
   }
   
   /**
     *this method creates a pile. It uses an integer parameter to determine where the pile should go
     *
     *@param i - the integer that helps with spacing all seven piles
     *@return the pile that was created
     */
   public static Pile makePile(int i)
   {
      return (new Pile(i * (Card.CARD_WIDTH + PILES_SPACE) + 2 * PILES_SPACE, PILE_Y));
   }
   
   /**
     *creates a foundation, given an integer parameter for spacing purposes
     *
     *@param i - the integer that helps with spacing all four foundations
     *@return the foundation that was created
     */
   public static Foundation makeFoundation(int i)
   {
      return (new Foundation(PANEL_WIDTH - Card.CARD_WIDTH - PILES_SPACE, i * (Card.CARD_HEIGHT + PILES_SPACE) + 10));
   }
   
   /**
     *creates a new deck with all 52 cards in a standard deck, then uses DeckShuffler (from the poker
     *lab, but written by me) to shuffle it. Returns the shuffled deck as an ArrayList of Cards.
     *
     *@return realDeck - the shuffled deck, as an ArrayList of Cards
     */
   public static ArrayList<Card> generateAndShuffle()
   {
      Card[] deck = new Card[52];
      int ii = 0;
      for (Card.Suit s : Card.Suit.values())
      {
         for (int value = 1; value <= 13; value++)
         {
            deck[ii] = new Card(s, value, 0, 0);
            ii++;
         }
      }
      DeckShuffler.shuffle(deck, 52); //DeckShuffler not written by me--from the poker lab
      ArrayList<Card> realDeck = new ArrayList<Card>(52);
      for (Card c : deck)
      {
         realDeck.add(c);
      }
      
      //for rigging purposes
      /**for (int asdf = 0; asdf < 52; asdf++)
      {
         realDeck.set(asdf, new Card());
      }
      realDeck.set(27, new Card(Card.Suit.Spades, 2, 0, 0));
      realDeck.set(26, new Card(Card.Suit.Spades, 3, 0, 0));
      realDeck.set(24, new Card(Card.Suit.Hearts, 4, 0, 0));**/

      return realDeck;
   }
   
   /**
     *this method takes the deck and deals out cards to all the piles. It then returns the remaining
     *deck, which will become the actual starting deck of the game.
     *
     *@param deck - the deck containing all 52 cards, usually will also be shuffled
     *@param pileArray - an array containing all the piles. This method will deal cards to each of the
     *piles in this array
     */
   public static ArrayList<Card> dealToPiles(ArrayList<Card> deck, Pile[] pileArray)
   {
      for (int nPilesToDeal = 7; nPilesToDeal > 0; nPilesToDeal--)
      {
         for (int nCardsDealt = 1; nCardsDealt <= nPilesToDeal; nCardsDealt++)
         {
            pileArray[nCardsDealt - 1].dealCard(deck.remove(0));
         }
      }
      return deck;
   }
   
   /**
     *this method goes through all the piles that have been created and checks to see if a given
     *x coordinate is inside any of them. It returns the pile that contains the x coordinate, or, if
     *there is no such pile, null. The reason why only an x coordinate is needed to identify a pile is
     *due to the fact that all piles have the same top y coordinate (since they're in a row)
     *
     *@param x - the x coordinate which is being checked for being inside a pile
     *@return either the pile that has the given x coordinate or null
     */
   public static Pile whichPileIsHit(int x)
   {
      for (Pile p : pileArray)
      {
         if (x >= p.getX() && x <= p.getX() + Card.CARD_WIDTH)
         {
            return p;
         }
      }
      System.out.println("no pile was hit");
      return null; 
   }
   
   /**
     *This method clears the screen, then draws the screen background, then draws all the objects on the
     *screen. It does this by calling the draw method for each object.
     */
   public static void drawScreen() //NOTE TO SELF: MAKE THIS MORE EFFICIENT? MAKE A LIST WITH DRAWABLE THINGS
   {                               //AND LIKE GO THROUGH ALL CLICKABLES AND DRAW THEM LIKE THAT???
      g.setColor(Color.WHITE);
      g.drawRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
      g.drawImage(background, 0, 0, null);
      for (Pile p : pileArray)
      {
         p.draw(g);
         for (Card c : p.getUnseen())
         {
            c.draw(g);
         }
         for (Card c : p.getSeeable())
         {
            c.draw(g);
         }
         //System.out.println(p.toString());
      } 
      deck.draw(g);
      for (Foundation f : foundationArray)
      {
         f.draw(g);
         for (Card c : f.getCards())
         {
            c.draw(g);
         }
      }
      dealButton.draw(g);
      completeButton.draw(g);
      restartButton.draw(g);
   }
   
   /**
     *this method uses the array of foundations to find which foundation, if any, was hit by something.
     *It only takes a y parameter because all foundations have the same x value. If no foundation was
     *hit, this method returns null. It is assumed that this will not happen frequently because this method
     *should only be called when some foundation is hit, and the program needs to know which one is hit.
     *
     *@param y - the y coordinate of the click or the card that is being checked
     */
   public static Foundation whichFoundationIsHit(int y)
   {
      for (Foundation f : foundationArray)
      {
         if (y >= f.getY() && y <= f.getY() + Card.CARD_HEIGHT)
         {
            return f;
         }
      }
      System.out.println("no foundation was hit");
      return null; 
   }
   
   /**
     *this method returns the current deck that is being used
     *
     *@return deck - the deck
     */
   public static Deck getDeck()
   {
      return deck;
   }
   
   /**
     *this method sets up all the necessary objects and variables to begin a new game.
     *Called in the beginning of the game and whenever the restart button is clicked.
     */
   public static void setup()
   {
      pileArray = new Pile[7];
      for (int ii = 1; ii <= 7; ii++)
      {
         pileArray[ii - 1] = makePile(ii);
      }
      
      foundationArray = new Foundation[4];
      for (int jj = 1; jj <= 4; jj++)
      {
         foundationArray[jj - 1] = makeFoundation(jj);
      }

      ArrayList<Card> deck52 = generateAndShuffle();
      ArrayList<Card> permaDeck = (ArrayList<Card>) deck52.clone();
      deck = new Deck(dealToPiles(deck52, pileArray), PILES_SPACE, Card.CARD_HEIGHT + PILES_SPACE + 10);
      CardListener cListener = new CardListener(permaDeck, panel, foundationArray, pileArray, deck);
      panel.addMouseListener(cListener);
      
      for (Pile p : pileArray)
      {
         p.flipLastCard();
      }
      
      dealButton = new Button(PILES_SPACE, Card.CARD_HEIGHT + PILES_SPACE - Button.BUTTON_HEIGHT, "Draw");
      DealButtonListener dealListener = new DealButtonListener(dealButton, deck);
      panel.addMouseListener(dealListener);
      
      completeButton = new Button(PILES_SPACE, PANEL_HEIGHT - Button.BUTTON_HEIGHT - PILES_SPACE, "Move");
      CompleteButtonListener completeListener = new CompleteButtonListener(completeButton, permaDeck, foundationArray);
      panel.addMouseListener(completeListener);
      
      restartButton = new Button(PANEL_WIDTH - Button.BUTTON_WIDTH - PILES_SPACE, PILES_SPACE, "Restart");
      RestartButtonListener restartListener = new RestartButtonListener(restartButton);
      panel.addMouseListener(restartListener);
      
      drawScreen();
   }
   
}