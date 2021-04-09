package com.example.logindemo.Retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logindemo.R;

import java.util.ArrayList;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.UserDataViewHolder> {

    private final Context mContext;
    private final ArrayList<UserData> mUserData;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        onItemClickListener = listener;
    }

    public UserDataAdapter(Context context, ArrayList<UserData> userData) {

        mContext = context;
        mUserData = userData;
    }

    @NonNull
    @Override
    public UserDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_data,parent,false);
        return new UserDataViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserDataViewHolder holder, int position) {

        UserData userdata = mUserData.get(position);

        String userName = userdata.getName();
        String userGender = userdata.getGender();
        String userCountry = userdata.getCountry();
        String userAge = userdata.getAge();
        String userDescription = userdata.getDescription();

        holder.mNameView.setText("Name : " + userName);
        holder.mGenderView.setText("Gender : " + userGender);
        holder.mCountryView.setText("Country : " + userCountry);
        holder.mAgeView.setText("Age : " + userAge);
    }

    @Override
    public int getItemCount() {
        return mUserData.size();
    }

    public class UserDataViewHolder extends RecyclerView.ViewHolder {

        public TextView mNameView;
        public TextView mGenderView;
        public TextView mCountryView;
        public TextView mAgeView;

        public UserDataViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameView = itemView.findViewById(R.id.name);
            mGenderView = itemView.findViewById(R.id.gender);
            mCountryView = itemView.findViewById(R.id.country);
            mAgeView = itemView.findViewById(R.id.age);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
