package com.firefly.fit;

/**
 * 拟合数据
 */
public class Fit {
    //数组除以一个数
    private static double[] arrDiv(double[] divisor,double dividend){
        int i;
        double[] retVal=new double[divisor.length];
        for(i=0;i<retVal.length;i++){
            retVal[i]=divisor[i]/dividend;
        }
        return retVal;
    }
    //数组乘以一个数
    private static double[] arrMult(double[] a,double b){
        int i;
        double[] retVal=new double[a.length];
        for(i=0;i<retVal.length;i++){
            retVal[i]=a[i]*b;
        }
        return retVal;
    }
    //数组相减
    private static double[] arrSub(double[] a,double[] b){
        int i;
        double[] retVal=new double[a.length];
        for(i=0;i<retVal.length;i++){
            retVal[i]=a[i]-b[i];
        }
        return retVal;
    }

    //将X,Y一维数组经过导数函数计算后组成二维数组
    private static double[][] matrix(double[] xVal,double[] yVal,int paramLen){
        int m;//数组长度
        double[] x;
        double[] yx;
        int xLen,yxLen;
        int i,j;

        xLen=(paramLen-1)*2+1;
        yxLen=paramLen;
        x=new double[xLen];
        yx=new double[yxLen];
        m=xVal.length;

        x[0]=m;
        for(i=1;i<x.length;i++){
            for(j=0;j<xVal.length;j++){
                x[i]=x[i]+Math.pow(xVal[j],i);
            }
        }

        for(i=0;i<yx.length;i++){
            for(j=0;j<yVal.length;j++){
                yx[i]=yx[i]+yVal[j]*Math.pow(xVal[j],i);
            }
        }

        //后面增加1列，这一列是为了放Y的值
        double[][] retVal=new double[paramLen][paramLen+1];
        for(i=0;i<paramLen;i++){
            for(j=0;j<paramLen;j++){
                retVal[i][j]=x[xLen-1-i-j];
            }
            retVal[i][paramLen]=yx[yxLen-1-i];
        }

        return retVal;
    }

    //高斯消元法解方程,将导数函数的二维数组解出abcde参数
    private static double[] gauss(double[][] matrix){
        int i,j;

        //正向消元，从第0行到最后进行消元
        for(i=0;i<matrix.length;i++){
            matrix[i]=arrDiv(matrix[i],matrix[i][i]);//将第i行的每个元素都除以第i个数

            //计算第i行以下的所有行
            for(j=i+1;j<matrix.length;j++){
                matrix[j]=arrSub(
                        matrix[j],
                        arrMult(matrix[i],matrix[j][i])
                );
            }
        }

        //反向消元，从后面一直到0行
        for(i=matrix.length-1;i>=0;i--){
            for(j=i-1;j>=0;j--){
                matrix[j]=arrSub(
                        matrix[j],
                        arrMult(matrix[i],matrix[j][i])
                );
            }
        }

        //经过上面的消元法后，剩下的每行的最后一个元素就是a、b、c....的拟合参数
        double[] retVal=new double[matrix.length];
        for(i=0;i<matrix.length;i++){
            //返回每行的最后一个元素
            retVal[i]=matrix[i][matrix[i].length-1];
        }
        return retVal;
    }

    /**
     * 计算拟合参数
     * @param xVal
     * @param yVal
     * @param paramLen
     * @return
     */
    public static double[] calcFitParams(double[] xVal,double[] yVal,int paramLen){
        //经过导数处理后的二维数组
        double[][] matrixVal=matrix(xVal,yVal,paramLen);
        //返回拟合参数
        return gauss(matrixVal);
    }

    //拟合数据
    public static double fitVal(double[] param,double val){
        int i;
        int paramLen;
        double retVal=0;
        paramLen=param.length;
        for(i=0;i<paramLen;i++){
            retVal=retVal+param[i]*Math.pow(val,paramLen-i-1);
        }

        return retVal;
    }
    //拟合曲线
    public static double[] fitVals(double[] param,double[] val){
        int i;
        double[] retVal=new double[val.length];

        for(i=0;i<val.length;i++){
            retVal[i]=fitVal(param,val[i]);
        }
        return retVal;
    }
}
