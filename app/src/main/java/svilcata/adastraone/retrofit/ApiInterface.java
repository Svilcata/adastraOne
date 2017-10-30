package svilcata.adastraone.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    //Get all breeds
    @GET("api/breeds/list/all")
    Call<Breed_ListHMResponse> getAllBreeds_ResponseHM();

    //Get all images from specific breed
    @GET("api/breed/{breed_name}/images")
    Call<Breed_ListResponse> getAllBreedImages_Response(@Path("breed_name") String breedName);

}