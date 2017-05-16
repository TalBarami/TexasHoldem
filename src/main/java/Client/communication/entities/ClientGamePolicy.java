package Client.communication.entities;

/**
 * Created by Tal on 15/05/2017.
 */
public enum ClientGamePolicy {
    LIMIT(0), NOLIMIT(1), POTLIMIT(2);

    private int policy;
    ClientGamePolicy(int policy) {
        this.policy = policy;
    }

    public int value() {
        return policy;
    }
}
