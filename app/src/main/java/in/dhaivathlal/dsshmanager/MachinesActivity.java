package in.dhaivathlal.dsshmanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import in.dhaivathlal.dsshmanager.models.Group;
import in.dhaivathlal.dsshmanager.models.Machine;

public class MachinesActivity extends AppCompatActivity {

    Integer group;
    String groupName;
    DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_machines);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        handler = new DBHandler(this);

        group = extras.getInt("group");
        groupName = extras.getString("groupName");

        ImageButton add = findViewById(R.id.new_machine_button);
        ListView listView = findViewById(R.id.machines_list_view);

        refreshData();

        listView.setOnItemClickListener((parent, view, position, id) -> {

            TextView ip = view.findViewById(R.id.ip_address);
            TextView username = view.findViewById(R.id.user_name);

            TextView tv = view.findViewById(R.id.pass_view);
            tv.setInputType(InputType.TYPE_CLASS_TEXT);
            tv.setOnClickListener(v -> {
                String password = tv.getText().toString();
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Password", password);
                clipboardManager.setPrimaryClip(clip);

                tv.setOnClickListener(null);
            });


            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("ssh", "ssh "+ username.getText() + "@" + ip.getText());
            clipboardManager.setPrimaryClip(clip);
        });

        add.setOnClickListener(v -> {
            Intent i = new Intent(this, AddMachine.class);
            i.putExtra("group", group);
            startActivity(i);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    public void refreshData() {
        ListView listView = findViewById(R.id.machines_list_view);
        List<Machine> machines = handler.getAllMachines(group);
        //Log.d("MACHINE", machines.get(0).getName());
        MachineAdapter machineAdapter = new MachineAdapter(this, machines);
        listView.setAdapter(machineAdapter);
    }
}