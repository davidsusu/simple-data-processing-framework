# simple-data-processing-framework

A simple framework for easy line-by-line data processing.


## Introduction ##

Main interface of this framework is `DataProcessor`. Data processors are input-output machines which read input from same `InputStream` or `Reader` and write output to an `OutputStream` or a `Writer`. The framework contains some abstract classes for the most common scenarios (`AbstractLineByLineDataProcessor`, `AbstractLinePatternDataProcessor` etc.).

Simplest usage of a data processor is to read data from standard input and write to standard output:

```java
new YourDataProcessor().process(System.in, System.out);
```

Also, this is the way how you can "standardize" your solution for Google Code Jam (see https://code.google.com/codejam/resources/quickstart-guide).


## Process line by line ##

Simplest way to process a text file is to handle each line separately. Extend `AbstractLineByLineDataProcessor` for this:

```java
public class AddLineNumbers extends AbstractLineByLineDataProcessor {
    
    @Override
    protected String processLine(int lineIndex, String inputLine) {
        return (lineIndex + 1) + ": " + inputLine;
    }

}
```

This data processor will insert line number before each line:

```java
String input = "some line\nan other line\nthird line";
StringWriter outputWriter = new StringWriter();
new AddLineNumbers().process(new StringReader(input), outputWriter);;
System.out.println(outputWriter.toString());
```

Output:

```
1: some line
2: an other line
3: third line
```

When your input and output are `String`, no need to use any reader and writer, because `StringDataProcessorWrapper` performs the work with the boilerplate code in the background. This is especially useful for testing.

So the latest code snippet could be replaced with this:

```java
String input = "some line\nan other line\nthird line";
System.out.println(new StringDataProcessorWrapper(new AddLineNumbers()).process(input));
```

This produces exactly the same output.


## Process header and multiline input items ##

TODO


## Parse numeric and other values ##

TODO

## Process binary file ##

You can process binary files too:

```java
class DigestDataProcessor extends AbstractDataProcessor {

    private final String algorithm;

    public DigestDataProcessor(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public void process(InputStream inputStream, OutputStream outputStream) throws IOException {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(algorithm + " not supported!", e);
        }

        DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest);
        while (digestInputStream.read(new byte[1024]) >= 0);
        byte[] bytes = messageDigest.digest();

        char[] characters = new char[bytes.length * 2];
        char[] charArray = "0123456789abcdef".toCharArray();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            int byteValue = b & 0xFF;
            characters[i * 2] = charArray[byteValue >>> 4];
            characters[i * 2 + 1] = charArray[byteValue & 0x0F];
        }

        outputStream.write(new String(characters).getBytes(StandardCharsets.ISO_8859_1));
    }

}
```

For example, when you need the SHA1-sum of a file:

```java
ByteArrayOutputStream out = new ByteArrayOutputStream();
new DigestDataProcessor("SHA1").process(new FileInputStream("/path/to/a-file"), out);
System.out.println(out.toString());
```

## Using the GUI ##

TODO
