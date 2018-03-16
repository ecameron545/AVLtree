package test;

import impl.BasicRecursiveBSTMap;

public class BRBSTMTest extends MapStressTest {

    protected void reset() {
        testMap = new BasicRecursiveBSTMap<String, String>(false);
    }

    @Override
    protected void resetInteger() {
        testMapInt = new BasicRecursiveBSTMap<Integer, Integer>(false);
        
    }

}
