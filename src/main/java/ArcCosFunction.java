public class ArcCosFunction {

    private static final double MIN = -1;
    private static final double MAX = 1;

    public double count(double epsilon, double x) {
        if (!(Double.compare(x, MIN) >= 0 && Double.compare(x, MAX) <= 0)) {
            return Double.NaN;
        }
        int n = 1;
        double series = 0;

        double member;
        do {
            member = getMember(x, n);
            series += member;
            n += 2;
        } while (Double.compare(Math.abs(member), epsilon) >= 0);
        return Math.PI / 2 - series;
    }

    private double getMember(double x, int n) {
        double top = 1;
        double bottom = 1;
        for (int i = 1; i < n; i++) {
            if (i % 2 == 1) {
                top *= i;
            } else {
                bottom *= i;
            }
        }
        return top * Math.pow(x, n) / (bottom * n);
    }
}
