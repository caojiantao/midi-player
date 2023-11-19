## Midi Player

将简谱按一定规则转换为 TXT 文件，通过解析成 MIDI 的 TICK EVENT 进行播放。

## 转换规则

文件示例；

```text
M:1=C(4/4) BPM=96 N=月半小夜曲

## 1
R:|··1 0(2) ·3(2) ·3(2) ·3(2) ··1(2) ·7(2)|
L:|6··(2) 3·(2) 6·(2) 3·(2) 1(2) 3·(2) 6·(2) 3·(2)|
```

文件头规范；

- 文件头以 **M:** 开头；
- **1=C(4/4)** 代表 C 大调，四四拍；
- **BPM** 每分钟多少拍；
- **#** 注释行，这里用来表示第几小节；

弹奏规范；

- **L:R:** 左右手弹奏节奏；
- **·** 放在前面是升调，后面是降调；
- **1** 代表 do，**2** 代表 re，以此内推；
- **(2)** 代表 1/2 拍，**(4)** 代表 1/4 拍，默认是 1 拍；
- **|** 是小节区分符，不影响弹奏；

## 快速开始

将要弹奏的曲谱例如“月半小夜曲”放在 resources 目录下；

```java
public class Main {

    public static void main(String[] args) throws Exception {
        String path = "月半小夜曲.txt";
        MidiPlayer player = new MidiPlayer(path);
        player.start();
    }
}
```


