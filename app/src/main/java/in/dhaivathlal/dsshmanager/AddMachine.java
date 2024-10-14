package in.dhaivathlal.dsshmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import in.dhaivathlal.dsshmanager.models.Machine;

public class AddMachine extends AppCompatActivity {

    Integer group;

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_machine);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        group = extras.getInt("group");

        Log.d("GOT", group.toString());

        TextInputLayout serverNameLayout, usernameLayout, ipAddressLayout, passwordLayout;

        CheckBox isPasswordCheck;

        dbHandler = new DBHandler(this);
//
        serverNameLayout = findViewById(R.id.server_name);
        usernameLayout = findViewById(R.id.username);
        ipAddressLayout = findViewById(R.id.ip_address);
        isPasswordCheck = findViewById(R.id.isPassword);
        passwordLayout = findViewById(R.id.password);

        passwordLayout.setEnabled(false);
        isPasswordCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                passwordLayout.setEnabled(true);
            }else {
                passwordLayout.setEnabled(false);
            }
        });

        Button button = findViewById(R.id.save);
        button.setOnClickListener(v -> {
            String serverName = serverNameLayout.getEditText().getText().toString();
            String username = usernameLayout.getEditText().getText().toString();
            String ipAddress = ipAddressLayout.getEditText().getText().toString();
            Boolean isPassword = isPasswordCheck.isChecked();
            String password = passwordLayout.getEditText().getText().toString();

            Machine machine = new Machine();
            machine.setGroupId(group);
            machine.setIp(ipAddress);
            machine.setName(serverName);
            machine.setUsername(username);
            if(isPassword)
                machine.setPassword(password);
            dbHandler.addMachine(machine);
            serverNameLayout.getEditText().setText("");
            usernameLayout.getEditText().setText("");
            passwordLayout.getEditText().setText("");
            ipAddressLayout.getEditText().setText("");
            isPasswordCheck.setEnabled(false);

            Toast.makeText(this, "Server Added", Toast.LENGTH_SHORT);


        });


    }



}