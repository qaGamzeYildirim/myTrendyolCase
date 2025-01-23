package enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum OperationsTypes {
    addition("/addition") ,
    subtraction("/subtraction"),
    multiplication("/multiplication"),
    division("/division"),
    sum("/sum"),
    user("/user");

    private final String endpoint;
    OperationsTypes(String endpoint) {
        this.endpoint = endpoint;
    }
    public String getEndpoint(){
        return this.endpoint;
    }
}
