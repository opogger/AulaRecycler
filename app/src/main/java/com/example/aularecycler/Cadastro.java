package com.example.aularecycler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Cadastro extends AppCompatActivity {
EditText NomeP, TipoP, CustoP;
static ArrayList<Produto> ListaDeProdutos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().hide();
        NomeP = findViewById(R.id.NomeP);
        TipoP = findViewById(R.id.TipoP);
        CustoP = findViewById(R.id.CustoP);
    }
    public void CadastrarProduto(View v){
        try {
            String nomecad = NomeP.getText().toString();
            String tipocad = TipoP.getText().toString();
            float custocad = Float.parseFloat(CustoP.getText().toString());
            if (verificaSeExiste(nomecad)) {
                Toast.makeText(this, "O produto já existe!", Toast.LENGTH_SHORT).show();
            }
            else {
                Produto p = new Produto(nomecad, tipocad, custocad);
                ListaDeProdutos.add(p);
                super.onBackPressed();
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Insira um valor válido!", Toast.LENGTH_SHORT).show();
        }
        }


    public boolean verificaSeExiste(String nome){
        for (Produto u : ListaDeProdutos){
            if (nome.equals(u.getNome())){
                return true;
            }
        }
        return false;
}
}


