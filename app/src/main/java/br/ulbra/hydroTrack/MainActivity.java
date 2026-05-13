package br.ulbra.hydroTrack;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText edAgua, edPeso, edIdade;
        Button btCal;
        TextView txRes;
        RadioGroup rgAtv;

        txRes = findViewById(R.id.txtRes);
        edAgua = findViewById(R.id.edtAgua);
        edIdade = findViewById(R.id.edtIdade);
        edPeso = findViewById(R.id.edtPeso);
        btCal = findViewById(R.id.btnCal);
        rgAtv = findViewById(R.id.rgAtv);

        btCal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 1. Pegar os textos primeiro para validar
                String sIdade = edIdade.getText().toString();
                String sPeso = edPeso.getText().toString();
                String sAgua = edAgua.getText().toString();
                int idSelecionado = rgAtv.getCheckedRadioButtonId();

                // 2. Validação: Verifica se algum campo está em branco ou se o RadioGroup não foi marcado
                if (sIdade.isEmpty() || sPeso.isEmpty() || sAgua.isEmpty()) {
                    txRes.setText("Preencha todos os campos numéricos!");
                } else if (idSelecionado == -1) {
                    txRes.setText("Selecione o seu nível de atividade!");
                } else {
                    // 3. Se passou na validação, faz os cálculos
                    try {
                        int idade = Integer.parseInt(sIdade);
                        double peso = Double.parseDouble(sPeso);
                        double agua = Double.parseDouble(sAgua);
                        double ct = 0, r;

                        // Identificar o RadioButton selecionado
                        RadioButton rbSelecionado = findViewById(idSelecionado);
                        String textoOpcao = rbSelecionado.getText().toString();

                        if (textoOpcao.equals("Sedentário")) {
                            ct = peso * 35;
                        } else if (textoOpcao.equals("Moderado")) {
                            ct = (peso * 35) + 300;
                        } else if (textoOpcao.equals("Intenso")) {
                            ct = (peso * 35) + 600;
                        }

                        r = ct - agua;

                        // Formata a saída (opcional: use String.format para arredondar)
                        txRes.setText(String.format("Meta diária: %.0f ml\nFaltam: %.0f ml", ct, r));

                    } catch (NumberFormatException e) {
                        txRes.setText("Erro: Digite apenas números válidos!");
                    }
                }
            }
        });
    }
}