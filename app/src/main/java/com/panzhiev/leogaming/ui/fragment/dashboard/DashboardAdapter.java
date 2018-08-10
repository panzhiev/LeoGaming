package com.panzhiev.leogaming.ui.fragment.dashboard;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.panzhiev.leogaming.R;
import com.panzhiev.leogaming.repository.pojo.Favorite;
import com.panzhiev.leogaming.repository.pojo.Invoice;
import com.panzhiev.leogaming.repository.pojo.Last;
import com.panzhiev.leogaming.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.panzhiev.leogaming.utils.Constants.KYIVSTAR_PREFIX_96;
import static com.panzhiev.leogaming.utils.Constants.KYIVSTAR_PREFIX_97;
import static com.panzhiev.leogaming.utils.Constants.LIFECELL_PREFIX_63;
import static com.panzhiev.leogaming.utils.Constants.LIFECELL_PREFIX_73;
import static com.panzhiev.leogaming.utils.Constants.LIFECELL_PREFIX_93;
import static com.panzhiev.leogaming.utils.Constants.VODAFONE_PREFIX_50;
import static com.panzhiev.leogaming.utils.Constants.VODAFONE_PREFIX_95;
import static com.panzhiev.leogaming.utils.Utils.formatNumber;
import static com.panzhiev.leogaming.utils.Utils.showAlertDialog;

