package TexasHoldem.common;

/**
 * Created by Tal on 05/04/2017.
 */
public class SystemUtils {
    private static final String[] invalidChars = {"\n", "\r", "\t"};
    public static boolean hasNullOrEmptyOrSpecialChars(String ... strings){
        for(String s : strings){
            if(s == null || s.isEmpty())
                return true;
            for(String c : invalidChars)
                if(s.contains(c))
                    return true;
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
