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
package jdplus.highfreq.base.core.extendedairline.decomposition;

import jdplus.highfreq.base.core.extendedairline.decomposiiton.ExtendedAirlineDecomposition;
import jdplus.highfreq.base.core.extendedairline.decomposiiton.DecompositionKernel;
import jdplus.toolkit.base.api.data.DoubleSeq;
import tck.demetra.data.Data;
import tck.demetra.data.MatrixSerializer;
import jdplus.highfreq.base.api.DecompositionSpec;
import jdplus.toolkit.base.api.math.matrices.Matrix;
import jdplus.toolkit.base.api.processing.ProcessingLog;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author PALATEJ
 */
public class DecompositionKernelTest {

    final static DoubleSeq EDF;

    static {
        DoubleSeq y;
        try {
            InputStream stream = Data.class.getResourceAsStream("edf.txt");
            Matrix edf = MatrixSerializer.read(stream);
            y = edf.column(0);
        } catch (IOException ex) {
            y = null;
        }
        EDF = y;
    }

    public DecompositionKernelTest() {
    }

    @Test
    public void testSimple() {
        DecompositionSpec spec = DecompositionSpec.builder()
                .periodicities(new double[]{7})
                .stdev(true)
                .forecastsCount(35)
                .build();
        DecompositionKernel kernel = new DecompositionKernel(spec);
        ExtendedAirlineDecomposition decomp = kernel.process(EDF.log(), true, ProcessingLog.dummy());
        assertTrue(decomp != null);
    }

    public static void main(String[] args){
        testComplex();
    }
        
        
    public static void testComplex() {
        DecompositionSpec spec = DecompositionSpec.builder()
                .stdev(false)
                .biasCorrection(false)
                .forecastsCount(35)
                .periodicities(new double[]{7, 365.25})
                .build();
        DecompositionKernel kernel = new DecompositionKernel(spec);
        ExtendedAirlineDecomposition decomp = kernel.process(EDF.log(), true, ProcessingLog.dummy());
        System.out.println(decomp.getFinalComponents().get(2));
    }
}
