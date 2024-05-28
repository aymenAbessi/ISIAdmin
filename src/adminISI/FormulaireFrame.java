package adminISI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Set;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;

import adminISI.DbQuery;

public class FormulaireFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField matriculeTF;
	private JTextField NomTF;
	private JTextField contactTF;
	private JTextField matiereTF;
	private JTextField matriculeEnseignTF;
	private JTable tableEnseign;
	private JTable tableCours;
	private JScrollPane scrollPanelEnseign;
	private JScrollPane scrollPanelCours;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormulaireFrame frame = new FormulaireFrame();
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

	public static boolean estNumeroTel(String chaine) {
		// Vérifier si la chaîne est numérique et a une longueur de 8
		return chaine.matches("\\d{8}");
	}

	private void showUpdatedTable(DefaultTableModel dtm, String type) {
		DbQuery dbQuery = new DbQuery("root", "0000", "jdbc:mysql://localhost:3306/emploidutemps_db");
		String ens_c = "";
		if (dtm.getRowCount() > 0) {
			for (int i = dtm.getRowCount() - 1; i > -1; i--) {
				dtm.removeRow(i);
			}
		}
		if (type.equals("ens")) {
			String enseigns = "";
			try {
				enseigns = dbQuery.select("Select * from tb_enseignant");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String[] tab_enseigns = enseigns.split(":");
			for (int i = 0; i < tab_enseigns.length - 1; i += 3) {
				dtm.addRow(new Object[] { tab_enseigns[i], tab_enseigns[i + 1], tab_enseigns[i + 2] });
			}
		} else if (type.equals("cours")) {
			String cours = "";
			try {
				cours = dbQuery.select("SELECT classe,matiere,jour,heure,nom FROM tb_cours, tb_enseignant "
						+ "where tb_cours.matricule_ens = tb_enseignant.matricule;");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String[] tab_cours = cours.split(":");
			for (int i = 0; i < tab_cours.length - 1; i += 5) {
				dtm.addRow(new Object[] { tab_cours[i], tab_cours[i + 1], tab_cours[i + 2], tab_cours[i + 3],
						tab_cours[i + 4] });
			}
		}
	}

	public FormulaireFrame() {
		DbQuery dbQuery = new DbQuery("root", "0000", "jdbc:mysql://localhost:3306/emploidutemps_db");
		setTitle("Formulaires ISI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 520);
		contentPane = new JPanel();
		setLocationRelativeTo(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// table enseignant
		DefaultTableModel dtmEnseign = new DefaultTableModel();
		tableEnseign = new JTable();
		tableEnseign.disable();
		scrollPanelEnseign = new JScrollPane();
		scrollPanelEnseign.setEnabled(false);
		scrollPanelEnseign.setBounds(344, 51, 385, 163);
		scrollPanelEnseign.setViewportView(tableEnseign);
		contentPane.add(scrollPanelEnseign);
		dtmEnseign.addColumn("Matricule");
		dtmEnseign.addColumn("Nom");
		dtmEnseign.addColumn("Contact");
		showUpdatedTable(dtmEnseign, "ens");
		tableEnseign.setModel(dtmEnseign);

		JLabel enseignantLabel = new JLabel("Formulaire d'enregistrement des enseignants");
		enseignantLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		enseignantLabel.setBounds(10, 45, 363, 22);
		contentPane.add(enseignantLabel);

		JLabel seanceLabel = new JLabel("Formulaire d'enregistrement des séances de cours\r\n");
		seanceLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		seanceLabel.setBounds(10, 235, 418, 22);
		contentPane.add(seanceLabel);

		JLabel matriculeLabel = new JLabel("Matricule");
		matriculeLabel.setBounds(10, 80, 102, 13);
		contentPane.add(matriculeLabel);

		matriculeTF = new JTextField();
		matriculeTF.setBounds(122, 77, 77, 22);
		contentPane.add(matriculeTF);
		matriculeTF.setColumns(10);

		JLabel nomLabel = new JLabel("Nom");
		nomLabel.setBounds(10, 109, 102, 13);
		contentPane.add(nomLabel);

		JLabel contactLabel = new JLabel("Contact");
		contactLabel.setBounds(10, 135, 102, 13);
		contentPane.add(contactLabel);

		JButton chercherBtn = new JButton("CHERCHER");
		chercherBtn.setToolTipText("Chercher enseignant par nom");
		chercherBtn.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		chercherBtn.setBackground(new Color(209, 255, 189));
		chercherBtn.setBounds(205, 77, 120, 21);
		chercherBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (NomTF.getText().length() == 0) {
					String errorMsgFindEnseign = "Veuillez spécifier un nom d'enseignant pour lancer la recherche.";
					JOptionPane.showMessageDialog(null, errorMsgFindEnseign, null, JOptionPane.ERROR_MESSAGE);
				} else {
					String ens_c = "";
					if (dtmEnseign.getRowCount() > 0) {
						for (int i = dtmEnseign.getRowCount() - 1; i > -1; i--) {
							dtmEnseign.removeRow(i);
						}
					}
					try {
						ens_c = dbQuery.searchInstructorByName(NomTF.getText());
						System.out.println(ens_c);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String[] tab_ens_c = ens_c.split(":");
					for (int i = 0; i < tab_ens_c.length - 1; i += 3) {
						dtmEnseign.addRow(new Object[] { tab_ens_c[i], tab_ens_c[i + 1], tab_ens_c[i + 2] });
					}
				}
			}
		});
		contentPane.add(chercherBtn);

		JButton enregistrerEnseignantBtn = new JButton("ENREGISTRER");
		enregistrerEnseignantBtn.setToolTipText("Ajouter enseignant");
		enregistrerEnseignantBtn.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		enregistrerEnseignantBtn.setBackground(new Color(209, 255, 189));
		enregistrerEnseignantBtn.setBounds(10, 161, 125, 21);
		enregistrerEnseignantBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (matriculeTF.getText().length() == 0 || NomTF.getText().length() == 0
						|| contactTF.getText().length() == 0) {
					String errorMsgAddEnseign = "Une information essentielle est manquante pour procéder à l'ajout de l'enseignant. Veuillez fournir toutes les données nécessaires.";
					JOptionPane.showMessageDialog(null, errorMsgAddEnseign, null, JOptionPane.ERROR_MESSAGE);

				} else if (!estNumeroTel(contactTF.getText())) {
					String errorMsgAddEnseign = "Format de numéro de téléphone invalide.";
					JOptionPane.showMessageDialog(null, errorMsgAddEnseign, null, JOptionPane.ERROR_MESSAGE);
				} else {
					dbQuery.insertInstructor(matriculeTF.getText(), NomTF.getText(), contactTF.getText());
					showUpdatedTable(dtmEnseign, "ens");
				}
			}
		});
		contentPane.add(enregistrerEnseignantBtn);

		JButton modifierBtn = new JButton("MODIFIER");
		modifierBtn.setToolTipText("Modifier enseignant");
		modifierBtn.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		modifierBtn.setBackground(new Color(209, 255, 189));
		modifierBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (matriculeTF.getText().length() == 0) {
					String errorMsgUpdateEnseign = "La matricule de l'enseignant est requise pour effectuer des modifications. Veuillez la saisir pour continuer.";
					JOptionPane.showMessageDialog(null, errorMsgUpdateEnseign, null, JOptionPane.ERROR_MESSAGE);

				} else if (NomTF.getText().length() == 0 || contactTF.getText().length() == 0) {
					String errorMsgUpdateEnseign = "Une information essentielle est manquante pour procéder à la modification de l'enseignant "
							+ matriculeTF.getText();
					JOptionPane.showMessageDialog(null, errorMsgUpdateEnseign, null, JOptionPane.ERROR_MESSAGE);

				} else if (!estNumeroTel(contactTF.getText())) {
					String errorMsgUpdateEnseign = "Format de numéro de téléphone invalide.";
					JOptionPane.showMessageDialog(null, errorMsgUpdateEnseign, null, JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						dbQuery.updateInstructor(matriculeTF.getText(), NomTF.getText(), contactTF.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					showUpdatedTable(dtmEnseign, "ens");
				}
			}
		});
		modifierBtn.setBounds(145, 161, 132, 21);
		contentPane.add(modifierBtn);

		JButton supprimerBtn = new JButton("SUPPRIMER");
		supprimerBtn.setToolTipText("Supprimer enseignant par matricule");
		supprimerBtn.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		supprimerBtn.setBackground(new Color(209, 255, 189));
		supprimerBtn.setBounds(80, 193, 120, 21);
		supprimerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (matriculeTF.getText().length() == 0) {
					String errorMsgDeleteEnseign = "Veuillez spécifier une matricule pour supprimer un enseignant.";
					JOptionPane.showMessageDialog(null, errorMsgDeleteEnseign, null, JOptionPane.ERROR_MESSAGE);
				} else {
					if (!dbQuery.deleteInstructor(matriculeTF.getText()))
						JOptionPane.showMessageDialog(null,
								"Échec de la suppression de l'enseignant : enseignant introuvable.", null,
								JOptionPane.ERROR_MESSAGE);
					showUpdatedTable(dtmEnseign, "ens");
				}
			}
		});
		contentPane.add(supprimerBtn);

		NomTF = new JTextField();
		NomTF.setColumns(10);
		NomTF.setBounds(122, 106, 155, 22);
		contentPane.add(NomTF);

		contactTF = new JTextField();
		contactTF.setColumns(10);
		contactTF.setBounds(122, 131, 155, 22);
		contentPane.add(contactTF);

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
		classeBox.setBounds(85, 267, 125, 21);
		contentPane.add(classeBox);

		JLabel classeLabel = new JLabel("Classe");
		classeLabel.setBounds(10, 267, 45, 13);
		contentPane.add(classeLabel);

		JLabel matiereBtn = new JLabel("Matiere");
		matiereBtn.setBounds(10, 297, 45, 13);
		contentPane.add(matiereBtn);

		JLabel jourBtn = new JLabel("Jour");
		jourBtn.setBounds(10, 329, 45, 13);
		contentPane.add(jourBtn);

		JLabel matriculeEnseignantLabel = new JLabel("Matricule enseignant ");
		matriculeEnseignantLabel.setBounds(10, 388, 125, 13);
		contentPane.add(matriculeEnseignantLabel);

		JLabel heureBtn = new JLabel("Heure");
		heureBtn.setBounds(10, 358, 45, 13);
		contentPane.add(heureBtn);

		JComboBox JourBox = new JComboBox();
		JourBox.addItem("LUNDI");
		JourBox.addItem("MARDI");
		JourBox.addItem("MERCREDI");
		JourBox.addItem("JEUDI");
		JourBox.addItem("VENDREDI");
		JourBox.addItem("SAMEDI");
		JourBox.setBounds(85, 325, 125, 21);
		contentPane.add(JourBox);

		JComboBox heureBox = new JComboBox();
		heureBox.addItem("1ere H");
		heureBox.addItem("2eme H");
		heureBox.addItem("3eme H");
		heureBox.addItem("4eme H");
		heureBox.addItem("5eme H");
		heureBox.addItem("6eme H");
		heureBox.addItem("1ere et 2eme H");
		heureBox.addItem("3eme et 4eme H");
		heureBox.addItem("5eme et 6eme H");
		heureBox.setBounds(85, 357, 125, 21);
		contentPane.add(heureBox);

		matiereTF = new JTextField();
		matiereTF.setBounds(85, 298, 125, 22);
		contentPane.add(matiereTF);
		matiereTF.setColumns(10);

		matriculeEnseignTF = new JTextField();
		matriculeEnseignTF.setColumns(10);
		matriculeEnseignTF.setBounds(133, 384, 77, 22);
		contentPane.add(matriculeEnseignTF);

		// Table cours
		DefaultTableModel dtmCours = new DefaultTableModel();
		tableCours = new JTable();
		tableCours.disable();
		scrollPanelCours = new JScrollPane();
		scrollPanelCours.setBounds(251, 267, 478, 163);
		scrollPanelCours.setViewportView(tableCours);
		contentPane.add(scrollPanelCours);
		dtmCours.addColumn("Classe");
		dtmCours.addColumn("Matiere");
		dtmCours.addColumn("Jour");
		dtmCours.addColumn("Heure");
		dtmCours.addColumn("Enseignant");
		showUpdatedTable(dtmCours, "cours");
		tableCours.setModel(dtmCours);

		JButton retourMenuBtn = new JButton("⬅  Retourner Au Menu");
		retourMenuBtn.setToolTipText("Retourner à la page d'accueil");
		retourMenuBtn.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		retourMenuBtn.setForeground(new Color(255, 255, 255));
		retourMenuBtn.setBackground(new Color(3, 129, 3));
		retourMenuBtn.setBounds(260, 10, 203, 21);
		retourMenuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuFrame menu = new MenuFrame();
				menu.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(retourMenuBtn);

		JButton enregistrerCoursBtn = new JButton("ENREGISTRER");
		enregistrerCoursBtn.setToolTipText("Ajouter cours");
		enregistrerCoursBtn.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		enregistrerCoursBtn.setBackground(new Color(209, 255, 189));
		enregistrerCoursBtn.setBounds(39, 416, 132, 21);
		enregistrerCoursBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (classeBox.getSelectedItem().equals(" ") || matiereTF.getText().length() == 0
						|| JourBox.getSelectedItem().equals(" ") || heureBox.getSelectedItem().equals(" ")
						|| matriculeEnseignTF.getText().length() == 0) {
					String errorMsgAddCours = "Une information essentielle est manquante pour procéder à l'ajout du cours. Veuillez fournir toutes les données nécessaires.";
					JOptionPane.showMessageDialog(null, errorMsgAddCours, null, JOptionPane.ERROR_MESSAGE);

				} else {
					if (JourBox.getSelectedItem().equals("LUNDI"))
						dbQuery.insertCourseSession(classeBox.getSelectedItem().toString(), matiereTF.getText(), 1,
								JourBox.getSelectedItem().toString(), heureBox.getSelectedItem().toString(),
								matriculeEnseignTF.getText());
					else if (JourBox.getSelectedItem().equals("MARDI"))
						dbQuery.insertCourseSession(classeBox.getSelectedItem().toString(), matiereTF.getText(), 2,
								JourBox.getSelectedItem().toString(), heureBox.getSelectedItem().toString(),
								matriculeEnseignTF.getText());
					else if (JourBox.getSelectedItem().equals("MERCREDI"))
						dbQuery.insertCourseSession(classeBox.getSelectedItem().toString(), matiereTF.getText(), 3,
								JourBox.getSelectedItem().toString(), heureBox.getSelectedItem().toString(),
								matriculeEnseignTF.getText());
					else if (JourBox.getSelectedItem().equals("JEUDI"))
						dbQuery.insertCourseSession(classeBox.getSelectedItem().toString(), matiereTF.getText(), 4,
								JourBox.getSelectedItem().toString(), heureBox.getSelectedItem().toString(),
								matriculeEnseignTF.getText());
					else if (JourBox.getSelectedItem().equals("VENDREDI"))
						dbQuery.insertCourseSession(classeBox.getSelectedItem().toString(), matiereTF.getText(), 5,
								JourBox.getSelectedItem().toString(), heureBox.getSelectedItem().toString(),
								matriculeEnseignTF.getText());
					else if (JourBox.getSelectedItem().equals("SAMEDI"))
						dbQuery.insertCourseSession(classeBox.getSelectedItem().toString(), matiereTF.getText(), 6,
								JourBox.getSelectedItem().toString(), heureBox.getSelectedItem().toString(),
								matriculeEnseignTF.getText());
					showUpdatedTable(dtmCours, "cours");
				}
			}
		});

		contentPane.add(enregistrerCoursBtn);

		JButton requetesBtn = new JButton("REQUETES  -->");
		requetesBtn.setToolTipText("Aller à la page des séances");
		requetesBtn.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		requetesBtn.setBackground(new Color(209, 255, 189));
		requetesBtn.setBounds(39, 447, 132, 21);
		requetesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RequetesFrame req = new RequetesFrame();
				req.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(requetesBtn);

		JButton deconnecterBtn = new JButton("Se déconnecter");
		deconnecterBtn.setForeground(Color.WHITE);
		deconnecterBtn.setFont(new Font("Tahoma", Font.PLAIN, 7));
		deconnecterBtn.setBackground(new Color(3, 129, 3));
		deconnecterBtn.setBounds(657, 8, 85, 21);
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
