import java.util.ArrayList;
import java.util.List;

public class dic {
    public List<String> pk;
    public List<String> line;

    //Constructor
    public dic() {
        pk = new ArrayList<>();
        line = new ArrayList<>();
    }

    public void add(String x, String y){
        this.pk.add(x);
        this.line.add(y);
    }

}
