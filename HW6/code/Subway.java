import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Subway {

    public static final String TRSF = "TRANSFER";
    public static final int INF = 2147483647;

    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(
                new FileReader(args[0])
        );
        String line;

        // 이름을 key, 고유번호와 line을 저장 : 고유번호에 따른 역 이름 조회를 위해 존재
        HashMap<String, dic> station_dic = new HashMap<>();

        Subway_Graph subway = new Subway_Graph();

        // vertex 입력
        while ((line = reader.readLine()) != null) {
            String[] newStr = line.split("\\s+");

            if (newStr.length != 3) {
                break;
            }

            if (station_dic.get(newStr[1])==null) {
                // 기존에 역이 존재하지 않는 상황

                Station new_station = new Station(newStr[0], newStr[1]);
                new_station.add_line(newStr[2]);
                subway.insert_v(newStr[0], new_station);
                // 고유번호로 key 지정

                // 이름->고유번호, 라인 을 조회할 수 있도록 함
                dic new_dic = new dic();
                new_dic.add(newStr[0],newStr[2]);
                station_dic.put(newStr[1], new_dic);


            } else {
                // 기존에 역이 존재하여 edge 추가해주어야 하는 경우

                Station new_station = new Station(newStr[0], newStr[1]);
                new_station.add_line(newStr[2]);

                dic target_dic = station_dic.get(newStr[1]);
                for (String x : target_dic.pk){

                    Station target_station = subway.map.get(x);

                    Edge new_edge = new Edge(target_station, new_station, x, newStr[0], 5, TRSF);
                    target_station.add_edge(new_edge);
                    Edge new_edge2 = new Edge(new_station, target_station, newStr[0], x, 5, TRSF);
                    new_station.add_edge(new_edge2);

                    //System.out.println(target_station.name + " >> " + new_station.name + " : TRSF");
                }

                target_dic.add(newStr[0],newStr[2]);

                subway.insert_v(newStr[0], new_station);


            }



        }

        // Edge 입력
        while ((line = reader.readLine()) != null) {

            String[] newStr2 = line.split("\\s+");

            Station from_station = subway.map.get(newStr2[0]);
            Station to_station = subway.map.get(newStr2[1]);

            String edge_line = from_station.line.get(0);

            Edge new_edge = new Edge(from_station, to_station, newStr2[0], newStr2[1], Integer.parseInt(newStr2[2]), edge_line);
            from_station.add_edge(new_edge);

        }
        reader.close();



        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true)
        {
            try
            {
                String input = br.readLine();
                if (input.compareTo("QUIT") == 0)
                    break;

                command(subway, station_dic, input);
            }
            catch (IOException e)
            {
                System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
            }
        }
    }

    private static void command(Subway_Graph subway, HashMap<String,dic> station_dic, String input) throws IOException {

        String[] stations = input.split("\\s+");
        String from_name = stations[0];
        String to_name = stations[1];



        String path_candidates = null;
        int time_candidates = INF;

        dic target_dic = station_dic.get(from_name);

        for (String x : target_dic.pk){

            PriorityQueue<Station> priorityQueue = new PriorityQueue<>();
            List<Station> visit_path = new ArrayList<>();
            List<Edge> visit_edge = new ArrayList<>();

            Station new_station = subway.map.get(x);
            new_station.set_distance(0);

            while(1==1){
                visit_path.add(new_station);
                if(new_station.name.equals(to_name)){
                    break;
                }
                List<Edge> adj_edge_list = new_station.Adj_station;
                for (Edge e : adj_edge_list){
                    int transfer = 0;


                    if(!(new_station.used_edge.line==null)){
                        if(!new_station.used_edge.pk_to.equals(e.pk_from)) {
                            transfer = 5;
                        }
                    }
                    if (new_station.distance + e.weight + transfer < e.station_to.distance){

                        priorityQueue.remove(e.station_to);
                        e.station_to.set_distance(new_station.distance + e.weight + transfer);
                        e.station_to.set_used_edge(e);
                        priorityQueue.add(e.station_to);
                    }
                }
                new_station = priorityQueue.poll();
                visit_edge.add(new_station.used_edge);
            }

            Station now = new_station;
            String time = Integer.toString(now.distance);
            Station station_from = subway.map.get(x);
            List<String> path = new ArrayList<>();
            StringBuilder strbuilder = new StringBuilder();

            path.add(0,now.name);
            while(1==1){


                Station previous = now.used_edge.station_from;

                if (previous == null){
                    break;
                }

                if (previous.name == station_from.name){
                    path.add(0,previous.name);
                    break;
                }
                Station previous2 = previous.used_edge.station_from;

                if(previous2!=null && previous2.name.equals(previous.name)){
                    path.add(0,"["+previous.name+"]");
                    now = previous2;
                } else {
                    path.add(0,previous.name);
                    now = previous;
                }




            }

            String a = "";
            for (String y : path){
                strbuilder.append(a).append(y);
                a=" ";
            }
            String Str = strbuilder.toString().trim();
            if (Integer.parseInt(time)<time_candidates){
                path_candidates = Str;
                time_candidates = Integer.parseInt(time);
            }

            // Graph 초기화 작업
            for (Station z : subway.map.values()){
                z.init();
            }

        }

        System.out.println(path_candidates);
        System.out.println(time_candidates);



    }




}

