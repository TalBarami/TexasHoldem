package TexasHoldem.common;

/**
 * Created by Tal on 05/04/2017.
 */
public class SystemUtils {
    public static boolean hasNullOrEmptyOrSpecialChars(String ... strings){
        for(String s : strings){
            if(s == null || s.isEmpty()) {
                return true;
            }
            char[] chars = s.toCharArray();
            for(char c : chars){
                if(c < 32 || c > 126){
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean arePositive(int ... ints){
        for(int i : ints){
            if(i < 0)
                return false;
        }
        return true;
    }


}
