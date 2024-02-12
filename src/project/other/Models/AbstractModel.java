package project.other.Models;

import project.other.Bind;

public abstract class AbstractModel {

    @Bind
    private int LL;

    @Bind
    private  double[] twKI;
    @Bind
    private  double[] twKS;
    @Bind
    private  double[] twINW;
    @Bind
    private  double[] twEKS;
    @Bind
    private  double[] twIMP;


    @Bind
    private  double[] KI;
    @Bind
    private  double[] KS;
    @Bind
    private  double[] INW;
    @Bind
    private  double[] EKS;
    @Bind
    private  double[] IMP;
    @Bind
    private  double[] PKB;

    private double temp;

    public abstract void run();

}
