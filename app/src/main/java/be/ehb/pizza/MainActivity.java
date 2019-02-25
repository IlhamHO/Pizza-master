package be.ehb.pizza;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import be.ehb.pizza.dialogs.SizeDialog;
import be.ehb.pizza.dialogs.ToppingsDialog;
import be.ehb.pizza.interfaces.SizeListener;
import be.ehb.pizza.interfaces.ToppingsListener;

public class MainActivity extends AppCompatActivity implements ToppingsListener, SizeListener {

    //No magic numbers/values
    private final String DIALOG_KEY = "dialog";
    //UI
    private TextView tvOrderConfirmation;
    //Util
    private List<String> toppingLines;
    private String sizeLine;
    private View.OnClickListener undoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //cancel this
        }
    };

    /*Lifecycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOrderConfirmation = findViewById(R.id.tv_order_confirmation);
        updateUI();
    }

    /*Buttons*/
    public void clickSize(View v){
        SizeDialog sd = new SizeDialog();
        sd.show(getSupportFragmentManager(), DIALOG_KEY);
    }

    public void clickToppings(View v){
        ToppingsDialog td = new ToppingsDialog();
        td.show(getSupportFragmentManager(), DIALOG_KEY);
    }

    public void clickPlaceOrder(View v){
        if(toppingLines == null || sizeLine == null){
            Snackbar sb = Snackbar.make(findViewById(R.id.main_container), R.string.txt_order_faulty, Snackbar.LENGTH_LONG );
            sb.show();
        }else{
            Snackbar sb = Snackbar.make(findViewById(R.id.main_container), R.string.txt_order_confirm, Snackbar.LENGTH_LONG );
            sb.setAction(R.string.txt_order_undo, undoListener);
            sb.show();
        }
    }

    /*Listener magico*/
    @Override
    public void onSizeSelected(String pizzaSize) {
        sizeLine = pizzaSize;
        updateUI();
    }

    @Override
    public void onToppingSelected(List<String> choices) {
        toppingLines = choices;
        updateUI();
    }

    /*Util*/
    public void updateUI(){
        if(toppingLines == null && sizeLine == null){
            tvOrderConfirmation.setText(R.string.txt_order_incomplete);
        }else if(toppingLines == null){
            tvOrderConfirmation.setText(R.string.txt_order_missing_toppings);
        }else if(sizeLine == null) {
            tvOrderConfirmation.setText(R.string.txt_order_missing_size);
        }else{
            StringBuilder orderLines = new StringBuilder();
            orderLines.append(sizeLine);
            for(String s : toppingLines){
                orderLines.append("\n\t");
                orderLines.append(s);
            }
            tvOrderConfirmation.setText(orderLines.toString());
        }
    }
}
