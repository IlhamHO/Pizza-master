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

import java.util.ArrayList;
import java.util.Arrays;

import be.ehb.pizza.R;
import be.ehb.pizza.interfaces.ToppingsListener;

/**
 * Created by Banaan on 20/01/2038. ;)
 */
public class ToppingsDialog extends DialogFragment {

    private static ArrayList<String> chosenToppings;
    private String toppings[];
    private static boolean[] isChecked;
    private ToppingsListener listener;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ToppingsListener) context;
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            Log.e("ConformInterface", "Correct interface not implemented");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        toppings = getResources().getStringArray(R.array.topping_list);
        if(isChecked == null) {
            isChecked = new boolean[toppings.length];
            Arrays.fill(isChecked, false);
        }
        if(chosenToppings == null){
            chosenToppings = new ArrayList<>();
        }

        builder.setTitle(R.string.txt_toppings_title);
        builder.setMultiChoiceItems(toppings, isChecked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    chosenToppings.add(toppings[which]);
                } else {
                    chosenToppings.remove(toppings[which]);
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onToppingSelected(chosenToppings);
            }
        });
        return builder.create();
    }
}
