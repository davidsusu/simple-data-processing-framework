package hu.webarticum.siof.gui;

import java.io.IOException;

import hu.webarticum.siof.example.ParseValuesExample;

public class Main {

    public static void main(String[] args) throws IOException {
        new SiofGui(new ParseValuesExample()).run(true);
    }

}
