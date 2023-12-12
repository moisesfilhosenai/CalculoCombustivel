package br.com.senai.calculocombustvel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private EditText etValorEtanol;
    private EditText etValorGasolina;
    private Button btCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etValorEtanol = findViewById(R.id.etValorEtanol);
        etValorGasolina = findViewById(R.id.etValorGasolina);
        btCalcular = findViewById(R.id.btCalcular);
        btCalcular.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        // Iniciando variáveis
        float valorEtanol = 0.0f;
        float valorGasolina = 0.0f;
        float percentual = 0.0f;

        if (view.getId() == R.id.btCalcular) {

            // Recuperando os valores digitados no app
            valorEtanol = Float.parseFloat(etValorEtanol.getText().toString());
            valorGasolina = Float.parseFloat(etValorGasolina.getText().toString());

            // Calculo
            percentual = valorEtanol / valorGasolina;

        }
    }
}