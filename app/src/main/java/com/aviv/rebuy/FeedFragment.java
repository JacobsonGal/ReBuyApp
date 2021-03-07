package com.aviv.rebuy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aviv.rebuy.Model.Model;
import com.aviv.rebuy.Model.Product;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;


import java.util.List;


public class FeedFragment extends Fragment {

    FeedViewModel viewModel;
    Button addBtn;

    SwipeRefreshLayout sref;
    public FeedFragment() {
        // Required empty public constructor
    }



    void reloadData(){
        Model.instance.refreshAllProducts(new Model.GetAllProductsListener() {
            @Override
            public void onComplete() {
                sref.setRefreshing(false);
            }
        });
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // reloadData();
        View view = inflater.inflate(R.layout.fragment_feed,container,false);
        viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
      //TODO: add button and etc
        sref = view.findViewById(R.id.swiperefresh);

        sref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sref.setRefreshing(true);
                reloadData();

            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerForSuggestions);
        ListAdapter listAdapter = new ListAdapter();
        recyclerView.setAdapter((listAdapter));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

    listAdapter.setOnClickListener(new OnItemClickListener() {
    @Override
    public void onItemClick(int position) {
        Log.d("TAG123","row was clicked" + position);
        FeedFragmentDirections.ActionFeedFragmentToDetailsFragment action = FeedFragmentDirections.actionFeedFragmentToDetailsFragment(position);
        Navigation.findNavController(view).navigate(action);
    }
    });

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.VISIBLE);

        viewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> students) {
                listAdapter.notifyDataSetChanged();
            }
        });


        return view;
    }
    interface  OnItemClickListener{
    void onItemClick(int position);
    }

        private class ListViewHolder extends RecyclerView.ViewHolder {
            public OnItemClickListener listener;
            private TextView itemText;
            private ImageView itemImage;
            private  TextView descText;
            int position;
            private LiveData<List<Product>> productList = Model.instance.getAllProducts();



            public ListViewHolder(View itemView){
                super(itemView);
                itemText = (TextView) itemView.findViewById(R.id.textView4);
                itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
                descText = (TextView) itemView.findViewById(R.id.description_textView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
    listener.onItemClick(position);
                    }
                });

            }

            public void bindView(int position){
                itemText.setText(viewModel.getList().getValue().get(position).getName());
                descText.setText(viewModel.getList().getValue().get(position).getDescription());
                Picasso.get().load(viewModel.getList().getValue().get(position).getImageUrl()).into(itemImage);
            this.position=position;

            }


        }

    public class ListAdapter extends RecyclerView.Adapter {
        private OnItemClickListener listener;

        void setOnClickListener(OnItemClickListener listener){
            this.listener =listener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_list_items,parent,false);
            ListViewHolder holder = new ListViewHolder(view);
            holder.listener = listener;

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ListViewHolder) holder ).bindView(position);


        }

        @Override
        public int getItemCount() {
            if (viewModel.getList().getValue() == null){
                return 0;
            }
            return viewModel.getList().getValue().size();
        }
    }


}