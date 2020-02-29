package com.georgebrown.myapplication;

import android.content.Context;
import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private Context context = ApplicationProvider.getApplicationContext();
    SQLiteDBM dbm=new SQLiteDBM(context);
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void checkDeletionTrue() {
        Business bus1=new Business();
        bus1.setId("123");
        dbm.insert(bus1,"","","");

        assertTrue(dbm.delete(bus1.getId()));
    }
}