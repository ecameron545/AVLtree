package test;

import impl.AVLBSTMap;
import impl.AVLVerify;

public class AVLBSTMTest extends MapStressTest {

    @Override
    protected void reset() {
        AVLBSTMap<String,String> tree = new AVLBSTMap<String,String>(true);
        assert(tree.getVs() instanceof AVLVerify);
        assert(tree.isVerifying());
        testMap = tree;
    }
    @Override
    protected void resetInteger() {
        AVLBSTMap<Integer,Integer> tree = new AVLBSTMap<Integer,Integer>(true);
        assert(tree.getVs() instanceof AVLVerify);
        assert(tree.isVerifying());
        testMapInt = tree;
        
    }

}
