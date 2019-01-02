package com.example.acer.lltgh_dogadoption;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.PaymentMethod;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;
import java.util.HashMap;
import java.util.Map;
import io.card.payment.i18n.StringKey;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class DonationFrag extends Fragment {

    private static final int REQUEST_CODE = 5476;

    // in emulator, localhost is 10.0.2.2
    final String API_GET_TOKEN = "http://10.0.2.2/braintree/main.php";
    final String API_CHECKOUT = "http://10.0.2.2/braintree/checkout.php";

    String token, amt;
    HashMap<String, String> paramsHash;
    View view;
    Button btn_donate;
    EditText edtAmt;
    LinearLayout ly_waiting, ly_payment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle SavedInstanceState) {
        if (container == null)
            return inflater.inflate(R.layout.donation_frag, container, false);

        view = inflater.inflate(R.layout.donation_frag, container, false);
        ly_payment = (LinearLayout)view.findViewById(R.id.payment_group);
        ly_waiting = (LinearLayout)view.findViewById(R.id.waiting_group);
        btn_donate = (Button)view.findViewById(R.id.btnDonate);
        edtAmt = (EditText)view.findViewById(R.id.txtAmount);

        new getToken().execute();

        // event
        btn_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPayment();
            }
        });

        return view;
    }

    private void submitPayment() {
        DropInRequest dropinreq = new DropInRequest().clientToken(token);
        startActivityForResult(dropinreq.getIntent(getActivity()), REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNonce = nonce.getNonce();

                if (!edtAmt.getText().toString().isEmpty()){
                    amt = edtAmt.getText().toString();
                    paramsHash = new HashMap<>();
                    paramsHash.put("amount", amt);
                    paramsHash.put("nonce", strNonce);

                    sendPayments();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid amount.", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED){
                Toast.makeText(getActivity().getApplicationContext(), "Donation canceled.", Toast.LENGTH_SHORT).show();
            } else {
                Exception ex = (Exception)data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("LLTGH_Error", ex.toString());
            }

        }

    }

    private void sendPayments() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest strRequest = new StringRequest(Request.Method.POST, API_CHECKOUT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toString().contains("Successful"))
                    Toast.makeText(getActivity().getApplicationContext(), "Transaction Successful!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity().getApplicationContext(), "Transaction failed..", Toast.LENGTH_SHORT).show();
                Log.d("LLTGH_LOG", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LLTGH_Error", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (paramsHash == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key: params.keySet()){
                    params.put(key, paramsHash.get(key));
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(strRequest);
    }

    private class getToken extends AsyncTask{

        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(getActivity());
            mDialog.setCancelable(false);
            mDialog.setMessage("Please wait");
            mDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client = new HttpClient();
            client.get(API_GET_TOKEN, new HttpResponseCallback() {
                @Override
                public void success(final String responseBody) {
                    getActivity().runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            // hide waiting group
                            ly_waiting.setVisibility(View.GONE);
                            // show payment group
                            ly_payment.setVisibility(View.VISIBLE);

                            // set token
                            token = responseBody;
                        }
                    });
                }

                @Override
                public void failure(Exception exception) {
                    Log.d("LLTGH_Error", exception.toString());
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mDialog.dismiss();
        }
    }
}
