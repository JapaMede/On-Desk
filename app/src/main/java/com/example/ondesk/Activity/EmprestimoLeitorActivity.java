package com.example.ondesk.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ondesk.Adapter.LeitorAdapter;
import com.example.ondesk.Listener.RecyclerItemClickListener;
import com.example.ondesk.R;
import com.example.ondesk.Service.EmprestimoService;
import com.example.ondesk.Service.LeitorService;
import com.example.ondesk.model.Emprestimo;
import com.example.ondesk.model.Leitor;
import com.example.ondesk.model.Livro;

import java.util.Calendar;
import java.util.List;

public class EmprestimoLeitorActivity extends AppCompatActivity {

    LeitorAdapter adapter;
    LeitorService lService;
    List<Leitor> leitorList;
    RecyclerView rv;

    AlertDialog.Builder builder;
    AlertDialog dialog;
    Livro livro;

    EditText inputDataEntrega;
    TextView lblNomeLivro, lblCategoria, lblAutor, lblNomeLeitor, lblEmail;
    Button btnDimiss, btnConfirmar;
    ImageButton btnVoltar;
    Calendar dataSaida, dataPrevista;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emprestimo_leitor);

        Intent i = getIntent();
        if (i.hasExtra("Livro")){
            livro = (Livro) i.getSerializableExtra("Livro");
            Log.i("TAGL", livro.getNome());
        }

        btnVoltar = findViewById(R.id.btnVoltarEmpLei);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmprestimoLeitorActivity.this, EmprestimoLivroActivity.class));
                finish();
            }
        });

        rv = findViewById(R.id.rvSelecionaLeitor);
        rv.setLayoutManager(new LinearLayoutManager(this));
        lService = new LeitorService();
        adapter = lService.getAdapterEmprestimo();
        rv.setAdapter(adapter);

        leitorList = LeitorService.getListaVerificado();

        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Leitor leitor = leitorList.get(position);
                showDialog(leitor);
            }
        }));
    }


    public void showDialog(Leitor L){
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        final View popUp = getLayoutInflater().inflate(R.layout.dialog_emprestimo, null);

        lblNomeLivro = popUp.findViewById(R.id.labelNomeLivro);
        lblCategoria = popUp.findViewById(R.id.labelCategoria);
        lblAutor = popUp.findViewById(R.id.labelAutor);

        lblNomeLeitor = popUp.findViewById(R.id.labelNomeLeitor);
        lblEmail = popUp.findViewById(R.id.labelEmailLeitor);

        inputDataEntrega = popUp.findViewById(R.id.inputDataEntrega);
        inputDataEntrega.setShowSoftInputOnFocus(false);
        inputDataEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataSaida = Calendar.getInstance();

                int ano = dataSaida.get(Calendar.YEAR);
                int mes = dataSaida.get(Calendar.MONTH);
                int dia = dataSaida.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        popUp.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                inputDataEntrega.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                Calendar dataSelecionada = Calendar.getInstance();
                                dataSelecionada.set(Calendar.YEAR, year);
                                dataSelecionada.set(Calendar.MONTH, monthOfYear);
                                dataSelecionada.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                dataPrevista = dataSelecionada;
                            }
                        },
                        ano, mes, dia);
                datePickerDialog.show();
            }
        });

        lblNomeLivro.setText(livro.getNome());
        lblAutor.setText(livro.getAutor());
        lblCategoria.setText(livro.getCategoria());

        lblNomeLeitor.setText(L.getNome());
        lblEmail.setText(L.getEmail());

        builder.setView(popUp);
        dialog = builder.create();
        dialog.show();

        btnDimiss = popUp.findViewById(R.id.btnDemiss);
        btnDimiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnConfirmar = popUp.findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Emprestimo em = new Emprestimo();
                em.setIdLeitor(L.getId());
                em.setNomeLeitor(L.getNome());

                em.setNomeLivro(livro.getNome());
                em.setIdLivro(String.valueOf(livro.getId()));
                em.setDataSaida(dataSaida.getTimeInMillis());
                em.setDataPrevista(dataPrevista.getTimeInMillis());

                em.setUrlImage(livro.getUrlImage());

                EmprestimoService es = new EmprestimoService();
                es.empresta(em);
                dialog.dismiss();
                finish();
            }
        });
    }
}
