package hu.webarticum.siof.gui;

import java.io.IOException;

import hu.webarticum.siof.example.MultilineExample;
import hu.webarticum.siof.example.ParseValuesExample;
import hu.webarticum.siof.example.VerySimpleExample;

public class Main {

    public static void main(String[] args) throws IOException {
        new SiofGui(
            new VerySimpleExample(),
            new MultilineExample(),
            new ParseValuesExample()
        ).run();
    }

}
