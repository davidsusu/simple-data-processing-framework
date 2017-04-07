package hu.webarticum.sdpf.gui;

import java.io.IOException;

import hu.webarticum.sdpf.example.MultilineExample;
import hu.webarticum.sdpf.example.ParseValuesExample;
import hu.webarticum.sdpf.example.VerySimpleExample;
import hu.webarticum.sdpf.framework.TextDataProcessor;

public class Main {

    public static void main(String[] args) throws IOException {
        new SiofGui(
            new TextDataProcessor[] {
                new VerySimpleExample(),
                new MultilineExample(),
                new ParseValuesExample(),
            },
            null, "", null, "", null, "", "meld"
        ).run();
    }

}
