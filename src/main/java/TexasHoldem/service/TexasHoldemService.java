package TexasHoldem.service;

import TexasHoldem.common.Exceptions.*;
import TexasHoldem.common.SystemUtils;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.GamePolicy;
import TexasHoldem.domain.game.GameSettings;
import TexasHoldem.domain.system.GameCenter;
import TexasHoldem.domain.user.User;
import org.apache.commons.lang3.NotImplementedException;

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
        verifyStrings(username, pass, email);
        verifyObjects(date);

        gameCenter.registerUser(username, pass, email, date, img);
    }

    public void deleteUser(String username) throws EntityDoesNotExistsException, InvalidArgumentException {
        verifyStrings(username);
        gameCenter.deleteUser(username);
    }

    public void login(String username,String pass) throws LoginException, InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(username);
        gameCenter.login(username, pass);
    }

    public void logout(String username) throws InvalidArgumentException {
        verifyStrings(username);
        gameCenter.logout(username);
    }

    public void editProfile(String originalUserName,String newUserName, String pass,String email, LocalDate date) throws InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(originalUserName, newUserName, pass, email);
        verifyObjects(date);
        gameCenter.editProfile(originalUserName, newUserName, pass, email, date);
    }

    public void deposit(String username, int amount) throws ArgumentNotInBoundsException, InvalidArgumentException {
        verifyStrings(username);
        verifyPositiveNumbers(amount);
        gameCenter.depositMoney(username, amount);
    }

    public User getUser(String username) throws InvalidArgumentException {
        verifyStrings(username);
        return gameCenter.getUser(username);
    }

    public void createGame(String creatorUsername, String gameName, GamePolicy policy, int limit, int minBet, int buyInPolicy, int chipPolicy,
                           int minPlayerAmount, int maxPlayerAmount, boolean specAccept) throws NoBalanceForBuyInException, InvalidArgumentException, ArgumentNotInBoundsException {
        verifyStrings(creatorUsername, gameName);
        verifyPositiveNumbers(limit, minBet, buyInPolicy, chipPolicy, minPlayerAmount, maxPlayerAmount);
        gameCenter.createGame(creatorUsername, new GameSettings(gameName, policy, limit, minBet, buyInPolicy, chipPolicy, minPlayerAmount, maxPlayerAmount, specAccept));
    }

    public void joinGame(String username, String gameName, boolean asSpectator) throws GameIsFullException, InvalidArgumentException, LeaguesDontMatchException, CantSpeactateThisRoomException, NoBalanceForBuyInException {
        verifyStrings(username, gameName);
        gameCenter.joinGame(username, gameName, false);
    }

    public void spectateGame(String username, String gameName, boolean asSpectator) throws GameIsFullException, InvalidArgumentException, LeaguesDontMatchException, CantSpeactateThisRoomException, NoBalanceForBuyInException {
        verifyStrings(username, gameName);
        gameCenter.joinGame(username, gameName, true);
    }

    public void leaveGame(String username, String gameName) throws GameException {
        verifyStrings(username, gameName);
        gameCenter.leaveGame(username, gameName);
    }

    public void replayGame(){
        throw new NotImplementedException("");
    }

    public void saveTurns(){
        throw new NotImplementedException("");
    }

    public List<Game> findAvailableGames(String username) throws InvalidArgumentException {
        verifyStrings(username);
        return gameCenter.findAvailableGames(username);
    }

    public List<Game> findSpectatableGames(){
        return gameCenter.findSpectateableGames();
    }

    public List<Game> findGamesByUsername(String username) throws InvalidArgumentException {
        verifyStrings(username);
        return gameCenter.findGamesByUsername(username);
    }

    public List<Game> findGamesByPotSize(int potSize){
        return gameCenter.findGamesByPotSize(potSize);
    }

    public List<Game> findGamesByGamePolicy(GamePolicy policy) throws InvalidArgumentException {
        verifyObjects(policy);
        return gameCenter.findGamesByGamePolicy(policy);
    }

    public List<Game> findGamesByMaximumBuyIn(int maximumBuyIn){
        return gameCenter.findGamesByMaximumBuyIn(maximumBuyIn);
    }

    public List<Game> findGamesByChipPolicy(int chipPolicy){
        return gameCenter.findGamesByChipPolicy(chipPolicy);
    }

    public List<Game> findGamesByMinimumBet(int minimumBet){
        return gameCenter.findGamesByMinimumBet(minimumBet);
    }

    public List<Game> findGamesByMinimumPlayers(int minimumPlayers){
        return gameCenter.findGamesByMinimumPlayers(minimumPlayers);
    }

    public List<Game> findGamesByMaximumPlayers(int maximumPlayers){
        return gameCenter.findGamesByMaximumPlayers(maximumPlayers);
    }

    public void setDefaultLeague(String admin, int league) throws NoPermissionException {
        gameCenter.setDefaultLeague(admin, league);
    }

    public void setUserLeague(String admin, String username, int league) throws NoPermissionException {
        gameCenter.setUserLeague(admin, username, league);
    }

    public void setLeagueCriteria(String username, int league) throws NoPermissionException {
        gameCenter.setLeagueCriteria(username, league);
    }


    private void verifyStrings(String ... strings) throws InvalidArgumentException {
        if(SystemUtils.hasNullOrEmptyOrSpecialChars(strings))
            throw new InvalidArgumentException("Null/Empty fields or invalid characters are not allowed.");
    }

    private void verifyObjects(Object ... objects) throws InvalidArgumentException{
        for(Object o : objects){
            if(o == null)
                throw new InvalidArgumentException("Null fields are not allowed.");
        }
    }

    private void verifyPositiveNumbers(int ... ints) throws InvalidArgumentException{
        if(!SystemUtils.arePositive(ints))
            throw new InvalidArgumentException("Numeric values must be positive.");
    }
}
