package com.example.instagram_clone;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram_clone.Fragment.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        isFollowing(user.getId(),holder.btnFollow);

        if (user.getId().equals(firebaseUser.getUid())){
            holder.btnFollow.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("profileid", user.getId());
                editor.apply();

                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
            }
        });



        holder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.btnFollow.getText().toString().equals("follow"))
                {
                   FirebaseFirestore.getInstance().collection("Follow")
                           .document(firebaseUser.getUid())
                           .collection("following").document(user.getId()).set(true);

                    FirebaseFirestore.getInstance().collection("Follow")
                            .document(user.getId())
                            .collection("followers").document(firebaseUser.getUid()).set(true);
                }
                else
                {
                    FirebaseFirestore.getInstance().collection("Follow")
                            .document(firebaseUser.getUid()).collection("following")
                            .document(user.getId()).set(true);

                    FirebaseFirestore.getInstance().collection("Follow")
                            .document(user.getId()).collection("followers")
                            .document(firebaseUser.getUid()).set(true);
                }
            }
        });

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
    private void isFollowing(String userid, Button button) {

        CollectionReference referance = FirebaseFirestore.getInstance().collection("Follow")
                .document(firebaseUser.getUid()).collection("followers");

        referance.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful())
                {
                    QuerySnapshot document = task.getResult();
                    System.out.println(document.toString());
                    if (document.toString().equals(userid))
                    {
                        button.setText("following");
                    }
                    else{
                        button.setText("follow");
                    }
                }


            }
        });

    }

}
