package falaai.app.com.falaai.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

import falaai.app.com.falaai.model.Mensagem;

public class MensagemAdapter extends ArrayAdapter<Mensagem>{

    private Context context;

    public MensagemAdapter(@NonNull Context c, @NonNull List<Mensagem> objects) {
        super(c, 0, objects);
        this.context = c;
    }
}
