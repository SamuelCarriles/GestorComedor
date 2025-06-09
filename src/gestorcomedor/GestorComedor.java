/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestorcomedor;
import guilayer.*;
import logiclayer.*;

/**
 *
 * @author samue
 */
public class GestorComedor {

    public static void main(String[] args) throws Exception {
        Sistema.iniciar();
        Login loginWindow = new Login();
        loginWindow.setVisible(true);
        loginWindow.setResizable(false);
        loginWindow.setLocationRelativeTo(null);
          
    }
    
}
