import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HelloJava4
{
  public static void main( String[] args ) {
    JFrame frame = new JFrame( "HelloJava4" );
    frame.add( new HelloComponent4( "Hello Java!") );
    frame.setDefaultCloseOperation( JFrame. EXIT_ON_CLOSE );
    frame.setSize( 300, 300 );
    frame.setVisible( true );
  }
}

/*
main() creates a JFrame, our window, and adds "HelloJava4" to the title
Then, frame.add calls the HelloComponent4 object
Then, setDefaultCloseOperation tells the frame to exit when closed
Then, frame.setSize sets the window size
Then, frame.setVisible makes sure our window is visible (true!)
*/

class HelloComponent4 extends JComponent
    implements MouseMotionListener, ActionListener, Runnable
{
  String theMessage;
  int messageX = 125, messageY = 95; // Coordinates of the message

/*
New simple class, HelloComponent3, that extends JComponent
It implements MouseMotionListener and ActionListener 
The HelloComponent2 constructor then sets the text of theMessage
Then, it calls addMouseMotionListener, telling the system that the program is interested in anything involving movement of the mouse
*/

  JButton theButton;

  int colorIndex; // Current index into someColors.
  static Color[] someColors = {
       Color. black, Color.red, Color.green, Color. blue, Color.magenta };

/*
We declare an integer variable that serves as an index into the following array of colors (colorIndex)
We create and initialise an array of color objects within the variable someColors
*/

  boolean blinkState;

  public HelloComponent4( String message ) {
    theMessage = message;
    theButton = new JButton("Change Color" );
    setLayout( new FlowLayout() );
    add( theButton );
    theButton.addActionListener( this );
    addMouseMotionListener( this );
    Thread t = new Thread( this ); // This bit starts the blinking right away. Constructor creates a new instance of Thread
    t.start(); // This tells the blinking to begin using HelloComponent4's run() method
  }

/*
theButton is a variable of type JButton
An instance of the JButton class is created
The JButton constructor sets the color of the text
setLayout tells HelloComponent4 how to arrange components, using FlowLayout()
The add() method then adds the button to our component
*/

  public void paintComponent( Graphics g ) {
    g.setColor(blinkState ? getBackground() : currentColor(  )); // When blinkState is true, setColor() draws the text in the background color
    g.drawString( theMessage, messageX , messageY );
  }

  public void mouseDragged(MouseEvent e ) {
    messageX = e.getX();
    messageY = e.getY();
    repaint();
  }

/*
The mouseDragged method is called repeatedly and updates us on the location of a MouseEvent, e,
getX and getY are used to report the coordinates of the mouse which are saved in messageX and messageY
Then, repaint() method is scheduled so that the paintComponent() method re-draws the message string at its new location
*/

  public void mouseMoved(MouseEvent e ) { }

  public void actionPerformed( ActionEvent e ) {
    // Did somebody push our button?
    if ( e.getSource() == theButton )
      changeColor();
  }

/*
When the JButton is clicked, an ActionEvent is called through the actionPerformed() method
The source of the event is then checked to make sure it matches (==) our theButton
If so, the color changes via the changeColor() method
*/

  synchronized private void changeColor() {
    if (++ colorIndex == someColors .length )
      colorIndex = 0;
    setForeground( currentColor() );
    repaint();
  }

  synchronized private Color currentColor(  ) {
    return someColors[colorIndex ];
  }

  public void run(  ) { // the run() method. An infinite while loop
    try {
      while( true) {
        blinkState = !blinkState ; // Toggles blinkState to its opposite value using not operator (!)
        repaint(); // Redraws the text
        Thread. sleep(300); // Sleeps for a third of a second - 3 blinks per sec .
      }
    } catch (InterruptedException ie) { } // try/catch construct traps errors and ignores them

/*
The synchronized modifer tells Java to acquire a lock for the object that contains the method before executing that method.
In this instance, paintComponent() could be called when mouseDragged() is updating and could get the new x value and an old y value
synchronised means that these two methods can NOT be running at the same time
They are both private, or encapsulated, so that they cannot be called outside of the containing object
The method changeColor() increments the colorIndex variable when the button is pressed, selecting a new current color
Then, setForeground() method changes the text color
Then, repaint() method redraws the draggable message in that selected color
*/

  }
}
