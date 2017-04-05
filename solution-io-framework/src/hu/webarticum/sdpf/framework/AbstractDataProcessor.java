package hu.webarticum.sdpf.framework;

public abstract class AbstractDataProcessor implements DataProcessor {
    
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    
}
