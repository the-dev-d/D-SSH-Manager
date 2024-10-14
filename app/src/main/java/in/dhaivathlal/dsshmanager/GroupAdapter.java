package in.dhaivathlal.dsshmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import in.dhaivathlal.dsshmanager.models.Group;

public class GroupAdapter extends ArrayAdapter<Group> {


    public GroupAdapter(@NonNull Context context, List<Group> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, parent, false);

        TextView tv = view.findViewById(R.id.group_name);
        TextView count = view.findViewById(R.id.machine_count);

        count.setText(getItem(position).getMachines().toString());
        tv.setText(getItem(position).getName());

        return view;
    }
}
