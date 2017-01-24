import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
				initCells();
			}

		private void initCells()
			{
				gameMap.put(gen, new HashMap<Integer, CellThread>());
				for (int w = 0; w < width; w++)
					{
						for (int h = 0; h < height; h++)
							{
								getCell(gen, w, h);
							}
					}
			}

		public CellThread getCell(int gen, int x, int y)
			{
				int loc = (x + y * width);
				CellThread res = gameMap.get(gen).get(loc);
				if (res == null)
					{
						res = new CellThread(false, x, y);
						gameMap.get(gen).put(loc, res);
					}
				return res;
			}

		public void setCell(int x, int y)
			{
				CellThread c = getCell(0, x, y);
                synchronized (c) {
                    c.notify();
                }
//				c.isAlive(true);
			}

		public void printMap(int gen)
			{
				for (int w = 0; w < width; w++)
					{
						for (int h = 0; h < height; h++)
							{
								CellThread c = getCell(gen, w, h);
								System.out.print(c);

							}
						System.out.println();
					}
			}

		public void printMap2(int gen)
			{
				for (int w = 0; w < width; w++)
					{
						for (int h = 0; h < height; h++)
							{
								CellThread c = getCell(gen, w, h);

								if (c.isAlive())
									{
										System.out.print(c.DebugString());
									}

							}

					}
			}

		public void nextGen()
			{
				gen++;
				initCells();
				for (int w = 0; w < width; w++)
					{
						for (int h = 0; h < height; h++)
							{
								CellThread p = getCell(gen - 1, w, h);
								CellThread c = getCell(gen, w, h);
								if (DEBUG)
									{
										System.out.println("-------------------");
//										System.out.println(c);
										System.out.println("....................");
									}
								List<CellThread> l = getNeighbors(gen - 1, c);
								int count = 0;
								for (CellThread n : l)
									{
										if (DEBUG)
											{
												System.out.println(n);
											}
//										if (n.getAlive())
//											{
//												count++;
//											}
									}
//								c.isAlive(p.getAlive());
//								if (p.getAlive())
//									{
//										if (count < 2 || count > 3)
//											{
//												c.isAlive(false);
//											}
//									}
//								else
//									{
//										if (count == 3)
//											{
//												c.isAlive(true);
//											}
//									}

							}
					}

			}

		public List<CellThread> getNeighbors(int gen, CellThread c)
			{
				LinkedList<CellThread> result = new LinkedList();
				// 1 2 3
				// 4 X 5
				// 6 7 8
				if (c.getX() > 0 && c.getY() > 0) // 1
					{
						result.add(getCell(gen, c.getX() - 1, c.getY() - 1));
					}
				if (c.getX() > 0) // 2
					{
						result.add(getCell(gen, c.getX() - 1, c.getY()));
					}
				if (c.getX() > 0 && c.getY() < height - 1) // 3
					{
						result.add(getCell(gen, c.getX() - 1, c.getY() + 1));
					}
				if (c.getY() > 0) // 4
					{
						result.add(getCell(gen, c.getX(), c.getY() - 1));
					}
				if (c.getY() < height - 1) // 5
					{
						result.add(getCell(gen, c.getX(), c.getY() + 1));
					}
				if (c.getX() < width - 1 && c.getY() > 0) // 6
					{
						result.add(getCell(gen, c.getX() + 1, c.getY() - 1));
					}
				if (c.getX() < width - 1) // 7
					{
						result.add(getCell(gen, c.getX() + 1, c.getY()));
					}
				if (c.getX() < width - 1 && c.getY() < height - 1) // 8
					{
						result.add(getCell(gen, c.getX() + 1, c.getY() + 1));
					}
				return result;
			}
	}
