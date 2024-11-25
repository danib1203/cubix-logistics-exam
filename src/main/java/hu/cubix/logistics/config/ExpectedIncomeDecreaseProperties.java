package hu.cubix.logistics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.TreeMap;

@ConfigurationProperties(prefix = "logistics")
@Component
public class ExpectedIncomeDecreaseProperties {

    private TreeMap<Integer, Long> expectedIncomeDecrease;

    public TreeMap<Integer, Long> getExpectedIncomeDecrease() {
        return expectedIncomeDecrease;
    }

    public void setExpectedIncomeDecrease(TreeMap<Integer, Long> expectedIncomeDecrease) {
        this.expectedIncomeDecrease = expectedIncomeDecrease;
    }
}
