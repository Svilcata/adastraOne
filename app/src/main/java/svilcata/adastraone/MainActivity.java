package svilcata.adastraone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import svilcata.adastraone.BreedGallery.BreedGalleryActivity;
import svilcata.adastraone.Models.Dog;
import svilcata.adastraone.retrofit.ApiInterface;
import svilcata.adastraone.retrofit.ApiUtils;
import svilcata.adastraone.retrofit.Breed_ListHMResponse;

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

        dogsAdapter = new DogsAdapter(dogList);
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
        Call<Breed_ListHMResponse> call = apiInterface.getAllBreeds_ResponseHM();
        call.enqueue(new Callback<Breed_ListHMResponse>() {
            @Override
            public void onResponse(@NonNull Call<Breed_ListHMResponse> call, @NonNull Response<Breed_ListHMResponse> response) {
                for (Map.Entry<String, List<String>> entry : response.body().getMessage().entrySet()) {
                    dogList.add(new Dog(entry.getKey(), entry.getValue(), null));
                }
                update_DogImgURL(dogList);
            }

            @Override
            public void onFailure(@NonNull Call<Breed_ListHMResponse> call, @NonNull Throwable t) {

            }
        });
    }

    private void update_DogImgURL(final List<Dog> dogList) {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        for (int i = 0; i < dogList.size(); i++) {
            RequestQueue queue = Volley.newRequestQueue(this);
            final int finalI1 = i;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://dog.ceo/api/breed/" + dogList.get(i).getBreed() + "/images/random", null, new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        dogList.get(finalI1).setmRandomImageURL(response.get("message").toString());
                        dogsAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(jsonObjectRequest);
        }
    }

    class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.ViewHolder> {

        private List<Dog> dogList;

        DogsAdapter(List<Dog> dogList) {
            this.dogList = dogList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dog_list_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            String name = dogList.get(position).getBreed();
            name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            holder.breedName.setText(name);
            Picasso.with(getBaseContext()).load(dogList.get(position).getmRandomImageURL()).into(holder.breedPhoto);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), BreedGalleryActivity.class);
                    intent.putExtra("breedname", holder.breedName.getText().toString());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dogList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.title_cardviewListItem)
            TextView breedName;
            @BindView(R.id.image_cardviewListItem)
            ImageView breedPhoto;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
