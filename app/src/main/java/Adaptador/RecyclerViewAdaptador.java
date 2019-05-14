package Adaptador;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omega.turnoapp.R;

import java.util.List;

import Modelo.Chofer;
import Modelo.TurnoLogic;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtAlias;
        private TextView txtTurno;
        private TextView txtCantPasajeros;
        private TextView txtTiempo;
        private ImageView fotoChofer;

        public ViewHolder (View itemView){
            super(itemView);
            txtAlias=  (TextView) itemView.findViewById(R.id.txtvAlias);
            txtTurno= (TextView) itemView.findViewById(R.id.txtvTurno);
            txtCantPasajeros= (TextView) itemView.findViewById(R.id.txtvCantPasajeros);
            txtTiempo =(TextView) itemView.findViewById(R.id.txtvTiempo);
            fotoChofer=(ImageView) itemView.findViewById(R.id.imgFotoChofer);
        }
    }

    public List<TurnoLogic> TurnoLista;

    public RecyclerViewAdaptador(List<TurnoLogic> choferLista){
        this.TurnoLista= choferLista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_turno, parent, false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.txtAlias.setText(TurnoLista.get(position).getAlias());
        holder.txtTurno .setText(TurnoLista.get(position).getTurno());
        holder.txtCantPasajeros .setText(TurnoLista.get(position).getCantPasajero());
        holder.txtTiempo.setText(TurnoLista.get(position).getTiempo());
        holder.fotoChofer.setImageResource(R.drawable.foto_perfil);
    }

    @Override
    public int getItemCount(){
        return TurnoLista.size();
    }
}
