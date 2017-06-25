package Server.communication.converters;

import MutualJsonObjects.ClientUserProfile;
import Server.domain.user.User;

/**
 * Created by אחיעד on 14/05/2017.
 */
public class UserClientUserProfileConverter {
    public static ClientUserProfile convert(User user){
        ClientUserProfile client = new ClientUserProfile();

        client.setUsername(user.getUsername());
        client.setPassword(user.getPassword());
        client.setEmail(user.getEmail());
        client.setYearOfBirth(user.getDateOfBirth().getYear());
        client.setMonthOfBirth(user.getDateOfBirth().getMonth().getValue());
        client.setDayOfBirth(user.getDateOfBirth().getDayOfMonth());
        client.setBalance(user.getBalance());
        client.setAmountEarnedInLeague(user.getAmountEarnedInLeague());
        client.setCurrLeague(user.getCurrLeague());
        client.setNumOfGamesPlayed(user.getNumOfGamesPlayed());
        client.setImage(user.getImg());

        return client;

    }
}
