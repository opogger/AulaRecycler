package com.example.aularecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Produto> listaProdutos = new ArrayList<>();
    RecyclerView recycler;
    Adaptador adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        cadastroInicial();
        recycler = findViewById(R.id.rv);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));

    }
    public void cadastroInicial(){
        Produto p1 = new Produto("Arroz","Alimento",(float)7.49);
        Produto p2 = new Produto("Batata","Alimento",(float)10.99);
        Produto p3 = new Produto("Geladeira","Eletrodoméstico",(float)5000);
        Produto p4 = new Produto("Lâmpada","Elétrica",(float)10.25);
        Produto p5 = new Produto("Dúzia de ovos","Alimento",(float)12);
        Produto p6 = new Produto("Macarrão","Alimento",(float)8.99);
        Produto p7 = new Produto("Java","Dev",(float)300);
        Produto p8 = new Produto("Mochila","Material",(float)150);
        Produto p9 = new Produto("Picanha","Alimento",(float)200);
        listaProdutos.add(p1);
        listaProdutos.add(p2);
        listaProdutos.add(p3);
        listaProdutos.add(p4);
        listaProdutos.add(p5);
        listaProdutos.add(p6);
        listaProdutos.add(p7);
        listaProdutos.add(p8);
        listaProdutos.add(p9);
    }
    public void telacadastro(View v){
        Intent i = new Intent(this, Cadastro.class);
        startActivity(i);
        Cadastro.ListaDeProdutos = listaProdutos;
    }

    @Override
    protected void onResume() {
        super.onResume();

        carrega();
    }
    public void carrega(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Produtos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listaProdutos.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Produto p = (Produto) ds.getValue(Produto.class);
                            listaProdutos.add(p);
                        }
                        adapter = new Adaptador(MainActivity.this, listaProdutos, new Adaptador.OnItemClickListener() {
                            @Override
                            public void onItemClick(Produto p) {

                                Toast.makeText(MainActivity.this, p.getNome(), Toast.LENGTH_SHORT).show();
                            }

                        });

                recycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}