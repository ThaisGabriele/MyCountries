package u.icomp.mycountries.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import u.icomp.mycountries.Model.Country;
import u.icomp.mycountries.R;

public class CustomAdapter extends ArrayAdapter<Country> {
    public CustomAdapter(Context context, List<Country> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Country country = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_country, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.name);
        TextView tvCode = (TextView) convertView.findViewById(R.id.capital);

        tvName.setText(country.name);
        tvCode.setText(country.capital);

        return convertView;
    }
}
