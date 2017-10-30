package svilcata.adastraone.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.List;

public class Breed_ListHMResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private LinkedHashMap<String, List<String>> message = null;

    public LinkedHashMap<String, List<String>> getMessage() {
        return message;
    }
}
