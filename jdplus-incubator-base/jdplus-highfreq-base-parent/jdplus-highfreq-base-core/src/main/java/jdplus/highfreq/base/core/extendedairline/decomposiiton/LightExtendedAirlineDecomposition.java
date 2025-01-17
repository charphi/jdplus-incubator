/*
 * Copyright 2023 National Bank of Belgium
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
package jdplus.highfreq.base.core.extendedairline.decomposiiton;

import jdplus.toolkit.base.api.data.DoubleSeq;
import jdplus.highfreq.base.api.ExtendedAirline;
import jdplus.highfreq.base.api.SeriesComponent;
import jdplus.toolkit.base.core.stats.likelihood.LikelihoodStatistics;
import jdplus.toolkit.base.api.math.matrices.Matrix;
import jdplus.toolkit.base.api.information.GenericExplorable;
import java.util.List;
import jdplus.toolkit.base.core.ucarima.UcarimaModel;

/**
 * Low-level results. Should be refined
 *
 * @author palatej
 */
@lombok.Value
@lombok.Builder
public class LightExtendedAirlineDecomposition implements GenericExplorable {

    DoubleSeq y;
    ExtendedAirline model;

    private DoubleSeq parameters, score;
    private Matrix parametersCovariance;

    LikelihoodStatistics likelihood;
    @lombok.Singular
    List<SeriesComponent> components;

    UcarimaModel ucarima;

    public SeriesComponent component(String name) {
        for (SeriesComponent cmp : components) {
            if (cmp.getName().equalsIgnoreCase(name)) {
                return cmp;
            }
        }
        return null;
    }

}
