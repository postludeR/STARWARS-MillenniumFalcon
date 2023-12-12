import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class OperationalData {
    static final String fileName="src/data.txt";

    public static void main(String[] args) {
        List<Result> results = loadData();
        for (Result result : results) {
            System.out.println(result);
        }
    }
    public static void saveData(List<Result>results){
        try {
            java.io.PrintWriter pw = new java.io.PrintWriter(fileName);
            results.sort((o1, o2) -> o2.getScore()-o1.getScore());
            for(int i=0;i<Math.min(results.size(),10);i++){
                Result result = results.get(i);
                pw.println(result.getSurvivalTime()+" "+result.getNumberOfSmallEnemyDestroyed()+" "+result.getNumberOfMediumEnemyDestroyed()+" "+result.getScore());
            }
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertDataToFile(Result result){
        List<Result>results = loadData();
        results.add(result);
        saveData(results);
    }
    public static List<Result> loadData(){
        List<Result>results = new CopyOnWriteArrayList<>();
        try {
            Scanner s = new Scanner(new FileReader(fileName));
            while (s.hasNext()){
                String line = s.nextLine();
                String[] data = line.split(" ");
                results.add(new Result(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(data[3])));
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // sort By score desc
        results.sort((o1, o2) -> o2.getScore()-o1.getScore());


        return results;
    }

}

class Result{
    int survivalTime;
    int score;
    int numberOfSmallEnemyDestroyed;
    int numberOfMediumEnemyDestroyed;

    public Result(int survivalTime, int numberOfSmallEnemyDestroyed, int numberOfMediumEnemyDestroyed,int score) {
        this.survivalTime = survivalTime;
        this.numberOfSmallEnemyDestroyed = numberOfSmallEnemyDestroyed;
        this.numberOfMediumEnemyDestroyed = numberOfMediumEnemyDestroyed;
        this.score = score;
    }

    public int getSurvivalTime() {
        return survivalTime;
    }

    public void setSurvivalTime(int survivalTime) {
        this.survivalTime = survivalTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumberOfSmallEnemyDestroyed() {
        return numberOfSmallEnemyDestroyed;
    }

    public void setNumberOfSmallEnemyDestroyed(int numberOfSmallEnemyDestroyed) {
        this.numberOfSmallEnemyDestroyed = numberOfSmallEnemyDestroyed;
    }

    public int getNumberOfMediumEnemyDestroyed() {
        return numberOfMediumEnemyDestroyed;
    }

    public void setNumberOfMediumEnemyDestroyed(int numberOfMediumEnemyDestroyed) {
        this.numberOfMediumEnemyDestroyed = numberOfMediumEnemyDestroyed;
    }

    @Override
    public String toString() {
        String str = survivalTime+" "+numberOfSmallEnemyDestroyed+" "+numberOfMediumEnemyDestroyed+" "+score;
        return str;
    }
}