package falaai.app.com.falaai.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import falaai.app.com.falaai.R;
import falaai.app.com.falaai.model.Contato;

public class ContatoAdapter extends ArrayAdapter<Contato> {

    Context context;
    private ArrayList<Contato> contatos;

    public ContatoAdapter(@NonNull Context c, @NonNull ArrayList<Contato> objects) {
        super(c, 0, objects);
        this.context = c;
        this.contatos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if (contatos != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_contatos, parent, false);

            TextView textView = view.findViewById(R.id.texto_nome_contato);

            Contato contato = contatos.get(position);

            textView.setText(contato.getNome());
        }

        return view;
    }
}
