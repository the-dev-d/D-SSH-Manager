package in.dhaivathlal.dsshmanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

public class NewGroupActivity extends AppCompatActivity {

    private DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_group);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        handler = new DBHandler(this);
        TextInputLayout textInputLayout = findViewById(R.id.textInputLayout);

        Button button = findViewById(R.id.save_group);
        button.setOnClickListener(v -> {

            String name = String.valueOf(textInputLayout.getEditText().getText());

            Log.d("GROUP", name);

            if (handler.groupExists(name)) {
                Toast.makeText(NewGroupActivity.this, "Group " + name + " already exist", Toast.LENGTH_LONG).show();
                return;
            }
            handler.addGroup(name);
            Toast.makeText(NewGroupActivity.this, "Group Added Successfully", Toast.LENGTH_LONG).show();
            textInputLayout.getEditText().setText("");
        });
    }
}