package adminISI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Toolkit;

public class MenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuFrame frame = new MenuFrame();
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
	public MenuFrame() {
		setResizable(false);
		setTitle("ISI Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 433);
		contentPane = new JPanel();
		setLocationRelativeTo(null);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnFormulaire = new JButton("Formulaires");
		btnFormulaire.setToolTipText("consulter les formulaires");
		btnFormulaire.setBackground(Color.WHITE);
		btnFormulaire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormulaireFrame formulaireFrame = new FormulaireFrame();
				formulaireFrame.setVisible(true);
				setVisible(false);
			}
		});
		btnFormulaire.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnFormulaire.setBounds(197, 276, 207, 45);
		contentPane.add(btnFormulaire);

		JLabel welcomeLabel = new JLabel("Bienvenue à l'application de gestion d'emplois");
		welcomeLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 19));
		welcomeLabel.setBounds(100, 126, 448, 57);
		contentPane.add(welcomeLabel);

		ImageIcon originalIcon = new ImageIcon(this.getClass().getResource("/isi_logo.png"));
		Image originalImage = originalIcon.getImage();
		Image resizedImage = originalImage.getScaledInstance(200, 100, Image.SCALE_SMOOTH); // Adjust width and height
		ImageIcon resizedIcon = new ImageIcon(resizedImage);
		JLabel photoLabel = new JLabel(resizedIcon);
		photoLabel.setBounds(197, 10, 207, 106);
		contentPane.add(photoLabel);

		JSeparator separatorUnderLogo = new JSeparator();
		separatorUnderLogo.setForeground(new Color(0, 0, 64));
		separatorUnderLogo.setBounds(75, 116, 448, 11);
		contentPane.add(separatorUnderLogo);

		JPanel panelConsulter = new JPanel();
		panelConsulter.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelConsulter.setBackground(new Color(3, 129, 3));
		panelConsulter.setBounds(135, 198, 328, 164);
		contentPane.add(panelConsulter);
		panelConsulter.setLayout(null);

		JLabel selectInterfaceLabel = new JLabel("Vous pouvez consulter ");
		selectInterfaceLabel.setBounds(93, 43, 150, 23);
		panelConsulter.add(selectInterfaceLabel);
		selectInterfaceLabel.setForeground(Color.WHITE);
		selectInterfaceLabel.setFont(new Font("Tahoma", Font.BOLD, 12));

		JButton deconnecterBtn = new JButton("Se déconnecter");
		deconnecterBtn.setBackground(new Color(3, 129, 3));
		deconnecterBtn.setForeground(new Color(255, 255, 255));
		deconnecterBtn.setFont(new Font("Tahoma", Font.PLAIN, 7));
		deconnecterBtn.setBounds(521, 10, 85, 21);
		deconnecterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginFrame login = new LoginFrame();
				login.setVisible(true);
				setVisible(false);
				JOptionPane.showMessageDialog(null, "Vous êtes déconnecté.", null, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		contentPane.add(deconnecterBtn);
	}
}
