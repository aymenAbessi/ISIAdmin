package adminISI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTable;

public class RequetesFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField matiereTF;
	private JTable seanceTable;
	private JTextField idTF;
	private JScrollPane scrollPanelSeance;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RequetesFrame frame = new RequetesFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void showUpdatedTable(DefaultTableModel dtm, String classe, String matiere) {
		DbQuery dbQuery = new DbQuery("root", "0000", "jdbc:mysql://localhost:3306/emploidutemps_db");
		String ens_c = "";
		if (dtm.getRowCount() > 0) {
			for (int i = dtm.getRowCount() - 1; i > -1; i--) {
				dtm.removeRow(i);
			}
		}
		if (!matiere.equals("")) {

			String cours = "";
			try {
				cours = dbQuery.searchCourseSessions(classe, matiere);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(cours);
			String[] tab_cours = cours.split(":");
			for (int i = 0; i < tab_cours.length - 1; i += 7) {
				dtm.addRow(new Object[] { tab_cours[i], tab_cours[i + 1], tab_cours[i + 2], tab_cours[i + 3],
						tab_cours[i + 4], tab_cours[i + 5], tab_cours[i + 6] });
			}
		} else {
			String timetable = "";
			try {
				timetable = dbQuery.fetchTimetableForClass(classe);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] tab_cours = timetable.split(":");
			for (int i = 0; i < tab_cours.length - 1; i += 7) {
				dtm.addRow(new Object[] { tab_cours[i], tab_cours[i + 1], tab_cours[i + 2], tab_cours[i + 3],
						tab_cours[i + 4], tab_cours[i + 5], tab_cours[i + 6] });
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public RequetesFrame() {
		DbQuery dbQuery = new DbQuery("root", "0000", "jdbc:mysql://localhost:3306/emploidutemps_db");

		DefaultTableModel dtmSeance = new DefaultTableModel();

		setTitle("Séances ISI\r\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 799, 476);
		contentPane = new JPanel();
		setLocationRelativeTo(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton retournerFormBtn = new JButton("⬅  Retourner aux formulaires");
		retournerFormBtn.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		retournerFormBtn.setToolTipText("Retourner à la page des formulaires");
		retournerFormBtn.setForeground(new Color(255, 255, 255));
		retournerFormBtn.setBackground(new Color(3, 129, 3));
		retournerFormBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormulaireFrame form = new FormulaireFrame();
				form.setVisible(true);
				setVisible(false);
			}
		});
		retournerFormBtn.setBounds(268, 10, 226, 21);
		contentPane.add(retournerFormBtn);

		JLabel seanceLabel = new JLabel("Les séances de cours dans la semaine d'une matière dans une classe");
		seanceLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		seanceLabel.setBounds(10, 45, 515, 21);
		contentPane.add(seanceLabel);

		JLabel classeLabel = new JLabel("Classe");
		classeLabel.setBounds(28, 78, 45, 13);
		contentPane.add(classeLabel);

		JLabel matiereLabel = new JLabel("Matière");
		matiereLabel.setBounds(208, 78, 45, 13);
		contentPane.add(matiereLabel);

		JComboBox classeBox = new JComboBox();
		Set<String> classes = null;
		try {
			classes = dbQuery.getClassesNames();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (String classe : classes) {
			classeBox.addItem(classe);
		}
		classeBox.setBounds(28, 97, 102, 22);
		contentPane.add(classeBox);

		matiereTF = new JTextField();
		matiereTF.setBounds(208, 97, 134, 22);
		contentPane.add(matiereTF);
		matiereTF.setColumns(10);

		JButton chercherBtn = new JButton("CHERCHER");
		chercherBtn.setToolTipText("Chercher séance par classe et matière");
		chercherBtn.setBackground(new Color(209, 255, 189));
		chercherBtn.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		chercherBtn.setBounds(358, 98, 125, 21);
		chercherBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (matiereTF.getText().length() == 0 || classeBox.getSelectedItem().equals(" ")) {
					String errorMsgFindSeance = "Veuillez spécifier la matière et la classe pour lancer la recherche d'une séance.";
					JOptionPane.showMessageDialog(null, errorMsgFindSeance, null, JOptionPane.ERROR_MESSAGE);
				} else {
					showUpdatedTable(dtmSeance, classeBox.getSelectedItem().toString(), matiereTF.getText());
				}
			}
		});
		contentPane.add(chercherBtn);

		// table seance

		seanceTable = new JTable();
		seanceTable.disable();
		scrollPanelSeance = new JScrollPane();
		scrollPanelSeance.setBounds(10, 145, 765, 157);
		scrollPanelSeance.setViewportView(seanceTable);
		contentPane.add(scrollPanelSeance);
		dtmSeance.addColumn("ID");
		dtmSeance.addColumn("Classe");
		dtmSeance.addColumn("Matiere");
		dtmSeance.addColumn("Jour");
		dtmSeance.addColumn("Heure");
		dtmSeance.addColumn("Nom enseignant");
		dtmSeance.addColumn("Contact enseignant");
		seanceTable.setModel(dtmSeance);

		idTF = new JTextField();
		idTF.setBounds(67, 322, 63, 22);
		contentPane.add(idTF);
		idTF.setColumns(10);

		JLabel idLabel = new JLabel("ID");
		idLabel.setBounds(41, 325, 28, 13);
		contentPane.add(idLabel);

		JButton supprimerBtn = new JButton("SUPPRIMER");
		supprimerBtn.setToolTipText("Supprimer séance par ID");
		supprimerBtn.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		supprimerBtn.setBackground(new Color(209, 255, 189));
		supprimerBtn.setBounds(145, 322, 125, 21);
		supprimerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idTF.getText().length() == 0) {
					String errorMsgDeleteSeance = "Veuillez spécifier l'ID d'une séance à supprimer.";
					JOptionPane.showMessageDialog(null, errorMsgDeleteSeance, null, JOptionPane.ERROR_MESSAGE);
				} else {
					if (!dbQuery.deleteCourseSession(Integer.parseInt(idTF.getText())))
						JOptionPane.showMessageDialog(null, "Échec de la suppression du cours : cours introuvable",
								null, JOptionPane.ERROR_MESSAGE);
					showUpdatedTable(dtmSeance, classeBox.getSelectedItem().toString(), matiereTF.getText());
				}
			}
		});
		contentPane.add(supprimerBtn);

		JLabel emploiLabel = new JLabel("Emploi du temps de la semaine par classe");
		emploiLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		emploiLabel.setBounds(10, 357, 515, 21);
		contentPane.add(emploiLabel);

		JLabel classeLabel2 = new JLabel("Classe");
		classeLabel2.setBounds(28, 398, 45, 13);
		contentPane.add(classeLabel2);

		JComboBox classeBox2 = new JComboBox();
		classeBox2.setBounds(78, 394, 104, 22);
		Set<String> classes2 = null;
		try {
			classes2 = dbQuery.getClassesNames();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (String classe : classes) {
			classeBox2.addItem(classe);
		}
		contentPane.add(classeBox2);

		JButton chercherBtn_2 = new JButton("CHERCHER");
		chercherBtn_2.setToolTipText("Chercher emploi du temps par classe ");
		chercherBtn_2.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		chercherBtn_2.setBackground(new Color(209, 255, 189));
		chercherBtn_2.setBounds(192, 394, 125, 21);
		chercherBtn_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (classeBox2.getSelectedItem().equals(" ")) {
					String errorMsgFindEmploi = "Veuillez spécifier la classe pour laquelle vous souhaitez consulter son emploi du temps.";
					JOptionPane.showMessageDialog(null, errorMsgFindEmploi, null, JOptionPane.ERROR_MESSAGE);
				} else {
					showUpdatedTable(dtmSeance, classeBox2.getSelectedItem().toString(), "");
				}
			}
		});
		contentPane.add(chercherBtn_2);

		JButton deconnecterBtn = new JButton("Se déconnecter");
		deconnecterBtn.setForeground(Color.WHITE);
		deconnecterBtn.setFont(new Font("Tahoma", Font.PLAIN, 7));
		deconnecterBtn.setBackground(new Color(3, 129, 3));
		deconnecterBtn.setBounds(700, 10, 85, 21);
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
