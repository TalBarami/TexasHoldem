package TexasHoldem.domain.game;

/**
 * Created by Tal on 19/04/2017.
 */
public enum GamePolicy {
    LIMIT(0), NOLIMIT(1), POTLIMIT(2);

    private int policy;
    GamePolicy(int policy) {
        this.policy = policy;
    }

    public int getPolicy() {
        return policy;
    }
}
