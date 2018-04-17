import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;


public class Main extends Application


{
    //public static final DecimalFormat df1 = new DecimalFormat( "#" );

    public static void main(String[] args)
    {
        launch(args); //launches program
    }

    @Override
    public void start(Stage theStage)
    {
        theStage.setTitle( "Jump... Dive... Kick!" );

        Image bg = new Image(("bg1.gif"));
        ImageView background = new ImageView();
        background.setImage(bg);

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        HBox box = new HBox();
        box.getChildren().add(background);
        root.getChildren().add(box);

        Canvas canvas = new Canvas( 800, 492);
        root.getChildren().add( canvas );

        ArrayList<String> input = new ArrayList<String>(); //The array holds the key input of the user

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

        GraphicsContext gc = canvas.getGraphicsContext2D(); // Uses a GraphicsContext to update and render within the canvas


        Sprite PlayerA = new Sprite(); //Initaites a Sprite() class to "PlayerA"
        PlayerA.setImage("101.png");    //Sets the Image of the Spite()
        PlayerA.setPosition(80, 300); //Sets the position of the Sprite

        Sprite PlayerB = new Sprite();
        PlayerB.setImage("201.png");
        PlayerB.setPosition(640, 300);


        LongValue lastNanoTime = new LongValue( System.nanoTime() ); //Used to identify how much time has passed since the last update
        //LongValue intialNanoTime = new LongValue( System.nanoTime() );

        new AnimationTimer()
        {
            public void handle(long currentNanoTime) {

                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0; //When updating, uses the time that has elapsed sine the last update
                lastNanoTime.value = currentNanoTime; //Once the update has occured, the timer resets to 0.

                if (PlayerA.positionY >= 300) { //Makes Y = 300 "the Ground". Player A cannot goto a higher Y value, as he will be transported back.
                    PlayerA.setVelocity(0, 0);
                    PlayerA.setPositionY(300);
                }

                if (input.contains("D"))
                {
                    if (PlayerA.positionY < 300)
                    {
                        PlayerA.setVelocityX(500);
                        PlayerA.setImage("102.png");
                        PlayerA.diveKick = true;
                    }
                }

                if (input.contains("A"))
                {
                    if (PlayerA.positionY >= 265)
                    {
                        PlayerA.jumpBack = true;

                        if (PlayerA.positionY == 300)
                        {
                            PlayerA.addVelocity(-400, -300);
                        }
                    }
                }
                if (input.contains("W") && (boolean) PlayerA.diveKick == false && (boolean) PlayerA.jumpBack== false) {
                    if (PlayerA.positionY >= 250) {
                        PlayerA.addVelocity(0, -150);
                        PlayerA.setImage("104.png");
                    }
                }

                if (PlayerA.jumpBack == true)
                {
                    PlayerA.setImage("103.png");
                }

                if (PlayerA.positionY <= 0) {
                    PlayerA.setVelocityY(50);
                }

                if (PlayerA.positionY == 300)
                {
                    PlayerA.jumpBack = false;
                    PlayerA.diveKick = false;
                    PlayerA.setImage("101.png");
                }

                if (PlayerA.positionX < -30) // Left Boundary Code
                {
                    PlayerA.setPositionX(830);

                }

                if (PlayerA.positionX > 830) // Right Boundary Code
                {
                    PlayerA.setPositionX(-30);

                }


                PlayerA.update(elapsedTime); // Uses position and time to update the player and their velocity

                if (PlayerB.positionY >= 300) { //Makes Y = 300 "the Ground". Player A cannot goto a higher Y value, as he will be transported back.
                    PlayerB.setVelocity(0, 0);
                    PlayerB.setPositionY(300);
                }

                if (input.contains("LEFT"))
                {
                    if (PlayerB.positionY < 300)
                    {
                        PlayerB.setVelocityX(-500);
                        PlayerB.setImage("202.png");
                        PlayerB.diveKick = true;
                    }
                }

                if (input.contains("RIGHT"))
                {
                    if (PlayerB.positionY >= 265)
                    {
                        PlayerB.jumpBack = true;

                        if (PlayerB.positionY == 300)
                        {
                            PlayerB.addVelocity(400, -300);
                        }
                    }
                }
                if (input.contains("UP") && (boolean) PlayerB.diveKick == false && (boolean) PlayerB.jumpBack== false) {
                    if (PlayerB.positionY >= 250) {
                        PlayerB.addVelocity(0, -150);
                        PlayerB.setImage("204.png");
                    }
                }

                if (PlayerB.jumpBack == true)
                {
                    PlayerB.setImage("203.png");
                }

                if (PlayerB.positionY <= 0) {
                    PlayerB.setVelocityY(50);
                }

                if (PlayerB.positionY == 300)
                {
                    PlayerB.jumpBack = false;
                    PlayerB.diveKick = false;
                    PlayerB.setImage("201.png");
                }

                if (PlayerB.positionX < -30) // Left Boundary Code
                {
                    PlayerB.setPositionX(830);

                }

                if (PlayerB.positionX > 830) // Right Boundary Code
                {
                    PlayerB.setPositionX(-30);

                }


                PlayerB.update(elapsedTime); // Uses position and time to update the player and their velocity

                // collision detection

                if ( PlayerA.intersects(PlayerB) )
                {

                    if ((PlayerA.positionY > PlayerB.positionY) && ((boolean) PlayerA.diveKick == true)) {
                        PlayerA.Win += 1;
                        PlayerA.setPosition(80, 300);
                        PlayerB.setPosition(640, 300);
                    }

                    if ((PlayerB.positionY > PlayerA.positionY) && ((boolean) PlayerB.diveKick == true)) {
                        PlayerB.Win += 1;
                        PlayerA.setPosition(80, 300);
                        PlayerB.setPosition(640, 300);
                    }
                }

              /*  if ( PlayerB.intersects(PlayerA) )
                {
                    PlayerA.update(elapsedTime);
                    PlayerB.update(elapsedTime);

                    if ((PlayerB.positionY > PlayerA.positionY)) {
                        PlayerB.Win += 1;
                        PlayerA.setPosition(80, 300);
                        PlayerB.setPosition(640, 300);
                    }
                } */





                gc.clearRect(0, 0, 800,492); //Renders a clear rectangle above which our sprites will be rendered
                PlayerA.render( gc ); //Renders our sprites within their relative postions
                PlayerB.render( gc );


                //double countDownTime =  ((intialNanoTime.value - currentNanoTime) + 100000000000.0 ) / 1000000000.0;
                String PlayerAPos = "Player A Wins: " + PlayerA.Win;
                gc.fillText( PlayerAPos, 360, 36 );
                gc.strokeText( PlayerAPos, 360, 36 );

                String PlayerBPos= "Player B Wins: " + PlayerB.Win;
                gc.fillText( PlayerBPos, 360, 50 );
                gc.strokeText( PlayerBPos, 360, 50 );
            }
        }.start(); // Begins execution

        theStage.show(); //Shows the stage, which shows the root, which shows the canvas and background.
    }
}