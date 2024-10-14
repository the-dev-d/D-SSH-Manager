package in.dhaivathlal.dsshmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

import in.dhaivathlal.dsshmanager.models.AuthStore;
import in.dhaivathlal.dsshmanager.models.Group;
import in.dhaivathlal.dsshmanager.models.Machine;

public class MainActivity extends AppCompatActivity {

    private DBHandler handler = null;
    private ListView lv;

    List<Group> groups;

    BiometricPrompt.PromptInfo.Builder builder() {
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Unlock")
                .setSubtitle("Use fingerprint");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton imageButton = findViewById(R.id.new_group_button);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewGroupActivity.class);
            startActivity(intent);
        });

        handler = new DBHandler(this);

        groups = handler.getAllGroup();

        GroupAdapter adapter = new GroupAdapter(this, groups);
        lv = findViewById(R.id.group_list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            String selected = ((TextView)view.findViewById(R.id.group_name)).getText().toString();
            Optional<Group> searchResult = groups.stream()
                    .filter(group -> group.getName().equalsIgnoreCase(selected)).findFirst();

            if(searchResult.isPresent()) {
                Intent intent = new Intent(this, MachinesActivity.class);
                intent.putExtra("group", searchResult.get().getId());
                startActivity(intent);
            }else {
                Toast.makeText(this, "Invalid group " + selected + " selected", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        groups = handler.getAllGroup();
        lv.setAdapter(
                new GroupAdapter(this, groups)
        );
    }

    //    @Override
//    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu, menu);
//        return true;
//    }
}