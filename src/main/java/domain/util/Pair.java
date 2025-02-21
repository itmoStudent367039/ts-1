package domain.util;

public record Pair<F, S>(F f, S s) {

    public F getF() {
        return f;
    }

    public S getS() {
        return s;
    }

    public static <F, S> Pair<F, S> of(F f, S s) {
        return new Pair<>(f, s);
    }
}
