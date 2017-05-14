package Client.communication.entities;

/**
 * Created by user on 12/05/2017.
 */
public class ClientUserProfile {
    private String username;
    private String password;
    private String email;
    private int dayOfBirth;
    private int monthOfBirth;
    private int yearOfBirth;
    private int balance;
    private int league;
    private int gamesPlayed;
    private int earnedInLeague;

    public ClientUserProfile(String username, String password, String email, int dayOfBirth, int monthOfBirth, int yearOfBirth,int balance,int league,int gamesPlayed,int earnedInLeague) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dayOfBirth = dayOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.yearOfBirth = yearOfBirth;
        this.balance = balance;
        this.earnedInLeague = earnedInLeague;
        this.league = league;
        this.gamesPlayed = gamesPlayed;
    }

    public ClientUserProfile() {

    }
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getLeague() {
        return league;
    }

    public void setLeague(int league) {
        this.league = league;
    }

    public int getNumOfGamesPlayed() {
        return gamesPlayed;
    }

    public void setNumOfGamesPlayed(int numofgamesplayed) {
        this.gamesPlayed = numofgamesplayed;
    }

    public int getAmountEarnedInLeague() {
        return earnedInLeague;
    }

    public void setAmountEarnedInLeague(int amountearnedinleague) {
        this.earnedInLeague = amountearnedinleague;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(int dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public int getMonthOfBirth() {
        return monthOfBirth;
    }

    public void setMonthOfBirth(int monthOfBirth) {
        this.monthOfBirth = monthOfBirth;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
