package in.dhaivathlal.dsshmanager;

import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import in.dhaivathlal.dsshmanager.models.Machine;

public class MachineAdapter extends ArrayAdapter<Machine> {
    public MachineAdapter(@NonNull Context context, List<Machine> machines) {
        super(context, 0, machines);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_machines, parent, false);

        TextView serverName = view.findViewById(R.id.machine_name);
        TextView userName = view.findViewById(R.id.user_name);
        TextView ipAddress = view.findViewById(R.id.ip_address);
        TextView password = view.findViewById(R.id.pass_view);


        Machine machine = getItem(position);
        serverName.setText(machine.getName());
        userName.setText(machine.getUsername());
        ipAddress.setText(machine.getIp());
        password.setText(machine.getPassword());


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetSocketAddress socketAddress = new InetSocketAddress(machine.getIp(), 22);
                    Socket socket = new Socket();
                    socket.connect(socketAddress, 3000);
                    view.findViewById(R.id.status_indicator).setVisibility(View.INVISIBLE);
                }catch (Exception e) {
                    Log.d("ex", e.toString());
                }
            }
        });
        thread.start();

        return view;
    }
}
