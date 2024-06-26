package ru.barsukon.ecs.client;

class FFT {

    private int size;
    private int bits;
    private double[] cosTable;
    private double[] sinTable;

    FFT(int n) {
        size = n;
        bits = (int) (Math.log(size) / Math.log(2));
        cosTable = new double[size / 2];
        sinTable = new double[size / 2];
        double dtheta = (-2 * Math.PI) / size;
        for (int i = 0; i < cosTable.length; i++) {
            cosTable[i] = Math.cos(dtheta * i);
            sinTable[i] = Math.sin(dtheta * i);
        }
    }

    /*
     * This uses the radix-2 decimation-in-time FFT algorithm.
     * Based on
     * http://www.ee.columbia.edu/~ronw/code/MEAPsoft/doc/html/FFT_8java-source.html
     * Douglas L. Jones
     * University of Illinois at Urbana-Champaign
     * January 19, 1992
     * http://cnx.rice.edu/content/m12016/latest/
     */
    void fft(double[] real, double[] imag) {
        int j = 0;
        int n2 = real.length / 2;
        for (int i = 1; i < real.length - 1; i++) {
            int n1 = n2;
            while (j >= n1) {
                j -= n1;
                n1 /= 2;
            }
            j += n1;
            if (i < j) {
                double t1 = real[i];
                real[i] = real[j];
                real[j] = t1;
                t1 = imag[i];
                imag[i] = imag[j];
                imag[j] = t1;
            }
        }
        n2 = 1;
        for (int i = 0; i < bits; i++) {
            int n1 = n2;
            n2 <<= 1;
            int a = 0;
            for (j = 0; j < n1; j++) {
                double c = cosTable[a];
                double s = sinTable[a];
                a += 1 << (bits - i - 1);
                for (int k = j; k < real.length; k += n2) {
                    int t = k + n1;
                    double t1 = c * real[t] - s * imag[t];
                    double t2 = s * real[t] + c * imag[t];
                    real[k + n1] = real[k] - t1;
                    imag[k + n1] = imag[k] - t2;
                    real[k] += t1;
                    imag[k] += t2;
                }
            }
        }
    }

    int getSize() {
        return size;
    }

    double magnitude(double real, double imag) {
        return Math.sqrt(real * real + imag * imag) / size;
    }
}
