package com.panzhiev.leogaming.ui.fragment.dashboard;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.panzhiev.leogaming.R;
import com.panzhiev.leogaming.repository.pojo.Favorite;
import com.panzhiev.leogaming.repository.pojo.Invoice;
import com.panzhiev.leogaming.repository.pojo.Last;
import com.panzhiev.leogaming.utils.Utils;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.panzhiev.leogaming.ui.fragment.dashboard.DashboardFragment.FAVORITE_VALUE;
import static com.panzhiev.leogaming.ui.fragment.dashboard.DashboardFragment.INVOICE_VALUE;
import static com.panzhiev.leogaming.ui.fragment.dashboard.DashboardFragment.ITEM;
import static com.panzhiev.leogaming.ui.fragment.dashboard.DashboardFragment.ITEM_TYPE_KEY;
import static com.panzhiev.leogaming.ui.fragment.dashboard.DashboardFragment.LAST_VALUE;

public class DialogFragmentDetails extends DialogFragment {

    @BindView(R.id.tv_service)
    TextView tvService;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_account)
    TextView tvAccount;

    @BindView(R.id.btn_ok)
    Button btnOk;

    public DialogFragmentDetails() {
        // Required empty public constructor
    }

    public static DialogFragmentDetails newInstance() {
        return new DialogFragmentDetails();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_details, container, false);
        ButterKnife.bind(this, v);
        try {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String htmlId = "<b><font color='#000000'>Id : </font></b>";
        String htmlService = "<b><font color='#000000'>Service : </font></b>";
        String htmlName = "<b><font color='#000000'>Name : </font></b>";
        String htmlAccount = "<b><font color='#000000'>Account : </font></b>";

        Bundle bundle = getArguments();
        switch (bundle.getString(ITEM_TYPE_KEY)) {
            case INVOICE_VALUE:
                Invoice invoice = bundle.getParcelable(ITEM);
                tvService.setText(Html.fromHtml(htmlId + invoice.getId()));
                tvName.setText(Html.fromHtml(htmlName + invoice.getName()));
                tvAccount.setText(Html.fromHtml(htmlAccount + Utils.formatNumber(invoice.getAccount())));
                break;
            case FAVORITE_VALUE:
                Favorite favorite = bundle.getParcelable(ITEM);
                tvService.setText(Html.fromHtml(htmlService + favorite.getService()));
                tvName.setText(Html.fromHtml(htmlName + favorite.getName()));
                tvAccount.setText(Html.fromHtml(htmlAccount + Utils.formatNumber(favorite.getAccount1())));
                break;
            case LAST_VALUE:
                Last last = bundle.getParcelable(ITEM);
                tvService.setText(Html.fromHtml(htmlService + last.getService()));
                tvName.setText(Html.fromHtml(htmlName + last.getName()));
                tvAccount.setText(Html.fromHtml(htmlAccount + Utils.formatNumber(last.getAccount1())));
                break;
        }

        btnOk.setOnClickListener(v -> dismissAllowingStateLoss());
    }
}
