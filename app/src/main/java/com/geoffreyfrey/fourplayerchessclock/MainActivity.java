package com.geoffreyfrey.fourplayerchessclock;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

//Variable Declarations
    //Setting up environment variables
    TextView delayTimerText;
    TextView originalDelayTimerText;
    private boolean isActive = false;
    private boolean isTouch = false;
    private boolean isGameOver = false;
    public static int numberPickerFactor = 5;
    private boolean isGameTimeLast = false;
    private boolean isPaused = false;
    //public String[] colorsOne = new String[10];
    //public String[] colorsTwo = new String[10];
    //public String[] colorsThree = new String[10];
    //public String[] colorsFour = new String[10];

    //Setting up the default values
    int playerQuantity;
    long tempLong;                              //Temporary Long for storing values when changing defaults.
    long defaultGameTime;
    long defaultDelayTime;
    int remainingPlayerQuantity;

    //Setting up the variables for to hold the current player values
    int currentPlayerID;
    int nextPlayerID;

    //Initializing more variables
    long gameTime;
    long delayTime;
    int score;
    int percentDoneLast;
    ObjectAnimator anim;

    //SettingUpThePlayers
    Player[] players = new Player[4];           //Array of the players made of player classes
    Player[] prevPlayers = new Player [4];      //Array to keep track of the score from a previous game.
    int[] timers = new int[4];                  //Returns the view id of the timer text boxes
    int[] backgrounds = new int[4];             //Returns the background id of the player boxes

    //Timer for the delay timer
    CountDownTimer delayTimer;
    CountDownTimer playerTimer;
    CountDownTimer pressTimer;

    //Player Timer Text Boxes
    TextView playerOneTimer;
    TextView playerTwoTimer;
    TextView playerThreeTimer;
    TextView playerFourTimer;

    //Player Background Boxes
    View playerOneBackground;
    View playerTwoBackground;
    View playerThreeBackground;
    View playerFourBackground;

    //Setting Up Color Variables
    //red - {"#B71C1C","#C62828","#D32F2F","#E53935","#F44336","#EF5350","#E57373","#EF9A9A","#FFCDD2","#E0E0E0"};
    //Indigo - {"#1A237E","#283593","#303F9F","#3949AB","#3F51B5","#5C6BC0","#7986CB","#9FA8DA","#C5CAE9","#E0E0E0"};
    //blue - {"#0D47A1","#1565C0","#1976D2","#1E88E5","#2196F3","#42A5F5","#64B5F6","#90CAF9","#BBDEFB","#E0E0E0"};
    //pink - {"#0D47A1","#1565C0","#1976D2","#1E88E5","#2196F3","#42A5F5","#64B5F6","#90CAF9","#BBDEFB","#E0E0E0"};
    //Deep orange - {"#BF360C","#D84315","#E64A19","#F4511E","#FF5722","#FF7043","#FF8A65","#FFAB91","#FFCCBC","#E0E0E0"};


    String[] colorsOne = new String[]{"#B71C1C","#C62828","#D32F2F","#E53935","#F44336","#EF5350","#E57373","#EF9A9A","#FFCDD2","#E0E0E0"};
    String[] colorsTwo = new String[] {"#0D47A1","#1565C0","#1976D2","#1E88E5","#2196F3","#42A5F5","#64B5F6","#90CAF9","#BBDEFB","#E0E0E0"};
    String[] colorsThree = new String[] {"#004D40","#00695C","#00796B","#00897B","#009688","#26A69A","#4DB6AC","#80CBC4","#B2DFDB","#E0E0E0"};
    String[] colorsFour = new String[] {"#E65100","#EF6C00","#F57C00","#FB8C00","#FF9800","#FFA726","#FFB74D","#FFCC80","#FFE0B2","#E0E0E0"};
    String[][] backgroundColors = new String[][] {colorsOne,colorsTwo,colorsThree,colorsFour};



    /*
    //Variables for the drop-down menu
    EditText input = new EditText(this);
    DialogInterface.OnClickListener inputBox;
    */

