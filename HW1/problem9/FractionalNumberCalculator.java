public class FractionalNumberCalculator {
    public static void printCalculationResult(String equation) {
        String[] in = equation.split(" ");
        String first = in[0], operator = in[1], second = in[2];

        FractionalNumber frac1 = new FractionalNumber(first),
                        frac2 = new FractionalNumber(second);

        if(operator.equals("+"))
            frac1.add(frac2).print();
        if(operator.equals("-"))
            frac1.subtract(frac2).print();
        if(operator.equals("*"))
            frac1.multiply(frac2).print();
        if(operator.equals("/"))
            frac1.divide(frac2).print();
    }
}

class FractionalNumber {
    private int numerator;
    private int denominator;

    FractionalNumber (String frac) {
        String[] split = frac.split("/");
        numerator = Integer.parseInt(split[0]);
        denominator = split.length==1 ? 1 : Integer.parseInt(split[1]);
    }

    FractionalNumber (int numerator) {
        this(numerator, 1);
    }
    FractionalNumber (int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    private static FractionalNumber gcd(long numerator, long denominator) {
        long a = Math.abs(numerator), b = Math.abs(denominator);
        int sign = numerator*denominator>=0 ? 1:-1;
        if(b > a) {
            long t=b; b=a; a=t;
        }
        while(b>0) {
            long t=b;
            b = a%b;
            a = t;
        }
        return new FractionalNumber((int)(sign*Math.abs(numerator)/a), (int)(Math.abs(denominator)/a));
    }

    public FractionalNumber add(FractionalNumber other) {
        long numerator, denominator;
        numerator = this.numerator*(long)other.denominator + this.denominator*(long)other.numerator;
        denominator = this.denominator*(long)other.denominator;
        return gcd(numerator, denominator);
    }

    public FractionalNumber subtract(FractionalNumber other) {
        return add(new FractionalNumber(-other.numerator, other.denominator));
    }

    public FractionalNumber multiply(FractionalNumber other) {
        return gcd(this.numerator*(long)other.numerator, this.denominator*(long)other.denominator);
    }

    public FractionalNumber divide(FractionalNumber other) {
        return gcd(this.numerator*(long)other.denominator, this.denominator*(long)other.numerator);
    }

    public void print(){
        System.out.println(numerator + (denominator==1 || numerator==0? "":"/"+denominator));
    }
}