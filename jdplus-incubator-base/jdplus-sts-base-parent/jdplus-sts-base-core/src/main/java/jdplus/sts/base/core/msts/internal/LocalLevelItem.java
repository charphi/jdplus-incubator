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
import jdplus.toolkit.base.core.ssf.sts.LocalLevel;
import java.util.Collections;
import java.util.List;
import jdplus.sts.base.core.msts.ParameterInterpreter;
import jdplus.toolkit.base.core.ssf.ISsfLoading;
import jdplus.toolkit.base.core.ssf.StateComponent;

/**
 *
 * @author palatej
 */
public class LocalLevelItem extends StateItem {

    public final VarianceInterpreter v;
    public final double initial;

    public LocalLevelItem(String name, final double lvar, final boolean fixed, final double initial) {
        super(name);
        this.initial = initial;
        this.v = new VarianceInterpreter(name + ".var", lvar, fixed, true);
    }

    private LocalLevelItem(LocalLevelItem item) {
        super(item.name);
        this.v = item.v.duplicate();
        this.initial = item.initial;
    }

    @Override
    public LocalLevelItem duplicate() {
        return new LocalLevelItem(this);
    }

    @Override
    public void addTo(MstsMapping mapping) {
        mapping.add(v);
        mapping.add((p, builder) -> {
            double e = p.get(0);
            StateComponent cmp = LocalLevel.stateComponent(e, initial);
            builder.add(name, cmp, LocalLevel.defaultLoading());
            return 1;
        });
    }

    @Override
    public List<ParameterInterpreter> parameters() {
        return Collections.singletonList(v);
    }

    @Override
    public StateComponent build(DoubleSeq p) {
        double e = p.get(0);
        return LocalLevel.stateComponent(e, initial);
    }

    @Override
    public int parametersCount() {
        return 1;
    }

    @Override
    public ISsfLoading defaultLoading(int m) {
        return LocalLevel.defaultLoading();
    }

    @Override
    public int defaultLoadingCount() {
        return 1;
    }

    @Override
    public int stateDim() {
        return 1;
    }
    
    @Override
    public boolean isScalable() {
        return !v.isFixed();
    }
}
