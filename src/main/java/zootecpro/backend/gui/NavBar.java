package zootecpro.backend.gui;

import java.sql.SQLException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import zootecpro.backend.gui.actions.ExportAnimalExcel;

public class NavBar extends JMenuBar {

  private JMenu opciones;
  private JMenu archivos;

  private JMenuItem menuItem1;
  private JMenuItem menuItem2;
  private JMenuItem menuItem3;
  private JMenuItem backUp;

  public NavBar() {
    super();
    archivos = new JMenu("Archivo");
    menuItem1 = new JMenuItem("Inicio");
    menuItem2 = new JMenuItem("Exportar animales a excel");
    menuItem3 = new JMenuItem("Ayuda");
    backUp = new JMenuItem("Realizar backup de la base de datos");

    backUp.addActionListener(a -> {
      ExportAnimalExcel exportAnimalExcel;
      try {
        exportAnimalExcel = new ExportAnimalExcel();
        exportAnimalExcel.export();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });

    archivos.add(menuItem1);
    archivos.add(menuItem2);
    archivos.add(backUp);
    archivos.add(menuItem3);
    add(archivos);

    opciones = new JMenu("Opciones");
    menuItem1 = new JMenuItem("Preferencias");
    menuItem2 = new JMenuItem("Temas");

    opciones.add(menuItem1);
    opciones.add(menuItem2);

    add(opciones);

  }

}
