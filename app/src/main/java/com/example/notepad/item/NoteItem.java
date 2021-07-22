package com.example.notepad.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepad.R;
import com.example.notepad.models.NoteModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoteItem extends RecyclerView.Adapter<NoteItem.ItemRowHolder>
{
    private List<NoteModel> datalist;
    private Context mContext;
    private int rowLayout;

    public NoteItem(List<NoteModel> datalist, Context mContext, int rowLayout) {
        this.datalist = datalist;
        this.mContext = mContext;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @NotNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        return new ItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NoteItem.ItemRowHolder holder, int position) {
        final NoteModel singleItem = datalist.get(position);
        holder.keterangankeg.setText(singleItem.getKeterangan());
        holder.tanggalkeg.setText(singleItem.getTanggal());
        holder.waktukeg.setText(singleItem.getWaktu());
        holder.lokasikeg.setText(singleItem.getLokasi());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder{

        TextView keterangankeg,waktukeg,tanggalkeg,lokasikeg;
        public ItemRowHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            keterangankeg = itemView.findViewById(R.id.keterangan);
            waktukeg = itemView.findViewById(R.id.waktu);
            tanggalkeg = itemView.findViewById(R.id.tanggal);
            lokasikeg = itemView.findViewById(R.id.lokasi);

        }
    }
}
