package com.androidexample.broadcastreceiver;

import android.app.LauncherActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.location.Address;
import android.location.Geocoder;
import android.location.*;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class BroadcastNewSms extends Activity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.androidexample_broadcast_newsms);
		String[] array = new String[1000];
		setContentView(R.layout.androidexample_broadcast_newsms);
		ListView view = (ListView)findViewById(R.id.listView);
		Uri uriSMSUri = Uri.parse("content://sms/inbox");
		Cursor cur = getContentResolver().query(uriSMSUri, null, null, null, null);
		String sms = "";
		String numero = "7191994380";
		int i = 0;
		while(cur.moveToNext()){
			String localizacao = cur.getString(12);
			String numeroReceiver = cur.getString(2);

			if (localizacao.length() >= 33) {
				localizacao = (cur.getString(12)).substring(13, 22);
				localizacao = localizacao + " - " + cur.getString(12).substring(24,33);
			}
			sms =localizacao;
			if ( (cur.getString(2) != null) && (cur.getString(2).length() >= 10) ) {
				//if (numero.equals(numeroReceiver)) {
					array[i] = sms;
					i++;
				//}
			}
		}
		view.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, array));

		view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView text = (TextView)findViewById(R.id.textView);
				Object locate = parent.getItemAtPosition(position);
				text.setText(locate.toString());
				mostrar(locate.toString());


			}
		});
	}

	public void mostrar(String locate){
		String lat = (locate.toString().substring(0,9)).replace(",", ".");
		String lon = (locate.toString().substring(11, 21)).replace(",", ".");
		Geocoder geocoder = new Geocoder(this, Locale.getDefault());

		String endereco, Estado, cep, Pais;
		TextView text = (TextView)findViewById(R.id.textView);
		try{
			List<Address> geocoord = new Geocoder(this).getFromLocation(Double.valueOf(lat), Double.valueOf(lon), 1);
			Log.i("geo", ( geocoord.isEmpty()+""));
			if (!geocoord.isEmpty()) {
				endereco = geocoord.get(0).getAddressLine(0);
				endereco = endereco + " - " + geocoord.get(0).getAddressLine(1);
				Estado = geocoord.get(0).getAdminArea();
				Pais = geocoord.get(0).getCountryCode();
				cep = geocoord.get(0).getPostalCode();
				//text.setText(endereco + ", " + Estado + ", " + Pais + " - " + cep);
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}


}
