package be.ehb.pizza.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import be.ehb.pizza.R;
import be.ehb.pizza.interfaces.SizeListener;

/**
 * Created by Banaan on 20/01/2038. ;)
 */
public class SizeDialog extends DialogFragment {

    private SizeListener listener;

    //Design by contract
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (SizeListener) context;
        } catch (ClassCastException ex) {
            Log.e("ConformInterface", "Correct interface not implemented");
            ex.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final String[] sizeNames = getResources().getStringArray(R.array.size_list);

        builder.setTitle(R.string.txt_size_title);
        builder.setItems(sizeNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onSizeSelected(sizeNames[which]);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);

        return builder.create();
    }
}
