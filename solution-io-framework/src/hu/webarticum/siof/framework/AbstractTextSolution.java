package hu.webarticum.siof.framework;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract class AbstractTextSolution implements TextSolution {
    
    static public final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    
    private final Charset inputCharset;
    private final Charset outputCharset;
    
    protected AbstractTextSolution() {
        this(DEFAULT_CHARSET);
    }

    protected AbstractTextSolution(Charset charset) {
        this(charset, charset);
    }
    
    protected AbstractTextSolution(Charset inputCharset, Charset outputCharset) {
        this.inputCharset = inputCharset;
        this.outputCharset = outputCharset;
    }
    
    public void solve(InputStream inputStream, OutputStream outputStream) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, outputCharset)) {
            solve(new InputStreamReader(inputStream, inputCharset), writer);
        }
    }

}
