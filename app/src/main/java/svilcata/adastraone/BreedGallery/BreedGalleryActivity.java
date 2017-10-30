package svilcata.adastraone.BreedGallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import svilcata.adastraone.R;
import svilcata.adastraone.retrofit.ApiInterface;
import svilcata.adastraone.retrofit.ApiUtils;
import svilcata.adastraone.retrofit.Breed_ListResponse;

public class BreedGalleryActivity extends AppCompatActivity {
    private List<String> imgUrls_List = new ArrayList<>();
    private GridViewAdapter gridAdapter;
    @BindView(R.id.gridView)
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed_gallery);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(getIntent().getStringExtra("breedname"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Click on any photo to enlarge", Snackbar.LENGTH_INDEFINITE).setAction("Hide", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        snackbar.show();

        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, imgUrls_List);
        gridView.setAdapter(gridAdapter);
        prepareDog_Adapter(getIntent().getStringExtra("breedname"));
    }

    private void prepareDog_Adapter(String breedName) {
        final ApiInterface apiInterface = ApiUtils.getAPIService();
        Call<Breed_ListResponse> call = apiInterface.getAllBreedImages_Response(breedName);
        call.enqueue(new Callback<Breed_ListResponse>() {
            @Override
            public void onResponse(@NonNull Call<Breed_ListResponse> call, @NonNull Response<Breed_ListResponse> response) {
                imgUrls_List.addAll(response.body().getMessage());
                gridAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<Breed_ListResponse> call, @NonNull Throwable t) {
                Log.e("LOG", t.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
