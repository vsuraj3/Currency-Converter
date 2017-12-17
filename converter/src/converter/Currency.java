package converter;

public enum Currency {
	INR("rupee",1), 
    USD("dollar", 62),
    GBP("pound", 85.86),
    EUR("euro", 76.71),
    YEN("yen",1.74);

	
 
    private double rupeeConversionRate;
 
    private String fullName;

    Currency(String fullName, double rupeeConversionRate) {
        this.rupeeConversionRate = rupeeConversionRate;
        this.fullName = fullName;
    }

    public double getRupeeConversionRate() {
        return rupeeConversionRate;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return name();
    }

}