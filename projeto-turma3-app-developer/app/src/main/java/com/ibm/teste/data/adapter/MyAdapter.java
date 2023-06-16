package com.ibm.teste.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ibm.teste.R;
import com.ibm.teste.data.model.MyModel;
import com.ibm.teste.data.repository.FavoriteReposity;
import com.ibm.teste.data.remote.Favorite.FavoriteRequest;
import com.ibm.teste.data.remote.Favorite.FavoriteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<MyModel> myAdapterList;
    private Context context;


    //construtor
    public MyAdapter(List<MyModel> myAdapterList, Context context) {
        this.myAdapterList = myAdapterList;
        this.context = context;
    }

    @NonNull
    @Override
    //inflar o card
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rec_single_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    //recebendo os dados do banco e atribuindo aos objetos
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.title.setText(myAdapterList.get(position).getName());
        holder.country.setText(myAdapterList.get(position).getCountry());
        Glide.with(context).load(myAdapterList.get(position).getImg()).into(holder.imageView);
        holder.imgfav.setOnClickListener(new View.OnClickListener() {
            @Override
            //metodo para favoritar um card
            public void onClick(View view) {
                Integer id_trip = myAdapterList.get(holder.getAdapterPosition()).getTripId();
                Integer id_user = myAdapterList.get(holder.getAdapterPosition()).getUserId();

                FavoriteRequest request = new FavoriteRequest();
                request.setUserId(id_user);
                request.setTripId(id_trip);

                FavoriteReposity.getfavoriteService().saveFavorite(request);
                getFavorite(request, view.getContext());


            }
        });
    }

    @Override
    public int getItemCount() {
        return myAdapterList.size();
    }

    //mapeamento dos objetos
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView, imgfav;
        TextView title, country;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            imgfav = itemView.findViewById(R.id.ivBtnFav);
            title = itemView.findViewById(R.id.tvTitulo);
            country = itemView.findViewById(R.id.tvCountry);
        }
    }


    //metodo Post para registrar o favorito do usuario carregando o ID do usuario, e o ID do card
    public void getFavorite(FavoriteRequest request, Context context){

        Call<FavoriteResponse> registerResponseCall = FavoriteReposity.getfavoriteService().saveFavorite(request);
        registerResponseCall.enqueue(new Callback<FavoriteResponse>() {
            @Override
            public void onResponse(Call<FavoriteResponse> call, Response<FavoriteResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, R.string.favorito_cadastrado_com_sucesso, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FavoriteResponse> call, Throwable t) {
                Toast.makeText(context, R.string.falha_no_cadastro+ t.getLocalizedMessage() + ";" + t.getCause(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
