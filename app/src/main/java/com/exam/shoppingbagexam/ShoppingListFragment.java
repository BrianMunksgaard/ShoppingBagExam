package com.exam.shoppingbagexam;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.exam.shoppingbagexam.domain.Product;
import com.exam.shoppingbagexam.domain.ShoppingBag;
import com.exam.shoppingbagexam.utils.ConfirmCancelSnack;
import com.exam.shoppingbagexam.utils.YNDialog;

public class ShoppingListFragment extends Fragment {

    /*
     * The current context (this).
     */
    private Context context;

    /*
     * Reference to the shopping bag.
     */
    private ShoppingBag shoppingBag;

    /*
     * Reference to the ListView used to
     * display the products in the bag.
     */
    private ListView listView;

    /*
     * The number of the currently checked item in
     * the product list.
     */
    private int currentCheckedItem = -1;

    /*
     * Items for the quantity spinner.
     */
    private String[] spinnerItems = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    private View thisView;

    public void setShoppingBag(ShoppingBag shoppingBag) {
        this.shoppingBag = shoppingBag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //This corresponds to the onCreate that we have in our
        //normal activities
        setRetainInstance(true);

        thisView = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        context = thisView.getContext();

        // Setup list view and connect adapter.
        listView = thisView.findViewById(R.id.list);
        if (listView != null) {
            listView.setAdapter(shoppingBag.getShoppingBagAdapter());
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }

        setButtonEventHandlers();
        setProductItemEventHandler();

        // Put numbers 1-9 in the quantity spinner.
        Spinner spinnerQuantity = thisView.findViewById(R.id.spinnerQuantity);
        ArrayAdapter<String> spinnerQuantityAdapter =
                new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        spinnerQuantity.setAdapter(spinnerQuantityAdapter);

        // Clear the bag if specified in settings.
        if(ShoppingAppSettingsFragment.getClearBagOnStartup(context)) {
            shoppingBag.clearBag();
        }

        return thisView;
    }

    /*
     * Register event handlers for shopping bag buttons.
     */
    private void setButtonEventHandlers() {

        thisView.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToBag_onClick(v);
            }
        });

        thisView.findViewById(R.id.deleteItemButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteItemFromBag_onClick(v);
            }
        });

        thisView.findViewById(R.id.deleteAllButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clearBag_onClick(v);
            }
        });

    }

    /*
     * Set event handlers for product items.
     */
    private void setProductItemEventHandler() {

        // Updates the currentCheckedItem whenever a product/item in
        // the list is clicked.
        ((ListView)thisView.findViewById(R.id.list)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentCheckedItem = i;
            }
        });
    }

    /**
     * Add an item and the quantity to the shopping bag.
     *
     * @param view
     */
    public void addToBag_onClick(View view) {
        // Hide the keyboard.
        hideKeyboard(view);

        // Get product item.
        EditText itemRef = thisView.findViewById(R.id.item);
        String itemText = itemRef.getText().toString();

        // TODO: Comment in for testing crashes
        //Crashlytics.getInstance().crash();

        // Only retrieve quantity if there is
        // an item.
        if(!itemText.isEmpty()) {

            // Get quantity from spinner.
            Spinner spinnerQuantity = thisView.findViewById(R.id.spinnerQuantity);
            int quantityFromSpinner = spinnerQuantity.getSelectedItemPosition() + 1;

            // Determine the number of items.
            int noOfItems = quantityFromSpinner;

            // Add item and quantity to bag.
            shoppingBag.addItemToBag(itemText, noOfItems);

            // Reset input fields.
            itemRef.getText().clear();
            spinnerQuantity.setSelection(0);
        }
    }

    /**
     * Delete the currently selected item from the shopping bag.
     * The user is presented with a snackbar allowing the delete
     * operation to be cancelled.
     *
     * @param view
     */
    public void deleteItemFromBag_onClick(View view) {

        Product productToDelete = shoppingBag.getProduct(currentCheckedItem);
        if (productToDelete != null) {
            // Get the name of the product we are about to delete.
            final String productName = productToDelete.getName();

            // Hide the keyboard.
            hideKeyboard(view);

            // Show snack allowing user to cancel deletion.
            String snackQuestionText = String.format("Really remove %s?", productName);
            String snackCancelledText = String.format("Remove of %s cancelled!", productName);
            View parent = thisView.findViewById(R.id.layout);
            ConfirmCancelSnack.showSnack(parent, snackQuestionText, snackCancelledText, new ConfirmCancelSnack.OnConfirmListener() {
                @Override
                public void onConfirmed() {
                    shoppingBag.removeItemFromBag(currentCheckedItem);
                }
            });
        }
    }

    /**
     * Clear the shopping bag.
     *
     * @param view
     */
    public void clearBag_onClick(View view) {

        // Use dialog to confirm clear bag.
        YNDialog dialog = new YNDialog();
        dialog.setPositiveCallback(new YNDialog.OnPositiveListener() {

            @Override
            public void onPositiveClicked() {
                shoppingBag.clearBag();
            }
        });

        dialog.show(getActivity().getFragmentManager(), "YNFragment");
    }

    /*
     * Hide the keyboard.
     */
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
