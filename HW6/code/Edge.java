public class Edge  {
    public int weight;
    public Station station_from;
    public String pk_from;
    public Station station_to;
    public String pk_to;
    public String line;

    public static final int INF = 2147483647;

    // Constructor
    public Edge(){
        this.weight = INF;
        this.station_from = null;
        this.station_to = null;
        this.pk_from = null;
        this.pk_to = null;
        this.line = null;
    }

    public Edge(Station f, Station t, String a, String b, int w, String l) {
        this.weight = w;
        this.station_from = f;
        this.station_to = t;
        this.pk_from = a;
        this.pk_to = b;
        this.line = l;
    }

}
