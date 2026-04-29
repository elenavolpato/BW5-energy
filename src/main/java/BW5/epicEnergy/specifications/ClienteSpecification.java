package BW5.epicEnergy.specifications;

import BW5.epicEnergy.entity.Cliente;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteSpecification {
    public static Specification<Cliente> filtra(
            BigDecimal fatturatoMin,
            BigDecimal fatturatoMax,
            BigDecimal fatturato,
            LocalDate dataInserimentoDopoDi,
            LocalDate dataInserimentoPrimaDi,
            LocalDate dataInserimento,
            LocalDate dataUltimoContattoDopoDi,
            LocalDate dataUltimoContattoPrimaDi,
            LocalDate dataUltimoContatto,
            String nome,
            String parteNome
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (fatturatoMin != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("fatturatoAnnuale"), fatturatoMin));
            }

            if (fatturatoMax != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("fatturatoAnnuale"), fatturatoMax));
            }

            if (fatturato != null) {
                predicates.add(cb.equal(root.get("fatturatoAnnuale"), fatturato));
            }

            if (dataInserimentoDopoDi != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataInserimento"), dataInserimentoDopoDi));
            }

            if (dataInserimentoPrimaDi != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dataInserimento"), dataInserimentoPrimaDi));
            }

            if (dataInserimento != null) {
                predicates.add(cb.equal(root.get("dataInserimento"), dataInserimento));
            }

            if (dataUltimoContattoDopoDi != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataUltimoContatto"), dataUltimoContattoDopoDi));
            }

            if (dataUltimoContattoPrimaDi != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dataUltimoContatto"), dataUltimoContattoPrimaDi));
            }

            if (dataUltimoContatto != null) {
                predicates.add(cb.equal(root.get("dataUltimoContatto"), dataUltimoContatto));
            }

            if (nome != null && !nome.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("ragioneSociale")), nome.toLowerCase()));
            }

            if (parteNome != null && !parteNome.isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("ragioneSociale")),
                        "%" + parteNome.toLowerCase() + "%"
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
