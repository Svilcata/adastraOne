package svilcata.adastraone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import svilcata.adastraone.BreedGallery.BreedGalleryActivity;
import svilcata.adastraone.Models.Dog;

class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.ViewHolder> {

    private List<Dog> dogList;
    private Context context;

    DogsAdapter(List<Dog> dogList, Context context) {
        this.dogList = dogList;
        this.context = context;
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
        Picasso.with(context).load(dogList.get(position).getmRandomImageURL()).into(holder.breedPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BreedGalleryActivity.class);
                intent.putExtra("breedname", holder.breedName.getText().toString());
                context.startActivity(intent);
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