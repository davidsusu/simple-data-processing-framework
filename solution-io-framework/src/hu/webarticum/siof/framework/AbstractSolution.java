package hu.webarticum.siof.framework;

public abstract class AbstractSolution implements Solution {
    
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    
}
