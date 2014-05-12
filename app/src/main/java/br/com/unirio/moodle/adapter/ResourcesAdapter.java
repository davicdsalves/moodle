package br.com.unirio.moodle.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.unirio.moodle.R;
import br.com.unirio.moodle.model.Resource;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by davi.alves on 11/05/2014.
 */
public class ResourcesAdapter extends ListAdapter<Resource> {


    public ResourcesAdapter(Activity activity, List<Resource> resources) {
        super(activity, resources);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Resource resourceRow = list.get(position);
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.resources_row, parent, false);
            holder = new ViewHolder(view);
            if (view != null) {
                view.setTag(holder);
            }
        }

        holder.resourceName.setText(resourceRow.getName());
        holder.resourceType.setText(resourceRow.getType());

        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.textViewResourceName)
        TextView resourceName;
        @InjectView(R.id.textViewResourceType)
        TextView resourceType;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
