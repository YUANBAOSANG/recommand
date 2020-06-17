package gradientDescent;


import dao.MarsMapper;
import org.apache.commons.net.nntp.Article;
import org.apache.ibatis.session.SqlSession;
import pojo.Mars;
import util.MybitsUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @创建人 YDL
 * @创建时间 2020/5/6 16:40
 * @描述
 */
public class GradientDescent {
    //批量梯度下降,因为数据量不大
    //y = t0+t1*x1+t2*x2;
    private static int dg(double a,double[] ts,double[][] xs,double[] ys,double error0,double epsilon){
        int count =0;
        while(true) {
            System.out.println("迭代次数"+count+" "+Arrays.toString(ts));
            count++;
            double[] sum = new double[ts.length];
            for (int i = 0; i < xs.length; i++) {
                double diff = Arith.sub(ys[i], ts[0]);
                for (int j = 1; j < ts.length; j++) {
                    double tmp = Arith.mul(ts[j], xs[i][j]);
                    diff = Arith.sub(diff, tmp);
                }
                for (int j = 0; j < ts.length; j++) {
                    double temp = Arith.mul(Arith.mul(diff, xs[i][j]), a);
                    sum[j] = Arith.add(sum[j], temp);
                }
            }
            for (int i = 0; i < ts.length; i++) {
                ts[i] = sum[i];
            }
            double error1 = 0.0;
            for (int i = 0; i < xs.length; i++) {
                double tmp = Arith.sub(ys[i], xs[i][0]);
                for (int j = 0; j < ts.length; j++) {
                    tmp = Arith.sub(tmp, Arith.mul(ts[j], xs[i][j]));
                }
                tmp = Arith.mul(tmp, tmp);
                tmp = Arith.sub(tmp, 2);
                error1 = Arith.add(error1, tmp);
            }

            if (Math.abs(Arith.sub(error1, error0)) < epsilon) {
                System.out.println("误差为："+(error0-error1));
                break;
            } else {
                error0 = error1;
            }
        }
        return count;
    }
    public static void main(String[] args) {
       try(SqlSession sqlSession = MybitsUtils.getSqlSession()){
           MarsMapper marsMapper = sqlSession.getMapper(MarsMapper.class);
           List<Mars> li = marsMapper.getMarsALL();
           double[][] xs = new double[li.size()][];
          for(int i=0;i<xs.length;i++){
              Mars m = li.get(i);
              xs[i] = new double[]{0,m.getMaxI(),m.getRank(),m.getReg()};
          }
           double[] ys = li.stream().mapToDouble(x->x.getMars()).toArray();
           double[] ts = new double[4];

           int count = dg(0.000003,ts,xs,ys,0,0.000001);
           double[] regs = new double[28];
           for(int i=0,j=2;i<28;i++,j++){
               regs[i] = Arith.div(j,100);
           }
           int[] rank = new int[100];
           for(int i=0,j=10;i<100;i++,j+=3){
               rank[i] = j;
           }
           int[] maxI = new int[10];
           for(int i=0,j=50;i<10;i++,j+=7){
               maxI[i] = j;
           }
           double min = 0;
           double[] parm = new double[3];
            for(int i=0;i<regs.length;i++){
                for(int j=0;j<maxI.length;j++){
                    for(int k =0;k<rank.length;k++){
                        double ma = Arith.add(Arith.add(Arith.mul(ts[1],maxI[j])
                                ,Arith.mul(ts[2],rank[k])),
                                Arith.mul(ts[3],regs[i]));
                        if(ma>min){
                            min = ma;
                            parm[0] = maxI[j];
                            parm[1] = rank[k];
                            parm[2] = regs[i];
                        }
                    }
                }
            }
            System.out.println("迭代次数:"+count);
            System.out.println("范围最优参数："+Arrays.toString(parm));
       }

    }
}
