package falaai.app.com.falaai.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import falaai.app.com.falaai.Activity.ConversaActivity;
import falaai.app.com.falaai.Activity.MainActivity;
import falaai.app.com.falaai.R;
import falaai.app.com.falaai.adapter.ContatoAdapter;
import falaai.app.com.falaai.helper.Preferencias;
import falaai.app.com.falaai.model.Contato;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Contato> contatos;
    private DatabaseReference mDatabase;
    private ValueEventListener valueEventListenerContato;

    public ContatosFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        contatos = new ArrayList<Contato>();

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contatos, container, false);
        listView = view.findViewById(R.id.listViewContatos);
/*        adapter = new ArrayAdapter(
                getActivity(),
                R.layout.lista_contatos,
                contatos
        );*/

        adapter = new ContatoAdapter(getActivity(), contatos);

        listView.setAdapter(adapter);

        Preferencias preferencias = new Preferencias(getActivity());

        String identificadorUsuarioLogado = preferencias.getIdentificador();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Contatos")
                                                                 .child(identificadorUsuarioLogado);

        valueEventListenerContato = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contatos.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                Contato contato = contatos.get(position);

                intent.putExtra("Nome", contato.getNome());
                intent.putExtra("Email", contato.getEmail());

                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(valueEventListenerContato);
    }

    @Override
    public void onStop() {
        super.onStop();
        mDatabase.removeEventListener(valueEventListenerContato);
    }

}
