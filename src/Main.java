import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main extends Application

{
    public static final DecimalFormat decimalFormatWINS = new DecimalFormat( "#" ); // Decimal Format used to remove decimals from Win Count

    public static void main(String[] args)
    {
        launch(args);
    } // Launches Code

    public void start(Stage theStage)
    {
        theStage.setTitle( "Jump... Dive... Kick!" ); // Sets Window Title

        Group root = new Group();   // Sets JavaFX scene, as well as the root, from which objects will be added.
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Image bg = new Image(("Background_1.gif")); // Sets Background_1.gif to background and adds the Background to the root
        ImageView background = new ImageView();
        background.setImage(bg);
        root.getChildren().add(background);

        Rectangle nameplateA = new Rectangle(); // Initiates the nameplate boxes behind the "Wins" text and adds the nameplates to the root
        Rectangle nameplateB = new Rectangle();
        root.getChildren().add(nameplateA);
        root.getChildren().add(nameplateB);

        Canvas canvas = new Canvas( 800, 492); // Initiates the nameplate boxes behind the "Wins" text and adds the nameplates to the root.
        root.getChildren().add( canvas );

        ArrayList<String> input = new ArrayList<String>(); // The array holds the key input of the user

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString(); //Converts Key Presses (i.e. pressing the E key will trigger input to contain "E")
                        if ( !input.contains(code) ) //If the array does not contain KeyEvent Code, add the input code
                            input.add( code );
                    }
                });

        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        input.remove( code ); //When the key is released, remove the input code from the array
                    }
                });

        Sprite PlayerA = new Sprite(); //Initiates a Sprite() class to "PlayerA", and sets into to its default location.
        PlayerA.ID(1); //Sets the Player ID, which handles which images are associated with which Player
        PlayerA.setPosition(80, 300);

        PlayerA.facingRight = true; // The first player faces right so this is set to true; This variable helps with DiveKicks and JumpBacks

        Sprite PlayerB = new Sprite(); //Initiates a Sprite() class to "PlayerB", and sets into to its default location.
        PlayerB.setPosition(640, 300);
        PlayerB.ID(2);
        PlayerB.facingRight = false; // The second player faces left so this is set to false; This variable helps with DiveKicks and JumpBacks

        GraphicsContext gameArea = canvas.getGraphicsContext2D(); // Uses a GraphicsContext to update and render within the canvas

        LongValue lastNanoTime = new LongValue( System.nanoTime() ); //Used to identify how much time has passed since the last update

        new AnimationTimer() //Begins an animation timer, which allows for actions to be reflected within a "game time"
        {
            public void handle(long currentNanoTime)  { //code within the handle called for every frame that the Animation Timer is active

                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0; //When updating, use the time that has elapsed since the last update
                lastNanoTime.value = currentNanoTime; //Once the update has occurred, lastNanoTime is changed to the currentNanoTime
            // while(PlayerA.Win <= 20 && PlayerB.Win <= 20 )

                if (input.contains("D")) // Input code reads key presses. If the key "D" is pressed, this if is true
                {
                    if (PlayerA.positionY < 300  &&  !PlayerA.jumpBackAnimation && !input.contains("W")) // If player is at least 80 pixels above the ground, not jumping back, and not holding the W Key...
                    {
                        PlayerA.diveKick = true; // Then set DiveKick = true; check for Divekick code in Sprie.java's update function
                    }
                }

                if (input.contains("A"))
                {
                    if (PlayerA.positionY == 300 && PlayerA.jump == false) //If the player is not jumping, and on the ground
                    {
                        PlayerA.jumpBackMotion = true; // Set both jumpBackMotion (which handles the Motion of the Sprite)
                        PlayerA.jumpBackAnimation = true; // and jumpBackAnimation as true; check for code in the update function
                    }

                    if (PlayerA.positionY < 300 && PlayerA.jumpBackMotion == true) //Once the player leaves the ground because of the Jumpback, JumpBackMotion is set to false so players don't fly off
                    {
                        PlayerA.jumpBackMotion = false;
                    }
                }
                if (input.contains("W") && PlayerA.positionY >= 250 && !input.contains("D")) {
                        PlayerA.jump = true;
                }

                if (input.contains("Y")) // Enables Mario
                		{
                	PlayerA.ID(3);
                		}

                if (PlayerA.diveKick == true)
                {
                    if (PlayerB.positionX < PlayerA.footpositionX && PlayerA.footpositionX < (PlayerB.width + PlayerB.positionX)
                            && PlayerB.positionY < PlayerA.footpositionY && PlayerA.footpositionY < (PlayerB.height + PlayerB.positionY)) {

                        PlayerA.Win += 1;

                        PlayerA.setPosition(80, 300);
                        PlayerB.setPosition(640, 300);
                    }
                }
                
                PlayerA.update(elapsedTime); // Uses position and time to update the player and their velocity

                if (input.contains("LEFT"))
                {
                    if (PlayerB.positionY < 300  && PlayerB.jumpBackAnimation== false && !input.contains("UP"))
                    {
                        PlayerB.diveKick = true;
                    }
                }

                if (input.contains("RIGHT"))
                {
                    if (PlayerB.positionY == 300 && PlayerB.jump == false)
                    {
                        PlayerB.jumpBackMotion = true;
                        PlayerB.jumpBackAnimation = true;
                    }

                    if (PlayerB.positionY < 300 && PlayerB.jumpBackMotion == true)
                    {
                        PlayerB.jumpBackMotion = false;
                    }
                }
                if (input.contains("UP") && PlayerB.positionY >= 250) {
                    PlayerB.jump = true;
                }

                if (PlayerB.diveKick == true)
                {
                    if (PlayerA.positionX < PlayerB.positionX && PlayerB.positionX < (PlayerA.width + PlayerA.positionX)
                            && PlayerA.positionY < (PlayerB.positionY + PlayerB.height) && (PlayerB.positionY + PlayerB.height) < (PlayerA.height + PlayerA.positionY))
                    {
                        PlayerB.Win += 1;
                        PlayerA.setPosition(80, 300);
                        PlayerB.setPosition(640, 300);
                    }
                }

                PlayerB.update(elapsedTime); // Uses position and time to update the player and their velocity

                gameArea.clearRect(0, 0, 800,492); //Renders a clear rectangle above which our sprites will be rendered
                PlayerA.render( gameArea ); //Renders our sprites within their relative positions
                PlayerB.render( gameArea );

                gameArea.setFont(Font.font ("Arial", 20));
                gameArea.setFill(Color.WHITE);

                nameplateB.setX(550);
                nameplateB.setY(20);
                nameplateB.setWidth(225);
                nameplateB.setHeight(25);
                nameplateB.setFill(Color.rgb(247, 173, 0));
                String PlayerBWins = "YELLOWBOT:  " + (decimalFormatWINS.format(PlayerB.Win));
                String WinsB = "WINS";
                gameArea.fillText( WinsB, 720, 40 );
                gameArea.fillText( PlayerBWins, 552, 40 );

                if (PlayerA.secretPlayer == true) {
                    nameplateA.setX(25);
                    nameplateA.setY(20);
                    nameplateA.setWidth(165);
                    nameplateA.setHeight(25);
                    nameplateA.setFill(Color.rgb(177, 52, 37));
                    String PlayerAWins = "MARIO:  " + (decimalFormatWINS.format(PlayerA.Win));
                    String WinsA = "WINS";
                    gameArea.fillText( PlayerAWins, 27, 40 );
                    gameArea.fillText( WinsA, 135, 40 );
                }
                else {
                    nameplateA.setX(25);
                    nameplateA.setY(20);
                    nameplateA.setWidth(195);
                    nameplateA.setHeight(25);
                    nameplateA.setFill(Color.rgb(156, 148, 231));
                    String PlayerAWins = "BLUEBOT:  " + (decimalFormatWINS.format(PlayerA.Win));
                    String WinsA = "WINS";
                    gameArea.fillText( WinsA, 165, 40 );
                    gameArea.fillText( PlayerAWins, 27, 40 );
                }
            }
        }.start(); // Begins execution

        theStage.show(); //Shows the stage, which shows the root, which shows the canvas and background.
    }
}