package principal;

import View.VentanaCafeteria;
import Controller.CafeteriaControlador;
import Model.CafeteriaModelo;

public class Main {
    public static void main(String[] args) {
        CafeteriaModelo modelo = new CafeteriaModelo();
        VentanaCafeteria vista = new VentanaCafeteria();
        new CafeteriaControlador(modelo, vista);
    }
}