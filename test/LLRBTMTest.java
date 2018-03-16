package test;

import impl.LLRBVerify;
import impl.LLRedBlackTreeMap;

public class LLRBTMTest extends MapStressTest {

    protected void reset() {
        LLRedBlackTreeMap<String, String> tree = new LLRedBlackTreeMap<String, String>(true);
        assert(tree.getVs() instanceof LLRBVerify);
        assert(tree.isVerifying());
        testMap = tree;
        
    }

    @Override
    protected void resetInteger() {
        LLRedBlackTreeMap<Integer, Integer> tree = new LLRedBlackTreeMap<Integer, Integer>(true);
        assert(tree.getVs() instanceof LLRBVerify);
        assert(tree.isVerifying());
        testMapInt = tree;
        
    }

}
