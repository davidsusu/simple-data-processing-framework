package hu.webarticum.siof.gui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import hu.webarticum.siof.example.Example;
import hu.webarticum.siof.example.ParseValuesExample;

public class Main {

    public static void main(String[] args) throws IOException {
        Example example = new ParseValuesExample();
        InputStream sampleInputStream = example.getSampleInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        example.solve(sampleInputStream, outputStream);
        
        System.out.println(outputStream.toString());
    }

}
