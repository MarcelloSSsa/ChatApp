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
import falaai.app.com.falaai.model.Conversa;

public class ConversaAdapter extends ArrayAdapter<Conversa> {

    private ArrayList<Conversa> conversas;
    private Context context;
    private Conversa conversa;

    public ConversaAdapter(@NonNull Context c, @NonNull ArrayList<Conversa> objects) {
        super(c, 0, objects);
        this.context = c;
        this.conversas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if( conversas != null ){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_conversas, parent, false);

            TextView nome = view.findViewById(R.id.texto_nome_conversa);
            TextView ultimaConversa = view.findViewById(R.id.texto_mensagem_conversa);

            conversa = conversas.get( position );
            nome.setText( conversa.getNome() );
            ultimaConversa.setText( conversa.getMensagem() );

        }

        return view;
    }
}
