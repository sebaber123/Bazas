package com.example.bazas.model;

public class Round {
    private int quantity_of_cards;
    private boolean visibility;

    public Round(int quantity_of_cards) {
        this.quantity_of_cards = quantity_of_cards;
        visibility = true;
    }

    public int getQuantity_of_cards() {
        return quantity_of_cards;
    }

    public void setQuantity_of_cards(int quantity_of_cards) {
        this.quantity_of_cards = quantity_of_cards;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
