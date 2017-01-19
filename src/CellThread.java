/**
 * Created by sean on 1/19/17.
 */
public class CellThread extends Thread {

    private int gen;
    private int x;
    private int y;

    public int getGen() {
        return gen;
    }

    public void setGen(int gen) {
        this.gen = gen;
    }

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



    public CellThread(boolean alive, int x, int y) {
        super();
    }

    @Override
    public String toString()
    {
        String result = "";
        if (GameMap.DEBUG)
            result = "[" + x + "," + y + "]";
        if (this.isAlive())
        {
            result += "X";
        }
        else
        {
            result += "_";
        }
        return result;
    }

    public String DebugString()
    {
        String result = "[" + x + "," + y + "]";
        if (this.isAlive())
        {
            result += "X";
        }
        else
        {
            result += "_";
        }
        return result;
    }
}
