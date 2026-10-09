package metiers;

import entities.Etudiant;
import entities.Option;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EtudiantBusiness {
    private static List<Etudiant> etudiants;
    private OptionBusiness optionBusiness = new OptionBusiness();

    public EtudiantBusiness() {
        if (etudiants != null) return;
        etudiants = new ArrayList<Etudiant>();
        etudiants.add(new Etudiant("I001", "Doe", "Jean", optionBusiness.getOptionByCode(1), 2023, "jean.doe@example.com"));
        etudiants.add(new Etudiant("I002", "Smith", "Alice", optionBusiness.getOptionByCode(1), 2022, "alice.smith@example.com"));
        etudiants.add(new Etudiant("I003", "Durand", "Pierre", optionBusiness.getOptionByCode(2), 2023, "pierre.durand@example.com"));
    }

    public boolean addEtudiant(Etudiant etudiant) {
        int codeOption = etudiant.getOption().getCodeOption();
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option != null) {
            etudiant.setOption(option);
            return etudiants.add(etudiant);
        }
        return false;
    }

    public Etudiant getEtudiantByIdentifiant(String identifiant) {
        for (Etudiant e : etudiants) {
            if (e.getIdentifiant().equals(identifiant)) {
                return e;
            }
        }
        return null;
    }

    public List<Etudiant> getEtudiantsByOption(Option option) {
        List<Etudiant> result = new ArrayList<>();
        for (Etudiant e : etudiants) {
            if (e.getOption() != null && e.getOption().getCodeOption() == option.getCodeOption()) {
                result.add(e);
            }
        }
        return result;
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiants;
    }

    public boolean updateEtudiant(String identifiant, Etudiant updatedEtudiant) {
        for (int i = 0; i < etudiants.size(); i++) {
            if (etudiants.get(i).getIdentifiant().equals(identifiant)) {
                etudiants.set(i, updatedEtudiant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteEtudiant(String identifiant) {
        Iterator<Etudiant> iterator = etudiants.iterator();
        while (iterator.hasNext()) {
            Etudiant e = iterator.next();
            if (e.getIdentifiant().equals(identifiant)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}