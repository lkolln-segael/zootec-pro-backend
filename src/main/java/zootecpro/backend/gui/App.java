package zootecpro.backend.gui;

import java.awt.CardLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.springframework.context.ConfigurableApplicationContext;

import com.sun.net.httpserver.HttpServer;

public class App extends JFrame {

  private JLabel springLabelState = new JLabel("No iniciado el servidor de backend");
  private JLabel springLabelFrontendState = new JLabel("No iniciado el servidor de backend");

  private JButton springStartButton = new JButton("Iniciar Servidor de spring");
  private JButton springCloseButton = new JButton("Cerrar Servidor de spring");

  private JButton springStartFrontend = new JButton("Iniciar Servidor frontend");
  private JButton springCloseFrontend = new JButton("Cerrar Servidor frontend");

  private Supplier<ConfigurableApplicationContext> springFunction;

  private ConfigurableApplicationContext springContext;

  private ExecutorService executor = Executors.newSingleThreadExecutor();
  private HttpServer server;

  public App(Supplier<ConfigurableApplicationContext> springFunction) {
    super();
    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Rectangle bounds = graphicsEnvironment.getMaximumWindowBounds();

    bounds.width = bounds.width / 4;
    bounds.height = bounds.height / 4;

    GridLayout gridLayout = new GridLayout(3, 2, 10, 10);

    this.springFunction = springFunction;

    setLayout(gridLayout);
    add(springLabelState);
    add(springLabelFrontendState);

    add(springStartButton, "START");
    add(springStartFrontend, "START_FRONTEND");

    add(springCloseButton, "CLOSE");
    add(springCloseFrontend, "CLOSE_FRONTEND");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(bounds.width, bounds.height);
  }

  public void start() {

    this.springStartButton.addActionListener(e -> {
      this.springLabelState.setText("Servidor Backend iniciado");
      this.springContext = this.springFunction.get();
    });

    this.springCloseButton.addActionListener(e -> {
      if (this.springContext == null) {
        return;
      }
      this.springLabelState.setText("Servidor Backend detenido");
      this.springContext.close();
    });

    this.springStartFrontend.addActionListener(e -> {
      try {
        server = HttpServer.create(new InetSocketAddress(4200), 0);
        server.setExecutor(executor);
        server.createContext("/", (exchange) -> {
          String path = exchange.getRequestURI().getPath();
          InputStream is;
          if (path.equals("/")) {
            is = getClass().getResourceAsStream("/frontend/index.html");
          } else {
            is = getClass().getResourceAsStream("/frontend" + path);
          }
          if (is == null) {
            is = getClass().getResourceAsStream("/frontend/index.html");
          }
          byte[] response = is.readAllBytes();
          String[] substrings = path.split("\\.");
          if (substrings.length > 0) {
            String type = substrings[substrings.length - 1];
            Map<String, String> types = Map.of("js", "text/javascript",
                "html", "text/html", "css", "text/css");
            if (types.containsKey(type)) {
              exchange.getResponseHeaders().set("Content-Type", types.get(type) + "; charset=UTF-8");
            }
          } else {
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
          }
          exchange.sendResponseHeaders(200, response.length);
          try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
          }
          exchange.close();
        });
        this.springLabelFrontendState.setText("Servidor Frontend iniciado");
        server.start();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    });

    this.springCloseFrontend.addActionListener(e -> {
      this.springLabelFrontendState.setText("Servidor Frontend detenido");
      server.stop(0);
    });
    SwingUtilities.invokeLater(() -> {
      setVisible(true);
    });
  }
}
