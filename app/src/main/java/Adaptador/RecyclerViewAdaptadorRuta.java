package Adaptador;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omega.turnoapp.R;

import java.util.List;

import Modelo.Ruta;

public class RecyclerViewAdaptadorRuta extends RecyclerView.Adapter<RecyclerViewAdaptadorRuta.ViewHolderRuta> {
    public static class ViewHolderRuta extends RecyclerView.ViewHolder{
        private TextView txtRutaId;
        private TextView txtDescripcion;

        public ViewHolderRuta (View itemView){
            super(itemView);
            txtRutaId=  (TextView) itemView.findViewById(R.id.txtvRutaId);
            txtDescripcion= (TextView) itemView.findViewById(R.id.txtvRuta);
        }
    }

    public List<Ruta> RutaLista;

    public RecyclerViewAdaptadorRuta(List<Ruta> rutaLista){
        this.RutaLista = rutaLista;
    }

    @Override
    public RecyclerViewAdaptadorRuta.ViewHolderRuta onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ruta, parent, false);
        RecyclerViewAdaptadorRuta.ViewHolderRuta  viewHolder= new RecyclerViewAdaptadorRuta.ViewHolderRuta (view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdaptadorRuta.ViewHolderRuta holder, int position){
        holder.txtRutaId.setText(RutaLista.get(position).getRutaId());
        holder.txtDescripcion .setText(RutaLista.get(position).getDescripcion());
    }

    @Override
    public int getItemCount(){
        return RutaLista.size();
    }

}
