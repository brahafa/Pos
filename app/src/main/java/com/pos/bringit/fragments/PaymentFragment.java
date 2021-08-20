package com.pos.bringit.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pos.bringit.R;
import com.pos.bringit.adapters.PaymentAdapter;
import com.pos.bringit.databinding.FragmentPaymentBinding;
import com.pos.bringit.dialog.AutoHideDialog;
import com.pos.bringit.dialog.PayByCardDialog;
import com.pos.bringit.dialog.PayByCashDialog;
import com.pos.bringit.dialog.RefundDialog;
import com.pos.bringit.models.PaymentDetailsModel;
import com.pos.bringit.models.PaymentModel;
import com.pos.bringit.models.response.InvoiceResponse;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.PriceCountKeyboardView;
import com.pos.bringit.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.pos.bringit.utils.Constants.NEW_ORDER_TYPE_DELIVERY;
import static com.pos.bringit.utils.Constants.PAYMENT_METHOD_CARD;
import static com.pos.bringit.utils.Constants.PAYMENT_METHOD_CASH;

public class PaymentFragment extends Fragment {

    private FragmentPaymentBinding binding;
    private Context mContext;

    private PaymentDetailsModel mPaymentDetails;

    private List<PaymentModel> mPayments;

    private String mToPayPrice = "";
    private double mPaidPrice;

    private OnPaymentMethodChosenListener listener;


