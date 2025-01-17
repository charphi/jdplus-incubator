/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdplus.sts.base.core.msts.internal;

import jdplus.sts.base.core.msts.StateItem;
import jdplus.toolkit.base.api.data.DoubleSeq;
import jdplus.sts.base.core.msts.MstsMapping;
import jdplus.sts.base.core.msts.VarianceInterpreter;
import java.util.Collections;
import java.util.List;
import jdplus.sts.base.core.msts.ParameterInterpreter;
import jdplus.toolkit.base.core.ssf.ISsfLoading;
import jdplus.toolkit.base.core.ssf.StateComponent;
import jdplus.toolkit.base.core.ssf.sts.PeriodicComponent;

/**
 *
 * @author palatej
 */
public class PeriodicItem extends StateItem {

    private final VarianceInterpreter v;
    private final double period;
    private final int[] k;

    public PeriodicItem(String name, double period, int[] k, double cvar, boolean fixedvar) {
        super(name);
        v = new VarianceInterpreter(name + ".var", cvar, fixedvar, true);
        this.period=period;
        this.k=k;
    }
    
    private PeriodicItem(PeriodicItem item){
        super(item.name);
        this.period=item.period;
        this.k=item.k;
        v=item.v.duplicate();
    }
    
    @Override
    public PeriodicItem duplicate(){
        return new PeriodicItem(this);
    }

    @Override
    public void addTo(MstsMapping mapping) {
        mapping.add(v);
        mapping.add((p, builder) -> {
            double var = p.get(0);
            builder.add(name, PeriodicComponent.stateComponent(period, k, var), PeriodicComponent.defaultLoading(k.length));
            return 1;
        });
    }

    @Override
    public List<ParameterInterpreter> parameters() {
        return Collections.singletonList(v);
    }

    @Override
    public StateComponent build(DoubleSeq p) {
        double var = p.get(0);
        return PeriodicComponent.stateComponent(period, k, var);
    }

    @Override
    public int parametersCount() {
        return 1;
    }

    @Override
    public ISsfLoading defaultLoading(int m) {
        if (m > 0) {
            return null;
        }
        return PeriodicComponent.defaultLoading(k.length);
    }

    @Override
    public int defaultLoadingCount() {
        return 1;
    }

    @Override
    public int stateDim() {
        return 2*k.length;
    }
    
    @Override
    public boolean isScalable() {
        return !v.isFixed();
    }
    
}
