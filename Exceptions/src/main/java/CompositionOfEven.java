public class CompositionOfEven {
    public CompositionOfEven() {};

    public double composition(double a, double b) throws NumberNotEvenException {
        if ((a%2 == 0) && (b%2 == 0)) {
            return a*b;
        } else if (a%2 != 0){
            throw new NumberNotEvenException("Число " + Double.toString(a) + " не являеться четным!");
        } else {
            throw new NumberNotEvenException("Число " + Double.toString(b) + " не являеться четным!");
        }
    }
}
