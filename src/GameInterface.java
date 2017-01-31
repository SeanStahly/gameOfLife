
public interface GameInterface
	{
		public void setWidth(int w);// width of map

		public void setHeight(int h);// height of map

		public void loadMap(String s);

		public void setCell(int x, int y);// set cell to alive
	}
