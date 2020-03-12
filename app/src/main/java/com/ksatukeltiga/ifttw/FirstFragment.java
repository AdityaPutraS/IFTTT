package com.ksatukeltiga.ifttw;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ListAdapter mListadapter;
    private RoutineRepository routineRepository;
    private LiveData<List<Routine>> activeRoutine;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.println(Log.INFO, "FirstFragment", "masuk onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFragFirst);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        forceUpdate(true);

        return view;
    }

    public void forceUpdate(boolean status)
    {
        routineRepository = new RoutineRepository(getActivity().getApplicationContext());
        activeRoutine = routineRepository.getRoutine(status);
        activeRoutine.observe(this.getViewLifecycleOwner(), new Observer<List<Routine>>() {
            @Override
            public void onChanged(@Nullable List<Routine> listRoutine) {
                Log.println(Log.INFO, "observer", "Observer terpanggil");
                mListadapter = new ListAdapter(getActivity().getApplicationContext(), new ArrayList<Routine>(listRoutine));
                mRecyclerView.setAdapter(mListadapter);
            }
        });
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.println(Log.INFO, "FirstFragment", "masuk onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.println(Log.INFO, "FirstFragment", "masuk onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.println(Log.INFO, "FirstFragment", "masuk onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.println(Log.INFO, "FirstFragment", "masuk onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        forceUpdate(true);
        Log.println(Log.INFO, "FirstFragment", "masuk onResume");
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {
        private ArrayList<Routine> dataList;
        private Context context;

        public ListAdapter(Context context, ArrayList<Routine> data)
        {
            this.context = context;
            this.dataList = data;
            for (Routine r: dataList) {
                r.initRoutine(context);
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textKondisi;
            TextView textAksi;
            Switch switchRoutine;

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textKondisi = (TextView) itemView.findViewById(R.id.kondisi);
                this.textAksi = (TextView) itemView.findViewById(R.id.aksi);
                this.switchRoutine = (Switch) itemView.findViewById(R.id.switchRoutine);
            }
        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position)
        {
//            Log.println(Log.INFO, "bindView", dataList.get(position).getKondisi().getConditionString());
            holder.textKondisi.setText(dataList.get(position).getKondisi().getConditionString());
            holder.textAksi.setText(dataList.get(position).getAksi().getActionString());
            holder.switchRoutine.setChecked(dataList.get(position).isActive());

            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(getActivity(), "Routine " + (position + 1) + " is clicked.", Toast.LENGTH_SHORT).show();
                }
            });

            holder.switchRoutine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String state = isChecked ? "started" : "stopped" ;
                    Routine temp = dataList.get(position);
                    temp.setActive(isChecked);
                    RoutineRepository routineRepository = new RoutineRepository(context);
                    routineRepository.deleteRoutine(dataList.get(position));
                    routineRepository.insertRoutine(temp);
                    Toast.makeText(getActivity(), "Routine " + (position + 1) + " " + state, Toast.LENGTH_SHORT).show();
//                    dataList.remove(position);
//                    forceUpdate(true);
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }
}
