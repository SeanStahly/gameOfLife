public class Cell
	{
		private Boolean	alive;
		private int		x;
		private int		y;

		public int getX()
			{
				return x;
			}

		public int getY()
			{
				return y;
			}

		public Cell(Boolean alive, int x, int y)
			{
				super();
				this.alive = alive;
				this.x = x;
				this.y = y;
			}

		public Boolean getAlive()
			{
				return alive;
			}

		public void isAlive(Boolean alive)
			{
				this.alive = alive;
			}

		@Override
		public String toString()
			{
				String result = "";
				if (GameMap.DEBUG)
					result = "[" + x + "," + y + "]";
				if (alive)
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
				if (alive)
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
