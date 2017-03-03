import java.util.LinkedHashMap;
import java.util.Map;
import map.*;
/**
 * Created by andre_000 on 17-Feb-17.
 */
public class Main {

    public static void main(String[] args) {
        Map<Integer,String> myMap2 = new LinkedMap<Integer, String>();
        Map<Integer,String> myMap = new LinkedMap<Integer, String>();
        Map<Integer,String> linkedMap2 = new LinkedHashMap<Integer, String>();
        for(int i = 0; i< 5; i++){
            myMap.put(i,"string"+i);
        }
        for(int i = 3; i< 9; i++){
            myMap2.put(i,"string"+i);
        }
        myMap.put(56,"hehe");
        myMap.put(null,"hehe");
        System.out.println(myMap);
        System.out.println(myMap2);
        //myMap.put(null,null);
        myMap.entrySet().removeAll(myMap2.entrySet());
        System.out.println(myMap);
        System.out.println(myMap.get(null));

        myMap.clear();

    }
}
