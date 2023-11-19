package com.caojiantao.player;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MidiReceiver implements Receiver {

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage) message;

            if (shortMessage.getCommand() == ShortMessage.NOTE_ON) {
                int note = shortMessage.getData1();
                int velocity = shortMessage.getData2();

                System.out.println("NOTE_ON Event - Note: " + note + ", Velocity: " + velocity);
            }
        }
    }

    @Override
    public void close() {

    }
}
