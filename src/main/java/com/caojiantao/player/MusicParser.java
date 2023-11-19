package com.caojiantao.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MusicParser {

    /**
     * 简谱名
     */
    public String name;
    /**
     * 音调，默认 C
     */
    private EMajor major = EMajor.C;
    /**
     * 每分钟节拍数 Beats pre Minutes
     */
    public int bpm = -1;
    /**
     * 每个四分音符分成多少份 Pulse Per Quartnote
     */
    public int ppq = -1;
    /**
     * 右手弹奏事件
     */
    public List<TickData> rightTickList = new ArrayList<>();
    /**
     * 左手弹奏事件
     */
    public List<TickData> leftTickList = new ArrayList<>();

    /**
     * 根据简谱路径，初始化各种数据
     */
    public void init(String filePath) {
        URL url = getClass().getClassLoader().getResource(filePath);
        assert url != null;
        try (InputStream is = url.openStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("L:")) {
                    parseLine(line, leftTickList);
                } else if (line.startsWith("R:")) {
                    parseLine(line, rightTickList);
                } else if (line.startsWith("M:")) {
                    parseMeta(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseLine(String line, List<TickData> tickList) {
        String[] beats = line.split("\\|");
        for (int i = 1; i < beats.length; i++) {
            String beat = beats[i];
            String[] ticks = beat.split(" ");
            for (String tick : ticks) {
                parseTick(tick, tickList);
            }
        }
    }

    private void parseMeta(String line) {
        line = line.replace("M:", "");
        String[] metas = line.split(" ");
        for (String meta : metas) {
            String[] split = meta.split("=");
            switch (split[0]) {
                case "1":
                    String s = split[1];
                    char c = s.charAt(0);
                    this.major = EMajor.valueOf(String.valueOf(c));
                    String str = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
                    String[] ss = str.split("/");
                    this.ppq = Integer.parseInt(ss[1]) / Integer.parseInt(ss[0]);
                    break;
                case "BPM":
                    this.bpm = Integer.parseInt(split[1]);
                    break;
                case "N":
                    this.name = split[1];
                    break;
                default:
                    break;
            }
        }
    }

    private void parseTick(String tick, List<TickData> tickList) {
        TickData tickData = new TickData();
        boolean up = true;
        for (int i = 0; i < tick.length(); i++) {
            char c = tick.charAt(i);
            switch (c) {
                // 不知道为什么 '·' 就不会走 case
                case 183:
                    int a = up ? 1 : -1;
                    tickData.area += a;
                    break;
                case '(':
                    int j = tick.indexOf(')');
                    String tickStr = tick.substring(i + 1, j);
                    tickData.tick = Integer.parseInt(tickStr);
                    i = j;
                    break;
                default:
                    up = false;
                    tickData.pitch = (c - '0');
                    break;
            }
        }
        transformPitchValue(tickData);
        tickList.add(tickData);
    }

    /**
     * 将简谱音高转换为数字大小
     */
    private void transformPitchValue(TickData tickData) {
        int pitch;
        switch (tickData.pitch) {
            case 1:
                pitch = major.getDo(tickData.area);
                break;
            case 2:
                pitch = major.getRe(tickData.area);
                break;
            case 3:
                pitch = major.getMi(tickData.area);
                break;
            case 4:
                pitch = major.getFa(tickData.area);
                break;
            case 5:
                pitch = major.getSol(tickData.area);
                break;
            case 6:
                pitch = major.getLa(tickData.area);
                break;
            case 7:
                pitch = major.getSi(tickData.area);
                break;
            default:
                return;
        }
        tickData.pitch = pitch;
    }
}
