/**
 * Created by sean on 1/19/17.
 */
public class CellThread implements Runnable {

    private int gen;
    private int x;
    private int y;
    private boolean alive;

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

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public CellThread(boolean alive, int x, int y) {
        super();
        this.alive = alive;
        this.x = x;
        this.y = y;
        gen = 0;
//        try {
//            this.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }



    @Override
    public String toString()
    {
        String result = "";
        if (GameMap.DEBUG)
            result = "[" + x + "," + y + "]";
        result += "X";
//
// if (this.isAlive())
//        {
//            result += "X";
//        }
//        else
//        {
//            result += "_";
//        }
        return result;
    }

    public String DebugString()
    {
        String result = "[" + x + "," + y + "]";
        result += "X";
//        if (this.isAlive())
//        {
//            result += "X";
//        }
//        else
//        {
//            result += "_";
//        }
        return result;
    }

    @Override
    public void run() {
        try {
            alive = false;
            synchronized (this) {
                this.wait();
            }
            alive = true;
            synchronized (this) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
