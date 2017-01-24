import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class GameOfLife
	{
		final private static Integer GENERATIONS = 10;
		final private static Integer X_SIZE = 5;
		final private static Integer Y_SIZE = 7;

		public static void main(String[] args)
			{
				ExecutorService executorService = Executors.newFixedThreadPool(3);
				GameMap g = new GameMap(X_SIZE, Y_SIZE);
				ForkJoinPool pool = new ForkJoinPool(3);
				for (CellThread ct: g.gameMap.values()) {
					executorService.submit(ct);
				}
//				pool.submit(g.gameMap.get(0).get(0));
//				g.gameMap.get(0);
				synchronized (g) {
					g.setCell(1, 1);

					g.setCell(0, 0);
					g.setCell(2, 1);
					g.setCell(1, 0);
					g.setCell(4, 6);
					g.setCell(0, 5);
					g.setCell(1, 5);
					g.setCell(2, 5);
				}
				synchronized (g) {
					g.printMap2(0);
				}
				System.out.println();
//				ForkJoinPool pool = new ForkJoinPool(3);
				for (int i = 1; i < GENERATIONS; i++)
					{
						g.cellCount = new HashMap<CellThread, Integer>();
						g.gameMap.
//						for (CellThread c: g.gameMap.)
						//for active cells of gen before
						//run cells
							//check neighbors




//						g.nextGen();
						// System.out.println();
						// g.printMap(i);
//						System.out.println("\nIteration " + i);
//						g.printMap2(i);
					}

			}
	}
