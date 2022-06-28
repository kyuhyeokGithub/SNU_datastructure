import java.util.HashMap;

public class Subway_Graph {

    public HashMap<String, Station> map;

    // Constructor
    public Subway_Graph() {
        this.map = new HashMap<>();
    }

    public void insert_v(String key, Station X) {
        this.map.put(key, X);
    }


}
