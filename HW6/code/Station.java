import java.util.ArrayList;
import java.util.List;

public class Station implements Comparable<Station>{
    public String pk;
    public String name;
    public List<String> line;
    public List<Edge> Adj_station;
    public int distance;
    public Edge used_edge;

    public static final int INF = 2147483647;

    // Constructor
    public Station(String pk, String name) {
        this.pk = pk;
        this.name = name;
        this.line = new ArrayList<String>();
        this.Adj_station = new ArrayList<Edge>();
        this.distance = INF;
        this.used_edge = new Edge();
    }

    public void add_line(String l){
        this.line.add(l);
    }

    public List<String> get_line(){
        return line;
    }

    public void add_edge(Edge e){
        this.Adj_station.add(e);
    }

    public void set_distance(int d){
        this.distance = d;
    }

    public void set_used_edge(Edge e){
        this.used_edge = e;
    }

    public void init(){
        this.distance = INF;
        this.used_edge = new Edge();
    }

    public int compareTo(Station obj) {
        if (this.distance == obj.distance) {
            return 0;
        } else if(this.distance < obj.distance) {
            return -1;
        } else {
            return 1;
        }

    }




}
