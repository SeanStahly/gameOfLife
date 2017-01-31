import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class GameOfLife
{
    final private static Integer GENERATIONS = 10;
    final private static Integer X_SIZE = 5;
    final private static Integer Y_SIZE = 7;

    public static void main(String[] args) throws Exception
    {
        Long start = System.currentTimeMillis();
        GameMap g = new GameMap();
        g.setWidth(30);
        g.setHeight(30);
        g.loadMap("30x30.txt");
        g.printMap2(0);
        System.out.println();
        g.nextGen();

        for (int i = 1; i < 61; i++)
        {
            g.nextGen();
            // System.out.println();
//            g.printMap(i);
            System.out.println("\nIteration " + i);
            g.printMap2(i);
        }
        Long end = System.currentTimeMillis();
        System.out.println("\nSeconds = " + (end - start) / 1000);
//        g.printMap2File(299, "100x1000-v1-300Iterations.txt");
    }
}
