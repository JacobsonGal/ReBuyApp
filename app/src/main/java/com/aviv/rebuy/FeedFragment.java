package com.aviv.rebuy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.aviv.rebuy.Model.Model;
import com.aviv.rebuy.Model.Product;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;


public class FeedFragment extends Fragment {

    FeedViewModel viewModel;
    Button addBtn;
    ProgressBar pb;
    SwipeRefreshLayout sref;
    SearchView search;
    String searchText;
    ListAdapter listAdapter;
    Fragment feed = this;
    LiveData<List<Product>> liveData;

    public FeedFragment() {
        // Required empty public constructor
    }


    void reloadData() {
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
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        viewModel = new ViewModelProvider(this).get(FeedViewModel.class);

        //TODO: add button and etc
        sref = view.findViewById(R.id.swiperefresh);
        pb = view.findViewById(R.id.feed_progress);
        search = view.findViewById(R.id.feedFrag_inputSearch);

        sref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sref.setRefreshing(true);
                reloadData();
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerForSuggestions);

        listAdapter = new ListAdapter();
        recyclerView.setAdapter((listAdapter));


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                listAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });


        listAdapter.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG123", "row was clicked" + position);
                FeedFragmentDirections.ActionFeedFragmentToDetailsFragment action = FeedFragmentDirections.actionFeedFragmentToDetailsFragment(position);
                Navigation.findNavController(view).navigate(action);
            }
        });

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.VISIBLE);

        viewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> Products) {
                listAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }


    List<String> filterList(List<String> list, String regex) {
        return list.stream().filter(s -> s.matches(regex)).collect(Collectors.toList());
    }


    interface OnItemClickListener {
        void onItemClick(int position);
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {
        public OnItemClickListener listener;
        private TextView itemText;
        private ImageView itemImage;
        private TextView descText;
        int position;
        private LiveData<List<Product>> productList = Model.instance.getAllProducts();


        public ListViewHolder(View itemView) {
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

    public class ListAdapter extends RecyclerView.Adapter  {
        private OnItemClickListener listener;
        private List<Product> copyList;
        private List<Product> searchList;
        private LiveData<List<Product>> productList = Model.instance.getAllProducts();

        void setOnClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }


        public ListAdapter() {

//            copyList = productList.getValue();
//            if (copyList == null)
//                Log.d("TAG222", "copylist is null");
//
//
//            searchList = new ArrayList<Product>(copyList);


        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_list_items, parent, false);
            ListViewHolder holder = new ListViewHolder(view);
            holder.listener = listener;

           Timer t = new Timer();
           t.schedule(new TimerTask() {
               @Override
               public void run() {
                   pb.setVisibility(View.INVISIBLE);
               }
          }, 2500);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ListViewHolder) holder).bindView(position);
        }

        @Override
        public int getItemCount() {
            if (viewModel.getList().getValue() == null) {
                return 0;
            }
            return viewModel.getList().getValue().size();
        }

//        @Override
//        public Filter getFilter() {
//            return exampleFilter;
//        }
//
//        private Filter exampleFilter = new Filter() {
//
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                List<Product> filteredList = new ArrayList<Product>();
//                if (constraint == null || constraint.length() == 0) {
//                    filteredList.addAll(copyList);
//                } else {
//                    String filterPattern = constraint.toString().toLowerCase().trim();
//                    for (Product item : copyList) {
//                        if (item.getName().toLowerCase().contains(filterPattern) || item.getDescription().toLowerCase().contains(filterPattern)) {
//                            filteredList.add(item);
//                        }
//                    }
//                }
//                FilterResults results = new FilterResults();
//                results.values = filteredList;
//                return results;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                searchList.clear();
//                searchList.addAll((List) results.values);
//                notifyDataSetChanged();
//            }
//        };
    }

}