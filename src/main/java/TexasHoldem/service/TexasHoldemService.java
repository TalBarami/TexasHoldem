package TexasHoldem.service;

import TexasHoldem.common.Exceptions.*;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.GameSettings;
import TexasHoldem.domain.system.GameCenter;
import TexasHoldem.domain.user.User;

import javax.security.auth.login.LoginException;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Tal on 12/04/2017.
 */
public class TexasHoldemService {
    private GameCenter gameCenter;

    public TexasHoldemService(){
        gameCenter = new GameCenter();
    }

    public void register(String username, String pass, String email, LocalDate date, BufferedImage img) throws InvalidArgumentException {
        gameCenter.registerUser(username, pass, email, date, img);
    }

    public void deleteUser(String username) throws EntityDoesNotExistsException {
        gameCenter.deleteUser(username);
    }

    public void login(String username,String pass) throws LoginException {
        gameCenter.login(username, pass);
    }

    public void logout(String username){
        gameCenter.logout(username);
    }

    public void editProfile(String originalUserName,String newUserName, String pass,String email, LocalDate date) throws InvalidArgumentException {
        gameCenter.editProfile(originalUserName, newUserName, pass, email, date);
    }

    public void deposit(String username, int amount) throws ArgumentNotInBoundsException {
        gameCenter.depositMoney(username, amount);
    }

    public User getUser(String username){
        return gameCenter.getUser(username);
    }

    public void createGame(String creatorUsername, String gameName, GameSettings.GamePolicy policy, int limit, int minBet, int buyInPolicy, int chipPolicy,
                           int minPlyerAmount, int maxPlayerAmount, boolean specAccept) throws NoBalanceForBuyInException, InvalidArgumentException {
        gameCenter.createGame(creatorUsername, new GameSettings(gameName, policy, limit, minBet, buyInPolicy, chipPolicy, minPlyerAmount, maxPlayerAmount, specAccept));
    }

    public void joinGame(String username, String gameName, boolean asSpectator) throws GameIsFullException, InvalidArgumentException, LeaguesDontMatchException, CantSpeactateThisRoomException, NoBalanceForBuyInException {
        gameCenter.joinGame(username, gameName, false);
    }

    public void spectateGame(String username, String gameName, boolean asSpectator) throws GameIsFullException, InvalidArgumentException, LeaguesDontMatchException, CantSpeactateThisRoomException, NoBalanceForBuyInException {
        gameCenter.joinGame(username, gameName, true);
    }

    public void leaveGame(){

    }

    public void replayGame(){

    }

    public void saveTurns(){

    }

    public List<Game> findAvailableGames(String username){
        return gameCenter.findAvailableGames(username);
    }

    public List<Game> findSpectatableGames(){
        return gameCenter.findSpectateableGames();
    }
}
