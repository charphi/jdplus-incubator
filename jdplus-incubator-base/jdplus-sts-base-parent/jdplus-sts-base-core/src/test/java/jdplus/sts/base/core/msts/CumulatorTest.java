/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdplus.sts.base.core.msts;

import tck.demetra.data.Data;
import jdplus.toolkit.base.api.data.DoubleSeq;
import jdplus.toolkit.base.api.math.functions.Optimizer;
import jdplus.toolkit.base.api.ssf.SsfInitialization;
import jdplus.toolkit.base.core.data.DataBlock;
import jdplus.toolkit.base.core.math.matrices.FastMatrix;
import jdplus.toolkit.base.core.ssf.StateStorage;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Jean Palate
 */
public class CumulatorTest {
 
    public CumulatorTest() {
    }

    
    @Test
    public void testChowLin() {
        int m = Data.PCRA.length;
        int n = Data.IND_PCR.length;
        FastMatrix x = FastMatrix.make(n, 2);
        x.column(0).copyFrom(Data.IND_PCR, 0);
        //x.column(0).div(100);
        x.column(1).set(1);
        
        FastMatrix y = FastMatrix.make(n, 1);
        DataBlock edata = y.column(0);
        edata.set(Double.NaN);
        edata.extract(3, m, 4).copyFrom(Data.PCRA, 0);
        CompositeModel model = new CompositeModel();
        
//        StateItem ar = AtomicModels.localLevel("ar", 1, true, Double.NaN);
        StateItem ar = AtomicModels.ar("ar", new double[]{.96}, false, 1, true, 0, 0);
        StateItem reg=AtomicModels.timeVaryingRegression("reg", x, 1, true);
//        StateItem reg=AtomicModels.regression("reg", x);
        StateItem c=DerivedModels.aggregation("agg", reg, ar);
        StateItem disagg= DerivedModels.cumulator("disagg", c, 4, 0);
        
        model.add(disagg);
        ModelEquation eq=new ModelEquation("eq", 0, true);
        eq.add("disagg", disagg.defaultLoading(0));
        model.add(eq);
        
        CompositeModelEstimation rslt = model.estimate(y, false, true, SsfInitialization.Augmented, Optimizer.BFGS, 1e-15, null);
        StateStorage states = rslt.getSmoothedStates();
        
        DataBlock q=DataBlock.of(states.getComponent(1));
        DoubleSeq e = states.getComponent(3);
        for (int i=0; i<q.length(); ++i){
            q.mul(i, Data.IND_PCR[i]);
        }
        q.add(states.getComponent(2));
        q.add(e);
//        System.out.println(q);
//        System.out.println(states.getComponent(1));
//        System.out.println(states.getComponent(2));
//        System.out.println(states.getComponent(3));
//        System.out.println(DoubleSeq.of(Data.PCRA));
//        System.out.println(rslt.getParameters()[0]);
    }
   
}
