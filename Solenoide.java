import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Solenoide implements ActionListener {

    //Variaveis globais
    JFrame frame;
    JButton calcBtn, resetBtn;
    JCheckBox calc_fluxo_magnetico_CB;
    JLabel campo_magnetico_L, corrente_L, raio_L, numEspiras_L, comprimento_L, fluxo_magnetico_L;
    JTextField campo_magnetico_TF, corrente_TF, raio_TF, numEspiras_TF, comprimento_TF, fluxo_magnetico_TF;
    static DecimalFormat df =new DecimalFormat("#.0000000", new DecimalFormatSymbols(Locale.US));
    static Double permiabilidade_magnietica_vacuo= 4*Math.PI * Math.pow(10,-7);

    Double campo_magnetico, corrente, raio, numEspiras, comprimento, fluxo_magnetico;
    
    public Solenoide() {
        // Inicializa o JFrame
        frame = new JFrame("Calculo de Solenoide");
        frame.setSize(400, 400);
        frame.setLayout(null);

        // Inicializa os componentes
        campo_magnetico_L = new JLabel("Campo Magnetico (T)(0 a 10):");
        campo_magnetico_L.setBounds(20, 20, 200, 30);
        campo_magnetico_TF = new JTextField();
        campo_magnetico_TF.setBounds(220, 20, 150, 30);
        campo_magnetico_TF.setText("0.0");

        corrente_L = new JLabel("Corrente (A)(0 a 100):");
        corrente_L.setBounds(20, 60, 200, 30);
        corrente_TF = new JTextField();
        corrente_TF.setBounds(220, 60, 150, 30);
        corrente_TF.setText("0.0");

        raio_L = new JLabel("Raio (m)(0 a 10):");
        raio_L.setBounds(20, 180, 200, 30);
        raio_TF = new JTextField();
        raio_TF.setBounds(220, 180, 150, 30);
        raio_TF.setText("0.0");
        raio_L.setVisible(false);
        raio_TF.setVisible(false);

        numEspiras_L = new JLabel("Numero de Espiras(0 a 1000000):");
        numEspiras_L.setBounds(20, 140, 200, 30);
        numEspiras_TF = new JTextField();
        numEspiras_TF.setBounds(220, 140, 150, 30);
        numEspiras_TF.setText("0.0");

        comprimento_L = new JLabel("Comprimento (m)(0 a 10):");
        comprimento_L.setBounds(20, 100, 200, 30);
        comprimento_TF = new JTextField();
        comprimento_TF.setBounds(220, 100, 150, 30);
        comprimento_TF.setText("0.0");

        fluxo_magnetico_L = new JLabel("Fluxo Magnetico(Wb)(0 a 10):");
        fluxo_magnetico_L.setBounds(20, 220, 200, 30);
        fluxo_magnetico_TF = new JTextField();
        fluxo_magnetico_TF.setBounds(220, 220, 150, 30);
        fluxo_magnetico_TF.setText("0.0");
        fluxo_magnetico_L.setVisible(false);
        fluxo_magnetico_TF.setVisible(false);

        calcBtn = new JButton("Calcular");
        calcBtn.setBounds(40, 270, 100, 30);
        calcBtn.addActionListener(this);
        calcBtn.setFocusable(false);

        resetBtn = new JButton("Resetar");
        resetBtn.setBounds(220, 270, 100, 30);
        resetBtn.addActionListener(this);
        resetBtn.setFocusable(false);

        calc_fluxo_magnetico_CB = new JCheckBox("Calcular Fluxo Magnetico");
        calc_fluxo_magnetico_CB.setBounds(100, 300, 200, 30);
        calc_fluxo_magnetico_CB.setSelected(false);
        calc_fluxo_magnetico_CB.addActionListener(this);

        frame.add(campo_magnetico_L);
        frame.add(campo_magnetico_TF);
        frame.add(corrente_L);
        frame.add(corrente_TF);
        frame.add(raio_L);
        frame.add(raio_TF);
        frame.add(numEspiras_L);
        frame.add(numEspiras_TF);
        frame.add(comprimento_L);
        frame.add(comprimento_TF);
        frame.add(fluxo_magnetico_L);
        frame.add(fluxo_magnetico_TF);
        frame.add(calcBtn);
        frame.add(resetBtn);
        frame.add(calc_fluxo_magnetico_CB);
        frame.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == calc_fluxo_magnetico_CB) {
            if (calc_fluxo_magnetico_CB.isSelected()) {
                fluxo_magnetico_L.setVisible(true);
                fluxo_magnetico_TF.setVisible(true);
                 fluxo_magnetico_TF.setText("0.0");
                raio_L.setVisible(true);
                raio_TF.setVisible(true);
                raio_TF.setText("0.0");
            } else {
                fluxo_magnetico_L.setVisible(false);
                fluxo_magnetico_TF.setVisible(false);
                 fluxo_magnetico_TF.setText("0.0");
                raio_L.setVisible(false);
                raio_TF.setVisible(false);
                raio_TF.setText("0.0");
            }
        }
        if (e.getSource() == calcBtn) {
           try{
            if(calc_fluxo_magnetico_CB.isSelected()) {
                AtribuiZeroEmCamposVazios();
                VerificaCaracteres();
                SubistiuiVirgulaPorPonto();
                InicializaVariaveis();
                VerificarLimitesDeValores();
                VerificaPossibilidadeDeCalculoFM();
                CalcularFM();
            }else {
                AtribuiZeroEmCamposVazios();
                VerificaCaracteres();
                SubistiuiVirgulaPorPonto();
                InicializaVariaveis();
                VerificarLimitesDeValores();
                VerificaPossibilidadeDeCalculoCM();
                CalcularCM();
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao calcular: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        }
        if(e.getSource() == resetBtn) {
            campo_magnetico_TF.setText("0.0");
            corrente_TF.setText("0.0");
            comprimento_TF.setText("0.0");
            numEspiras_TF.setText("0.0");
            raio_TF.setText("0.0");
            fluxo_magnetico_TF.setText("0.0");
        }
        
    }
    
    // Verifica se os campos de texto contêm apenas números e pontos/virgulas
    // e lança uma exceção se algum campo contiver caracteres inválidos.
    private void VerificaCaracteres() throws Exception {
        if(!campo_magnetico_TF.getText().matches("[\\d.,]+")){
            throw new Exception("Campo Magnetico deve conter apenas numeros");
        }
        if(!corrente_TF.getText().matches("[\\d.,]+")){
            throw new Exception("Corrente deve conter apenas numeros");
        }
        if(!comprimento_TF.getText().matches("[\\d.,]+")){
            throw new Exception("Comprimento deve conter apenas numeros");
        }
        if(!numEspiras_TF.getText().matches("[\\d.,]+")){
            throw new Exception("Numero de Espiras deve conter apenas numeros");
        }
        if(!raio_TF.getText().matches("[\\d.,]+")){
            throw new Exception("Raio deve conter apenas numeros");
        }
        if(!fluxo_magnetico_TF.getText().matches("[\\d.,]+")){
            throw new Exception("Fluxo Magnetico deve conter apenas numeros");
        }

           
    }

    // Inicializa as variáveis com os valores dos campos de texto.
    // Converte os valores de String para Double.
    private void InicializaVariaveis() {
      campo_magnetico = Double.parseDouble(campo_magnetico_TF.getText());
        corrente = Double.parseDouble(corrente_TF.getText());
        comprimento = Double.parseDouble(comprimento_TF.getText());
        numEspiras = Double.parseDouble(numEspiras_TF.getText());
        raio = Double.parseDouble(raio_TF.getText());
        fluxo_magnetico = Double.parseDouble(fluxo_magnetico_TF.getText());
    }

    // Verifica se os valores dos campos estão dentro dos limites especificados.
    // Lança uma exceção se algum valor estiver fora dos limites.
    private void VerificarLimitesDeValores() throws Exception {
        if(campo_magnetico <0 || campo_magnetico > 10){
            throw new Exception("Campo Magnetico deve ter valores entre 0 e 10 T");
        }
        if(corrente <0 || corrente > 100){
            throw new Exception("Corrente deve ter valores entre 0 e 100 A");
        }
        if(comprimento <0 || comprimento > 10){
            throw new Exception("Comprimento deve ter valores entre 0 e 10 m");
        }
        if(numEspiras <0 || numEspiras > 1000000){
            throw new Exception("Numero de Espiras deve ter valores entre 0 e 1000000");
        }
        if(raio <0 || raio > 10){
            throw new Exception("Raio deve ter valores entre 0 e 10 m");
        }
        if(fluxo_magnetico <0 || fluxo_magnetico > 10){
            throw new Exception("Fluxo Magnetico deve ter valores entre 0 e 10 Wb");
        }
    }

    // Calcula o Fluxo Magnetico ou o Raio com base nas variáveis informadas.
    // Se o Campo Magnetico for 0, chama a função para calcular o Campo Magnetico.
    // Se o Fluxo Magnetico for 0, calcula o Fluxo Magnetico com base no Campo Magnetico e Raio.
    // Se o Raio for 0, calcula o Raio com base no Fluxo Magnetico e Campo Magnetico.
    // Lança uma exceção se não for possível calcular com as variáveis atuais.
    private void CalcularFM() {
try{
        if(campo_magnetico==0.0){
            VerificaPossibilidadeDeCalculoCM();
            CalcularCM();
        }
    }catch(IllegalArgumentException e){
        throw new IllegalArgumentException("Nao e possivel calcular o Fluxo Magnetico ou o Raio pois nao se tem \n o Campo Magnetico ou nao e possivel calcula-lo com as variaveis atuais\n Insira o campo magnetico ou prencha as vairaveis corrente, comprimento e numero de espiras");
    }
        if(fluxo_magnetico==0.0){
            fluxo_magnetico = campo_magnetico * Math.PI * Math.pow(raio, 2);
            fluxo_magnetico_TF.setText(df.format(fluxo_magnetico));
            return;
        }
        if(raio==0.0){
            raio = Math.sqrt(fluxo_magnetico / (campo_magnetico * Math.PI));
            raio_TF.setText(df.format(raio));
            return;
        }
    }

    // Substitui vírgulas por pontos nos campos de texto para garantir que os valores sejam
    // interpretados corretamente como números decimais.
    private void SubistiuiVirgulaPorPonto() {
        campo_magnetico_TF.setText(campo_magnetico_TF.getText().replace(',', '.'));
        corrente_TF.setText(corrente_TF.getText().replace(',', '.'));
        comprimento_TF.setText(comprimento_TF.getText().replace(',', '.'));
        numEspiras_TF.setText(numEspiras_TF.getText().replace(',', '.'));
        raio_TF.setText(raio_TF.getText().replace(',', '.'));
        fluxo_magnetico_TF.setText(fluxo_magnetico_TF.getText().replace(',', '.'));
    }

    //Verifica se é possivel calcular o Fluxo Magnetico ou o Raio
    private void VerificaPossibilidadeDeCalculoFM() throws Exception {    
        
    try{
        
        int contagem_variaves=0;
        if (fluxo_magnetico != 0.0) {
            contagem_variaves++;
        }
        if(raio != 0.0){
            contagem_variaves++;
        }
        if(contagem_variaves ==0){
            throw new Exception("Para calcular o Fluxo Magnetico ou o Raio e necessario preencher pelo menos uma das variaveis: \n Fluxo Magnetico ou Raio");
        } 
        if(contagem_variaves == 2){
            throw new Exception("Para calcular o Fluxo Magnetico ou o Raio e necessario preencher APENAS uma das variaveis: \n Fluxo Magnetico ou Raio - apague uma delas");
        }

        
    } catch(IllegalArgumentException e){
        throw new IllegalArgumentException("Nao e possivel calcular o Fluxo Magnetico pois nao se tem: \n Campo magnetico e nem e possivel calcula-lo com as variaveis atuais");
    } catch (Exception e) {
        throw new Exception(e.getMessage());
    }
    }

    //Calcula o Campo Magnético ou quais quer variaves relacionadas caso seja possível
    private void CalcularCM() {
        if(campo_magnetico==0.0){
            campo_magnetico = (permiabilidade_magnietica_vacuo * corrente * numEspiras) / comprimento;
            campo_magnetico_TF.setText(df.format(campo_magnetico));
            return;
        }
        if(corrente==0.0){
            corrente = (campo_magnetico * comprimento) / (permiabilidade_magnietica_vacuo * numEspiras);
            corrente_TF.setText(df.format(corrente));
            return;
        }
        if(comprimento==0.0){
            comprimento = (permiabilidade_magnietica_vacuo * corrente * numEspiras) / campo_magnetico;
            comprimento_TF.setText(df.format(comprimento));
            return;
        }
        if(numEspiras==0.0){
            numEspiras = (campo_magnetico * comprimento) / (permiabilidade_magnietica_vacuo * corrente);
            numEspiras_TF.setText(df.format(numEspiras));
            return;
        }
        

        
    }

    //Atribui 0 a campos de texto vazios para evitar valores nulos
    private void AtribuiZeroEmCamposVazios() {
        if(campo_magnetico_TF.getText().isEmpty()){
        campo_magnetico_TF.setText("0.0");
       }
        if(corrente_TF.getText().isEmpty()){
            corrente_TF.setText("0.0");
        }
        if(comprimento_TF.getText().isEmpty()){
            comprimento_TF.setText("0.0");
        } 
        if(numEspiras_TF.getText().isEmpty()){
            numEspiras_TF.setText("0.0");
        }
        if(raio_TF.getText().isEmpty()){
            raio_TF.setText("0.0");
        }
        if(fluxo_magnetico_TF.getText().isEmpty()){
            fluxo_magnetico_TF.setText("0.0");
        }
    }
    
    // Verifica se e possivel calcular o Campo Magnetico ou suas respectivas variáveis
    // com base nas entradas do usuário.
    // e necessário informar 3 variáveis diferentes entre Campo Magnetico, Corrente,
    // Comprimento e Número de Espiras.
    private void VerificaPossibilidadeDeCalculoCM() {
       

        campo_magnetico = Double.parseDouble(campo_magnetico_TF.getText());
        corrente = Double.parseDouble(corrente_TF.getText());
        comprimento = Double.parseDouble(comprimento_TF.getText());
        numEspiras = Double.parseDouble(numEspiras_TF.getText());

        int contagem_variaves=0;
        if (campo_magnetico != 0.0) {
            contagem_variaves++;
        }
        if (corrente != 0.0) {
            contagem_variaves++;
        }
        if (comprimento != 0.0) {
            contagem_variaves++;
        } 
        if (numEspiras != 0.0) {
            contagem_variaves++;
        }

        if (contagem_variaves < 3) {
            throw new IllegalArgumentException("Para calcular e necessario informar 3 variaveis diferentes entre:\n Campo Magnetico, Corrente, Comprimento e Numero de Espiras.");
        }
        if(contagem_variaves == 4) {
            throw new IllegalArgumentException("Para calcular e necessario informar APENAS 3 variaveis diferentes entre:\n Campo Magnetico, Corrente, Comprimento e Numero de Espiras.Use o botao Resetar para limpar os campos. e insira novamete");
        }

    }

    public static void main(String[] args) {
                new Solenoide();
        }
}

 
