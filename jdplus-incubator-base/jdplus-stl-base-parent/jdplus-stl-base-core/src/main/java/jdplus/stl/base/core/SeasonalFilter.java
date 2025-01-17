/*
 * Copyright 2022 National Bank of Belgium
 *
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved 
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and 
 * limitations under the Licence.
 */
package jdplus.stl.base.core;

import jdplus.stl.base.api.LoessSpec;
import jdplus.stl.base.api.SeasonalSpec;
import java.util.function.IntToDoubleFunction;

/**
 *
 * @author Jean Palate
 */
public class SeasonalFilter {
    private final SeasonalLoessFilter sfilter;
    private final LowPassLoessFilter lfilter;
    
    public static SeasonalFilter of(SeasonalSpec spec){
        return new SeasonalFilter(spec.getSeasonalSpec(), spec.getLowPassSpec(), spec.getPeriod());
    }
    
    public SeasonalFilter(final SeasonalLoessFilter sfilter, final LowPassLoessFilter lfilter) {
        this.sfilter=sfilter;
        this.lfilter=lfilter;
    }
    
    public SeasonalFilter(LoessSpec sspec, LoessSpec lspec, int np){
        this.sfilter=new SeasonalLoessFilter(sspec, np);
        this.lfilter=new LowPassLoessFilter(lspec, np);
    }
    
    public boolean filter(IDataGetter y, IntToDoubleFunction userWeights, boolean mul, IDataSelector ys) {
        int n=y.getLength();
        int np=sfilter.getPeriod();
        double[] l = new double[n];
        double[] c = new double[n + 2 * np];
        if (! sfilter.filter(y, userWeights, IDataSelector.of(c, -np)))
            return false;
        if (! lfilter.filter(IDataGetter.of(c), IDataSelector.of(l)))
            return false;
        for (int i=0; i<n; ++i)
            ys.set(i, mul ? c[i+np]/l[i]:c[i+np]-l[i]);
        return true;
    }
}
