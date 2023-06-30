package com.example.ondesk.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ondesk.R;
import com.example.ondesk.model.Emprestimo;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class EmprestimoAdapter extends RecyclerView.Adapter {


    public EmprestimoAdapter(List<Emprestimo> emprestimoList) {
        this.emprestimoList = emprestimoList;
    }

    private List<Emprestimo> emprestimoList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livro_empretimo, parent, false);
        ViewHolderClass vhClass = new ViewHolderClass(view);
        return vhClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass vhClass = (ViewHolderClass) holder;
        Emprestimo e = emprestimoList.get(position);

        Calendar agora = Calendar.getInstance();

        Calendar saida = Calendar.getInstance();
        saida.setTimeInMillis(e.getDataSaida());

        Calendar prevista = Calendar.getInstance();
        prevista.setTimeInMillis(e.getDataPrevista());

        vhClass.tvNomeLivro.setText(e.getNomeLivro());
        vhClass.tvDataSaida.setText(calenderToString(saida));
        vhClass.tvDataPrevista.setText(calenderToString(prevista));
        vhClass.txtNomeLeitor.setText(e.getNomeLeitor());

        if(agora.after(prevista)){
            vhClass.tvAviso.setText("ATRASADO ");
        }

        Uri i = Uri.parse(e.getUrlImage());

        Picasso.get().load(i)
                .fit()
                .centerInside()
                .into(vhClass.image);

    }

    @Override
    public int getItemCount() {
        return emprestimoList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView tvNomeLivro, tvDataSaida, tvDataPrevista, tvAviso, txtNomeLeitor;
        ImageView image;

        public ViewHolderClass(View itemView) {
            super(itemView);
            tvNomeLivro = itemView.findViewById(R.id.txtNomeLivroEmprestimo);
            tvDataSaida = itemView.findViewById(R.id.txtDataSaida);
            tvDataPrevista = itemView.findViewById(R.id.txtDataPrevisata);
            tvAviso = itemView.findViewById(R.id.txtAviso);
            txtNomeLeitor = itemView.findViewById(R.id.txtNomeLeitor);
            image = itemView.findViewById(R.id.item_image);

        }
    }

    private String calenderToString(Calendar c){
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        String s = dia+"/"+(mes+1)+"/"+ano;
        return s;
    }
}
