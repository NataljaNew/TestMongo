package MongoDB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private String uniqueId;
    private String name;
    private String surname;
    private double balance;
}
