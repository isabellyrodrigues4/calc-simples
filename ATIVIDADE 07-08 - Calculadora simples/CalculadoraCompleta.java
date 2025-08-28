import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CalculadoraCompleta extends JFrame {

    private final JTextField txtA = new JTextField(10);
    private final JTextField txtB = new JTextField(10);
    private final JLabel lblResultado = new JLabel("Resultado: —");

    public CalculadoraCompleta() {
        super("Calculadora Completa");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        c.gridx = 0; c.gridy = 0; add(new JLabel("Número 1:"), c);
        c.gridx = 1; c.gridy = 0; add(txtA, c);
        c.gridx = 0; c.gridy = 1; add(new JLabel("Número 2:"), c);
        c.gridx = 1; c.gridy = 1; add(txtB, c);

        // Botões de operação
        JPanel botoes = new JPanel(new GridLayout(1, 5, 8, 0));
        JButton btnSoma = new JButton("+");
        JButton btnSub  = new JButton("-");
        JButton btnMul  = new JButton("×");
        JButton btnDiv  = new JButton("÷");
        JButton btnLimpar = new JButton("Limpar");
        botoes.add(btnSoma); botoes.add(btnSub); botoes.add(btnMul); botoes.add(btnDiv); botoes.add(btnLimpar);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        add(botoes, c);

        // Resultado
        lblResultado.setFont(lblResultado.getFont().deriveFont(Font.BOLD, 14f));
        c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
        add(lblResultado, c);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuOperacoes = new JMenu("Operações");

        JMenuItem itemSoma = new JMenuItem("Somar");
        JMenuItem itemSub  = new JMenuItem("Subtrair");
        JMenuItem itemMul  = new JMenuItem("Multiplicar");
        JMenuItem itemDiv  = new JMenuItem("Dividir");
        JMenuItem itemLimpar = new JMenuItem("Limpar");

        // Atalhos de teclado
        itemSoma.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        itemSub.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
        itemMul.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
        itemDiv.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        itemLimpar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));

        menuOperacoes.add(itemSoma);
        menuOperacoes.add(itemSub);
        menuOperacoes.add(itemMul);
        menuOperacoes.add(itemDiv);
        menuOperacoes.addSeparator();
        menuOperacoes.add(itemLimpar);
        menuBar.add(menuOperacoes);
        setJMenuBar(menuBar);

        // Listeners
        btnSoma.addActionListener(e -> calcular('+'));
        btnSub.addActionListener(e -> calcular('-'));
        btnMul.addActionListener(e -> calcular('*'));
        btnDiv.addActionListener(e -> calcular('/'));
        btnLimpar.addActionListener(e -> limpar());

        itemSoma.addActionListener(e -> calcular('+'));
        itemSub.addActionListener(e -> calcular('-'));
        itemMul.addActionListener(e -> calcular('*'));
        itemDiv.addActionListener(e -> calcular('/'));
        itemLimpar.addActionListener(e -> limpar());

        // Enter faz soma
        Action somaEnter = new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { calcular('+'); }
        };
        txtA.addActionListener(somaEnter);
        txtB.addActionListener(somaEnter);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void calcular(char op) {
        Double a = lerNumero(txtA);
        Double b = lerNumero(txtB);

        if (a == null || b == null) {
            lblResultado.setText("Resultado: erro – digite números válidos.");
            return;
        }

        double r;
        switch (op) {
            case '+': r = a + b; break;
            case '-': r = a - b; break;
            case '*': r = a * b; break;
            case '/':
                if (b == 0) {
                    lblResultado.setText("Resultado: erro – divisão por zero.");
                    return;
                }
                r = a / b; break;
            default: return;
        }
        lblResultado.setText("Resultado: " + r);
    }

    private Double lerNumero(JTextField campo) {
        String s = campo.getText().trim().replace(',', '.');
        if (s.isEmpty()) return null;
        try { return Double.parseDouble(s); }
        catch (NumberFormatException e) { return null; }
    }

    private void limpar() {
        txtA.setText("");
        txtB.setText("");
        lblResultado.setText("Resultado: —");
        txtA.requestFocus();
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(CalculadoraCompleta::new);
    }
}
