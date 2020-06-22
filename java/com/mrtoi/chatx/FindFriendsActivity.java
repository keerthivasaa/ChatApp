package com.mrtoi.chatx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendsActivity extends AppCompatActivity
{

    private Toolbar mToolbar;
    private ImageButton SearchButton;
    private EditText SearchInputText;

    private RecyclerView SearchResultList;

    private DatabaseReference allUsersDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        allUsersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");

        SearchResultList = (RecyclerView) findViewById(R.id.find_friends_recycler_list);
        SearchResultList.setHasFixedSize(true);
        SearchResultList.setLayoutManager(new LinearLayoutManager(this));

        SearchButton = (ImageButton) findViewById(R.id.search_people_friends_button);
        SearchInputText = (EditText) findViewById(R.id.search_box_input);

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String searchBoxInput = SearchInputText.getText().toString();
                SearchPeopleAndFriends(searchBoxInput);
            }
        });
    }

    private void SearchPeopleAndFriends(String searchBoxInput)
    {
        Toast.makeText(this, "Searching..", Toast.LENGTH_LONG).show();

        FirebaseRecyclerOptions<Contacts> options =
                new FirebaseRecyclerOptions.Builder<Contacts>()
                        .setQuery(allUsersDatabaseRef.orderByChild("name")
                                .startAt(searchBoxInput).endAt(searchBoxInput + "\uf8ff"), Contacts.class)
                        .build();

        FirebaseRecyclerAdapter<Contacts, FindFriendsViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Contacts, FindFriendsViewHolder>(options)
        {

            @NonNull
            @Override
            public FindFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
            {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_display_layout, viewGroup, false);
                FindFriendsViewHolder viewHolder = new FindFriendsViewHolder(view);
                return viewHolder;
            }

            @Override
            protected void onBindViewHolder(@NonNull FindFriendsViewHolder findFriendsViewHolder, final int i, @NonNull Contacts findFriends)
            {
                findFriendsViewHolder.setFullname(findFriends.getName());
                findFriendsViewHolder.setStatus(findFriends.getStatus());
                findFriendsViewHolder.setProfileimage(getApplicationContext(),findFriends.getImage());

                findFriendsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        String visit_user_id = getRef(i).getKey();

                        Intent profileIntent = new Intent(FindFriendsActivity.this, ProfileActivity.class);
                        profileIntent.putExtra("visit_user_id", visit_user_id);
                        startActivity(profileIntent);
                    }
                });
            }
        };

        SearchResultList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }


    public static class FindFriendsViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public FindFriendsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setProfileimage(Context ctx, String profileimage) {
            CircleImageView myImage = (CircleImageView) mView.findViewById(R.id.users_profile_image);
            Picasso.get().load(profileimage).placeholder(R.drawable.profile).into(myImage);
        }

        public void setFullname(String fullname)
        {
            TextView myName = (TextView) mView.findViewById(R.id.user_profile_name);
            myName.setText(fullname);
        }

        public void setStatus (String status)
        {
            TextView myStatus = (TextView) mView.findViewById(R.id.user_status);
            myStatus.setText(status);
        }
    }
}

