package com.example.ondesk.Activity;



import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.ondesk.FragmentLeitor.CatalogoLeitor;
import com.example.ondesk.FragmentLeitor.MeusLivros;
import com.example.ondesk.FragmentLeitor.UsuarioLeitor;
import com.example.ondesk.R;
import com.example.ondesk.Service.EmprestimoService;
import com.example.ondesk.databinding.IndexLeitorBinding;
import com.example.ondesk.model.Emprestimo;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Indexleitor extends AppCompatActivity {

    IndexLeitorBinding binding;
    Calendar dataAtual, dataEntega;
    List<Emprestimo> atrasados, emprestimos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = IndexLeitorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataAtual = Calendar.getInstance();
        dataEntega = Calendar.getInstance();


        BottomNavigationView navbar = binding.NavegationBarLeitor;
        MenuItem menuA = navbar.getMenu().findItem(R.id.meus_livros);
        BadgeDrawable badgeDrawable = navbar.getOrCreateBadge(menuA.getItemId());
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(0);


        replaceFragment(new MeusLivros());

        binding.NavegationBarLeitor.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.meus_livros:
                    replaceFragment(new MeusLivros());
                    badgeDrawable.setNumber(contadorAtrasados());
                    break;
                case R.id.catalogo:
                    replaceFragment(new CatalogoLeitor());
                    badgeDrawable.setNumber(contadorAtrasados());
                    break;
                case R.id.usuario:
                    replaceFragment(new UsuarioLeitor());
                    badgeDrawable.setNumber(contadorAtrasados());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.layoutIndexLeitor, fragment);
        ft.commit();
    }

    private int contadorAtrasados(){
        atrasados = new ArrayList<>();

        try {
            emprestimos = EmprestimoService.getEmprestimos();
            for (int i = 0; i < emprestimos.size(); i++){
                Emprestimo e = emprestimos.get(i);
                dataEntega.setTimeInMillis(e.getDataPrevista());
                if (dataAtual.after(dataEntega)){
                    atrasados.add(e);
                }
            }
        }catch (RuntimeException e){
            Log.i("catch", e.getMessage());
        }
        return atrasados.size();
    }
}
