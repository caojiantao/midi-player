package com.caojiantao.player;

public class Main {

    public static void main(String[] args) throws Exception {
        String path = "月半小夜曲.txt";
        MidiPlayer player = new MidiPlayer(path);
        player.start();
    }
}
