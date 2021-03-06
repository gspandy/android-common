package org.solovyev.android.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * User: serso
 * Date: 4/14/12
 * Time: 8:02 PM
 */
public class ListItemArrayAdapter<LI extends ListItem> extends ListAdapter<LI> {


    public ListItemArrayAdapter(@NotNull Context context,
                                @NotNull List<? extends LI> listItems) {
        super(context, 0, 0, castList(listItems));
    }

    @SuppressWarnings("unchecked")
    @NotNull
    private static <LI> List<LI> castList(List<? extends LI> listItems) {
        return (List<LI>)listItems;
    }

    @NotNull
    public static <LI extends ListItem> ListItemArrayAdapter<LI> createAndAttach(@NotNull final ListView lv,
                                                       @NotNull final Context context,
                                                       @NotNull List<LI> listItems) {
        final ListItemArrayAdapter<LI> result = new ListItemArrayAdapter<LI>(context, listItems);

        lv.setAdapter(result);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ListItem listItem = (ListItem) lv.getItemAtPosition(position);
                ListItem.OnClickAction onClickAction = listItem.getOnClickAction();
                if ( onClickAction != null ) {
                    onClickAction.onClick(context, result, lv);
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final ListItem listItem = (ListItem) lv.getItemAtPosition(position);
                ListItem.OnClickAction onLongClickAction = listItem.getOnLongClickAction();
                if ( onLongClickAction != null ) {
                    onLongClickAction.onClick(context, result, lv);
                    return true;
                } else {
                    return false;
                }
            }
        });


        return result;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListItem listItem = getItem(position);

        if (convertView == null) {
            return listItem.build(getContext());
        } else {
            return listItem.updateView(getContext(), convertView);
        }
    }
}
