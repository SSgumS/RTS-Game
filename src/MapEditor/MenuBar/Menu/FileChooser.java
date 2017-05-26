package MapEditor.MenuBar.Menu;

import MapEditor.Addresses.Addresses;
import MapEditor.GameEvent.Events;
import MapEditor.GameEvent.GameEvent;
import MapEditor.Map.Board;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Saeed on 5/22/2017.
 */
public class FileChooser extends JFileChooser implements ActionListener {

    private File current = Addresses.map;

    public FileChooser(String currentDirectoryPath) {
        super(currentDirectoryPath);

        setFileFilter(new FileNameExtensionFilter("Saeed & Mostafa Map File", "S&M"));

        addActionListener(this);
    }



    private void save() {
        String name = getSelectedFile().getName();
        if (!name.contains(".S&M"))
            name = name + ".S&M";

        if (new File(getSelectedFile().getParent() + "\\" + name).exists()) {
            int value = JOptionPane.showConfirmDialog(this, name + " already exists.\nDo you want to replace it?");

            if (value == JOptionPane.YES_OPTION) {
                try {
                    File file = new File("resources\\maps\\saves\\" + name);
                    file.delete();
                    file.createNewFile();
                    ObjectOutputStream ou = new ObjectOutputStream(new FileOutputStream(file));
                    ou.writeObject(Addresses.board);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                try {
//                    Files.copy(current.toPath(), Paths.get("resources\\maps\\saves", name), REPLACE_EXISTING);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        } else {
            try {
                File file = new File("resources\\maps\\saves\\" + name);
                file.createNewFile();
                ObjectOutputStream ou = new ObjectOutputStream(new FileOutputStream(file));



                ou.writeObject(Addresses.board);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void load() {
        String name = getSelectedFile().getName();
        if (!name.contains(".S&M"))
            name = name + ".S&M";

        try {
            File file = new File("resources\\maps\\saves\\" + name);
            ObjectInputStream ou = new ObjectInputStream(new FileInputStream(file));
            Addresses.board = (Board) ou.readObject();
            Addresses.panel.dispatchEvent(new GameEvent(this, Events.load));
            Addresses.panel.dispatchEvent(new GameEvent(this, Events.clearSelection));
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
