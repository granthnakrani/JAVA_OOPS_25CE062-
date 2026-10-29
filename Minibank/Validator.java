import java.util.regex.Pattern;
public final class Validator {
    private static final Pattern MOBILE=Pattern.compile("^[6-9][0-9]{9}$");
    private static final Pattern EMAIL=Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PAN=Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");
    private static final Pattern IFSC=Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");
    private Validator(){}
    public static boolean isValidMobile(String s){return s!=null&&MOBILE.matcher(s).matches();}
    public static boolean isValidEmail(String s){return s!=null&&EMAIL.matcher(s).matches();}
    public static boolean isValidPan(String s){return s!=null&&PAN.matcher(s).matches();}
    public static boolean isValidIfsc(String s){return s!=null&&IFSC.matcher(s).matches();}
}
