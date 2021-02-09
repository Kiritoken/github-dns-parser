package dns.parser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eli
 */
@Data
@AllArgsConstructor
public class Host {

    private String domain;

    private String ipv4;
}
