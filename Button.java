import java.awt.*;

public class Button extends Clickable
{
   public static final int BUTTON_WIDTH = Card.CARD_WIDTH;
   public static final int BUTTON_HEIGHT = 40;
   private static final Color BUTTON_COLOR = Color.WHITE;
   private String message;
   
   public Button(int x, int y, String text)
   {
      super(x, y);
      message = text;
      
   }
   
   public Button()
   {
      this(0, 0, "");
   }
   
   public Button(String text)
   {
      this(0, 0, text);
   }
   
   public void draw(Graphics g)
   {
      g.setColor(BUTTON_COLOR);
      g.fillRect(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
      g.setColor(Color.BLACK);
      g.drawRect(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
      g.drawString(message, x + BUTTON_HEIGHT / 2 - 10, y + BUTTON_HEIGHT / 2 + 5);
   }
   
   public boolean isHit(int x, int y)
   {
      return (x >= this.x && x <= this.x + BUTTON_WIDTH && y >= this.y && y <= this.y + BUTTON_HEIGHT);
   }
}