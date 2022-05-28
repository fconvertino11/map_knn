package data;
public class ContinuousAttribute extends Attribute {
    private double min;
    private double max;
    ContinuousAttribute(String name, int index) {
        super(name, index);
    }
    void setMin(final Double v){
        if(v < min) {
            min = v;
        }
    }

    public void setMax(final Double m) {
        if(m > max){
            max = m;
        }
    }
    double scale (final Double value) {
        return (value-min)/(max-min);
    }
}