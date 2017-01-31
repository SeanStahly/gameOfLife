import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by sean on 1/19/17.
 */
public class CellThread implements Callable<List<Point>> {

    //    private int gen;
    private int x;
    private int y;
    private int height;
    private int width;
    HashMap<Integer, CellThread> prevGen;

    public CellThread(int x, int y, int height, int width, HashMap<Integer, CellThread> prevGen)
    {
        this.x = x;
        this.y = y;
        this.height = height;
        this. width = width;
        this.prevGen = prevGen;
    }

    /**
     * Checks if the previous generation is alive or if its hash map is empty
     * then returns a new list of arrays containing x and y coordinates
     * @return A list of x and y coordinates
     * @throws Exception
     */
    @Override
    public List<Point> call() throws Exception {
        if (alivePrevGen() || prevGen.size() == 0) {
            return returnNeighbors();
        } else {
            return Arrays.asList(new Point(x, y));
        }
    }

    /**
     * Checks the surrounding alive cells in the previous generation. Then will return
     * True if there are only three adjacent live cells, otherwise it will
     * return false.
     * @return True if there are 3 adjacent cells alive around the current cell
     */
    public boolean alivePrevGen() {
        int adjacent = 0;
        // 1 2 3
        // 4 X 5
        // 6 7 8
        if (getX() > 0 && getY() > 0) // 1
        {
            if (prevGen.containsKey((getX()-1) + (getY()-1) * width)) {
                adjacent++;
            }
        }
        if (getX() > 0) // 2
        {
            if (prevGen.containsKey((getX()-1) + (getY()) * width)) {
                adjacent++;
            }
        }
        if (getX() > 0 && getY() < height - 1) // 3
        {
            if (prevGen.containsKey((getX()-1) + (getY()+1) * width)) {
                adjacent++;
            }
        }
        if (getY() > 0) // 4
        {
            if (prevGen.containsKey((getX()) + (getY()-1) * width)) {
                adjacent++;
            }
        }
        if (getY() < height - 1) // 5
        {
            if (prevGen.containsKey((getX()) + (getY()+1) * width)) {
                adjacent++;
            }
        }
        if (getX() < width - 1 && getY() > 0) // 6
        {
            if (prevGen.containsKey((getX()+1) + (getY()-1) * width)) {
                adjacent++;
            }
        }
        if (getX() < width - 1) // 7
        {
            if (prevGen.containsKey((getX()+1) + (getY()) * width)) {
                adjacent++;
            }
        }
        if (getX() < width - 1 && getY() < height - 1) // 8
        {
            if (prevGen.containsKey((getX()+1) + (getY()+1) * width)) {
                adjacent++;
            }
        }

        if (prevGen.containsKey(x + y * width)) {
            if (adjacent < 2 || adjacent > 3) {
                return false;
            } else {
                return true;
            }
        } else {
            if (adjacent == 3) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Returns a list of neighbors around this current cell
     * @return List of x and y coordinates
     */
    public List<Point> returnNeighbors() {
        LinkedList<Point> result = new LinkedList();
        result.add(new Point(x,y));
        // 1 2 3
        // 4 X 5
        // 6 7 8
        if (getX() > 0 && getY() > 0) // 1
        {
//            result.add((getX()-1) + (getY()-1) * width);
//            result.add(prevGen.get((getX()-1) + (getY()-1) *10));
            result.add(new Point(getX() - 1, getY() - 1));
        }
        if (getX() > 0) // 2
        {
//            result.add((getX()-1) + (getY()) * width);
//            result.add(prevGen.get((getX() - 1)+ getY() * 10));
            result.add(new Point(getX() - 1, getY()));
        }
        if (getX() > 0 && getY() < height - 1) // 3
        {
//            result.add((getX()-1) + (getY()+1) * width);
//            result.add(prevGen.get(((getX() - 1) + (getY() + 1)*10)));
            result.add(new Point(getX() - 1, getY() + 1));
        }
        if (getY() > 0) // 4
        {
//            result.add((getX()) + (getY()-1) * width);
//            result.add(prevGen.get(getX() + (getY() - 1)*10));
            result.add(new Point(getX(), getY() - 1));
        }
        if (getY() < height - 1) // 5
        {
//            result.add((getX()) + (getY()+1) * width);
//            result.add(prevGen.get(getX() + (getY() + 1) * 10));
            result.add(new Point(getX(), getY() + 1));
        }
        if (getX() < width - 1 && getY() > 0) // 6
        {
//            result.add((getX()+1) + (getY()-1) * width);
//            result.add(prevGen.get((getX()+1) + (getY() -1) * 10));
            result.add(new Point(getX() + 1, getY() - 1));
        }
        if (getX() < width - 1) // 7
        {
//            result.add((getX()+1) + (getY()) * width);
//            result.add(prevGen.get((getX() +1) + getY() * 10));
            result.add(new Point(getX() + 1, getY()));
        }
        if (getX() < width - 1 && getY() < height - 1) // 8
        {
//            result.add((getX()+1) + (getY()+1) * width);
//            result.add(prevGen.get((getX()+1) + (getY() + 1) * 10));
            result.add(new Point(getX() + 1, getY() + 1));
        }
        return result;
    }

    //============ Getters and Setters ================
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    //============ Getters and Setters ================

    @Override
    public String toString()
    {
        String result = "";
        if (GameMap.DEBUG)
            result = "[" + x + "," + y + "]";
        result += "X";
        return result;
    }

    public String debugString()
    {
        String result = "[" + x + "," + y + "]";
        result += "X";
        return result;
    }
}
