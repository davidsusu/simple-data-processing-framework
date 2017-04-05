package hu.webarticum.sdpf.gui;

import java.io.IOException;

import hu.webarticum.sdpf.example.MultilineExample;
import hu.webarticum.sdpf.example.ParseValuesExample;
import hu.webarticum.sdpf.example.VerySimpleExample;

public class Main {

    public static void main(String[] args) throws IOException {
        new SiofGui(
            new VerySimpleExample(),
            new MultilineExample(),
            new ParseValuesExample()
        ).run();
    }

}
