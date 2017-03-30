package objects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public void add(GObject gObject) {
		// TODO: Implement this method.
		gObjects.add(gObject);
	}

	public void remove(GObject gObject) {
		// TODO: Implement this method.
		gObjects.remove(gObject);
	}

	@Override
	public void move(int dX, int dY) {
		// TODO: Implement this method.
		gObjects.forEach(gObj -> {
			if (gObj instanceof CompositeGObject) {
				CompositeGObject comObj = (CompositeGObject) gObj;
				comObj.move((gObj.x - x) + dX, (gObj.y - y) + dY);
			} else {
				gObj.move((gObj.x - x) + dX, (gObj.y - y) + dY);
			}
		});
		super.x = dX;
		super.y = dY;
	}
	
	public void recalculateRegion() {
		// TODO: Implement this method.
		int minX = gObjects.get(0).x;
		int minY = gObjects.get(0).y;
		int maxW = gObjects.get(0).x;
		int maxH = gObjects.get(0).y;
		int maxX = gObjects.get(0).x;
		int maxY = gObjects.get(0).y;
		for (GObject g : gObjects) {
			minX = (minX > g.x) ? g.x : minX;
			minY = (minY > g.y) ? g.y : minY;
			maxX = (maxX < g.x + g.width) ? g.x + g.width : maxX;
			maxY = (maxY < g.y + g.height) ? g.y + g.height : maxY;
		}
		x = minX;
		y = minY;
		width = maxX - minX;
		height = maxY - minY;
	}

	@Override
	public void paintObject(Graphics g) {
		// TODO: Implement this method.
		gObjects.forEach(gObject -> gObject.paint(g));
	}

	@Override
	public void paintLabel(Graphics g) {
		// TODO: Implement this method.
		g.drawString("Grouped", x, y);
	}
	
}
