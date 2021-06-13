package edu.coursera.parallel;

import java.util.Random;

import junit.framework.TestCase;

public class MatrixMultiplyTest extends TestCase {

    final static private int REPEATS = 20;

    private static int getNCores() {
        String ncoresStr = System.getenv("COURSERA_GRADER_NCORES");
        if (ncoresStr == null) {
            return Runtime.getRuntime().availableProcessors();
        } else {
            return Integer.parseInt(ncoresStr);
        }
    }


    private double[][] createMatrix(final int N) {
        final double[][] input = new double[N][N];
        final Random rand = new Random(314);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                input[i][j] = rand.nextInt(100);
            }
        }

        return input;
    }

    private void checkResult(final double[][] ref, final double[][] output, final int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                String msg = "Error detected on cell (" + i + ", " + j + ")";
                assertEquals(msg, ref[i][j], output[i][j]);
            }
        }
    }

    public void seqMatrixMultiply(final double[][] A, final double[][] B, final double[][] C, final int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                C[i][j] = 0.0;
                for (int k = 0; k < N; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
    }

    private double parTestHelper(final int N) {

        final double[][] A = createMatrix(N);
        final double[][] B = createMatrix(N);
        final double[][] C = new double[N][N];
        final double[][] refC = new double[N][N];

        seqMatrixMultiply(A, B, refC, N);

        MatrixMultiply.parMatrixMultiply(A, B, C, N);

        checkResult(refC, C, N);

        final long seqStartTime = System.currentTimeMillis();
        for (int r = 0; r < REPEATS; r++) {
            seqMatrixMultiply(A, B, C, N);
        }
        final long seqEndTime = System.currentTimeMillis();

        final long parStartTime = System.currentTimeMillis();
        for (int r = 0; r < REPEATS; r++) {
            MatrixMultiply.parMatrixMultiply(A, B, C, N);
        }
        final long parEndTime = System.currentTimeMillis();

        final long seqTime = (seqEndTime - seqStartTime) / REPEATS;
        final long parTime = (parEndTime - parStartTime) / REPEATS;

        return (double)seqTime / (double)parTime;
    }

    public void testPar512_x_512() {
        final int ncores = getNCores();
        double speedup = parTestHelper(512);
        double minimalExpectedSpeedup = (double)ncores * 0.6;
        final String errMsg = String.format("It was expected that the parallel implementation would run at " +
                "least %fx faster, but it only achieved %fx speedup", minimalExpectedSpeedup, speedup);
        assertTrue(errMsg, speedup >= minimalExpectedSpeedup);
    }

    public void testPar768_x_768() {
        final int ncores = getNCores();
        double speedup = parTestHelper(768);
        double minimalExpectedSpeedup = (double)ncores * 0.6;
        final String errMsg = String.format("It was expected that the parallel implementation would run at " +
                "least %fx faster, but it only achieved %fx speedup", minimalExpectedSpeedup, speedup);
        assertTrue(errMsg, speedup >= minimalExpectedSpeedup);
    }
}
