package br.com.senai.calculocombustvel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private EditText etValorEtanol;
    private EditText etValorGasolina;
    private ImageButton ibAvancar;
    private ImageButton ibVoltar;
    private Button btCalcular;
    private TextView tvResultado;
    private SharedPreferences pref;
    private List<CombustivelHistorico> lista = new ArrayList<>();
    private final String KEY_LISTA = "key_lista";

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
        ibAvancar = findViewById(R.id.imRight);
        ibAvancar.setOnClickListener(this);
        ibVoltar = findViewById(R.id.ibLeft);
        ibVoltar.setOnClickListener(this);

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
        } else if (view.getId() == R.id.imRight) {
            // avançar
            // ler a lista de informações
            // navegar até o proximo registro da lista
            // exibir as informações na tela
        } else if (view.getId() == R.id.ibLeft) {
            // voltar
            // ler a lista de informações
            // navegar para o registro anterior da lista
            // exibir as informações na tela
        }
    }

    private void armazenarInformacoes(float valorEtanol,
                                      float valorGasolina,
                                      float percentual) {
        Gson gson = new Gson();
        CombustivelHistorico ch = new
                CombustivelHistorico("", valorEtanol,
                                    valorGasolina,
                                    percentual);
        lista.add(ch);

        String listaString = gson.toJson(lista);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LISTA, listaString);
        editor.apply();
    }

    private void exibirHistorico(float ve, float vg, float pe) {

        TextView tvEtanolResultado = findViewById(R.id.tvEtanolResultado);
        TextView tvGasolinaResultado = findViewById(R.id.tvGasolinaResultado);
        TextView tvPercentual = findViewById(R.id.tvPercentualResultado);

        tvEtanolResultado.setText(String.format("Etanol R$ %.2f", ve));
        tvGasolinaResultado.setText(String.format("Gasolina R$ %.2f", vg));
        tvPercentual.setText(String.format("Percentual %.2f %%", pe));
    }
}