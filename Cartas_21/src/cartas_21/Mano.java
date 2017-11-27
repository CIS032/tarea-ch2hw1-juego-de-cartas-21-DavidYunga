/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartas_21;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Mano {
    private ArrayList<Carta> mano;   // The cards in the hand.
    public Mano() {
        mano = new ArrayList<Carta>();
    }

    /**
     * Remove all cards from the hand, leaving it empty.
     */
    public void clear() {
        mano.clear();
    }

    /**
     * Add a card to the hand.  It is added at the end of the current hand.
     * @param c the non-null card to be added.
     * @throws NullPointerException if the parameter c is null.
     */
    public void agregarCarta(Carta c) {
        if (c == null)
            throw new NullPointerException("No se puede agregar una carta nula a una mano.");
        mano.add(c);
    }

    /**
     * Remove a card from the hand, if present.
     * @param c the card to be removed.  If c is null or if the card is not in 
     * the hand, then nothing is done.
     */
    public void eliminarCarta(Carta c) {
        mano.remove(c);
    }

    /**
     * Remove the card in a specified position from the hand.
     * @param position the position of the card that is to be removed, where
     * positions are starting from zero.
     * @throws IllegalArgumentException if the position does not exist in
     * the hand, that is if the position is less than 0 or greater than
     * or equal to the number of cards in the hand.
     */
    public void eliminarCarta(int posision) {
        if (posision < 0 || posision >= mano.size())
            throw new IllegalArgumentException("Position does not exist in hand: " + posision);
        mano.remove(posision);
    }

    /**
     * Returns the number of cards in the hand.
     */
    public int getRecuentoCarta() {
        return mano.size();
    }

    /**
     * Gets the card in a specified position in the hand.  (Note that this card
     * is not removed from the hand!)
     * @param position the position of the card that is to be returned
     * @throws IllegalArgumentException if position does not exist in the hand
     */
    public Carta getCarta(int position) {
        if (position < 0 || position >= mano.size())
            throw new IllegalArgumentException("La posición no existe en la mano: " + position);
        return mano.get(position);
    }

    /**
     * Sorts the cards in the hand so that cards of the same suit are
     * grouped together, and within a suit the cards are sorted by value.
     * Note that aces are considered to have the lowest value, 1.
     */
    public void sortBySuit() {
        ArrayList<Carta> newMano = new ArrayList<>();
        while (mano.size() > 0) {
            int pos = 0;  // Position of minimal card.
            Carta c = mano.get(0);  // Minimal card.
            for (int i = 1; i < mano.size(); i++) {
                Carta c1 = mano.get(i);
                if ( c1.getSuit() < c.getSuit() ||
                        (c1.getSuit() == c.getSuit() && c1.getValue() < c.getValue()) ) {
                    pos = i;
                    c = c1;
                }
            }
            mano.remove(pos);
            newMano.add(c);
        }
        mano = newMano;
    }

    /**
     * Sorts the cards in the hand so that cards of the same value are
     * grouped together.  Cards with the same value are sorted by suit.
     * Note that aces are considered to have the lowest value, 1.
     */
    public void sortByValue() {
        ArrayList<Carta> newMano = new ArrayList<Carta>();
        while (mano.size() > 0) {
            int pos = 0;  // Position of minimal card.
            Carta c = mano.get(0);  // Minimal card.
            for (int i = 1; i < mano.size(); i++) {
                Carta c1 = mano.get(i);
                if ( c1.getValue() < c.getValue() ||
                        (c1.getValue() == c.getValue() && c1.getSuit() < c.getSuit()) ) {
                    pos = i;
                    c = c1;
                }
            }
            mano.remove(pos);
            newMano.add(c);
        }
        mano = newMano;
    }
}
