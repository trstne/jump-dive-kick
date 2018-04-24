import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

import java.awt.image.RescaleOp;

import javafx.geometry.Rectangle2D;

public class Sprite
{
    private Image image;

    public double positionX;
    public double positionY;

    public double footpositionX;
    public double footpositionY;

    private double velocityX;
    private double velocityY;

    private double gravity = 30;

    public double width;
    public double height;

    public boolean diveKick;
    public boolean jumpBackMotion;
    public boolean jumpBackAnimation;
    public boolean jump;

    public boolean secretPlayer= false;

    public String Player_backJumpImage;
    public String Player_JumpImage;
    public String Player_idleImage;
    public String Player_diveKickImage;

    public boolean facingRight = false;

    public double Win = 0;



    public Sprite() // Holds the position of the sprite, to be accessed by the program
    {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;

        String Player_diveKickImage;
    }

    public void setFile(String filename) // Initiates "i" to an image, Initiates width and height to that image's width and height (for hitbox)
    {
        String Player_backJumpImage;
        String Player_JumpImage;
        String Player_idleImage;
        String Player_diveKickImage;
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

        footpositionX = positionX + width;
        footpositionY = positionY + height;

        if (positionY >= 300) { //Makes Y = 300 "the Ground".
            setVelocity(0, 0);
            setPositionY(300);
            setImage(Player_idleImage);
        }

        if (diveKick == true)
        {
            setImage(Player_diveKickImage);
            setVelocityY(0);
            addVelocity(0, 500);

            if (facingRight) {
                setVelocityX(500);
            }

            if (!facingRight){
                setVelocityX(-500);
            }
        }

        if (jumpBackMotion == true)
        {
            if (positionY == 300)
            {
                if (facingRight) {
                    addVelocity(-400, -300);
                }
                if (!facingRight) {
                    addVelocity(400, -300);
                }
            }
        }

        if (jumpBackAnimation == true)
        {
            setImage(Player_backJumpImage);
        }

        if (jump == true)
        {
            if (positionY >= 250 && diveKick == false && jumpBackAnimation == false) {
                addVelocity(0, -150);
                setImage(Player_JumpImage);
            }

            else
            {
                jump = false;
            }
        }

        if (positionY == 300){
            jumpBackAnimation = false;
            jumpBackMotion = false;
            diveKick = false;
        }


        if (positionX < -30) // Left Boundary Code
        {
            setPositionX(830);
        }

        if (positionX > 830) // Right Boundary Code
        {
            setPositionX(-30);
        }
    }

    public void render(GraphicsContext gc) // Tells the canvas what and where to draw an image
    {
        gc.drawImage( image, positionX, positionY );
    }



    public String toString() //Returns a textual representation of these springs as "[# , #}", as opposed to hash code.
    {
        return " Position: [" + positionX + "," + positionY + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }
}