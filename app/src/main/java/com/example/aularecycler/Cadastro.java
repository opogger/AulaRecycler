package com.example.aularecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                //ListaDeProdutos.add(p);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference.child("produtos").child(p.getNome()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Toast.makeText(Cadastro.this, "Este produto já existe!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            p.salvar();
                            Cadastro.super.onBackPressed();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


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


