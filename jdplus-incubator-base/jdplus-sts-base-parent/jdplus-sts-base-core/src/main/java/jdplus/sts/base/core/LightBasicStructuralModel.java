/*
 * Copyright 2020 National Bank of Belgium
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
package jdplus.sts.base.core;

import jdplus.toolkit.base.core.stats.likelihood.DiffuseLikelihoodStatistics;
import jdplus.toolkit.base.api.data.DoubleSeq;
import jdplus.toolkit.base.api.timeseries.regression.MissingValueEstimation;
import jdplus.toolkit.base.api.data.ParametersEstimation;
import jdplus.toolkit.base.api.processing.ProcessingLog;
import jdplus.sa.base.api.SeriesDecomposition;
import jdplus.toolkit.base.api.stats.StatisticalTest;
import jdplus.toolkit.base.api.timeseries.TsData;
import jdplus.toolkit.base.api.timeseries.calendars.LengthOfPeriodType;
import jdplus.toolkit.base.api.timeseries.regression.Variable;
import java.util.List;
import java.util.Map;
import jdplus.toolkit.base.api.math.matrices.Matrix;
import jdplus.sts.base.api.BsmDecomposition;
import jdplus.sts.base.api.BsmDescription;
import jdplus.sts.base.api.BsmSpec;

/**
 *
 * @author PALATEJ
 * @param <M>
 */
@lombok.Value
@lombok.Builder
public class LightBasicStructuralModel<M> implements BasicStructuralModel {

    Description description;
    Estimation estimation;
    BsmDecomposition bsmDecomposition;
    SeriesDecomposition finalDecomposition;

    @lombok.Singular
    private Map<String, StatisticalTest> diagnostics;

    @lombok.Singular
    private Map<String, Object> additionalResults;

    @lombok.Value
    @lombok.Builder
    public static class Description implements BsmDescription {

        /**
         * Original series
         */
        TsData series;
        /**
         * Log transformation
         */
        boolean logTransformation;

        /**
         * Transformation for leap year or length of period
         */
        LengthOfPeriodType lengthOfPeriodTransformation;

        /**
         * Regression variables (including mean correction)
         */
        Variable[] variables;

        /**
         * For instance SarimaSpec
         */
        BsmSpec specification;
        
    }

    @lombok.Value
    @lombok.Builder
    public static class Estimation implements BsmEstimation {

//        @lombok.NonNull
        private DoubleSeq y;
//        @lombok.NonNull
        private Matrix X;

        /**
         * Regression estimation. The order correspond to the order of the
         * variables
         * Fixed coefficients are not included
         */
//        @lombok.NonNull
        private DoubleSeq coefficients;
//        @lombok.NonNull
        private Matrix coefficientsCovariance;

//        @lombok.NonNull
        private MissingValueEstimation[] missing;
        /**
         * Parameters of the stochastic component. Fixed parameters are not
         * included
         */
//        @lombok.NonNull
        private ParametersEstimation parameters;

//        @lombok.NonNull
        private DiffuseLikelihoodStatistics statistics;

//        @lombok.NonNull
        private DoubleSeq residuals;

        @lombok.Singular
        private List<ProcessingLog.Information> logs;

    }

}
