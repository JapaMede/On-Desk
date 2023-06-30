package com.example.ondesk.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ondesk.R;
import com.example.ondesk.model.Livro;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LivroAdapter extends RecyclerView.Adapter {

    private List<Livro> livros;

    public LivroAdapter(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livro, parent, false);
        ViewHolderClass vhClass = new ViewHolderClass(view);
        return vhClass;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass vhClass = (ViewHolderClass) holder;
        Livro livro = livros.get(position);
        vhClass.bind(livro);

    }

    @Override
    public int getItemCount() {
        return livros.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView tvNome, tvCategoria, tvAutor;
        ImageView img;

        public ViewHolderClass(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.IMG);
            tvNome = itemView.findViewById(R.id.txtNomeLivro);
            tvCategoria = itemView.findViewById(R.id.txtCategoria);
            tvAutor = itemView.findViewById(R.id.txtAutor);
        }
        public void bind(Livro l) {
            tvNome.setText(l.getNome());
            tvCategoria.setText(l.getCategoria());
            tvAutor.setText(l.getAutor());
            Uri i = Uri.parse(l.getUrlImage());

            Picasso.get().load(i)
                    .fit()
                    .centerInside()
                    .into(img);
        }
    }
}
