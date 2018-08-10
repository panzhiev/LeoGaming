package com.panzhiev.leogaming.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.panzhiev.leogaming.R;
import com.panzhiev.leogaming.ui.fragment.dashboard.DashboardFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.panzhiev.leogaming.R.id.nav_item_wallet;
import static com.panzhiev.leogaming.R.id.nav_item_bonus;
import static com.panzhiev.leogaming.R.id.nav_item_history;
import static com.panzhiev.leogaming.R.id.nav_item_profile;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bnv)
    BottomNavigationViewEx bnv;

    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setBnv();
        navigateOnFragment(DashboardFragment.newInstance());
    }

    private void navigateOnFragment(Fragment fragment) {
        String tag = fragment.getClass().getSimpleName();
        Fragment f = fm.findFragmentByTag(tag);
        if (f == null) {

            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(R.id.container, fragment, tag)
                    .commit();

        } else if (!f.isVisible()) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.container, fragment, tag)
                    .commit();
        }
    }

    private void setBnv() {
        bnv.enableAnimation(true);
        bnv.enableItemShiftingMode(false);
        bnv.enableShiftingMode(false);

        bnv.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case nav_item_wallet:
                    navigateOnFragment(DashboardFragment.newInstance());
                    Toast.makeText(this, "КОШЕЛЕК", Toast.LENGTH_SHORT).show();
                    break;
                case nav_item_bonus:
                    Toast.makeText(this, "БОНУСЫ", Toast.LENGTH_SHORT).show();
                    break;
                case nav_item_history:
                    Toast.makeText(this, "ИСТОРИЯ", Toast.LENGTH_SHORT).show();
                    break;
                case nav_item_profile:
                    Toast.makeText(this, "ПРОФИЛЬ", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            return true;
        });
    }
}
