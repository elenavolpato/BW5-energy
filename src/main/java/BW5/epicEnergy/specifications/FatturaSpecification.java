package BW5.epicEnergy.specifications;

import BW5.epicEnergy.entity.Fattura;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FatturaSpecification {
    public static Specification<Fattura> filtra(
            UUID idCliente,
            String nomeCliente,
            String parteNomeCliente,
            String stato,
            LocalDate dataDopoDi,
            LocalDate dataPrimaDi,
            LocalDate data,
            Integer annoDopoDi,
            Integer annoPrimaDi,
            Integer anno,
            BigDecimal importoMin,
            BigDecimal importoMax,
            BigDecimal importo
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (idCliente != null) {
                predicates.add(cb.equal(root.get("cliente").get("id"), idCliente));
            }

            if (nomeCliente != null && !nomeCliente.isEmpty()) {
                predicates.add(cb.equal(root.get("cliente").get("ragioneSociale"), nomeCliente));
            }

            if (parteNomeCliente != null && !parteNomeCliente.isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("cliente").get("ragioneSociale")),
                        "%" + parteNomeCliente.toLowerCase() + "%"
                ));
            }

            if (stato != null && !stato.isEmpty()) {
                predicates.add(cb.equal(root.get("stato").get("tipo"), stato.toUpperCase()));
            }

            if (dataDopoDi != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("data"), dataDopoDi));
            }

            if (dataPrimaDi != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("data"), dataPrimaDi));
            }

            if (data != null) {
                predicates.add(cb.equal(root.get("data"), data));
            }

            if (annoDopoDi != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("data"),
                        LocalDate.of(annoDopoDi, 1, 1)
                ));
            }

            if (annoPrimaDi != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("data"),
                        LocalDate.of(annoPrimaDi, 12, 31)
                ));
            }

            if (anno != null) {
                LocalDate start = LocalDate.of(anno, 1, 1);
                LocalDate end = LocalDate.of(anno, 12, 31);

                predicates.add(cb.between(root.get("data"), start, end));
            }

            if (importoMin != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("importo"), importoMin));
            }

            if (importoMax != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("importo"), importoMax));
            }

            if (importo != null) {
                predicates.add(cb.equal(root.get("importo"), importo));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
