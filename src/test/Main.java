package test;

import com.firefly.fit.Fit;

public class Main {
    public static void main(String[] args){
        double[][] datas=new double[][]{
                {32.3787591,32},
                {32.8252527,32.5},
                {33.3753966,33},
                {33.6624282,33.5},
                {33.9414867,34},
                {34.4039265,34.5},
                {34.7308236,35},
                {35.2331289,35.5},
                {35.5121874,36},
                {35.9188155,36.5},
                {36.5088249,37},
                {36.8038296,37.5},
                {36.9393723,38},
                {37.3539735,38.5},
                {38.0077677,39},
                {38.2629069,39.5},
                {38.749266,40},
                {39.1957596,40.5},
                {39.6342801,41},
                {40.0409082,41.5},
                {40.343886,42},
                {40.8701106,42.5},
                {41.2926849,43}
        };

        //将X、Y数据分开
        double[] x=new double[datas.length];
        double[] y=new double[datas.length];
        for(int i=0;i<datas.length;i++){
            x[i]=datas[i][0];
            y[i]=datas[i][1];
        }

        //计算拟合参数
        double[] params= Fit.calcFitParams(x,y,5);

        //拟合数据
        double[] yt=Fit.fitVals(params,x);

        for(int i=0;i<params.length;i++){
            System.out.println("参数("+(i+1)+"):"+params[i]);
        }
        System.out.println();
        //显示结果
        for(int i=0;i<yt.length;i++){
            System.out.println("y:"+y[i]+"   y':"+yt[i]);
        }
    }
}
