package pl.bak.credit.domain.uri;

public class UrlData {
    private static final String customerCreateURL = "http://localhost:8081/customer/create";
    private static final String customerGetURL = "http://localhost:8081/customer/all";

    private static final String productCreateURL = "http://localhost:8082/product/create";
    private static final String productGetURL = "http://localhost:8082/product/all";

    public static String getCustomerCreateURL() {
        return customerCreateURL;
    }

    public static String getProductCreateURL() {
        return productCreateURL;
    }

    public static String getCustomerGetURL() {
        return customerGetURL;
    }

    public static String getProductGetURL() {
        return productGetURL;
    }
}
