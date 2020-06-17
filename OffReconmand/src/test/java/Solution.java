


import java.util.*;


/**
 * @创建人 YDL
 * @创建时间 2020/5/31 17:05
 * @描述
 */
public class Solution {
    public boolean checkInclusion(String s1, String s2) {

        Map<Character,Integer> map = new HashMap<>();
        for(int i=0;i<s1.length();i++){
            char c =  s1.charAt(i);
            map.put(c,map.getOrDefault(c,0)+1);
        }
        for(int right=0,left = 0;right<s2.length();){
            char c = s2.charAt(right++);
            map.put(c,map.getOrDefault(c,0)-1);
            while(left<right&&map.get(c)<0){
                char cc = s2.charAt(left++);
                map.put(cc,map.get(cc)+1);
            }
            if(right-left==s1.length()){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
        Solution solution = new Solution();

        System.out.println(solution.checkInclusion("bdo","eidboaoo"));
    }
}