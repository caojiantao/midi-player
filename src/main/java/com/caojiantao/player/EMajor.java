package com.caojiantao.player;

/**
 * do re mi fa sol la si
 */
public enum EMajor {

    C(60),
    D(62),
    ;

    private int value;

    EMajor(int value) {
        this.value = value;
    }

    public int getDo(int area) {
        return value + 12 * area;
    }

    public int getDoUp(int area) {
        return value + 1 + 12 * area;
    }

    public int getRe(int area) {
        return value + 2 + 12 * area;
    }

    public int getReUp(int area) {
        return value + 3 + 12 * area;
    }

    public int getMi(int area) {
        return value + 4 + 12 * area;
    }

    public int getFa(int area) {
        return value + 5 + 12 * area;
    }

    public int getFaUp(int area) {
        return value + 6 + 12 * area;
    }

    public int getSol(int area) {
        return value + 7 + 12 * area;
    }

    public int getSolUp(int area) {
        return value + 8 + 12 * area;
    }

    public int getLa(int area) {
        return value + 9 + 12 * area;
    }

    public int getLaUp(int area) {
        return value + 10 + 12 * area;
    }

    public int getSi(int area) {
        return value + 11 + 12 * area;
    }
}
