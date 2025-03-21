package vn.iotstar.ui_gk.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import vn.iotstar.ui_gk.R;
import vn.iotstar.ui_gk.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    //vophuhao
    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.txtCateName.setText(category.getCateName());

        Glide.with(context)
                .load(category.getCateImage()) // URL ảnh
                .into(holder.imgCate); // Gán vào ImageView

//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, ProductCategoryActivity.class);
//            intent.putExtra("CATEGORY_ID", category.getCateId());
//            context.startActivity(intent);
//        });
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCate;
        TextView txtCateName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCate = itemView.findViewById(R.id.imgCate);
            txtCateName = itemView.findViewById(R.id.txtCateName);
        }
    }
    //vophuhao
}
