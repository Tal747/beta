package com.tal.beta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class NewGroupActivity extends AppCompatActivity {

    Group groupdb;
    String uid, gName, gDate, gid;
    Boolean gActive, gPrvt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgroup);
    }

    public void createNewGroup(View view) {
    }

    public void btnCancel(View view) {
        finish();
    }
}
