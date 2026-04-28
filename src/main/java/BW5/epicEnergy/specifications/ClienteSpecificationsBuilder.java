/*package BW5.epicEnergy.specifications;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ClienteSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public ClienteSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public final ClienteSpecificationsBuilder with(String key, String operation, Object value, String prefix, String suffix) {
        return with(key, operation, value, prefix, suffix);
    }

    public final ClienteSpecificationsBuilder with(String orPredicate, String key, String operation, Object value, String prefix, String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) {
                boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SearchCriteria(key, op, value, orPredicate.equals("true")));
        }
        return this;
    }

    public Specification build(){
        if(params.size() == 0)
            return null;
        Specification result = new ClienteSpecification(params.get(0));

        for (int i =1; i<params.size(); i++) {
            result = params.get(i).isOrPredicate() ? Specification.where(result).or(new ClienteSpecification(params.get(i)))
        }
    }
}*/
