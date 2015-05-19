public class Clickable
{
   protected int x;
   protected int y;
   
   public Clickable(int x, int y)
   {
      this.x = x;
      this.y = y;
   }
   
   public Clickable()
   {
      this(0, 0);
   }
   
   public boolean isHit(int x, int y)
   {
      return (x >= this.x && x <= this.x + Card.CARD_WIDTH && y >= this.y && y <= this.y + Card.CARD_HEIGHT);
   }
}