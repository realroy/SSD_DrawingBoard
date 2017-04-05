package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import objects.CompositeGObject;
import objects.GObject;

public class DrawingBoard extends JPanel {

  private MouseAdapter mouseAdapter;
  private List<GObject> gObjects;
  private GObject target;

  private int gridSize = 10;

  public DrawingBoard() {
    gObjects = new ArrayList<GObject>();
    mouseAdapter = new MAdapter();
    addMouseListener(mouseAdapter);
    addMouseMotionListener(mouseAdapter);
    setPreferredSize(new Dimension(800, 600));
  }

  public void addGObject(GObject gObject) {
    // TODO: Implement this method.
    gObjects.add(gObject);
    repaint();
  }

  public void groupAll() {
    // TODO: Implement this method.
    target.deselected();
    CompositeGObject compositeGObject = new CompositeGObject();
    gObjects.forEach(compositeGObject::add);
    gObjects = new ArrayList<>();
    compositeGObject.recalculateRegion();
    addGObject(compositeGObject);
    target = compositeGObject;
    target.selected();
  }

  public void deleteSelected() {
    // TODO: Implement this method.
    gObjects.remove(target);
    target = null;
    repaint();
  }

  public void clear() {
    // TODO: Implement this method.
    gObjects = new ArrayList<>();
    repaint();
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    paintBackground(g);
    paintGrids(g);
    paintObjects(g);
  }

  private void paintBackground(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());
  }

  private void paintGrids(Graphics g) {
    g.setColor(Color.lightGray);
    int gridCountX = getWidth() / gridSize;
    int gridCountY = getHeight() / gridSize;
    for (int i = 0; i < gridCountX; i++) {
      g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
    }
    for (int i = 0; i < gridCountY; i++) {
      g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
    }
  }

  private void paintObjects(Graphics g) {
    for (GObject go : gObjects) {
      go.paint(g);
    }
  }

  class MAdapter extends MouseAdapter {

    // TODO: You need some variables here

    private void deselectAll() {
      // TODO: Implement this method.
      gObjects.forEach(GObject::deselected);
      repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
      // TODO: Implement this method.
      int x = e.getX();
      int y = e.getY();
      for (GObject g : gObjects) {
        if (g.pointerHit(x, y)) {
          if (target != null) {
            deselectAll();
          }
          g.selected();
          target = g;
        }
      }
      repaint();
    }

  }

  @Override
  public void mouseDragged(MouseEvent e) {
    // TODO: Implement this method.
    if (target != null) {
      int x = e.getX();
      int y = e.getY();
      target.move(x, y);
      repaint();
    }
  }

}