/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartas_21;

import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class Cartas_21 {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        
        int dinero;         
        int bet;          
        boolean ganaUsuario; 
        
        System.out.println("Bienvenido al juego de blackjack.");
        System.out.println();

        dinero = 100;  // User starts with $100.

        while (true) {
            System.out.println("tienes " + dinero + " dolares.");
            do {
                System.out.println("¿Cuántos dólares quieres apostar? (Ingrese 0 para finalizar)");
                System.out.print("? \n");
                bet = Integer.parseInt(entrada.nextLine());
                if (bet < 0 || bet > dinero) {
                    System.out.println("Tu respuesta debe estar entre 0 y" + dinero + '.');
                }
            } while (bet < 0 || bet > dinero);
            if (bet == 0) {
                break;
            }
            ganaUsuario = playBlackjack();
            if (ganaUsuario) {
                dinero = dinero + bet;
            } else {
                dinero = dinero - bet;
            }
            System.out.println();
            if (dinero == 0) {
                System.out.println("¡Parece que te has quedado sin dinero!");
                break;
            }
        }

        System.out.println();
        System.out.println("Te vas con $" + dinero + '.');

    } // end main()
    static boolean playBlackjack() {

        Cubierta cubierta;     // Una baraja de cartas. Una nueva baraja para cada juego.
        Mano_21 dealerHand;   // La mano del crupier
        Mano_21 userHand;     // La mano del usuario.

        cubierta = new Cubierta();
        dealerHand = new Mano_21();
        userHand = new Mano_21();

        /*  Baraja el mazo, luego reparte dos cartas a cada jugador.*/
        cubierta.barajar();
        dealerHand.agregarCarta(cubierta.tarjeta_oferta());
        dealerHand.agregarCarta(cubierta.tarjeta_oferta());
        userHand.agregarCarta(cubierta.tarjeta_oferta());
        userHand.agregarCarta(cubierta.tarjeta_oferta());

        System.out.println();
        System.out.println();

        /* Verifica si uno de los jugadores tiene Blackjack (dos cartas que suman un total de 21).
         El jugador con Blackjack gana el juego. El distribuidor gana lazos.
         */
        if (dealerHand.getBlackjackValue() == 21) {
            System.out.println("El distribuidor tiene el " + dealerHand.getCarta(0)
                    + " y el " + dealerHand.getCarta(1) + ".");
            System.out.println("El usuario tiene el " + userHand.getCarta(0)
                    + " y el " + userHand.getCarta(1) + ".");
            System.out.println();
            System.out.println("El distribuidor tiene Blackjack. El distribuidor gana");
            return false;
        }

        if (userHand.getBlackjackValue() == 21) {
            System.out.println("El distribuidor tiene el " + dealerHand.getCarta(0)
                    + " y el " + dealerHand.getCarta(1) + ".");
            System.out.println("El usuario tiene el " + userHand.getCarta(0)
                    + " y el " + userHand.getCarta(1) + ".");
            System.out.println();
            System.out.println("Tienes Blackjack. Tú ganas.");
            return true;
        }

        /*  If ninguno de los jugadores tiene Blackjack, juega el juego. Primero el usuario
         tiene la oportunidad de robar cartas (es decir, de "golpear"). El bucle while termina
         cuando el usuario elige "Stand". Si el usuario supera los 21,
         el usuario pierde inmediatamente.
         */
        while (true) {

           
            System.out.println();
            System.out.println();
            System.out.println("Tus cartas son:");
            for (int i = 0; i < userHand.getRecuentoCarta(); i++) {
                System.out.println("    " + userHand.getCarta(i));
            }
            System.out.println("Tu total es" + userHand.getBlackjackValue());
            System.out.println();
            System.out.println("El concesionario muestra el " + dealerHand.getCarta(0));
            System.out.println();
            System.out.print("Golpear (H) or Estar (S)? ");
            char userAction;  //Respuesta del usuario, 'H' o 'S'.
            do {
                  Scanner entrada = new Scanner(System.in);
                userAction = entrada.nextLine().charAt(0);
                 userAction=Character.toUpperCase(userAction);
                if (userAction != 'H' && userAction != 'S') {
                    System.out.print("Por favor responda H o S:  ");
                }
            } while (userAction != 'H' && userAction != 'S');

            /* Si el usuario tiene éxito, el usuario obtiene una tarjeta. Si el usuario está parado,
             el bucle termina (y es el turno del crupier de robar cartas).
             */
            if (userAction == 'S') {
                // Loop ends; el usuario ha terminado de tomar cartas.
                break;
            } else { 
                Carta newCard = cubierta.tarjeta_oferta();
                userHand.agregarCarta(newCard);
                System.out.println();
                System.out.println("Aciertos de usuario.");
                System.out.println("Tu tarjeta es " + newCard);
                System.out.println("Tu total es ahora " + userHand.getBlackjackValue());
                if (userHand.getBlackjackValue() > 21) {
                    System.out.println();
                    System.out.println("Has perdido yendo por encima de 21. Pierdes.");
                    System.out.println("La otra tarjeta del distribuidor era la "
                            + dealerHand.getCarta(1));
                    return false;
                }
            }

        } // end while loop

        /* Si llegamos a este punto, el usuario tiene Stood con 21 o menos. Ahora es
         la oportunidad del distribuidor para dibujar. El distribuidor roba cartas hasta que el crupier
         el total es> 16. Si el crupier supera los 21, el crupier pierde.
         */
        System.out.println();
        System.out.println("Usuario parado.");
        System.out.println("Las tarjetas de los vendedores son");
        System.out.println("    " + dealerHand.getCarta(0));
        System.out.println("    " + dealerHand.getCarta(1));
        while (dealerHand.getBlackjackValue() <= 16) {
            Carta newCarta = cubierta.tarjeta_oferta();
            System.out.println("El distribuidor golpea y obtiene el " + newCarta);
            dealerHand.agregarCarta(newCarta);
            if (dealerHand.getBlackjackValue() > 21) {
                System.out.println();
                System.out.println("Distribuidor detenido por pasar de 21. Usted gana.");
                return true;
            }
        }
        System.out.println("El total del distribuidor es " + dealerHand.getBlackjackValue());

        /* Si llegamos a este punto, ambos jugadores tienen 21 o menos. Nosotros
         puede determinar el ganador comparando los valores de sus manos. */
        System.out.println();
        if (dealerHand.getBlackjackValue() == userHand.getBlackjackValue()) {
            System.out.println("El distribuidor gana en un empate. Tú pierdes.");
            return false;
        } else if (dealerHand.getBlackjackValue() > userHand.getBlackjackValue()) {
            System.out.println("El distribuidor gana, " + dealerHand.getBlackjackValue()
                    + "punto a" + userHand.getBlackjackValue() + ".");
            return false;
        } else {
            System.out.println("Tú ganas, " + userHand.getBlackjackValue()
                    + " puntos a " + dealerHand.getBlackjackValue() + ".");
            return true;
        }

    }  // end playBlackjack()

} // end class Blackjack
