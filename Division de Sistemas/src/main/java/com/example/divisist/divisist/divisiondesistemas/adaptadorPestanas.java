package com.example.divisist.divisist.divisiondesistemas;

/**
 * Created by alejandro on 4/7/15.
 */


    import android.support.v4.app.Fragment;
    import android.support.v4.app.FragmentManager;
    import android.support.v4.app.FragmentPagerAdapter;

public class adaptadorPestanas  extends FragmentPagerAdapter {

        public adaptadorPestanas(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {

            switch (index) {
                case 0:
                    // Fragmento de Notas
             //       return new FragmentNotas();
                case 1:
                    // Fragmento de Horario
               //     return new FragmentHorario();


            }

            return null;
        }

        @Override
        public int getCount() {
            // get item count - equal to number of tabs
            return 2;
        }


}
