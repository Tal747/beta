package com.tal.beta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import static com.tal.beta.FBref.*;

public class ListActivity extends AppCompatActivity {

    Button btnNew;
    EditText etSrc;
    ListView lvGroups;

    ArrayList<String> stringList = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    boolean uType;

    ValueEventListener valueEventListener;
    FirebaseUser user = refAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btnNew = (Button) findViewById(R.id.btnNew);
        etSrc = (EditText) findViewById(R.id.etSrc);
        lvGroups = (ListView) findViewById(R.id.lvGroups);

        userType();

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, stringList);
        lvGroups.setAdapter(adapter);

        refreshGroupList();
    }

    private void userType() {
        refUsers.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                String uid = user.getUid();
                if (u.getUid().equals(uid)) {
                    uType = u.getuType();
                    if (uType) btnNew.setVisibility(View.VISIBLE);
                    else btnNew.setVisibility(View.INVISIBLE);
                    Toast.makeText(ListActivity.this, "uType = " + uType, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListActivity.this, "Error: " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void refreshGroupList() {
        refGroups.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stringList.clear();
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    String value = childSnapShot.getValue(String.class);

                    stringList.add(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListActivity.this, "Error: " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void newGroupActivity(View view) {
        Intent t = new Intent(ListActivity.this, NewGroupActivity.class);
        startActivity(t);
    }
}
