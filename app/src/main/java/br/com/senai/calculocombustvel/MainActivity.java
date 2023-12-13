package br.com.senai.calculocombustvel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private EditText etValorEtanol;
    private EditText etValorGasolina;
    private Button btCalcular;
    private TextView tvResultado;
    private SharedPreferences pref;
    private final String KEY_ETANOL = "valor_etanol";
    private final String KEY_GASOLINA = "valor_gasolina";
    private final String KEY_PERCENTUAL = "percentual";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("historico", Context.MODE_PRIVATE);

        etValorEtanol = findViewById(R.id.etValorEtanol);
        etValorGasolina = findViewById(R.id.etValorGasolina);
        btCalcular = findViewById(R.id.btCalcular);
        btCalcular.setOnClickListener(this);
        tvResultado = findViewById(R.id.tvResultado);

        exibirHistorico();
    }

    @Override
    public void onClick(View view) {

        // Iniciando variáveis
        float valorEtanol = 0.0f;
        float valorGasolina = 0.0f;
        float percentual = 0.0f;

        if (view.getId() == R.id.btCalcular) {

            InputMethodManager im = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(view.getWindowToken(), 0);

            // Recuperando os valores digitados no app
            valorEtanol = Float.parseFloat(etValorEtanol.getText().toString());
            valorGasolina = Float.parseFloat(etValorGasolina.getText().toString());

            // Calculo
            percentual = valorEtanol / valorGasolina;

            String resultado = "";

            if (percentual <= 0.70) {
                resultado = "Utilizar etanol";
            } else {
                resultado = "Utilizar gasolina";
            }

            tvResultado.setText(resultado);
            tvResultado.setVisibility(View.VISIBLE);

            armazenarInformacoes(valorEtanol,
                    valorGasolina, percentual);

            exibirHistorico();
        }
    }

    private void armazenarInformacoes(float valorEtanol,
                                      float valorGasolina,
                                      float percentual) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(KEY_ETANOL, valorEtanol);
        editor.putFloat(KEY_GASOLINA, valorGasolina);
        editor.putFloat(KEY_PERCENTUAL, percentual);
        editor.apply();
    }

    private void exibirHistorico() {
        float ve = pref.getFloat(KEY_ETANOL, 0.00f);
        float vg = pref.getFloat(KEY_GASOLINA, 0.0f);
        float pe = pref.getFloat(KEY_PERCENTUAL, 0.0f);

        TextView tvEtanolResultado = findViewById(R.id.tvEtanolResultado);
        TextView tvGasolinaResultado = findViewById(R.id.tvGasolinaResultado);
        TextView tvPercentual = findViewById(R.id.tvPercentualResultado);

        tvEtanolResultado.setText(String.format("Etanol R$ %.2f", ve));
        tvGasolinaResultado.setText(String.format("Gasolina R$ %.2f", vg));
        tvPercentual.setText(String.format("Percentual %.2f %%", pe));
    }
}