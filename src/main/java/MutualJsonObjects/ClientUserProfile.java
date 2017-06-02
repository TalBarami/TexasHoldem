package MutualJsonObjects;

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
    private int currLeague;
    private int numOfGamesPlayed;
    private int amountEarnedInLeague;

    public ClientUserProfile(String username, String password, String email, int dayOfBirth, int monthOfBirth, int yearOfBirth, int balance, int currLeague, int numOfGamesPlayed, int amountEarnedInLeague) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dayOfBirth = dayOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.yearOfBirth = yearOfBirth;
        this.balance = balance;
        this.amountEarnedInLeague = amountEarnedInLeague;
        this.currLeague = currLeague;
        this.numOfGamesPlayed = numOfGamesPlayed;
    }

    public ClientUserProfile() {

    }
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCurrLeague() {
        return currLeague;
    }

    public void setCurrLeague(int currLeague) {
        this.currLeague = currLeague;
    }

    public int getNumOfGamesPlayed() {
        return numOfGamesPlayed;
    }

    public void setNumOfGamesPlayed(int numofgamesplayed) {
        this.numOfGamesPlayed = numofgamesplayed;
    }

    public int getAmountEarnedInLeague() {
        return amountEarnedInLeague;
    }

    public void setAmountEarnedInLeague(int amountearnedinleague) {
        this.amountEarnedInLeague = amountearnedinleague;
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
