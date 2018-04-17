import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public class Sprite
{
    private Image image;
    public double positionX;
    public double positionY;
    public double velocityX;
    public double velocityY;
    private double gravity = 35;
    private double width;
    private double height;
    public boolean onGround = false;
    public boolean diveKick;
    public boolean jumpBack = false;

    public double Win = 0;



    public Sprite() // Holds the position of the sprite, to be accessed by the program
    {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
    }

    public void setImage(Image i) // Initiates "i" to an image, Initiates width and height to that image's width and height (for hitbox)
    {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename) // Allows the program to set an image to a new Sprite();
    {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(double x, double y) //Allows the program to set the position of a new Sprite();
    {
        positionX = x;
        positionY = y;
    }

    public void setPositionY(double y) //Allows the program to set the position of a new Sprite();
    {

        positionY = y;
    }

    public void setPositionX(double x) //Allows the program to set the position of a new Sprite();
    {

        positionX = x;
    }


    public void setVelocity(double x, double y) // Allows the program to set the velocity of a new Sprite();
    {
        velocityX = x;
        velocityY = y;
    }

    public void setVelocityX(double x) // Allows the program to set the velocity of a new Sprite();
    {
        velocityX = x;
    }

    public void setVelocityY(double y) // Allows the program to set the velocity of a new Sprite();
    {
        velocityY = y;
    }

    public void addVelocity(double x, double y) // Allows the program to add velocity to a new Sprite();
    {
        velocityX += x;
        velocityY += y;
    }


    public void update (double time)
        {
        positionX += velocityX * time;
        positionY += velocityY * time;
        velocityY += gravity;

        if (positionY == 300){
            onGround = true;
        }


    }

    public void render(GraphicsContext gc) // Tells the canvas what and where to draw an image
    {
        gc.drawImage( image, positionX, positionY );
    }

    public Rectangle2D getBoundary() // Gets the boundaries of an associated image (to be used in hitbox)
    {
        return new Rectangle2D(positionX,positionY,width,height);

    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }

    public String toString() //Returns a textual representation of these springs as "[# , #}", as opposed to hash code.
    {
        return " Position: [" + positionX + "," + positionY + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }
}