    private TextWatcher surplusCountWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String toPay = binding.tvToPayPrice.getText().toString();
            double surplusValue = mPaidPrice - Double.parseDouble(toPay.isEmpty() ? "0" : toPay);
            binding.tvSurplusPrice.setText(String.format(Locale.US, "%.2f", surplusValue > 0 ? surplusValue : 0.00));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private PaymentAdapter mPaymentAdapter = new PaymentAdapter(new PaymentAdapter.AdapterCallback() {
        @Override
        public void onItemClick(String id) {
            getReceiptByPaymentId(id);
        }

        @Override
        public void onItemDelete(PaymentModel paymentModel) {
            Request.getInstance().cancelReceiptByPaymentId(mContext, paymentModel.getId(), isDataSuccess -> {
                if (isDataSuccess) {
                    Request.getInstance().getOrderDetailsByID(mContext, mPaymentDetails.getOrderId(), response -> {
                        mPayments = response.getPayments();
                        mPaymentAdapter.updateList(mPayments);
                        editRemaining("-" + paymentModel.getPrice());
                        listener.onCanceled(Double.parseDouble(binding.tvRemainingPrice.getText().toString()), mPayments);
                    });
                }
            });
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);

        mPaymentDetails = PaymentFragmentArgs.fromBundle(getArguments()).getPaymentDetails();
        mPayments = mPaymentDetails.getPayments();

        initRV();
        initListeners();

        setData();

        return binding.getRoot();
    }

    private void setData() {
        binding.tvTitleToPay.setSelected(true);

        double totalPriceToPay = Double.parseDouble(mPaymentDetails.getTotal());
        double totalPrice = totalPriceToPay + countedPayments();

        checkIfRefund(totalPriceToPay < 0);

        binding.tvToDeliveryMan.setVisibility(
                mPaymentDetails.getOrderType().equals(NEW_ORDER_TYPE_DELIVERY) ? View.VISIBLE : View.GONE);
        binding.tvToDeliveryMan.setCompoundDrawablesWithIntrinsicBounds(
                0, mPaymentDetails.isToDeliveryMan()
                        ? R.drawable.ic_icon_to_delivery_man_active
                        : R.drawable.ic_icon_to_delivery_man, 0, 0);

        binding.tvTotalPrice.setText(String.format(Locale.US, "%.2f", totalPrice));
        binding.tvRemainingPrice.setText(String.format(Locale.US, "%.2f", totalPriceToPay));
        binding.tvToPayPrice.setText(String.format(Locale.US, "%.2f", totalPriceToPay));
        binding.tvPaidPrice.setText(String.format(Locale.US, "%.2f", totalPriceToPay));

        mPaymentAdapter.updateList(mPayments);
    }

    private double countedPayments() {
        double sum = 0;
        for (PaymentModel payment : mPayments) {
            if (!payment.getStatus().equals("canceled"))
                sum += Double.parseDouble(payment.getPrice());
        }
        return sum;
    }


    private void initRV() {
        binding.rvPayments.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvPayments.setAdapter(mPaymentAdapter);

    }

    private void initListeners() {
        binding.ivClose.setOnClickListener(view -> requireActivity().onBackPressed());

        binding.priceCountKeyboardView.keyListener(new PriceCountKeyboardView.KeyPressListener() {
            @Override
            public void onKeyPress(String keyTxt) {
                if (!binding.tvTitlePaid.isSelected()) {
                    if (keyTxt.equals("X")) {
                        if (!mToPayPrice.isEmpty())
                            mToPayPrice = mToPayPrice.substring(0, mToPayPrice.length() - 1);
                    } else {
                        mToPayPrice = mToPayPrice.concat(keyTxt);
                    }

                    mPaidPrice = 0;
                    binding.tvToPayPrice.setText(mToPayPrice);
                    binding.tvPaidPrice.setText(mToPayPrice);
                } else {
                    String paidPrice = binding.tvPaidPrice.getText().toString();
                    if (keyTxt.equals("X")) {
                        if (!paidPrice.isEmpty())
                            paidPrice = paidPrice.substring(0, paidPrice.length() - 1);
                    } else {
                        paidPrice = paidPrice.concat(keyTxt);
                    }

                    mPaidPrice = Double.parseDouble(paidPrice.isEmpty() ? "0" : paidPrice);
                    binding.tvPaidPrice.setText(paidPrice);
                }
            }

            @Override
            public void onPricePress(int keyValue) {
                mPaidPrice += keyValue;
                binding.tvPaidPrice.setText(String.valueOf(mPaidPrice));
            }
        });
        binding.tvTitleToPay.setOnClickListener(v -> selectField(false));
        binding.tvTitlePaid.setOnClickListener(v -> selectField(true));

        binding.tvPayByCash.setOnClickListener(v -> {
            if (checkRemaining()) openPayByCashDialog();
        });
        binding.tvPayByCard.setOnClickListener(v -> {
            if (checkRemaining()) openPayByCardDialog();
        });
        binding.tvToDeliveryMan.setOnClickListener(v -> {
            if (checkRemaining()) openToDeliveryManDialog();
        });
        binding.tvRefund.setOnClickListener(v -> openRefundDialog());

        binding.tvToPayPrice.addTextChangedListener(surplusCountWatcher);
        binding.tvPaidPrice.addTextChangedListener(surplusCountWatcher);

    }

    private void selectField(boolean isPaidField) {
        binding.tvTitleToPay.setSelected(!isPaidField);
        binding.tvTitlePaid.setSelected(isPaidField);
        binding.priceCountKeyboardView.setActivated(isPaidField);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            listener = (OnPaymentMethodChosenListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnPaymentMethodChosenListener");
        }
    }

    private void openPayByCashDialog() {
        String surplus = binding.tvSurplusPrice.getText().toString();
        String toPay = binding.tvToPayPrice.getText().toString();
        PayByCashDialog dialog = new PayByCashDialog(mContext, toPay, surplus, mPaymentDetails.getPhone(), (price, otherNumber) -> {
            PaymentModel paymentModel = new PaymentModel(price, PAYMENT_METHOD_CASH);
            performPayment(price, otherNumber, paymentModel);
        });
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void openPayByCardDialog() {
        String toPay = binding.tvToPayPrice.getText().toString();
        PayByCardDialog dialog = new PayByCardDialog(mContext, toPay, mPaymentDetails.getPhone(), (price, otherNumber) -> {
            PaymentModel paymentModel = new PaymentModel(price, PAYMENT_METHOD_CARD);

//fixme when back is ok
//            if (BusinessModel.getInstance().getEmv_terminal_id() != null) {
//                Request.getInstance().payWithEMV(mContext, price, "125", (convertedXml) -> { //fixme
//
//                    String resultCode = convertedXml.get("ResultCode");
//                    String status = convertedXml.get("Status");
//                    String ashStatus = convertedXml.get("AshStatus");
//
//                    if (resultCode.equals("0") && status.equals("0") && ashStatus.equals("0")) {
//                        performPayment(price, otherNumber, paymentModel);
//                    } else
//                        Utils.openAlertDialog(mContext, "EMV payment failed", "Error");
//                });
//            } else
            performPayment(price, otherNumber, paymentModel);

        });
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void performPayment(String price, String otherNumber, PaymentModel paymentModel) {
        if (!mPaymentDetails.getOrderId().isEmpty() && !mPaymentDetails.getOrderId().equals("-1") && !mPaymentDetails.isEdited())
            createNewPayment(mPaymentDetails.getOrderId(), paymentModel, otherNumber);
        else {
            mPaymentAdapter.addItem(paymentModel);
            editRemaining(price);
            setPaidValue(paymentModel);
        }
    }

    private void assignToDeliveryMan() {
        boolean isToDeliveryMan = !mPaymentDetails.isToDeliveryMan();

        if (!mPaymentDetails.getOrderId().isEmpty() && !mPaymentDetails.getOrderId().equals("-1") && !mPaymentDetails.isEdited())
            Request.getInstance().assignToDeliveryMan(mContext, mPaymentDetails.getOrderId(), isToDeliveryMan, isDataSuccess -> {
                if (isDataSuccess) {
                    mPaymentDetails.setToDeliveryMan(isToDeliveryMan);
                    binding.tvToDeliveryMan.setCompoundDrawablesWithIntrinsicBounds(
                            0, isToDeliveryMan
                                    ? R.drawable.ic_icon_to_delivery_man_active
                                    : R.drawable.ic_icon_to_delivery_man, 0, 0);

                } else
                    Utils.openAlertDialog(mContext, "Failed, try again", "");

            });
    }

    private void openRefundDialog() {
        String toPay = binding.tvToPayPrice.getText().toString();
        RefundDialog dialog = new RefundDialog(mContext, toPay, mPaymentDetails.getPhone(), (price, otherNumber) -> {
            PaymentModel paymentModel = new PaymentModel(price, PAYMENT_METHOD_CASH);
            performPayment(price, otherNumber, paymentModel);
        });
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void setPaidValue(PaymentModel paymentModel) {
        listener.onPaid(paymentModel.getType(),
                Double.parseDouble(binding.tvRemainingPrice.getText().toString()), mPayments);
    }

    private void openToDeliveryManDialog() {
        AutoHideDialog autoHideDialog = new AutoHideDialog(mContext, "The customer will pay\nto the delivery man");
        autoHideDialog.setCancelable(false);
        autoHideDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        autoHideDialog.show();
        assignToDeliveryMan();

    }

    private void editRemaining(String price) {
        double remaining = Double.parseDouble(binding.tvRemainingPrice.getText().toString());
        double current = Double.parseDouble(price);
        remaining -= current;

        mToPayPrice = "";
        mPaidPrice = 0;

        checkIfRefund(remaining < 0);
        checkIfZero(remaining == 0);

        binding.tvRemainingPrice.setText(String.format(Locale.US, "%.2f", remaining));
        binding.tvToPayPrice.setText(String.format(Locale.US, "%.2f", remaining));
        binding.tvPaidPrice.setText(String.format(Locale.US, "%.2f", remaining));
    }

    private void checkIfRefund(boolean isRefund) {
        binding.tvRefund.setVisibility(isRefund ? View.VISIBLE : View.GONE);
        binding.tvPayByCash.setVisibility(isRefund ? View.GONE : View.VISIBLE);
        binding.tvPayByCard.setVisibility(isRefund ? View.GONE : View.VISIBLE);
    }

    private void checkIfZero(boolean isZero) {
        if (isZero) assignToDeliveryMan();
    }

    private boolean checkRemaining() {
        double remaining = Double.parseDouble(binding.tvRemainingPrice.getText().toString());
        double current = Double.parseDouble(binding.tvToPayPrice.getText().toString());
        if (remaining == 0) {
            Utils.openAlertDialog(mContext, "Nothing to pay", "Warning");
            return false;
        } else if (remaining - current < 0) {
            Utils.openAlertDialog(mContext, "You can't pay more than remaining price", "Warning");

            mToPayPrice = "";
            mPaidPrice = 0;

            binding.tvRemainingPrice.setText(String.format(Locale.US, "%.2f", remaining));
            binding.tvToPayPrice.setText(String.format(Locale.US, "%.2f", remaining));
            binding.tvPaidPrice.setText(String.format(Locale.US, "%.2f", remaining));
            return false;
        }
        return true;
    }

    private void createNewPayment(String orderId, PaymentModel paymentModel, String otherNumber) {
        List<PaymentModel> paymentModels = new ArrayList<>();
        if (!otherNumber.isEmpty()) {
            if (otherNumber.equals("-1")) paymentModel.setSendSms("0");
            else paymentModel.setNewPhone(otherNumber);
        }
        paymentModels.add(paymentModel);
        Request.getInstance().createNewPayment(mContext, orderId, paymentModels, isDataSuccess -> {
            if (isDataSuccess) {
                setPaidValue(paymentModel);
                editRemaining(paymentModel.getPrice());
            } else
                Utils.openAlertDialog(mContext, "Payment failed, try again", "");

            Request.getInstance().getOrderDetailsByID(mContext, orderId, response -> {
                mPayments = response.getPayments();
                mPaymentAdapter.updateList(mPayments);
                if (paymentModel.getSendSms() != null && paymentModel.getSendSms().equals("0")) {
                    getReceiptByPaymentId(mPayments.get(mPayments.size() - 1).getId());
                }
            });
        });
    }

    private void getReceiptByPaymentId(String id) {
        Request.getInstance().getReceiptByPaymentId(mContext, id, response -> {
            if (!response.getInvoice().isPrinted()) {
                Request.getInstance().markAsPrinted(mContext, id, isDataSuccess -> {
                });
            }
            listener.onPrint(response.getInvoice());
        });
    }

    public interface OnPaymentMethodChosenListener {
        void onPaid(String paymentMethod, double priceRemaining, List<PaymentModel> payments);

        void onCanceled(double priceRemaining, List<PaymentModel> payments);

        void onPrint(InvoiceResponse.InvoiceBean invoice);
    }
}
