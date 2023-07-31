package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class simple_calculator extends JFrame {
    private JTextField displayField;
    private String expression;
    private double result;

    public simple_calculator() {
        setTitle("iPhone Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        expression = "";
        result = 0.0;

        displayField = new JTextField("0.0");
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.PLAIN, 40));
        displayField.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 4, 4));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "Del", "Clr"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            buttonPanel.add(button);
        }

        add(displayField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (Character.isDigit(command.charAt(0)) || command.equals(".")) {
                expression += command;
                displayField.setText(expression);
            } else if (command.equals("=")) {
                calculateResult();
                displayField.setText(Double.toString(result));
                expression = Double.toString(result);
            } else if (command.equals("Del")) {
                if (!expression.isEmpty()) {
                    expression = expression.substring(0, expression.length() - 1);
                    displayField.setText(expression);
                }
            } else if (command.equals("Clr")) {
                expression = "";
                result = 0.0;
                displayField.setText("0.0");
            } else {
                if (!expression.isEmpty()) {
                    expression += " " + operatorToString(command) + " ";
                    displayField.setText(expression);
                }
            }
        }

        private String operatorToString(String operator) {
            switch (operator) {
                case "+":
                    return "+";
                case "-":
                    return "-";
                case "*":
                    return "*";
                case "/":
                    return "/";
                default:
                    return "";
            }
        }
    }

    private void calculateResult() {
        if (!expression.isEmpty()) {
            String[] parts = expression.split("\\s+");
            double operand1 = Double.parseDouble(parts[0]);
            String operator = parts[1];
            double operand2 = Double.parseDouble(parts[2]);

            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    
                    if (operand2 != 0) {
                        result = operand1 / operand2;
                    } else {
                        System.out.println(operand2);
                        result = 0.0;
                    }
                    break;
                default:
                    result = 0.0;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                simple_calculator calculator = new simple_calculator();
                calculator.setVisible(true);
            }
        });
    }
}
