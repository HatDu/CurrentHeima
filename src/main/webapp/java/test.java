import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j(topic = "test")
public class test {
    public static void main(String[] args){

        int[] nums = {-497,-494,-484,-477,-453,-453,-444,-442,-428,-420,-401,-393,-392,-381,-357,-357,-327,-323,-306,-285,-284,-263,-262,-254,-243,-234,-208,-170,-166,-162,-158,-136,-133,-130,-119,-114,-101,-100,-86,-66,-65,-6,1,3,4,11,69,77,78,107,108,108,121,123,136,137,151,153,155,166,170,175,179,211,230,251,255,266,288,306,308,310,314,321,322,331,333,334,347,349,356,357,360,361,361,367,375,378,387,387,408,414,421,435,439,440,441,470,492};
        fourSum(nums, 1682);
    }


    public static List<List<Integer>> fourSum(int[] nums, int target) {

        List<List<Integer>> res = new ArrayList<List<Integer>>();

        backTrack(nums, target, 0, 0, new ArrayList<Integer>(), res);
        return res;
    }

    public static void backTrack(int[] nums, int target, int index, int sum, ArrayList<Integer> list,  List<List<Integer>> res){

        if(index >= nums.length){
            if(sum == target && list.size() == 4){
                ArrayList<Integer> bin = new ArrayList<Integer>(list);
                Collections.sort(bin);
                System.out.println(isContain(bin, res));
                if(!isContain(bin, res)){
                    res.add(bin);
                }
            }
            return;
        }

        if(list.size() == 4){
            if(sum == target){
                ArrayList<Integer> bin = new ArrayList<Integer>(list);
                Collections.sort(bin);
                System.out.println(isContain(bin, res));
                if(!isContain(bin, res)){
                    res.add(bin);
                }
            }
            return;
        }

        if(list.size() < 4){
            sum += nums[index];
            list.add(nums[index]);
            backTrack(nums, target, index+1, sum, list, res);
            list.remove(list.size()-1);
            sum -= nums[index];
        }

        backTrack(nums, target, index+1, sum, list, res);

    }
    public static boolean isContain(ArrayList<Integer> list,  List<List<Integer>> res){

        for(int i = 0; i < res.size(); i++){
            ArrayList<Integer> bin = (ArrayList<Integer>) res.get(i);
            if(isSame(list, bin)){
                return true;
            }
        }
        return false;

    }
    public static boolean isSame(ArrayList<Integer> list1, ArrayList<Integer> list2){
        for(int i=0; i< list1.size(); i++){
            if((list1.get(i) != list2.get(i))){
                if(list1.get(i)==387){
                    System.out.println(list2.get(i)==387);
                    System.out.println(list2.get(i).equals(list1.get(i)));
                }
                System.out.println(list1.get(i) +"!="+ list2.get(i));
                return false;
            }
        }
        return true;
    }
}
