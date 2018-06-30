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
import falaai.app.com.falaai.helper.Preferencias;
import falaai.app.com.falaai.model.Mensagem;

public class MensagemAdapter extends ArrayAdapter<Mensagem>{

    private Context context;
    private ArrayList<Mensagem> mensagens;

    public MensagemAdapter(@NonNull Context c, @NonNull ArrayList<Mensagem> objects) {
        super(c, 0, objects);
        this.context = c;
        this.mensagens = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if (mensagens != null){
            Mensagem mensagem = mensagens.get(position);
            Preferencias preferencias = new Preferencias(context);
            String usuarioLogado = preferencias.getIdentificador();

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            if (usuarioLogado.equals(mensagem.getIdUsuario())){
                view = layoutInflater.inflate(R.layout.item_mensagem_direita, parent, false);
            }
            else {
                view = layoutInflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
            }

            TextView textView = view.findViewById(R.id.tv_mensagem);
            textView.setText(mensagem.getMensagem());
        }

        return view;
    }
}
