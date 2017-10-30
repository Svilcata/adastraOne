package svilcata.adastraone.retrofit;

public class ApiUtils {
    public ApiUtils() {
    }

    private static final String BASE_URL = "https://dog.ceo/";

    public static ApiInterface getAPIService() {
        return RetrofitController.getClient(BASE_URL).create(ApiInterface.class);
    }
}
