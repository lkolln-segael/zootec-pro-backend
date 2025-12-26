package zootecpro.backend.gui.actions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExportAnimalExcel {

  private Connection connection;

  public ExportAnimalExcel() throws SQLException {
    connection = DriverManager.getConnection("jdbc:sqlite:./zootecpro.db");
  }

  public void export() throws SQLException {
    PreparedStatement stm = connection.prepareStatement("SELECT * from animal");
    IO.println(stm.executeQuery().getString(2));
  }
}
