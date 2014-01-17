package com.example.agenda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AddUpdateUser extends Activity {
    private EditText add_name, add_mobile, add_email, add_address;
    private Button add_save_btn, add_view_all, update_btn, update_view_all;
    private LinearLayout add_view, update_view;
    private String valid_mob_number = null, valid_email = null, valid_name = null, valid_address = null, toast_msg = null;
    private int USER_ID;
    private DatabaseHandler dbHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_update_screen);
	
		// set screen
		setAddUpdateScreen();
	
		// set visibility of view as per calling activity
		String called_from = getIntent().getStringExtra("called");
	
		if (called_from.equalsIgnoreCase("add")) {
		    add_view.setVisibility(View.VISIBLE);
		    update_view.setVisibility(View.GONE);
		} else {
	
		    update_view.setVisibility(View.VISIBLE);
		    add_view.setVisibility(View.GONE);
		    USER_ID = Integer.parseInt(getIntent().getStringExtra("USER_ID"));
	
		    Contact c = dbHandler.getContact(USER_ID);
	
		    add_name.setText(c.getName());
		    add_mobile.setText(c.getPhoneNumber());
		    add_email.setText(c.getEmail());
		    add_address.setText(c.getAddress());
		    // dbHandler.close();
		}
		
		add_mobile.addTextChangedListener(new TextWatcher() {
	
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before,
			    int count) {
		    	// TODO Auto-generated method stub
	
		    }
	
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count,
			    int after) {
		    	// TODO Auto-generated method stub
	
		    }
	
		    @Override
		    public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				// min lenth 10 and max lenth 12 (2 extra for - as per phone
				// matcher format)
				isValidSignNumberValidation(12, 12, add_mobile);
		    }
		});
		
		add_mobile
			.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
	
		add_email.addTextChangedListener(new TextWatcher() {
	
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before,
			    int count) {
		    	// TODO Auto-generated method stub
	
		    }
	
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count,
			    int after) {
		    	// TODO Auto-generated method stub
	
		    }
	
		    @Override
		    public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				isValidEmail(add_email);
		    }
		});
	
		add_name.addTextChangedListener(new TextWatcher() {
	
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before,
			    int count) {
		    	// TODO Auto-generated method stub
	
		    }
	
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count,
			    int after) {
		    	// TODO Auto-generated method stub
	
		    }
	
		    @Override
		    public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				isValidPersonName(add_name);
		    }
		});
		
		add_address.addTextChangedListener(new TextWatcher() {
			
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before,
			    int count) {
		    	// TODO Auto-generated method stub
	
		    }
	
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count,
			    int after) {
		    	// TODO Auto-generated method stub
	
		    }
	
		    @Override
		    public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
		    	isValidPostalAddress(add_address);
		    }
		});
	
		add_save_btn.setOnClickListener(new View.OnClickListener() {
	
		    @Override
		    public void onClick(View v) {
				// TODO Auto-generated method stub
				// check the value state is null or not
				if (valid_name != null && valid_mob_number != null
					&& valid_email != null && valid_name.length() != 0
					&& valid_mob_number.length() != 0
					&& valid_email.length() != 0
					&& valid_address.length() != 0) {
		
				    dbHandler.addContact(new Contact(valid_name,
					    valid_mob_number, valid_email, valid_address));
				    toast_msg = "Dados inseridos com sucesso!";
				    showToast(toast_msg);
				    resetText();
		
				}
	
		    }
		});
	
		update_btn.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
				// TODO Auto-generated method stub
		
				valid_name = add_name.getText().toString();
				valid_mob_number = add_mobile.getText().toString();
				valid_email = add_email.getText().toString();
		
				// check the value state is null or not
				if (valid_name != null && valid_mob_number != null
					&& valid_email != null && valid_name.length() != 0
					&& valid_mob_number.length() != 0
					&& valid_email.length() != 0
					&& valid_address.length() != 0) {
		
				    dbHandler.updateContact(new Contact(USER_ID, valid_name,
					    valid_mob_number, valid_email, valid_address));
				    dbHandler.close();
				    toast_msg = "Dados atualizados com sucesso!";
				    showToast(toast_msg);
				    resetText();
				} else {
				    toast_msg = "Desculpe Alguns campos estão faltando. \nPor favor, preencha todos.";
				    showToast(toast_msg);
				}
	
		    }
		});
		
		update_view_all.setOnClickListener(new View.OnClickListener() {
	
		    @Override
		    public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent view_user = new Intent(AddUpdateUser.this,
					MainScreen.class);
				view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(view_user);
				finish();
		    }
		});
	
		add_view_all.setOnClickListener(new View.OnClickListener() {
	
		    @Override
		    public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent view_user = new Intent(AddUpdateUser.this,
					MainScreen.class);
				view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(view_user);
				finish();
		    }
		});

    }

    public void setAddUpdateScreen() {

		add_name = (EditText) findViewById(R.id.add_name);
		add_mobile = (EditText) findViewById(R.id.add_mobile);
		add_email = (EditText) findViewById(R.id.add_email);
		add_address = (EditText) findViewById(R.id.add_address);
	
		add_save_btn = (Button) findViewById(R.id.add_save_btn);
		update_btn = (Button) findViewById(R.id.update_btn);
		add_view_all = (Button) findViewById(R.id.add_view_all);
		update_view_all = (Button) findViewById(R.id.update_view_all);
	
		add_view = (LinearLayout) findViewById(R.id.add_view);
		update_view = (LinearLayout) findViewById(R.id.update_view);
	
		add_view.setVisibility(View.GONE);
		update_view.setVisibility(View.GONE);

    }

    public void isValidSignNumberValidation(int MinLen, int MaxLen,
	    EditText edt) throws NumberFormatException {
		if (edt.getText().toString().length() <= 0) {
		    edt.setError("Somente números");
		    valid_mob_number = null;
		} else if (edt.getText().toString().length() < MinLen) {
		    edt.setError("Tamanho mínimo " + MinLen);
		    valid_mob_number = null;
	
		} else if (edt.getText().toString().length() > MaxLen) {
		    edt.setError("Tamanho máximo " + MaxLen);
		    valid_mob_number = null;
	
		} else {
		    valid_mob_number = edt.getText().toString();
	
		}

    } // END OF Edittext validation

    public void isValidEmail(EditText edt) {
		if (edt.getText().toString() == null) {
		    edt.setError("Endereço de email inválido");
		    valid_email = null;
		} else if (isEmailValid(edt.getText().toString()) == false) {
		    edt.setError("Endereço de email inválido");
		    valid_email = null;
		} else {
		    valid_email = edt.getText().toString();
		}
    }

    boolean isEmailValid(CharSequence email) {
    	return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    } // end of email matcher

    public void isValidPersonName(EditText edt) throws NumberFormatException {
		if (edt.getText().toString().length() <= 0) {
		    edt.setError("Somente caracteres alfabéticos.");
		    valid_name = null;
		} else if (!edt.getText().toString().matches("[a-zA-Z ]+")) {
		    edt.setError("Somente caracteres alfabéticos.");
		    valid_name = null;
		} else {
		    valid_name = edt.getText().toString();
		}

    }
    
    public void isValidPostalAddress(EditText edt){
    	valid_address = edt.getText().toString();
    }

    public void showToast(String msg) {
    	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void resetText() {
		add_name.getText().clear();
		add_mobile.getText().clear();
		add_email.getText().clear();
		add_address.getText().clear();
    }

}
