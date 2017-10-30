package svilcata.adastraone.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Breed_ListResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private List<String> message = null;

    public List<String> getMessage() {
        return message;
    }
}