//Main Code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Define player time default variables
        defaultDelayTime = 15;
        defaultGameTime = 60;
        playerQuantity=4;

        //Setting All values to default values
        setToDefault();

        //Setting up the delayTimer
        delayTimerText =(TextView)findViewById(R.id.timer);
        //originalDelayTimerText = delayTimerText;





    }

    //calls menu inflater for the toolbar when the toolbar is created.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    //Runs whenever there is a touch event
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                //Toast.makeText(this, "ACTION_DOWN AT COORDS "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                cancelTimers();

                if(isGameOver) {
                    pressTimer = new CountDownTimer(1500,500) {
                        @Override
                        public void onTick(long l) {
                            playerOneTimer.playSoundEffect(SoundEffectConstants.CLICK);
                        }

                        @Override
                        public void onFinish() {
                            setToDefault();
                        }
                    }.start();
                } else {
                    isTouch = true;
                    if(isActive){
                        switchActivePlayer();
                    } else {
                        isActive=true;
                    }
                    if(isPaused){
                        isPaused=false;
                    } else {
                        resetDelayTimer();
                    }
                }

                break;

            case MotionEvent.ACTION_MOVE:
                //Toast.makeText(this, "MOVE "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                break;

            case MotionEvent.ACTION_UP:
                //Toast.makeText(this, "ACTION_UP "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                if(isGameOver){
                    try{
                        pressTimer.cancel();
                    } catch(Exception e) {
                        //Log.d("pressTimer cancel",e.getMessage());
                    }
                }
                break;
        }

        return true;
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        //tempLong=numberPicker.getValue();
        //Toast.makeText(this,"selected number " + numberPicker.getValue(), Toast.LENGTH_SHORT).show();
        //if(isGameTimeLast){
        //    defaultGameTime=numberPicker.getValue()*numberPickerFactor;
        //} else {
        //    defaultDelayTime=numberPicker.getValue()*numberPickerFactor;
        //}
        tempLong=(numberPicker.getValue()*numberPickerFactor-5);
        if(isGameTimeLast){
            defaultGameTime=tempLong;
        } else {
            defaultDelayTime = tempLong;
        }
        cancelTimers();
        setToDefault();
    }

    public void createViews(){
        setContentView(R.layout.activity_main);

        //Sets up the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        TextView toolbarText = (TextView) findViewById(R.id.toolbar_text);
        toolbarText.setText("Game Clock");
        setSupportActionBar(myToolbar);

        //Assigning players their timer TextViewIDs
        setupTimerTextViews();

        delayTimerText =(TextView)findViewById(R.id.timer);

    }

    public void showNumberPicker(){
        NumberPickerDialog newFragment = new NumberPickerDialog();
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), "time picker");
    }



    //region Description
    //*
    //Actions for when things in the toolbar are clicked.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set_total_time:
                isGameTimeLast=true;
                showNumberPicker();
                return true;

            case R.id.set_timer_delay:
                isGameTimeLast=false;
                showNumberPicker();
                return true;

            case R.id.new_game:
                cancelTimers();
                setToDefault();
                return true;

            case R.id.pause:
                cancelTimers();
                isActive=false;
                isPaused=false;
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    //*/
    //endregion


    //Assigning each player their timer TextView ID
    public void setupTimerTextViews() {
        playerOneTimer = (TextView) findViewById(R.id.playerOneTimer);
        playerTwoTimer = (TextView) findViewById(R.id.playerTwoTimer);
        playerThreeTimer = (TextView) findViewById(R.id.playerThreeTimer);
        playerFourTimer = (TextView) findViewById(R.id.playerFourTimer);
        timers[0] = playerOneTimer.getId();
        timers[1] = playerTwoTimer.getId();
        timers[2] = playerThreeTimer.getId();
        timers[3] = playerFourTimer.getId();

        playerOneBackground = (View) findViewById(R.id.playerOneBackground);
        playerTwoBackground = (View) findViewById(R.id.playerTwoBackground);
        playerThreeBackground = (View) findViewById(R.id.playerThreeBackground);
        playerFourBackground = (View) findViewById(R.id.playerFourBackground);
        backgrounds[0] = playerOneBackground.getId();
        backgrounds[1] = playerTwoBackground.getId();
        backgrounds[2] = playerThreeBackground.getId();
        backgrounds[3] = playerFourBackground.getId();

    }

    //Set or Reset all values to default
    public void setToDefault(){
        //Resetting all variables
        createViews();
        delayTime=defaultDelayTime;
        gameTime=defaultGameTime;
        remainingPlayerQuantity=playerQuantity;
        isActive = false;
        isGameOver=false;
        currentPlayerID =0;
        resetPlayers();
        //delayTimerText = originalDelayTimerText;
        //delayTimerText.invalidate();
        //delayTimerText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 56f);
        //delayTimerText.setText("Tap to Start");
        //delayTimerText.setBackgroundColor(Color.parseColor("#E0E0E0"));
        for(int i=0; i<playerQuantity; i++){
            TextView v = (TextView) findViewById(players[i].get_timerTextViewID());
            View b = (View) findViewById((players[i].get_playerBackgroundViewID()));
            v.setPaintFlags(v.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
            v.setText(String.valueOf(players[i].get_gameTime()));
            b.setBackgroundColor(Color.parseColor(backgroundColors[i][0]));
        }
    }

    //Function to set up players
    public void resetPlayers(){
        for (int i =0; i<players.length; i++){
            players[i]= new Player(i, defaultDelayTime,defaultGameTime, 0,timers[i],backgrounds[i], "Player "+String.valueOf(i+1),defaultDelayTime,defaultGameTime,(i<playerQuantity));
        }

    }

    //Function to count the remaining players
    public void countRemainingPlayers(){
        //Resetting variable to 0 for the count.
        remainingPlayerQuantity=0;

        //Counting the remaining players
        for(int i =0; i<players.length; i++){
            if(players[i].is_isActive()){
                Log.d("Player "+String.valueOf(i+1)," is active");
                remainingPlayerQuantity++;
            }
        }

        if(remainingPlayerQuantity==1){
            showEndGame();
        }

        Log.d("Remaining: ",String.valueOf(remainingPlayerQuantity));
    }

    //Function to call main equation
    public void resetDelayTimer(){

        try{
            anim.cancel();
        } catch (Exception e){
            Log.e("No Animation to Cancel","MMMKay");
        }

        try{
            delayTimer.cancel();
        } catch(Exception e) {
            Log.e("delayTimer Exception",String.valueOf(currentPlayerID));
        }
        delayTimerText.clearAnimation();
        percentDoneLast = 0;
        delayTimerText.setBackgroundColor(Color.parseColor(backgroundColors[currentPlayerID][0]));

        delayTimer = new CountDownTimer((long) (players[currentPlayerID].get_delayTime()+1)*1000, 250) {
            public void onTick(long millisUntilFinished) {
                final View b = (View) findViewById(players[currentPlayerID].get_playerBackgroundViewID());
                long psgt = (players[currentPlayerID].get_playerStartDelayTime()*1000);
                //delayTimerText.setText("P"+String.valueOf(currentPlayerID+1)+" - "+String.valueOf(millisUntilFinished / 1000));
                delayTimerText.setText(String.valueOf(millisUntilFinished / 1000));
                int percentDone = (9-((int)(9*((float)millisUntilFinished/(float)psgt))));

                if (percentDone<1){
                    delayTimerText.setBackgroundColor(Color.parseColor(backgroundColors[currentPlayerID][0]));
                } else if (percentDoneLast!=percentDone){
                    anim = ObjectAnimator.ofArgb(delayTimerText,"backgroundColor",Color.parseColor(backgroundColors[currentPlayerID][percentDone-1]),Color.parseColor(backgroundColors[currentPlayerID][percentDone]));
                    long duration = (long)((float)psgt/(float)9);
                    anim.setDuration(duration).start();
                }
                percentDoneLast = percentDone;
            }

            public void onFinish() {
                delayTimerText.setBackgroundColor(Color.parseColor(backgroundColors[currentPlayerID][9]));
                startPlayerGameTimer();
                delayTimerText.setText("");
            }
        }.start();
    }

    //Function to start player game clocks
    public void startPlayerGameTimer(){
        final TextView v = (TextView) findViewById(players[currentPlayerID].get_timerTextViewID());
        final View b = (View) findViewById(players[currentPlayerID].get_playerBackgroundViewID());
        percentDoneLast = 0;

        playerTimer = new CountDownTimer(players[currentPlayerID].get_gameTime()*1000,100) {
            public void onTick(long millisUntilFinished) {
                v.setText(String.format("%.1f",(float) ((float) millisUntilFinished / 1000)));
                players[currentPlayerID].set_gameTime(millisUntilFinished/1000);
                String s = new String("!P"+String.valueOf(currentPlayerID+1)+"!");
                long psgt = (players[currentPlayerID].get_playerStartGameTime()*1000);
                int percentDone = (9-((int)(9*((float)millisUntilFinished/(float)psgt))));
                    for(int i = 0;i<percentDone;i++){
                        s="-"+s+"-";
                    }
                //s=String.valueOf(players[currentPlayerID].get_playerStartGameTime()*1000);
                //delayTimerText.setText(s);

                if (percentDoneLast!=percentDone){
                    ObjectAnimator anim = ObjectAnimator.ofArgb(b,"backgroundColor",Color.parseColor(backgroundColors[currentPlayerID][percentDone-1]),Color.parseColor(backgroundColors[currentPlayerID][percentDone]));
                    long duration = (long)((float)psgt/(float)9);
                    anim.setDuration(duration).start();
                }


                percentDoneLast = percentDone;

            }

            public void onFinish() {
                players[currentPlayerID].set_gameTime(0);
                v.setText(String.valueOf(0));
                disableCurrentPlayer();

            }
        }.start();
    }

    //Disables the current player
    public void disableCurrentPlayer(){
        players[currentPlayerID].set_isActive(false);

        switchActivePlayer();
    }

    //Switches to the next player
    public void switchActivePlayer(){

        countRemainingPlayers();

        try{
            playerTimer.cancel();
        } catch(Exception e) {
            //Log.d("playTimer cancel", e.getMessage());
        }

        if(!isGameOver){
            nextPlayerID=currentPlayerID;
            do{
                if (nextPlayerID == (playerQuantity-1)) {
                    Log.d("Line 263: ","Thinks nextPlayerID == playerQuantity-1"+String.valueOf(nextPlayerID)+"<- Next Player. PQ-1 ->"+String.valueOf(playerQuantity-1));
                    nextPlayerID = 0;
                } else {
                    nextPlayerID = nextPlayerID +1;
                }
            } while (!(players[nextPlayerID].is_isActive()) && (nextPlayerID != currentPlayerID));


            Log.d(String.valueOf(currentPlayerID), "Current Player ID");

            if(nextPlayerID == currentPlayerID){
                isActive=false;
                resetPlayers();
                setupTimerTextViews();
                showEndGame();
            }
            else {
                currentPlayerID = nextPlayerID;
                cancelTimers();
                resetDelayTimer();
            }
        }

    }

    //Cancel Timers if they are running
    public void cancelTimers(){
        try{
            delayTimer.cancel();
        } catch(Exception e) {
            //Log.d("playTimer cancel", e.getMessage());
        }

        try{
            anim.cancel();
        } catch (Exception e){
            Log.e("No Animation to Cancel","MMMKay");
        }
        delayTimerText.setBackgroundColor(Color.parseColor(backgroundColors[currentPlayerID][0]));
        delayTimerText.invalidate();

        try{
            playerTimer.cancel();
        } catch(Exception e) {
            //Log.d("playTimer cancel", e.getMessage());
        }
        try{
            pressTimer.cancel();
        } catch(Exception e) {
            //Log.d("pressTimer cancel",e.getMessage());
        }
    }

    //Initiate End Game
    public void showEndGame(){

        cancelTimers();

        for(int i = 0;i<players.length;i++){
            prevPlayers[i]=players[i];
        }


        delayTimerText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26f);
        delayTimerText.setText("Game Over!\nPress and Hold for New Game");
        isGameOver=true;
    }


}
