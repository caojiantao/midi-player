package com.caojiantao.player;

import javax.sound.midi.*;
import java.util.List;

public class MidiPlayer {

    private final Sequencer sequencer = MidiSystem.getSequencer();

    public MidiPlayer(String path) throws Exception{
        int scale = 4;

        MusicParser parser = new MusicParser();

        parser.init(path);
        System.out.println("简谱解析完成..." + parser.name);

        // 注：ppq 不太懂啥意思，先这样能跑就行~
        Sequence sequence = new Sequence(Sequence.PPQ, parser.ppq * scale);
        sequencer.setSequence(sequence);
        Track track = sequence.createTrack();

        addTickEvent(scale, track, parser.leftTickList);
        addTickEvent(scale, track, parser.rightTickList);

        sequencer.open();

        sequencer.setTempoInBPM(parser.bpm);

        sequencer.getTransmitter().setReceiver(new MidiReceiver());
    }

    private void addTickEvent(int scale, Track track, List<TickData> tickList) throws InvalidMidiDataException {
        int currentTick = 0;
        for (TickData tickData : tickList) {
            MidiMessage message = new ShortMessage(
                    ShortMessage.NOTE_ON,
                    /*钢琴*/6,
                    /*音调*/tickData.pitch,
                    /*音量*/127
            );
            MidiEvent event = new MidiEvent(message, currentTick);
            currentTick += (1.0 / tickData.tick * scale);
            if (tickData.pitch != 0) {
                track.add(event);
            }
        }
    }

    public void start() throws Exception {
        sequencer.start();
        System.out.println("开始播放...");

        // 等待播放完成
        while (sequencer.isRunning()) {
            Thread.sleep(1000); // 每秒检查一次
        }

        sequencer.close();
    }
}
