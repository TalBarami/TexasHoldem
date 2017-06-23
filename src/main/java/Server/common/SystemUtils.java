package Server.common;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tal on 05/04/2017.
 */
public class SystemUtils {
    public static boolean hasNullOrEmptyOrSpecialChars(String ... strings){
        for(String s : strings){
            if(nullOrEmpty(s)){
                return true;
            }

            char[] chars = s.toCharArray();
            for(char c : chars){
                if(c < 32 || c > 126){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean verifyPassword(String pass){
        if(nullOrEmpty(pass)){
            return true;
        }

        List<Character> bad = Arrays.asList(new Character[]{'\t','\b','r','f','\n','\'','\"','\\'});
        char[] chars = pass.toCharArray();
        for(char c : chars){
            if(bad.contains(c)){
                return true;
            }
        }
        return false;
    }

    public static boolean nullOrEmpty(String s){
        if(s == null || s.isEmpty()) {
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
