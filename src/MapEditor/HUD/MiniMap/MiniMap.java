package MapEditor.HUD.MiniMap;

import MapEditor.Addresses.Addresses;
import MapEditor.Map.Board;
import MapEditor.Map.Cell.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by Saeed on 5/15/2017.
 */
public class MiniMap extends JPanel {

    private Board board;
    private double width;
    private double height;
    private double rectWidth;
    private double rectHeight;
    private Path2D.Double[][] paths;

    public MiniMap(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        setOpaque(false);
        setSize(392, 196);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        for (int i = 0; i < board.mapSize; i++) {
            for (int j = 0; j < board.mapSize; j++) {
                Cell cell = board.cells[i][j];

                g2D.setColor(cell.getTerrain().getColor());
                g2D.fill(paths[i][j]);

                try {
                    g2D.setColor(cell.getColor());
                    g2D.fill(paths[i][j]);
                } catch (NullPointerException ignored) {}
            }
        }

        Rectangle2D.Double rect = new Rectangle2D.Double((double) -board.xo*width/board.width, (double) -board.yo*height/board.height + getHeight()/2, rectWidth, rectHeight);
        g2D.setColor(Color.WHITE);
        g2D.draw(rect);
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        board = Addresses.board;

        width = (double) getWidth()/board.mapSize;
        height = (double) getHeight()/board.mapSize;

        rectWidth = (double) board.getWidth()*width/board.width;
        rectHeight = (double) board.getHeight()*height/board.height;

        paths = new Path2D.Double[board.mapSize][board.mapSize];

        for (int i = 0; i < board.mapSize; i++) {
            for (int j = 0; j < board.mapSize; j++) {
                Path2D.Double path = new Path2D.Double();
                path.moveTo(j*width/2+i*width/2, -j*height/2+i*height/2 + getHeight()/2);
                path.lineTo(width/2+j*width/2+i*width/2, -height/2-j*height/2+i*height/2 + getHeight()/2);
                path.lineTo(width+j*width/2+i*width/2, -j*height/2+i*height/2 + getHeight()/2);
                path.lineTo(width/2+j*width/2+i*width/2, height/2-j*height/2+i*height/2 + getHeight()/2);
                path.closePath();

                paths[i][j] = path;
            }
        }
    }

}
