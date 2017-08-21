package Utils;

/**
 * Created by T510 on 8/21/2017.
 */

public class ParametricFunctions {

    public static float exponentialEasing(float x, float a){

        float epsilon = 0.00001f;
        float min_param_a = 0.0f + epsilon;
        float max_param_a = 1.0f - epsilon;
        a = Math.max(min_param_a, Math.min(max_param_a, a));

        if (a < 0.5){
            // emphasis
            a = 2.0f*(a);
            double y = Math.pow(x, a);
            return (float)y;
        } else {
            // de-emphasis
            a = 2.0f*(a-0.5f);
            double y = Math.pow(x, 1.0f/(1.0f-a));
            return (float)y;
        }
    }

}
