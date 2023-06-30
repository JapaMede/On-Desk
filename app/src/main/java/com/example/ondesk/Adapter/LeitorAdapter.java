package com.example.ondesk.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ondesk.R;
import com.example.ondesk.model.Leitor;

import java.util.List;

public class LeitorAdapter extends RecyclerView.Adapter {



    private List<Leitor> leitorList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leitor, parent, false);
        ViewHolderClass vhClass = new ViewHolderClass(view);
        return vhClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass vhClass = (ViewHolderClass) holder;
        Leitor leitor = leitorList.get(position);
        vhClass.txtNome.setText(leitor.getNome());
        vhClass.txtEmail.setText(leitor.getEmail());
    }

    @Override
    public int getItemCount() {
        return leitorList.size();
    }

    public LeitorAdapter(List<Leitor> leitorList) {
        this.leitorList = leitorList;
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView txtNome, txtEmail;

        public ViewHolderClass(View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtEmail = itemView.findViewById(R.id.txtEmail);
        }
    }
}
