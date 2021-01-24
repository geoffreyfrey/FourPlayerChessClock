package com.geoffreyfrey.fourplayerchessclock;

/**
 * Created by geoff on 3/24/18.
 */

public class Player {

    //Declaring Variables
    private int _id;
    private long _delayTime;
    private long _gameTime;
    private int _playerScore;
    private int _timerTextViewID;
    private int _playerBackgroundViewID;
    private String _playerName;
    private long _playerStartDelayTime;
    private long _playerStartGameTime;
    private boolean _isActive;


    //Constructors
    public Player(int id) { this(id,15,60,0,0,0,"Player " + String.valueOf(id+1),15,60,true);}

    //Definitions
    public Player(int id, long delayTime, long gameTime, int playerScore, int timerTextViewID,int playerBackgroundViewID, String playerName,long playerStartDelayTime,long playerStartGameTime, boolean isActive){
        //Assigning variables
        this._id=id;
        this._delayTime=delayTime;
        this._gameTime=gameTime;
        this._playerScore=playerScore;
        this._timerTextViewID=timerTextViewID;
        this._playerBackgroundViewID=playerBackgroundViewID;
        this._playerName=playerName;
        this._playerStartDelayTime=playerStartDelayTime;
        this._playerStartGameTime=playerStartGameTime;
        this._isActive=isActive;
    }

    //Methods


    //Getter and Setter Methods


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public long get_delayTime() {
        return _delayTime;
    }

    public void set_delayTime(long _delayTime) {
        this._delayTime = _delayTime;
    }

    public long get_gameTime() {
        return _gameTime;
    }

    public void set_gameTime(long _gameTime) {
        this._gameTime = _gameTime;
    }

    public int get_playerScore() {
        return _playerScore;
    }

    public void set_playerScore(int _playerScore) {
        this._playerScore = _playerScore;
    }

    public String get_playerName() {
        return _playerName;
    }

    public void set_playerName(String _playerName){
        this._playerName=_playerName;
    }

    public boolean is_isActive() {
        return _isActive;
    }

    public void set_isActive(boolean _isActive) {
        this._isActive = _isActive;
    }

    public int get_timerTextViewID() {
        return _timerTextViewID;
    }

    public void set_timerTextViewID(int _timerTextViewID) {
        this._timerTextViewID = _timerTextViewID;
    }

    public long get_playerStartDelayTime() {
        return _playerStartDelayTime;
    }

    public void set_playerStartDelayTime(long _playerStartDelayTime) {
        this._playerStartDelayTime = _playerStartDelayTime;
    }

    public long get_playerStartGameTime() {
        return _playerStartGameTime;
    }

    public void set_playerStartGameTime(long _playerStartGameTime) {
        this._playerStartGameTime = _playerStartGameTime;
    }

    public int get_playerBackgroundViewID() {
        return _playerBackgroundViewID;
    }

    public void set_playerBackgroundViewID(int _playerBackgroundViewID) {
        this._playerBackgroundViewID = _playerBackgroundViewID;
    }
}
