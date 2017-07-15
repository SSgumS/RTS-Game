package Units.Human;

import Addresses.Addresses;
import GameEvent.Events;
import GameEvent.GameEvent;
import Map.GameBoard;
import Map.GameCell;
import Player.Player;
import Terrain.Terrain;
import Units.Building.Building;
import Units.Resource.Resource;
import Units.Units;

import java.awt.event.ComponentEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Created by Saeed on 6/6/2017.
 */
public class Human extends Units implements Runnable {

    protected Player owner;
    protected Object target;
    protected Object originalTarget;
    protected int targetX, targetY;
    protected int job = 0;
    protected int state = 2;
    protected boolean fadeFlag = false;
    protected double speed = 4;
    protected double startX, startY;
    protected int workPlaceI, workPlaceJ;

    public Human(GameCell cell, Player owner, int size) {
        super(cell, owner, size);

        this.owner = owner;

        originalColor = owner.getColor();
        color = originalColor;
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.setKind:
                owner.addUnit(this);

                Addresses.undo.push(this);
                Addresses.redo.removeAll();
                break;
            case Events.setKindStack:
                owner.addUnit(this);
                break;
            case Events.clearKind:
                owner.clearUnit(this);
                break;
        }
    }

    protected GameBoard board = Addresses.board;
    protected boolean[][] wasHere = new boolean[Addresses.board.mapSize][Addresses.board.mapSize];
    protected int[][] correctPath = new int [board.mapSize][board.mapSize];
    //    protected Integer[][][][] correctPath = new Integer[Addresses.board.mapSize][Addresses.board.mapSize][2][2];
    protected int startI = 0, startJ = 0;
    protected int endI = 0, endJ = 0;

