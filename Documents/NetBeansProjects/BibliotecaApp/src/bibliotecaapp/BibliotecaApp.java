package bibliotecaapp;

/**
 *
 * @author Ivan Jara
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BibliotecaApp extends JFrame {
    private Bibliotecario bibliotecario;

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JPanel panelAgregarLibro;
    private JPanel panelActualizarLibro;
    private JPanel panelVerLibros;

    private JTextField txtAgregarId;
    private JTextField txtAgregarTitulo;
    private JTextField txtAgregarAutor;
    private JTextField txtAgregarAnio;
    private JTextField txtAgregarGenero;

    private JTextField txtActualizarId;
    private JTextField txtActualizarTitulo;
    private JTextField txtActualizarAutor;
    private JTextField txtActualizarAnio;
    private JTextField txtActualizarGenero;

    private JTextArea areaListaLibros;

    public BibliotecaApp() {
        bibliotecario = new Bibliotecario();

        setTitle("Sistema de Gestión de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        crearPanelAgregarLibro();
        crearPanelActualizarLibro();
        crearPanelVerLibros();

        mainPanel.add(panelAgregarLibro, "AgregarLibro");
        mainPanel.add(panelActualizarLibro, "ActualizarLibro");
        mainPanel.add(panelVerLibros, "VerLibros");

        JMenuBar menuBar = new JMenuBar();
        JMenu menuOpciones = new JMenu("Opciones");

        JMenuItem itemAgregar = new JMenuItem("Agregar Libro");
        itemAgregar.addActionListener(e -> cardLayout.show(mainPanel, "AgregarLibro"));

        JMenuItem itemActualizar = new JMenuItem("Actualizar Libro");
        itemActualizar.addActionListener(e -> cardLayout.show(mainPanel, "ActualizarLibro"));

        JMenuItem itemVer = new JMenuItem("Ver Libros");
        itemVer.addActionListener(e -> {
            actualizarListaLibros();
            cardLayout.show(mainPanel, "VerLibros");
        });

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));

        menuOpciones.add(itemAgregar);
        menuOpciones.add(itemActualizar);
        menuOpciones.add(itemVer);
        menuOpciones.addSeparator();
        menuOpciones.add(itemSalir);
        menuBar.add(menuOpciones);
        setJMenuBar(menuBar);

        add(mainPanel);
    }

    private void crearPanelAgregarLibro() {
        panelAgregarLibro = new JPanel(new GridLayout(7, 2, 10, 10));
        panelAgregarLibro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelAgregarLibro.add(new JLabel("ID del Libro:"));
        txtAgregarId = new JTextField();
        panelAgregarLibro.add(txtAgregarId);

        panelAgregarLibro.add(new JLabel("Título:"));
        txtAgregarTitulo = new JTextField();
        panelAgregarLibro.add(txtAgregarTitulo);

        panelAgregarLibro.add(new JLabel("Autor:"));
        txtAgregarAutor = new JTextField();
        panelAgregarLibro.add(txtAgregarAutor);

        panelAgregarLibro.add(new JLabel("Año de Publicación:"));
        txtAgregarAnio = new JTextField();
        panelAgregarLibro.add(txtAgregarAnio);

        panelAgregarLibro.add(new JLabel("Género:"));
        txtAgregarGenero = new JTextField();
        panelAgregarLibro.add(txtAgregarGenero);

        JButton btnAgregar = new JButton("Agregar Libro");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = txtAgregarId.getText();
                    String titulo = txtAgregarTitulo.getText();
                    String autor = txtAgregarAutor.getText();
                    int anio = Integer.parseInt(txtAgregarAnio.getText());
                    String genero = txtAgregarGenero.getText();

                    Libro nuevoLibro = new Libro(id, titulo, autor, anio, genero);
                    if (bibliotecario.agregarLibro(nuevoLibro)) {
                        JOptionPane.showMessageDialog(panelAgregarLibro, "Libro agregado exitosamente.");
                        limpiarCamposAgregar();
                    } else {
                        JOptionPane.showMessageDialog(panelAgregarLibro, "Error: Ya existe un libro con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelAgregarLibro, "El año de publicación debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelAgregarLibro, "Ocurrió un error al agregar el libro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panelAgregarLibro.add(btnAgregar);
        panelAgregarLibro.add(new JLabel(""));
    }

    private void limpiarCamposAgregar() {
        txtAgregarId.setText("");
        txtAgregarTitulo.setText("");
        txtAgregarAutor.setText("");
        txtAgregarAnio.setText("");
        txtAgregarGenero.setText("");
    }

    private void crearPanelActualizarLibro() {
        panelActualizarLibro = new JPanel(new GridLayout(8, 2, 10, 10));
        panelActualizarLibro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        panelActualizarLibro.add(new JLabel("ID del Libro a Actualizar:"));
        txtActualizarId = new JTextField();
        panelActualizarLibro.add(txtActualizarId);

        JButton btnBuscar = new JButton("Buscar Libro");
        btnBuscar.addActionListener(e -> {
            String id = txtActualizarId.getText();
            Libro libro = bibliotecario.buscarLibroPorId(id);
            if (libro != null) {
                txtActualizarTitulo.setText(libro.getTitulo());
                txtActualizarAutor.setText(libro.getAutor());
                txtActualizarAnio.setText(String.valueOf(libro.getAnioPublicacion()));
                txtActualizarGenero.setText(libro.getGenero());
            } else {
                JOptionPane.showMessageDialog(panelActualizarLibro, "Libro no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                limpiarCamposActualizar();
            }
        });
        panelActualizarLibro.add(btnBuscar);
        panelActualizarLibro.add(new JLabel(""));

        panelActualizarLibro.add(new JLabel("Nuevo Título:"));
        txtActualizarTitulo = new JTextField();
        panelActualizarLibro.add(txtActualizarTitulo);

        panelActualizarLibro.add(new JLabel("Nuevo Autor:"));
        txtActualizarAutor = new JTextField();
        panelActualizarLibro.add(txtActualizarAutor);

        panelActualizarLibro.add(new JLabel("Nuevo Año de Publicación:"));
        txtActualizarAnio = new JTextField();
        panelActualizarLibro.add(txtActualizarAnio);

        panelActualizarLibro.add(new JLabel("Nuevo Género:"));
        txtActualizarGenero = new JTextField();
        panelActualizarLibro.add(txtActualizarGenero);

        JButton btnActualizar = new JButton("Actualizar Libro");
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = txtActualizarId.getText();
                    String nuevoTitulo = txtActualizarTitulo.getText();
                    String nuevoAutor = txtActualizarAutor.getText();
                    int nuevoAnio = Integer.parseInt(txtActualizarAnio.getText());
                    String nuevoGenero = txtActualizarGenero.getText();

                    if (bibliotecario.actualizarLibro(id, nuevoTitulo, nuevoAutor, nuevoAnio, nuevoGenero)) {
                        JOptionPane.showMessageDialog(panelActualizarLibro, "Libro actualizado exitosamente.");
                        limpiarCamposActualizar();
                    } else {
                        JOptionPane.showMessageDialog(panelActualizarLibro, "Error: Libro no encontrado para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelActualizarLibro, "El año de publicación debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelActualizarLibro, "Ocurrió un error al actualizar el libro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panelActualizarLibro.add(btnActualizar);
        panelActualizarLibro.add(new JLabel("")); 
    }

    private void limpiarCamposActualizar() {
        txtActualizarId.setText("");
        txtActualizarTitulo.setText("");
        txtActualizarAutor.setText("");
        txtActualizarAnio.setText("");
        txtActualizarGenero.setText("");
    }

    private void crearPanelVerLibros() {
        panelVerLibros = new JPanel(new BorderLayout());
        panelVerLibros.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        areaListaLibros = new JTextArea();
        areaListaLibros.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaListaLibros);
        panelVerLibros.add(scrollPane, BorderLayout.CENTER);

        JButton btnRefrescar = new JButton("Refrescar Lista");
        btnRefrescar.addActionListener(e -> actualizarListaLibros());
        panelVerLibros.add(btnRefrescar, BorderLayout.SOUTH);
    }

    private void actualizarListaLibros() {
        String[] listaLibros = bibliotecario.obtenerListaLibros();
        areaListaLibros.setText(""); 
        if (listaLibros.length > 0) {
            for (String libroStr : listaLibros) {
                areaListaLibros.append(libroStr + "\n");
            }
        } else {
            areaListaLibros.append("No hay libros registrados.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BibliotecaApp().setVisible(true);
        });
    }
}