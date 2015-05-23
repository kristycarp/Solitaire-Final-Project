import javax.swing.event.*;
import java.awt.event.*;

public class RestartButtonListener extends MouseInputAdapter
{
   private Button b;
   
   public RestartButtonListener(Button b)
   {
      this.b = b;
   }
   
   public void mouseClicked(MouseEvent event)
   {
      if (b.isHit(event.getX(), event.getY()))
      {
         Solitaire.setup();
      }
   }
}