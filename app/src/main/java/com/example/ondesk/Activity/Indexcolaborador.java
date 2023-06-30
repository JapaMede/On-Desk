package com.example.ondesk.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.ondesk.Adapter.EmprestimoAdapter;
import com.example.ondesk.FragmentColab.CatalogoColab;
import com.example.ondesk.FragmentColab.MenuAtrasados;
import com.example.ondesk.FragmentColab.MenuRua;
import com.example.ondesk.FragmentColab.UsuarioColab;
import com.example.ondesk.R;
import com.example.ondesk.Service.EmprestimoService;
import com.example.ondesk.databinding.IndexColaboradorBinding;
import com.example.ondesk.model.Emprestimo;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class Indexcolaborador extends AppCompatActivity {

    IndexColaboradorBinding binding;
    BadgeDrawable badgeDrawable;
    List<Emprestimo> atrasados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = IndexColaboradorBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        BottomNavigationView navbar = binding.NavegationBarColab;
        MenuItem menuA = navbar.getMenu().findItem(R.id.menuAtrasado);

        badgeDrawable = navbar.getOrCreateBadge(menuA.getItemId());
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(0);

        atrasados = EmprestimoService.getAtrasados();

        replaceFragment(new CatalogoColab());

        binding.NavegationBarColab.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.catalogoColab:
                    replaceFragment(new CatalogoColab());
                    atrasados = EmprestimoService.getAtrasados();
                    try {
                        badgeDrawable.setNumber(atrasados.size());
                    }catch (RuntimeException e){
                        badgeDrawable.setNumber(0);
                    }
                    break;
                case R.id.menuRua:
                    replaceFragment(new MenuRua());
                    atrasados = EmprestimoService.getAtrasados();
                    try {
                        badgeDrawable.setNumber(atrasados.size());
                    }catch (RuntimeException e){
                        badgeDrawable.setNumber(0);
                    }
                    break;
                case R.id.menuAtrasado:
                    replaceFragment(new MenuAtrasados());
                    atrasados = EmprestimoService.getAtrasados();
                    try {
                        badgeDrawable.setNumber(atrasados.size());
                    }catch (RuntimeException e){
                        badgeDrawable.setNumber(0);
                    }
                    break;
                case R.id.euColab:
                    replaceFragment(new UsuarioColab());
                    atrasados = EmprestimoService.getAtrasados();
                    try {
                        badgeDrawable.setNumber(atrasados.size());
                    }catch (RuntimeException e){
                        badgeDrawable.setNumber(0);
                    }
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.layoutIndexColab, fragment);
        ft.commit();
    }
}