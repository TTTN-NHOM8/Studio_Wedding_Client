package com.example.studiowedding.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studiowedding.R;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Incurrent;
import com.example.studiowedding.utils.FormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class IncurrentAdapter extends RecyclerView.Adapter<IncurrentAdapter.IncurrentViewHolder> {

    private List<Incurrent> incurrentList;
    private Context context;
     static OnItemClickListner itemClickListener;
    FormatUtils formatUtils;
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");

    public IncurrentAdapter(List<Incurrent> incurrentList, Context context) {
        this.incurrentList = incurrentList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListner onItemClickListner){
        this.itemClickListener=onItemClickListner;
    }
    @NonNull
    @Override
    public IncurrentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_incurrent, parent, false);
        return new IncurrentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncurrentViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Incurrent currentItem = incurrentList.get(position);

        Glide.with(holder.itemView)
                .load(currentItem.getAnh())
                .centerCrop()
                .placeholder(R.drawable.img_default)
                .into(holder.img);

        String noiDung = currentItem.getNoiDung();
        if (noiDung != null) {
            holder.tvNote.setText(noiDung);
            holder.tvNote.setTextColor(ContextCompat.getColor(context, R.color.red_light));
        } else {
            holder.tvNote.setText("Không có phát sinh");
            holder.tvNote.setTextColor(ContextCompat.getColor(context, R.color.dark_green));
        }

        Float phiPhatSinh = currentItem.getPhiPhatSinh();
        holder.tvFine.setText(phiPhatSinh != null ? formatUtils.formatCurrencyVietnam(phiPhatSinh) : "");

        String hanTra = currentItem.getHanTra();
        try {
            holder.tvReturn.setText(hanTra != null ? formatUtils.formatDateToString(sdf.parse(hanTra)) : "");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        holder.tvName.setText(currentItem.getTenSanPham());
        holder.position=position;
    }

    @Override
    public int getItemCount() {
        return incurrentList == null ? 0 : incurrentList.size();
    }

    public static class IncurrentViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvFine;
        public TextView tvReturn;
        public TextView tvNote;
        public ImageView img, imgMenu;
        public int position;

        public IncurrentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvNameProductIncurrentItem);
            tvNote = itemView.findViewById(R.id.tvNoteIncurrentItem);
            tvFine = itemView.findViewById(R.id.tvFineIncurrentItem);
            tvReturn = itemView.findViewById(R.id.tvDorIncurrentItem);
            img = itemView.findViewById(R.id.imgProductIncurrentItem);
            imgMenu = itemView.findViewById(R.id.imgMenuOptionIncurrent);

            imgMenu.setOnClickListener(view -> {
                if(itemClickListener!=null){
                    itemClickListener.onItemClick(position);
                }
            });
        }
    }


}

