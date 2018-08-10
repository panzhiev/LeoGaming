package com.panzhiev.leogaming.ui.fragment.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.panzhiev.leogaming.R;
import com.panzhiev.leogaming.repository.RepositoryProvider;
import com.panzhiev.leogaming.repository.pojo.Favorite;
import com.panzhiev.leogaming.repository.pojo.Invoice;
import com.panzhiev.leogaming.repository.pojo.Last;
import com.panzhiev.leogaming.repository.pojo.ResponseAccount;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.panzhiev.leogaming.utils.Utils.formatBalance;
import static com.panzhiev.leogaming.utils.Utils.formatHeaderNumber;
import static com.panzhiev.leogaming.utils.Utils.showAlertDialog;

public class DashboardFragment extends Fragment implements DashboardAdapter.Callback {

    public static final String ITEM_TYPE_KEY = "ITEM_TYPE_KEY";
    public static final String INVOICE_VALUE = "INVOICE_VALUE";
    public static final String FAVORITE_VALUE = "FAVORITE_VALUE";
    public static final String LAST_VALUE = "LAST_VALUE";
    public static final String ITEM = "ITEM";

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.tv_account_number)
    TextView tvAccountNumber;

    @BindView(R.id.tv_balance)
    TextView tvBalance;

    @BindView(R.id.btn_replenish)
    FrameLayout btnReplenish;

    @BindView(R.id.btn_withdraw)
    FrameLayout btnWithdraw;

    @BindView(R.id.fab_send_money)
    FloatingActionButton fab1;

    @BindView(R.id.fab_pay_for_services)
    FloatingActionButton fab2;

    @BindView(R.id.fab_bill)
    FloatingActionButton fab3;

    @BindView(R.id.ll_send_money)
    LinearLayout ll1;

    @BindView(R.id.ll_pay_for_services)
    LinearLayout ll2;

    @BindView(R.id.ll_bill)
    LinearLayout ll3;

    RepositoryProvider mRepositoryProvider;
    ResponseAccount mResponseAccount;
    DashboardAdapter mAdapter;
    private List items = new ArrayList();

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepositoryProvider = new RepositoryProvider();
        mResponseAccount = mRepositoryProvider.getAccountInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, v);
        setupRV();
        setListeners();
        return v;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvAccountNumber.setText(formatHeaderNumber(mResponseAccount.getAccount()));
        tvBalance.setText(formatBalance(mResponseAccount.getBalance().toString()));

        initListForAdapter();
        setList((ArrayList) items);
    }

    private void setupRV() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    private void setListeners() {
        btnReplenish.setOnClickListener(v -> showAlertDialog(getContext(), "Кнопка \"Пополнить\""));
        btnWithdraw.setOnClickListener(v -> showAlertDialog(getContext(), "Кнопка \"Вывести\""));
        tvBalance.setOnClickListener(v -> showAlertDialog(getContext(), "Баланс Пользователя"));
        tvAccountNumber.setOnClickListener(v -> showAlertDialog(getContext(), "Номер Пользователя"));
        fab1.setOnClickListener(v -> showAlertDialog(getContext(), "Перевести деньги"));
        fab2.setOnClickListener(v -> showAlertDialog(getContext(), "Оплатить услуги"));
        fab3.setOnClickListener(v -> showAlertDialog(getContext(), "Выставить счет"));
        ll1.setOnClickListener(v -> showAlertDialog(getContext(), "Перевести деньги"));
        ll2.setOnClickListener(v -> showAlertDialog(getContext(), "Оплатить услуги"));
        ll3.setOnClickListener(v -> showAlertDialog(getContext(), "Выставить счет"));
    }

    void setList(ArrayList list) {
        if (mAdapter == null) {
            mAdapter = new DashboardAdapter(list, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.reloadList(list);
        }
    }

    @Override
    public void onItemClick(Parcelable item) {

        DialogFragmentDetails df = DialogFragmentDetails.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ITEM, item);

        if (item instanceof Invoice) {
            bundle.putString(ITEM_TYPE_KEY, INVOICE_VALUE);
        } else if (item instanceof Favorite) {
            bundle.putString(ITEM_TYPE_KEY, FAVORITE_VALUE);
        } else if (item instanceof Last) {
            bundle.putString(ITEM_TYPE_KEY, LAST_VALUE);
        }
        df.setArguments(bundle);
        df.show(getActivity().getSupportFragmentManager(), "");
    }

    @Override
    public void onElementClick(String msg) {
        showAlertDialog(getContext(), msg);
    }

    public List initListForAdapter() {
        List invoices = mResponseAccount.getInvoices();
        for (int i = 0; i < invoices.size(); i++) {
            items.add(invoices.get(i));
            if (i != invoices.size() - 1) {
                items.add(0);
            }
        }
        items.add(getResources().getString(R.string.favorites));
        List favs = mResponseAccount.getFavorites();
        for (int i = 0; i < favs.size(); i++) {
            items.add(favs.get(i));
            if (i != favs.size() - 1) {
                items.add(0);
            }
        }
        items.add(getResources().getString(R.string.last));
        List lasts = mResponseAccount.getLast();
        for (int i = 0; i < lasts.size(); i++) {
            items.add(lasts.get(i));
            if (i != lasts.size() - 1) {
                items.add(0);
            }
        }
        return items;
    }
}
