package adminISI;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailTF;
	private JPasswordField passwordTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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

	public LoginFrame() {
		DbQuery dbQuery = new DbQuery("root", "0000", "jdbc:mysql://localhost:3306/emploidutemps_db");
		setTitle("Login ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 679, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel greenPanel = new JPanel();
		greenPanel.setBackground(new Color(209, 255, 189));
		greenPanel.setForeground(new Color(0, 0, 0));
		greenPanel.setBounds(0, 0, 346, 400);
		contentPane.add(greenPanel);

		ImageIcon originalIcon = new ImageIcon(this.getClass().getResource("/isi_logo.png"));
		Image originalImage = originalIcon.getImage();
		Image resizedImage = originalImage.getScaledInstance(200, 100, Image.SCALE_SMOOTH); // Adjust width and height
		ImageIcon resizedIcon = new ImageIcon(resizedImage);
		greenPanel.setLayout(null);
		JLabel photoLabel = new JLabel(resizedIcon);
		photoLabel.setBounds(55, 38, 200, 100);
		greenPanel.add(photoLabel);

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

		JLabel connectLabel = new JLabel("LOGIN");
		connectLabel.setBounds(437, 106, 160, 38);
		contentPane.add(connectLabel);
		connectLabel.setFont(new Font("Yu Gothic", Font.BOLD, 27));

		JLabel emailLabel = new JLabel("E-mail :");
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		emailLabel.setBounds(371, 164, 45, 22);
		contentPane.add(emailLabel);

		emailTF = new JTextField();
		emailTF.setBounds(472, 164, 160, 25);
		contentPane.add(emailTF);
		emailTF.setColumns(10);

		JLabel passwordLabel = new JLabel("Mot de passe :");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passwordLabel.setBounds(371, 216, 91, 13);
		contentPane.add(passwordLabel);

		passwordTF = new JPasswordField();
		passwordTF.setBounds(472, 213, 160, 25);
		contentPane.add(passwordTF);

		JSeparator separator = new JSeparator();
		separator.setBounds(405, 136, 171, 2);
		contentPane.add(separator);

		JButton connecterBtn = new JButton("Connecter");
		connecterBtn.setForeground(new Color(255, 255, 255));
		connecterBtn.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		connecterBtn.setBackground(new Color(3, 129, 3));
		connecterBtn.setBounds(437, 291, 139, 21);
		connecterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (emailTF.getText().length() == 0 || passwordTF.getText().length() == 0) {
					String errorMsgConnect = "Veuillez entrez votre email et votre mot de passe.";
					JOptionPane.showMessageDialog(null, errorMsgConnect, null, JOptionPane.ERROR_MESSAGE);

				} else if (!isValidEmail(emailTF.getText())) {
					String errorMsgEmail = "Format de l'adresse email invalide !";
					JOptionPane.showMessageDialog(null, errorMsgEmail, null, JOptionPane.ERROR_MESSAGE);
				} else {
					if (dbQuery.authenticateUser(emailTF.getText(), passwordTF.getText())) {
						MenuFrame menu = new MenuFrame();
						menu.setVisible(true);
						setVisible(false);
						JOptionPane.showMessageDialog(null, "Vous êtes connecté avec succès.", null,
								JOptionPane.INFORMATION_MESSAGE);
					} else
						JOptionPane.showMessageDialog(null,
								"Veuillez entrez votre email et votre mot de passe correcte.", null,
								JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		contentPane.add(connecterBtn);

		JButton signUpBtn = new JButton("Vous n'avez pas un compte ?");
		signUpBtn.setBackground(new Color(3, 129, 3));
		signUpBtn.setForeground(new Color(255, 255, 255));
		signUpBtn.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		signUpBtn.setBounds(393, 322, 219, 21);
		signUpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUpFrame signup = new SignUpFrame();
				signup.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(signUpBtn);

	}
}
