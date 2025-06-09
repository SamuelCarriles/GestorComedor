package logiclayer;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class Sistema implements Serializable {

    private static List<Usuario> usuarios = new ArrayList<>();
    private static Usuario operador = null;
    private static UsuarioRoot root = new UsuarioRoot("rooter", "manager", "rooter");
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void iniciar() throws IOException, ClassNotFoundException {
        File archivo = new File("database.bin");
        if (archivo.exists()) {
            cargarDatos();
        }
        scheduler.scheduleAtFixedRate(() -> {
            CompletableFuture.runAsync(() -> {
                try {
                    exportarDatos();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }, 0, 1, TimeUnit.MINUTES);

        //Para detener el autoguardado se pone
        //scheduler.shutdown();
    }

    public static void detener() throws IOException {
        scheduler.shutdown();
        exportarDatos();
    }

    public static boolean acceder(String nombre, String pass) {
        if (root.getNombre().equals(nombre)) {
            if (root.getPassword().equals(pass)) {
                JOptionPane.showMessageDialog(
                        null,
                        "¡Bienvenido al sistema " + root.getNombre() + "!",
                        "Bienvenida al Sistema",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Contraseña inválida. Por favor, vuelva a intentarlo...",
                        "Contraseña Inválida",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        for (Usuario u : usuarios) {
            if ((u.getNombre().equals(nombre)) && (u.getPassword().equals(pass))) {
                operador = u;
                JOptionPane.showMessageDialog(
                        null,
                        "¡Bienvenido al sistema " + operador.getNombre() + "!",
                        "Bienvenida al Sistema",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else if ((u.getNombre().equals(nombre)) && (!u.getPassword().equals(pass))) {
                JOptionPane.showMessageDialog(
                        null,
                        "Contraseña inválida. Por favor, vuelva a intentarlo...",
                        "Contraseña Inválida",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        JOptionPane.showMessageDialog(
                null,
                "El usuario " + nombre + " no existe.",
                "Usuario No válido",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public static void cerrarSesion() {
        operador = null;
    }

    private static boolean isAdmin(Usuario user) {
        if (user instanceof UsuarioRoot) {
            return true;
        }
        return (user.getRol().equals("administrador"));
    }

    public static boolean addUsuario(Usuario operador, Usuario usuario) {
        for (Usuario u : usuarios) {
            if (usuario.someEqual(u)) {
                String element = usuario.whichEqual(u);
                JOptionPane.showMessageDialog(
                        null,
                        "Fallo al registrar el usuario de nombre " + usuario.getNombre() + ". Existe otro usuario en el sistema con un " + element + " idéntico.",
                        "Operación Fallida",
                        JOptionPane.WARNING_MESSAGE
                );
                return false;
            }

        }
        if ((usuarios.size() < 1000)) {
            usuarios.add(usuario);
            return true;
        } else if (usuarios.size() == 1000) {
            JOptionPane.showMessageDialog(
                    null,
                    "No se pueden añadir más usuarios. El sistema solo soporta mil usuarios.",
                    "Operación Fallida",
                    JOptionPane.WARNING_MESSAGE
            );
        }
        return false;
    }

    public static boolean deleteUsuario(Usuario operador, String solapin) {
        for (Usuario usuario : usuarios) {
            if (usuario.getSolapin().equals(solapin)) {
                usuarios.remove(usuario);
                return true;
            }

        }
        return false;
    }

    public static boolean marcarUsuario(Usuario operador, String solapin) {
        if (!operador.getRol().equals("cliente")) {
            for (Usuario usuario : usuarios) {
                if (usuario.getSolapin().equals(solapin) && usuario.marcado != true) {
                    usuario.marcado = true;
                    JOptionPane.showMessageDialog(null, "Usuario " + usuario.getNombre() + " marcado.", "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean desmarcarUsuarios() {
        usuarios.forEach(usuario -> {
            usuario.marcado = false;
        });
        return false;
    }

    public static boolean cambiarPass(Usuario operador, String solapin, String nuevaPass) {
        for (Usuario usuario : usuarios) {
            if (usuario.getSolapin().equals(solapin)) {
                usuario.setPassword(nuevaPass);
                JOptionPane.showMessageDialog(null, "Contraseña del usuario " + usuario.getNombre() + " cambiada exitosamente.", "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        return false;
    }

    public static void exportarDatos() throws IOException {
        desmarcarUsuarios();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.bin"));
        try {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar el archivo: " + e.getMessage(),
                    "Error", // Título del diálogo
                    JOptionPane.ERROR_MESSAGE // Tipo de mensaje (ERROR_MESSAGE, WARNING_MESSAGE, etc.)
            );
        } finally {
            oos.close();
        }
    }

    public static void cargarDatos() throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("database.bin"));
            usuarios = (List<Usuario>) ois.readObject();
            ois.close();
        } catch (IOException e) {
            // Diálogo de error con icono de alerta roja
            JOptionPane.showMessageDialog(
                    null, // Componente padre (null = centro de la pantalla)
                    "Error al leer el archivo: " + e.getMessage(),
                    "Error", // Título del diálogo
                    JOptionPane.ERROR_MESSAGE // Tipo de mensaje (ERROR_MESSAGE, WARNING_MESSAGE, etc.)
            );
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Clase no encontrada: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void listaFaltantes(Usuario operador) throws IOException {
        if (isAdmin(operador)) {
            String archivo = "Lista de Faltantes.csv";
            String ruta = System.getProperty("user.home") + "/Documents/" + archivo;
            try (OutputStreamWriter editor = new OutputStreamWriter(new FileOutputStream(ruta), StandardCharsets.UTF_8)) {
                String linea;
                editor.write('\uFEFF');
                editor.append("Nombre y Apellidos,Número de Solapín\n");
                for (Usuario u : usuarios) {
                    if (!u.marcado) {
                        linea = u.getNombre() + "," + u.getSolapin() + "\n";
                        editor.append(linea);

                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Lista de usuarios faltantes exportada con éxito. El documento llamado \"Lista de Faltantes.csv\" se encuentra en la siguiente ruta: " + ruta, "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    public static boolean cambiarPassword(Usuario usuario, String pass) {
        for (Usuario u : usuarios) {
            if (u.equals(usuario)) {
                u.setPassword(pass);
                return true;
            }
        }
        return false;
    }

    public static Usuario getOperador() {
        if (operador == null) {
            return root;
        } else {
            return operador;
        }
    }
}
