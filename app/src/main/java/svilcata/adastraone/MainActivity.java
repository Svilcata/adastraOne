package svilcata.adastraone;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import svilcata.adastraone.Models.Dog;
import svilcata.adastraone.retrofit.ApiInterface;
import svilcata.adastraone.retrofit.ApiUtils;
import svilcata.adastraone.retrofit.Breed_ListHMResponse;
import svilcata.adastraone.retrofit.Breed_RandomImgResponse;

public class MainActivity extends AppCompatActivity {
    private List<Dog> dogList = new ArrayList<>();
    private DogsAdapter dogsAdapter;
    @BindView(R.id.item_list)
    RecyclerView recyclerView;
    @BindView(R.id.indeterminateBar)
    ProgressBar progressBar;
    @BindView(R.id.retrieveList_button)
    Button btn_start;
    @BindView(R.id.camera_pic)
    ImageView imgView_cameraIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dogsAdapter = new DogsAdapter(dogList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(dogsAdapter);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_start.setVisibility(View.INVISIBLE);
                imgView_cameraIcon.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                prepareDog_Adapter();
            }
        });
    }

    private void prepareDog_Adapter() {
        final ApiInterface apiInterface = ApiUtils.getAPIService();
        Call<Breed_ListHMResponse> call = apiInterface.getAllBreedsResponseHM();
        call.enqueue(new Callback<Breed_ListHMResponse>() {
            @Override
            public void onResponse(@NonNull Call<Breed_ListHMResponse> call, @NonNull Response<Breed_ListHMResponse> response) {
                for (Map.Entry<String, List<String>> entry : response.body().getMessage().entrySet()) {
                    dogList.add(new Dog(entry.getKey(), entry.getValue(), null));
                }
                updateDogImgURL(dogList);
            }

            @Override
            public void onFailure(@NonNull Call<Breed_ListHMResponse> call, @NonNull Throwable t) {

            }
        });
    }

    private void updateDogImgURL(final List<Dog> dogList) {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiUtils.getAPIService();
        for (int i = 0; i < dogList.size(); i++) {
            Call<Breed_RandomImgResponse> call = apiInterface.getBreedRandomImageResponse(dogList.get(i).getBreed());
            final int finalI1 = i;
            call.enqueue(new Callback<Breed_RandomImgResponse>() {
                @Override
                public void onResponse(Call<Breed_RandomImgResponse> call, Response<Breed_RandomImgResponse> response) {
                    dogList.get(finalI1).setmRandomImageURL(response.body().getMessage());
                    dogsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Breed_RandomImgResponse> call, Throwable t) {

                }
            });
        }
    }
}
