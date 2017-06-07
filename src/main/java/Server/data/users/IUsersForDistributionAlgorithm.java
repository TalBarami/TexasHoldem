package Server.data.users;

import Server.domain.user.User;

import java.util.List;

/**
 * Created by user on 12/05/2017.
 */
public interface IUsersForDistributionAlgorithm {
    List<User> getAllUsersInList();
    List<User> getUsersByLeague(int leagueNum);
}
