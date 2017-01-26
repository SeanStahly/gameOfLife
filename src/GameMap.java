import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class GameMap
{
    public static final boolean					DEBUG	= false;
    HashMap<Integer, HashMap<Integer, CellThread>>	gameMap	= new HashMap<Integer, HashMap<Integer, CellThread>>();
    int											width;
    int											height;
    int											gen		= 0;

    public GameMap(int w, int h)
    {
        width = w;
        height = h;
        gameMap.put(gen, new HashMap<Integer, CellThread>());
//        initCells();
    }

//    private void initCells()
//    {
//        gameMap.put(gen, new HashMap<Integer, CellThread>());
//        for (int w = 0; w < width; w++)
//        {
//            for (int h = 0; h < height; h++)
//            {
//                getCell(gen, w, h);
//            }
//        }
//    }

//    public CellThread getCell(int gen, int x, int y)
//    {
//        int loc = (x + y * width);
//        CellThread res = gameMap.get(gen).get(loc);
//        if (res == null)
//        {
//            res = new CellThread(x, y, );
//            gameMap.get(gen).put(loc, res);
//        }
//        return res;
//    }

    public void setCell(int x, int y)
    {
        int loc = (x + y * width);
        CellThread ct= new CellThread(x, y, height, width, new HashMap<Integer, CellThread>());
        gameMap.get(0).put(loc, ct);
//        CellThread c = getCell(gen, x, y);
//        c.isAlive(true);
    }

    public void printMap(int gen)
    {
        for(CellThread ct : gameMap.get(gen).values()) {
            System.out.println(ct);
        }
//        for (int w = 0; w < width; w++)
//        {
//            for (int h = 0; h < height; h++)
//            {
//                Cell c = getCell(gen, w, h);
//                System.out.print(c);
//
//            }
//            System.out.println();
//        }
    }

    public void printMap2(int gen)
    {
        for(CellThread ct : gameMap.get(gen).values()) {
            System.out.print(ct.debugString());
        }
        System.out.println("COUNT: "+gameMap.get(gen).values().size());

//        for (int w = 0; w < width; w++)
//        {
//            for (int h = 0; h < height; h++)
//            {
//                CellThread c = getCell(gen, w, h);
//                if (c.getAlive())
//                {
//                    System.out.print(c.DebugString());
//                }
//
//            }
//
//        }
    }

    public void nextGen() {
        gen++;
        ForkJoinPool pool = new ForkJoinPool(3);
        List<Future<List<Point>>> res = pool.invokeAll(gameMap.get(gen - 1).values());

        List<Point> nextGen = new LinkedList<Point>();

        res.forEach(x -> {
            try {
                List<Point> list = x.get();
                if (list.size() > 1) {
                    list.remove(0);
                    synchronized (nextGen) {
                        nextGen.addAll(list.stream()
                                .filter(y ->
                                        !nextGen.contains(y))
                                .collect(Collectors.toList()));


                    }
                } else {
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


        System.out.println("blarg");
        gameMap.put(gen, new HashMap<Integer, CellThread>());
        nextGen.forEach( point -> {
            int loc = point.x + point.y * width;
            gameMap.get(gen).put(loc, new CellThread(point.x, point.y, height, width, gameMap.get(gen -1)));
        });


    }

//        initCells();
//        for (int w = 0; w < width; w++)
//        {
//            for (int h = 0; h < height; h++)
//            {
//                Cell p = getCell(gen - 1, w, h);
//                Cell c = getCell(gen, w, h);
//                if (DEBUG)
//                {
//                    System.out.println("-------------------");
//                    System.out.println(c);
//                    System.out.println("....................");
//                }
//                List<Cell> l = getNeighbors(gen - 1, c);
//                int count = 0;
//                for (Cell n : l)
//                {
//                    if (DEBUG)
//                    {
//                        System.out.println(n);
//                    }
//                    if (n.getAlive())
//                    {
//                        count++;
//                    }
//                }
//                c.isAlive(p.getAlive());
//                if (p.getAlive())
//                {
//                    if (count < 2 || count > 3)
//                    {
//                        c.isAlive(false);
//                    }
//                }
//                else
//                {
//                    if (count == 3)
//                    {
//                        c.isAlive(true);
//                    }
//                }
//
//            }
//        }

//    }

//    public List<Cell> getNeighbors(int gen, Cell c)
//    {
//        LinkedList<Cell> result = new LinkedList();
//        // 1 2 3
//        // 4 X 5
//        // 6 7 8
//        if (c.getX() > 0 && c.getY() > 0) // 1
//        {
//            result.add(getCell(gen, c.getX() - 1, c.getY() - 1));
//        }
//        if (c.getX() > 0) // 2
//        {
//            result.add(getCell(gen, c.getX() - 1, c.getY()));
//        }
//        if (c.getX() > 0 && c.getY() < height - 1) // 3
//        {
//            result.add(getCell(gen, c.getX() - 1, c.getY() + 1));
//        }
//        if (c.getY() > 0) // 4
//        {
//            result.add(getCell(gen, c.getX(), c.getY() - 1));
//        }
//        if (c.getY() < height - 1) // 5
//        {
//            result.add(getCell(gen, c.getX(), c.getY() + 1));
//        }
//        if (c.getX() < width - 1 && c.getY() > 0) // 6
//        {
//            result.add(getCell(gen, c.getX() + 1, c.getY() - 1));
//        }
//        if (c.getX() < width - 1) // 7
//        {
//            result.add(getCell(gen, c.getX() + 1, c.getY()));
//        }
//        if (c.getX() < width - 1 && c.getY() < height - 1) // 8
//        {
//            result.add(getCell(gen, c.getX() + 1, c.getY() + 1));
//        }
//        return result;
//    }
}
