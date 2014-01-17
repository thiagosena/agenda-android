package com.example.agenda;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainScreen extends Activity {
	Button add_btn;
	ListView contact_listview;
	ArrayList<Contact> contact_data = new ArrayList<Contact>();
	ContactAdapter cAdapter;
	DatabaseHandler db;
	String toast_msg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		try {
			contact_listview = (ListView) findViewById(R.id.list);
			contact_listview.setItemsCanFocus(false);
			add_btn = (Button) findViewById(R.id.add_btn);

			setReferashData();

		} catch (Exception e) {
			// TODO: handle exception
			Log.e("some error", "" + e);
		}
		add_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent add_user = new Intent(MainScreen.this,
						AddUpdateUser.class);
				add_user.putExtra("called", "add");
				add_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(add_user);
				finish();
			}
		});

	}

	public void setReferashData() {
		contact_data.clear();
		db = new DatabaseHandler(this);
		ArrayList<Contact> contact_array_from_db = db.getAllContacts();

		for (int i = 0; i < contact_array_from_db.size(); i++) {

			int tidno = contact_array_from_db.get(i).getID();
			
			String name = contact_array_from_db.get(i).getName();
			String mobile = contact_array_from_db.get(i).getPhoneNumber();
			String email = contact_array_from_db.get(i).getEmail();
			String address = contact_array_from_db.get(i).getAddress();
			
			Contact cnt = new Contact();
			
			cnt.setID(tidno);
			cnt.setName(name);
			cnt.setEmail(email);
			cnt.setPhoneNumber(mobile);
			cnt.setAddress(address);

			contact_data.add(cnt);
		}
		db.close();
		cAdapter = new ContactAdapter(MainScreen.this, R.layout.listview_row,
				contact_data);
		contact_listview.setAdapter(cAdapter);
		cAdapter.notifyDataSetChanged();
	}

	public void Show_Toast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setReferashData();

	}

	public class ContactAdapter extends ArrayAdapter<Contact> {
		Activity activity;
		int layoutResourceId;
		Contact user;
		ArrayList<Contact> data = new ArrayList<Contact>();

		public ContactAdapter(Activity act, int layoutResourceId,
				ArrayList<Contact> data) {
			super(act, layoutResourceId, data);
			this.layoutResourceId = layoutResourceId;
			this.activity = act;
			this.data = data;
			notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			UserHolder holder = null;

			if (row == null) {
				LayoutInflater inflater = LayoutInflater.from(activity);

				row = inflater.inflate(layoutResourceId, parent, false);
				holder = new UserHolder();
				
				holder.name = (TextView) row.findViewById(R.id.user_name_txt);
				holder.email = (TextView) row.findViewById(R.id.user_email_txt);
				holder.number = (TextView) row.findViewById(R.id.user_mob_txt);
				holder.address = (TextView) row.findViewById(R.id.user_address_txt);
				
				holder.edit = (Button) row.findViewById(R.id.btn_update);
				holder.delete = (Button) row.findViewById(R.id.btn_delete);
				row.setTag(holder);
			} else {
				holder = (UserHolder) row.getTag();
			}
			user = data.get(position);
			holder.edit.setTag(user.getID());
			holder.delete.setTag(user.getID());
			
			holder.name.setText(user.getName());
			holder.email.setText(user.getEmail());
			holder.number.setText(user.getPhoneNumber());
			holder.address.setText(user.getAddress());

			holder.edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("Edit Button Clicked", "**********");

					Intent update_user = new Intent(activity,
							AddUpdateUser.class);
					update_user.putExtra("called", "update");
					update_user.putExtra("USER_ID", v.getTag().toString());
					activity.startActivity(update_user);

				}
			});
			
			holder.delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					// TODO Auto-generated method stub

					// show a message while loader is loading

					AlertDialog.Builder adb = new AlertDialog.Builder(activity);
					adb.setTitle("Delete?");
					adb.setMessage("Voc� tem certeza que deseja excluir esse contato?");
					final int user_id = Integer.parseInt(v.getTag().toString());
					adb.setNegativeButton("Cancelar", null);
					adb.setPositiveButton("Ok",
							new AlertDialog.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// MyDataObject.remove(positionToRemove);
									DatabaseHandler dBHandler = new DatabaseHandler(
											activity.getApplicationContext());
									dBHandler.deleteContact(user_id);
									MainScreen.this.onResume();

								}
							});
					adb.show();
				}

			});
			return row;

		}

		class UserHolder {
			TextView name;
			TextView email;
			TextView number;
			TextView address;
			Button edit;
			Button delete;
		}

	}

}
