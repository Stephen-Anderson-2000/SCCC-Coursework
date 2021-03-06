
import org.netbeans.xml.schema.shares.Share;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author steppy
 */
public class NewCompanyGUI extends javax.swing.JFrame {

    /**
     * Creates new form NewCompanyGUI
     */
    public NewCompanyGUI() {
        initComponents();
        invalidInputLabel.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        companyNameLabel = new javax.swing.JLabel();
        companyNameTextField = new javax.swing.JTextField();
        companySymbolLabel = new javax.swing.JLabel();
        companySymbolTextField = new javax.swing.JTextField();
        availableSharesLabel = new javax.swing.JLabel();
        availableSharesTextField = new javax.swing.JTextField();
        currencyTextField = new javax.swing.JTextField();
        currencyLabel = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();
        priceTextField = new javax.swing.JTextField();
        addCompanyButton = new javax.swing.JButton();
        invalidInputLabel = new javax.swing.JLabel();
        wikipediaNameLable = new javax.swing.JLabel();
        wikipediaNameTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(new java.awt.Point(480, 240));

        companyNameLabel.setText("Company Name");

        companySymbolLabel.setText("Company Symbol");

        availableSharesLabel.setText("Available Shares");

        currencyLabel.setText("Currency");

        priceLabel.setText("Price");

        addCompanyButton.setText("CONFIRM");
        addCompanyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCompanyButtonActionPerformed(evt);
            }
        });

        invalidInputLabel.setText("INVALID DATA IN FIELDS!");

        wikipediaNameLable.setText("Wikipedia Name");

        wikipediaNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wikipediaNameTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(62, 62, 62)
                            .addComponent(companyNameLabel)
                            .addGap(18, 18, 18)
                            .addComponent(companyNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(53, 53, 53)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(availableSharesLabel)
                                .addComponent(companySymbolLabel)
                                .addComponent(wikipediaNameLable)
                                .addComponent(currencyLabel)
                                .addComponent(priceLabel))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(priceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(currencyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(wikipediaNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(availableSharesTextField)
                                            .addComponent(companySymbolTextField)))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(invalidInputLabel)
                        .addGap(18, 18, 18)
                        .addComponent(addCompanyButton)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(companyNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(companyNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(companySymbolLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(companySymbolTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(availableSharesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(availableSharesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wikipediaNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(wikipediaNameLable))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currencyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currencyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(invalidInputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                    .addComponent(addCompanyButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addCompanyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCompanyButtonActionPerformed
        /**
         * Just a simple function to get the relevant variables that can be 
         * used to create a new Share object. Displays the relevant warning to
         * a user if there is an invalid field
         */
        String companyName = companyNameTextField.getText();
        String companySymbol = companySymbolTextField.getText();
        String wikipediaName = wikipediaNameTextField.getText();
        int availableShares = -1;
        double price = -1.0;
        try {
            availableShares = Integer.parseInt(availableSharesTextField.getText());
            price = Double.parseDouble(priceTextField.getText());
        }
        catch (NumberFormatException | NullPointerException e) {
            System.out.print(e);
        }
        String currency = currencyTextField.getText();
        
        if (availableShares > 0 && price > 0 && !companyName.isEmpty() 
                && !companySymbol.isEmpty() && !wikipediaName.isEmpty()) {
            // Runs the SharesBrokerWS function to marshall a new object
            addNewCompany(companyName, companySymbol, availableShares, wikipediaName,
                currency, price);
            // Closes the window
            this.dispose();
        }
        else {
            invalidInputLabel.setVisible(true);
        }
    }//GEN-LAST:event_addCompanyButtonActionPerformed

    private void wikipediaNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wikipediaNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_wikipediaNameTextFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewCompanyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewCompanyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewCompanyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewCompanyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewCompanyGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCompanyButton;
    private javax.swing.JLabel availableSharesLabel;
    private javax.swing.JTextField availableSharesTextField;
    private javax.swing.JLabel companyNameLabel;
    private javax.swing.JTextField companyNameTextField;
    private javax.swing.JLabel companySymbolLabel;
    private javax.swing.JTextField companySymbolTextField;
    private javax.swing.JLabel currencyLabel;
    private javax.swing.JTextField currencyTextField;
    private javax.swing.JLabel invalidInputLabel;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JTextField priceTextField;
    private javax.swing.JLabel wikipediaNameLable;
    private javax.swing.JTextField wikipediaNameTextField;
    // End of variables declaration//GEN-END:variables

    private static void addNewCompany(java.lang.String arg0, java.lang.String arg1, int arg2, java.lang.String arg3, java.lang.String arg4, double arg5) {
        org.me.sharesbroker.SharesBrokerWS_Service service = new org.me.sharesbroker.SharesBrokerWS_Service();
        org.me.sharesbroker.SharesBrokerWS port = service.getSharesBrokerWSPort();
        port.addNewCompany(arg0, arg1, arg2, arg3, arg4, arg5);
    }

}
