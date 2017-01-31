import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class GameMap implements GameInterface
{
    // Variable Initialization
    public static final boolean					DEBUG	= false;

    // Initialization of a new Hashmap to hold the current generation as the key
    // and the current iteration of cells
    HashMap<Integer, HashMap<Integer, CellThread>>	gameMap	= new HashMap<Integer, HashMap<Integer, CellThread>>();
    int											width;
    int											height;
    int											gen		= 0;

    public GameMap() {
        // Initialization of the first generation
        gameMap.put(gen, new HashMap<Integer, CellThread>());
    }

    @Override
    public void setWidth(int w) {
        width = w;
    }

    @Override
    public void setHeight(int h) {
        height = h;
    }

    /**
     * Creates a new CellThread object and inserts it along with
     * the x and y coordinates into the hashmap that holds the
     * current generation.
     * @param x Coordinate
     * @param y Coordinate
     */
    public void setCell(int x, int y)
    {
        int loc = (x + y * width);
        CellThread ct= new CellThread(x, y, height, width, new HashMap<Integer, CellThread>());
        gameMap.get(0).put(loc, ct);
    }

    public void printMap(int gen)
    {
        for(CellThread ct : gameMap.get(gen).values()) {
            System.out.println(ct);
        }
    }

    /**
     * Prints the game map of the current generation
     * @param gen Current generation of cells to print
     */
    public void printMap2(int gen)
    {
        Comparator<CellThread> comparator = Comparator.comparing(CellThread::getX);
        comparator.thenComparing(Comparator.comparing(CellThread::getY));

        // Comparing the x and y values using the comparator to sort then compare
        List<CellThread> cellThreads = new LinkedList<>(
                gameMap.get(gen).values().stream()
                        .sorted(comparator)
                        .collect(Collectors.toList()));

        for(CellThread ct : cellThreads) {
            System.out.print(ct.debugString());
        }
        System.out.println();
        System.out.println("COUNT: "+gameMap.get(gen).values().size());
    }

    public void nextGen() {
        gen++;

        // Creates the ExecutorService object and initializes the parallelism to use three cores at most
        ForkJoinPool pool = new ForkJoinPool(3);

        // Wakes all the threads and grabs the values from generation - 1
        List<Future<List<Point>>> res = pool.invokeAll(gameMap.get(gen - 1).values());

        List<Point> nextGen = new LinkedList<Point>();

        // Iterates through each item of the list of future lists
        res.forEach(x -> {
            try {
                List<Point> list = x.get();

                if (list.size() > 1) {
                    list.remove(0);

                    // Syncing up so we don't have multiple threads modifying the list at once
                    synchronized (nextGen) {
                        // Adds new items to the list and filters out the ones that have the same x and y points
                        nextGen.addAll(list.stream()
                                .filter(y ->
                                        !nextGen.contains(y))
                                .collect(Collectors.toList()));


                    }
                } else {

                    // Syncing up so we don't have multiple threads editing the hashmap
                    synchronized (gameMap) {
                        Point p = list.get(0);
                        int loc = p.x + p.y * width;
                        gameMap.get(gen -1).remove(loc);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        // Creates the new generation's hashmap
        gameMap.put(gen, new HashMap<Integer, CellThread>());

        // Starts initialization of CellThread Objects in the new generation
        nextGen.forEach( point -> {
            int loc = point.x + point.y * width;
            gameMap.get(gen).put(loc, new CellThread(point.x, point.y, height, width, gameMap.get(gen -1)));
        });
    }

    // Millers stuff
    @Override
    public void loadMap(String s)
    {
        Scanner t;
        try
        {
            t = new Scanner(new File(s));
            for (int h = 0; h < height && t.hasNextLine(); h++)
            {
                String line = t.nextLine();
                System.out.println(line);
                for (int w = 0; w < width; w++)
                {
                    if (line.length() > w && line.charAt(w) == 'X')
                    {
                        // System.out.println("cell" + w + "," + h);
                        setCell(w, h);

                    }
                }
            }
            t.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
