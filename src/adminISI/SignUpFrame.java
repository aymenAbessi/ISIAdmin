package adminISI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class SignUpFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailTF;
	private JPasswordField passwordField;
	private JTextField pseudoTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpFrame frame = new SignUpFrame();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public static boolean isValidEmail(String email) {
		String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public SignUpFrame() {
		DbQuery dbQuery = new DbQuery("root", "0000", "jdbc:mysql://localhost:3306/emploidutemps_db");
		setTitle("SignUp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 679, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel greenPanel = new JPanel();
		greenPanel.setLayout(null);
		greenPanel.setForeground(Color.BLACK);
		greenPanel.setBackground(new Color(209, 255, 189));
		greenPanel.setBounds(0, 0, 346, 400);
		contentPane.add(greenPanel);

		JLabel gererLabel = new JLabel("Gérez vos informations");
		gererLabel.setForeground(new Color(1, 1, 254));
		gererLabel.setFont(new Font("Yu Gothic", Font.BOLD, 19));
		gererLabel.setBounds(58, 206, 215, 35);
		greenPanel.add(gererLabel);

		JLabel adminLabel = new JLabel("administratives");
		adminLabel.setForeground(new Color(1, 1, 254));
		adminLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 32));
		adminLabel.setBounds(55, 230, 236, 59);
		greenPanel.add(adminLabel);

		ImageIcon originalIcon = new ImageIcon(this.getClass().getResource("/isi_logo.png"));
		Image originalImage = originalIcon.getImage();
		Image resizedImage = originalImage.getScaledInstance(200, 100, Image.SCALE_SMOOTH); // Adjust width and height
		ImageIcon resizedIcon = new ImageIcon(resizedImage);
		greenPanel.setLayout(null);
		JLabel photoLabel = new JLabel(resizedIcon);
		photoLabel.setBounds(55, 38, 200, 100);
		greenPanel.add(photoLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(410, 105, 171, 2);
		contentPane.add(separator);

		JLabel lblSignup = new JLabel("SIGNUP");
		lblSignup.setFont(new Font("Yu Gothic", Font.BOLD, 27));
		lblSignup.setBounds(442, 75, 160, 38);
		contentPane.add(lblSignup);

		JLabel emailLabel = new JLabel("E-mail :");
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		emailLabel.setBounds(374, 184, 45, 22);
		contentPane.add(emailLabel);

		emailTF = new JTextField();
		emailTF.setColumns(10);
		emailTF.setBounds(475, 184, 160, 25);
		contentPane.add(emailTF);

		JLabel passwordLabel = new JLabel("Mot de passe :");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passwordLabel.setBounds(374, 236, 91, 13);
		contentPane.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(475, 233, 160, 25);
		contentPane.add(passwordField);

		JButton creerCompteBtn = new JButton("Créer Compte");
		creerCompteBtn.setForeground(Color.WHITE);
		creerCompteBtn.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		creerCompteBtn.setBackground(new Color(3, 129, 3));
		creerCompteBtn.setBounds(442, 315, 139, 21);
		creerCompteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pseudoTF.getText().isEmpty() || emailTF.getText().isEmpty() || passwordField.getText().isEmpty()) {

					String errorMsgCreerCompte = "Veuillez saisir tous les données pour créer un compte.";
					JOptionPane.showMessageDialog(null, errorMsgCreerCompte, null, JOptionPane.ERROR_MESSAGE);

				} else if (!isValidEmail(emailTF.getText())) {
					String errorMsgEmail = "Format de l'adresse email invalide !";
					JOptionPane.showMessageDialog(null, errorMsgEmail, null, JOptionPane.ERROR_MESSAGE);
				} else {
					dbQuery.signUpAcc(pseudoTF.getText(), emailTF.getText(), passwordField.getText());
					JOptionPane.showMessageDialog(null, "Votre compte a été créé avec succès.", null,
							JOptionPane.INFORMATION_MESSAGE);

				}

			}
		});
		contentPane.add(creerCompteBtn);

		JLabel pseudoLabel = new JLabel("Pseudo :");
		pseudoLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pseudoLabel.setBounds(374, 134, 70, 22);
		contentPane.add(pseudoLabel);

		pseudoTF = new JTextField();
		pseudoTF.setColumns(10);
		pseudoTF.setBounds(475, 134, 160, 25);
		contentPane.add(pseudoTF);

		JButton loginBtn = new JButton("Vous avez déja un compte ?");
		loginBtn.setForeground(Color.WHITE);
		loginBtn.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		loginBtn.setBackground(new Color(3, 129, 3));
		loginBtn.setBounds(401, 346, 219, 21);
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginFrame login = new LoginFrame();
				login.setVisible(true);
				setVisible(false);

			}
		});
		contentPane.add(loginBtn);
	}

}
