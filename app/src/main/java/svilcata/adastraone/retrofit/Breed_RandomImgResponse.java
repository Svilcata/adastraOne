package svilcata.adastraone.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Svilcata on 31-Oct-17.
 */

public class Breed_RandomImgResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message = null;

    public String getMessage() {
        return message;
    }
}
