package com.caojiantao.player;

public class TickData {
    /**
     * 升降八度
     */
    public int area = 0;
    /**
     * 音高，1~7 do~si
     */
    public int pitch;
    /**
     * 时长
     */
    public int tick = 1;

    @Override
    public String toString() {
        return "{" + area + "," + pitch + "," + tick + "}";
    }
}
