package com.zz.m;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zz.m.ui.bezier.BezierStartActivity;


import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {


    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rxPermissions=new RxPermissions(this);
    }

    @OnClick({R.id.btn_bezier,R.id.btn_contacts})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_bezier:
                startActivity(new Intent(MainActivity.this,
                        BezierStartActivity.class));
                break;
            case R.id.btn_contacts:
                rxPermissions
                        .request(Manifest.permission.READ_CONTACTS)
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d("rxPermissions","onSubscribe");
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if(aBoolean){
                                    chooseContacts();
                                    Log.d("rxPermissions","true");
                                }else{
                                    Log.d("rxPermissions","false");
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("rxPermissions","error");
                            }

                            @Override
                            public void onComplete() {
                                Log.d("rxPermissions","onComplete");
                            }
                        });
                break;
        }
    }

    private void chooseContacts(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 0);
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (0):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    ContentResolver cr=getContentResolver();
                    Cursor c = cr.query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String phoneNumber ;
                        if (hasPhone.equalsIgnoreCase("1")) {
                            hasPhone = "true";
                        }
                        else {
                            hasPhone = "false";
                        }
                        if (Boolean.parseBoolean(hasPhone)) {
                            Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                                            + contactId,
                                    null,
                                    null);
                            if(phones!=null){
                                if (phones.moveToNext()) {
                                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    Log.d("AndPermission",phoneNumber);
                                }
                                phones.close();
                            }
                        }
                    }
                    c.close();
                }
                break;
        }
    }
}