public class DashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM_INVOICE = 0;
    private final int ITEM_FAVORITE = 1;
    private final int ITEM_LAST = 2;
    private final int ITEM_HEADER = 3;
    private final int ITEM_DIVIDER = 4;


    private List items;
    private Callback mCallback;

    interface Callback {
        void onItemClick(Parcelable item);

        void onElementClick(String msg);
    }

    public DashboardAdapter(List items, Callback callback) {
        this.items = items;
        mCallback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;

        switch (viewType) {
            case ITEM_INVOICE:
                itemView = inflater.inflate(R.layout.item_dashboard, parent, false);
                viewHolder = new InvoiceHolder(itemView);
                break;
            case ITEM_FAVORITE:
                itemView = inflater.inflate(R.layout.item_dashboard, parent, false);
                viewHolder = new FavoriteHolder(itemView);
                break;
            case ITEM_LAST:
                itemView = inflater.inflate(R.layout.item_dashboard, parent, false);
                viewHolder = new LastHolder(itemView);
                break;
            case ITEM_HEADER:
                itemView = inflater.inflate(R.layout.item_header, parent, false);
                viewHolder = new HeaderHolder(itemView);
                break;
            default:
                itemView = inflater.inflate(R.layout.item_divider, parent, false);
                viewHolder = new DividerHolder(itemView);
                break;
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Object item = items.get(position);

        switch (getItemViewType(position)) {
            case ITEM_INVOICE:
                InvoiceHolder invoiceHolder = (InvoiceHolder) holder;
                invoiceHolder.bind((Invoice) item);
                invoiceHolder.itemView.setOnClickListener(v -> mCallback.onItemClick((Invoice) item));
                invoiceHolder.tvNumber.setOnClickListener(v -> mCallback.onElementClick("Аккаунт"));
                invoiceHolder.tvName.setOnClickListener(v -> mCallback.onElementClick("Имя"));
                invoiceHolder.ivAvatar.setOnClickListener(v -> mCallback.onElementClick("Аватар"));
                invoiceHolder.ivStar.setOnClickListener(v -> mCallback.onElementClick("Избранное"));
                break;
            case ITEM_FAVORITE:
                FavoriteHolder favoriteHolder = (FavoriteHolder) holder;
                favoriteHolder.bind((Favorite) item);
                favoriteHolder.itemView.setOnClickListener(v -> mCallback.onItemClick((Favorite) item));
                favoriteHolder.tvNumber.setOnClickListener(v -> mCallback.onElementClick("Аккаунт"));
                favoriteHolder.tvName.setOnClickListener(v -> mCallback.onElementClick("Имя"));
                favoriteHolder.ivAvatar.setOnClickListener(v -> mCallback.onElementClick("Аватар"));
                favoriteHolder.ivStar.setOnClickListener(v -> mCallback.onElementClick("Избранное"));
                break;
            case ITEM_LAST:
                LastHolder lastHolder = (LastHolder) holder;
                lastHolder.bind((Last) item);
                lastHolder.itemView.setOnClickListener(v -> mCallback.onItemClick((Last) item));
                lastHolder.tvNumber.setOnClickListener(v -> mCallback.onElementClick("Аккаунт"));
                lastHolder.tvName.setOnClickListener(v -> mCallback.onElementClick("Имя"));
                lastHolder.ivAvatar.setOnClickListener(v -> mCallback.onElementClick("Аватар"));
                lastHolder.ivStar.setOnClickListener(v -> mCallback.onElementClick("Избранное"));
                break;
            case ITEM_HEADER:
                HeaderHolder headerHolder = (HeaderHolder) holder;
                headerHolder.bind((String) item);
                break;
            default:
                // do nothing. here will be divider view, that does not need info about model
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getType(items.get(position));
    }

    private int getType(Object item) {
        if (item instanceof Invoice) {
            return ITEM_INVOICE;
        } else if (item instanceof Favorite) {
            return ITEM_FAVORITE;
        } else if (item instanceof Last) {
            return ITEM_LAST;
        } else if (item instanceof String) {
            return ITEM_HEADER;
        }

        return ITEM_DIVIDER;
    }

    public void reloadList(ArrayList list) {
        items = list;
        notifyDataSetChanged();
    }

    class InvoiceHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;

        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_number)
        TextView tvNumber;

        @BindView(R.id.iv_star)
        ImageView ivStar;

        InvoiceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        void bind(Invoice invoice) {

            Drawable bgColor = new ColorDrawable(itemView.getResources().getColor(R.color.light_blue));
            Drawable service = null;

            String name = invoice.getName();
            if (name.isEmpty()) {
                tvName.setVisibility(View.GONE);
                tvNumber.setText(Html.fromHtml("<b>" + formatNumber(invoice.getAccount()) + "</b>"));
                tvNumber.setTextColor(tvNumber.getResources().getColor(R.color.black));
            } else {
                tvName.setText(name);
                tvNumber.setText(formatNumber(invoice.getAccount()));
            }

            ivStar.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.ic_star));

            String number = invoice.getAccount();
            if (number.startsWith(LIFECELL_PREFIX_63) ||
                    number.startsWith(LIFECELL_PREFIX_73) ||
                    number.startsWith(LIFECELL_PREFIX_93)) {

                service = itemView.getResources().getDrawable(R.drawable.life);
            }

            if (number.startsWith(KYIVSTAR_PREFIX_96) ||
                    number.startsWith(KYIVSTAR_PREFIX_97)) {
                service = itemView.getResources().getDrawable(R.drawable.kyivstar);
            }

            if (number.startsWith(VODAFONE_PREFIX_50) ||
                    number.startsWith(VODAFONE_PREFIX_95)) {
                service = itemView.getResources().getDrawable(R.drawable.vodafone);
            }

            LayerDrawable ld = new LayerDrawable(new Drawable[]{bgColor, service});
            ivAvatar.setImageDrawable(ld);
        }
    }

    class FavoriteHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;

        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_number)
        TextView tvNumber;

        @BindView(R.id.iv_star)
        ImageView ivStar;

        FavoriteHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Favorite fav) {

            Drawable bgColor = new ColorDrawable(itemView.getResources().getColor(R.color.light_blue));
            Drawable service = null;

            String name = fav.getName();

            if (name.isEmpty()) {
                tvName.setVisibility(View.GONE);
                tvNumber.setText(Html.fromHtml("<b>" + formatNumber(fav.getAccount1()) + "</b>"));
                tvNumber.setTextColor(tvNumber.getResources().getColor(R.color.black));
            } else {
                tvName.setText(name);
                tvNumber.setText(formatNumber(fav.getAccount1()));
            }

            ivStar.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.ic_star_checked));

            switch (fav.getService()) {
                case Constants.MASTERCARD_ID:
                    service = itemView.getResources().getDrawable(R.drawable.mastercard);
                    break;
                case Constants.TRIOLAN_ID:
                    service = itemView.getResources().getDrawable(R.drawable.triolan);
                    break;
                case Constants.VODAFONE_ID:
                    service = itemView.getResources().getDrawable(R.drawable.vodafone);
                    break;
                case Constants.VISA_ID:
                    service = itemView.getResources().getDrawable(R.drawable.visa);
                    break;
                case Constants.KYIVSTAR_ID:
                    service = itemView.getResources().getDrawable(R.drawable.kyivstar);
                    break;
            }

            LayerDrawable ld = new LayerDrawable(new Drawable[]{bgColor, service});
            ivAvatar.setImageDrawable(ld);
        }
    }

    class LastHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;

        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_number)
        TextView tvNumber;

        @BindView(R.id.iv_star)
        ImageView ivStar;

        LastHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Last last) {

            Drawable bgColor = new ColorDrawable(itemView.getResources().getColor(R.color.light_blue));
            Drawable service = null;

            String name = last.getName();

            if (name.isEmpty()) {
                tvName.setVisibility(View.GONE);
                tvNumber.setText(Html.fromHtml("<b>" + formatNumber(last.getAccount1()) + "</b>"));
                tvNumber.setTextColor(tvNumber.getResources().getColor(R.color.black));
            } else {
                tvName.setText(name);
                tvNumber.setText(formatNumber(last.getAccount1()));
            }

            ivStar.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.ic_star));

            switch (last.getService()) {
                case Constants.MASTERCARD_ID:
                    service = itemView.getResources().getDrawable(R.drawable.mastercard);
                    break;
                case Constants.TRIOLAN_ID:
                    service = itemView.getResources().getDrawable(R.drawable.triolan);
                    break;
                case Constants.VODAFONE_ID:
                    service = itemView.getResources().getDrawable(R.drawable.vodafone);
                    break;
                case Constants.VISA_ID:
                    service = itemView.getResources().getDrawable(R.drawable.visa);
                    break;
                case Constants.KYIVSTAR_ID:
                    service = itemView.getResources().getDrawable(R.drawable.kyivstar);
                    break;
            }
            LayerDrawable ld = new LayerDrawable(new Drawable[]{bgColor, service});
            ivAvatar.setImageDrawable(ld);
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_header)
        TextView tvHeader;

        HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(String header) {
            tvHeader.setText(header);
        }
    }

    class DividerHolder extends RecyclerView.ViewHolder {

        DividerHolder(View itemView) {
            super(itemView);
        }
    }
}
