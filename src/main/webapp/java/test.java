import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;

import javax.sound.midi.Soundbank;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j(topic = "test")
public class test {
    public static void main(String[] args) {
        int[] arr = {7,4,8,9,7,7,5};
        System.out.println(movesToMakeZigzag(arr));
    }
    public static int movesToMakeZigzag(int[] nums) {
        int even = 0;
        int odd = 0;
        int[] numsC1 = new int[nums.length];
        int[] numsC2 = new int[nums.length];
        System.arraycopy(nums, 0, numsC1, 0, nums.length);
        System.arraycopy(nums, 0, numsC2, 0, nums.length);

        for(int i = 0; i < numsC1.length; i += 2){
            if(i-1 >= 0 && numsC1[i-1] > numsC1[i]){
                int sub = numsC1[i-1] - numsC1[i] + 1;
                even += sub;
                numsC1[i-1] -= sub;
            }
            if(i+1 < numsC1.length && numsC1[i+1] > numsC1[i]){
                int sub = numsC1[i+1] - numsC1[i] + 1;
                even += sub;
                numsC1[i+1] -= sub;
            }
        }

        for(int i = 1; i < numsC2.length; i += 2){
            if(i-1 >= 0 && numsC2[i-1] > numsC2[i]){
                int sub = numsC2[i-1] - numsC2[i] + 1;
                odd += sub;
                numsC2[i-1] -= sub;
            }
            if(i+1 < numsC2.length && numsC2[i+1] > numsC2[i]){
                int sub = numsC2[i+1] - numsC2[i] + 1;
                odd += sub;
                numsC2[i+1] -= sub;
            }
        }
        System.out.println(even + " " + odd);
        return even > odd ? odd : even;
    }
}