//    protected void walk() {
//        int I = findCellI((int) getOriginalCenterX(), (int) getOriginalCenterY(), 0, Addresses.board.mapSize);
//        int J = findCellJ((int) getOriginalCenterX(), (int) getOriginalCenterY(), 0, Addresses.board.mapSize);
//        int endI, endJ;
//        if (target instanceof Units) {
//            endI = findCellI((int) ((Units) target).getOriginalCenterX(), (int) ((Units) target).getOriginalCenterY(), 0, Addresses.board.mapSize);
//            endJ = findCellJ((int) ((Units) target).getOriginalCenterX(), (int) ((Units) target).getOriginalCenterY(), 0, Addresses.board.mapSize);
//        } else {
//            endI = findCellI((int) ((GameCell) target).getOriginalCenterX(), (int) ((GameCell) target).getOriginalCenterY(), 0, Addresses.board.mapSize);
//            endJ = findCellJ((int) ((GameCell) target).getOriginalCenterX(), (int) ((GameCell) target).getOriginalCenterY(), 0, Addresses.board.mapSize);
//        }
//        GameCell nowCell = Addresses.board.cells[I][J];
//        GameCell endCell = Addresses.board.cells[endI][endJ];
//        System.out.println(I + " " + J + " " + endI + " " + endJ + " " + this.endI + " " + this.endJ);
//
//        boolean hasWay = true;
//        double vx, vy;
//
//        if (this.endI != endI || this.endJ != endJ || (getOriginalCenterX() == startX && getOriginalCenterY() == startY)) {
//            this.endI = endI;
//            this.endJ = endJ;
//
//            hasWay = findWay(nowCell, endCell);
//        }
//        System.out.println(hasWay);
//
//        if (hasWay && !nowCell.equals(endCell)) {
//            System.out.println("salam");
//            vx = 2*speed/Math.sqrt(5);
//            vy = speed/Math.sqrt(5);
//
//            Integer[][] direction = correctPath[nowCell.getI()][nowCell.getJ()];
//
//            if (cellIsValid(Addresses.board.cells[nowCell.getI() + direction[0][0]][nowCell.getJ() + direction[0][1]])) {
//                originalOriginX = originalOriginX + direction[1][0]*vx;
//                originalOriginY = originalOriginY + direction[1][1]*vy;
//
//                for (int k = 0; k < 4; k++) {
//                    originalXPoints[k] = originalXPoints[k] + direction[1][0]*vx;
//                    originalYPoints[k] = originalYPoints[k] + direction[1][1]*vy;
//                }
//
//                if (Arrays.equals(direction[1], new Integer[]{1, -1}))
//                    this.direction = 5;
//                else if (Arrays.equals(direction[1], new Integer[]{-1, -1}))
//                    this.direction = 3;
//                else if (Arrays.equals(direction[1], new Integer[]{-1, 1}))
//                    this.direction = 1;
//                else if (Arrays.equals(direction[1], new Integer[]{1, 1}))
//                    this.direction = 7;
//            }
//        } else if (hasWay) {
//            System.out.println("chetori");
//            double deltaX, deltaY;
//            if (target instanceof Units) {
//                deltaX = ((Units) target).getOriginalCenterX() - getOriginalCenterX();
//                deltaY = ((Units) target).getOriginalCenterY() - getOriginalCenterY();
//            } else {
//                deltaX = ((GameCell) target).getOriginalCenterX() - getOriginalCenterX();
//                deltaY = ((GameCell) target).getOriginalCenterY() - getOriginalCenterY();
//            }
//            double degree = Math.atan(deltaX/deltaY);
//            if (deltaX < 0)
//                degree += Math.PI;
//            else if (deltaY <= 0)
//                degree += 2*Math.PI;
//
//            if (Math.hypot(deltaX, deltaY) < speed) {
//                state = 2;
//                return;
//            }
//
//            vx = speed*Math.cos(degree);
//            vy = speed*Math.sin(degree);
//
//            originalOriginX = originalOriginX + vx;
//            originalOriginY = originalOriginY + vy;
//
//            for (int k = 0; k < 4; k++) {
//                originalXPoints[k] = originalXPoints[k] + vx;
//                originalYPoints[k] = originalYPoints[k] + vy;
//            }
//
//            if (15*Math.PI/8 < degree && degree <= Math.PI/8) {
//                direction = 6;
//            } else if (Math.PI/8 < degree && degree <= 3*Math.PI/8) {
//                direction = 5;
//            } else if (3*Math.PI/8 < degree && degree <= 5*Math.PI/8) {
//                direction = 4;
//            } else if (5*Math.PI/8 < degree && degree <= 7*Math.PI/8) {
//                direction = 3;
//            } else if (7*Math.PI/8 < degree && degree <= 9*Math.PI/8) {
//                direction = 2;
//            } else if (9*Math.PI/8 < degree && degree <= 11*Math.PI/8) {
//                direction = 1;
//            } else if (11*Math.PI/8 < degree && degree <= 13*Math.PI/8) {
//                direction = 0;
//            } else if (13*Math.PI/8 < degree && degree <= 15*Math.PI/8) {
//                direction = 7;
//            }
//        } else {
//            state = 2;
//        }
//    }
//
//    protected boolean findWay(GameCell nowCell, GameCell endCell) {
//        if (nowCell.getI() == endCell.getI() && nowCell.getJ() == endCell.getJ())
//            return true;
//
//        for (int i = 0; i < Addresses.board.mapSize; i++)
//            for (int j = 0; j < Addresses.board.mapSize; j++)
//                correctPath[i][j] = null;
//
//        double deltaX = endCell.getOriginalCenterX() - nowCell.getOriginalCenterX();
//        double deltaY = endCell.getOriginalCenterY() - nowCell.getOriginalCenterY();
//        double degree = Math.atan(deltaX/deltaY);
//        if (deltaX < 0)
//            degree += Math.PI;
//        else if (deltaY <= 0)
//            degree += 2*Math.PI;
//
//        HashMap<Double, Integer[][]> priority = new HashMap<>();
//        priority.put(Math.abs(degree - Math.PI/4), new Integer[][]{{1, 0}, {1, -1}});
//        priority.put(Math.abs(degree - 3*Math.PI/4), new Integer[][]{{0, -1}, {-1, -1}});
//        priority.put(Math.abs(degree - 5*Math.PI/4), new Integer[][]{{-1, 0}, {-1, 1}});
//        priority.put(Math.abs(degree - 7*Math.PI/4), new Integer[][]{{0, 1}, {1, 1}});
//
//        TreeMap<Double, Integer[][]> sortedPriority = new TreeMap<>(priority);
//
//        for (int i = 0; i < 4; i++) {
//            Integer[][] integers = (Integer[][]) sortedPriority.values().toArray()[i];
//            System.out.println(Arrays.toString(integers[0]));
//            System.out.println(Arrays.toString(integers[1]));
//            try {
//                GameCell cell = Addresses.board.cells[nowCell.getI() + integers[0][0]][nowCell.getJ() + integers[0][1]];
//                if (cellIsValid(cell) && correctPath[cell.getI()][cell.getJ()] == null) {
//                    correctPath[nowCell.getI()][nowCell.getJ()] = integers;
//                    return findWay(cell, endCell);
//                }
//            } catch (ArrayIndexOutOfBoundsException ignored) {}
//        }
//
//        return false;
//    }

    @Override
    protected boolean findWay() {
        haveV = false;
        endI = findCellI(targetX, targetY, 0, board.mapSize);
        endJ = findCellJ(targetX, targetY, 0, board.mapSize);
        startI = findCellI((int) originalOriginX + board.originalWidth/2, (int) originalOriginY + board.originalHeight/2, 0, board.mapSize);
        startJ = findCellJ((int) originalOriginX + board.originalWidth/2, (int) originalOriginY + board.originalHeight/2, 0, board.mapSize);
        for (int row = 0; row < board.mapSize; row++)
            for (int col = 0; col < board.mapSize; col++){
                wasHere[row][col] = false;
                correctPath[row][col] = 0;
            }
        correctPath[endI][endJ]=1;
        Vector<Integer> vi=new Vector<>();
        Vector<Integer> vj=new Vector<>();
        vi.add(new Integer(endI));
        vj.add(new Integer(endJ));
        if(recursive(vi,vj, 2))
            return true;
        return false;
    }

    protected boolean recursive(Vector<Integer> vi, Vector<Integer> vj,int counter) {
        Vector <Integer> VI=new Vector<>();
        Vector <Integer> VJ=new Vector<>();
        for (int i=0;i<vi.size();i++) {
            int I=vi.get(i),J=vj.get(i);
            if(I>0&&correctPath[I-1][J]==0&&( cellIsValid(board.cells[I-1][J]) || (startI == I - 1 && startJ == J) )) {
                correctPath[I-1][J]=counter;
                VI.add(new Integer(I-1));
                VJ.add(new Integer(J));
                if(I-1==startI&&J==startJ)
                    return true;
            }
            if(J>0&&correctPath[I][J-1]==0&&( cellIsValid(board.cells[I][J-1]) || (startI == I && startJ == J-1) )) {
                correctPath[I][J-1]=counter;
                VI.add(new Integer(I));
                VJ.add(new Integer(J-1));
                if(I==startI&&J-1==startJ)
                    return true;
            }
            if(I<board.mapSize-1&&correctPath[I+1][J]==0&&( cellIsValid(board.cells[I+1][J]) || (startI == I + 1 && startJ == J) )) {
                correctPath[I+1][J]=counter;
                VI.add(new Integer(I+1));
                VJ.add(new Integer(J));
                if(I+1==startI&&J==startJ)
                    return true;
            }
            if(J<board.mapSize-1&&correctPath[I][J+1]==0&&( cellIsValid(board.cells[I][J+1]) || (startI == I&& startJ == J+1) )) {
                correctPath[I][J+1]=counter;
                VI.add(new Integer(I));
                VJ.add(new Integer(J+1));
                if(I==startI&&J+1==startJ)
                    return true;
            }
        }
        if(VI.size()!=0)
        {
            return recursive(VI,VJ,counter+1);
        }
        else
            return false;
    }

    protected boolean haveV = false;
    protected double vx, vy;

    protected void walk() {
        int I = findCellI((int) originalOriginX + board.originalWidth/2, (int) originalOriginY + board.originalHeight/2, 0, board.mapSize),
                J = findCellJ((int) originalOriginX + board.originalWidth/2, (int) originalOriginY + board.originalHeight/2, 0, board.mapSize);

        correctPath[I][J] = 0;

        synchronized (this) {
            if (I == endI && J == endJ) {
                if(!haveV) {
                    vx = speed*(targetX - (originalOriginX + board.originalWidth/2))/Math.hypot(targetX - (originalOriginX + board.originalWidth/2), targetY - (originalOriginY + board.originalHeight/2));
                    vy = speed*(targetY - (originalOriginY + board.originalHeight/2))/Math.hypot(targetX - (originalOriginX + board.originalWidth/2), targetY - (originalOriginY + board.originalHeight/2));
                    double degree = Math.atan(vy/vx);

                    if(vx > 0) {
                        if(degree > -Math.PI/8 && degree < Math.PI/8)
                            direction = 6;
                        else if(degree > Math.PI/8 && degree < 3*Math.PI/8)
                            direction = 7;
                        else if(degree > -3*Math.PI/8 && degree < -Math.PI/8)
                            direction = 5;
                        else if(degree > 3*Math.PI/8)
                            direction = 0;
                        else if(degree < -3*Math.PI/8)
                            direction = 4;
                    } else {
                        if(degree > -Math.PI/8 && degree < Math.PI/8)
                            direction = 2;
                        else if(degree > Math.PI/8 && degree < 3*Math.PI/8)
                            direction = 3;
                        else if(degree > -3*Math.PI/8 && degree < -Math.PI/8)
                            direction = 1;
                        else if(degree > 3*Math.PI/8)
                            direction = 4;
                        else if(degree < -3*Math.PI/8)
                            direction = 0;
                    }

                    haveV = true;
                }

                if(cellIsValid(board.cells[endI][endJ])) {
                    if(Math.hypot(targetX - (originalOriginX + board.originalWidth/2), targetY - (originalOriginY + board.originalHeight/2)) <= speed/2) {
                        state = 2;
                        return;
                    }
//                if(Math.hypot(targetX - (originalOriginX + board.originalWidth/2), targetY - (originalOriginY + board.originalHeight/2)) <= speed/2) {
//                    state = 2;
//                    return;
//                }
                    originalOriginX += vx;
                    originalOriginY += vy;
                    for (int k = 0; k < 4; k++) {
                        originalXPoints[k] = originalXPoints[k] + vx;
                        originalYPoints[k] = originalYPoints[k] + vy;
                    }
                } else {
                    state = 0;
                    return;
                }
            } else {
                haveV = false;

                int[][] sort = new int[2][4];
                for(int i = 0; i < 4; i++) {
                    sort[0][i] = 0;
                    sort[1][i] = i;
                }

                if(I > 0)
                    sort[0][0] = correctPath[I-1][J];
                if(J < board.mapSize-1)
                    sort[0][1] = correctPath[I][J+1];
                if(I < board.mapSize-1)
                    sort[0][2] = correctPath[I+1][J];
                if(J > 0)
                    sort[0][3] = correctPath[I][J-1];
                for(int i = 0; i < 3; i++)
                    if((sort[0][i+1]==0||sort[0][i] < sort[0][i+1])&&sort[0][i]!=0) {
                        sort[0][i+1] = sort[0][i];
                        sort[1][i+1] = sort[1][i];
                    }
                if(sort[0][3] == 0) {
                    if(!findWay()) {
                        state = 2;
                        return;
                    }
                } else {
                    int best = sort[1][3];
                    if(best == 0 && (cellIsValid(board.cells[I-1][J]) || (endI == I - 1 && endJ == J))) {
                        originalOriginX = originalOriginX - speed;
                        originalOriginY = originalOriginY - speed/2;
                        for (int k = 0; k < 4; k++) {
                            originalXPoints[k] = originalXPoints[k] - speed;
                            originalYPoints[k] = originalYPoints[k] - speed/2;
                        }
                        state = 1;
                        direction = 3;
                    } else if(best == 1 && (cellIsValid(board.cells[I][J+1]) || (endI == I && endJ == J+1))) {
                        originalOriginX = originalOriginX + speed;
                        originalOriginY = originalOriginY - speed/2;
                        for (int k = 0; k < 4; k++) {
                            originalXPoints[k] = originalXPoints[k] + speed;
                            originalYPoints[k] = originalYPoints[k] - speed/2;
                        }
                        state = 1;
                        direction = 5;
                    } else if(best == 2 && (cellIsValid(board.cells[I+1][J]) || (endI == I + 1 && endJ == J))) {
                        originalOriginX = originalOriginX + speed;
                        originalOriginY = originalOriginY + speed/2;
                        for (int k = 0; k < 4; k++) {
                            originalXPoints[k] = originalXPoints[k] + speed;
                            originalYPoints[k] = originalYPoints[k] + speed/2;
                        }
                        state = 1;
                        direction = 7;
                    } else if(best == 3 && (cellIsValid(board.cells[I][J-1]) || (endI == I && endJ == J - 1))) {
                        originalOriginX = originalOriginX - speed;
                        originalOriginY = originalOriginY + speed/2;
                        for (int k = 0; k < 4; k++) {
                            originalXPoints[k] = originalXPoints[k] - speed;
                            originalYPoints[k] = originalYPoints[k] + speed/2;
                        }
                        state = 1;
                        direction = 1;
                    } else {
                        if(!findWay()) {
                            state = 2;
                            return;
                        }
                    }
                }
            }

            zoom();
        }

        reArrange();
    }

    protected boolean cellIsValid(GameCell cell) {
        for(Terrain terrain : abandonTerrains)
            if(terrain == cell.getTerrain())
                return false;

        Units unit = cell.getKind();
        return !((unit instanceof Resource) || (unit instanceof Building));
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    protected void work() {}

    protected void build(int endI, int endJ) {}

    @Override
    public void run() {
        while (alive) {
            switch (state) {
                case 0:
                    switch (job) {
                        case 0:
                            break;
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            work();
                            break;
                        case 5:
                            build(endI, endJ);
                            break;
                        case 6:
                            work();
                            break;
                    }
                    break;
                case 1:
                    switch (job) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
//                            int I = findCellI((int) getOriginalCenterX(), (int) getOriginalCenterY(), 0, Addresses.board.mapSize);
//                            int J = findCellJ((int) getOriginalCenterX(), (int) getOriginalCenterY(), 0, Addresses.board.mapSize);
//                            int endI, endJ;
//                            if (target instanceof Units) {
//                                endI = findCellI((int) ((Units) target).getOriginalCenterX(), (int) ((Units) target).getOriginalCenterY(), 0, Addresses.board.mapSize);
//                                endJ = findCellJ((int) ((Units) target).getOriginalCenterX(), (int) ((Units) target).getOriginalCenterY(), 0, Addresses.board.mapSize);
//                            } else {
//                                endI = findCellI((int) ((GameCell) target).getOriginalCenterX(), (int) ((GameCell) target).getOriginalCenterY(), 0, Addresses.board.mapSize);
//                                endJ = findCellJ((int) ((GameCell) target).getOriginalCenterX(), (int) ((GameCell) target).getOriginalCenterY(), 0, Addresses.board.mapSize);
//                            }
//                            GameCell nowCell = Addresses.board.cells[I][J];
//                            GameCell endCell = Addresses.board.cells[endI][endJ];
                            walk();
                            break;
                    }
                    break;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
