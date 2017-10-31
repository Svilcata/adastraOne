package svilcata.adastraone.BreedGallery;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import svilcata.adastraone.R;

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private List<String> data = new ArrayList();

    GridViewAdapter(Context context, int layoutResourceId, List<String> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Picasso.with(context).load(data.get(position)).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImage(data.get(position));
            }
        });

        return row;
    }

    private void showImage(String imageUri) {
        final AlertDialog.Builder imageDialog = new AlertDialog.Builder(context);
        LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.image_dialogfragment, null);
        ImageView imageView = view.findViewById(R.id.imageView_enlarged);
        Picasso.with(context).load(imageUri).into(imageView);
        imageDialog.setView(view);
        imageDialog.show();
    }

    static class ViewHolder {
        ImageView image;
    }
}
