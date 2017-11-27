/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartas_21;

import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class BlackjackGUI {

    public static void main(String[] args) {
        int dinero;
        int apuestas = 0;
        boolean usuarioGano;
        String texto;
     
        dinero = 100;
        while (true) {
            texto = "\nUsted tiene: $" + dinero + "  para realizar apuestas";
            do {
                texto += "\nCuanto dinero desea apostar?(Ingrese 0 para finalizar)\n?";
                apuestas = Integer.parseInt(JOptionPane.showInputDialog(null, texto, "Bienvenido al juego de Blackjack", JOptionPane.INFORMATION_MESSAGE));
                if (apuestas < 0 || apuestas > dinero) {
                    JOptionPane.showMessageDialog(null, "\nSu respuesta debe estar entre 0 y " + dinero + " ");
                }
            } while (apuestas < 0 || apuestas > dinero);
            if (apuestas == 0) {
                break;
            }
            usuarioGano = playBlackjack();
            if (usuarioGano) {
                dinero = dinero + apuestas;
            } else {
                dinero = dinero - apuestas;
            }
            System.out.println();
            if (dinero == 0) {
                JOptionPane.showMessageDialog(null, "Parece que te has quedado sin dinero!!!");
                break;
            }
        }
        JOptionPane.showMessageDialog(null, "\nSales con $ " + dinero + " ");
    }//termina el metodo main

    /* Permita que el usuario juegue un juego de Blackjack, con la computadora como distribuidor.
     * @return true si el usuario gana el juego, falso si el usuario pierde.*/
    static boolean playBlackjack() {

        String aux = "";
        Cubierta cubierta = new Cubierta();//una nueva baraja de cartas para el juego
        Mano_21 manoDistribuidor = new Mano_21();//la mano de crupier
        Mano_21 manoUsuario = new Mano_21();//la mano del usuario
        // Baraja el mazo, reparte dos cartas a cada jugador. *
        cubierta.barajar();
        manoDistribuidor.agregarCarta(cubierta.tarjeta_oferta());
        manoDistribuidor.agregarCarta(cubierta.tarjeta_oferta());
        manoUsuario.agregarCarta(cubierta.tarjeta_oferta());
        manoUsuario.agregarCarta(cubierta.tarjeta_oferta());
        System.out.println();
        
        /*Verifica si uno de los jugadores tiene Blackjack (dos cartas que suman un total de 21).
         El jugador con Blackjack gana el juego. El distribuidor gana lazos.*/
        
        if (manoDistribuidor.getBlackjackValue() == 21) {
            aux = "\nEl distribuidor tiene la " + manoDistribuidor.getCarta(0) + " y la " + manoDistribuidor.getCarta(1) + "."
                    + "\nEl usuario tiene la " + manoUsuario.getCarta(0) + " y la" + manoUsuario.getCarta(1) + " . "
                    + "\nEl distribuidor tiene Blackjack. El distribuidor gana";
            JOptionPane.showMessageDialog(null, aux);
            return false;
        }
        if (manoUsuario.getBlackjackValue() == 21) {
            aux = "\nEl distribuidor tiene la " + manoDistribuidor.getCarta(0) + " y la " + manoDistribuidor.getCarta(1) + "."
                    + "\nEl usuario tiene la " + manoUsuario.getCarta(0) + " y la" + manoUsuario.getCarta(1) + " . "
                    + "\nUsted tiene Blackjack. !!FELICEDADES!! Usted gana!!";
            JOptionPane.showMessageDialog(null, aux);
            return true;
        
        }
        /*Si ninguno de los jugadores tiene Blackjack, juega el juego. Primero el usuario
         tiene la oportunidad de robar cartas (es decir, de "golpear"). El bucle while termina
         cuando el usuario elige "Stand". Si el usuario supera los 21,
         el usuario pierde inmediatamente.*/
        
        while (true) {
            //Mostrar tarjetas de usuario, y dejar que el usuario decida golpear o pararse. 
            aux = "";
            for (int i = 0; i < manoUsuario.getRecuentoCarta(); i++) {
                aux += "\n" + manoUsuario.getCarta(i);
            }
            aux = aux + "\nSu total es " + manoUsuario.getBlackjackValue()
                    + "\ndistribuidor está mostrando el " + manoDistribuidor.getCarta(0);
            JOptionPane.showMessageDialog(null, aux, "Tus cartas son", JOptionPane.INFORMATION_MESSAGE);
            char accionUsuario = 'H'; // Respuesta del usuario, 'H' o 'S'
            do {
                String car = (JOptionPane.showInputDialog(null, "Jugar (G) o Levantarse (L) ?", "Ingrese una opción", JOptionPane.INFORMATION_MESSAGE));
                accionUsuario = car.charAt(0);
                accionUsuario = Character.toUpperCase(accionUsuario);
                if (accionUsuario != 'G' && accionUsuario != 'L') {
                    JOptionPane.showMessageDialog(null, "\nPor favor responda G o L:  ", "Elección equivocada", JOptionPane.INFORMATION_MESSAGE);
                }
            } while (accionUsuario != 'G' && accionUsuario != 'L');
            /*Si el usuario tiene éxito, el usuario obtiene una tarjeta. Si el usuario está parado,
             el bucle termina (y es el turno del crupier de robar cartas)*/
            if (accionUsuario == 'L') {
                //Finaliza el ciclo; el usuario ha terminado de tomar cartas.
                break;
            } else {
                    //userAction es 'G'. Dale una tarjeta al usuario.  
                // Si el usuario supera los 21, el usuario pierde.
                Carta nuevaCarta = cubierta.tarjeta_oferta();
                manoUsuario.agregarCarta(nuevaCarta);
                aux = "\nSu tarjeta es la " + nuevaCarta
                        + "\nSu total es ahora " + manoUsuario.getBlackjackValue();
                JOptionPane.showMessageDialog(null, aux, "El usuario acierta", JOptionPane.INFORMATION_MESSAGE);
                if (manoUsuario.getBlackjackValue() > 21) {
                    aux = "\nOtra tarjeta del distribuidor fue el " + manoDistribuidor.getCarta(1);
                    JOptionPane.showMessageDialog(null, aux, "Has fallado al pasar de 21. Usted Pierde", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
            }
        }//fin del While
         
        /* Si llegamos a este punto, el usuario tiene valor de 21 o menos. Ahora es
         la oportunidad del distribuidor para dibujar. El distribuidor roba cartas hasta que el crupier
         el total es> 16. Si el crupier supera los 21, el crupier pierde.*/
        
        aux = "\nLas tarjetas del distribuidor son"
                + "\n" + manoDistribuidor.getCarta(0)
                + "\n" + manoDistribuidor.getCarta(1);
        JOptionPane.showMessageDialog(null, aux, "Usuario parado", JOptionPane.INFORMATION_MESSAGE);
        while (manoDistribuidor.getBlackjackValue() <= 16) {
            Carta nuevaCarta = cubierta.tarjeta_oferta();
            aux = "\nEl distribuidor juega y obtiene la " + nuevaCarta;
            JOptionPane.showMessageDialog(null, aux, "Jack Black", JOptionPane.INFORMATION_MESSAGE);
            manoDistribuidor.agregarCarta(nuevaCarta);
            if (manoDistribuidor.getBlackjackValue() > 21) {
                aux = "\nDistribuidor detenido por pasar de 21. Usted gana";
                JOptionPane.showMessageDialog(null, aux, "Jack Black", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        aux = "\nEl total del distribuidor es " + manoDistribuidor.getBlackjackValue();
        JOptionPane.showMessageDialog(null, aux, "Jack Black", JOptionPane.INFORMATION_MESSAGE);
        /* Si llegamos a este punto, ambos jugadores tienen 21 o menos. Nosotros
         puede determinar el ganador comparando los valores de sus manos*/
        if (manoDistribuidor.getBlackjackValue() == manoUsuario.getBlackjackValue()) {
            aux = "\nEl distribuidor gana en un empate. Pierdes";
            JOptionPane.showMessageDialog(null, aux, "Jack Black", JOptionPane.INFORMATION_MESSAGE);
            return false;
        } else if (manoDistribuidor.getBlackjackValue() > manoUsuario.getBlackjackValue()) {
            aux = "\nEl distribuidor gana " + manoDistribuidor.getBlackjackValue() + " tu puntuacion " + manoUsuario.getBlackjackValue() + " . ";
            JOptionPane.showMessageDialog(null, aux, "Jack Black", JOptionPane.INFORMATION_MESSAGE);
            return false;
        } else {
            aux = "\nUsted gana " + manoUsuario.getBlackjackValue() + " puntuacion del ganador " + manoDistribuidor + " . ";
            JOptionPane.showMessageDialog(null, aux, "Jack Black", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
    }
}
