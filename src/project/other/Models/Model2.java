package project.other.Models;

import project.other.Bind;

public class Model2 extends AbstractModel {


    @Bind
    private int LL;

    @Bind
    private double[] twKI;
    @Bind
    private double[] twKS;
    @Bind
    private double[] twINW;
    @Bind
    private double[] twEKS;
    @Bind
    private double[] twIMP;


    @Bind
    private double[] KI;
    @Bind
    private double[] KS;
    @Bind
    private double[] INW;
    @Bind
    private double[] EKS;
    @Bind
    private double[] IMP;
    @Bind
    private double[] totalInvestment;


    @Override
    public void run() {
        // Calculate total investment
        totalInvestment = new double[LL];
        totalInvestment[0] = KI[0] + KS[0] + INW[0] + EKS[0];
        for (int t = 1; t < LL; t++) {
            KI[t] = twKI[t] * KI[t - 1];
            KS[t] = twKS[t] * KS[t - 1];
            INW[t] = twINW[t] * INW[t - 1];
            EKS[t] = twEKS[t] * EKS[t - 1];
            IMP[t] = twIMP[t] * IMP[t - 1];
            totalInvestment[t] = KI[t] + KS[t] + INW[t] + EKS[t];
        }
    }
}
