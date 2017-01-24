import java.util.concurrent.ForkJoinPool;

public class GameOfLife
	{
		public static void main(String[] args)
			{
				GameMap g = new GameMap(5, 7);
				g.setCell(1, 1);

				g.setCell(0, 0);
				g.setCell(2, 1);
				g.setCell(1, 0);
				g.setCell(4, 6);
				g.setCell(0, 5);
				g.setCell(1, 5);
				g.setCell(2, 5);
				g.printMap2(0);
				System.out.println();
				ForkJoinPool pool = new ForkJoinPool(3);
				for (int i = 1; i < 10; i++)
					{
//						g.nextGen();
						// System.out.println();
						// g.printMap(i);
//						System.out.println("\nIteration " + i);
//						g.printMap2(i);
					}

			}
	}
