package MapEditor.MenuBar.Menu;

import MapEditor.Addresses.Addresses;
import MapEditor.GameEvent.Events;
import MapEditor.GameEvent.GameEvent;
import MapEditor.Map.Board;
import MapEditor.Map.Cell.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by Saeed on 5/22/2017.
 */
public class FileChooser extends JFileChooser implements ActionListener {

    public FileChooser(String currentDirectoryPath) {
        super(currentDirectoryPath);

        setFileFilter(new FileNameExtensionFilter("Saeed & Mostafa Map File", "S&M"));

        addActionListener(this);
    }

    private void save() {
        String name = getSelectedFile().getName();
        if (!name.contains(".S&M"))
            name = name + ".S&M";

        File file = new File(getSelectedFile().getParent() + "\\" + name);

        if (file.exists()) {
            int value = JOptionPane.showConfirmDialog(this, name + " already exists.\nDo you want to replace it?", "Confirm Overwrite", JOptionPane.YES_NO_OPTION);

            if (value == JOptionPane.YES_OPTION)
                file.delete();
            else
                return;
        }

        try {
            file.createNewFile();
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(file));

            zipOut.putNextEntry(new ZipEntry("map"));
            ObjectOutputStream out = new ObjectOutputStream(zipOut);
            out.writeObject(Addresses.board);
            out.flush();

            Vector<Cell> cells = Addresses.undo.getCells();
            for (int i = 0; i < cells.size(); i++) {
                zipOut.putNextEntry(new ZipEntry("undo image " + i + ".png"));
                ImageIO.write(cells.elementAt(i).getTerrainImage(), "png", zipOut);
            }
            cells = Addresses.redo.getCells();
            for (int i = 0; i < cells.size(); i++) {
                zipOut.putNextEntry(new ZipEntry("redo image " + i + ".png"));
                ImageIO.write(cells.elementAt(i).getTerrainImage(), "png", zipOut);
            }

            out.close();
            zipOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        String name = getSelectedFile().getName();
        if (!name.contains(".S&M"))
            name = name + ".S&M";

        File file = new File(getSelectedFile().getParent() + "\\" + name);

        try {
            ZipFile zipFile = new ZipFile(file);

            ObjectInputStream in = new ObjectInputStream(zipFile.getInputStream(zipFile.getEntry("map")));
            Addresses.board = (Board) in.readObject();
            Addresses.panel.dispatchEvent(new GameEvent(this, Events.load));
            Addresses.panel.dispatchEvent(new GameEvent(this, Events.clearSelection));

            Cell[][] mapCells = Addresses.board.cells;
            for (int i = 0; i < mapCells.length; i++)
                for (int j = 0; j < mapCells.length; j++)
                    mapCells[i][j].dispatchEvent(new GameEvent(this, Events.cellRefactor));

            Vector<Cell> cells = Addresses.undo.getCells();
            for (int i = 0; i < cells.size(); i++)
                cells.elementAt(i).setTerrainImage(ImageIO.read(zipFile.getInputStream(zipFile.getEntry("undo image " + i + ".png"))));
            cells = Addresses.redo.getCells();
            for (int i = 0; i < cells.size(); i++)
                cells.elementAt(i).setTerrainImage(ImageIO.read(zipFile.getInputStream(zipFile.getEntry("redo image " + i + ".png"))));

            in.close();
            zipFile.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileChooser source = (FileChooser) e.getSource();

        if (e.getActionCommand().equals(APPROVE_SELECTION)) {
            if (source.getDialogType() == SAVE_DIALOG) {
                save();
            } else {
                load();
            }
        }
    }

}
