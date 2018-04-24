import javafx.application.Application;
import javafx.scene.paint.Color;
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

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


import java.text.DecimalFormat;
import java.util.ArrayList;


public class Main extends Application


{
	public boolean debugMenu = false;
    public static final DecimalFormat df1 = new DecimalFormat( "#" );

    public static void main(String[] args)
    {
        launch(args);
    } //launches program

    @Override
    public void start(Stage theStage)
    {
        theStage.setTitle( "Jump... Dive... Kick!" );

        Image bg = new Image(("Background_1.gif"));
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

        Sprite PlayerA = new Sprite(); //Initiates a Sprite() class to "PlayerA"
        PlayerA.setPosition(80, 300); //Sets the position of the Sprite

        PlayerA.facingRight = true;

        PlayerA.Player_diveKickImage = "PlayerA_DIVEKICK.png";
        PlayerA.Player_backJumpImage = "PlayerA_BACKJUMP.png";
        PlayerA.Player_JumpImage = "PlayerA_JUMP.png";
        PlayerA.Player_idleImage = "PlayerA_IDLE.png";

        Sprite PlayerB = new Sprite();
        PlayerB.setPosition(640, 300);

        PlayerB.facingRight = false;

        PlayerB.Player_diveKickImage = "PlayerB_DIVEKICK.png";
        PlayerB.Player_backJumpImage = "PlayerB_BACKJUMP.png";
        PlayerB.Player_JumpImage = "PlayerB_JUMP.png";
        PlayerB.Player_idleImage = "PlayerB_IDLE.png";

        GraphicsContext gc = canvas.getGraphicsContext2D(); // Uses a GraphicsContext to update and render within the canvas


        LongValue lastNanoTime = new LongValue( System.nanoTime() ); //Used to identify how much time has passed since the last update

        new AnimationTimer()
        {
            public void handle(long currentNanoTime) {

                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0; //When updating, uses the time that has elapsed sine the last update
                lastNanoTime.value = currentNanoTime; //Once the update has occurred, the timer resets to 0.
            // while(PlayerA.Win <= 20 && PlayerB.Win <= 20 )

                if (input.contains("D"))
                {
                    if (PlayerA.positionY < 200  && (boolean) PlayerA.jumpBackAnimation== false)
                    {
                        PlayerA.diveKick = true;
                    }
                }

                if (input.contains("A"))
                {
                    if (PlayerA.positionY == 300 && PlayerA.jump == false) {
                        PlayerA.jumpBackMotion = true;
                        PlayerA.jumpBackAnimation = true;
                    }

                    if (PlayerA.positionY < 300 && PlayerA.jumpBackMotion == true)
                    {
                        PlayerA.jumpBackMotion = false;
                    }
                }
                if (input.contains("W") && PlayerA.positionY >= 250) {

                        PlayerA.jump = true;
                }

                if (input.contains("Y"))
                		{
                	PlayerA.secretPlayer = true;
                	PlayerA.Player_diveKickImage = "SecretA_DIVEKICK.png";
                	PlayerA.Player_backJumpImage = "SecretA_BACKJUMP.png";
                	PlayerA.Player_JumpImage = "SecretA_JUMP.png";
                	PlayerA.Player_idleImage = "SecretA_IDLE.png";
                	debugMenu = true;
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
                    if (PlayerB.positionY < 200  && PlayerB.jumpBackAnimation== false)
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
                            && PlayerA.positionY < (PlayerB.positionY + PlayerB.height) && (PlayerB.positionY + PlayerB.height) < (PlayerA.height + PlayerA.positionY)) {

                        PlayerB.Win += 1;
                        PlayerA.setPosition(80, 300);
                        PlayerB.setPosition(640, 300);
                    }
                }

                PlayerB.update(elapsedTime); // Uses position and time to update the player and their velocity

                gc.clearRect(0, 0, 800,492); //Renders a clear rectangle above which our sprites will be rendered
                PlayerA.render( gc ); //Renders our sprites within their relative positions
                PlayerB.render( gc );
                
                gc.setFont(Font.font ("Arial", 20));
                String PlayerAWins = "Player A Wins: " + PlayerA.Win;
                gc.fillText( PlayerAWins, 300, 36 );
                

                String PlayerBWins= "Player B Wins: " + PlayerB.Win;
                gc.fillText( PlayerBWins, 300, 56 );
                
                
                if (debugMenu == true) {

                    gc.setFill(Color.RED);

                    //String PlayerAPosition = "Player A Position: (" + (df1.format(PlayerA.positionX)) + "," + (df1.format(PlayerA.positionY)) + ")";
                   // String PlayerBPosition = "Player B Position: (" + (df1.format(PlayerB.positionX)) + "," + (df1.format(PlayerB.positionY)) + ")";
                    String InputString = "Keys Pressed " + input;
                    gc.fillText( InputString, 300, 80 );

                    String DebugMode = "DEVELOPER MODE";
                    gc.fillText(DebugMode, 0, 20);
                }

               
            }
        }.start(); // Begins execution

        theStage.show(); //Shows the stage, which shows the root, which shows the canvas and background.
    }
}