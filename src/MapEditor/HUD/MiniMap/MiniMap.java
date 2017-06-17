package MapEditor.HUD.MiniMap;

import Addresses.Addresses;
import Map.GameBoard;
import Map.GameCell;
import Units.Units;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by Saeed on 5/15/2017.
 */
public class MiniMap extends JPanel {

    private GameBoard board;
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
                GameCell cell = board.cells[i][j];
//                try {
                g2D.setColor(cell.getColor());
                g2D.fill(paths[i][j]);
//                } catch (NullPointerException ignored) {}
            }
        }

            for (Units unit : Units.getUnits()) {
                g2D.setColor(unit.getColor());

                Polygon shape = unit.getShape();
                Path2D.Double path = new Path2D.Double();
                path.moveTo((double) shape.xpoints[0]*width/board.width, (double) shape.ypoints[0]*width/board.width + getHeight()/2);
                path.lineTo((double) shape.xpoints[1]*width/board.width, (double) shape.ypoints[1]*width/board.width + getHeight()/2);
                path.lineTo((double) shape.xpoints[2]*width/board.width, (double) shape.ypoints[2]*width/board.width + getHeight()/2);
                path.lineTo((double) shape.xpoints[3]*width/board.width, (double) shape.ypoints[3]*width/board.width + getHeight()/2);
                path.closePath();

                g2D.fill(path);
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
