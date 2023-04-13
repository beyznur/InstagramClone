package com.example.instagram_clone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.emulators.EmulatedServiceSettings;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<User> userArrayList;

    private FirebaseUser firebaseUser;

    public UserAdapter(Context mContext, ArrayList<User> userArrayList) {
        this.mContext = mContext;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final  User user= userArrayList.get(position);

        holder.btnFollow.setVisibility(View.VISIBLE);
        holder.username.setText(user.getUserName());
        holder.name.setText(user.getName());
        Glide.with(mContext).load(user.getImageUrl()).into(holder.imageProfile);

        if (user.getId().equals(firebaseUser.getUid())){
            holder.btnFollow.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView username,name;
        CircleImageView imageProfile;
        Button btnFollow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            name = itemView.findViewById(R.id.name);
            imageProfile = itemView.findViewById(R.id.image_profile);
            btnFollow = itemView.findViewById(R.id.btn_follow);

        }
    }
}
