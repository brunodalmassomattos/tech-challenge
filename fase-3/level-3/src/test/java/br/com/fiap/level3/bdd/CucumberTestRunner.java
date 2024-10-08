package br.com.fiap.level3.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"br.com.fiap.level3.bdd"},
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class CucumberTestRunner {
}
