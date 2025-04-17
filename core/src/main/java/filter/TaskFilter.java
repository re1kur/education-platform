package filter;

import java.math.BigDecimal;

public record TaskFilter (
        String name,
        Integer trackId,
        Integer level,
        BigDecimal cost) {
}
