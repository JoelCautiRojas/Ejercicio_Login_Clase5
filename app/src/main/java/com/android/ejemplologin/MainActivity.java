package com.android.ejemplologin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    EditText user,pass;
    Button boton;
    String base_url = "http://www.programadoresperuanos.com/test_app/acceso/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        boton = (Button) findViewById(R.id.button);
        //Aqui empieza el cliente http
        AsyncHttpClient cliente = new AsyncHttpClient();
        RequestParams datos = new RequestParams();
        datos.put("usuario",user.getText().toString());
        datos.put("clave",pass.getText().toString());
        cliente.post(getApplicationContext(), base_url, datos, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200)
                {
                    String respuesta = new String(responseBody);
                    if("correcto".equals(respuesta))
                    {
                        startActivity(new Intent(MainActivity.this,Main2Activity.class));
                        finish();
                    }
                    else if("incorrecto".equals(respuesta))
                    {
                        Toast.makeText(getApplicationContext(),"Usuario y/o contraseña incorrecto",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error en el servidor",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(),"Sin conexion",Toast.LENGTH_LONG).show();
            }
        });
    }
}