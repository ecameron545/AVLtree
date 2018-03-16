package test;

import impl.TRBVerify;
import impl.TraditionalRedBlackTreeMap;

public class TRBBSTMTest extends MapStressTest {

    protected void reset() {
        TraditionalRedBlackTreeMap<String,String> tree = new TraditionalRedBlackTreeMap<String, String>(true);
        assert(tree.getVs() instanceof TRBVerify);
        assert(tree.isVerifying());
        testMap = tree;

    }

    @Override
    protected void resetInteger() {
        TraditionalRedBlackTreeMap<Integer,Integer> tree = new TraditionalRedBlackTreeMap<Integer, Integer>(true);
        assert(tree.getVs() instanceof TRBVerify);
        assert(tree.isVerifying());
        testMapInt = tree;
    }

}
